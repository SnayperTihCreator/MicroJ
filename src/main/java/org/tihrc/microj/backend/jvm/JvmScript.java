package org.tihrc.microj.backend.jvm;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;

public interface JvmScript {
    PyObject execute(RuntimeExecuter ctx);
}
