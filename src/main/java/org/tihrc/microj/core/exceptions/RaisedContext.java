package org.tihrc.microj.core.exceptions;

public class RaisedContext {
    public final String filename;
    public final String functionName;
    public final int line;

    public RaisedContext(String filename, String functionName, int line) {
        this.filename = filename;
        this.functionName = functionName;
        this.line = line;
    }

    @Override
    public String toString() {
        return "  File \"%s\", line %d, in %s".formatted(filename, line, functionName);
    }
}
