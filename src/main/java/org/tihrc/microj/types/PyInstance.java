package org.tihrc.microj.types;

import org.tihrc.microj.core.Capability;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.types.core.PyContext;
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
    @PyExport(name = "__format__")
    public PyObject pyDanderFormat(String spec) {
        PyObject method = findAttribute("__format__");
        if (method instanceof Protocols.PyCallable callable) {
            RuntimeExecuter ctx = PyContext.current();
            return callable.pyDanderCallFast(ctx, new PyObject[]{new PyString(spec)}, new String[0], new PyObject[0]);
        }
        return super.pyDanderFormat(spec);
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
