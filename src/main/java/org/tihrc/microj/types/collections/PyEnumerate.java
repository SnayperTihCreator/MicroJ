package org.tihrc.microj.types.collections;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;

public class PyEnumerate extends PyObject implements Protocols.PyIterable, Protocols.PyIterator {

    public PyEnumerate(Protocols.PyIterable iterable) {

    }

    @Override
    public Protocols.PyIterator pyDanderIter() {
        return this;
    }

    @Override
    public PyObject pyDanderNext() {
        return null;
    }

    @Override
    public String pyDanderRepr() {
        return "enumerate";
    }

    @Override
    public String toString() {
        return "";
    }
}
