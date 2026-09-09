package org.tihrc.microj.core.exceptions;

import org.tihrc.microj.core.PyObject;

public class PyBaseException extends PyObject {
    private final String errorType;
    private final String message;
    private RaisedContext context;

    public PyBaseException(String errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public void setContext(RaisedContext context) { this.context = context; }
    public RaisedContext getContext() { return context; }
    public String getErrorType() { return errorType; }
    public String getMessage() { return message; }

    public <T> T raise() {
        throw new PyUnwind(this);
    }

    @Override
    public String toString() {
        return errorType + ": " + message;
    }
}
