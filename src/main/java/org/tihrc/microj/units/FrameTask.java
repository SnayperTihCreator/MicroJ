package org.tihrc.microj.units;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.PyBaseException;
import org.tihrc.microj.types.core.PyNone;

import java.util.function.Consumer;

public final class FrameTask {
    private final Frame frame;
    private final Consumer<PyObject> callback;
    private final Consumer<PyObject> onFailure;

    private boolean yielded = false;
    private PyObject result = PyNone.INSTANCE;

    public FrameTask(Frame frame) {
        this(frame, null, null);
    }

    public FrameTask(Frame frame, Consumer<PyObject> callback) {
        this(frame, callback, null);
    }

    public FrameTask(Frame frame, Consumer<PyObject> callback, Consumer<PyObject> onFailure) {
        this.frame = frame;
        this.callback = callback;
        this.onFailure = onFailure;
    }

    public Frame frame() {
        return frame;
    }

    public Consumer<PyObject> callback() {
        return callback;
    }
    public Consumer<PyObject> onFailure() {
        return onFailure;
    }

    public PyObject result() {
        return result;
    }
    public void result(PyObject result) {
        this.result = result;
    }

    public boolean yielded() { return yielded; }
    public void yielded(boolean v) { this.yielded = v; }
}