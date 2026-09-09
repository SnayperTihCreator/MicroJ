package org.tihrc.microj.types;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.PyExport;

import java.util.Map;

public class PyBuiltinFunction extends PyObject implements Protocols.PyCallable {
    private final Protocols.PyCallable func;

    public PyBuiltinFunction(Protocols.PyCallable func) {
        this.func = func;
    }

    @Override
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        return func.pyDanderCall(ctx, kwargs, args);
    }

    @Override
    public String pyDanderRepr() {
        return "<built-in function>";
    }

    @Override
    public String toString() {
        return "PyBuiltinFunction";
    }
}
