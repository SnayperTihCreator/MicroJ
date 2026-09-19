package org.tihrc.microj.types.collections;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.Frame;
import org.tihrc.microj.units.FrameTask;

public class PyGenerator extends PyObject implements Protocols.PyIterator, Protocols.PyIterable {
    public final String name;
    private Frame frame;
    private final RuntimeExecuter ctx;

    public PyGenerator(String name, Frame frame, RuntimeExecuter ctx) {
        this.name = name;
        this.frame = frame;
        this.ctx = ctx;
    }

    @Override
    @PyExport(name = "__iter__")
    public Protocols.PyIterator pyDanderIter() {
        return this;
    }

    @Override
    @PyExport(name = "__next__")
    public PyObject pyDanderNext() {
        if (frame == null)
            return new Exceptions.PyStopIteration().raise();

        FrameTask task = frame.createTask();
        ctx.pushTask(task);

        try {
            ctx.runUntil(ctx.getSizeTasks() - 1);
        } catch (PyUnwind e) {
            if (e.exception instanceof Exceptions.PyStopIteration) {
                frame = null;
                return new Exceptions.PyStopIteration().raise();
            }
            throw e;
        }

        if (task.yielded()) return task.result();
        else {
            frame = null;
            return new Exceptions.PyStopIteration().raise();
        }
    }

    @Override
    public String toString() { return "PyGenerator<%s>".formatted(name); }
    @Override
    public String pyDanderRepr() { return "<generator %s>".formatted(name); }
}
