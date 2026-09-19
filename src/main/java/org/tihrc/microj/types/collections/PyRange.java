package org.tihrc.microj.types.collections;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyNotImplemented;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyInt;

public class PyRange extends PyObject implements Protocols.PyIterable, Protocols.PyContainer {
    private final int start;
    private final int stop;
    private final int step;

    public PyRange(Integer start, Integer stop, Integer step) {
        this.start = start;
        this.stop = stop;
        this.step = step;
    }

    public static final class PyRangeIterator
            extends PyObject
            implements Protocols.PyIterator {

        private int current;
        private final int stop;
        private final int step;

        public PyRangeIterator(int start, int stop, int step) {
            this.current = start;
            this.stop = stop;
            this.step = step;
        }

        @Override
        public PyObject pyDanderNext() {
            if (step > 0) {
                if (current >= stop)
                    return new Exceptions.PyStopIteration().raise();
            } else {
                if (current <= stop)
                    return new Exceptions.PyStopIteration().raise();
            }

            int val = current;
            current += step;
            return PyInt.from(val);
        }

        @Override
        public String toString() {
            return "PyRangeIterator";
        }
    }

    @Override
    @PyExport(name = "__iter__")
    public Protocols.PyIterator pyDanderIter() {
        return new PyRangeIterator(start, stop, step);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @PyExport(name = "__contains__")
    public PyObject pyDanderContains(PyObject item) {
        int val = Transforms.fromPython(item, int.class);
        return PyBool.from(val >= start && val < stop);
    }

    @Override
    @PyExport(name = "__len__")
    public int pyDanderLen() {
        return (int) Math.ceil((double) (stop - start) /(step));
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    @PyExport(name = "__getitem__")
    public PyObject pyDanderGetItem(PyObject index) {
        int n = Transforms.fromPython(index, int.class);
        int idx = n >= 0? n: this.pyDanderLen() + n;
        int result = start + idx * step;
        if (result > stop)
            return new Exceptions.PyIndexError("range object index out of range").raise();
        return PyInt.from(result);
    }

    @Override
    public void pyDanderSetItem(PyObject index, PyObject value) {}

    @Override
    public String toString() { return "PyRange(%d, %d)".formatted(start, stop); }

    @Override
    public String pyDanderRepr() { return "range(%d, %d)".formatted(start, stop); }
}