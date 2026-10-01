package org.tihrc.microj.core.loaders;

import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.types.objects.PyModule;

public interface ScriptLoader {
    PyModule loadModule(String name, RuntimeExecuter runtime) throws Exception;
}
