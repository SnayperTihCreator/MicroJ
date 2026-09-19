package org.tihrc.microj.units;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.PyBaseException;

import java.util.*;
import java.util.function.Consumer;

public class Frame {
    public List<Instruction> code;
    public int pc = 0;

    public FastStack stack = new FastStack(8);
    public Map<String, PyObject> locals = new FastMap<>();
    public Map<String, PyObject> closure = null;
    public Deque<int[]> tryHandlers = null;
    public PyObject[] constants;

    private static final PyObject[] NO_CONSTANTS = new PyObject[0];

    public void pushTryHandler(int target, int prevPc) {
        if (tryHandlers == null) tryHandlers = new ArrayDeque<>();
        tryHandlers.push(new int[]{target, prevPc});
    }

    public Frame(List<Instruction> code, PyObject[] constants) {
        this.code = code;
        this.constants = constants;
    }

    public Frame(List<Instruction> code) {
        this(code, NO_CONSTANTS);
    }

    public Frame(List<Instruction> code, Map<String, PyObject> locals) {
        this(code, NO_CONSTANTS);
        this.locals = locals;
    }

    public static Frame createClosure(List<Instruction> code, Map<String, PyObject> closure, PyObject[] constants) {
        Frame frame = new Frame(code, constants);
        frame.closure = closure;
        return frame;
    }

    public static Frame fromGlobals(List<Instruction> code, Map<String, PyObject> globals, PyObject[] constants) {
        Frame frame = new Frame(code, constants);
        frame.locals = globals;
        return frame;
    }

    public void reset(List<Instruction> code, PyObject[] constants, Map<String, PyObject> closure) {
        this.code = code;
        this.constants = constants;
        this.closure = closure;
        this.pc = 0;
        this.stack.clear();
        this.locals.clear();
        if (this.tryHandlers != null) this.tryHandlers.clear();
    }

    public void clean() {
        this.code = null;
        this.constants = null;
        this.closure = null;
        this.pc = 0;
        this.stack.clear();
        this.locals.clear();
        if (this.tryHandlers != null) this.tryHandlers.clear();
    }

    public FrameTask createTask() {
        return new FrameTask(this);
    }

    public FrameTask createTask(Consumer<PyObject> callback) {
        return new FrameTask(this, callback);
    }
    public FrameTask createTask(Consumer<PyObject> callback, Consumer<PyBaseException> errorHandler) {
        return new FrameTask(this, callback, errorHandler);
    }

    public boolean isFinished() {
        return pc >= code.size();
    }

    public Instruction get(){
        return code.get(pc);
    }
}
