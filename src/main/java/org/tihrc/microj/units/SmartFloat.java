package org.tihrc.microj.units;

import java.math.BigDecimal;

public class SmartFloat implements Comparable<SmartFloat> {
    private enum Type { DOUBLE, BIG_DECIMAL }

    private final Type type;
    private double dVal;
    private BigDecimal bdVal;

    public SmartFloat(double val) {
        this.type = Type.DOUBLE;
        this.dVal = val;
    }

    public SmartFloat(BigDecimal val) {
        double d = val.doubleValue();
        if (!Double.isInfinite(d) && !Double.isNaN(d)) {
            this.type = Type.DOUBLE;
            this.dVal = d;
        } else {
            this.type = Type.BIG_DECIMAL;
            this.bdVal = val;
        }
    }

    public static SmartFloat parse(String str) {
        str = str.trim();
        try {
            double d = Double.parseDouble(str);
            return new SmartFloat(d);
        } catch (NumberFormatException e) {
            return new SmartFloat(new BigDecimal(str));
        }
    }

    private boolean isSpecialDouble() {
        return type == Type.DOUBLE && (Double.isInfinite(dVal) || Double.isNaN(dVal));
    }

    public SmartFloat add(SmartFloat other) {
        // Если хоть одно число Infinite/NaN, просто складываем как double (IEEE 754)
        if (this.isSpecialDouble() || other.isSpecialDouble()) {
            return new SmartFloat(this.toDouble() + other.toDouble());
        }

        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL) {
            return new SmartFloat(this.toBigDecimal().add(other.toBigDecimal()));
        }

        double res = this.dVal + other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res)) {
            return new SmartFloat(this.toBigDecimal().add(other.toBigDecimal()));
        }
        return new SmartFloat(res);
    }

    public SmartFloat subtract(SmartFloat other) {
        if (this.isSpecialDouble() || other.isSpecialDouble()) {
            return new SmartFloat(this.toDouble() - other.toDouble());
        }
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL) {
            return new SmartFloat(this.toBigDecimal().subtract(other.toBigDecimal()));
        }
        double res = this.dVal - other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res)) {
            return new SmartFloat(this.toBigDecimal().subtract(other.toBigDecimal()));
        }
        return new SmartFloat(res);
    }

    public SmartFloat multiply(SmartFloat other) {
        if (this.isSpecialDouble() || other.isSpecialDouble()) {
            return new SmartFloat(this.toDouble() * other.toDouble());
        }
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL) {
            return new SmartFloat(this.toBigDecimal().multiply(other.toBigDecimal()));
        }
        double res = this.dVal * other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res)) {
            return new SmartFloat(this.toBigDecimal().multiply(other.toBigDecimal()));
        }
        return new SmartFloat(res);
    }

    public SmartFloat divide(SmartFloat other) {
        if (other.type == Type.DOUBLE && other.dVal == 0.0) {
            throw new RuntimeException("ZeroDivisionError: float division by zero");
        }

        if (this.isSpecialDouble() || other.isSpecialDouble()) {
            return new SmartFloat(this.toDouble() / other.toDouble());
        }

        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL) {
            return new SmartFloat(this.toBigDecimal().divide(other.toBigDecimal(), java.math.MathContext.DECIMAL128));
        }

        double res = this.dVal / other.dVal;
        if (Double.isInfinite(res) || Double.isNaN(res)) {
            return new SmartFloat(this.toBigDecimal().divide(other.toBigDecimal(), java.math.MathContext.DECIMAL128));
        }
        return new SmartFloat(res);
    }

    public int compareTo(SmartFloat other) {
        if (this.isSpecialDouble() || other.isSpecialDouble()) {
            return Double.compare(this.toDouble(), other.toDouble());
        }
        if (this.type == Type.BIG_DECIMAL || other.type == Type.BIG_DECIMAL) {
            return this.toBigDecimal().compareTo(other.toBigDecimal());
        }
        return Double.compare(this.dVal, other.dVal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SmartFloat other)) return false;
        return this.compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        return this.toBigDecimal().hashCode();
    }

    public double toDouble() {
        return (type == Type.DOUBLE) ? dVal : bdVal.doubleValue();
    }

    private BigDecimal toBigDecimal() {
        if (type == Type.BIG_DECIMAL) return bdVal;
        return BigDecimal.valueOf(dVal);
    }

    public boolean isNegative() {
        if (type == Type.BIG_DECIMAL) return bdVal.signum() < 0;
        return dVal < 0;
    }

    public String getStorageType() {
        return type.name();
    }

    @Override
    public String toString() {
        if (Double.isInfinite(dVal)) return dVal > 0 ? "inf" : "-inf";
        if (Double.isNaN(dVal)) return "nan";
        return (type == Type.DOUBLE) ? String.valueOf(dVal) : bdVal.toPlainString();
    }
}