package org.tihrc.microj.units;

import org.tihrc.microj.core.PyObject;

import java.util.Arrays;

public final class FastStack {
    private PyObject[] array;
    private int sp = 0;

    public FastStack(int capacity) {
        array = new PyObject[capacity];
    }

    public void push(PyObject obj) {
        if (sp == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[sp++] = obj;
    }

    public PyObject pop() {
        PyObject val = array[--sp];
        array[sp] = null;
        return val;
    }

    public PyObject peek() {
        return array[sp - 1];
    }

    public boolean isEmpty() {
        return sp == 0;
    }

    public int size() {
        return sp;
    }

    public void clear() {
        Arrays.fill(array, null);
        sp = 0;
    }
}
