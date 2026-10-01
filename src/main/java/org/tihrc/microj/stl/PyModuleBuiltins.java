package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.ExceptionsRegistry;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.callables.PyBuiltinFunction;
import org.tihrc.microj.types.sequences.PyEnumerate;
import org.tihrc.microj.types.sequences.PyRange;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.core.PyNone;

@SuppressWarnings("DataFlowIssue")
public class PyModuleBuiltins extends PyModule {
    public PyModuleBuiltins(Interpreter interpreter) {
        super("builtins");

        registerAttribute("True", PyBool.TRUE);
        registerAttribute("False", PyBool.FALSE);
        registerAttribute("None", PyNone.INSTANCE);

        ExceptionsRegistry.registerBuiltins(this);

        registerAttribute("str", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) -> new PyString(arg.pyDanderStr())));
        registerAttribute("repr", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) -> new PyString(arg.pyDanderRepr())));

        registerAttribute("print", new PyBuiltinFunction((PyBuiltinFunction.CallVarArgs) (ctx, args) -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (i > 0) sb.append(" ");
                PyObject arg = args[i];
                PyObject builtin_str = this.findAttribute("str");
                PyObject result = ctx.callSync(builtin_str, arg);
                sb.append(result.pyDanderStr());
            }
            System.out.println(sb);
            return PyNone.INSTANCE;
        }));

        registerAttribute("len", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, obj) -> {
            if (obj instanceof Protocols.PyContainer seq) return PyInt.from(seq.pyDanderLen());

            PyObject lenMethod = obj.findAttribute("__len__");
            if (lenMethod != null) {
                PyObject res = ctx.callSync(lenMethod, obj);
                if (res instanceof PyInt i) return i;
            }
            return new Exceptions.PyTypeError("%s has no len()".formatted(obj)).raise();
        }));

        registerAttribute("sum", new PyBuiltinFunction((PyBuiltinFunction.CallVarArgs)(ctx, args) -> {
            if (args.length == 0)
                return new Exceptions.PyTypeError("sum expected at least 1 argument").raise();

            PyObject iterable = args[0];
            PyObject result = args.length > 1 ? args[1] : PyInt.from(0);

            if (iterable instanceof Protocols.PyIterable iter) {
                Protocols.PyIterator iterator = iter.pyDanderIter();
                try {
                    //noinspection InfiniteLoopStatement
                    while (true) {
                        PyObject item = iterator.pyDanderNext();
                        if (result instanceof Protocols.PyNumber num) {
                            result = num.pyDanderAdd(item);
                        }
                    }
                } catch (PyUnwind e) {
                    if (!ExceptionsRegistry.matches(e.payload, "StopIteration"))
                        throw e;
                }
            }
            return result;
        }));

        registerAttribute("next", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) -> {
            PyObject nextMethod = arg.findAttribute("__next__");
            if (nextMethod != null) {
                return ctx.callSync(nextMethod, arg);
            }
            return new Exceptions.PyTypeError("%s has no next()".formatted(arg)).raise();
        }));

        registerAttribute("type", new PyBuiltinFunction(((PyBuiltinFunction.Call1)(ctx, arg) -> new PyString(arg.getClass().getSimpleName()))));

        registerAttribute("range", new PyBuiltinFunction((PyBuiltinFunction.CallVarArgs) (ctx, args) -> {
            Integer start = 0, stop = 0, step = 1;
            if (args.length == 1) stop = Transforms.fromPython(args[0], int.class);
            else if (args.length == 2) {
                start = Transforms.fromPython(args[0], int.class);
                stop = Transforms.fromPython(args[1], int.class);
            } else if (args.length == 3) {
                start = Transforms.fromPython(args[0], int.class);
                stop = Transforms.fromPython(args[1], int.class);
                step =  Transforms.fromPython(args[2], int.class);
            }
            return new PyRange(start, stop, step);
        }));

        registerAttribute("enumerate", new PyBuiltinFunction((PyBuiltinFunction.CallVarArgs) (ctx, args) -> {
            if (args.length == 0)
                return new Exceptions.PyTypeError("enumerate expected at least 1 argument").raise();
            int start = args.length > 1 ? Transforms.fromPython(args[1], int.class) : 0;
            return new PyEnumerate(args[0], start);
        }));

        registerAttribute("abs", new PyBuiltinFunction(((PyBuiltinFunction.Call1)(ctx, arg) -> {
            Integer value1 = Transforms.fromPythonOrNull(arg, Integer.class);
            if (value1 != null)
                return Transforms.toPython(Math.abs(value1));
            Double value2 = Transforms.fromPythonOrNull(arg, Double.class);
            if (value2 != null)
                return Transforms.toPython(Math.abs(value2));
            return new Exceptions.PyTypeError("Not abs").raise();
        })));

        registerAttribute("dir", new PyBuiltinFunction(((PyBuiltinFunction.Call1)(ctx, arg) -> {
            PyObject dirMethod = arg.findAttribute("__dir__");
            if (dirMethod != null) {
                PyObject res = ctx.callSync(dirMethod, arg);
                return res instanceof PyString ? res : new PyString(res.pyDanderRepr());
            }
            return Transforms.toPython(arg.pyDanderDir());
        })));
    }
}
