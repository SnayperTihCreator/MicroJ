package org.tihrc.microj.core.exceptions;

import org.tihrc.microj.core.PyObject;

public class PyUnwind extends Error {
    public final PyObject payload;
    public RaisedContext ctx;

    private static final boolean CAPTURE_STACK = Boolean.getBoolean("microj.debug.unwindStack");

    public PyUnwind(PyObject payload) {
        super((String) null);
        this.payload = payload;
    }

    @Override
    public String getMessage() {
        return ExceptionsRegistry.formatError(payload);
    }

    @Override
    public Throwable fillInStackTrace() {
        return CAPTURE_STACK ? super.fillInStackTrace() : this;
    }
}
