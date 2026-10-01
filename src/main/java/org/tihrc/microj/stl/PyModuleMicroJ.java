package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.primitives.PyString;

public class PyModuleMicroJ extends PyModule {
    public PyModuleMicroJ(Interpreter interpreter) {
        super("microj");
        registerAttribute("backend", () -> new PyString(interpreter.state.backend));
    }
}
