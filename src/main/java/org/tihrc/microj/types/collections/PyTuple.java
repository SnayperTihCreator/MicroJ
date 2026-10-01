package org.tihrc.microj.types.collections;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyString;

import java.util.Arrays;

@SuppressWarnings("unused")
public class PyTuple extends PyObject implements Protocols.PyContainer, Protocols.PyIterable {
    private final PyObject[] items;

    public PyTuple(PyObject[] items) {
        this.items = items;
    }

    public static PyTuple from(PyObject... items) {
        return new PyTuple(items);
    }

    @PyExport(name = "count")
    public PyObject pyTupleCount(PyObject item) {
        int count = 0;
        for (PyObject pyItem : items) {
            if (pyItem.equals(item)) count++;
        }
        return PyInt.from(count);
    }
    @PyExport(name = "index")
    public PyObject pyTupleIndex(PyObject item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(item)) return PyInt.from(i);
        }
        return new Exceptions.PyValueError("tuple.index(x): x not in tuple").raise();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @PyExport(name = "__getitem__")
    public PyObject pyDanderGetItem(PyObject index) {
        int idx = Transforms.fromPython(index, int.class);
        if (idx < 0) idx += items.length;
        if (idx >= 0 && idx < items.length) return items[idx];
        return new Exceptions.PyIndexError("tuple index out of range").raise();
    }
    @Override
    @PyExport(name = "__setitem__")
    public void pyDanderSetItem(PyObject index, PyObject value) {
        new Exceptions.PyTypeError("'tuple' object does not support item assignment").raise();
    }
    @Override
    @PyExport(name = "__len__")
    public int pyDanderLen() {
        return items.length;
    }

    @Override
    @PyExport(name = "__contains__")
    public PyObject pyDanderContains(PyObject item) {
        for (PyObject pyItem : items) {
            if (pyItem.equals(item)) return PyBool.TRUE;
        }
        return PyBool.FALSE;
    }

    public static final class PyTupleIterator
            extends PyObject
            implements Protocols.PyIterator {

        private final PyTuple tuple;
        private int index;

        public PyTupleIterator(PyTuple tuple) {
            this.tuple = tuple;
        }

        @Override
        public PyObject pyDanderNext() {
            if (index < tuple.items.length)
                return tuple.items[index++];

            return new Exceptions.PyStopIteration().raise();
        }

        @Override
        public String pyDanderRepr() {
            return "<tuple_iterator>";
        }

        @Override
        public String toString() {
            return "PyTupleIterator";
        }
    }

    public PyObject[] getInner(){
        return items;
    }

    @Override
    @PyExport(name = "__iter__")
    public Protocols.PyIterator pyDanderIter() {
        return new PyTupleIterator(this);
    }


    @Override
    public String pyDanderRepr() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < items.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(items[i].pyDanderRepr());
        }
        if (items.length == 1) sb.append(",");
        return sb.append(")").toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PyTuple o) return Arrays.equals(this.items, o.items);
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < items.length; i++) {
            if (i > 0) sb.append(", ");
            PyObject item = items[i];
            if (item instanceof PyString s) sb.append("\"").append(s.value).append("\"");
            else sb.append(item.toString());
        }
        if (items.length == 1) sb.append(",");
        return sb.append(")").toString();
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(items);
    }
}
