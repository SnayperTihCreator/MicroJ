package org.tihrc.microj.core.exceptions;

@SuppressWarnings("unused")
public final class OSExceptions {
    private OSExceptions() {}

    public static class PyOSError extends Exceptions.PyException {
        public PyOSError(String message) {
            super("OSError", message);
        }
        public PyOSError(String type, String message) {
            super(type, message);
        }
    }

    public static class PyBlockingIOError extends PyOSError {
        public PyBlockingIOError(String message) {
            super("BlockingIOError", message);
        }
    }

    public static class PyChildProcessError extends PyOSError {
        public PyChildProcessError(String message) {
            super("ChildProcessError", message);
        }
    }

    public static class PyConnectionError extends PyOSError {
        public PyConnectionError(String message) {
            super("ConnectionError", message);
        }
        public PyConnectionError(String type, String message) {
            super(type, message);
        }
    }

    public static class PyBrokenPipeError extends PyConnectionError {
        public PyBrokenPipeError(String message) {
            super("BrokenPipeError", message);
        }
    }

    public static class PyConnectionAbortedError extends PyConnectionError {
        public PyConnectionAbortedError(String message) {
            super("ConnectionAbortedError", message);
        }
    }

    public static class PyConnectionRefusedError extends PyConnectionError {
        public PyConnectionRefusedError(String message) {
            super("ConnectionRefusedError", message);
        }
    }

    public static class PyConnectionResetError  extends PyConnectionError {
        public PyConnectionResetError(String message) {
            super("ConnectionResetError", message);
        }
    }

    public static class PyFileExistsError extends PyOSError {
        public PyFileExistsError(String message) {
            super("FileExistsError", message);
        }
    }

    public static class PyFileNotFoundError extends PyOSError {
        public PyFileNotFoundError(String message) {
            super("FileNotFoundError", message);
        }
    }

    public static class PyInterruptedError extends PyOSError {
        public PyInterruptedError(String message) {
            super("InterruptedError", message);
        }
    }

    public static class PyIsADirectoryError extends PyOSError {
        public PyIsADirectoryError(String message) {
            super("IsADirectoryError", message);
        }
    }

    public static class PyNotADirectoryError extends PyOSError {
        public PyNotADirectoryError(String message) {
            super("NotADirectoryError", message);
        }
    }

    public static class PyPermissionError extends PyOSError {
        public PyPermissionError(String message) {
            super("PermissionError", message);
        }
    }

    public static class PyProcessLookupError extends PyOSError {
        public PyProcessLookupError(String message) {
            super("ProcessLookupError", message);
        }
    }

    public static class PyTimeoutError extends PyOSError {
        public PyTimeoutError(String message) {
            super("TimeoutError", message);
        }
    }
}
