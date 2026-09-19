package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyMethodProxy;
import org.tihrc.microj.types.*;
import org.tihrc.microj.types.collections.PyGenerator;
import org.tihrc.microj.types.collections.PyGeneratorFunc;
import org.tihrc.microj.units.Frame;

import java.util.Arrays;
import java.util.List;

public class CallInstructions {
    private static final PyObject[] NO_ARGS = new PyObject[0];
    private static final String[] NO_KW_NAMES = new String[0];

    public record MakeFunction(String name, List<Instruction> body, List<String> params, String starArg, String kwArg) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            f.stack.push(new PyFunction(name, body, params, f.locals, f.constants));
            return true;
        }
    }

    public record MakeGenerator(String name, int codeIndex, List<String> params) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyCode code = (PyCode) f.constants[codeIndex];
            f.stack.push(new PyGeneratorFunc(name, code.body, params, f.constants));
            return true;
        }
    }

    public record CallFunction(int posCount, String[] kwNames) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject func = f.stack.pop();
            int kwCount = kwNames.length;
            PyObject[] kwValues = null;

            if (kwCount > 0) {
                kwValues = new PyObject[kwCount];
                for (int i = kwCount - 1; i >= 0; i--) kwValues[i] = f.stack.pop();
            }

            PyObject[] args;
            if (posCount == 0) args = NO_ARGS;
            else {
                args = new PyObject[posCount];
                for (int i = posCount - 1; i >= 0; i--) args[i] = f.stack.pop();
            }

            if (func instanceof PyBoundMethod bound) {
                PyObject result = bound.callBoundFast(vm, args, kwNames, kwValues);
                f.stack.push(result);
                return true;
            }

            if (func instanceof PyFunction pyFunction) {
                PyObject result = pyFunction.pyDanderCallFast(vm, args, kwNames, kwValues);
                f.stack.push(result);
                return true;
            }

            if (func instanceof Protocols.PyCallable callable) {
                PyObject result = callable.pyDanderCallFast(vm, args, kwNames, kwValues);
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
                        PyObject result = callable.pyDanderCallFast(vm, args, kwNames, kwValues);
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