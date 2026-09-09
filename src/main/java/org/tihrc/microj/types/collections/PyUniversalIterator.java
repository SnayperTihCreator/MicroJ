package org.tihrc.microj.types.collections;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;

import java.util.function.Supplier;

public class PyUniversalIterator extends PyObject implements Protocols.PyIterator {
    private final Supplier<PyObject> supplier;

    public PyUniversalIterator(Supplier<PyObject> supplier) {
        this.supplier = supplier;
    }

    @Override
    public PyObject pyDanderNext() {
        PyObject next = supplier.get();
        if (next == null)
            return new Exceptions.PyStopIteration().raise();
        return next;
    }

    @Override
    public String pyDanderRepr() {
        return "<iterator>";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
