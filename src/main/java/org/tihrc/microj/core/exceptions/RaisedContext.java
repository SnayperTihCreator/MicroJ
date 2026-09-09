package org.tihrc.microj.core.exceptions;

public class RaisedContext {
    public String functionName;
    public int line;

    public RaisedContext(String functionName, int line) {
        this.functionName = functionName;
        this.line = line;
    }

    @Override
    public String toString() {
        return "  File \"<script>\", line " + line + ", in " + functionName;
    }
}
