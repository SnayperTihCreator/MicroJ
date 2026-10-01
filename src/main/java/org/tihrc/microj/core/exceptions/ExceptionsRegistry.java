package org.tihrc.microj.core.exceptions;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.objects.PyClass;
import org.tihrc.microj.types.objects.PyClassException;
import org.tihrc.microj.types.objects.PyInstance;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.units.FastMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ExceptionsRegistry {
    private ExceptionsRegistry() {}

    public static final Map<String, PyClassException> ALL = new FastMap<>();
    public static void register(PyClassException cls) { ALL.put(cls.name, cls); }

    public static final PyClassException BASE_EXCEPTION  = new PyClassException("BaseException", PyBaseException.class);
    public static final PyClassException SYSTEM_EXIT     = new PyClassException("SystemExit", BaseExceptions.PySystemExit.class, BASE_EXCEPTION);
    public static final PyClassException KEYBOARD_INTERRUPT = new PyClassException("KeyboardInterrupt", BaseExceptions.PyKeyboardInterrupt.class, BASE_EXCEPTION);
    public static final PyClassException GENERATOR_EXIT  = new PyClassException("GeneratorExit", BaseExceptions.PyGeneratorExit.class, BASE_EXCEPTION);

    public static final PyClassException EXCEPTION       = new PyClassException("Exception", Exceptions.PyException.class, BASE_EXCEPTION);
    public static final PyClassException STOP_ITERATION  = new PyClassException("StopIteration", Exceptions.PyStopIteration.class, EXCEPTION);
    public static final PyClassException TYPE_ERROR      = new PyClassException("TypeError", Exceptions.PyTypeError.class, EXCEPTION);
    public static final PyClassException VALUE_ERROR     = new PyClassException("ValueError", Exceptions.PyValueError.class, EXCEPTION);
    public static final PyClassException ASSERTION_ERROR = new PyClassException("AssertionError", Exceptions.PyAssertionError.class, EXCEPTION);
    public static final PyClassException ATTRIBUTE_ERROR = new PyClassException("AttributeError", Exceptions.PyAttributeError.class, EXCEPTION);
    public static final PyClassException BUFFER_ERROR    = new PyClassException("BufferError", Exceptions.PyBufferError.class, EXCEPTION);
    public static final PyClassException EOF_ERROR       = new PyClassException("EOFError", Exceptions.PyEOFError.class, EXCEPTION);
    public static final PyClassException MEMORY_ERROR    = new PyClassException("MemoryError", Exceptions.PyMemoryError.class, EXCEPTION);
    public static final PyClassException REFERENCE_ERROR = new PyClassException("ReferenceError", Exceptions.PyReferenceError.class, EXCEPTION);
    public static final PyClassException SYSTEM_ERROR    = new PyClassException("SystemError", Exceptions.PySystemError.class, EXCEPTION);

    public static final PyClassException ARITHMETIC_ERROR = new PyClassException("ArithmeticError", Exceptions.PyArithmeticError.class, EXCEPTION);
    public static final PyClassException FLOATING_POINT_ERROR = new PyClassException("FloatingPointError", Exceptions.PyFloatingPointError.class, ARITHMETIC_ERROR);
    public static final PyClassException OVERFLOW_ERROR  = new PyClassException("OverflowError", Exceptions.PyOverflowError.class, ARITHMETIC_ERROR);
    public static final PyClassException ZERO_DIVISION_ERROR = new PyClassException("ZeroDivisionError", Exceptions.PyZeroDivisionError.class, ARITHMETIC_ERROR);

    public static final PyClassException LOOKUP_ERROR    = new PyClassException("LookupError", Exceptions.PyLookupError.class, EXCEPTION);
    public static final PyClassException INDEX_ERROR     = new PyClassException("IndexError", Exceptions.PyIndexError.class, LOOKUP_ERROR);
    public static final PyClassException KEY_ERROR       = new PyClassException("KeyError", Exceptions.PyKeyError.class, LOOKUP_ERROR);

    public static final PyClassException IMPORT_ERROR    = new PyClassException("ImportError", Exceptions.PyImportError.class, EXCEPTION);
    public static final PyClassException MODULE_NOT_FOUND_ERROR = new PyClassException("ModuleNotFoundError", Exceptions.PyModuleNotFoundError.class, IMPORT_ERROR);

    public static final PyClassException NAME_ERROR      = new PyClassException("NameError", Exceptions.PyNameError.class, EXCEPTION);
    public static final PyClassException UNBOUND_LOCAL_ERROR = new PyClassException("UnboundLocalError", Exceptions.PyUnboundLocalError.class, NAME_ERROR);

    public static final PyClassException RUNTIME_ERROR   = new PyClassException("RuntimeError", Exceptions.PyRuntimeError.class, EXCEPTION);
    public static final PyClassException NOT_IMPLEMENTED_ERROR = new PyClassException("NotImplementedError", Exceptions.PyNotImplementedError.class, RUNTIME_ERROR);
    public static final PyClassException RECURSION_ERROR = new PyClassException("RecursionError", Exceptions.PyRecursionError.class, RUNTIME_ERROR);

    public static final PyClassException SYNTAX_ERROR    = new PyClassException("SyntaxError", Exceptions.PySyntaxError.class, EXCEPTION);
    public static final PyClassException INDENTATION_ERROR = new PyClassException("IndentationError", Exceptions.PyIndentationError.class, SYNTAX_ERROR);
    public static final PyClassException TAB_ERROR       = new PyClassException("TabError", Exceptions.PyTabError.class, INDENTATION_ERROR);

//    public static final PyClassException OS_ERROR        = new PyClassException("OSError", OSExceptions.class-related /* см. ниже */, EXCEPTION);

    public static void registerBuiltins(PyModule builtins) {
        for (Map.Entry<String, PyClassException> e : ALL.entrySet())
            builtins.registerAttribute(e.getKey(), e.getValue());
    }

    public static PyObject get(String name) { return ALL.get(name); }

    public static boolean matches(PyObject exc, String typeName) {
        if (typeName == null) return true;
        PyClass target = ALL.get(typeName);
        if (target == null)
            return errorTypeOf(exc).equals(typeName);
        if (exc instanceof PyInstance inst)
            return matchesClass(inst.pyClass, target, new HashSet<>());
        if (exc instanceof PyBaseException pe) {
            Class<? extends PyBaseException> t = ALL.get(typeName).error;
            return t != null && t.isInstance(pe);
        }
        return false;
    }

    private static boolean matchesClass(PyClass cls, PyClass target, Set<PyClass> visited) {
        if (cls == target) return true;
        if (!visited.add(cls)) return false;
        for (PyClass b : cls.bases)
            if (matchesClass(b, target, visited)) return true;
        return false;
    }

    public static boolean isExceptionClass(PyClass cls) {
        return cls instanceof PyClassException || matchesClass(cls, BASE_EXCEPTION, new HashSet<>());
    }

    public static String errorTypeOf(PyObject exc) {
        if (exc instanceof PyInstance i)     return i.pyClass.name;
        if (exc instanceof PyBaseException p) return p.getErrorType();
        return "";
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <T> T reraise(PyObject obj){
        if (obj instanceof PyBaseException exc) exc.raise();
        throw new PyUnwind(obj);
    }

    public static Integer systemExitCode(PyObject exc) {
        if (!matches(exc, "SystemExit")) return null;
        if (exc instanceof BaseExceptions.PySystemExit se)
            return se.getExitCode();
        if (exc instanceof PyInstance inst) {
            PyObject a = inst.attrs.get("args");
            if (a instanceof PyTuple t) {
                if (t.getInner().length == 0) return 0;
                if (t.getInner()[0] instanceof PyInt i) return i.value.toInt();
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public static String formatError(PyObject payload) {
        if (payload instanceof PyInstance inst) {
            String type = inst.pyClass.name;
            PyObject msg = inst.attrs.get("message");
            if (msg == null || msg == PyNone.INSTANCE) return type;
            return type + ": " + msg.pyDanderStr();
        }
        if (payload instanceof PyBaseException pe) {
            String msg = pe.getMessage();
            return (msg == null || msg.isEmpty()) ? pe.getErrorType() : pe.getErrorType() + ": " + msg;
        }
        return String.valueOf(payload);
    }
}
