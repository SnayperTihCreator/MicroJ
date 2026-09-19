package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.*;
import org.tihrc.microj.types.primitives.PyFloat;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.SmartFloat;

public class PyModuleTime extends PyModule {

    public PyModuleTime(Interpreter interpreter) {
        super("time");

        registerAttribute("time", new PyBuiltinFunction((ctx) ->
                new PyFloat(new SmartFloat(System.currentTimeMillis() / 1000.0))));

        registerAttribute("sleep", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) -> {
            double seconds = Transforms.fromPythonOrNull(arg, double.class);
            long ms = (long) (seconds * 1000);
            try { Thread.sleep(ms); } catch (InterruptedException ignored) { }
            return PyNone.INSTANCE;
        }));
    }
}
