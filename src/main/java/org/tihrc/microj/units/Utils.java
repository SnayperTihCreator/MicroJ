package org.tihrc.microj.units;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;

public final class Utils {
    private Utils() {}

    @SafeVarargs
    public static <T>  T[] arrs(T... values){
        return values;
    }

    public static PyObject runFastSelf(
            RuntimeExecuter ctx, Protocols.PyCallable callable, PyObject self,
            PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        PyObject[] newArgs = new PyObject[args.length + 1];
        System.arraycopy(args, 0, newArgs, 1, args.length);
        newArgs[0] = self;
        return callable.pyDanderCallFast(ctx, newArgs, kwNames, kwValues);
    }
}
