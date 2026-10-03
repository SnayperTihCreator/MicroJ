package org.tihrc.microj.types.objects;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.JavaExceptions;
import org.tihrc.microj.core.transforms.ArgsMatcher;
import org.tihrc.microj.core.transforms.JavaTypes;
import org.tihrc.microj.core.transforms.PyOverloadMethod;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.units.FastMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PyJavaClass extends PyClass {
    public final Class<?> jvmType;
    private final List<Constructor<?>> constructors;
    private final Map<String, PyObject> staticMembers = new FastMap<>();
    

    public PyJavaClass(Class<?> type) {
        super(type.getSimpleName(), new FastMap<>(), new ArrayList<>());
        this.jvmType = type;

        List<Constructor<?>> ctors = new ArrayList<>();
        for (Constructor<?> c : type.getConstructors())
            if (Modifier.isPublic(c.getModifiers())) ctors.add(c);
        this.constructors = ctors;

        Map<String, List<Method>> byName = new FastMap<>();
        for (Method m : type.getMethods()) {
            if (!Modifier.isStatic(m.getModifiers())) continue;
            byName.computeIfAbsent(m.getName(), k -> new ArrayList<>()).add(m);
        }

        for (var e : byName.entrySet())
            staticMembers.put(e.getKey(), new PyOverloadMethod(e.getKey(), null, e.getValue()));

        for (var f : type.getFields()) {
            if (!Modifier.isStatic(f.getModifiers())) continue;
            try { staticMembers.put(f.getName(), Transforms.toPython(f.get(null))); }
            catch (IllegalAccessException ignored) {}
        }
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        for (Constructor<?> c : constructors) {
            Object[] javaArgs = matchConstructor(c, args);
            if (javaArgs == null) continue;
            try {
                return JavaTypes.wrap(c.newInstance(javaArgs));
            } catch (Exception e) {
                throw JavaExceptions.java2python(e instanceof InvocationTargetException ite ? ite.getCause() : e);
            }
        }
        return new Exceptions.PyTypeError("no matching constructor for " + jvmType.getSimpleName()).raise();
    }

    private Object[] matchConstructor(Constructor<?> c, PyObject[] args) {
        return ArgsMatcher.match(c.getParameterTypes(), c.isVarArgs(), args);
    }

    @Override
    public PyObject findAttribute(String name) {
        PyObject m = staticMembers.get(name);
        if (m != null) return m;
        return super.findAttribute(name);
    }

    @Override
    public List<String> pyDanderDir() {
        var base = super.pyDanderDir();
        base.addAll(staticMembers.keySet());
        return base;
    }

    @Override
    public String pyDanderRepr() {
        return "<java class '" + jvmType.getName() + "'>";
    }
}
