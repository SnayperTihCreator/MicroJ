package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyMethodProxy;
import org.tihrc.microj.types.callables.PyBoundMethod;
import org.tihrc.microj.types.callables.PyFunction;
import org.tihrc.microj.types.sequences.PyGeneratorFunc;
import org.tihrc.microj.types.core.PyCell;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.runtime.PyCode;
import org.tihrc.microj.units.Constants;
import org.tihrc.microj.units.FastMap;
import org.tihrc.microj.units.Frame;
import org.tihrc.microj.units.LineTables;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CallInstructions {
    private static PyObject preBindFreeVars(Frame f, RuntimeExecuter ctx, List<String> freeVars, Map<String, PyObject> closure) {
        PyObject defaults = f.stack.pop();
        if (f.locals != ctx.getGlobals()) {
            for (String free : freeVars) {
                PyObject cur = f.locals.get(free);
                if (cur instanceof PyCell cell) closure.put(free, cell);
                else if (cur != null) {
                    PyCell cell = new PyCell(cur);
                    f.locals.put(free, cell);
                    closure.put(free, cell);
                } else if (f.closure != null && f.closure.get(free) instanceof PyCell outer)
                    closure.put(free, outer);
//                else {
//                    PyCell cell = new PyCell(null);
//                    f.locals.put(free, cell);
//                    closure.put(free, cell);
//                }
            }
        }
        return defaults;
    }

    public record MakeFunction(String name, List<Instruction> body, List<String> params,
                               String starArg, String kwArg, List<String> freeVars, List<int[]> funcLines) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            Map<String, PyObject> closure = new FastMap<>();
            PyObject defaults = preBindFreeVars(f, ctx, freeVars, closure);
            f.stack.push(new PyFunction(name, body, params, starArg, kwArg,
                    closure, defaults, f.constants, LineTables.build(body.size(), funcLines)));
            return true;
        }
    }

    public record MakeGenerator(String name, int codeIndex, List<String> params,
                                String starArg, String kwArg, List<String> freeVars) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            Map<String, PyObject> closure = new FastMap<>();
            PyObject defaults = preBindFreeVars(f, ctx, freeVars, closure);
            PyCode code = (PyCode) f.constants[codeIndex];
            f.stack.push(new PyGeneratorFunc(name, code.body, params, starArg, kwArg, closure, defaults, f.constants, code.lineTable));
            return true;
        }
    }

    public record CallFunction(int posCount, String[] kwNames) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            PyObject func = f.stack.pop();
            int kwCount = kwNames.length;
            PyObject[] kwValues = null;

            if (kwCount > 0) {
                kwValues = new PyObject[kwCount];
                for (int i = kwCount - 1; i >= 0; i--) kwValues[i] = f.stack.pop();
            }

            PyObject[] args;
            if (posCount == 0) args = Constants.NO_ARGS;
            else {
                args = new PyObject[posCount];
                for (int i = posCount - 1; i >= 0; i--) args[i] = f.stack.pop();
            }

            if (func instanceof PyBoundMethod bound) {
                PyObject result = bound.callBoundFast(ctx, args, kwNames, kwValues);
                f.stack.push(result);
                return true;
            }

            if (func instanceof PyFunction pyFunction) {
                PyObject result = pyFunction.pyDanderCallFast(ctx, args, kwNames, kwValues);
                f.stack.push(result);
                return true;
            }

            if (func instanceof Protocols.PyCallable callable) {
                PyObject result = callable.pyDanderCallFast(ctx, args, kwNames, kwValues);
                f.stack.push(result);
                return true;
            }

            if (func.hasCap(Capability.CALLABLE)) {
                PyObject callMethod = func.findAttribute("__call__");
                if (callMethod != null) {
                    if ((callMethod instanceof PyFunction || callMethod instanceof PyMethodProxy) && !(func instanceof PyModule)) {
                        callMethod = new PyBoundMethod(func, callMethod);
                    }
                    if (callMethod instanceof Protocols.PyCallable callable) {
                        PyObject result = callable.pyDanderCallFast(ctx, args, kwNames, kwValues);
                        f.stack.push(result);
                        return true;
                    }
                }
            }
            return new Exceptions.PyTypeError("'" + func + "' object is not callable").raise();
        }

        @Override
        public String toString() {
            return "CallFunction[posCount=%s, kwNames=%s]".formatted(posCount, Arrays.toString(kwNames));
        }
    }
}