package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.PyBoundMethod;
import org.tihrc.microj.types.PyBuiltinFunction;
import org.tihrc.microj.types.PyFunction;
import org.tihrc.microj.types.PyModule;
import org.tihrc.microj.types.primitives.PyNone;

public class PyModuleDis extends PyModule {
    public PyModuleDis(Interpreter interpreter) {
        super("dis");

        registerAttribute("dis", new PyBuiltinFunction(((PyBuiltinFunction.Call1)(ctx, arg) -> {
            if (arg instanceof PyBoundMethod method){
                arg = method.getFunc();
            }
            if (arg instanceof PyFunction func){
                for (int i = 0; i < func.body.size(); i++) {
                    System.out.println(i + ": " + func.body.get(i));
                }
                return PyNone.INSTANCE;
            }
            return new Exceptions.PyValueError("not dis function, got " + arg.getClass().getSimpleName()).raise();
        })));
    }
}
