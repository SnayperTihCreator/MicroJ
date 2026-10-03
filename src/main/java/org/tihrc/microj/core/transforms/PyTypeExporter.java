package org.tihrc.microj.core.transforms;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.units.FastMap;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;


public final class PyTypeExporter extends ClassValue<FastMap<PyObject>> {
    public static final PyTypeExporter INSTANCE = new PyTypeExporter();
    public interface PyFunc0 {
        Object call(Object self);
    }
    public interface PyFunc1 {
        Object call(Object self, Object arg1);
    }
    public interface PyFunc2 {
        Object call(Object self, Object arg1, Object arg2);
    }

    @Override
    protected FastMap<PyObject> computeValue(Class<?> type) {
        FastMap<PyObject> methods = new FastMap<>();
        for (Method method : type.getMethods()) {
            if (method.getDeclaringClass() == PyObject.class) continue;
            PyExport exp = findAnnotation(method);
            if (exp != null) {
                String name = exp.name().isEmpty() ? method.getName() : exp.name();
                methods.put(name, createProxy(method, name, exp));
            }
        }
        return methods;
    }

    private PyExport findAnnotation(Method m) {
        PyExport exp = m.getAnnotation(PyExport.class);
        if (exp != null) return exp;

        Class<?> sup = m.getDeclaringClass().getSuperclass();
        while (sup != null && sup != PyObject.class) {
            try {
                Method supMethod = sup.getDeclaredMethod(m.getName(), m.getParameterTypes());
                exp = supMethod.getAnnotation(PyExport.class);
                if (exp != null) return exp;
                sup = sup.getSuperclass();
            } catch (NoSuchMethodException e) {
                break;
            }
        }
        return null;
    }

    public static PyObject findExported(PyObject obj, String name) {
        return INSTANCE.get(obj.getClass()).get(name);
    }

    private PyObject createProxy(Method method, String name, PyExport exp) {
        try {
            method.setAccessible(true);
            MethodHandle handle = MethodHandles.lookup().unreflect(method);
            int paramCount = method.getParameterTypes().length;

            boolean useArgs = exp.args();
            boolean useKwargs = exp.kwargs();

            Object fastProxy = (!useArgs && !useKwargs) ? switch (paramCount) {
                case 0 -> MethodHandleProxies.asInterfaceInstance(PyFunc0.class, handle);
                case 1 -> MethodHandleProxies.asInterfaceInstance(PyFunc1.class, handle);
                case 2 -> MethodHandleProxies.asInterfaceInstance(PyFunc2.class, handle);
                default -> null;
            } : null;

            return new PyMethodProxy(handle, fastProxy, paramCount, method.getParameterTypes(), name, useArgs, useKwargs);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access method: " + method.getName(), e);
        }
    }
}