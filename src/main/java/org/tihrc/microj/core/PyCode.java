package org.tihrc.microj.core;

import org.tihrc.microj.compiler.Instruction;

import java.util.List;

public class PyCode extends PyObject{
    public final List<Instruction> body;
    public PyCode(List<Instruction> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "PyCode";
    }
}
