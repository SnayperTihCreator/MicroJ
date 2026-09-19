package org.tihrc.microj.backend.jvm;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.PyUnwind;

import java.lang.invoke.MethodHandle;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class JitFunction extends PyObject implements Protocols.PyCallable {

    public final String name;
    public final MethodHandle handle;
    public final PyObject[] closure;
    public final PyObject defaults;

    public static final PyObject[] EMPTY_ARGS = new PyObject[0];
    public static final String[] EMPTY_KW_NAMES = new String[0];
    public static final PyObject[] EMPTY_KW_VALS = new PyObject[0];

    public JitFunction(String name, MethodHandle handle, PyObject[] closure, PyObject defaults) {
        this.name = name;
        this.handle = handle;
        this.closure = closure;
        this.defaults = defaults;
    }

    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        try {
            return (PyObject) handle.invokeExact(ctx, closure, defaults, args, kwNames, kwValues);
        } catch (PyUnwind pyUnwind) {
            throw pyUnwind;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Override
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        String[] kwNames = kwargs.isEmpty() ? EMPTY_KW_NAMES : kwargs.keySet().toArray(new String[0]);
        PyObject[] kwValues = kwargs.isEmpty() ? EMPTY_KW_VALS : kwargs.values().toArray(new PyObject[0]);
        return pyDanderCallFast(ctx, args, kwNames, kwValues);
    }

    @Override
    public void pyDanderCall(RuntimeExecuter ctx, Consumer<PyObject> callback, PyObject[] args, Map<String, PyObject> kwargs) {
        Protocols.PyCallable.super.pyDanderCall(ctx, callback, args, kwargs);
    }

    @Override
    public PyObject pyDanderCallBound(RuntimeExecuter ctx, PyObject self, Map<String, PyObject> kwargs, PyObject[] args) {
        return Protocols.PyCallable.super.pyDanderCallBound(ctx, self, kwargs, args);
    }

    @Override
    public PyObject pyDanderCallBoundFast(RuntimeExecuter ctx, PyObject self, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        return Protocols.PyCallable.super.pyDanderCallBoundFast(ctx, self, args, kwNames, kwValues);
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx) {
        return Protocols.PyCallable.super.pyDanderCallFast(ctx);
    }

    @Override
    public String toString() {
        return "JITFunction<" + name + ">";
    }

    @Override
    public String pyDanderRepr() {
        return "<jit function " + name + ">";
    }
}