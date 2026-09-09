package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.PyBaseException;
import org.tihrc.microj.core.exceptions.PyUnwind;
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

    public record CheckException(String typeName, int nextExceptTarget) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyBaseException exc = (PyBaseException) f.stack.peek();
            if (typeName == null || typeName.equals(exc.getErrorType()) || typeName.equals("Exception") || typeName.equals("BaseException")) {
                return true;
            }
            f.pc = nextExceptTarget;
            return false;
        }
    }

    public record ReRaise() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyBaseException exc = (PyBaseException) f.stack.pop();
            throw new PyUnwind(exc);
        }
    }
}
