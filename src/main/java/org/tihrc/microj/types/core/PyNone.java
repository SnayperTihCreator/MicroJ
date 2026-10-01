package org.tihrc.microj.types.core;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.types.primitives.PyBool;

public class PyNone extends PyObject implements Protocols.PyComparable {
    public static final PyNone INSTANCE = new PyNone();

    private PyNone() {}

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        return false;
    }

    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        return PyBool.from(other instanceof PyNone);
    }

    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        return PyBool.from(!(other instanceof PyNone));
    }

    @Override
    @PyExport(name = "__lt__")
    public PyObject pyDanderLt(PyObject other) {
        if (other instanceof PyNone) return PyBool.FALSE;
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__gt__")
    public PyObject pyDanderGt(PyObject other) {
        if (other instanceof PyNone) return PyBool.FALSE;
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__le__")
    public PyObject pyDanderLe(PyObject other) {
        if (other instanceof PyNone) return PyBool.TRUE;
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__ge__")
    public PyObject pyDanderGe(PyObject other) {
        if (other instanceof PyNone) return PyBool.TRUE;
        return PyNotImplemented.INSTANCE;
    }

    @Override
    public String pyDanderRepr() {
        return "None";
    }

    @Override
    public String toString() {
        return "PyNone";
    }
}
