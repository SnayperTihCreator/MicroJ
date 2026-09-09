package org.tihrc.microj.core.transforms;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.collections.PyList;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyFloat;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.SmartFloat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class Transforms {
    private Transforms() {}

    @SuppressWarnings("unchecked")
    public static <T extends PyObject> T check(PyObject obj, Class<T> expected){
        if (expected.isInstance(obj)) return (T)obj;

        String expectedName = expected.getSimpleName().toLowerCase().replace("Py", "");
        String actualName = obj == null ? "None" : obj.getClass().getSimpleName().toLowerCase().replace("Py", "");
        return new Exceptions.PyTypeError("expected '" + expectedName + "', but got '" + actualName + "'").raise();
    }

    public static PyString checkString(PyObject obj) {
        return check(obj, PyString.class);
    }
    public static PyInt checkInt(PyObject obj) {
        return check(obj, PyInt.class);
    }
    public static PyBool checkBool(PyObject obj) {
        return check(obj, PyBool.class);
    }
    public static PyList checkList(PyObject obj) {
        return check(obj, PyList.class);
    }
    public static PyDict checkDict(PyObject obj) {
        return check(obj, PyDict.class);
    }
    public static PyTuple checkTuple(PyObject obj) {
        return check(obj, PyTuple.class);
    }

    private static <T> T typeError(PyObject obj, String expectedType) {
        String actualName = obj.getClass().getSimpleName().toLowerCase().replace("py", "");
        return new Exceptions.PyTypeError("expected '" + expectedType + "', but got '" + actualName + "'").raise();
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromPython(PyObject obj, Class<T> type) {
        if (obj == null || obj == PyNone.INSTANCE) {
            if (type == PyObject.class || type == Object.class) return null;
            return new Exceptions.PyTypeError("Cannot convert None to " + type.getSimpleName()).raise();
        }

        if (type == PyObject.class || type == Object.class) return (T) obj;

        // String
        if (type == String.class) {
            if (obj instanceof PyString s) return (T) s.value;
            return typeError(obj, "str");
        }

        // Integer / int
        if (type == Integer.class || type == int.class) {
            if (obj instanceof PyInt i) return (T) Integer.valueOf(i.value.toInt());
            if (obj instanceof PyBool b) return (T) Integer.valueOf(b.value ? 1 : 0);
            return typeError(obj, "int");
        }

        // Double / double
        if (type == Double.class || type == double.class) {
            if (obj instanceof PyFloat f) return (T) Double.valueOf(f.value.toDouble());
            if (obj instanceof PyInt i) return (T) Double.valueOf(i.value.toDouble());
            return typeError(obj, "float");
        }
        // Boolean / boolean
        if (type == Boolean.class || type == boolean.class) {
            if (obj instanceof PyBool b) return (T) Boolean.valueOf(b.value);
            if (obj instanceof PyInt i) return (T) Boolean.valueOf(i.value.toInt() != 0);
            return typeError(obj, "bool");
        }

        // List
        if (type == List.class) {
            if (obj instanceof PyList list) return (T) list.getInner();
            if (obj instanceof PyTuple tuple) {
                List<PyObject> javaList = new ArrayList<>();
                for (int i = 0; i < tuple.pyDanderLen(); i++) javaList.add(tuple.pyDanderGetItem(PyInt.from(i)));
                return (T) javaList;
            }
            return typeError(obj, "list");
        }

        // Map
        if (type == Map.class) {
            if (obj instanceof PyDict dict) return (T) dict.getInner();
            return typeError(obj, "dict");
        }

        throw new RuntimeException("PyTransform Error: Unsupported Java type '" + type.getName() + "'");
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromPythonOrNull(PyObject obj, Class<T> type) {
        if (obj == null || obj == PyNone.INSTANCE) return null;

        if (type == PyObject.class || type == Object.class) return (T) obj;

        if (type == String.class) {
            if (obj instanceof PyString s) return (T) s.value;
        } else if (type == Integer.class || type == int.class) {
            if (obj instanceof PyInt i) return (T) Integer.valueOf(i.value.toInt());
            if (obj instanceof PyBool b) return (T) Integer.valueOf(b.value ? 1 : 0);
        } else if (type == Double.class || type == double.class) {
            if (obj instanceof PyFloat f) return (T) Double.valueOf(f.value.toDouble());
            if (obj instanceof PyInt i) return (T) Double.valueOf(i.value.toDouble());
        } else if (type == Boolean.class || type == boolean.class) {
            if (obj instanceof PyBool b) return (T) Boolean.valueOf(b.value);
            if (obj instanceof PyInt i) return (T) Boolean.valueOf(i.value.toInt() != 0);
        } else if (type == List.class) {
            if (obj instanceof PyList list) return (T) list.getInner();
            if (obj instanceof PyTuple tuple) {
                List<PyObject> javaList = new ArrayList<>();
                for (int i = 0; i < tuple.pyDanderLen(); i++) javaList.add(tuple.pyDanderGetItem(PyInt.from(i)));
                return (T) javaList;
            }
        } else if (type == Map.class) {
            if (obj instanceof PyDict dict) return (T) dict.getInner();
        }

        return null;
    }

    public static PyObject toPython(Object res) {
        if (res == null) return PyNone.INSTANCE;

        return switch (res) {
            case PyObject py -> py;
            case String s -> new PyString(s);
            case Integer i -> PyInt.from(i);
            case Long l -> PyInt.from(l);
            case Boolean b -> PyBool.from(b);
            case Double d -> new PyFloat(new SmartFloat(d));
            case Float f -> new PyFloat(new SmartFloat(f.doubleValue()));

            case List<?> list -> {
                PyObject[] pyItems = new PyObject[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    pyItems[i] = toPython(list.get(i));
                }
                yield new PyList(pyItems);
            }
            case Map<?, ?> map -> {
                PyDict pyDict = new PyDict();
                for (var entry : map.entrySet()) {
                    pyDict.put(toPython(entry.getKey()), toPython(entry.getValue()));
                }
                yield pyDict;
            }

            default -> throw new RuntimeException("Cannot convert Java type '" + res.getClass().getName() + "' to Python");
        };
    }
}
