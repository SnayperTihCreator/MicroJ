package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.callables.PyBoundMethod;
import org.tihrc.microj.types.callables.PyBuiltinFunction;
import org.tihrc.microj.types.callables.PyFunction;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.core.PyNone;

public class PyModuleDis extends PyModule {
    public PyModuleDis(Interpreter interpreter) {
        super("dis");

        registerAttribute("dis", new PyBuiltinFunction(((PyBuiltinFunction.Call1)(ctx, arg) -> {
            if (arg instanceof PyBoundMethod method) arg = method.getFunc();
            if (arg instanceof PyFunction func){
                for (int i = 0; i < func.body.size(); i++) System.out.println(i + ": " + func.body.get(i));
                return PyNone.INSTANCE;
            }
            return new Exceptions.PyValueError("not dis function, got " + arg.getClass().getSimpleName()).raise();
        })));
    }
}
