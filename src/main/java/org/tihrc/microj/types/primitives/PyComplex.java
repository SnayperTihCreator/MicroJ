package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.SmartComplex;
import org.tihrc.microj.units.SmartFloat;

public class PyComplex extends PyObject implements Protocols.PyNumber, Protocols.PyComparable {
    public final SmartComplex value;

    public PyComplex(SmartComplex value) {
        this.value = value;
    }

    private SmartComplex toSmartComplex(PyObject other) {
        if (other instanceof PyComplex c) return c.value;
        if (other instanceof PyFloat f) return new SmartComplex(f.value, new SmartFloat(0.0));
        if (other instanceof PyInt i) return new SmartComplex(new SmartFloat(i.value.toDouble()), new SmartFloat(0.0));
        return null;
    }

    @Override
    @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        SmartComplex otherVal = toSmartComplex(other);
        if (otherVal != null) return new PyComplex(this.value.add(otherVal));
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__sub__")
    public PyObject pyDanderSub(PyObject other) {
        SmartComplex otherVal = toSmartComplex(other);
        if (otherVal != null) return new PyComplex(this.value.subtract(otherVal));
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        SmartComplex otherVal = toSmartComplex(other);
        if (otherVal != null) return new PyComplex(this.value.multiply(otherVal));
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__truediv__")
    public PyObject pyDanderTrueDiv(PyObject other) {
        SmartComplex otherVal = toSmartComplex(other);
        if (otherVal != null) return new PyComplex(this.value.divide(otherVal));
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__neg__")
    public PyObject pyDanderNeg() {
        return new PyComplex(this.value.multiply(new SmartComplex(-1, 0)));
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        return this.value.getRealAsDouble() != 0.0 || this.value.getImagAsDouble() != 0.0;
    }

    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        SmartComplex otherVal = toSmartComplex(other);
        if (otherVal != null) return PyBool.from(this.value.equals(otherVal));
        return PyBool.FALSE;
    }

    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        SmartComplex otherVal = toSmartComplex(other);
        if (otherVal != null) return PyBool.from(!this.value.equals(otherVal));
        return PyBool.TRUE;
    }

    @Override public PyObject pyDanderLt(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderGt(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderLe(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderGe(PyObject other) { return PyNotImplemented.INSTANCE; }

    @Override
    public String pyDanderRepr() {
        double real = value.getRealAsDouble();
        double imag = value.getImagAsDouble();

        String realStr = (real == Math.floor(real) && !Double.isInfinite(real)) ? String.valueOf((long) real) : String.valueOf(real);
        String imagStr = (imag == Math.floor(imag) && !Double.isInfinite(imag)) ? (long) imag + ".0" : String.valueOf(imag);

        if (imag >= 0) {
            return "(%s+%sj)".formatted(realStr, imagStr);
        }
        return "(%s%sj)".formatted(realStr, imagStr);
    }

    @Override
    public String toString() {
        return "PyComplex<%s>".formatted(this.pyDanderRepr());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PyComplex other)) return false;

        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}