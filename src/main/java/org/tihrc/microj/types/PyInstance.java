package org.tihrc.microj.types;

import org.tihrc.microj.core.Capability;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.FastMap;

public class PyInstance extends PyObject {
    public final PyClass pyClass;
    public final FastMap<PyObject> attrs;

    public PyInstance (PyClass pyClass){
        this.pyClass = pyClass;
        attrs = new FastMap<>();
    }

    @Override
    public boolean hasCap(Capability cap) {
        return pyClass.hasCap(cap);
    }

    @Override
    public PyObject findAttribute(String name) {
        PyObject attr = attrs.get(name);
        if (attr == null) attr = pyClass.findAttribute(name);
        if (attr == null) attr = super.findAttribute(name);

        if (attr instanceof Protocols.PyCallable && !(attr instanceof PyBoundMethod))
            return new PyBoundMethod(this, attr);
        return attr;
    }

    public void setAttribute(String name, PyObject value) {
        attrs.put(name, value);
    }

    @Override
    public String pyDanderRepr() {
        return "<%s object>".formatted(pyClass.name);
    }

    @Override
    public String toString() {
        return "PyInstance(%s)".formatted(pyClass.name);
    }

}
