package org.tihrc.microj.core;

import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.core.loaders.ResourceScriptLoader;
import org.tihrc.microj.core.loaders.ScriptLoader;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.primitives.PyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class RuntimeLibrary {
    private final Interpreter vm;
    private final StandardLibrary stl;
    private final PyModule builtins;

    private final List<ScriptLoader> loaders = new ArrayList<>();

    public RuntimeLibrary(Interpreter vm, StandardLibrary stl) {
        this.vm = vm;
        this.stl = stl;
        this.builtins = stl.getModule("builtins");
        this.loaders.add(new ResourceScriptLoader(this));
    }

    public void addLoader(ScriptLoader loader) {
        loaders.add(loader);
    }
    public void removeLoader(ScriptLoader loader) {
        loaders.remove(loader);
    }
    public void clearLoaders() {
        loaders.clear();
    }

    public PyModule getBuiltins() {
        return builtins;
    }
    public StandardLibrary getStdLib() {return stl;}

    public static class PyFileModule extends PyModule {
        public PyFileModule(String moduleName){
            super(moduleName);
        }

        public void importAttributesFromGlobals(Map<String, PyObject> context){
            for(var pair : context.entrySet())
                registerAttribute(pair.getKey(), pair.getValue());
        }
    }

    public PyModule resolveModule(String moduleName, RuntimeExecuter ctx) {
        PyString key = new PyString(moduleName);
        var modules = vm.state.modules;
        if (modules.containsKey(key))
            return (PyModule) modules.pyDanderGetItem(key);
        for (ScriptLoader loader : loaders) {
            try {
                PyModule module = loader.loadModule(moduleName, ctx);
                if (module != null) return module;
            } catch (PyUnwind e) {
                throw e;
            } catch (Exception e) {
                continue;
            }
        }
        return new Exceptions.PyModuleNotFoundError("No module named '" + moduleName + "'").raise();
    }

    public void registerScript(PyModule module) {
        vm.state.modules.put(new PyString(module.getName()), module);
    }
}
