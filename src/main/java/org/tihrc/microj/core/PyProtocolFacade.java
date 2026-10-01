package org.tihrc.microj.core;

import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.callables.PyFunction;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.core.PyNotImplemented;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.units.FastMap;
import org.tihrc.microj.units.SmartInt;

import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unused")
public final class PyProtocolFacade {

    private PyProtocolFacade() {}

    public static <P> PyObject dispatch(
            PyObject obj,
            Class<P> protocol,
            Function<P, PyObject> nativeCall,
            String dunder,
            RuntimeExecuter vm,
            PyObject[] args,
            Map<String, PyObject> kwargs) {

        if (protocol != null && protocol.isInstance(obj)) {
            PyObject res = nativeCall.apply(protocol.cast(obj));
            if (!(res instanceof PyNotImplemented)) {
                return res;
            }
        }

        PyObject method = obj.findAttribute(dunder);
        if (method != null) {
            if (method instanceof PyFunction) {
                PyObject[] allArgs = new PyObject[args.length + 1];
                allArgs[0] = obj;
                System.arraycopy(args, 0, allArgs, 1, args.length);
                return vm.callSync(method, allArgs, kwargs);
            } else return vm.callSync(method, args, kwargs);
        }
        return null;
    }

    public static <P> PyObject dispatchBinary(
            PyObject left, PyObject right,
            Class<P> protocol,
            Function<P, PyObject> nativeCall,
            String dunder, String reflectedDunder,
            RuntimeExecuter vm) {

        if (protocol != null && protocol.isInstance(left)) {
            PyObject res = nativeCall.apply(protocol.cast(left));
            if (!(res instanceof PyNotImplemented)) {
                return res;
            }
        }

        PyObject method = left.findAttribute(dunder);
        if (method != null) {
            if (method instanceof PyFunction) {
                return vm.callSync(method, left, right);
            } else {
                return vm.callSync(method, right);
            }
        }

        PyObject rmethod = right.findAttribute(reflectedDunder);
        if (rmethod != null) {
            if (rmethod instanceof PyFunction) {
                return vm.callSync(rmethod, right, left);
            } else {
                return vm.callSync(rmethod, left);
            }
        }

        return null;
    }

    @SafeVarargs
    private static <T> T[] vArr(T... objs) {
        return objs;
    }

    // ===================== Sequence =====================

    public static PyObject getItem(
            PyObject obj,
            PyObject index,
            RuntimeExecuter vm
    ) {
        if (obj instanceof Protocols.PyContainer seq) {
            PyObject res = seq.pyDanderGetItem(index);
            if (res != PyNotImplemented.INSTANCE)
                return res;
        }

        PyObject method = obj.findAttribute("__getitem__");

        if (method != null) {
            if (method instanceof PyFunction) {
                return vm.callSync(method, obj, index);
            }
            return vm.callSync(method, index);
        }

        return new Exceptions.PyTypeError(
                "'" + obj.pyDanderRepr() + "' object is not subscriptable"
        ).raise();
    }

    public static void setItem(PyObject obj, PyObject index, PyObject value, RuntimeExecuter vm) {
        PyObject res = dispatch(obj, Protocols.PyContainer.class,
                seq -> { seq.pyDanderSetItem(index, value); return PyNone.INSTANCE; },
                "__setitem__", vm, vArr(index, value), FastMap.empty());
        if (res == null) {
            new Exceptions.PyTypeError(
                    "'" + obj.pyDanderRepr() + "' object does not support item assignment"
            ).raise();
        }
    }

    public static int getLen(PyObject obj, RuntimeExecuter vm) {
        PyObject res = dispatch(obj, Protocols.PyContainer.class,
                seq -> PyInt.from(seq.pyDanderLen()), "__len__", vm, vArr(), FastMap.empty());
        if (res instanceof PyInt i) return i.value.toInt();
        return new Exceptions.PyTypeError(
                "object of type '" + obj.getClass().getSimpleName() + "' has no len()"
        ).raise();
    }

    // ===================== Callable =====================

    public static PyObject call(PyObject func, RuntimeExecuter vm, PyObject[] args, Map<String, PyObject> kwargs) {
        if (func instanceof Protocols.PyCallable builtin) {
            return builtin.pyDanderCall(vm, FastMap.empty(), args);
        }
        PyObject res = dispatch(func, null, null, "__call__", vm, args, kwargs);
        if (res == null) {
            return  new Exceptions.PyTypeError(
                    "'" + func.pyDanderRepr() + "' object is not callable"
            ).raise();
        }
        return res;
    }

    // ===================== Итерация =====================

    public static Protocols.PyIterator getIterator(PyObject obj, RuntimeExecuter vm) {
        if (obj instanceof Protocols.PyIterable it) {
            Protocols.PyIterator iter = it.pyDanderIter();
            if (iter instanceof PyObject) return iter;
        }
        PyObject method = obj.findAttribute("__iter__");
        if (method != null) {
            PyObject result = (method instanceof PyFunction)
                    ? vm.callSync(method, obj)
                    : vm.callSync(method);
            if (result instanceof Protocols.PyIterator iter) return iter;
            return new PyIteratorAdapter(result, vm);
        }
        return new Exceptions.PyTypeError(
                "'" + obj.pyDanderRepr() + "' object is not iterable"
        ).raise();
    }

    public static PyObject getNext(PyObject iterator, RuntimeExecuter ctx) {
        if (iterator instanceof Protocols.PyIterator iter) {
            return iter.pyDanderNext();
        }
        PyObject method = iterator.findAttribute("__next__");
        if (method != null) {
            return (method instanceof PyFunction)
                    ? ctx.callSync(method, iterator)
                    : ctx.callSync(method);
        }
        return new Exceptions.PyTypeError(
                "'" + iterator.pyDanderRepr() + "' object is not an iterator"
        ).raise();
    }

    // ===================== Истинность =====================

    public static boolean getBool(PyObject obj, RuntimeExecuter vm) {
        if (obj instanceof Protocols.PyComparable cmp) {
            return cmp.pyDanderBool();
        }
        PyObject res = dispatch(obj, null, null, "__bool__", vm, null, null);
        if (res != null) {
            if (res instanceof PyBool b) return b.pyDanderBool();
            return res != PyNone.INSTANCE;
        }
        // Fallback: __len__
        if (obj instanceof Protocols.PyContainer seq) {
            return seq.pyDanderLen() > 0;
        }
        PyObject lenRes = dispatch(obj, null, null, "__len__", vm, null, null);
        if (lenRes instanceof PyInt i) {
            return !i.value.equals(SmartInt.ZERO);
        }
        return true;
    }

    // ===================== Строковые =====================

    public static String getRepr(PyObject obj, RuntimeExecuter vm) {
        PyObject method = obj.findAttribute("__repr__");
        if (method != null) {
            PyObject res = (method instanceof PyFunction)
                    ? vm.callSync(method, obj)
                    : vm.callSync(method);
            if (res instanceof PyString s) return s.value;
            return res.pyDanderRepr();
        }
        return obj.pyDanderRepr();
    }

    public static String getStr(PyObject obj, RuntimeExecuter vm) {
        PyObject method = obj.findAttribute("__str__");
        if (method != null) {
            PyObject res = (method instanceof PyFunction)
                    ? vm.callSync(method, obj)
                    : vm.callSync(method);
            if (res instanceof PyString s) return s.value;
            return res.pyDanderStr();
        }
        return obj.pyDanderStr();
    }

    // ===================== Арифметика =====================

    public static PyObject add(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyNumber.class,
                num -> num.pyDanderAdd(right), "__add__", "__radd__", vm);
        if (res == null) return binOpError("+", left, right).raise();
        return res;
    }

    public static PyObject sub(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyNumber.class,
                num -> num.pyDanderSub(right), "__sub__", "__rsub__", vm);
        if (res == null) return binOpError("-", left, right).raise();
        return res;
    }

    public static PyObject mul(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyNumber.class,
                num -> num.pyDanderMul(right), "__mul__", "__rmul__", vm);
        if (res == null) return binOpError("*", left, right).raise();
        return res;
    }

    public static PyObject truediv(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyNumber.class,
                num -> num.pyDanderTrueDiv(right), "__truediv__", "__rtruediv__", vm);
        if (res == null) return binOpError("/", left, right).raise();
        return res;
    }

    public static PyObject neg(PyObject obj, RuntimeExecuter vm) {
        PyObject res = dispatch(obj, Protocols.PyNumber.class,
                Protocols.PyNumber::pyDanderNeg, "__neg__", vm, vArr(), FastMap.empty());
        if (res == null) {
            return new Exceptions.PyTypeError(
                    "bad operand type for unary -: '" + obj.pyDanderRepr() + "'"
            ).raise();
        }
        return res;
    }

    // ===================== Сравнения =====================

    public static PyObject eq(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyComparable.class,
                cmp -> cmp.pyDanderEq(right), "__eq__", "__eq__", vm);
        if (res != null && !(res instanceof PyNotImplemented)) return res;
        return PyBool.from(left == right);
    }

    public static PyObject ne(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyComparable.class,
                cmp -> cmp.pyDanderNe(right), "__ne__", "__ne__", vm);
        if (res != null && !(res instanceof PyNotImplemented)) return res;
        return PyBool.from(left != right);
    }

    public static PyObject lt(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyComparable.class,
                cmp -> cmp.pyDanderLt(right), "__lt__", "__gt__", vm);
        if (res == null) return binOpError("<", left, right).raise();
        return res;
    }

    public static PyObject le(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyComparable.class,
                cmp -> cmp.pyDanderLe(right), "__le__", "__ge__", vm);
        if (res == null) return binOpError("<=", left, right).raise();
        return res;
    }

    public static PyObject gt(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyComparable.class,
                cmp -> cmp.pyDanderGt(right), "__gt__", "__lt__", vm);
        if (res == null) return binOpError(">", left, right).raise();
        return res;
    }

    public static PyObject ge(PyObject left, PyObject right, RuntimeExecuter vm) {
        PyObject res = dispatchBinary(left, right, Protocols.PyComparable.class,
                cmp -> cmp.pyDanderGe(right), "__ge__", "__le__", vm);
        if (res == null) return binOpError(">=", left, right).raise();
        return res;
    }

    // ===================== Хелперы =====================

    private static Exceptions.PyTypeError binOpError(String op, PyObject left, PyObject right) {
        return new Exceptions.PyTypeError(
                "unsupported operand type(s) for " + op + ": '" +
                        left.pyDanderRepr() + "' and '" + right.pyDanderRepr() + "'"
        );
    }
}