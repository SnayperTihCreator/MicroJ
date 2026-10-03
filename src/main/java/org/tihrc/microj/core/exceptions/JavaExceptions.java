package org.tihrc.microj.core.exceptions;

import org.tihrc.microj.core.PyObject;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

public final class JavaExceptions {
    private JavaExceptions() {}

    /**
     * Java Throwable -> готовый PyUnwind (можно throw).
     * Уже-Python-исключения проносятся нетронутыми — payload любого вида
     * (PyBaseException ИЛИ PyInstance), кастов нет.
     */
    public static PyUnwind java2python(Throwable t) {
        if (t instanceof PyUnwind u) return u;
        return new PyUnwind(mapToPayload(t));
    }

    private static PyObject mapToPayload(Throwable t) {
        Throwable cause = t;
        while (cause.getCause() != null && cause.getCause() != cause) cause = cause.getCause();

        String msg = cause.getMessage() == null ? cause.toString() : cause.getMessage();

        return switch (cause) {
            case NoSuchFileException ignored -> new OSExceptions.PyFileNotFoundError(msg);
            case AccessDeniedException ignored -> new OSExceptions.PyPermissionError(msg);
            case ArithmeticException ignored -> new Exceptions.PyZeroDivisionError(msg);
            case IndexOutOfBoundsException ignored -> new Exceptions.PyIndexError(msg);
            case ClassCastException ignored -> new Exceptions.PyTypeError(msg);
            case NumberFormatException ignored -> new Exceptions.PyValueError(msg);
            case IllegalArgumentException ignored -> new Exceptions.PyValueError(msg);
            case NullPointerException ignored -> new Exceptions.PyAttributeError("null reference: " + msg);
            case IllegalStateException ignored -> new Exceptions.PyRuntimeError(msg);
            case UnsupportedOperationException ignored -> new Exceptions.PyNotImplementedError(msg);
            case IOException ignored -> new OSExceptions.PyOSError(msg);
            case StackOverflowError ignored -> new Exceptions.PyRecursionError("java stack overflow");
            default -> new Exceptions.PyRuntimeError(cause.getClass().getSimpleName() + ": " + msg);
        };
    }

    public static RuntimeException python2java(PyUnwind unwind) {
        return new RuntimeException(
                ExceptionsRegistry.formatError(unwind.payload), unwind);
    }
}