package org.tihrc.microj.types.core;

import org.tihrc.microj.core.RuntimeExecuter;

import java.util.function.Supplier;

public final class PyContext {
    private static final ThreadLocal<RuntimeExecuter> CURRENT = new ThreadLocal<>();

    public static <T> T with(RuntimeExecuter ctx, Supplier<T> body){
        CURRENT.set(ctx);
        try {
            return body.get();
        } finally {
            CURRENT.remove();
        }
    }

    public static RuntimeExecuter current() {
        RuntimeExecuter vm = CURRENT.get();
        if (vm == null)
            throw new IllegalStateException("No active RuntimeExecuter (repr called outside execution?)");
        return vm;
    }

    public static RuntimeExecuter currentOrNull() {
        return CURRENT.get();
    }
}
