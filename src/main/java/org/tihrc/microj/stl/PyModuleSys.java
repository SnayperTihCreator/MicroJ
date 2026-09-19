package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.core.exceptions.BaseExceptions;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.*;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.types.primitives.PyNone;

@SuppressWarnings("DataFlowIssue")
public class PyModuleSys extends PyModule {
    private String backend = "interpreter";

    public PyModuleSys(Interpreter interpreter) {
        super("sys");

        registerAttribute("modules", () -> interpreter.getLib().getModulesDict());
        registerAttribute("path", () -> interpreter.getLib().getSysPath());

        registerAttribute("exit", new PyBuiltinFunction(((PyBuiltinFunction.CallVarArgs)(ctx, args) -> {
            if (args.length > 1)
                return new Exceptions.PyTypeError("to many arguments").raise();
            int exitCode;
            if (args.length == 1) exitCode = Transforms.fromPython(args[0], int.class);
            else exitCode = 0;
            return new BaseExceptions.PySystemExit(exitCode).raise();
        })));

        registerAttribute("setrecursionlimit", new PyBuiltinFunction(((PyBuiltinFunction.Call1)(ctx, arg) -> {
            interpreter.MAX_RECURSION_DEPTH = Transforms.fromPython(arg, int.class);
            return PyNone.INSTANCE;
        })));

        registerAttribute("backend", () -> new PyString(backend));
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }
}
