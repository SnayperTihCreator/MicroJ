package org.tihrc.microj.backend.jvm;

public class MicroJClassLoader extends ClassLoader {
    public MicroJClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> defineClass(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }
}
