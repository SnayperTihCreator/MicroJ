package org.tihrc.microj.core;

import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.loaders.ResourceScriptLoader;
import org.tihrc.microj.core.loaders.ScriptLoader;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.PyBuiltinFunction;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.collections.PyList;
import org.tihrc.microj.types.PyModule;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.FastMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuntimeLibrary {
    private final StandardLibrary stl;
    private final PyModule builtins;

    private final SysModulesProxy modules;
    private final List<ScriptLoader> loaders = new ArrayList<>();
    private final PyList sysPath = PyList.from(new PyString("scripts/"));

    public RuntimeLibrary(StandardLibrary stl) {
        this.stl = stl;
        this.modules = new SysModulesProxy(stl.getModules());
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
    public PyList getSysPath() {
        return sysPath;
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

    protected static class SysModulesProxy extends PyDict {
        private final Map<String, PyModule> builtins;

        public SysModulesProxy(Map<String, PyModule> builtins) {
            super();
            this.builtins = builtins;
        }

        private static final Map<String, PyObject> attributes = new FastMap<>();
        static {
            attributes.put("get", new PyBuiltinFunction((ctx, kwargs, args) -> {
                SysModulesProxy self = Transforms.check(args[0], SysModulesProxy.class);
                PyObject key = args[1];
                PyObject def = args.length > 2 ? args[2] : PyNone.INSTANCE;
                PyObject val = self.map.get(key);
                if (val != null) return val;
                if (key instanceof PyString s) {
                    PyModule builtin = self.builtins.get(s.value);
                    if (builtin != null) return builtin;
                }
                return def;
            }));

            attributes.put("keys", new PyBuiltinFunction((ctx, kwargs, args) -> {
                SysModulesProxy self = Transforms.check(args[0], SysModulesProxy.class);
                List<PyObject> allKeys = new ArrayList<>(self.map.keySet());
                for (var entry : self.builtins.entrySet()) {
                    allKeys.add(new PyString(entry.getKey()));
                }
                return new PyList(allKeys.toArray(new PyObject[0]));
            }));
        }

        @Override
        public PyObject findAttribute(String name) {
            PyObject value = attributes.get(name);
            if (value != null) return value;
            return super.findAttribute(name);
        }

        @Override
        public PyObject pyDanderGetItem(PyObject key) {
            PyObject val = map.get(key);
            if (val != null) return val;

            if (key instanceof PyString s) {
                PyModule builtin = builtins.get(s.value);
                if (builtin != null) return builtin;
            }
            return new Exceptions.PyKeyError(key.toString()).raise();
        }

        @Override
        public boolean containsKey(PyObject key) {
            if (map.containsKey(key)) return true;
            if (key instanceof PyString s) return builtins.get(s.value) != null;
            return false;
        }

        @Override
        public int pyDanderLen() {
            return map.size() + builtins.size();
        }

        @Override
        public Protocols.PyIterator pyDanderIter() {
            List<PyObject> allKeys = new ArrayList<>(map.keySet());
            for (var entry : builtins.entrySet()) {
                allKeys.add(new PyString(entry.getKey()));
            }
            return new SysModulesIterator(allKeys);
        }

        public static class SysModulesIterator extends PyObject implements Protocols.PyIterator {
            private final List<PyObject> keys;
            private int idx = 0;

            public SysModulesIterator(List<PyObject> keys) {
                this.keys = keys;
            }

            @Override
            public PyObject pyDanderNext() {
                if (idx < keys.size()) return keys.get(idx++);
                return PyNone.INSTANCE;
            }

            @Override
            public String toString() {
                return "<sys.modules iterator>";
            }
        }

    }

    public PyDict getModulesDict() { return modules; }

    public PyModule resolveModule(String moduleName, RuntimeExecuter vm) {
        PyString key = new PyString(moduleName);
        if (modules.containsKey(key)) {
            return (PyModule) modules.pyDanderGetItem(key);
        }
        for (ScriptLoader loader : loaders) {
            try {
                PyModule module = loader.loadModule(moduleName, vm);
                if (module != null) {
                    modules.put(key, module);
                    return module;
                }
            } catch (Exception e) {
                throw new RuntimeException("ImportError: " + e.getMessage());
            }
        }
        return null;
    }

    public void registerScript(PyModule module) {
        modules.put(new PyString(module.getName()), module);
    }
}
