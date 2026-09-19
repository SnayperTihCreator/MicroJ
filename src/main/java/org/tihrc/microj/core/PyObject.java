package org.tihrc.microj.core;

import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.core.transforms.PyTypeExporter;
import org.tihrc.microj.types.PyBoundMethod;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.units.FastMap;

import java.util.ArrayList;
import java.util.List;

public abstract class PyObject {
    public abstract String toString();

    @PyExport(name="__repr__")
    public String pyDanderRepr() { return "<%s object>".formatted(this.getClass().getSimpleName()); }
    @PyExport(name="__str__")
    public String pyDanderStr() { return this.pyDanderRepr();}
    @PyExport(name="__format__")
    public PyObject pyDanderFormat(PyObject spec) { return new PyString(this.toString()); }
    @PyExport(name="__hash__")
    public int pyDanderHash() { return System.identityHashCode(this); }
    @PyExport(name="__doc__")
    public String pyDanderDoc() { return "No docs"; }
    @PyExport(name="__eq__")
    public PyObject pyDanderEq(PyObject other) {
        return PyBool.from(this == other);
    }
    @PyExport(name="__ne__")
    public PyObject pyDanderNe(PyObject other) {
        return PyBool.from(this != other);
    }
    @PyExport(name = "__dir__")
    public List<String> pyDanderDir() {
        FastMap<PyObject> methods = PyTypeExporter.INSTANCE.get(this.getClass());
        return new ArrayList<>(methods.keySet());
    }

    private int capabilities = Capability.NONE.bit();
    public boolean hasCap(Capability cap) {
        return (capabilities & cap.bit()) != 0;
    }
    public void addCap(Capability cap) {
        this.capabilities |= cap.bit();
    }

    public PyObject findAttribute(String name) {
        PyObject attr = PyTypeExporter.findExported(this, name);
        if (attr instanceof Protocols.PyCallable)
            return new PyBoundMethod(this, attr);
        return attr;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof PyString s && this instanceof PyString selfS) {
            return selfS.value.equals(s.value);
        }
        return super.equals(obj);
    }

    public void setAttribute(String name, PyObject value) {}

    @Override
    public int hashCode() {
        return pyDanderHash();
    }
}
