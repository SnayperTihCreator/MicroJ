package org.tihrc.microj.core;

import org.tihrc.microj.core.transforms.PyExport;

/**
 * Обёртка над Python-итератором (результатом __iter__) с зафиксированным RuntimeContext.
 * Реализует Protocols.PyIterator, чтобы нативный код мог вызывать pyDanderNext()
 * без знания о контексте.
 *
 * Используется, когда __iter__ возвращает объект, у которого __next__ —
 * Python-функция или PyMethodProxy, требующая RuntimeContext для вызова.
 */
public class PyIteratorAdapter extends PyObject implements Protocols.PyIterator {
    private final PyObject target;
    private final RuntimeExecuter ctx;

    public PyIteratorAdapter(PyObject target, RuntimeExecuter ctx) {
        this.target = target;
        this.ctx = ctx;
    }

    @Override
    @PyExport(name = "__next__")
    public PyObject pyDanderNext() {
        return PyProtocolFacade.getNext(target, ctx);
    }

    @Override
    public String pyDanderRepr() {
        return "<iterator adapter>";
    }

    @Override
    public String toString() {
        return "PyIteratorAdapter";
    }
}
