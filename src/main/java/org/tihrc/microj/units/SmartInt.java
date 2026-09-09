package org.tihrc.microj.units;

import java.math.BigInteger;
import java.util.Objects;

public class SmartInt implements Comparable<SmartInt> {
    private enum StorageType {INT, LONG, BIG_INTEGER}

    private final StorageType type;
    private int intValue;
    private long longValue;
    private BigInteger bigValue;

    public static final SmartInt ZERO = new SmartInt(0);
    public static final SmartInt ONE = new SmartInt(1);
    public static final SmartInt U8 = new SmartInt(256);

    public SmartInt(int value) {
        this.type = StorageType.INT;
        this.intValue = value;
    }

    public SmartInt(long value) {
        if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
            this.type = StorageType.INT;
            this.intValue = (int) value;
        } else {
            this.type = StorageType.LONG;
            this.longValue = value;
        }
    }

    public SmartInt(BigInteger value) {
        if (value.bitLength() <= 31) {
            this.type = StorageType.INT;
            this.intValue = value.intValue();
        } else if (value.bitLength() <= 63) {
            this.type = StorageType.LONG;
            this.longValue = value.longValue();
        } else {
            this.type = StorageType.BIG_INTEGER;
            this.bigValue = value;
        }
    }

    private boolean willLongAddOverflow(long a, long b) {
        if (b > 0 && a > Long.MAX_VALUE - b) return true;
        return b < 0 && a < Long.MIN_VALUE - b;
    }

    private boolean willLongMulOverflow(long a, long b) {
        if (a == 0 || b == 0) return false;
        if (a == Long.MIN_VALUE && b == -1) return true;
        if (a == -1 && b == Long.MIN_VALUE) return true;

        long result = a * b;
        return (result / b != a);
    }

    private boolean willLongSubOverflow(long a, long b) {
        if (b > 0 && a < Long.MIN_VALUE + b) return true;
        return b < 0 && a > Long.MAX_VALUE + b;
    }

    private boolean willLongDivOverflow(long a, long b) {
        return (a == Long.MIN_VALUE && b == -1);
    }

    public boolean isZero() {
        return switch (type) {
            case INT -> intValue == 0;
            case LONG -> longValue == 0L;
            case BIG_INTEGER -> bigValue.signum() == 0;
        };
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

        long res = (long) this.intValue + (long) other.intValue;
        if (res < Integer.MIN_VALUE || res > Integer.MAX_VALUE) {
            return new SmartInt(res); // Апгрейд до LONG
        }
        return new SmartInt((int) res); // Остается INT
    }

    public SmartInt multiply(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER) {
            return new SmartInt(this.toBigInteger().multiply(other.toBigInteger()));
        }

        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            long a = this.toLong();
            long b = other.toLong();

            if (willLongMulOverflow(a, b)) {
                return new SmartInt(BigInteger.valueOf(a).multiply(BigInteger.valueOf(b)));
            }
            return new SmartInt(a * b);
        }

        long res = (long) this.intValue * (long) other.intValue;
        if (res < Integer.MIN_VALUE || res > Integer.MAX_VALUE) {
            return new SmartInt(res);
        }
        return new SmartInt((int) res);
    }

    public SmartInt subtract(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER) {
            return new SmartInt(this.toBigInteger().subtract(other.toBigInteger()));
        }

        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            long a = this.toLong();
            long b = other.toLong();

            if (willLongSubOverflow(a, b)) {
                return new SmartInt(BigInteger.valueOf(a).subtract(BigInteger.valueOf(b)));
            }
            return new SmartInt(a - b);
        }

        long res = (long) this.intValue - (long) other.intValue;
        if (res < Integer.MIN_VALUE || res > Integer.MAX_VALUE) {
            return new SmartInt(res); // Апгрейд до LONG
        }
        return new SmartInt((int) res); // Остается INT
    }

    public SmartInt divideInt(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER) {
            return new SmartInt(this.toBigInteger().divide(other.toBigInteger()));
        }

        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            long a = this.toLong();
            long b = other.toLong();

            if (willLongDivOverflow(a, b)) {
                return new SmartInt(BigInteger.valueOf(a).divide(BigInteger.valueOf(b)));
            }
            return new SmartInt(a / b);
        }

        long res = (long) this.intValue / (long) other.intValue;
        return new SmartInt((int) res);
    }

    public SmartFloat divide(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER) {
            return new SmartFloat(this.toBigInteger().divide(other.toBigInteger()).doubleValue());
        }

        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            long a = this.toLong();
            long b = other.toLong();

            if (willLongDivOverflow(a, b)) {
                return new SmartFloat(BigInteger.valueOf(a).divide(BigInteger.valueOf(b)).doubleValue());
            }
            return new SmartFloat((double) a / b);
        }

        double res = (double) this.intValue / (double) other.intValue;
        return new SmartFloat(res);
    }

    public boolean isInt() {
        return type == StorageType.INT;
    }

    public int toInt() {
        if (type == StorageType.INT) return intValue;
        return (int) toLong();
    }

    public long toLong() {
        if (type == StorageType.INT) return intValue;
        if (type == StorageType.LONG) return longValue;
        return bigValue.longValue();
    }

    public BigInteger toBigInteger() {
        if (type == StorageType.BIG_INTEGER) return bigValue;
        if (type == StorageType.LONG) return BigInteger.valueOf(longValue);
        return BigInteger.valueOf(intValue);
    }

    public double toDouble() {
        return switch (type) {
            case INT -> (double) intValue;
            case LONG -> (double) longValue;
            case BIG_INTEGER -> bigValue.doubleValue();
        };
    }

    @Override
    public int compareTo(SmartInt other) {
        if (this.type == StorageType.BIG_INTEGER || other.type == StorageType.BIG_INTEGER) {
            return this.toBigInteger().compareTo(other.toBigInteger());
        }
        if (this.type == StorageType.LONG || other.type == StorageType.LONG) {
            return Long.compare(this.toLong(), other.toLong());
        }
        return Integer.compare(this.intValue, other.intValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SmartInt other)) return false;
        return compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        return switch (type) {
            case INT -> Integer.hashCode(intValue);
            case LONG -> Long.hashCode(longValue);
            case BIG_INTEGER -> bigValue.hashCode();
        };
    }

    @Override
    public String toString() {
        return switch (type) {
            case INT -> String.valueOf(intValue);
            case LONG -> String.valueOf(longValue);
            case BIG_INTEGER -> bigValue.toString();
        };
    }

    public static SmartInt parse(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new NumberFormatException("Строка пустая или null");
        }
        str = str.trim();

        boolean isNegative = false;
        int startIndex = 0;
        if (str.charAt(0) == '-') {
            isNegative = true;
            startIndex = 1;
        } else if (str.charAt(0) == '+') {
            startIndex = 1;
        }

        while (startIndex < str.length() - 1 && str.charAt(startIndex) == '0') {
            startIndex++;
        }

        String digits = str.substring(startIndex);
        for (int i = 0; i < digits.length(); i++) {
            if (!Character.isDigit(digits.charAt(i))) {
                throw new NumberFormatException("Неверный формат числа: " + str);
            }
        }

        if (digits.isEmpty() || digits.equals("0")) {
            return new SmartInt(0);
        }

        int length = digits.length();
        if (length <= 18) {
            long val = Long.parseLong(digits);
            if (isNegative) val = -val;
            return new SmartInt(val);
        }

        BigInteger big = new BigInteger(digits);
        if (isNegative) {
            big = big.negate();
        }
        return new SmartInt(big);
    }

}
