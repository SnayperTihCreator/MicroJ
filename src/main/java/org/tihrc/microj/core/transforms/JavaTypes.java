package org.tihrc.microj.core.transforms;

import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.objects.PyJavaClass;
import org.tihrc.microj.types.objects.PyJavaObject;

public final class JavaTypes {
    private JavaTypes() {}

    private static final ClassValue<PyJavaClass> CACHE = new ClassValue<>() {
        @Override
        protected PyJavaClass computeValue(Class<?> type) {
            return new PyJavaClass(type);
        }
    };

    public static PyJavaClass of(Class<?> type) {
        return CACHE.get(type);
    }

    public static PyObject wrap(Object host) {
        if (host == null) return PyNone.INSTANCE;
        return new PyJavaObject(host);
    }
}
