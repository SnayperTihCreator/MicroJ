package org.tihrc.microj.core;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.core.exceptions.*;
import org.tihrc.microj.types.PyModule;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.FastMap;
import org.tihrc.microj.units.Frame;
import org.tihrc.microj.units.FrameTask;

import java.util.*;

public class RuntimeExecuter {
    private final Interpreter interpreter;
    private final Map<String, PyObject> globals = new FastMap<>();
    private final Deque<FrameTask> frameTasks = new ArrayDeque<>();
    private final Deque<Frame> framePool = new ArrayDeque<>();

    public RuntimeExecuter(Interpreter interpreter){
        this.interpreter = interpreter;
    }

    public Interpreter getInterpreter() { return interpreter; }
    public Map<String, PyObject> getGlobals() { return globals; }
    public PyModule getBuiltins() { return this.interpreter.getLib().getBuiltins(); }

    public void pushTask(FrameTask task) {
        if (frameTasks.size() > interpreter.MAX_RECURSION_DEPTH)
            new Exceptions.PyRecursionError("maximum recursion depth exceeded").raise();
        frameTasks.push(task);
    }
    public FrameTask popTask() { return frameTasks.pop(); }
    public FrameTask getCurrentTask() { return frameTasks.peek(); }
    public int getSizeTasks() { return frameTasks.size(); }
    @SuppressWarnings("DataFlowIssue")
    public Frame getCurrentFrame() { return frameTasks.peek().frame(); }
    public boolean isEmpty() { return frameTasks.isEmpty(); }

    public Frame obtainFrame(List<Instruction> code, PyObject[] constants, Map<String, PyObject> closure) {
        Frame frame = framePool.pollFirst();
        if (frame == null) {
            frame = new Frame(code, constants);
            frame.closure = closure;
        } else {
            frame.reset(code, constants, closure);
        }
        return frame;
    }

    public PyObject run(InstructionGenerator.CompiledScript script) {
        pushTask(Frame.fromGlobals(script.code(), globals, script.constants()).createTask());
        try {
            executeTasks(0);
        } catch (PyUnwind e) {
            return raised(e.exception);
        }
        return PyNone.INSTANCE;
    }

    public PyObject run(List<Instruction> code) {
        return run(new InstructionGenerator.CompiledScript(code, new PyObject[0]));
    }

    public PyObject callSync(PyObject func, PyObject... args) throws PyUnwind {
        return callSync(func, args, FastMap.empty());
    }

    public PyObject callSync(PyObject func, PyObject[] args, Map<String, PyObject> kwargs) throws PyUnwind {
        if (func instanceof Protocols.PyCallable callable){
            return callable.pyDanderCall(this, kwargs, args);
        }
        PyObject callDunder = func.findAttribute("__call__");
        if (callDunder != null)
            return callSync(callDunder, args, kwargs);
        return new Exceptions.PyTypeError("%s is not callable".formatted(func.pyDanderRepr())).raise();
    }

    public void runUntil(int targetSize) throws PyUnwind {
        this.executeTasks(targetSize);
    }

    public PyObject runFrameSync(Frame frame) throws PyUnwind {
        int targetSize = frameTasks.size();

        FrameTask task = frame.createTask();
        pushTask(task);

        executeTasks(targetSize);

        return task.result();
    }

    public long runInstructions(InstructionGenerator.CompiledScript script) throws PyUnwind {
        pushTask(new FrameTask(
                Frame.fromGlobals(
                        script.code(),
                        globals,
                        script.constants()
                )
        ));

        return executeTasks(0);
    }

    private long executeTasks(int targetSize) throws PyUnwind {
        long executedInstructions = 0;
        while (!frameTasks.isEmpty() && frameTasks.size() > targetSize) {
            FrameTask task = frameTasks.peek();
            Frame frame = task.frame();

            if (frame.isFinished() || task.yielded()) {
                popTask();
                if (task.yielded()) {
                    continue;
                }
                if (task.callback() != null) task.callback().accept(PyNone.INSTANCE);
                else if (!isEmpty()) getCurrentFrame().stack.push(PyNone.INSTANCE);

                frame.clean();
                framePool.offerFirst(frame);
                continue;
            }

            Instruction inst = frame.get();
            executedInstructions++;
            try {
                boolean advancePc = inst.execute(frame, this);
                if (advancePc) {
                    frame.pc++;
                }
            } catch (PyUnwind e) {
                PyBaseException pyExc = e.exception;

                if (pyExc instanceof BaseExceptions.PySystemExit) {
                    throw e;
                }

                if (frame.tryHandlers != null && !frame.tryHandlers.isEmpty()) {
                    int[] handler = frame.tryHandlers.pop();
                    frame.stack.clear();
                    frame.stack.push(pyExc);
                    frame.pc = handler[0];
                    continue;
                }
                if (task.onFailure() != null) {
                    task.onFailure().accept(pyExc);
                    continue;
                }
                throw e;
            }
        }
        return executedInstructions;
    }

    private PyObject raised(PyBaseException e) {
        if (e instanceof BaseExceptions.PySystemExit systemExit) {
            if (systemExit.getExitCode() == 0)
                return PyNone.INSTANCE;
        }

        e.setContext(new RaisedContext("module", -1));
        System.err.println("Traceback (most recent call last):");
        System.err.println(e.getContext());
        System.err.println(e.getErrorType() + ": " + e.getMessage());
        return e;
    }
}
