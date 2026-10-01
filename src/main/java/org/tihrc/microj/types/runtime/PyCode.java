package org.tihrc.microj.types.runtime;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.PyObject;

import java.util.List;

public class PyCode extends PyObject {
    public final List<Instruction> body;
    public final int[] lineTable;
    public PyCode(List<Instruction> body, int[] lineTable) {
        this.body = body;
        this.lineTable = lineTable;
    }

    @Override
    public String toString() {
        return "PyCode";
    }
}
