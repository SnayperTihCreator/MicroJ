package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.SmartComplex;
import org.tihrc.microj.units.SmartFloat;
import org.tihrc.microj.units.SmartInt;

public class PyFloat extends PyObject implements Protocols.PyNumber, Protocols.PyComparable {
    public final SmartFloat value;
    public static final PyFloat NaN = new PyFloat(new SmartFloat(Double.NaN));
    public static final PyFloat INF = new PyFloat(new SmartFloat(Double.POSITIVE_INFINITY));

    public PyFloat(SmartFloat value) {
        this.value = value;
    }

    @Override
    @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        if (other instanceof PyFloat f) return new PyFloat(this.value.add(f.value));
        if (other instanceof PyInt i) return new PyFloat(this.value.add(new SmartFloat(i.value.toDouble())));
        if (other instanceof PyComplex c) return c.pyDanderAdd(this); // Делегируем комплексному числу
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__sub__")
    public PyObject pyDanderSub(PyObject other) {
        if (other instanceof PyFloat f) return new PyFloat(this.value.subtract(f.value));
        if (other instanceof PyInt i) return new PyFloat(this.value.subtract(new SmartFloat(i.value.toDouble())));
        if (other instanceof PyComplex c) {
            SmartComplex newC = new SmartComplex(this.value.subtract(c.value.getReal()), new SmartFloat(0).subtract(c.value.getImag()));
            return new PyComplex(newC);
        }
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        if (other instanceof PyFloat f) return new PyFloat(this.value.multiply(f.value));
        if (other instanceof PyInt i) return new PyFloat(this.value.multiply(new SmartFloat(i.value.toDouble())));
        if (other instanceof PyComplex c) return c.pyDanderMul(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__truediv__")
    public PyObject pyDanderTrueDiv(PyObject other) {
        if (other instanceof PyFloat f) {
            if (f.value.compareTo(new SmartFloat(0.0)) == 0)
                return new Exceptions.PyZeroDivisionError("float division by zero").raise();
            return new PyFloat(this.value.divide(f.value));
        }
        if (other instanceof PyInt i) {
            if (i.value.compareTo(SmartInt.ZERO) == 0)
                return new Exceptions.PyZeroDivisionError("float division by zero").raise();
            return new PyFloat(this.value.divide(new SmartFloat(i.value.toDouble())));
        }
        if (other instanceof PyComplex c) {
            SmartFloat denom = c.value.getReal().multiply(c.value.getReal()).add(c.value.getImag().multiply(c.value.getImag()));
            if (denom.toDouble() == 0.0)
                return new Exceptions.PyZeroDivisionError("complex division by zero").raise();
            SmartFloat newReal = this.value.multiply(c.value.getReal()).divide(denom);
            SmartFloat newImag = new SmartFloat(0).subtract(this.value.multiply(c.value.getImag()).divide(denom));
            return new PyComplex(new SmartComplex(newReal, newImag));
        }
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__neg__")
    public PyObject pyDanderNeg() {
        return new PyFloat(this.value.multiply(new SmartFloat(-1)));
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        return this.value.compareTo(new SmartFloat(0.0)) != 0;
    }

    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        if (other instanceof PyFloat f) return PyBool.from(this.value.compareTo(f.value) == 0);
        if (other instanceof PyInt i) return PyBool.from(this.value.compareTo(new SmartFloat(i.value.toDouble())) == 0);
        return PyBool.FALSE;
    }
    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        if (other instanceof PyFloat f) return PyBool.from(this.value.compareTo(f.value) != 0);
        if (other instanceof PyInt i) return PyBool.from(this.value.compareTo(new SmartFloat(i.value.toDouble())) != 0);
        return PyBool.TRUE;
    }
    @Override
    @PyExport(name = "__lt__")
    public PyObject pyDanderLt(PyObject other) {
        if (other instanceof PyFloat f) return PyBool.from(this.value.compareTo(f.value) < 0);
        if (other instanceof PyInt i) return PyBool.from(this.value.compareTo(new SmartFloat(i.value.toDouble())) < 0);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__le__")
    public PyObject pyDanderLe(PyObject other) {
        if (other instanceof PyFloat f) return PyBool.from(this.value.compareTo(f.value) <= 0);
        if (other instanceof PyInt i) return PyBool.from(this.value.compareTo(new SmartFloat(i.value.toDouble())) <= 0);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__gt__")
    public PyObject pyDanderGt(PyObject other) {
        if (other instanceof PyFloat f) return PyBool.from(this.value.compareTo(f.value) > 0);
        if (other instanceof PyInt i) return PyBool.from(this.value.compareTo(new SmartFloat(i.value.toDouble())) > 0);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__ge__")
    public PyObject pyDanderGe(PyObject other) {
        if (other instanceof PyFloat f) return PyBool.from(this.value.compareTo(f.value) >= 0);
        if (other instanceof PyInt i) return PyBool.from(this.value.compareTo(new SmartFloat(i.value.toDouble())) >= 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    public String pyDanderRepr() {
        double d = value.toDouble();
        if (Double.isInfinite(d)) return d > 0 ? "inf" : "-inf";
        if (Double.isNaN(d)) return "nan";
        if (d == Math.floor(d) && !Double.isInfinite(d)) return (long) d + ".0";
        return String.valueOf(d);
    }

    @Override
    public String toString() {
        return "PyFloat(%s)".formatted(pyDanderRepr());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PyFloat other)) return false;
        return Double.doubleToLongBits(value.toDouble())
                == Double.doubleToLongBits(other.value.toDouble());
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value.toDouble());
    }
}