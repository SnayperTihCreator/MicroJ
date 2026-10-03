package org.tihrc.microj.core.transforms;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.JavaExceptions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class PyOverloadMethod extends PyObject implements Protocols.PyCallable {
    private final String name;
    private final Object receiver;
    private final List<Method> candidates;

    public PyOverloadMethod(String name, Object receiver, List<Method> candidates) {
        this.name = name;
        this.receiver = receiver;
        this.candidates = List.copyOf(candidates);
    }

    @Override
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        return pyDanderCallFast(ctx, args, null, null);
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args,
                                     String[] kwNames, PyObject[] kwValues) {
        if (kwNames != null && kwNames.length > 0)
            return new Exceptions.PyTypeError(name + "() does not take keyword arguments").raise();

        for (Method m : candidates) {
            Object[] javaArgs = ArgsMatcher.match(
                    m.getParameterTypes(), m.isVarArgs(), args);
            if (javaArgs == null) continue;
            try {
                m.setAccessible(true);
                return Transforms.toPython(m.invoke(receiver, javaArgs));
            } catch (InvocationTargetException e) {
                throw JavaExceptions.java2python(e.getCause());
            } catch (IllegalAccessException e) {
                throw JavaExceptions.java2python(e);
            }
        }
        return new Exceptions.PyTypeError("no matching overload for " + name + "(" + arityHint(args) + ")").raise();
    }

    private static String arityHint(PyObject[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(args[i].getClass().getSimpleName());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String pyDanderRepr() {
        return "<java method '" + name + "' (" + candidates.size() + " overloads)>";
    }
}