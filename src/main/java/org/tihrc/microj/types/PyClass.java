package org.tihrc.microj.types;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.PyExport;

import java.util.Map;

public class PyClass extends PyObject implements Protocols.PyCallable {
    public final String name;
    public final Map<String, PyObject> attrs;

    public PyClass (String name, Map<String, PyObject> attrs){
        this.name = name;
        this.attrs = attrs;
    }

    @Override
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        PyObject newMethod = findAttribute("__new__");

        if (newMethod != null) {
            PyObject instance = ctx.callSync(newMethod, args);

            if (instance instanceof PyInstance inst && inst.pyClass == this) {
                PyObject initMethod = findAttribute("__init__");
                if (initMethod != null) {
                    PyObject[] initArgs = new PyObject[args.length + 1];
                    initArgs[0] = instance;
                    System.arraycopy(args, 0, initArgs, 1, args.length);
                    ctx.callSync(initMethod, initArgs);
                }
            }
            return instance;
        }

        PyInstance instance = new PyInstance(this);
        PyObject initMethod = findAttribute("__init__");
        if (initMethod != null) {
            PyObject[] initArgs = new PyObject[args.length + 1];
            initArgs[0] = instance;
            System.arraycopy(args, 0, initArgs, 1, args.length);
            ctx.callSync(initMethod, initArgs);
        }
        return instance;
    }

    @Override
    public PyObject findAttribute(String name) {
        PyObject attr = attrs.get(name);
        if (attr != null) return attr;
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
