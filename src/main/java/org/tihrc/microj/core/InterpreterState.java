package org.tihrc.microj.core;

import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.collections.PyList;
import org.tihrc.microj.types.primitives.PyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InterpreterState {
    public String backend = "bytecode";
    public int recursionLimit = 1500;

    public void recordJit(){ backend = "jit"; }
    public void recordBytecode(){ backend = "bytecode"; }

    public final PyList sysPath = PyList.from(new PyString("scripts/"));
    public final SysModulesProxy modules;

    public InterpreterState(Map<String, PyModule> modules){
        this.modules = new SysModulesProxy(modules);
    }

    public static class SysModulesProxy extends PyDict {
        private final Map<String, PyModule> builtins;

        public SysModulesProxy(Map<String, PyModule> builtins) {
            super();
            this.builtins = builtins;
        }

        @Override
        public PyObject pyDictGet(PyObject key, PyObject def) {
            PyObject value = super.pyDictGet(key, def);
            if (value != null) return value;
            if (key instanceof PyString pyStr) {
                PyModule builtin = this.builtins.get(pyStr.value);
                if (builtin != null) return builtin;
            }
            return def;
        }

        @Override
        public PyList pyDictKeys() {
            List<PyObject> allKeys = new ArrayList<>(this.map.keySet());
            for (var entry: this.builtins.entrySet())
                allKeys.add(new PyString(entry.getKey()));
            return (PyList) Transforms.toPython(allKeys);
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
}
