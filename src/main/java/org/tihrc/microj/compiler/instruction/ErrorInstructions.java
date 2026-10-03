package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.backend.jvm.JvmHelper;
import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.*;
import org.tihrc.microj.types.objects.PyClass;
import org.tihrc.microj.types.objects.PyInstance;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.units.Constants;
import org.tihrc.microj.units.Frame;

public final class ErrorInstructions {
    private ErrorInstructions() {}

    public record SetupExcept(int handler) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            f.pushTryHandler(handler, f.pc);
            return true;
        }
    }

    public record PopTry() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            if (!f.tryHandlers.isEmpty()) {
                f.tryHandlers.pop();
            }
            return true;
        }
    }

    public record CheckException(String typeName, int exceptBodyTarget) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            if (ExceptionsRegistry.matches(f.stack.peek(), typeName)) {
                f.pc = exceptBodyTarget;
                return false;
            }
            return true;
        }
    }

    public record ReRaise() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            if (f.stack.isEmpty())
                return new Exceptions.PyRuntimeError("No active exception to re-raise").raise();
            throw new PyUnwind(f.stack.pop());
        }
    }

    public record RaiseException() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            PyObject exc = f.stack.pop();
            if (exc instanceof PyClass cls) {
                if (!ExceptionsRegistry.isExceptionClass(cls))
                    return new Exceptions.PyTypeError("exceptions must derive from BaseException").raise();
                exc = cls.pyDanderCallFast(ctx, Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
            }
            if (exc instanceof PyInstance inst) {
                if (!ExceptionsRegistry.isExceptionClass(inst.pyClass))
                    return new Exceptions.PyTypeError("exceptions must derive from BaseException").raise();
                throw new PyUnwind(inst);
            }
            if (exc instanceof PyBaseException pe)
                return pe.raise();
            return new Exceptions.PyTypeError("exceptions must derive from BaseException").raise();
        }
    }

    public record Assert() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            PyObject msg = f.stack.pop();
            PyObject cond = f.stack.pop();

            if (!JvmHelper.truthy(ctx, cond)) {
                String msgStr = (msg == PyNone.INSTANCE) ? "assertion failed" : msg.pyDanderStr();
                new Exceptions.PyAssertionError(msgStr).raise();
            }
            return true;
        }
    }
}
