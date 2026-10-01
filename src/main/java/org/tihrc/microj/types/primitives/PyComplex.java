package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.types.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.SmartComplex;

public class PyComplex extends PyObject implements Protocols.PyNumber, Protocols.PyComparable {
    public final SmartComplex value;

    public PyComplex(SmartComplex value) { this.value = value; }

    private SmartComplex toSmartComplex(PyObject other) {
        if (other instanceof PyComplex c) return c.value;
        if (other instanceof PyFloat f)  return SmartComplex.ofReal(f.value);
        if (other instanceof PyInt i)    return SmartComplex.ofReal(i.value);
        return null;
    }

    // ---------- this OP other ----------

    @Override @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? new PyComplex(value.add(o)) : PyNotImplemented.INSTANCE;
    }

    @Override @PyExport(name = "__sub__")
    public PyObject pyDanderSub(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? new PyComplex(value.sub(o)) : PyNotImplemented.INSTANCE;
    }

    @Override @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? new PyComplex(value.mul(o)) : PyNotImplemented.INSTANCE;
    }

    @Override @PyExport(name = "__truediv__")
    public PyObject pyDanderTrueDiv(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        if (o == null) return PyNotImplemented.INSTANCE;
        if (o.isZero())
            return new Exceptions.PyZeroDivisionError("complex division by zero").raise();
        return new PyComplex(value.div(o));
    }

    @PyExport(name = "__radd__")
    public PyObject pyDanderRAdd(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? new PyComplex(o.add(value)) : PyNotImplemented.INSTANCE;
    }

    @PyExport(name = "__rsub__")
    public PyObject pyDanderRSub(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? new PyComplex(o.sub(value)) : PyNotImplemented.INSTANCE;
    }

    @PyExport(name = "__rmul__")
    public PyObject pyDanderRMul(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? new PyComplex(o.mul(value)) : PyNotImplemented.INSTANCE;
    }

    @PyExport(name = "__rtruediv__")
    public PyObject pyDanderRTruediv(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        if (o == null) return PyNotImplemented.INSTANCE;
        if (value.isZero())
            return new Exceptions.PyZeroDivisionError("complex division by zero").raise();
        return new PyComplex(o.div(value));
    }

    @Override @PyExport(name = "__neg__")
    public PyObject pyDanderNeg() {
        return new PyComplex(value.negate());
    }

    @Override @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        return !value.isZero();
    }

    @Override @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? PyBool.from(value.equals(o)) : PyNotImplemented.INSTANCE;
    }

    @Override @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        SmartComplex o = toSmartComplex(other);
        return o != null ? PyBool.from(!value.equals(o)) : PyNotImplemented.INSTANCE;
    }

    @Override public PyObject pyDanderFloorDiv(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderMod(PyObject other)      { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderLt(PyObject other)       { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderGt(PyObject other)       { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderLe(PyObject other)       { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderGe(PyObject other)       { return PyNotImplemented.INSTANCE; }

    @Override
    public String pyDanderRepr() {
        double real = value.getRealAsDouble();
        double imag = value.getImagAsDouble();
        String realStr = (real == Math.floor(real) && !Double.isInfinite(real)) ? String.valueOf((long) real) : String.valueOf(real);
        String imagStr = (imag == Math.floor(imag) && !Double.isInfinite(imag)) ? (long) imag + ".0" : String.valueOf(imag);
        return imag >= 0 ? "(%s+%sj)".formatted(realStr, imagStr) : "(%s%sj)".formatted(realStr, imagStr);
    }

    @Override
    public String toString() {
        return "PyComplex<%s>".formatted(pyDanderRepr());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PyComplex o && this.value.equals(o.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}