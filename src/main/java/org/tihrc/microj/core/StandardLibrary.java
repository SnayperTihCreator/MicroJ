package org.tihrc.microj.core;

import org.tihrc.microj.stl.*;
import org.tihrc.microj.types.PyModule;
import org.tihrc.microj.units.FastMap;

import java.util.Map;
import java.util.Set;

public class StandardLibrary {
    private final Map<String, PyModule> modules = new FastMap<>();

    public StandardLibrary(Interpreter interpreter) {
        registerModule(new PyModuleBuiltins(interpreter));
        registerModule(new PyModuleMath(interpreter));
        registerModule(new PyModuleTime(interpreter));
        registerModule(new PyModuleSys(interpreter));
        registerModule(new PyModuleDis(interpreter));
    }

    private void registerModule(PyModule module) {
        modules.put(module.getName(), module);
    }

    public PyModule getModule(String moduleName) {
        return modules.get(moduleName);
    }

    public boolean isLoaded(String moduleName) {
        return modules.containsKey(moduleName);
    }

    public Set<String> getModuleNames() {
        return modules.keySet();
    }

    public Map<String, PyModule> getModules() {
        return modules;
    }
}
