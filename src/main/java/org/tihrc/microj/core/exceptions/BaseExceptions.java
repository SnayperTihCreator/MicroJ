package org.tihrc.microj.core.exceptions;

@SuppressWarnings("unused")
public class BaseExceptions {
    private  BaseExceptions() {}

    public static class PySystemExit extends PyBaseException {
        private final int exitCode;
        public PySystemExit(int code){
            super("SystemExit", "exit code: " + code);
            this.exitCode = code;
        }

        public int getExitCode() {
            return exitCode;
        }
    }

    public static class PyKeyboardInterrupt extends PyBaseException {
        public PyKeyboardInterrupt() {
            super("KeyboardInterrupt", "Keyboard interrupted");
        }
    }

    public static class PyGeneratorExit extends PyBaseException {
        public PyGeneratorExit() {
            super("GeneratorExit", "generator exit");
        }
    }
}
