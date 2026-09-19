package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.Frame;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.units.FrameTask;

public class StackInstructions {
    public record LoadConst(int index) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            f.stack.push(f.constants[index]);
            return true;
        }
    }
    public record StoreName(String name) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            f.locals.put(name, f.stack.pop());
            return true;
        }
    }
    public record LoadName(String name) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject value;

            if (f.locals == vm.getGlobals()) {
                value = f.locals.get(name);
            } else {
                value = f.locals.get(name);

                if (value == null && f.closure != null)
                    value = f.closure.get(name);

                if (value == null)
                    value = vm.getGlobals().get(name);
            }

            if (value == null)
                value = vm.getBuiltins().findAttribute(name);

            if (value == null)
                return new Exceptions.PyNameError(
                        "name '" + name + "' is not defined"
                ).raise();

            f.stack.push(value);
            return true;
        }
    }

    public record DeleteName(String name) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            f.locals.remove(name);
            return true;
        }
    }

    public record Global(String name) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) { return true; }
    }

    public record Nonlocal(String name) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) { return true; }
    }

    public record PopTop() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            f.stack.pop();
            return true;
        }
    }

    public record ReturnValue() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject retVal = f.stack.pop();
            FrameTask task = vm.popTask();
            task.result(retVal);

            if (task.callback() != null) task.callback().accept(retVal);
            else if (!vm.isEmpty()) vm.getCurrentFrame().stack.push(retVal);
            return false;
        }
    }

    public record Yield() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject val = f.stack.isEmpty() ? PyNone.INSTANCE : f.stack.pop();
            FrameTask task = vm.getCurrentTask();
            task.result(val);
            task.yielded(true);
            return true;
        }
    }
}
