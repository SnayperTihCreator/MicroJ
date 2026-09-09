package org.tihrc.microj.units;

public class SmartComplex {
    private final SmartFloat real;
    private final SmartFloat imag;

    public SmartComplex(SmartFloat real, SmartFloat imag) {
        this.real = real;
        this.imag = imag;
    }

    public SmartComplex(double real, double imag) {
        this.real = new SmartFloat(real);
        this.imag = new SmartFloat(imag);
    }

    public static SmartComplex parse(String realStr, String imagStr) {
        return new SmartComplex(SmartFloat.parse(realStr), SmartFloat.parse(imagStr));
    }

    public SmartComplex add(SmartComplex other) {
        return new SmartComplex(
                this.real.add(other.real),
                this.imag.add(other.imag)
        );
    }

    // Вычитание: (a + bi) - (c + di) = (a-c) + (b-d)i
    public SmartComplex subtract(SmartComplex other) {
        return new SmartComplex(
                this.real.subtract(other.real),
                this.imag.subtract(other.imag)
        );
    }

    // Умножение: (a + bi) * (c + di) = (ac - bd) + (ad + bc)i
    public SmartComplex multiply(SmartComplex other) {
        SmartFloat ac = this.real.multiply(other.real);
        SmartFloat bd = this.imag.multiply(other.imag);
        SmartFloat ad = this.real.multiply(other.imag);
        SmartFloat bc = this.imag.multiply(other.real);

        SmartFloat newReal = ac.subtract(bd);
        SmartFloat newImag = ad.add(bc);

        return new SmartComplex(newReal, newImag);
    }

    public SmartComplex divide(SmartComplex other) {
        SmartFloat c = other.real;
        SmartFloat d = other.imag;

        SmartFloat denom = c.multiply(c).add(d.multiply(d)); // c^2 + d^2
        if (denom.toDouble() == 0.0) {
            throw new RuntimeException("ZeroDivisionError: complex division by zero");
        }

        SmartFloat ac = this.real.multiply(c);
        SmartFloat bd = this.imag.multiply(d);
        SmartFloat bc = this.imag.multiply(c);
        SmartFloat ad = this.real.multiply(d);

        SmartFloat newReal = ac.add(bd).divide(denom);
        SmartFloat newImag = bc.subtract(ad).divide(denom);

        return new SmartComplex(newReal, newImag);
    }

    public boolean equals(SmartComplex other) {
        return this.real.equals(other.real) && this.imag.equals(other.imag);
    }

    public SmartFloat getReal() { return real; }
    public SmartFloat getImag() { return imag; }

    public double getRealAsDouble() { return real.toDouble(); }
    public double getImagAsDouble() { return imag.toDouble(); }

    @Override
    public String toString() {
        boolean realIsZero = real.toString().equals("0.0") || real.toString().equals("0");
        boolean imagIsZero = imag.toString().equals("0.0") || imag.toString().equals("0");

        if (realIsZero && imagIsZero) return "0";
        if (realIsZero) return imag + "i";
        if (imagIsZero) return real.toString();

        if (imag.isNegative()) {
            return real + " - " + imag.toString().substring(1) + "i";
        } else {
            return real + " + " + imag + "i";
        }
    }

    public String getStorageType() {
        return "Real: " + real.getStorageType() + ", Imag: " + imag.getStorageType();
    }

    public static SmartComplex parse(String str) {
        if (str == null || str.trim().isEmpty()) {
            return new SmartComplex(0, 0);
        }

        str = str.replaceAll("\\s+", "");

        boolean isImaginary = str.endsWith("i") || str.endsWith("j");
        if (isImaginary) {
            str = str.substring(0, str.length() - 1);
        }

        if (str.isEmpty() || str.equals("+")) {
            return new SmartComplex(0, 1);
        }
        if (str.equals("-")) {
            return new SmartComplex(0, -1);
        }

        int splitIndex = -1;

        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            char prev = str.charAt(i - 1);
            if ((c == '+' || c == '-') && Character.isDigit(prev)) {
                splitIndex = i;
                break;
            }
        }

        if (splitIndex != -1) {
            String realStr = str.substring(0, splitIndex);
            String imagStr = str.substring(splitIndex);

            if (imagStr.equals("+")) imagStr = "1";
            else if (imagStr.equals("-")) imagStr = "-1";

            return new SmartComplex(SmartFloat.parse(realStr), SmartFloat.parse(imagStr));
        } else {
            SmartFloat val = SmartFloat.parse(str);
            if (isImaginary) {
                return new SmartComplex(new SmartFloat(0.0), val);
            } else {
                return new SmartComplex(val, new SmartFloat(0.0));
            }
        }
    }
}
