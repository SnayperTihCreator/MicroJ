package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.types.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.FormatSpecs;
import org.tihrc.microj.units.SmartFloat;

public class PyFloat extends PyObject implements Protocols.PyNumber, Protocols.PyComparable {
    public final SmartFloat value;
    public static final PyFloat NaN = new PyFloat(new SmartFloat(Double.NaN));
    public static final PyFloat INF = new PyFloat(new SmartFloat(Double.POSITIVE_INFINITY));

    public PyFloat(SmartFloat value) { this.value = value; }
    public PyFloat(double value) { this.value = new SmartFloat(value); }

    private static boolean isNaNVal(SmartFloat f) {
        return Double.isNaN(f.toDouble());
    }

    @Override
    @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f)  return new PyFloat(value.add(f.value));
        if (other instanceof PyInt i)    return new PyFloat(value.add(i.value));
        if (other instanceof PyComplex c) return c.pyDanderRAdd(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__sub__")
    public PyObject pyDanderSub(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f)  return new PyFloat(value.sub(f.value));
        if (other instanceof PyInt i)    return new PyFloat(value.sub(i.value));
        if (other instanceof PyComplex c) return c.pyDanderRSub(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f)  return new PyFloat(value.mul(f.value));
        if (other instanceof PyInt i)    return new PyFloat(value.mul(i.value));
        if (other instanceof PyComplex c) return c.pyDanderRMul(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__pow__")
    public PyObject pyDanderPow(PyObject exp) {
        exp = PyInt.asInt(exp);
        if (exp instanceof PyFloat f)  return new PyFloat(Math.pow(value.toDouble(), f.value.toDouble()));
        if (exp instanceof PyInt i)    return new PyFloat(Math.pow(value.toDouble(), i.value.toDouble()));
//        if (exp instanceof PyComplex c) return c.pyDanderRMul(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__truediv__")
    public PyObject pyDanderTrueDiv(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (f.value.isZero())
                return new Exceptions.PyZeroDivisionError("float division by zero").raise();
            return new PyFloat(value.div(f.value));
        }
        if (other instanceof PyInt i) {
            if (i.value.isZero())
                return new Exceptions.PyZeroDivisionError("float division by zero").raise();
            return new PyFloat(value.div(i.value));
        }
        if (other instanceof PyComplex c) return c.pyDanderRTruediv(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__floordiv__")
    public PyObject pyDanderFloorDiv(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (f.value.isZero())
                return new Exceptions.PyZeroDivisionError("float floor division by zero").raise();
            return new PyFloat(value.floorDiv(f.value));
        }
        if (other instanceof PyInt i) {
            if (i.value.isZero())
                return new Exceptions.PyZeroDivisionError("float floor division by zero").raise();
            return new PyFloat(value.floorDiv(i.value));
        }
        return PyNotImplemented.INSTANCE;   // PyComplex сюда не попадёт — как TypeError в Python
    }

    @Override
    @PyExport(name = "__mod__")
    public PyObject pyDanderMod(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (f.value.isZero())
                return new Exceptions.PyZeroDivisionError("float modulo").raise();
            return new PyFloat(value.mod(f.value));   // -5.0 % 3.0 == 1.0, а не -2.0
        }
        if (other instanceof PyInt i) {
            if (i.value.isZero())
                return new Exceptions.PyZeroDivisionError("float modulo").raise();
            return new PyFloat(value.mod(i.value));
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__neg__")
    public PyObject pyDanderNeg() {
        return new PyFloat(value.negate());
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        return !value.isZero();   // NaN -> true, как в Python
    }

    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (isNaNVal(value) || isNaNVal(f.value)) return PyBool.FALSE;  // nan == nan -> False
            return PyBool.from(value.compareTo(f.value) == 0);
        }
        if (other instanceof PyInt i) {
            if (isNaNVal(value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(i.value) == 0);
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (isNaNVal(value) || isNaNVal(f.value)) return PyBool.TRUE;   // nan != nan -> True
            return PyBool.from(value.compareTo(f.value) != 0);
        }
        if (other instanceof PyInt i) {
            if (isNaNVal(value)) return PyBool.TRUE;
            return PyBool.from(value.compareTo(i.value) != 0);
        }
        return PyNotImplemented.INSTANCE;
    }

    // С NaN любое сравнение порядка — False (как в Python)
    @Override
    @PyExport(name = "__lt__")
    public PyObject pyDanderLt(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (isNaNVal(value) || isNaNVal(f.value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(f.value) < 0);
        }
        if (other instanceof PyInt i) {
            if (isNaNVal(value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(i.value) < 0);
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__le__")
    public PyObject pyDanderLe(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (isNaNVal(value) || isNaNVal(f.value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(f.value) <= 0);
        }
        if (other instanceof PyInt i) {
            if (isNaNVal(value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(i.value) <= 0);
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__gt__")
    public PyObject pyDanderGt(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (isNaNVal(value) || isNaNVal(f.value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(f.value) > 0);
        }
        if (other instanceof PyInt i) {
            if (isNaNVal(value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(i.value) > 0);
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__ge__")
    public PyObject pyDanderGe(PyObject other) {
        other = PyInt.asInt(other);
        if (other instanceof PyFloat f) {
            if (isNaNVal(value) || isNaNVal(f.value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(f.value) >= 0);
        }
        if (other instanceof PyInt i) {
            if (isNaNVal(value)) return PyBool.FALSE;
            return PyBool.from(value.compareTo(i.value) >= 0);
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    public String pyDanderRepr() {
        double d = value.toDouble();
        if (Double.isInfinite(d)) return d > 0 ? "inf" : "-inf";
        if (Double.isNaN(d)) return "nan";
        if (d == Math.floor(d) && Math.abs(d) < 1e16) return (long) d + ".0";
        return String.valueOf(d);
    }

    @Override
    @PyExport(name = "__format__")
    public PyObject pyDanderFormat(String spec) {
        if (spec == null || spec.isEmpty()) return new PyString(pyDanderStr());
        return new PyString(FormatSpecs.pyFloatSpec(value, spec));
    }

    @Override
    public String toString() {
        return "PyFloat(%s)".formatted(pyDanderRepr());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PyFloat o && this.value.equals(o.value);  // 0.0 == -0.0, NaN == NaN (Java-уровень)
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}