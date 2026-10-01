package org.tihrc.microj.types.collections;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyString;

import java.util.*;

@SuppressWarnings("unused")
public class PyDict extends PyObject implements Protocols.PyContainer, Protocols.PyIterable {
    protected final Map<PyObject, PyObject> map;

    public PyDict() {
        map = new HashMap<>();
    }

    public PyDict(Map<PyObject, PyObject> map) {
        this.map = map;
    }

    @PyExport(name = "get")
    public PyObject pyDictGet(PyObject key, PyObject def) {
        return map.getOrDefault(key, def);
    }

    @PyExport(name = "keys")
    public PyList pyDictKeys() {
        return new PyList(map.keySet().toArray(new PyObject[0]));
    }

    @PyExport(name = "values")
    public PyList pyDictValues() {
        return new PyList(map.values().toArray(new PyObject[0]));
    }

    @Override
    @PyExport(name = "__getitem__")
    public PyObject pyDanderGetItem(PyObject key) {
        PyObject val = map.get(key);
        if (val == null) {
            return new Exceptions.PyKeyError(key.toString()).raise();
        }
        return val;
    }

    @Override
    @PyExport(name = "__setitem__")
    public void pyDanderSetItem(PyObject index, PyObject value) {
        map.put(index, value);
    }

    @Override
    @PyExport(name = "__len__")
    public int pyDanderLen() {
        return map.size();
    }

    @Override
    @PyExport(name = "__contains__")
    public PyObject pyDanderContains(PyObject index) {
        return PyBool.from(map.containsKey(index));
    }

    public static final class PyDictIterator
            extends PyObject
            implements Protocols.PyIterator {

        private final Iterator<PyObject> iterator;

        public PyDictIterator(PyDict dict) {
            this.iterator = dict.map.keySet().iterator();
        }

        @Override
        public PyObject pyDanderNext() {
            if (iterator.hasNext())
                return iterator.next();

            return new Exceptions.PyStopIteration().raise();
        }

        @Override
        public String toString() {
            return "PyDictIterator";
        }
    }

    @Override
    @PyExport(name = "__iter__")
    public Protocols.PyIterator pyDanderIter() {
        return new PyDictIterator(this);
    }

    @Override
    public String pyDanderRepr() {
        StringBuilder sb = new StringBuilder("{");
        int i = 0;
        for (var entry : map.entrySet()) {
            if (i > 0) sb.append(", ");
            if (entry.getKey() instanceof PyString k) sb.append("'").append(k.value).append("'");
            else sb.append(entry.getKey().pyDanderRepr());
            sb.append(": ");
            if (entry.getValue() instanceof PyString v) sb.append("'").append(v.value).append("'");
            else sb.append(entry.getValue().pyDanderRepr());
            i++;
        }
        return sb.append("}").toString();
    }

    @Override
    public String toString() {
        return "PyDict<%s>".formatted(map);
    }

    public void put(PyObject key, PyObject value) {
        map.put(key, value);
    }

    public boolean containsKey(PyObject key) {
        return map.containsKey(key);
    }

    public Map<PyObject, PyObject> getInner() { return map; }

}
