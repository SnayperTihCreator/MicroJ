package org.tihrc.microj.compiler;

import org.tihrc.microj.core.PyObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstantPool {
    private final List<PyObject> values = new ArrayList<>();
    private final Map<PyObject, Integer> indexes = new HashMap<>();

    public int add(PyObject value) {
        Integer index = indexes.get(value);
        if (index != null) {
            return index;
        }

        int newIndex = values.size();
        values.add(value);
        indexes.put(value, newIndex);
        return newIndex;
    }

    public PyObject get(int index) {
        return values.get(index);
    }

    public PyObject[] toArray() {
        return values.toArray(PyObject[]::new);
    }
}
