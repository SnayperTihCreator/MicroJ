package org.tihrc.microj.types;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;

import java.util.Map;

public class PyBoundMethod extends PyObject implements Protocols.PyCallable {
    private final PyObject self;
    private final PyObject func;

    public PyBoundMethod(PyObject self, PyObject func) {
        this.self = self;
        this.func = func;
    }

    @Override
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        return callBound(ctx, kwargs, args);
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        PyObject[] fullArgs = new PyObject[args.length + 1];
        fullArgs[0] = this.self;
        System.arraycopy(args, 0, fullArgs, 1, args.length);
        return ((Protocols.PyCallable) func).pyDanderCallFast(ctx, fullArgs, kwNames, kwValues);
    }

    public PyObject callBound(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject[] args) {
        Protocols.PyCallable callable = (Protocols.PyCallable) func;
        return callable.pyDanderCallBound(ctx, self, kwargs, args);
    }

    public PyObject callBoundFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        Protocols.PyCallable callable = (Protocols.PyCallable) func;
        return callable.pyDanderCallBoundFast(ctx, self, args, kwNames, kwValues);
    }

    @Override
    public String toString() {
        return "PyBoundMethod<%s, %s>".formatted(self, func);
    }

    @Override
    public String pyDanderRepr() {
        return "<bound method %s of %s>".formatted(func, self);
    }

    public PyObject getSelf() {
        return self;
    }

    public PyObject getFunc() {
        return func;
    }
}
