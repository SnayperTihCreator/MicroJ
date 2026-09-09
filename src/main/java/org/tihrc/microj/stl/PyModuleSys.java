package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.core.exceptions.BaseExceptions;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.*;
import org.tihrc.microj.types.primitives.PyNone;

public class PyModuleSys extends PyModule {
    public PyModuleSys(Interpreter interpreter) {
        super("sys");

        registerAttribute("modules", () -> interpreter.getLib().getModulesDict());
        registerAttribute("path", () -> interpreter.getLib().getSysPath());

        registerAttribute("exit", new PyBuiltinFunction(((ctx, kwargs, args) -> {
            if (args.length > 1)
                return new Exceptions.PyTypeError("to many arguments").raise();
            int exitCode;
            if (args.length == 1) exitCode = Transforms.fromPython(args[0], int.class);
            else exitCode = 0;
            return new BaseExceptions.PySystemExit(exitCode).raise();
        })));

        registerAttribute("setrecursionlimit", new PyBuiltinFunction(((ctx, kwargs, args) -> {
            if (args.length > 1)
                return new Exceptions.PyTypeError("to many arguments").raise();
            interpreter.MAX_RECURSION_DEPTH = Transforms.fromPython(args[0], int.class);
            return PyNone.INSTANCE;
        })));
    }
}
