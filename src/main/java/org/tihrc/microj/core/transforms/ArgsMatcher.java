package org.tihrc.microj.core.transforms;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.objects.PyJavaObject;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyFloat;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.units.SmartInt;

import java.util.Map;

public final class ArgsMatcher {
    public static final Object NULL_SENTINEL = new Object();
    public static final Object NO_MATCH = new Object();

    private static final SmartInt LONG_MAX = new SmartInt(Long.MAX_VALUE);
    private static final SmartInt LONG_MIN = new SmartInt(Long.MIN_VALUE);

    private static final Map<Class<?>, Class<?>> PRIMITIVE_BOX = Map.of(
            int.class, Integer.class, long.class, Long.class,
            double.class, Double.class, float.class, Float.class,
            boolean.class, Boolean.class,
            byte.class, Byte.class, short.class, Short.class,
            char.class, Character.class);

    private ArgsMatcher() {}

    public static Object[] match(Class<?>[] params, boolean varargs, PyObject[] pyArgs) {
        if (!varargs) {
            if (params.length != pyArgs.length) return null;
            Object[] out = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                Object v = convertOne(pyArgs[i], params[i]);
                if (v == NO_MATCH) return null;
                out[i] = (v == NULL_SENTINEL) ? null : v;
            }
            return out;
        }

        int fixed = params.length - 1;
        if (pyArgs.length < fixed) return null;

        Object[] out = new Object[params.length];
        for (int i = 0; i < fixed; i++) {
            Object v = convertOne(pyArgs[i], params[i]);
            if (v == NO_MATCH) return null;
            out[i] = (v == NULL_SENTINEL) ? null : v;
        }

        Class<?> component = params[fixed].getComponentType();
        Object varArray = java.lang.reflect.Array.newInstance(component, pyArgs.length - fixed);
        for (int i = fixed; i < pyArgs.length; i++) {
            Object v = convertOne(pyArgs[i], component);
            if (v == NO_MATCH) return null;
            if (v == NULL_SENTINEL) {
                if (component.isPrimitive()) return null;
                v = null;
            }
            java.lang.reflect.Array.set(varArray, i - fixed, v);
        }
        out[fixed] = varArray;
        return out;
    }

    public static Object convertOne(PyObject arg, Class<?> paramType) {
        if (arg == null || arg == PyNone.INSTANCE) {
            return paramType.isPrimitive() ? NO_MATCH : NULL_SENTINEL;
        }

        Class<?> t = PRIMITIVE_BOX.getOrDefault(paramType, paramType);

        if (t == Integer.class) {
            Integer i = argAsInt(arg);
            return i == null ? NO_MATCH : i;
        }
        if (t == Long.class) {
            Long l = argAsLong(arg);
            return l == null ? NO_MATCH : l;
        }
        if (t == Double.class) {
            Double d = argAsDouble(arg);
            return d == null ? NO_MATCH : d;
        }
        if (t == Float.class) {
            Double d = argAsDouble(arg);
            return d == null ? NO_MATCH : d.floatValue();
        }
        if (t == Boolean.class) {
            if (arg instanceof PyBool b) return b.boolValue;
            if (arg instanceof PyInt i)  return i.value.toInt() != 0;
            return NO_MATCH;
        }
        if (t == String.class) return arg instanceof PyString s ? s.value : NO_MATCH;
        if (t == Character.class)
            return arg instanceof PyString s && s.value.length() == 1 ? s.value.charAt(0) : NO_MATCH;
        if (t == Byte.class || t == Short.class) {
            Integer i = argAsInt(arg);
            if (i == null) return NO_MATCH;
            if (t == Byte.class && (i < Byte.MIN_VALUE || i > Byte.MAX_VALUE)) return NO_MATCH;
            if (t == Short.class && (i < Short.MIN_VALUE || i > Short.MAX_VALUE)) return NO_MATCH;
            return t == Byte.class ? (Object) i.byteValue() : (Object) i.shortValue();
        }
        if (arg instanceof PyJavaObject jo) return t.isInstance(jo.host) ? jo.host : NO_MATCH;
        return NO_MATCH;
    }

    private static Integer argAsInt(PyObject arg) {
        if (arg instanceof PyBool b) return b.boolValue ? 1 : 0;
        if (arg instanceof PyInt i)  return i.value.hasIntStorage() ? i.value.toInt() : null;
        return null;
    }
    private static Long argAsLong(PyObject arg) {
        if (arg instanceof PyBool b) return b.boolValue ? 1L : 0L;
        if (arg instanceof PyInt i) {
            if (i.value.compareTo(LONG_MAX) > 0 || i.value.compareTo(LONG_MIN) < 0) return null;
            return i.value.toLong();
        }
        return null;
    }
    private static Double argAsDouble(PyObject arg) {
        if (arg instanceof PyFloat f) return f.value.toDouble();
        if (arg instanceof PyInt i)   return i.value.toDouble();
        return null;
    }
}