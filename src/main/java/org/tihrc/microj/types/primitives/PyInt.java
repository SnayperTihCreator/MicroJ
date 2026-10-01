package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.types.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.FormatSpecs;
import org.tihrc.microj.units.SmartComplex;
import org.tihrc.microj.units.SmartFloat;
import org.tihrc.microj.units.SmartInt;

public class PyInt extends PyObject implements Protocols.PyNumber, Protocols.PyComparable {
    public final SmartInt value;

    private static final int CACHE_MIN = -256;
    private static final int CACHE_MAX = 100000;
    private static final PyInt[] CACHE = new PyInt[CACHE_MAX - CACHE_MIN + 1];
    static {
        for (int i = CACHE_MIN; i <= CACHE_MAX; i++)
            CACHE[i - CACHE_MIN] = new PyInt(new SmartInt(i));
    }

    public PyInt(SmartInt value) { this.value = value; }

    public static PyInt from(int value) {
        if (value >= CACHE_MIN && value <= CACHE_MAX) return CACHE[value - CACHE_MIN];
        return new PyInt(new SmartInt(value));
    }

    public static PyInt from(long value) {
        if (value >= CACHE_MIN && value <= CACHE_MAX) return CACHE[(int) value - CACHE_MIN];
        return new PyInt(new SmartInt(value));
    }

    public static PyInt from(SmartInt value) {
        if (value.isInt()) {
            int i = value.toInt();
            if (i >= CACHE_MIN && i <= CACHE_MAX) return CACHE[i - CACHE_MIN];
        }
        return new PyInt(value);
    }

    public static PyObject asInt(PyObject other) {
        if (other instanceof PyBool b) return PyInt.from(b.boolValue ? 1 : 0);
        return other;
    }

    @Override
    @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)    return PyInt.from(value.add(i.value));
        if (other instanceof PyFloat f)  return new PyFloat(value.add(f.value));
        if (other instanceof PyComplex c) return c.pyDanderRAdd(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__sub__")
    public PyObject pyDanderSub(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)    return PyInt.from(value.sub(i.value));
        if (other instanceof PyFloat f)  return new PyFloat(value.sub(f.value));
        if (other instanceof PyComplex c) return c.pyDanderRSub(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)    return PyInt.from(value.mul(i.value));
        if (other instanceof PyFloat f)  return new PyFloat(value.mul(f.value));
        if (other instanceof PyComplex c) return c.pyDanderRMul(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__truediv__")
    public PyObject pyDanderTrueDiv(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i) {
            if (i.value.isZero())
                return new Exceptions.PyZeroDivisionError("division by zero").raise();
            return new PyFloat(value.div(i.value));
        }
        if (other instanceof PyFloat f) {
            if (f.value.isZero())
                return new Exceptions.PyZeroDivisionError("float division by zero").raise();
            return new PyFloat(value.div(f.value));
        }
        if (other instanceof PyComplex c) return c.pyDanderRTruediv(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__floordiv__")
    public PyObject pyDanderFloorDiv(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i) {
            if (i.value.isZero())
                return new Exceptions.PyZeroDivisionError("integer division or modulo by zero").raise();
            return PyInt.from(value.floorDiv(i.value));
        }
        if (other instanceof PyFloat f) {
            if (f.value.isZero())
                return new Exceptions.PyZeroDivisionError("float floor division by zero").raise();
            return new PyFloat(value.floorDiv(f.value));
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__mod__")
    public PyObject pyDanderMod(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i) {
            if (i.value.isZero())
                return new Exceptions.PyZeroDivisionError("integer division or modulo by zero").raise();
            return PyInt.from(value.mod(i.value));
        }
        if (other instanceof PyFloat f) {
            if (f.value.isZero())
                return new Exceptions.PyZeroDivisionError("float modulo").raise();
            return new PyFloat(value.mod(f.value));
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__neg__")
    public PyObject pyDanderNeg() {
        return PyInt.from(value.negate());
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        return !value.isZero();
    }

    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)   return PyBool.from(value.compareTo(i.value) == 0);
        if (other instanceof PyFloat f) return PyBool.from(value.compareTo(f.value) == 0);  // точно, без toDouble
        return PyNotImplemented.INSTANCE;   // было PyBool.FALSE — блокировало reflected __eq__ у других типов
    }

    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)   return PyBool.from(value.compareTo(i.value) != 0);
        if (other instanceof PyFloat f) return PyBool.from(value.compareTo(f.value) != 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__gt__")
    public PyObject pyDanderGt(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)   return PyBool.from(value.compareTo(i.value) > 0);
        if (other instanceof PyFloat f) return PyBool.from(value.compareTo(f.value) > 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__lt__")
    public PyObject pyDanderLt(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)   return PyBool.from(value.compareTo(i.value) < 0);
        if (other instanceof PyFloat f) return PyBool.from(value.compareTo(f.value) < 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__ge__")
    public PyObject pyDanderGe(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)   return PyBool.from(value.compareTo(i.value) >= 0);
        if (other instanceof PyFloat f) return PyBool.from(value.compareTo(f.value) >= 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__le__")
    public PyObject pyDanderLe(PyObject other) {
        other = asInt(other);
        if (other instanceof PyInt i)   return PyBool.from(value.compareTo(i.value) <= 0);
        if (other instanceof PyFloat f) return PyBool.from(value.compareTo(f.value) <= 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__format__")
    public PyObject pyDanderFormat(String spec) {
        if (spec == null || spec.isEmpty()) return new PyString(pyDanderStr());
        return new PyString(FormatSpecs.pyIntSpec(value.toLong(), spec));
    }

    @Override
    public String pyDanderRepr() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return "PyInt(%s)".formatted(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PyInt o && this.value.equals(o.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}