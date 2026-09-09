package org.tihrc.microj.core;

public class PyNotImplemented extends PyObject {
    public static final PyNotImplemented INSTANCE = new PyNotImplemented();
    private PyNotImplemented() {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String pyDanderRepr() {
        return "NotImplemented";
    }
}
