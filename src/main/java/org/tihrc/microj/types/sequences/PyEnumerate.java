package org.tihrc.microj.types.sequences;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.core.PyContext;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.units.Constants;

public class PyEnumerate extends PyObject implements Protocols.PyIterable, Protocols.PyIterator {
    private final Holder holder;
    private int index;

    private record Holder(TypeHolder type, PyObject iter) {
        public enum TypeHolder {NATIVE, BYTECODE}

        public PyObject next() {
                return switch (type) {
                    case NATIVE -> ((Protocols.PyIterator) iter).pyDanderNext();
                    case BYTECODE -> {
                        PyObject nextMethod = iter.findAttribute("__next__");
                        if (nextMethod instanceof Protocols.PyCallable callable) {
                            RuntimeExecuter ctx = PyContext.current();
                            yield callable.pyDanderCallFast(ctx, Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
                        } else {
                            yield new Exceptions.PyTypeError("iterator has no __next__").raise();
                        }
                    }
                };
            }
        }

    public PyEnumerate(PyObject iterable, int start) {
        if (iterable instanceof Protocols.PyIterable iter) {
            holder = new Holder(Holder.TypeHolder.NATIVE, (PyObject) iter.pyDanderIter());
        } else {
            PyObject method = iterable.findAttribute("__iter__");
            if (method instanceof Protocols.PyCallable callable) {
                RuntimeExecuter ctx = PyContext.current();
                holder = new Holder(Holder.TypeHolder.BYTECODE,
                        callable.pyDanderCallFast(ctx, Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES));
            } else {
                new Exceptions.PyTypeError("enumerate() argument must be iterable").raise();
                holder = null;
            }
        }
        this.index = start;
    }

    @Override
    public Protocols.PyIterator pyDanderIter() {
        return this;
    }

    @Override
    public PyObject pyDanderNext() {
        PyObject value = holder.next();
        return new PyTuple(new PyObject[]{PyInt.from(index++), value});
    }

    @Override
    public String pyDanderRepr() { return "<enumerate object>"; }

    @Override
    public String toString() { return "<enumerate object>"; }
}