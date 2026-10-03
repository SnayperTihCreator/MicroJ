package org.tihrc.microj.core.exceptions;

public record RaisedContext(String filename, String functionName, int line) {

    @Override
    public String toString() {
        return "  File \"%s\", line %d, in %s".formatted(filename, line, functionName);
    }
}
