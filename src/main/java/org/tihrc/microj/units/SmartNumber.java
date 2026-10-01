package org.tihrc.microj.units;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class SmartNumber implements Comparable<SmartNumber> {
    public static SmartNumber from(int value){ return new SmartInt(value); }
    public static SmartNumber from(long value){ return new SmartInt(value); }
    public static SmartNumber from(double value){ return new SmartFloat(value); }
    public static SmartNumber from(BigInteger value){ return new SmartInt(value); }
    public static SmartNumber from(BigDecimal value){ return new SmartFloat(value); }

    public abstract SmartNumber add(SmartNumber other);
    public abstract SmartNumber sub(SmartNumber other);
    public abstract SmartNumber mul(SmartNumber other);
    public abstract SmartNumber div(SmartNumber other);
    public abstract SmartNumber floorDiv(SmartNumber other);
    public abstract SmartNumber mod(SmartNumber other);

    public abstract SmartNumber negate();

    public SmartNumber abs(){
        return isNegative()? negate(): this;
    }

    public abstract int signum();
    public boolean isZero(){ return signum() == 0; }
    public boolean isPositive(){ return signum() > 0; }
    public boolean isNegative(){ return signum() < 0; }

    public abstract boolean isInt();
    public abstract boolean isFloat();

    public abstract SmartInt toSmartInt();
    public abstract SmartFloat toSmartFloat();
    public abstract BigDecimal toBigDecimal();
    public abstract BigInteger toBigInteger();
    public abstract double toDouble();

    @Override
    public abstract int compareTo(SmartNumber other);
    public boolean gt(SmartNumber other) { return this.compareTo(other) > 0; }
    public boolean lt(SmartNumber other) { return this.compareTo(other) < 0; }
    public boolean ge(SmartNumber other) { return this.compareTo(other) >= 0; }
    public boolean le(SmartNumber other) { return this.compareTo(other) <= 0; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SmartNumber other)) return false;
        return compareTo(other) == 0;
    }

    @Override
    public abstract int hashCode();
    protected static int hashOfInt(BigInteger value){
        if (value.bitLength() <= 31) return value.intValue();
        if (value.bitLength() <= 63) return Long.hashCode(value.longValue());
        return value.hashCode();
    }
}
