package org.tihrc.microj.types.primitives;

import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.SmartInt;

public class PyBool extends PyInt {
    public static final PyBool TRUE = new PyBool(true);
    public static final PyBool FALSE = new PyBool(false);
    public final boolean boolValue;

    private PyBool(boolean value) {
        super((SmartInt) SmartInt.from(value ? 1 : 0));
        this.boolValue = value;
    }

    @Override
    @PyExport(name = "__bool__")
    public boolean pyDanderBool() { return boolValue; }

    public static PyBool from(boolean val) { return val ? TRUE : FALSE; }

    @Override public String pyDanderRepr() { return boolValue ? "True" : "False"; }
    @Override public String toString() { return "PyBool(%s)".formatted(boolValue); }
}