package org.tihrc.microj.types.objects;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.ExceptionsRegistry;
import org.tihrc.microj.core.exceptions.PyBaseException;
import org.tihrc.microj.types.callables.PyBuiltinFunction;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.units.FastMap;

import java.util.Arrays;
import java.util.Map;

public final class PyClassException extends PyClass{
    public final Class<? extends PyBaseException> error;

    public PyClassException(String name, Class<? extends PyBaseException> error, PyClass... bases) {
        super(name, builtinsExceptionAttribute(), Arrays.asList(bases));
        this.error = error;
        ExceptionsRegistry.register(this);
    }

    public static Map<String, PyObject> builtinsExceptionAttribute(){
        FastMap<PyObject> attrs =  new FastMap<>();
        attrs.put("__init__", new PyBuiltinFunction((PyBuiltinFunction.CallVarArgs)(ctx, args)->{
            if (args.length == 0 || !(args[0] instanceof PyInstance inst)) return PyNone.INSTANCE;
            PyObject[] rest = new PyObject[args.length - 1];
            System.arraycopy(args, 1, rest, 0, rest.length);
            PyObject msg = rest.length > 0 ? rest[0] : PyNone.INSTANCE;
            inst.setAttribute("args", new PyTuple(rest));
            inst.setAttribute("message", msg);
            return PyNone.INSTANCE;
        }));
        attrs.put("__str__", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, self) -> {
            PyInstance inst = (PyInstance) self;
            PyObject msg = inst.attrs.get("message");
            if (msg == null || msg == PyNone.INSTANCE) return new PyString(inst.pyClass.name);
            return new PyString(msg.pyDanderStr());
        }));
        attrs.put("__repr__", attrs.get("__str__"));
        return attrs;
    }
}
