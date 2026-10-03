package org.tihrc.microj.units;

import org.tihrc.microj.core.exceptions.Exceptions;

import java.math.BigInteger;

public final class FormatSpecs {
    private FormatSpecs() {}

    public static String pyStringSpec(String strVal, String spec) {
        char fill = ' ';
        char align;
        int width;
        try {
            if (spec.length() >= 2 && isAlign(spec.charAt(1))) {
                fill = spec.charAt(0); align = spec.charAt(1);
                width = Integer.parseInt(spec.substring(2));
            } else if (!spec.isEmpty() && isAlign(spec.charAt(0))) {
                align = spec.charAt(0);
                width = Integer.parseInt(spec.substring(1));
            } else {
                width = Integer.parseInt(spec);
                align = '>';
            }
        } catch (NumberFormatException e) {
            return strVal;
        }
        return applyAlign("", strVal, width, false, align, fill);
    }

    public static String pyIntSpec(SmartInt v, String spec) {
        char type = 'd';
        int width = 0;
        boolean zeroPad = false;
        char align = '>';

        if (!spec.isEmpty()) {
            char last = spec.charAt(spec.length() - 1);
            if ("dbcoxXcn".indexOf(last) >= 0) { type = last; spec = spec.substring(0, spec.length() - 1); }
        }
        if (spec.startsWith("0") && spec.length() > 1) { zeroPad = true; spec = spec.substring(1); }
        if (!spec.isEmpty() && isAlign(spec.charAt(0))) { align = spec.charAt(0); spec = spec.substring(1); }
        try { if (!spec.isEmpty()) width = Integer.parseInt(spec); }
        catch (NumberFormatException e) { return v.toString(); }

        String sign = v.signum() < 0 ? "-" : "";
        BigInteger abs = v.toBigInteger().abs();

        String numStr = switch (type) {
            case 'b' -> abs.toString(2);
            case 'o' -> abs.toString(8);
            case 'x' -> abs.toString(16);
            case 'X' -> abs.toString(16).toUpperCase();
            case 'c' -> {
                if (v.signum() < 0 || v.compareTo(SmartInt.from(0x10FFFF)) > 0) yield null;
                yield String.valueOf((char) v.toInt());
            }
            default -> abs.toString();
        };

        if (numStr == null)
            return new Exceptions.PyValueError("'c' format requires 0 <= number <= 0x10FFFF").raise();

        return applyAlign(sign, numStr, width, zeroPad, align, '0');
    }

    public static String pyFloatSpec(SmartFloat v, String spec) {
        double number = v.toDouble();
        char type = 'g';
        int width = 0;
        int precision = 6;
        boolean zeroPad = false;
        boolean signAlways = false;
        char align = '>';

        if (!spec.isEmpty()) {
            char last = spec.charAt(spec.length() - 1);
            if ("eEfFgG%".indexOf(last) >= 0) { type = last; spec = spec.substring(0, spec.length() - 1); }
        }
        if (spec.startsWith("+")) { signAlways = true; spec = spec.substring(1); }
        if (spec.startsWith("0") && spec.length() > 1) { zeroPad = true; spec = spec.substring(1); }
        if (!spec.isEmpty() && isAlign(spec.charAt(0))) { align = spec.charAt(0); spec = spec.substring(1); }

        int dotIdx = spec.indexOf('.');
        try {
            if (dotIdx != -1) {
                if (dotIdx > 0) width = Integer.parseInt(spec.substring(0, dotIdx));
                precision = Integer.parseInt(spec.substring(dotIdx + 1));
            } else if (!spec.isEmpty()) width = Integer.parseInt(spec);
        } catch (NumberFormatException e) {
            return String.valueOf(number);
        }

        String javaFormat = "%." + precision + (type == '%' ? "f%%" : type);
        String numStr = String.format(java.util.Locale.US, javaFormat, number);

        String sign = "";
        if (numStr.startsWith("-")) { sign = "-"; numStr = numStr.substring(1); }
        else if (signAlways) sign = "+";

        return applyAlign(sign, numStr, width, zeroPad, align, '0');
    }

    private static boolean isAlign(char c) { return c == '<' || c == '>' || c == '^'; }

    private static String applyAlign(String sign, String numStr, int width,
                                     boolean zeroPad, char align, char fill) {
        String full = sign + numStr;
        if (full.length() >= width) return full;
        int pad = width - full.length();

        if (zeroPad)
            return sign + "0".repeat(pad) + numStr;

        StringBuilder sb = new StringBuilder();
        switch (align) {
            case '<' -> { sb.append(full); sb.append(String.valueOf(fill).repeat(pad)); }
            case '^' -> {
                int left = pad / 2;
                sb.append(String.valueOf(fill).repeat(left));
                sb.append(full);
                sb.append(String.valueOf(fill).repeat(pad - left));
            }
            default  -> { sb.append(String.valueOf(fill).repeat(pad)); sb.append(full); }
        }
        return sb.toString();
    }
}
