package org.tihrc.microj.compiler;

public enum UnaryOperator {
    NEG("__neg__"),
    NOT(null);

    private final String dunderName;

    UnaryOperator(String dunderName) {
        this.dunderName = dunderName;
    }

    public String getDunderName() {
        return dunderName;
    }
}
