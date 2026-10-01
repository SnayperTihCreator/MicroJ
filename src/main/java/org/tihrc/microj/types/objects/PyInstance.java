package org.tihrc.microj.types.objects;

import org.tihrc.microj.core.Capability;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.callables.PyBoundMethod;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.core.PyContext;
import org.tihrc.microj.units.Constants;
import org.tihrc.microj.units.FastMap;
import org.tihrc.microj.units.Utils;

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

    private PyObject userLookup(String name) {
        PyObject m = attrs.get(name);
        return m != null ? m : pyClass.findAttribute(name);
    }

    @Override
    public PyObject pyDanderFormat(String spec) {
        PyObject method = userLookup("__format__");
        if (method instanceof Protocols.PyCallable callable) {
            RuntimeExecuter ctx = PyContext.current();
            return callable.pyDanderCallFast(ctx, new PyObject[]{ this, new PyString(spec) }, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
        }
        return super.pyDanderFormat(spec);
    }

    @Override
    public String pyDanderRepr() {
        PyObject method = userLookup("__repr__");
        if (method instanceof Protocols.PyCallable callable) {
            RuntimeExecuter ctx = PyContext.current();
            PyObject res = callable.pyDanderCallFast(ctx, new PyObject[]{ this }, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
            if (res instanceof PyString s) return s.value;
        }
        return "<%s object>".formatted(pyClass.name);
    }

    @Override
    public String pyDanderStr() {
        PyObject method = userLookup("__str__");
        if (method instanceof Protocols.PyCallable callable) {
            RuntimeExecuter ctx = PyContext.current();
            PyObject res = callable.pyDanderCallFast(ctx,
                    new PyObject[]{ this }, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
            if (res instanceof PyString s) return s.value;
        }
        return pyDanderRepr();
    }

    @Override
    public String toString() {
        return "PyInstance(%s)".formatted(pyClass.name);
    }

}
