package org.tihrc.microj.types.sequences;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.units.FastMap;
import org.tihrc.microj.units.Frame;

import java.util.List;
import java.util.Map;

public class PyGeneratorFunc extends PyObject implements Protocols.PyCallable {
    public final String name;
    public final List<Instruction> body;
    public final List<String> params;
    public final String starArg;
    public final String kwArg;
    public final Map<String, PyObject> closure;
    public final PyObject defaults;
    public final PyObject[] constants;

    public PyGeneratorFunc(String name, List<Instruction> body, List<String> params,
                           String starArg, String kwArg, Map<String, PyObject> closure,
                           PyObject defaults, PyObject[] constants) {
        this.name = name;
        this.body = body;
        this.params = params;
        this.starArg = starArg;
        this.kwArg = kwArg;
        this.closure = closure;
        this.defaults = defaults;
        this.constants = constants;
    }

    @Override
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        return pyDanderCallFast(ctx, args, kwargs.keySet().toArray(new String[0]), kwargs.values().toArray(new PyObject[0]));
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter vm, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        Map<String, PyObject> locals = new FastMap<>();
        int paramCount = params.size();

        for (int i = 0; i < paramCount; i++) {
            if (i < args.length) locals.put(params.get(i), args[i]);
        }

        if (kwNames != null && kwNames.length > 0) {
            for (int i = 0; i < kwNames.length; i++) {
                String name = kwNames[i];
                if (params.contains(name)) locals.put(name, kwValues[i]);
            }
        }

        if (defaults != null && defaults != PyNone.INSTANCE) {
            PyTuple defaultsTuple = (PyTuple) defaults;
            PyObject[] defaultVals = defaultsTuple.getInner();
            int defaultOffset = paramCount - defaultVals.length;
            for (int i = 0; i < defaultVals.length; i++) {
                String pName = params.get(defaultOffset + i);
                locals.putIfAbsent(pName, defaultVals[i]);
            }
        }

        for (String p : params) locals.putIfAbsent(p, PyNone.INSTANCE);

        if (starArg != null) {
            int extraCount = args.length > paramCount ? args.length - paramCount : 0;
            PyObject[] starArgs = new PyObject[extraCount];
            System.arraycopy(args, paramCount, starArgs, 0, extraCount);
            locals.put(starArg, new PyTuple(starArgs));
        }

        if (kwArg != null) {
            PyDict kwargsDict = new PyDict();
            if (kwNames != null && kwNames.length > 0) {
                for (int i = 0; i < kwNames.length; i++) {
                    String name = kwNames[i];
                    if (!params.contains(name)) {
                        kwargsDict.put(new PyString(name), kwValues[i]);
                    }
                }
            }
            locals.put(kwArg, kwargsDict);
        }

        Frame genFrame = vm.obtainFrame(body, constants, closure);
        genFrame.locals = locals;
        return new PyGenerator(name, genFrame);
    }

    @Override
    public String toString() { return "PyGeneratorFunction<" + name + ">"; }
    @Override
    public String pyDanderRepr() { return "<generator function " + name + ">"; }
}