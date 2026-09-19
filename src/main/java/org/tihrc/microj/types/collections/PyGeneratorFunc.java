package org.tihrc.microj.types.collections;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.Frame;

import java.util.List;
import java.util.Map;

public class PyGeneratorFunc extends PyObject implements org.tihrc.microj.core.Protocols.PyCallable {
    public final String name;
    public final List<Instruction> body;
    public final List<String> params;
    public final PyObject[] constants;

    public PyGeneratorFunc(String name, List<Instruction> body, List<String> params, PyObject[] constants) {
        this.name = name;
        this.body = body;
        this.params = params;
        this.constants = constants;
    }

    @Override
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        Frame genFrame = ctx.obtainFrame(body, constants, null);
        return new PyGenerator(name, genFrame, ctx);
    }

    @Override
    public String toString() { return "PyGeneratorFunction<" + name + ">"; }
    @Override
    public String pyDanderRepr() { return "<generator function " + name + ">"; }
}