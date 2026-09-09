package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.Frame;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.PyFunction;
import org.tihrc.microj.types.primitives.PyBool;

public class ControlFlowInstructions {
    public record PopJumpIfFalse(int target) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject cond = f.stack.pop();
            boolean truthy;

            if (cond instanceof Protocols.PyComparable cmp) {
                truthy = cmp.pyDanderBool();
            } else {
                PyObject method = cond.findAttribute("__bool__");
                if (method != null) {
                    PyObject res = vm.callSync(method, cond);
                    truthy = !(res instanceof PyBool b) || b.value;
                } else {
                    truthy = true;
                }
            }

            if (!truthy) {
                f.pc = target;
                return false;
            }
            return true;
        }
    }

    public record JumpAbsolute(int target) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            f.pc = target;
            return false;
        }
    }

    public record JumpIfFalseOrPop(int target) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject cond = f.stack.peek();
            boolean truthy;

            if (cond instanceof Protocols.PyComparable cmp) {
                truthy = cmp.pyDanderBool();
            } else {
                PyObject method = cond.findAttribute("__bool__");
                if (method != null) {
                    PyObject res = vm.callSync(method, cond);
                    truthy = !(res instanceof PyBool b) || b.value;
                } else {
                    truthy = true;
                }
            }

            if (!truthy) {
                f.pc = target;
                return false;
            } else {
                f.stack.pop();
                return true;
            }
        }
    }

    public record JumpIfTrueOrPop(int target) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject cond = f.stack.peek();
            boolean truthy;

            if (cond instanceof Protocols.PyComparable cmp) {
                truthy = cmp.pyDanderBool();
            } else {
                PyObject method = cond.findAttribute("__bool__");
                if (method != null) {
                    PyObject res = vm.callSync(method, cond);
                    truthy = !(res instanceof PyBool b) || b.value;
                } else {
                    truthy = true;
                }
            }

            if (truthy) {
                f.pc = target;
                return false;
            } else {
                f.stack.pop();
                return true;
            }
        }
    }

    public record GetIter() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject obj = f.stack.pop();

            if (obj instanceof Protocols.PyIterable iter) {
                f.stack.push((PyObject) iter.pyDanderIter());
                return true;
            }

            if (obj.hasCap(Capability.ITERABLE)) {
                PyObject method = obj.findAttribute("__iter__");
                PyObject result = vm.callSync(method, obj);
                f.stack.push(result);
                return true;
            }

            return new Exceptions.PyTypeError("object is not iterable").raise();
        }
    }

    public record ForIter(int target) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject iterObj = f.stack.peek();

            if (iterObj instanceof Protocols.PyIterator iter) {
                try {
                    PyObject item = iter.pyDanderNext();
                    f.stack.push(item);
                    return true;
                } catch (PyUnwind e) {
                    if (e.exception instanceof Exceptions.PyStopIteration) {
                        f.stack.pop();
                        f.pc = target;
                        return false;
                    }
                    throw e;
                }
            }

            if (iterObj.hasCap(Capability.ITERATOR)) {
                PyObject method = iterObj.findAttribute("__next__");
                if (method instanceof PyFunction pyMethod) {
                    Frame newFrame = pyMethod.createClosure();
                    if (!pyMethod.params.isEmpty()) {
                        newFrame.locals.put(pyMethod.params.getFirst(), iterObj);
                    }

                    f.pc++;
                    vm.pushTask(newFrame.createTask(
                            result -> {
                                if (result == PyNone.INSTANCE) {
                                    f.stack.pop();
                                    f.pc = target;
                                } else {
                                    f.stack.push(result);
                                }
                            },
                            exc -> {
                                if (exc instanceof Exceptions.PyStopIteration) {
                                    f.stack.pop();
                                    f.pc = target;
                                } else {
                                    exc.raise();
                                }
                            }
                    ));
                    return false;
                }
            }

            return new Exceptions.PyTypeError("'" + iterObj + "' object is not an iterator").raise();
        }
    }
}