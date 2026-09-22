package org.tihrc.microj.types.core;

import org.tihrc.microj.core.PyObject;

public class PyCell extends PyObject {
    public PyObject value;
    public PyCell(PyObject value) { this.value = value; }

    @Override public String toString() { return "PyCell"; }
    @Override public String pyDanderRepr() { return "<cell>"; }
}
