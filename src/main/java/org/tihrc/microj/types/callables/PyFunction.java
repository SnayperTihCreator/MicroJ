package org.tihrc.microj.types.callables;

import org.tihrc.microj.backend.jvm.JvmCompiler;
import org.tihrc.microj.backend.jvm.JvmHelder;
import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.units.Constants;
import org.tihrc.microj.units.Frame;
import org.tihrc.microj.units.FrameTask;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class PyFunction extends PyObject implements Protocols.PyCallable {
    public final String name;
    public final List<Instruction> body;
    public final List<String> params;
    public final Set<String> paramNames;
    public final String starArg;
    public final String kwArg;
    public final Map<String, PyObject> closure;
    public final PyObject defaults;
    public final PyObject[] constants;
    public final int[] lineTable;

    public PyFunction(String name, List<Instruction> body, List<String> params,
                      String starArg, String kwArg, Map<String, PyObject> closure,
                      PyObject defaults, PyObject[] constants, int[] lineTable) {
        this.name = name;
        this.body = body;
        this.params = params;
        this.starArg = starArg;
        this.kwArg = kwArg;
        this.paramNames = Set.copyOf(params);
        this.closure = closure;
        this.defaults = defaults;
        this.constants = constants;
        this.lineTable = lineTable;
    }

    private Frame bindFrame(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        Frame frame = ctx.obtainFrame(body, constants, closure);
        frame.functionName = name;
        frame.lineTable = lineTable;

        if (kwNames != null && kwArg == null)
            for (String kw : kwNames) if (!paramNames.contains(kw))
                    new Exceptions.PyTypeError(name + "() got an unexpected keyword argument '" + kw + "'").raise();

        if (args == null) args = Constants.NO_ARGS;

        PyObject[] slots = new PyObject[params.size() + (starArg != null ? 1 : 0) + (kwArg != null ? 1 : 0)];
        JvmHelder.bindArgs(ctx, slots, defaults, args, kwNames, kwValues,
                params.toArray(String[]::new), starArg, kwArg);

        for (int i = 0; i < params.size(); i++) frame.locals.put(params.get(i), slots[i]);
        int idx = params.size();
        if (starArg != null) frame.locals.put(starArg, slots[idx++]);
        if (kwArg != null)   frame.locals.put(kwArg,   slots[idx]);
        return frame;
    }

    private static String[] kwNamesOf(Map<String, PyObject> kwargs) {
        if (kwargs == null || kwargs.isEmpty()) return Constants.NO_KW_NAMES;
        String[] names = new String[kwargs.size()];
        int i = 0;
        for (var e : kwargs.entrySet()) names[i++] = e.getKey();
        return names;
    }
    private static PyObject[] kwValuesOf(Map<String, PyObject> kwargs) {
        if (kwargs == null || kwargs.isEmpty()) return Constants.NO_KW_VALUES;
        PyObject[] vals = new PyObject[kwargs.size()];
        int i = 0;
        for (var e : kwargs.entrySet()) vals[i++] = e.getValue();
        return vals;
    }

    public Frame createClosure() {
        Frame frame = Frame.createClosure(body, closure, constants);
        frame.functionName = name;
        frame.lineTable = lineTable;
        return frame;
    }

    private Frame prepareFrame(RuntimeExecuter ctx, PyObject[] args, Map<String, PyObject> kwargs) {
        Frame frame = ctx.obtainFrame(body, constants, closure);

        int paramCount = params.size();

        for (int i = 0; i < paramCount; i++)
            frame.locals.put(params.get(i), args != null && i < args.length ? args[i] : PyNone.INSTANCE);

        if (kwargs != null && !kwargs.isEmpty()) {
            for (var entry : kwargs.entrySet()) {
                String key = entry.getKey();
                if (!paramNames.contains(key))
                    return new Exceptions.PyTypeError("unexpected keyword argument '" + key + "'").raise();
                frame.locals.put(key, entry.getValue());
            }
        }

        return frame;
    }

    @Override
    public void pyDanderCall(RuntimeExecuter ctx, Consumer<PyObject> callback, PyObject[] args, Map<String, PyObject> kwargs) {
        ctx.pushTask(bindFrame(ctx, args, kwNamesOf(kwargs), kwValuesOf(kwargs)).createTask(callback));
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        return ctx.runFrameSync(bindFrame(ctx, args, kwNames, kwValues));
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx) {
        return ctx.runFrameSync(bindFrame(ctx, Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES));
    }

    @Override
    public PyObject pyDanderCallBoundFast(RuntimeExecuter ctx, PyObject self, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        PyObject[] combined = new PyObject[(args == null ? 0 : args.length) + 1];
        combined[0] = self;
        if (args != null) System.arraycopy(args, 0, combined, 1, args.length);
        return ctx.runFrameSync(bindFrame(ctx, combined, kwNames, kwValues));
    }

    @Override
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        Frame frame = bindFrame(ctx, args, kwNamesOf(kwargs), kwValuesOf(kwargs));
        int targetSize = ctx.getSizeTasks();
        FrameTask task = frame.createTask();
        ctx.pushTask(task);
        ctx.runUntil(targetSize);
        return task.result();
    }

    private Frame prepareFrameFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        Frame frame = ctx.obtainFrame(body, constants, closure);

        int paramCount = params.size();

        for (int i = 0; i < paramCount; i++) {
            frame.locals.put(
                    params.get(i),
                    i < args.length
                            ? args[i]
                            : PyNone.INSTANCE
            );
        }

        if (kwNames != null) {
            for (int i = 0; i < kwNames.length; i++) {
                String name = kwNames[i];

                if (!paramNames.contains(name)) {
                    return new Exceptions.PyTypeError(
                            "unexpected keyword argument '" + name + "'"
                    ).raise();
                }

                frame.locals.put(name, kwValues[i]);
            }
        }

        return frame;
    }

    @Override
    public String pyDanderRepr() {
        return "<function %s %s>".formatted(name, params);
    }

    @Override
    public String toString() {
        return "PyFunction<%s:%s>".formatted(name, params);
    }
}
