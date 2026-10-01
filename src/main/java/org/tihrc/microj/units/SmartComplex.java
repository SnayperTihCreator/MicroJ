package org.tihrc.microj.units;

import java.util.Objects;

public class SmartComplex {
    public static final SmartComplex ZERO = new SmartComplex(SmartFloat.ZERO, SmartFloat.ZERO);
    public static final SmartComplex ONE  = new SmartComplex(SmartFloat.ONE, SmartFloat.ZERO);
    public static final SmartComplex I    = new SmartComplex(SmartFloat.ZERO, SmartFloat.ONE);

    private final SmartFloat real;
    private final SmartFloat imag;

    public SmartComplex(SmartFloat real, SmartFloat imag) {
        this.real = Objects.requireNonNull(real, "real");
        this.imag = Objects.requireNonNull(imag, "imag");
    }

    public SmartComplex(double real, double imag) {
        this(new SmartFloat(real), new SmartFloat(imag));
    }

    public static SmartComplex ofReal(SmartFloat re) { return new SmartComplex(re, SmartFloat.ZERO); }
    public static SmartComplex ofReal(SmartInt re)   { return new SmartComplex(re.toSmartFloat(), SmartFloat.ZERO); }
    public static SmartComplex ofReal(double re)     { return new SmartComplex(re, 0.0); }

    public boolean isZero() { return real.isZero() && imag.isZero(); }
    public boolean isReal() { return imag.isZero(); }

    public SmartComplex add(SmartComplex o) {
        return new SmartComplex(real.add(o.real), imag.add(o.imag));
    }

    public SmartComplex sub(SmartComplex o) {
        return new SmartComplex(real.sub(o.real), imag.sub(o.imag));
    }

    public SmartComplex mul(SmartComplex o) {
        SmartFloat ac = real.mul(o.real);
        SmartFloat bd = imag.mul(o.imag);
        SmartFloat ad = real.mul(o.imag);
        SmartFloat bc = imag.mul(o.real);
        return new SmartComplex(ac.sub(bd), ad.add(bc));
    }

    public SmartComplex div(SmartComplex o) {
        if (o.isZero())
            throw new RuntimeException("ZeroDivisionError: complex division by zero");
        SmartFloat a = this.real, b = this.imag, c = o.real, d = o.imag;

        if (Math.abs(c.toDouble()) >= Math.abs(d.toDouble())) {
            SmartFloat ratio = d.div(c);
            SmartFloat denom = c.add(d.mul(ratio));       // == (c² + d²)/c, не переполняется
            return new SmartComplex(a.add(b.mul(ratio)).div(denom),
                    b.sub(a.mul(ratio)).div(denom));
        } else {
            SmartFloat ratio = c.div(d);
            SmartFloat denom = c.mul(ratio).add(d);
            return new SmartComplex(a.mul(ratio).add(b).div(denom),
                    b.mul(ratio).sub(a).div(denom));
        }
    }

    public SmartComplex add(SmartFloat o)    { return add(ofReal(o)); }
    public SmartComplex sub(SmartFloat o)    { return sub(ofReal(o)); }
    public SmartComplex mul(SmartFloat o)    { return mul(ofReal(o)); }
    public SmartComplex div(SmartFloat o)    { return div(ofReal(o)); }

    public SmartComplex add(SmartInt o)      { return add(ofReal(o)); }
    public SmartComplex sub(SmartInt o)      { return sub(ofReal(o)); }
    public SmartComplex mul(SmartInt o)      { return mul(ofReal(o)); }
    public SmartComplex div(SmartInt o)      { return div(ofReal(o)); }

    public SmartComplex add(SmartNumber o)   { return add(o.toSmartFloat()); }
    public SmartComplex sub(SmartNumber o)   { return sub(o.toSmartFloat()); }
    public SmartComplex mul(SmartNumber o)   { return mul(o.toSmartFloat()); }
    public SmartComplex div(SmartNumber o)   { return div(o.toSmartFloat()); }

    public SmartComplex negate()    { return new SmartComplex(real.negate(), imag.negate()); }
    public SmartComplex conjugate() { return new SmartComplex(real, imag.negate()); }

    public SmartFloat abs() {
        return new SmartFloat(Math.hypot(real.toDouble(), imag.toDouble()));
    }

    public SmartFloat getReal() { return real; }
    public SmartFloat getImag() { return imag; }
    public double getRealAsDouble() { return real.toDouble(); }
    public double getImagAsDouble() { return imag.toDouble(); }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof SmartComplex o) return real.equals(o.real) && imag.equals(o.imag);
        if (obj instanceof SmartNumber n && isReal()) return real.equals(n);
        return false;
    }

    @Override
    public int hashCode() {
        if (isReal()) return real.hashCode();
        return 31 * real.hashCode() + imag.hashCode();
    }

    @Override
    public String toString() {
        boolean reZero = real.isZero();
        boolean imZero = imag.isZero();

        if (reZero && imZero) return "0";
        if (reZero) return imag + "i";
        if (imZero) return real.toString();

        boolean imNegative = !Double.isNaN(imag.toDouble()) && imag.isNegative();
        return imNegative ? real + " - " + imag.negate() + "i" : real + " + " + imag + "i";
    }

    public String getStorageType() {
        return "Real: " + real.getStorageType() + ", Imag: " + imag.getStorageType();
    }

    public static SmartComplex parse(String str) {
        if (str == null || str.trim().isEmpty())
            throw new NumberFormatException("Строка пустая или null");
        String src = str;
        str = str.replaceAll("\\s+", "");

        boolean isImaginary = false;
        char last = Character.toUpperCase(str.charAt(str.length() - 1));
        if (last == 'I' || last == 'J') {
            isImaginary = true;
            str = str.substring(0, str.length() - 1);
        }

        if (str.isEmpty() || str.equals("+")) return new SmartComplex(0, 1);
        if (str.equals("-"))                  return new SmartComplex(0, -1);

        int splitIndex = -1;
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c == '+' || c == '-') && Character.isDigit(str.charAt(i - 1))) {
                splitIndex = i;
                break;
            }
        }

        if (splitIndex != -1) {
            if (!isImaginary)
                throw new NumberFormatException("Неверный формат комплексного числа: " + src);
            String realStr = str.substring(0, splitIndex);
            String imagStr = str.substring(splitIndex);
            if (imagStr.equals("+")) imagStr = "1";
            else if (imagStr.equals("-")) imagStr = "-1";
            return new SmartComplex(SmartFloat.parse(realStr), SmartFloat.parse(imagStr));
        }

        SmartFloat val = SmartFloat.parse(str);
        return isImaginary ? new SmartComplex(SmartFloat.ZERO, val) : new SmartComplex(val, SmartFloat.ZERO);
    }

    public static SmartComplex parse(String realStr, String imagStr) {
        return new SmartComplex(SmartFloat.parse(realStr), SmartFloat.parse(imagStr));
    }
}