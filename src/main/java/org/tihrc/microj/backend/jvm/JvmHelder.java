package org.tihrc.microj.backend.jvm;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.ExceptionsRegistry;
import org.tihrc.microj.core.exceptions.PyBaseException;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.core.PyContext;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.core.PyNotImplemented;
import org.tihrc.microj.types.objects.PyClass;
import org.tihrc.microj.types.objects.PyInstance;
import org.tihrc.microj.types.primitives.PyFloat;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.runtime.PyCode;
import org.tihrc.microj.types.sequences.PyGeneratorFunc;
import org.tihrc.microj.units.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JvmHelder implements AsmTypes{
    private JvmHelder() {}

    @SuppressWarnings("unused")
    public static PyObject buildClass(String name, Map<String, PyObject> attrs, String[] baseNames, RuntimeExecuter ctx) {
        List<PyClass> bases = new ArrayList<>();
        for (String bn : baseNames) {
            PyObject base = ctx.getGlobals().get(bn);
            if (base == null) base = ctx.getBuiltins().findAttribute(bn);
            if (!(base instanceof PyClass pc))
                return new Exceptions.PyNameError("name '" + bn + "' is not defined").raise();
            bases.add(pc);
        }
        return new PyClass(name, attrs, bases);
    }

    public static final String SbuildClass = "(L%s;L%s;[L%s;L%s;)L%s;".formatted(STRING, MAP, STRING, RUN_EXECUTER, PY_OBJECT);

    @SuppressWarnings("unused")
    public static PyObject makeGeneratorFunction(
            RuntimeExecuter ctx, PyObject codeObj, String name, PyObject[] constants,
            String[] params, String starArg, String kwArg, String[] freeVars,
            PyObject defaults,PyObject[] closureArray) {
        PyCode code = (PyCode) codeObj;
        Map<String, PyObject> closureMap = new HashMap<>();
        for (int i = 0; i < freeVars.length; i++)
            closureMap.put(freeVars[i], closureArray[i]);
        return new PyGeneratorFunc(name, code.body, List.of(params), starArg, kwArg, closureMap, defaults, constants, code.lineTable);
    }

    public static final String SmakeGeneratorFunction = "(L%s;L%s;L%s;%s%sL%s;L%s;%sL%s;%s)L%s;".formatted(
            RUN_EXECUTER,   // RuntimeExecuter
            PY_OBJECT,      // PyObject codeObj
            STRING,         // String name
            PYARR_OBJECT,   // PyObject[] constants
            STRING_ARR,     // String[] params
            STRING,         // String starArg
            STRING,         // String kwArg
            STRING_ARR,     // String[] freeVars
            PY_OBJECT,      // PyObject defaults
            PYARR_OBJECT,   // PyObject[] closureArray
            PY_OBJECT       // return
    );

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public static PyObject[] bindArgs(RuntimeExecuter ctx, PyObject[] locals, PyObject defaults, PyObject[] args, String[] kwNames, PyObject[] kwValues, String[] params, String starArg, String kwArg) {
        int paramCount = params.length;
        int starOffset = starArg != null ? 1 : 0;
        int kwOffset = kwArg != null ? 1 : 0;

        for (int i = 0; i < paramCount; i++) {
            if (i < args.length) locals[i] = args[i];
        }

        if (kwNames != null && kwNames.length > 0) {
            for (int i = 0; i < kwNames.length; i++) {
                String name = kwNames[i];
                for (int j = 0; j < paramCount; j++) {
                    if (params[j].equals(name)) {
                        locals[j] = kwValues[i];
                        break;
                    }
                }
            }
        }

        if (defaults != null && defaults != PyNone.INSTANCE) {
            PyTuple defaultsTuple = (PyTuple) defaults;
            PyObject[] defaultVals = defaultsTuple.getInner();
            int defaultOffset = paramCount - defaultVals.length;
            for (int i = 0; i < defaultVals.length; i++) {
                int paramIdx = defaultOffset + i;
                if (locals[paramIdx] == null) locals[paramIdx] = defaultVals[i];
            }
        }

        for (int i = 0; i < paramCount; i++) {
            if (locals[i] == null) locals[i] = PyNone.INSTANCE;
        }

        if (starArg != null) {
            int extraCount = args.length > paramCount ? args.length - paramCount : 0;
            PyObject[] starArgs = new PyObject[extraCount];
            System.arraycopy(args, paramCount, starArgs, 0, extraCount);
            locals[paramCount] = new PyTuple(starArgs);
        }

        if (kwArg != null) {
            PyDict kwargsDict = new PyDict();
            if (kwNames != null && kwNames.length > 0) {
                for (int i = 0; i < kwNames.length; i++) {
                    String name = kwNames[i];
                    boolean isParam = false;
                    for (String param : params) {
                        if (param.equals(name)) {
                            isParam = true;
                            break;
                        }
                    }
                    if (!isParam) kwargsDict.put(new PyString(name), kwValues[i]);
                }
            }
            locals[paramCount + starOffset] = kwargsDict;
        }

        return locals;
    }

    public static final String SbindArgs = "(L%s;%sL%s;%s[L%s;[L%s;[L%s;L%s;L%s;)[L%s;".formatted(
            RUN_EXECUTER, PYARR_OBJECT, PY_OBJECT, PYARR_OBJECT, STRING, PY_OBJECT, STRING, STRING, STRING, PY_OBJECT);

    @SuppressWarnings("unused")
    public static PyObject overloadOperator(PyObject left, PyObject right, RuntimeExecuter ctx, String dunderName) {
        PyObject method = left.findAttribute(dunderName);
        if (method instanceof Protocols.PyCallable callable) {
            return callable.pyDanderCallFast(ctx, new PyObject[]{right}, JitFunction.EMPTY_KW_NAMES, JitFunction.EMPTY_KW_VALS);
        }
        throw new RuntimeException("Unsupported operand type for " + dunderName);
    }

    public static final String SoverloadOperator = "(L%s;L%s;L%s;L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT, RUN_EXECUTER, STRING, PY_OBJECT);

    @SuppressWarnings("unused")
    public static PyObject getIter(RuntimeExecuter ctx, PyObject obj) {
        if (obj instanceof Protocols.PyIterable iter) {
            return (PyObject) iter.pyDanderIter();
        }
        PyObject method = obj.findAttribute("__iter__");
        if (method instanceof Protocols.PyCallable callable) {
            return callable.pyDanderCallFast(ctx,
                    Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
        }
        return new Exceptions.PyTypeError("object %s is not an iterable".formatted(obj.pyDanderRepr())).raise();
    }

    public static final String SgetIter = "(L%s;L%s;)L%s;".formatted(RUN_EXECUTER, PY_OBJECT, PY_OBJECT);

    @SuppressWarnings("unused")
    public static PyObject forIterNext(PyObject iter) {
        try {
            if (iter instanceof Protocols.PyIterator pyIter) return pyIter.pyDanderNext();
            PyObject nextMethod = iter.findAttribute("__next__");
            if (nextMethod instanceof Protocols.PyCallable callable) {
                RuntimeExecuter ctx = PyContext.current();
                return callable.pyDanderCallFast(ctx,
                        Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
            }
            return new Exceptions.PyTypeError("object %s is not an iterator".formatted(iter.pyDanderRepr())).raise();

        } catch (PyUnwind e) {
            if (ExceptionsRegistry.matches(e.payload, "StopIteration"))
                return null;
            throw e;

        } catch (Throwable t) {
            System.err.println("[JIT] UNEXPECTED EXCEPTION in forIterNext:");
            t.printStackTrace();
            throw new RuntimeException("Unexpected error in forIterNext", t);
        }
    }

    public static final String SforIterNext = "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT);

    @SuppressWarnings("unused")
    public static void doImport(RuntimeExecuter ctx, String moduleName, String alias) {
        var module = ctx.getVM().getLib().resolveModule(moduleName, ctx);
        if (module != null) {
            String storeName = (alias != null) ? alias : moduleName;
            ctx.getGlobals().put(storeName, module);
        }
    }

    public static final String SdoImport = "(L%s;L%s;L%s;)V".formatted(RUN_EXECUTER, STRING, STRING);

    @SuppressWarnings("unused")
    public static void doImportFrom(RuntimeExecuter ctx, String moduleName, String[] names) {
        var module = ctx.getVM().getLib().resolveModule(moduleName, ctx);
        if (module != null) {
            for (String name : names) {
                PyObject attr = module.findAttribute(name);
                if (attr != null) {
                    ctx.getGlobals().put(name, attr);
                }
            }
        }
    }

    public static final String SdoImportFrom = "(L%s;L%s;[L%s;)V".formatted(RUN_EXECUTER, STRING, STRING);

    @SuppressWarnings("unused")
    public static void reRaise(PyObject payload) {
        if (payload == null)
            new Exceptions.PyRuntimeError("No active exception to re-raise").raise();
        throw new PyUnwind(payload);
    }

    public static final String SreRaise = "(L%s;)V".formatted(PY_OBJECT);

    @SuppressWarnings("unused")
    public static void assertFail(PyObject cond, PyObject msg) {
        boolean truthy = false;
        if (cond instanceof Protocols.PyComparable cmp) {
            truthy = cmp.pyDanderBool();
        }

        if (!truthy) {
            String msgStr = (msg == PyNone.INSTANCE) ? "assertion failed" : msg.toString();
            throw new PyUnwind(new Exceptions.PyAssertionError(msgStr));
        }
    }

    public static final String SassertFail = "(L%s;L%s;)V".formatted(PY_OBJECT, PY_OBJECT);

    @SuppressWarnings("unused")
    public static PyObject nameError(String name) {
        return new Exceptions.PyNameError("name '" + name + "' is not defined").raise();
    }

    public static final String SnameError = "(L%s;)L%s;".formatted(STRING, PY_OBJECT);

    @SuppressWarnings("unused")
    public static void raiseException(RuntimeExecuter vm, PyObject exc) {
        if (exc instanceof PyClass cls) {
            if (!ExceptionsRegistry.isExceptionClass(cls))
                new Exceptions.PyTypeError("exceptions must derive from BaseException").raise();
            exc = vm.callSyncFast(cls, Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
        }
        if (exc instanceof PyInstance inst) {
            if (!ExceptionsRegistry.isExceptionClass(inst.pyClass))
                new Exceptions.PyTypeError("exceptions must derive from BaseException").raise();
            throw new PyUnwind(inst);
        }
        if (exc instanceof PyBaseException pe)
            throw new PyUnwind(pe);
        new Exceptions.PyTypeError("exceptions must derive from BaseException").raise();
    }

    public static final String SraiseException = "(L%s;L%s;)V".formatted(RUN_EXECUTER, PY_OBJECT);

    @SuppressWarnings("unused")
    public static boolean matchesException(PyObject exc, String typeName) {
        return ExceptionsRegistry.matches(exc, typeName);
    }

    public static final String SmatchesException = "(L%s;L%s;)Z".formatted(PY_OBJECT, STRING);

    @SuppressWarnings("unused")
    public static boolean truthy(RuntimeExecuter ctx, PyObject obj) {
        PyObject boolMethod = obj.findAttribute("__bool__");
        if (boolMethod instanceof Protocols.PyCallable callable) {
            PyObject res = callable.pyDanderCallFast(ctx, Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
            if (res instanceof Protocols.PyComparable cmp) return cmp.pyDanderBool();
            return new Exceptions.PyTypeError("__bool__ should return boolean, returned " + res.pyDanderRepr()).raise();
        }
        PyObject lenMethod = obj.findAttribute("__len__");
        if (lenMethod instanceof Protocols.PyCallable callable) {
            PyObject res = callable.pyDanderCallFast(
                    ctx, Constants.NO_ARGS, Constants.NO_KW_NAMES, Constants.NO_KW_VALUES);
            if (res instanceof PyInt i) return !i.value.isZero();
            if (res instanceof PyFloat f) return !f.value.isZero();
            return new Exceptions.PyTypeError("__len__ should return int, returned " + res.pyDanderRepr()).raise();
        }
        if (obj instanceof Protocols.PyComparable cmp) return cmp.pyDanderBool();
        if (obj instanceof Protocols.PyContainer ct)   return ct.pyDanderLen() > 0;
        return true;
    }

    public static final String Struthy = "(L%s;L%s;)Z".formatted(RUN_EXECUTER, PY_OBJECT);

    @SuppressWarnings("unused")
    public static PyObject checkNotImplemented(PyObject result) {
        if (result instanceof PyNotImplemented) {
            return new Exceptions.PyTypeError("unsupported operand type").raise();
        }
        return result;
    }

    public static final String ScheckNotImplemented = "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT);

    @SuppressWarnings("unused")
    public static PyObject[] unpack(RuntimeExecuter ctx, PyObject seq, int count) {
        PyObject[] result = new PyObject[count];
        if (seq instanceof Protocols.PyContainer container) {
            for (int i = 0; i < count; i++) {
                result[i] = container.pyDanderGetItem(PyInt.from(i));
            }
            return result;
        }
        throw new RuntimeException("cannot unpack non-sequence");
    }

    public static final String Sunpack = "(L%s;L%s;I)[L%s;".formatted(RUN_EXECUTER, PY_OBJECT, PY_OBJECT);

    @SuppressWarnings("unused")
    public static void storeSubscript(RuntimeExecuter ctx, PyObject container, PyObject index, PyObject value) {
        if (container instanceof Protocols.PyContainer cont) {
            cont.pyDanderSetItem(index, value);
            return;
        }
        PyObject method = container.findAttribute("__setitem__");
        if (method instanceof Protocols.PyCallable callable) {
            callable.pyDanderCallFast(ctx, new PyObject[]{index, value}, JitFunction.EMPTY_KW_NAMES, JitFunction.EMPTY_KW_VALS);
            return;
        }
        throw new RuntimeException("object does not support item assignment");
    }

    public static final String SstoreSubscript = "(L%s;L%s;L%s;L%s;)V".formatted(RUN_EXECUTER, PY_OBJECT, PY_OBJECT, PY_OBJECT);


}
