package org.tihrc.microj.types.collections;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyNone;

import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class PyList extends PyObject implements Protocols.PyContainer, Protocols.PyIterable, Protocols.PyNumber {
    protected final List<PyObject> items;

    public PyList(PyObject[] items) {
        this.items = new ArrayList<>(java.util.Arrays.asList(items));
    }

    public PyList(List<PyObject> items) {
        this.items = new ArrayList<>(items);
    }

    public static PyList from(PyObject... items) {
        return new PyList(items);
    }


    @PyExport(name = "append")
    public PyObject pyListAppend(PyObject item) {
        items.add(item);
        return PyNone.INSTANCE;
    }
    @PyExport(name = "pop")
    public PyObject pyListPop() {
        if (items.isEmpty()) return new Exceptions.PyIndexError("pop from empty list").raise();
        return items.removeLast();
    }
    @PyExport(name = "extend")
    public PyObject pyListExtend(Protocols.PyIterable iterable) {
        if (iterable != null) {
            Protocols.PyIterator it = iterable.pyDanderIter();
            while (true) {
                PyObject item = it.pyDanderNext();
                if (item == PyNone.INSTANCE) break;
                items.add(item);
            }
        }
        return PyNone.INSTANCE;
    }
    @PyExport(name = "insert")
    public PyObject pyListInsert(int idx, PyObject item) {
        if (idx < 0) idx += items.size();
        if (idx < 0) idx = 0;
        if (idx > items.size()) idx = items.size();
        items.add(idx, item);
        return PyNone.INSTANCE;
    }
    @PyExport(name = "remove")
    public PyObject pyListRemove(PyObject item) {
        items.remove(item);
        return PyNone.INSTANCE;
    }
    @PyExport(name = "clear")
    public PyObject pyListClear() {
        items.clear();
        return PyNone.INSTANCE;
    }
    @PyExport(name = "index")
    public PyObject pyListIndex(PyObject item) {
        int idx = items.indexOf(item);
        if (idx == -1) return new Exceptions.PyValueError("not in list").raise();
        return PyInt.from(idx);
    }
    @PyExport(name = "count")
    public PyObject pyListCount(PyObject item) {
        return PyInt.from(Collections.frequency(items, item));
    }
    @PyExport(name = "reverse")
    public PyObject pyListReverse() {
        Collections.reverse(items);
        return PyNone.INSTANCE;
    }
    @PyExport(name = "copy")
    public PyObject pyListCopy() {
        return new PyList(new ArrayList<>(items));
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @PyExport(name = "__getitem__")
    public PyObject pyDanderGetItem(PyObject index) {
        int idx = Transforms.fromPython(index, int.class);
        if (idx < 0) idx += items.size();
        if (idx >= 0 && idx < items.size()) return items.get(idx);
        return new Exceptions.PyIndexError("list index out of range").raise();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @PyExport(name = "__setitem__")
    public void pyDanderSetItem(PyObject index, PyObject value) {
        int idx = Transforms.fromPython(index, int.class);
        if (idx < 0) idx += items.size();
        if (idx >= 0 && idx < items.size()) {
            items.set(idx, value);
            return;
        }
        new Exceptions.PyIndexError("list assignment index out of range").raise();
    }
    @Override
    @PyExport(name = "__len__")
    public int pyDanderLen() {
        return items.size();
    }

    @Override
    @PyExport(name = "__contains__")
    public PyObject pyDanderContains(PyObject index) {
        return PyBool.from(items.contains(index));
    }

    @Override
    @PyExport(name = "__iter__")
    public Protocols.PyIterator pyDanderIter() {
        return new PyListIterator(this);
    }

    @Override
    @PyExport(name = "__add__")
    public PyObject pyDanderAdd(PyObject other) {
        PyList otherList = Transforms.checkList(other);
        List<PyObject> result = Stream.concat(this.items.stream(), otherList.items.stream()).toList();
        return new PyList(result);
    }

    @Override
    @PyExport(name = "__mul__")
    public PyObject pyDanderMul(PyObject other) {
        PyInt otherInt = Transforms.checkInt(other);
        List<PyObject> result = Collections.nCopies(otherInt.value.toInt(), this.items)
                .stream().flatMap(List::stream).toList();
        return new PyList(result);
    }

    @Override
    public PyObject pyDanderSub(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override
    public PyObject pyDanderTrueDiv(PyObject other) { return PyNotImplemented.INSTANCE; }
    @Override
    public PyObject pyDanderNeg() { return PyNotImplemented.INSTANCE; }

    @Override
    public String toString() {
        return "PyList<%s>".formatted(items);
    }

    @Override
    public String pyDanderRepr() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(items.get(i).pyDanderRepr());
        }
        return sb.append("]").toString();
    }

    public List<PyObject> getInner() { return items; }


    public static final class PyListIterator
            extends PyObject
            implements Protocols.PyIterator {

        private final PyList list;
        private int index;

        public PyListIterator(PyList list) {
            this.list = list;
        }

        @Override
        public PyObject pyDanderNext() {
            if (index < list.items.size()) {
                return list.items.get(index++);
            }
            return new Exceptions.PyStopIteration().raise();
        }

        @Override
        public String pyDanderRepr() {
            return "<list_iterator>";
        }

        @Override
        public String toString() {
            return "PyListIterator";
        }
    }

}