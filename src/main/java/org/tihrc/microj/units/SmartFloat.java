package org.tihrc.microj.units;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public final class SmartFloat extends SmartNumber {
    private enum Type { DOUBLE, BIG_DECIMAL }

    private final Type type;
    private final double dVal;
    private final BigDecimal bdVal;

    public static final SmartFloat ZERO = new SmartFloat(0);
    public static final SmartFloat ONE  = new SmartFloat(1);

    public SmartFloat(double val) {
        this.type = Type.DOUBLE;
        this.dVal = val;
        this.bdVal = null;
    }

    public SmartFloat(BigInteger bigInteger) {
        this(new BigDecimal(bigInteger));
    }

    public SmartFloat(BigDecimal val) {
        Objects.requireNonNull(val, "val");
        double d = val.doubleValue();
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            this.type = Type.DOUBLE;
            this.dVal = d;
            this.bdVal = null;
        } else {
            this.type = Type.BIG_DECIMAL;
            this.dVal = 0.0;
            this.bdVal = val;
        }
    }

    public static SmartFloat parse(String str) {
        if (str == null || str.trim().isEmpty())
            throw new NumberFormatException("Строка пустая или null");
        str = str.trim();
        try {
            return new SmartFloat(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            return new SmartFloat(new BigDecimal(str));
        }
    }

    public boolean isSpecialDouble() {
        return type == Type.DOUBLE && (Double.isInfinite(dVal) || Double.isNaN(dVal));
    }

    @Override
    public boolean isZero() {
        return (type == Type.BIG_DECIMAL) ? bdVal.signum() == 0 : dVal == 0.0;   // ловит и -0.0
    }

    @Override
    public boolean isNegative() {
        if (type == Type.BIG_DECIMAL) return bdVal.signum() < 0;
        if (Double.isNaN(dVal)) return false;
        return dVal < 0.0;
    }

    @Override
    public boolean isPositive() {
        if (type == Type.BIG_DECIMAL) return bdVal.signum() > 0;
        if (Double.isNaN(dVal)) return false;
        return dVal > 0.0;
    }

    public SmartFloat add(SmartFloat other) {
        if (this.isSpecialDouble() || other.isSpecialDouble())
            return new SmartFloat(this.toDouble() + other.toDouble());
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL)
            return new SmartFloat(this.toBigDecimal().add(other.toBigDecimal()));
        double res = this.dVal + other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res))
            return new SmartFloat(this.toBigDecimal().add(other.toBigDecimal()));
        return new SmartFloat(res);
    }

    public SmartFloat sub(SmartFloat other) {
        if (this.isSpecialDouble() || other.isSpecialDouble())
            return new SmartFloat(this.toDouble() - other.toDouble());
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL)
            return new SmartFloat(this.toBigDecimal().subtract(other.toBigDecimal()));
        double res = this.dVal - other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res))
            return new SmartFloat(this.toBigDecimal().subtract(other.toBigDecimal()));
        return new SmartFloat(res);
    }

    public SmartFloat mul(SmartFloat other) {
        if (this.isSpecialDouble() || other.isSpecialDouble())
            return new SmartFloat(this.toDouble() * other.toDouble());
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL)
            return new SmartFloat(this.toBigDecimal().multiply(other.toBigDecimal()));
        double res = this.dVal * other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res))
            return new SmartFloat(this.toBigDecimal().multiply(other.toBigDecimal()));
        return new SmartFloat(res);
    }

    public SmartFloat div(SmartFloat other) {
        if (other.isZero())
            throw new RuntimeException("ZeroDivisionError: float division by zero");
        if (this.isSpecialDouble() || other.isSpecialDouble())
            return new SmartFloat(this.toDouble() / other.toDouble());
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL)
            return new SmartFloat(this.toBigDecimal().divide(other.toBigDecimal(), MathContext.DECIMAL128));
        double res = this.dVal / other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res))
            return new SmartFloat(this.toBigDecimal().divide(other.toBigDecimal(), MathContext.DECIMAL128));
        return new SmartFloat(res);
    }

    public SmartFloat floorDiv(SmartFloat other) {
        if (other.isZero())
            throw new RuntimeException("ZeroDivisionError: float floor division by zero");
        if (this.isSpecialDouble() || other.isSpecialDouble())
            return new SmartFloat(Math.floor(this.toDouble() / other.toDouble()));
        double vx = this.dVal, wx = other.dVal;
        double mod = vx % wx;
        double div = (vx - mod) / wx;
        if (mod != 0.0 && ((wx < 0) != (mod < 0))) {
            div -= 1.0;
        }
        double floordiv = Math.floor(div);
        if (div - floordiv > 0.5)
            floordiv += 1.0;
        return new SmartFloat(floordiv);
    }

    public SmartFloat mod(SmartFloat other) {
        if (other.isZero())
            throw new RuntimeException("ZeroDivisionError: float modulo");
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL) {
            BigDecimal a = this.toBigDecimal(), b = other.toBigDecimal();
            BigDecimal q = a.divide(b, MathContext.DECIMAL128).setScale(0, RoundingMode.FLOOR);
            return new SmartFloat(a.subtract(q.multiply(b)));
        }
        if (this.isSpecialDouble())
            return new SmartFloat(this.toDouble() % other.toDouble());
        double r = this.dVal % other.dVal;
        if (r != 0.0 && ((r < 0) != (other.dVal < 0)))
            r += other.dVal;
        return new SmartFloat(r);
    }

    public SmartFloat add(SmartInt o)      { return add(o.toSmartFloat()); }
    public SmartFloat sub(SmartInt o)      { return sub(o.toSmartFloat()); }
    public SmartFloat mul(SmartInt o)      { return mul(o.toSmartFloat()); }
    public SmartFloat div(SmartInt o)      { return div(o.toSmartFloat()); }
    public SmartFloat floorDiv(SmartInt o) { return floorDiv(o.toSmartFloat()); }
    public SmartFloat mod(SmartInt o)      { return mod(o.toSmartFloat()); }

    @Override public SmartNumber add(SmartNumber o)      { return add(o.toSmartFloat()); }
    @Override public SmartNumber sub(SmartNumber o)      { return sub(o.toSmartFloat()); }
    @Override public SmartNumber mul(SmartNumber o)      { return mul(o.toSmartFloat()); }
    @Override public SmartFloat div(SmartNumber o)       { return div(o.toSmartFloat()); }
    @Override public SmartNumber floorDiv(SmartNumber o) { return floorDiv(o.toSmartFloat()); }
    @Override public SmartNumber mod(SmartNumber o)      { return mod(o.toSmartFloat()); }

    @Override
    public SmartFloat negate() {
        return (type == Type.BIG_DECIMAL) ? new SmartFloat(bdVal.negate()) : new SmartFloat(-dVal);
    }

    @Override
    public SmartFloat abs() {
        if (type == Type.BIG_DECIMAL)
            return bdVal.signum() < 0 ? new SmartFloat(bdVal.negate()) : this;
        if (Double.isNaN(dVal)) return this;
        return dVal < 0 ? new SmartFloat(-dVal) : this;
    }

    @Override
    public int signum() {
        if (type == Type.BIG_DECIMAL) return bdVal.signum();
        if (Double.isNaN(dVal)) throw new ArithmeticException("signum() of NaN");
        if (dVal == 0.0) return 0;
        return dVal > 0 ? 1 : -1;
    }

    @Override public boolean isInt()   { return false; }
    @Override public boolean isFloat() { return true; }
    @Override public SmartFloat toSmartFloat() { return this; }

    @Override
    public SmartInt toSmartInt() {
        if (type == Type.BIG_DECIMAL) return new SmartInt(bdVal.toBigInteger());
        if (Double.isNaN(dVal) || Double.isInfinite(dVal))
            throw new ArithmeticException("cannot convert float " + this + " to int");
        return new SmartInt(BigDecimal.valueOf(dVal).toBigInteger());
    }

    @Override
    public double toDouble() {
        return (type == Type.DOUBLE) ? dVal : bdVal.doubleValue();
    }

    @Override
    public BigInteger toBigInteger() {
        return toSmartInt().toBigInteger();
    }

    @Override
    public BigDecimal toBigDecimal() {
        return (type == Type.BIG_DECIMAL) ? bdVal : new BigDecimal(dVal);
    }

    public int compareTo(SmartFloat other) {
        if (this.isSpecialDouble() || other.isSpecialDouble())
            return Double.compare(this.toDouble(), other.toDouble());
        return this.toBigDecimal().compareTo(other.toBigDecimal());
    }

    public int compareTo(SmartInt other) {
        if (this.isSpecialDouble())
            return Double.compare(this.dVal, other.toDouble());
        return this.toBigDecimal().compareTo(other.toBigDecimal());
    }

    @Override
    public int compareTo(SmartNumber other) {
        if (other instanceof SmartInt i) return compareTo(i);
        return compareTo((SmartFloat) other);
    }

    @Override
    public int hashCode() {
        if (type == Type.BIG_DECIMAL) {
            BigDecimal stripped = bdVal.stripTrailingZeros();
            if (stripped.scale() <= 0) return hashOfInt(stripped.toBigInteger());
            return stripped.hashCode();
        }
        if (Double.isNaN(dVal) || Double.isInfinite(dVal)) return Double.hashCode(dVal);
        if (dVal == Math.floor(dVal))
            return hashOfInt(new BigDecimal(dVal).toBigInteger());
        return Double.hashCode(dVal);
    }

    public String getStorageType() {
        return type.name();
    }

    @Override
    public String toString() {
        if (type == Type.BIG_DECIMAL) return bdVal.toPlainString();
        if (Double.isInfinite(dVal)) return dVal > 0 ? "inf" : "-inf";
        if (Double.isNaN(dVal)) return "nan";
        return String.valueOf(dVal);
    }
}