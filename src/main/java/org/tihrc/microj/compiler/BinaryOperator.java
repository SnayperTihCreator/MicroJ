package org.tihrc.microj.compiler;

import org.tihrc.microj.core.Capability;

public enum BinaryOperator {
    ADD("__add__"),
    SUB("__sub__"),
    MUL("__mul__"),
    DIV("__truediv__"),
    POW("__pow__"),
    EQ("__eq__"),
    NE("__ne__"),
    LT("__lt__"),
    LE("__le__"),
    GT("__gt__"),
    GE("__ge__");

    private final String dunderName;

    BinaryOperator(String dunderName) {
        this.dunderName = dunderName;
    }

    public String getDunderName() {
        return dunderName;
    }

    public Capability getRequiredCap() {
        return switch (this) {
            case ADD, SUB, MUL, DIV, POW -> Capability.NUMBER;
            default -> Capability.COMPARABLE;
        };
    }
}