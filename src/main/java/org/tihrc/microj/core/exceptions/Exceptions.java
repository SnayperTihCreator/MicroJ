package org.tihrc.microj.core.exceptions;

@SuppressWarnings("unused")
public class Exceptions {
    private Exceptions() {}

    public static class PyException extends PyBaseException {
        public PyException(String message) {
            super("Exception", message);
        }
        public PyException(String type, String message) {
            super(type, message);
        }
    }

    public static class PyStopIteration extends PyException {
        public PyStopIteration() {
            super("StopIteration", "");
        }
    }

    public static class PyStopAsyncIteration extends PyException {
        public PyStopAsyncIteration() {
            super("StopAsyncIteration", "stop async iteration");
        }
    }

    public static class PyArithmeticError extends PyException {
        public PyArithmeticError(String message) {
            super("ArithmeticError", message);
        }

        public PyArithmeticError(String type, String message) {
            super(type, message);
        }
    }

    public static class PyFloatingPointError extends PyArithmeticError {
        public PyFloatingPointError(String message) {
            super("FloatingPointError", message);
        }
    }

    public static class PyOverflowError extends PyArithmeticError {
        public PyOverflowError(String message) {
            super("OverflowError", message);
        }
    }

    public static class PyZeroDivisionError extends PyArithmeticError {
        public PyZeroDivisionError(String message) {
            super("ZeroDivisionError", message);
        }
    }

    public static class PyAssertionError extends PyException {
        public PyAssertionError(String message) {
            super("AssertionError", message);
        }
    }

    public static class PyAttributeError extends PyException {
        public PyAttributeError(String message) {
            super("AttributeError", message);
        }
    }

    public static class PyBufferError extends PyException {
        public PyBufferError(String message) {
            super("BufferError", message);
        }
    }

    public static class PyEOFError extends PyException {
        public PyEOFError(String message) {
            super("EOFError", message);
        }
    }

    public static class PyImportError extends PyException {
        public PyImportError(String message) {
            super("ImportError", message);
        }

        public PyImportError(String type, String message) {
            super(type, message);
        }
    }

    public static class PyModuleNotFoundError extends PyImportError {
        public PyModuleNotFoundError(String message) {
            super("ModuleNotFoundError", message);
        }
    }

    public static class PyLookupError extends PyException {
        public PyLookupError(String message) {
            super("LookupError", message);
        }

        public PyLookupError(String type, String message) {
            super(type, message);
        }
    }

    public static class PyIndexError extends PyLookupError {
        public PyIndexError(String message) {
            super("IndexError", message);
        }
    }

    public static class PyKeyError extends PyLookupError {
        public PyKeyError(String message) {
            super("KeyError", message);
        }
    }

    public static class PyMemoryError extends PyException {
        public PyMemoryError(String message) {
            super("MemoryError", message);
        }
    }

    public static class PyNameError extends PyException {
        public PyNameError(String message) {
            super("NameError", message);
        }

        public PyNameError(String type, String message) {
            super(type, message);
        }
    }

    public static class PyUnboundLocalError extends PyNameError {
        public PyUnboundLocalError(String message) {
            super("UnboundLocalError", message);
        }
    }

    public static class PyReferenceError extends PyException {
        public PyReferenceError(String message) {
            super("ReferenceError", message);
        }
    }

    public static class PyRuntimeError extends PyException {
        public PyRuntimeError(String message) {
            super("RuntimeError", message);
        }
        public PyRuntimeError(String type, String message) {
            super(type, message);
        }
    }

    public static class PyNotImplementedError extends PyRuntimeError {
        public PyNotImplementedError(String message) {
            super("NotImplementedError", message);
        }
    }

    public static class PyRecursionError extends PyRuntimeError {
        public PyRecursionError(String message) {
            super("RecursionError", message);
        }
    }

    public static class PySyntaxError extends PyBaseException{
        public PySyntaxError(String message){
            super("SyntaxError", message);
        }
        public PySyntaxError(String type, String message){
            super(type, message);
        }
    }

    public static class PyIndentationError extends PySyntaxError {
        public PyIndentationError(String message){
            super("IndentationError", message);
        }
        public PyIndentationError(String type, String message){
            super(type, message);
        }
    }

    public static class  PyTabError extends PyIndentationError {
        public PyTabError(String message){
            super("TabError", message);
        }
    }

    public static class PySystemError extends PyException {
        public PySystemError(String message) {
            super("SystemError", message);
        }
    }

    public static class PyTypeError extends PyException {
        public PyTypeError(String message) {
            super("TypeError", message);
        }
    }

    public static class PyValueError extends PyException {
        public PyValueError(String message) {
            super("ValueError", message);
        }
    }
}
