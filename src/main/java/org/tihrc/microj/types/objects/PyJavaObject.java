package org.tihrc.microj.types.objects;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.JavaExceptions;
import org.tihrc.microj.core.transforms.PyOverloadMethod;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.core.PyContext;
import org.tihrc.microj.types.core.PyNotImplemented;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.units.Constants;
import org.tihrc.microj.units.FastMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.util.*;

public class PyJavaObject extends PyObject implements Protocols.PyComparable {
    public final Object host;
    private final Class<?> type;
    private final Map<String, List<Method>> methods;
    private final Set<String> components;

    public PyJavaObject(Object host) {
        this.host = host;
        this.type = host.getClass();
        this.methods = new FastMap<>();
        for (Method m : type.getMethods()) {          // getMethods = public включая наследование
            if (Modifier.isStatic(m.getModifiers())) continue;
            methods.computeIfAbsent(m.getName(), k -> new ArrayList<>()).add(m);
        }
        this.components = new HashSet<>();
        if (type.isRecord()) {
            for (RecordComponent rc : type.getRecordComponents())
                components.add(rc.getName());
        }
    }

    @Override
    public PyObject findAttribute(String name) {
        if (components.contains(name)) {
            List<Method> ms = methods.get(name);
            if (ms != null && !ms.isEmpty()) {
                PyOverloadMethod accessor = new PyOverloadMethod(name, host, ms);
                return accessor.pyDanderCallFast(PyContext.current(), Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
            }
        }

        List<Method> ms = methods.get(name);
        if (ms != null) return new PyOverloadMethod(name, host, ms);

        try {
            Field f = type.getField(name);
            return Transforms.toPython(f.get(host));
        } catch (NoSuchFieldException ignored) {
        } catch (IllegalAccessException e) {
            throw JavaExceptions.java2python(e);
        }
        return super.findAttribute(name);
    }

    @Override
    public void setAttribute(String name, PyObject value) {
        new Exceptions.PyAttributeError("cannot set attribute '" + name + "' on Java object").raise();
    }

    @Override public String pyDanderStr() { return String.valueOf(host); }
    @Override public String toString() { return String.valueOf(host); }

    @Override
    public String pyDanderRepr() {
        if (type.isRecord()) {
            StringBuilder sb = new StringBuilder(type.getSimpleName()).append('(');
            boolean first = true;
            for (RecordComponent rc : type.getRecordComponents()) {
                if (!first) sb.append(", ");
                first = false;
                sb.append(rc.getName()).append('=');
                try {
                    Object val = rc.getAccessor().invoke(host);
                    sb.append(Transforms.toPython(val).pyDanderRepr());
                } catch (Exception e) {
                    sb.append("?");
                }
            }
            return sb.append(')').toString();
        }
        return "<java object " + type.getSimpleName() + ">";
    }

    @Override
    public List<String> pyDanderDir() {
        var base = super.pyDanderDir();
        base.addAll(methods.keySet());
        base.addAll(components);
        return base;
    }

    @Override
    public boolean pyDanderBool() { return true; }

    @Override
    public PyObject pyDanderEq(PyObject other) {
        Object o = (other instanceof PyJavaObject jo) ? jo.host : null;
        return PyBool.from(host.equals(o));
    }

    @Override public PyObject pyDanderNe(PyObject other) {
        PyObject eq = pyDanderEq(other);
        return PyBool.from(!((PyBool) eq).boolValue);
    }

    @Override public PyObject pyDanderLt(PyObject o) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderGt(PyObject o) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderLe(PyObject o) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderGe(PyObject o) { return PyNotImplemented.INSTANCE; }

    @Override public int hashCode() { return host.hashCode(); }
    @Override public boolean equals(Object o) {
        return o instanceof PyJavaObject jo && host.equals(jo.host);
    }

}
