package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;

public class PyBool extends PyObject implements Protocols.PyComparable {
    public static final PyBool TRUE = new PyBool(true);
    public static final PyBool FALSE = new PyBool(false);

    public final boolean value;

    private PyBool(boolean value) {
        this.value = value;
    }

    public static PyBool from(boolean val) {
        return val ? TRUE : FALSE;
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        return value;
    }
    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        if (other instanceof PyBool o) return PyBool.from(this.value == o.value);
        if (other instanceof PyInt i) return PyBool.from((this.value ? 1 : 0) == i.value.toInt());
        return PyBool.FALSE;
    }
    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        if (other instanceof PyBool o) return PyBool.from(this.value != o.value);
        if (other instanceof PyInt i) return PyBool.from((this.value ? 1 : 0) != i.value.toInt());
        return PyBool.TRUE;
    }
    @Override
    @PyExport(name = "__lt__")
    public PyObject pyDanderLt(PyObject other) {
        if (other instanceof PyBool o) return PyBool.from(Boolean.compare(this.value, o.value) < 0);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__gt__")
    public PyObject pyDanderGt(PyObject other) {
        if (other instanceof PyBool o) return PyBool.from(Boolean.compare(this.value, o.value) > 0);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__le__")
    public PyObject pyDanderLe(PyObject other) {
        if (other instanceof PyBool o) return PyBool.from(Boolean.compare(this.value, o.value) <= 0);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__ge__")
    public PyObject pyDanderGe(PyObject other) {
        if (other instanceof PyBool o) return PyBool.from(Boolean.compare(this.value, o.value) >= 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    public String pyDanderRepr() {
        return value ? "True" : "False";
    }

    @Override
    public String toString() {
        return "PyBool(%s)".formatted(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PyBool o) return this.value == o.value;
        return false;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.value);
    }


}
