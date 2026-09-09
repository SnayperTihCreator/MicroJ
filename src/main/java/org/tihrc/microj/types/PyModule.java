package org.tihrc.microj.types;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.FastMap;

import java.util.function.Supplier;

public class PyModule extends PyObject {
    private final String name;
    private final FastMap<Supplier<PyObject>> attributes = new FastMap<>();

    public PyModule(String name) {
        this.name = name;
    }

    public void registerAttribute(String name, Supplier<PyObject> supplier) {
        attributes.put(name, supplier);
    }
    
    public void registerAttribute(String name, PyObject value) {
        attributes.put(name, () -> value);
    }

    @Override
    public PyObject findAttribute(String name) {
        PyObject value = super.findAttribute(name);
        if (value != null) return value;

        Supplier<PyObject> func = attributes.get(name);
        if (func == null) {
            return null;
        }
        return func.get();
    }

    @Override
    @PyExport(name = "__repr__")
    public String pyDanderRepr() {
        return "<module '%s'>".formatted(name);
    }

    @Override
    public String toString() {
        return "PyModule(%s)".formatted(name);
    }

    public String getName() {
        return name;
    }
}
