package org.tihrc.microj.units;

public final class FormatSpecs {
    private FormatSpecs() {}

    public static String pyStringSpec(String strVal, String spec) {
        char fill = ' ';
        char align;
        int width;

        try {
            if (spec.length() >= 2 && (spec.charAt(1) == '<' || spec.charAt(1) == '>' || spec.charAt(1) == '^')) {
                fill = spec.charAt(0);
                align = spec.charAt(1);
                width = Integer.parseInt(spec.substring(2));
            } else if (!spec.isEmpty() && (spec.charAt(0) == '<' || spec.charAt(0) == '>' || spec.charAt(0) == '^')) {
                align = spec.charAt(0);
                width = Integer.parseInt(spec.substring(1));
            } else if (spec.length() >= 2 && spec.charAt(0) == '0' && Character.isDigit(spec.charAt(1))) {
                fill = '0';
                align = '>';
                width = Integer.parseInt(spec.substring(1));
            } else {
                width = Integer.parseInt(spec);
                align = '>';
            }
        } catch (NumberFormatException e) {
            return strVal; // Пока игнорируем сложные спеки (типа .2f для строк)
        }

        if (strVal.length() >= width) return strVal;
        int padCount = width - strVal.length();
        StringBuilder sb = new StringBuilder();
        if (align == '>') {
            sb.append(String.valueOf(fill).repeat(padCount));
            sb.append(strVal);
        } else if (align == '<') {
            sb.append(strVal);
            sb.append(String.valueOf(fill).repeat(padCount));
        } else if (align == '^') {
            int left = padCount / 2;
            int right = padCount - left;
            sb.append(String.valueOf(fill).repeat(left));
            sb.append(strVal);
            sb.append(String.valueOf(fill).repeat(Math.max(0, right)));
        } else {
            return strVal;
        }
        return sb.toString();
    }

    public static String pyIntSpec(long number, String spec) {
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

    public static String pyFloatSpec(double number, String spec) {
        char type = 'g';
        int width = 0;
        int precision = 6;
        char fill = ' ';
        char align = '>';
        boolean zeroPad = false;
        boolean signAlways = false;

        if (!spec.isEmpty()) {
            char last = spec.charAt(spec.length() - 1);
            if (last == 'e' || last == 'E' || last == 'f' || last == 'F' || last == 'g' || last == 'G' || last == '%') {
                type = last;
                spec = spec.substring(0, spec.length() - 1);
            }
        }

        if (spec.startsWith("+")) {
            signAlways = true;
            spec = spec.substring(1);
        }

        if (spec.startsWith("0") && spec.length() > 1) {
            zeroPad = true;
            spec = spec.substring(1);
        }

        if (!spec.isEmpty() && (spec.charAt(0) == '<' || spec.charAt(0) == '>' || spec.charAt(0) == '^')) {
            align = spec.charAt(0);
            spec = spec.substring(1);
        }

        int dotIdx = spec.indexOf('.');
        try {
            if (dotIdx != -1) {
                if (dotIdx > 0) width = Integer.parseInt(spec.substring(0, dotIdx));
                precision = Integer.parseInt(spec.substring(dotIdx + 1));
            } else if (!spec.isEmpty()) {
                width = Integer.parseInt(spec);
            }
        } catch (NumberFormatException e) {
            return String.valueOf(number);
        }

        String javaFormat = "%." + precision + type;
        if (type == '%') javaFormat = "%." + precision + "f%%";

        String numStr = String.format(java.util.Locale.US, javaFormat, number);

        String sign = "";
        if (numStr.startsWith("-")) {
            sign = "-";
            numStr = numStr.substring(1);
        } else if (signAlways) {
            sign = "+";
        }

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
}
