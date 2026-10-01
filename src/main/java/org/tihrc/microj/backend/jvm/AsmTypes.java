package org.tihrc.microj.backend.jvm;

public interface AsmTypes {

    String PREFIX           = "org/tihrc/microj/";
    String PREFIX2          = "org.tihrc.microj.";

    String PY_OBJECT        = "org/tihrc/microj/core/PyObject";
    String PY_LIST          = "org/tihrc/microj/types/collections/PyList";
    String PY_TUPLE         = "org/tihrc/microj/types/collections/PyTuple";
    String PY_DICT          = "org/tihrc/microj/types/collections/PyDict";
    String PY_BOOL          = "org/tihrc/microj/types/primitives/PyBool";
    String PY_MODULE        = "org/tihrc/microj/types/objects/PyModule";
    String PY_UNWIND        = "org/tihrc/microj/core/exceptions/PyUnwind";
    String FAST_MAP         = "org/tihrc/microj/units/FastMap";
    String RUN_EXECUTER     = "org/tihrc/microj/core/RuntimeExecuter";
    String PY_CELL          = "org/tihrc/microj/types/core/PyCell";
    String PY_BASE_EXCEPTION= "org/tihrc/microj/core/exceptions/PyBaseException";

    String PYP_NUMBER       = "org/tihrc/microj/core/Protocols$PyNumber";
    String PYP_CONTAINER    = "org/tihrc/microj/core/Protocols$PyContainer";
    String PYP_COMPARABLE   = "org/tihrc/microj/core/Protocols$PyComparable";
    String PYP_CALLABLE     = "org/tihrc/microj/core/Protocols$PyCallable";

    String JVM_SCRIPT       = "org/tihrc/microj/backend/jvm/JvmScript";
    String JVM_COMPILER     = "org/tihrc/microj/backend/jvm/JvmCompiler";
    String JIT_FUNCTION     = "org/tihrc/microj/backend/jvm/JitFunction";

    String OBJECT           = "java/lang/Object";
    String STRING           = "java/lang/String";
    String MAP              = "java/util/Map";
    String METHOD_HANDLE    = "java/lang/invoke/MethodHandle";

    String PYOBJECT_DESC = "L%s;".formatted(PY_OBJECT);
    String CTX_DESC = "L%s;".formatted(RUN_EXECUTER);

    String PYARR_OBJECT = "[L%s;".formatted(PY_OBJECT);
    String STRING_ARR   = "[Ljava/lang/String;";
    String FUNC_METHOD_DESC = "(" + CTX_DESC + PYARR_OBJECT + PYOBJECT_DESC
            + PYARR_OBJECT + STRING_ARR + PYARR_OBJECT + ")" + PYOBJECT_DESC;

    static String internalName(Class<?> c) {
        return c.getName().replace(".", "/");
    }

}
