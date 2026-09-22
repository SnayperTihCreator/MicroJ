package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.BinaryOperator;
import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.compiler.UnaryOperator;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.PyFunction;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.units.FastMap;
import org.tihrc.microj.units.Frame;
import org.tihrc.microj.units.SmartInt;

public class OperatorInstructions {
    public record BinaryOp(BinaryOperator type) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject right = f.stack.pop();
            PyObject left = f.stack.pop();
            PyObject res = switch (type) {
                case ADD -> left instanceof Protocols.PyNumber n
                        ? n.pyDanderAdd(right)
                        : PyNotImplemented.INSTANCE;

                case SUB -> left instanceof Protocols.PyNumber n
                        ? n.pyDanderSub(right)
                        : PyNotImplemented.INSTANCE;

                case MUL -> left instanceof Protocols.PyNumber n
                        ? n.pyDanderMul(right)
                        : PyNotImplemented.INSTANCE;

                case DIV -> left instanceof Protocols.PyNumber n
                        ? n.pyDanderTrueDiv(right)
                        : PyNotImplemented.INSTANCE;

                case POW -> PyNotImplemented.INSTANCE;

                case EQ -> left instanceof Protocols.PyComparable c
                        ? c.pyDanderEq(right)
                        : PyNotImplemented.INSTANCE;

                case NE -> left instanceof Protocols.PyComparable c
                        ? c.pyDanderNe(right)
                        : PyNotImplemented.INSTANCE;

                case LT -> left instanceof Protocols.PyComparable c
                        ? c.pyDanderLt(right)
                        : PyNotImplemented.INSTANCE;

                case LE -> left instanceof Protocols.PyComparable c
                        ? c.pyDanderLe(right)
                        : PyNotImplemented.INSTANCE;

                case GT -> left instanceof Protocols.PyComparable c
                        ? c.pyDanderGt(right)
                        : PyNotImplemented.INSTANCE;

                case GE -> left instanceof Protocols.PyComparable c
                        ? c.pyDanderGe(right)
                        : PyNotImplemented.INSTANCE;
            };

            if (res != PyNotImplemented.INSTANCE) {
                f.stack.push(res);
                return true;
            }

            if (left.hasCap(this.type.getRequiredCap())) {
                PyObject method = left.findAttribute(this.type.getDunderName());

                if (method instanceof PyFunction pyMethod) {
                    Frame newFrame = pyMethod.createClosure();

                    if (!pyMethod.params.isEmpty()) {
                        newFrame.locals.put(pyMethod.params.getFirst(), left);  // self
                    }
                    if (pyMethod.params.size() > 1) {
                        newFrame.locals.put(pyMethod.params.get(1), right); // other
                    }

                    f.pc++;
                    vm.pushTask(newFrame.createTask(f.stack::push));
                    return false;
                }
            }
            return new Exceptions.PyTypeError("unsupported operand type for " + type).raise();
        }
    }
    public record BinaryIn(boolean inverted) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject container = f.stack.pop();  // правый операнд
            PyObject item = f.stack.pop();        // левый операнд

            // Fast path для встроенных типов
            if (container instanceof Protocols.PyContainer c) {
                PyObject res = c.pyDanderContains(item);
                if (res != PyNotImplemented.INSTANCE) {
                    boolean truthy = !(res instanceof Protocols.PyComparable cmp) || cmp.pyDanderBool();
                    f.stack.push(PyBool.from(inverted != truthy));
                    return true;
                }
            }

            // Slow path через дандер-метод пользовательского класса
            if (container.hasCap(Capability.CONTAINER)) {
                PyObject method = container.findAttribute("__contains__");
                if (method instanceof PyFunction pyMethod) {
                    Frame newFrame = pyMethod.createClosure();

                    if (!pyMethod.params.isEmpty()) {
                        newFrame.locals.put(pyMethod.params.getFirst(), container); // self
                    }
                    if (pyMethod.params.size() > 1) {
                        newFrame.locals.put(pyMethod.params.get(1), item);          // item
                    }

                    f.pc++;
                    vm.pushTask(newFrame.createTask(result -> {
                        boolean truthy;
                        if (result instanceof Protocols.PyComparable cmp) {
                            truthy = cmp.pyDanderBool();
                        } else if (result instanceof Protocols.PyContainer seq) {
                            truthy = seq.pyDanderLen() > 0;
                        } else {
                            truthy = true;
                        }
                        f.stack.push(PyBool.from(inverted != truthy));
                    }));
                    return false;
                }
            }

            return new Exceptions.PyTypeError(
                    "argument of type '" + container + "' is not iterable").raise();
        }
    }
    public record BinarySubscript() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject index = f.stack.pop();
            PyObject left = f.stack.pop();

            PyObject result;

            if (left instanceof Protocols.PyContainer container) {
                result = container.pyDanderGetItem(index);
            } else {
                result = PyProtocolFacade.getItem(left, index, vm);
            }

            f.stack.push(result);
            return true;
        }
    }

    public record StoreSubscript() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject value = f.stack.pop();
            PyObject index = f.stack.pop();
            PyObject left = f.stack.pop();

            // 1. Быстрый путь для встроенных типов
            if (left instanceof Protocols.PyContainer seq) {
                seq.pyDanderSetItem(index, value);
                return true;
            }

            if (left.hasCap(Capability.CONTAINER)) {
                PyObject method = left.findAttribute("__setitem__");
                if (method instanceof PyFunction pyMethod) {
                    Frame newFrame = pyMethod.createClosure();

                    if (!pyMethod.params.isEmpty()) {
                        newFrame.locals.put(pyMethod.params.getFirst(), left);  // self
                    }
                    if (pyMethod.params.size() > 1) {
                        newFrame.locals.put(pyMethod.params.get(1), index);    // index
                    }
                    if (pyMethod.params.size() > 2) {
                        newFrame.locals.put(pyMethod.params.get(2), value);    // value
                    }

                    f.pc++;
                    vm.pushTask(newFrame.createTask(res -> {}));
                    return false;
                }
            }

            return new Exceptions.PyTypeError("'" + left + "' object does not support item assignment").raise();
        }
    }

    public record UnaryOp(UnaryOperator type) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject operand = f.stack.pop();

            if (operand instanceof Protocols.PyNumber num) {
                PyObject res = num.pyDanderNeg();
                if (res != PyNotImplemented.INSTANCE) {
                    f.stack.push(res);
                    return true;
                }
            }

            if (operand.hasCap(Capability.NUMBER)) {
                PyObject method = operand.findAttribute(type.getDunderName());
                if (method instanceof PyFunction pyMethod) {
                    Frame newFrame = pyMethod.createClosure();
                    if (!pyMethod.params.isEmpty()) {
                        newFrame.locals.put(pyMethod.params.getFirst(), operand);
                    }
                    f.pc++;
                    vm.pushTask(newFrame.createTask(f.stack::push));
                    return false;
                }
            }

            return new Exceptions.PyTypeError("bad operand type for unary -: '" + operand + "'").raise();
        }
    }

    public record UnaryNot() implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject operand = f.stack.pop();
            boolean truthy;

            if (operand instanceof Protocols.PyComparable cmp) {
                truthy = cmp.pyDanderBool();
            }

            else if (operand instanceof Protocols.PyContainer seq) {
                int result = seq.pyDanderLen();
                truthy = result > 0;
            }

            else if (operand.hasCap(Capability.CONTAINER)) {
                PyObject method = operand.findAttribute("__len__");
                if (method instanceof Protocols.PyCallable builtin) {
                    PyObject res = builtin.pyDanderCall(vm, FastMap.empty(), operand);
                    truthy = res instanceof PyInt i && !i.value.equals(SmartInt.ZERO);
                } else {
                    truthy = true;
                }
            }
            else {
                truthy = true;
            }

            f.stack.push(PyBool.from(!truthy));
            return true;
        }
    }
}
