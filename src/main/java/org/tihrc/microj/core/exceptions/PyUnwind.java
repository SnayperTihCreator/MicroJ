package org.tihrc.microj.core.exceptions;

public class PyUnwind extends Error {
    public final PyBaseException exception;

    public PyUnwind(PyBaseException exception) {
        super(exception.toString());
        this.exception = exception;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
