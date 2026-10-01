package org.tihrc.microj.core.exceptions;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.primitives.PyString;

public class PyBaseException extends PyObject {
    private final String errorType;
    private final String message;

    public PyBaseException(String errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    @Override
    public PyObject findAttribute(String name) {
        return switch (name) {
            case "args"      -> new PyTuple(new PyObject[]{ new PyString(message) });
            case "message"   -> new PyString(message);
            case "__class__" -> ExceptionsRegistry.get(errorType);
            default          -> null;
        };
    }

    public String getErrorType() { return errorType; }
    public String getMessage() { return message; }

    public <T> T raise() {
        throw new PyUnwind(this);
    }

    @Override
    public String toString() {
        return errorType + ": " + message;
    }
    @Override
    public String pyDanderStr() { return errorType + ": " + message; }
}
