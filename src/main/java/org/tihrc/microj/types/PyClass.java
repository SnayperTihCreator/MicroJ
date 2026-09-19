package org.tihrc.microj.types;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.PyExport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PyClass extends PyObject implements Protocols.PyCallable {
    public final String name;
    public final Map<String, PyObject> attrs;
    public final List<PyClass> bases;

    public PyClass (String name, Map<String, PyObject> attrs, List<PyClass> bases) {
        this.name = name;
        this.attrs = attrs;
        this.bases = bases != null ? bases : new ArrayList<>();
    }

    @Override
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        return pyDanderCallFast(ctx, args, null, null);
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        PyObject newMethod = findAttribute("__new__");

        if (newMethod != null) {
            PyObject instance = ((Protocols.PyCallable) newMethod).pyDanderCallFast(ctx, args, kwNames, kwValues);
            if (instance instanceof PyInstance inst && inst.pyClass == this) {
                PyObject initMethod = findAttribute("__init__");
                if (initMethod instanceof Protocols.PyCallable callable) {
                    PyObject[] initArgs = new PyObject[args.length + 1];
                    initArgs[0] = instance;
                    System.arraycopy(args, 0, initArgs, 1, args.length);
                    callable.pyDanderCallFast(ctx, initArgs, kwNames, kwValues);
                }
            }
            return instance;
        }

        PyInstance instance = new PyInstance(this);
        PyObject initMethod = findAttribute("__init__");
        if (initMethod instanceof Protocols.PyCallable callable) {
            PyObject[] initArgs = new PyObject[args.length + 1];
            initArgs[0] = instance;
            System.arraycopy(args, 0, initArgs, 1, args.length);
            callable.pyDanderCallFast(ctx, initArgs, kwNames, kwValues);
        }
        return instance;
    }

    @Override
    public PyObject findAttribute(String name) {
        PyObject attr = attrs.get(name);
        if (attr != null) return attr;

        for (PyClass base : bases) {
            PyObject baseAttr = base.findAttribute(name);
            if (baseAttr != null) return baseAttr;
        }

        return super.findAttribute(name);
    }

    public void setAttribute(String name, PyObject value) {
        attrs.put(name, value);
    }

    @Override
    public String pyDanderRepr() {
        return "<class '%s'>".formatted(this.name);
    }

    @Override
    public String toString() {
        return "PyClass(%s)".formatted(this.name);
    }
}
