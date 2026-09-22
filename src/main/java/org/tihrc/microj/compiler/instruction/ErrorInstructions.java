package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.PyBaseException;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.types.PyClass;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.Frame;

public class ErrorInstructions {
    public record SetupExcept(int handler) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            f.pushTryHandler(handler, f.pc);
            return true;
        }
    }

    public record PopTry() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            if (!f.tryHandlers.isEmpty()) {
                f.tryHandlers.pop();
            }
            return true;
        }
    }

    public record CheckException(String typeName, int exceptBodyTarget) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyBaseException exc = (PyBaseException) f.stack.peek();

            boolean matches = (typeName == null ||
                    typeName.equals(exc.getErrorType()) ||
                    typeName.equals("Exception") ||
                    typeName.equals("BaseException"));

            if (matches) {
                f.pc = exceptBodyTarget;
                return false;
            }
            return true;
        }
    }

    public record ReRaise() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyBaseException exc = (PyBaseException) f.stack.pop();
            throw new PyUnwind(exc);
        }
    }

    public record RaiseException() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject exc = f.stack.pop();
            if (exc instanceof PyClass) {
                exc = ((PyClass) exc).pyDanderCallFast(vm, new PyObject[0], new String[0], new PyObject[0]);
            }
            if (exc instanceof PyBaseException) {
                ((PyBaseException) exc).raise();
            } else {
                return new Exceptions.PyTypeError("exceptions must derive from BaseException").raise();
            }
            return true;
        }
    }

    public record Assert() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject msg = f.stack.pop();
            PyObject cond = f.stack.pop();
            boolean truthy = false;
            if (cond instanceof Protocols.PyComparable cmp) truthy = cmp.pyDanderBool();

            if (!truthy) {
                String msgStr = (msg == PyNone.INSTANCE) ? "assertion failed" : msg.toString();
                new Exceptions.PyAssertionError(msgStr).raise();
            }
            return true;
        }
    }
}
