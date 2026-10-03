package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.collections.PyList;
import org.tihrc.microj.units.FormatSpecs;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class PyString extends PyObject implements Protocols.PyNumber, Protocols.PyContainer, Protocols.PyComparable, Protocols.PyIterable {
    public final String value;

    public PyString(String value) {
        this.value = value;
    }

    @PyExport(name = "format", args = true, kwargs = true)
    public PyObject pyStringFormat(PyObject[] args, Map<String, PyObject> kwargs) {
        if (kwargs == null) kwargs = new HashMap<>();

        StringBuilder result = new StringBuilder();
        int autoIdx = 0;
        int i = 0;

        while (i < value.length()) {
            char c = value.charAt(i);
            if (c == '{') {
                if (i + 1 < value.length() && value.charAt(i + 1) == '{') {
                    result.append('{');
                    i += 2;
                    continue;
                }
                int end = value.indexOf('}', i);
                if (end == -1) {
                    result.append(c);
                    i++;
                    continue;
                }
                String field = value.substring(i + 1, end);
                String fieldName = field;
                String spec = "";
                int colon = field.indexOf(':');
                if (colon != -1) {
                    fieldName = field.substring(0, colon);
                    spec = field.substring(colon + 1);
                }

                PyObject val;
                if (fieldName.isEmpty()) {
                    if (autoIdx >= args.length) {
                        return new Exceptions.PyIndexError("tuple index out of range").raise();
                    }
                    val = args[autoIdx++];
                } else if (Character.isDigit(fieldName.charAt(0))) {
                    int idx = Integer.parseInt(fieldName);
                    if (idx >= args.length) {
                        return new Exceptions.PyIndexError("tuple index out of range").raise();
                    }
                    val = args[idx];
                } else {
                    val = kwargs.get(fieldName);
                }

                if (val == null) val = PyNone.INSTANCE;

                PyObject formatted = val.pyDanderFormat(spec);
                result.append(formatted.pyDanderStr());

                i = end + 1;
            } else if (c == '}') {
                if (i + 1 < value.length() && value.charAt(i + 1) == '}') {
                    result.append('}');
                    i += 2;
                    continue;
                }
                result.append(c);
                i++;
            } else {
                result.append(c);
                i++;
            }
        }
        return new PyString(result.toString());
    }

    @Override
    @PyExport(name = "__format__")
    public PyObject pyDanderFormat(String spec) {
        if (spec == null || spec.isEmpty()) return this;
        return new PyString(FormatSpecs.pyStringSpec(this.value, spec));
    }

    @PyExport(name = "upper")
    public PyObject pyStringUpper() {
        return new PyString(value.toUpperCase());
    }

    @PyExport(name = "lower")
    public PyObject pyStringLower() {
        return new PyString(value.toLowerCase());
    }
    @PyExport(name = "capitalize")
    public PyObject pyStringCapitalize() {
        if (value.isEmpty()) return new PyString("");
        return new PyString(value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase());
    }
    @PyExport(name = "title")
    public PyObject pyStringTitle() {
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = true;
        for (char c : value.toCharArray()) {
            if (Character.isWhitespace(c)) {
                sb.append(c);
                nextUpper = true;
            } else {
                sb.append(nextUpper ? Character.toUpperCase(c) : Character.toLowerCase(c));
                nextUpper = false;
            }
        }
        return new PyString(sb.toString());
    }
    @PyExport(name = "strip")
    public PyObject pyStringStrip() {
        return new PyString(value.trim());
    }
    @PyExport(name = "find")
    public PyObject pyStringFind(String sub) {
        return PyInt.from(value.indexOf(sub));
    }
    @PyExport(name = "startswith")
    public PyObject pyStringStartSwitch(String prefix) {
        return PyBool.from(value.startsWith(prefix));
    }
    @PyExport(name = "endswith")
    public PyObject pyStringEndSwitch(String suffix) {
        return PyBool.from(value.endsWith(suffix));
    }
    @PyExport(name = "count")
    public PyObject pyStringCount(String sub) {
        if (sub.isEmpty()) return PyInt.from(value.length() + 1);
        int count = 0, idx = 0;
        while ((idx = value.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return PyInt.from(count);
    }
    @PyExport(name = "replace")
    public PyObject pyStringReplace(String oldStr, String newStr) {
        return new PyString(value.replace(oldStr, newStr));
    }
    @SuppressWarnings("DataFlowIssue")
    @PyExport(name = "split")
    public PyObject pyStringSplit(PyObject sepObj) {
        String[] parts;
        if (sepObj == PyNone.INSTANCE) {
            parts = value.trim().split("\\s+");
        } else {
            String sep = Transforms.fromPython(sepObj, String.class);
            parts = value.split(Pattern.quote(sep));
        }
        PyObject[] pyParts = new PyObject[parts.length];
        for (int i = 0; i < parts.length; i++) {
            pyParts[i] = new PyString(parts[i]);
        }
        return new PyList(pyParts);
    }
    @PyExport(name = "isdigit")
    public PyObject pyStringIsDigit() {
        if (value.isEmpty()) return PyBool.FALSE;
        for (char c : value.toCharArray()) if (!Character.isDigit(c)) return PyBool.FALSE;
        return PyBool.TRUE;
    }
    @PyExport(name = "isalpha")
    public PyObject pyStringIsAlpha() {
        if (value.isEmpty()) return PyBool.FALSE;
        for (char c : value.toCharArray()) if (!Character.isLetter(c)) return PyBool.FALSE;
        return PyBool.TRUE;
    }

    @Override
    @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        if (other instanceof PyString s) return new PyString(this.value + s.value);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        if (other instanceof PyInt i) {
            int repeat = i.value.toInt();
            if (repeat <= 0) return new PyString("");
            return new PyString(this.value.repeat(repeat));
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override public PyObject pyDanderMod(PyObject object) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderPow(PyObject exp) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderFloorDiv(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderSub(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderTrueDiv(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override public PyObject pyDanderNeg() { return PyNotImplemented.INSTANCE; }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @PyExport(name = "__getitem__")
    public PyObject pyDanderGetItem(PyObject index) {
        int idx = Transforms.fromPython(index, int.class);
        if (idx < 0) idx += value.length();
        if (idx >= 0 && idx < value.length()) {
            return new PyString(String.valueOf(value.charAt(idx)));
        }
        return new Exceptions.PyIndexError("string index out of range").raise();
    }

    @Override
    @PyExport(name = "__setitem__")
    public void pyDanderSetItem(PyObject index, PyObject value) {
        new Exceptions.PyTypeError("'str' object does not support item assignment").raise();
    }

    @Override
    @PyExport(name = "__len__")
    public int pyDanderLen() { return value.length(); }

    @Override
    @PyExport(name = "__contains__")
    public PyObject pyDanderContains(PyObject item) {
        if (item instanceof PyString s) {
            return PyBool.from(this.value.contains(s.value));
        }
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() { return !value.isEmpty(); }

    @Override
    @PyExport(name = "__eq__")
    public PyObject pyDanderEq(PyObject other) {
        if (other instanceof PyString o) return PyBool.from(this.value.equals(o.value));
        return PyBool.FALSE;
    }

    @Override
    @PyExport(name = "__ne__")
    public PyObject pyDanderNe(PyObject other) {
        if (other instanceof PyString o) return PyBool.from(!this.value.equals(o.value));
        return PyBool.TRUE;
    }

    @Override
    @PyExport(name = "__lt__")
    public PyObject pyDanderLt(PyObject other) {
        if (other instanceof PyString o) return PyBool.from(this.value.compareTo(o.value) < 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__le__")
    public PyObject pyDanderLe(PyObject other) {
        if (other instanceof PyString o) return PyBool.from(this.value.compareTo(o.value) <= 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__gt__")
    public PyObject pyDanderGt(PyObject other) {
        if (other instanceof PyString o) return PyBool.from(this.value.compareTo(o.value) > 0);
        return PyNotImplemented.INSTANCE;
    }

    @Override
    @PyExport(name = "__ge__")
    public PyObject pyDanderGe(PyObject other) {
        if (other instanceof PyString o) return PyBool.from(this.value.compareTo(o.value) >= 0);
        return PyNotImplemented.INSTANCE;
    }

    public static final class PyStringIterator
            extends PyObject
            implements Protocols.PyIterator {

        private final String value;
        private int index;

        public PyStringIterator(String value) {
            this.value = value;
        }

        @Override
        public PyObject pyDanderNext() {
            if (index >= value.length())
                return new Exceptions.PyStopIteration().raise();

            return new PyString(String.valueOf(value.charAt(index++)));
        }

        @Override
        public String toString() {
            return "PyStringIterator";
        }
    }

    @Override
    @PyExport(name = "__iter__")
    public Protocols.PyIterator pyDanderIter() {
        return new PyStringIterator(value);
    }

    @Override
    public String toString() {
        return "PyString(\"" + value + "\")";
    }
    @Override
    public String pyDanderRepr() {
        return "'" + this.value + "'";
    }
    @Override
    public String pyDanderStr() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PyString o) return this.value.equals(o.value);
        return false;
    }

    @Override
    public int hashCode() { return this.value.hashCode(); }

}