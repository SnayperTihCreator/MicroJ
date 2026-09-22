package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.units.SmartFloat;
import org.tihrc.microj.units.SmartInt;

public class PyInt extends PyObject implements Protocols.PyNumber, Protocols.PyComparable {
    public final SmartInt value;
    private static final int CACHE_MIN = -256;
    private static final int CACHE_MAX = 100000;
    private static final PyInt[] CACHE = new PyInt[CACHE_MAX - CACHE_MIN + 1];
    static {
        for (int i = CACHE_MIN; i <= CACHE_MAX; i++) {
            CACHE[i - CACHE_MIN] = new PyInt(new SmartInt(i));
        }
    }

    public PyInt(SmartInt value) {this.value = value;}

    public static PyInt from(int value) {
        if (value >= CACHE_MIN && value <= CACHE_MAX) {
            return CACHE[value - CACHE_MIN];
        }
        return new PyInt(new SmartInt(value));
    }

    public static PyInt from(long value) {
        if (value >= CACHE_MIN && value <= CACHE_MAX) {
            return CACHE[(int) value - CACHE_MIN];
        }
        return new PyInt(new SmartInt(value));
    }

    public static PyInt from(SmartInt value) {
        if (value.isInt()) {
            int i = value.toInt();
            if (i >= CACHE_MIN && i <= CACHE_MAX) {
                return CACHE[i - CACHE_MIN];
            }
        }
        return new PyInt(value);
    }

    @Override
    @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        if (other instanceof PyInt otherInt) {
            if (value.isInt() && otherInt.value.isInt()) {
                long result = (long) value.toInt() + otherInt.value.toInt();
                return PyInt.from(result);
            }
            return PyInt.from(value.add(otherInt.value));
        }
        if (other instanceof PyFloat f)
            return new PyFloat(new SmartFloat(this.value.toDouble()).add(f.value));
        if (other instanceof PyComplex c)
            return c.pyDanderAdd(this);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__sub__")
    public PyObject pyDanderSub(PyObject other) {
        if (other instanceof PyInt otherInt) {
            if (value.isInt() && otherInt.value.isInt()) {
                long result = (long) value.toInt() - otherInt.value.toInt();
                return PyInt.from(result);
            }
            return PyInt.from(value.subtract(otherInt.value));
        }
        if (other instanceof PyFloat f) return new PyFloat(new SmartFloat(this.value.toDouble()).subtract(f.value));
        if (other instanceof PyComplex c) return c.pyDanderSub(this);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        if (other instanceof PyInt otherInt) {
            if (value.isInt() && otherInt.value.isInt()) {
                long result = (long) value.toInt() * otherInt.value.toInt();
                return PyInt.from(result);
            }
            return PyInt.from(value.multiply(otherInt.value));
        }
        if (other instanceof PyFloat f) return new PyFloat(new SmartFloat(this.value.toDouble()).multiply(f.value));
        if (other instanceof PyComplex c) return c.pyDanderMul(this);
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__truediv__")
    public PyObject pyDanderTrueDiv(PyObject other) {
        if (other instanceof PyInt otherInt) {
            if (otherInt.value.isZero())
                return new Exceptions.PyZeroDivisionError("integer division by zero").raise();
            return new PyFloat(this.value.divide(otherInt.value));
        }
        if (other instanceof PyFloat f)
            return f.pyDanderTrueDiv(this);
        if (other instanceof PyComplex c)
            return c.pyDanderTrueDiv(this);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__neg__")
    public PyObject pyDanderNeg() {
        if (value.isInt()) {
            return PyInt.from(-(long) value.toInt());
        }

        return PyInt.from(value.multiply(new SmartInt(-1)));
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() {
        if (value.isInt()) {
            return value.toInt() != 0;
        }

        return !value.equals(SmartInt.ZERO);
    }

    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        if (other instanceof PyInt o) {
            if (value.isInt() && o.value.isInt())
                return PyBool.from(value.toInt() == o.value.toInt());
            return PyBool.from(value.equals(o.value));
        }
        if (other instanceof PyFloat f)
            return PyBool.from(value.toDouble() == f.value.toDouble());
        if (other instanceof PyBool b)
            return PyBool.from(value.toInt() == (b.value ? 1 : 0));
        return PyBool.FALSE;
    }
    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        if (other instanceof PyInt o) {
            if (value.isInt() && o.value.isInt())
                return PyBool.from(value.toInt() != o.value.toInt());
            return PyBool.from(!value.equals(o.value));
        }
        if (other instanceof PyFloat f)
            return PyBool.from(value.toDouble() != f.value.toDouble());
        if (other instanceof PyBool b)
            return PyBool.from(value.toInt() != (b.value ? 1 : 0));
        return PyBool.TRUE;
    }
    @Override
    @PyExport(name = "__gt__")
    public PyObject pyDanderGt(PyObject other) {
        if (other instanceof PyInt o) {
            if (value.isInt() && o.value.isInt())
                return PyBool.from(value.toInt() > o.value.toInt());
            return PyBool.from(value.compareTo(o.value) > 0);
        }
        if (other instanceof PyFloat f)
            return PyBool.from(value.toDouble() > f.value.toDouble());
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__lt__")
    public PyObject pyDanderLt(PyObject other) {
        if (other instanceof PyInt o) {
            if (value.isInt() && o.value.isInt())
                return PyBool.from(value.toInt() < o.value.toInt());
            return PyBool.from(value.compareTo(o.value) < 0);
        }
        if (other instanceof PyFloat f)
            return PyBool.from(value.toDouble() < f.value.toDouble());
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__ge__")
    public PyObject pyDanderGe(PyObject other) {
        if (other instanceof PyInt o) {
            if (value.isInt() && o.value.isInt())
                return PyBool.from(value.toInt() >= o.value.toInt());
            return PyBool.from(value.compareTo(o.value) >= 0);
        }
        if (other instanceof PyFloat f)
            return PyBool.from(value.toDouble() >= f.value.toDouble());
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__le__")
    public PyObject pyDanderLe(PyObject other) {
        if (other instanceof PyInt o) {
            if (value.isInt() && o.value.isInt())
                return PyBool.from(value.toInt() <= o.value.toInt());
            return PyBool.from(value.compareTo(o.value) <= 0);
        }
        if (other instanceof PyFloat f)
            return PyBool.from(value.toDouble() <= f.value.toDouble());
        return PyNotImplemented.INSTANCE;
    }
    @Override
    @PyExport(name = "__format__")
    public PyObject pyDanderFormat(String spec) {
        if (spec == null || spec.isEmpty()) {
            return new PyString(pyDanderStr());
        }
        long number = this.value.toLong();
        return new PyString(applyIntFormatSpec(number, spec));
    }

    private String applyIntFormatSpec(long number, String spec) {
        char type = 'd';
        int width = 0;
        char fill = ' ';
        char align = '>';
        boolean zeroPad = false;

        if (!spec.isEmpty()) {
            char last = spec.charAt(spec.length() - 1);
            if (last == 'd' || last == 'b' || last == 'o' || last == 'x' || last == 'X' || last == 'c' || last == 'n') {
                type = last;
                spec = spec.substring(0, spec.length() - 1);
            }
        }

        if (spec.startsWith("0") && spec.length() > 1) {
            zeroPad = true;
            spec = spec.substring(1);
        }

        if (!spec.isEmpty() && (spec.charAt(0) == '<' || spec.charAt(0) == '>' || spec.charAt(0) == '^')) {
            align = spec.charAt(0);
            spec = spec.substring(1);
        }

        try {
            if (!spec.isEmpty()) {
                width = Integer.parseInt(spec);
            }
        } catch (NumberFormatException e) {
            return String.valueOf(number);
        }

        String sign = "";
        long absNum = number;
        if (number < 0) {
            sign = "-";
            absNum = -number;
        }

        String numStr = switch (type) {
            case 'b' -> Long.toBinaryString(absNum);
            case 'o' -> Long.toOctalString(absNum);
            case 'x' -> Long.toHexString(absNum);
            case 'X' -> Long.toHexString(absNum).toUpperCase();
            case 'c' -> String.valueOf((char) absNum);
            default -> String.valueOf(absNum);
        };

        String fullStr = sign + numStr;

        if (fullStr.length() >= width) return fullStr;

        int padCount = width - fullStr.length();
        StringBuilder sb = new StringBuilder();

        if (zeroPad) {
            fill = '0';
            align = '>';
        }

        if (zeroPad && !sign.isEmpty()) {
            sb.append(sign);
            sb.append(String.valueOf(fill).repeat(padCount));
            sb.append(numStr);
        } else {
            if (align == '>') {
                sb.append(String.valueOf(fill).repeat(padCount));
                sb.append(fullStr);
            } else if (align == '<') {
                sb.append(fullStr);
                sb.append(String.valueOf(fill).repeat(padCount));
            } else if (align == '^') {
                int left = padCount / 2;
                int right = padCount - left;
                sb.append(String.valueOf(fill).repeat(left));
                sb.append(fullStr);
                sb.append(String.valueOf(fill).repeat(Math.max(0, right)));
            }
        }
        return sb.toString();
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
        if (obj instanceof PyInt o) return this.value.equals(o.value);
        return false;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
