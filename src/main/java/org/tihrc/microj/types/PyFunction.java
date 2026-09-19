package org.tihrc.microj.types;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.types.primitives.PyNone;
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
    public final Map<String, PyObject> closure;
    public final PyObject[] constants;

    public PyFunction(String name, List<Instruction> body, List<String> params,  Map<String, PyObject> closure, PyObject[] constants) {
        this.name = name;
        this.body = body;
        this.params = params;
        this.paramNames = Set.copyOf(params);
        this.closure = closure;
        this.constants = constants;
    }

    public Frame createClosure() {
        return Frame.createClosure(body, closure, constants);
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
        Frame frame = prepareFrame(ctx, args, kwargs);
        ctx.pushTask(frame.createTask(callback));
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        Frame frame = prepareFrameFast(ctx, args, kwNames, kwValues);
        return ctx.runFrameSync(frame);
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx) {
        return ctx.runFrameSync(createClosure());
    }

    @Override
    public PyObject pyDanderCallBoundFast(RuntimeExecuter ctx, PyObject self, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        Frame frame = createClosure();

        if (!params.isEmpty()) {
            frame.locals.put(params.getFirst(), self);
        }

        for (int i = 0; i < args.length; i++) {
            int paramIndex = i + 1;

            if (paramIndex < params.size()) {
                frame.locals.put(params.get(paramIndex), args[i]);
            }
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
        return ctx.runFrameSync(frame);

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
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        Frame newFrame = prepareFrame(ctx, args, kwargs);

        int targetSize = ctx.getSizeTasks();
        FrameTask task = newFrame.createTask();
        ctx.pushTask(task);
        ctx.runUntil(targetSize);
        return task.result();
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
