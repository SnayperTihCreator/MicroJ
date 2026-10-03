package org.tihrc.microj.units;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;

public final class SmartInt extends SmartNumber {
    private enum StorageType {INT, LONG, BIG_INTEGER}

    private final StorageType type;
    private final int intValue;
    private final long longValue;
    private final BigInteger bigValue;

    public static final SmartInt ZERO = new SmartInt(0);
    public static final SmartInt ONE = new SmartInt(1);

    public SmartInt(int value) {
        this.type = StorageType.INT;
        this.intValue = value;
        this.longValue = 0L;
        this.bigValue = null;
    }
    public SmartInt(long value) {
        if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
            this.type = StorageType.INT;
            this.intValue = (int) value;
            this.longValue = 0L;
        } else {
            this.type = StorageType.LONG;
            this.intValue = 0;
            this.longValue = value;
        }
        this.bigValue = null;
    }
    public SmartInt(BigInteger value) {
        Objects.requireNonNull(value, "value");
        if (value.bitLength() <= 31) {
            this.type = StorageType.INT;
            this.intValue = value.intValue();
            this.longValue = 0L;
            this.bigValue = null;
        } else if (value.bitLength() <= 63) {
            this.type = StorageType.LONG;
            this.intValue = 0;
            this.longValue = value.longValue();
            this.bigValue = null;
        } else {
            this.type = StorageType.BIG_INTEGER;
            this.intValue = 0;
            this.longValue = 0L;
            this.bigValue = value;
        }
    }

    private static boolean willLongAddOverflow(long a, long b) {
        if (b > 0 && a > Long.MAX_VALUE - b) return true;
        return b < 0 && a < Long.MIN_VALUE - b;
    }
    private static boolean willLongSubOverflow(long a, long b) {
        if (b > 0 && a < Long.MIN_VALUE + b) return true;
        return b < 0 && a > Long.MAX_VALUE + b;
    }
    private static boolean willLongMulOverflow(long a, long b) {
        if (a == 0 || b == 0) return false;
        if (a == Long.MIN_VALUE && b == -1) return true;
        if (a == -1 && b == Long.MIN_VALUE) return true;
        long result = a * b;
        return (result / b != a);
    }

    public SmartInt add(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER) {
            return new SmartInt(this.toBigInteger().add(other.toBigInteger()));
        }
        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            long a = this.toLong();
            long b = other.toLong();
            if (willLongAddOverflow(a, b)) {
                return new SmartInt(BigInteger.valueOf(a).add(BigInteger.valueOf(b)));
            }
            return new SmartInt(a + b);
        }
        long res = (long) this.intValue + other.intValue;
        if (res < Integer.MIN_VALUE || res > Integer.MAX_VALUE) {
            return new SmartInt(res);              // апгрейд до LONG
        }
        return new SmartInt((int) res);
    }
    public SmartInt sub(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER)
            return new SmartInt(this.toBigInteger().subtract(other.toBigInteger()));
        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            long a = this.toLong();
            long b = other.toLong();
            if (willLongSubOverflow(a, b))
                return new SmartInt(BigInteger.valueOf(a).subtract(BigInteger.valueOf(b)));
            return new SmartInt(a - b);
        }
        long res = (long) this.intValue - other.intValue;
        if (res < Integer.MIN_VALUE || res > Integer.MAX_VALUE) return new SmartInt(res);
        return new SmartInt((int) res);
    }
    public SmartInt mul(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER)
            return new SmartInt(this.toBigInteger().multiply(other.toBigInteger()));
        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            long a = this.toLong();
            long b = other.toLong();
            if (willLongMulOverflow(a, b))
                return new SmartInt(BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)));
            return new SmartInt(a * b);
        }
        long res = (long) this.intValue * other.intValue;
        if (res < Integer.MIN_VALUE || res > Integer.MAX_VALUE) return new SmartInt(res);
        return new SmartInt((int) res);
    }

    public SmartInt pow(SmartInt other) {
        int exp = other.toInt();
        if (exp < 0) return null;
        if (exp == 0) return SmartInt.ONE;
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER)
            return new SmartInt(this.toBigInteger().pow(exp));
        long base = this.toLong();
        long acc = 1;
        for (int bit = 0; bit < 32 && (exp >> bit) > 0; bit++) {
            if ((exp & (1 << bit)) != 0) {
                if (willLongMulOverflow(acc, base)) return new SmartInt(
                        BigInteger.valueOf(acc).multiply(BigInteger.valueOf(base)).pow(1));
                acc *= base;
            }
            if ((exp >> (bit + 1)) > 0) {
                if (willLongMulOverflow(base, base))
                    return new SmartInt(this.toBigInteger().pow(exp));
                base *= base;
            }
        }
        return new SmartInt(acc);
    }

    public SmartInt floorDiv(SmartInt other) {
        if (other.isZero())
            throw new RuntimeException("ZeroDivisionError: integer floor division by zero");
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER)
            return new SmartInt(floorDivBI(this.toBigInteger(), other.toBigInteger()));
        return new SmartInt(Math.floorDiv(this.toLong(), other.toLong()));
    }
    public SmartInt mod(SmartInt other) {
        if (other.isZero())
            throw new RuntimeException("ZeroDivisionError: integer modulo by zero");
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER)
            return new SmartInt(floorModBI(this.toBigInteger(), other.toBigInteger()));
        return new SmartInt(Math.floorMod(this.toLong(), other.toLong()));
    }

    public SmartFloat div(SmartInt other) {
        if (other.isZero())
            throw new RuntimeException("ZeroDivisionError: division by zero");
        if (this.type == StorageType.INT && other.type == StorageType.INT)
            return new SmartFloat((double) intValue / other.intValue);
        return new SmartFloat(this.toBigDecimal().divide(other.toBigDecimal(), MathContext.DECIMAL128));
    }

    private static BigInteger floorDivBI(BigInteger a, BigInteger b) {
        BigInteger[] dr = a.divideAndRemainder(b);
        if (dr[1].signum() != 0 && a.signum() != b.signum())
            return dr[0].subtract(BigInteger.ONE);
        return dr[0];
    }
    private static BigInteger floorModBI(BigInteger a, BigInteger b) {
        BigInteger r = a.remainder(b);
        if (r.signum() != 0 && a.signum() != b.signum())
            r = r.add(b);
        return r;
    }

    public SmartFloat add(SmartFloat other)      { return toSmartFloat().add(other); }
    public SmartFloat sub(SmartFloat other)      { return toSmartFloat().sub(other); }
    public SmartFloat mul(SmartFloat other)      { return toSmartFloat().mul(other); }
    public SmartFloat div(SmartFloat other)      { return toSmartFloat().div(other); }
    public SmartFloat floorDiv(SmartFloat other) { return toSmartFloat().floorDiv(other); }
    public SmartFloat mod(SmartFloat other)      { return toSmartFloat().mod(other); }

    @Override public SmartNumber add(SmartNumber o) {
        return (o instanceof SmartFloat f) ? add(f) : add((SmartInt) o);
    }
    @Override public SmartNumber sub(SmartNumber o) {
        return (o instanceof SmartFloat f) ? sub(f) : sub((SmartInt) o);
    }
    @Override public SmartNumber mul(SmartNumber o) {
        return (o instanceof SmartFloat f) ? mul(f) : mul((SmartInt) o);
    }
    @Override public SmartFloat div(SmartNumber o) {   // ковариантный возврат
        return (o instanceof SmartFloat f) ? div(f) : div((SmartInt) o);
    }
    @Override public SmartNumber floorDiv(SmartNumber o) {
        return (o instanceof SmartFloat f) ? floorDiv(f) : floorDiv((SmartInt) o);
    }
    @Override public SmartNumber mod(SmartNumber o) {
        return (o instanceof SmartFloat f) ? mod(f) : mod((SmartInt) o);
    }

    @Override
    public SmartInt negate() {
        return switch (type) {
            case INT -> (intValue == Integer.MIN_VALUE)
                    ? new SmartInt(-(long) intValue)
                    : new SmartInt(-intValue);
            case LONG -> (longValue == Long.MIN_VALUE)
                    ? new SmartInt(BigInteger.valueOf(longValue).negate())
                    : new SmartInt(-longValue);
            case BIG_INTEGER -> new SmartInt(bigValue.negate());
        };
    }

    @Override
    public SmartInt abs() {
        return isNegative() ? negate() : this;
    }

    @Override
    public int signum() {
        return switch (type) {
            case INT -> Integer.signum(intValue);
            case LONG -> Long.signum(longValue);
            case BIG_INTEGER -> bigValue.signum();
        };
    }

    @Override public boolean isInt()   { return true; }
    @Override public boolean isFloat() { return false; }

    @Override public SmartInt toSmartInt() { return this; }

    @Override
    public SmartFloat toSmartFloat() {
        return new SmartFloat(toBigInteger());
    }

    @Override
    public double toDouble() {
        return switch (type) {
            case INT -> (double) intValue;
            case LONG -> (double) longValue;
            case BIG_INTEGER -> bigValue.doubleValue();
        };
    }

    @Override
    public BigInteger toBigInteger() {
        if (type == StorageType.BIG_INTEGER) return bigValue;
        if (type == StorageType.LONG) return BigInteger.valueOf(longValue);
        return BigInteger.valueOf(intValue);
    }

    @Override
    public BigDecimal toBigDecimal() {
        return switch (type) {
            case INT -> BigDecimal.valueOf(intValue);
            case LONG -> BigDecimal.valueOf(longValue);
            case BIG_INTEGER -> new BigDecimal(bigValue);
        };
    }

    public int toInt() {
        if (type == StorageType.INT) return intValue;
        return (int) toLong();
    }

    public long toLong() {
        if (type == StorageType.INT) return intValue;
        if (type == StorageType.LONG) return longValue;
        return bigValue.longValue();   // осторожно: обрезка
    }

    // ================= Сравнение =================

    public int compareTo(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER)
            return this.toBigInteger().compareTo(other.toBigInteger());   // НЕ через long — была обрезка!
        if (this.type == StorageType.LONG || other.type == StorageType.LONG)
            return Long.compare(this.toLong(), other.toLong());
        return Integer.compare(this.intValue, other.intValue);
    }

    @Override
    public int compareTo(SmartNumber other) {
        if (other instanceof SmartFloat f) {
            if (f.isSpecialDouble()) {
                double d = f.toDouble();
                if (Double.isNaN(d)) return -1;
                return d > 0 ? -1 : 1;
            }
            return this.toBigDecimal().compareTo(f.toBigDecimal());
        }
        return compareTo((SmartInt) other);
    }

    @Override
    public int hashCode() {
        return hashOfInt(toBigInteger());
    }

    @Override
    public String toString() {
        return switch (type) {
            case INT -> Integer.toString(intValue);
            case LONG -> Long.toString(longValue);
            case BIG_INTEGER -> bigValue.toString();
        };
    }

    public static SmartInt parse(String str) {
        if (str == null || str.trim().isEmpty())
            throw new NumberFormatException("Строка пустая или null");
        str = str.trim();

        boolean isNegative = false;
        int startIndex = 0;
        if (str.charAt(0) == '-') { isNegative = true; startIndex = 1; }
        else if (str.charAt(0) == '+') { startIndex = 1; }

        while (startIndex < str.length() - 1 && str.charAt(startIndex) == '0') startIndex++;

        String digits = str.substring(startIndex);
        if (digits.isEmpty())   // "+", "-" — не числа
            throw new NumberFormatException("Неверный формат числа: " + str);
        for (int i = 0; i < digits.length(); i++)
            if (!Character.isDigit(digits.charAt(i)))
                throw new NumberFormatException("Неверный формат числа: " + str);

        if (digits.equals("0")) return ZERO;

        if (digits.length() <= 18) {
            long val = Long.parseLong(digits);
            return new SmartInt(isNegative ? -val : val);
        }
        BigInteger big = new BigInteger(digits);
        return new SmartInt(isNegative ? big.negate() : big);
    }
}