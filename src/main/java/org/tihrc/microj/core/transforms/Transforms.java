package org.tihrc.microj.core.transforms;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.collections.PyList;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyFloat;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.units.SmartFloat;
import org.tihrc.microj.units.SmartInt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public final class Transforms {
    private Transforms() {}

    private static final SmartInt INT_MAX  = new SmartInt(Integer.MAX_VALUE);
    private static final SmartInt INT_MIN  = new SmartInt(Integer.MIN_VALUE);
    private static final SmartInt LONG_MAX = new SmartInt(Long.MAX_VALUE);
    private static final SmartInt LONG_MIN = new SmartInt(Long.MIN_VALUE);

    private static int checkedInt(PyInt i) {
        if (i.value.compareTo(INT_MAX) > 0 || i.value.compareTo(INT_MIN) < 0)
            return new Exceptions.PyOverflowError("Python int too large to convert to Java int").raise();
        return i.value.toInt();
    }

    private static long checkedLong(PyInt i) {
        if (i.value.compareTo(LONG_MAX) > 0 || i.value.compareTo(LONG_MIN) < 0)
            return new Exceptions.PyOverflowError("Python int too large to convert to Java long").raise();
        return i.value.toLong();
    }

    @SuppressWarnings("unchecked")
    public static <T extends PyObject> T check(PyObject obj, Class<T> expected) {
        if (expected.isInstance(obj)) return (T) obj;

        String expectedName = expected.getSimpleName().toLowerCase().replace("Py", "");
        String actualName = obj == null ? "None" : obj.getClass().getSimpleName().toLowerCase().replace("Py", "");
        return new Exceptions.PyTypeError("expected '" + expectedName + "', but got '" + actualName + "'").raise();
    }

    public static PyString checkString(PyObject obj) { return check(obj, PyString.class); }
    public static PyInt checkInt(PyObject obj)       { return check(obj, PyInt.class); }
    public static PyBool checkBool(PyObject obj)     { return check(obj, PyBool.class); }
    public static PyList checkList(PyObject obj)     { return check(obj, PyList.class); }
    public static PyDict checkDict(PyObject obj)     { return check(obj, PyDict.class); }
    public static PyTuple checkTuple(PyObject obj)   { return check(obj, PyTuple.class); }

    private static <T> T typeError(PyObject obj, String expectedType) {
        String actualName = obj.getClass().getSimpleName().toLowerCase().replace("py", "");
        return new Exceptions.PyTypeError("expected '" + expectedType + "', but got '" + actualName + "'").raise();
    }

    private static <T> T fail(PyObject obj, String expectedType, boolean strict) {
        if (strict) return typeError(obj, expectedType);
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(PyObject obj, Class<T> type, boolean strict) {
        if (obj == null || obj == PyNone.INSTANCE) {
            if (type == PyObject.class || type == Object.class) return null;
            if (strict)
                return new Exceptions.PyTypeError("Cannot convert None to " + type.getSimpleName()).raise();
            return null;
        }

        if (type == PyObject.class || type == Object.class) return (T) obj;

        if (type == String.class) {
            if (obj instanceof PyString s) return (T) s.value;
            return fail(obj, "str", strict);
        }

        if (type == Integer.class || type == int.class) {
            if (obj instanceof PyBool b) return (T) Integer.valueOf(b.boolValue ? 1 : 0);
            if (obj instanceof PyInt i)  return (T) Integer.valueOf(checkedInt(i));
            return fail(obj, "int", strict);
        }

        if (type == Long.class || type == long.class) {
            if (obj instanceof PyBool b) return (T) Long.valueOf(b.boolValue ? 1 : 0);
            if (obj instanceof PyInt i)  return (T) Long.valueOf(checkedLong(i));
            return fail(obj, "int", strict);
        }

        if (type == Double.class || type == double.class) {
            if (obj instanceof PyFloat f) return (T) Double.valueOf(f.value.toDouble());
            if (obj instanceof PyInt i)   return (T) Double.valueOf(i.value.toDouble());
            return fail(obj, "float", strict);
        }
        if (type == Float.class || type == float.class) {
            if (obj instanceof PyFloat f) return (T) Float.valueOf((float) f.value.toDouble());
            if (obj instanceof PyInt i)   return (T) Float.valueOf((float) i.value.toDouble());
            return fail(obj, "float", strict);
        }

        if (type == Boolean.class || type == boolean.class) {
            if (obj instanceof PyBool b) return (T) Boolean.valueOf(b.boolValue);
            if (obj instanceof PyInt i)  return (T) Boolean.valueOf(i.value.toInt() != 0);
            return fail(obj, "bool", strict);
        }

        if (type == BigInteger.class) {
            if (obj instanceof PyInt i) return (T) i.value.toBigInteger();
            return fail(obj, "int", strict);
        }
        if (type == BigDecimal.class) {
            if (obj instanceof PyInt i)   return (T) i.value.toBigDecimal();
            if (obj instanceof PyFloat f) return (T) f.value.toBigDecimal();
            return fail(obj, "number", strict);
        }

        if (type == List.class) {
            if (obj instanceof PyList list) return (T) list.getInner();
            if (obj instanceof PyTuple tuple) {
                List<PyObject> javaList = new ArrayList<>(tuple.pyDanderLen());
                for (int i = 0; i < tuple.pyDanderLen(); i++)
                    javaList.add(tuple.pyDanderGetItem(PyInt.from(i)));
                return (T) javaList;
            }
            return fail(obj, "list", strict);
        }
        if (type == Map.class) {
            if (obj instanceof PyDict dict) return (T) dict.getInner();
            return fail(obj, "dict", strict);
        }

        throw new RuntimeException("PyTransform Error: Unsupported Java type '" + type.getName() + "'");
    }

    public static <T> T fromPython(PyObject obj, Class<T> type) {
        return convert(obj, type, true);
    }

    public static <T> T fromPythonOrNull(PyObject obj, Class<T> type) {
        return convert(obj, type, false);
    }

    public static PyObject toPython(Object res) {
        if (res == null) return PyNone.INSTANCE;

        return switch (res) {
            case PyObject py -> py;
            case String s -> new PyString(s);
            case Character c -> new PyString(String.valueOf(c));
            case Integer i -> PyInt.from(i);
            case Long l -> PyInt.from(l);
            case Short s -> PyInt.from(s.intValue());
            case Byte by -> PyInt.from(by.intValue());
            case BigInteger bi -> new PyInt(new SmartInt(bi));
            case Boolean b -> PyBool.from(b);
            case Double d -> new PyFloat(new SmartFloat(d));
            case Float f -> new PyFloat(new SmartFloat(f.doubleValue()));
            case BigDecimal bd -> new PyFloat(new SmartFloat(bd));

            case List<?> list -> {
                PyObject[] items = new PyObject[list.size()];
                for (int i = 0; i < list.size(); i++) items[i] = toPython(list.get(i));
                yield new PyList(items);
            }
            case Map<?, ?> map -> {
                PyDict dict = new PyDict();
                for (var entry : map.entrySet())
                    dict.put(toPython(entry.getKey()), toPython(entry.getValue()));
                yield dict;
            }

            default -> throw new RuntimeException(
                    "Cannot convert Java type '" + res.getClass().getName() + "' to Python");
        };
    }
}