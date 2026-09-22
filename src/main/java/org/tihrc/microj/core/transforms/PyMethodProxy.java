package org.tihrc.microj.core.transforms;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.types.primitives.PyNone;
import org.tihrc.microj.units.FastMap;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PyMethodProxy extends PyObject implements Protocols.PyCallable {
    private final MethodHandle handle;
    private final Object fastProxy;
    private final int paramCount;
    private final Class<?>[] paramTypes;
    private final String name;
    private final boolean useArgs;
    private final boolean useKwargs;

    public PyMethodProxy(MethodHandle handle, Object fastProxy, int paramCount, Class<?>[] paramTypes, String name, boolean useArgs, boolean useKwargs) {
        this.handle = handle;
        this.fastProxy = fastProxy;
        this.paramCount = paramCount;
        this.paramTypes = paramTypes;
        this.name = name;
        this.useArgs = useArgs;
        this.useKwargs = useKwargs;
    }

    private PyObject invokeRaw(Object self, PyObject[] posArgs, Map<String, PyObject> kwargs) {
        try {
            if (useArgs && useKwargs) {
                return Transforms.toPython(handle.invoke(self, posArgs, kwargs == null ? new FastMap<>() : kwargs));
            } else if (useArgs) {
                return Transforms.toPython(handle.invoke(self, posArgs));
            } else if (useKwargs) {
                return Transforms.toPython(handle.invoke(self, kwargs == null ? new FastMap<>() : kwargs));
            }
            return Transforms.toPython(handle.invoke(self));
        } catch (Throwable e) {
            if (e instanceof PyUnwind) throw (PyUnwind) e;
            throw new RuntimeException("Failed to call exported method (raw): " + e.getMessage(), e);
        }
    }


    @Override
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        if (useArgs || useKwargs) {
            Object self = args.length > 0 ? args[0] : null;
            PyObject[] posArgs = new PyObject[args.length > 0 ? args.length - 1 : 0];
            if (posArgs.length > 0) System.arraycopy(args, 1, posArgs, 0, posArgs.length);
            return invokeRaw(self, posArgs, kwargs);
        }
        try {
            Object self = args[0];
            Object result;

            if (fastProxy != null) {
                switch (paramCount) {
                    case 0:
                        result = ((PyTypeExporter.PyFunc0) fastProxy).call(self);
                        break;
                    case 1:
                        Object arg1 = args.length > 1 ? Transforms.fromPython(args[1], paramTypes[0]) : null;
                        result = ((PyTypeExporter.PyFunc1) fastProxy).call(self, arg1);
                        break;
                    case 2:
                        Object arg1_2 = args.length > 1 ? Transforms.fromPython(args[1], paramTypes[0]) : null;
                        Object arg2_2 = args.length > 2 ? Transforms.fromPython(args[2], paramTypes[1]) : null;
                        result = ((PyTypeExporter.PyFunc2) fastProxy).call(self, arg1_2, arg2_2);
                        break;
                    default:
                        return new Exceptions.PyTypeError("Unsupported arguments count").raise();
                }
            } else {
                List<Object> javaArgs = new ArrayList<>();
                javaArgs.add(self);
                for (int i = 0; i < paramCount; i++) {
                    if (i + 1 < args.length) {
                        javaArgs.add(Transforms.fromPython(args[i + 1], paramTypes[i]));
                    } else {
                        javaArgs.add(Transforms.fromPython(PyNone.INSTANCE, paramTypes[i]));
                    }
                }
                result = handle.invokeWithArguments(javaArgs);
            }

            return Transforms.toPython(result);
        } catch (Throwable e) {
            if (e instanceof PyUnwind) throw (PyUnwind) e;
            throw new RuntimeException("Failed to call exported method: " + e.getMessage(), e);
        }
    }

    @Override
    public PyObject pyDanderCallBound(
            RuntimeExecuter ctx,
            PyObject self,
            Map<String, PyObject> kwargs,
            PyObject[] args
    ) {
        if (useArgs || useKwargs) {
            return invokeRaw(self, args, kwargs);
        }
        try {
            if (fastProxy == null) {
                Object[] javaArgs = new Object[paramCount + 1];
                javaArgs[0] = self;

                for (int i = 0; i < paramCount; i++) {
                    javaArgs[i + 1] = Transforms.fromPython(
                            i < args.length ? args[i] : PyNone.INSTANCE,
                            paramTypes[i]
                    );
                }

                return Transforms.toPython(handle.invokeWithArguments(javaArgs));
            }

            Object result = switch (paramCount) {
                case 0 -> ((PyTypeExporter.PyFunc0) fastProxy).call(self);

                case 1 -> ((PyTypeExporter.PyFunc1) fastProxy).call(
                        self,
                        args.length > 0 ? Transforms.fromPython(args[0], paramTypes[0]) : null
                );

                case 2 -> ((PyTypeExporter.PyFunc2) fastProxy).call(
                        self,
                        args.length > 0 ? Transforms.fromPython(args[0], paramTypes[0]) : null,
                        args.length > 1 ? Transforms.fromPython(args[1], paramTypes[1]) : null
                );

                default -> new Exceptions.PyTypeError(
                        "Unsupported arguments count"
                ).raise();
            };

            return Transforms.toPython(result);

        } catch (Throwable e) {
            if (e instanceof PyUnwind)
                throw (PyUnwind) e;
            throw new RuntimeException(
                    "Failed to call exported method: " + e.getMessage(), e
            );
        }
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        if (useArgs || useKwargs) {
            Object self = args.length > 0 ? args[0] : null;
            PyObject[] posArgs = new PyObject[args.length > 0 ? args.length - 1 : 0];
            if (posArgs.length > 0) System.arraycopy(args, 1, posArgs, 0, posArgs.length);

            Map<String, PyObject> kwargs = new FastMap<>();
            if (kwNames != null && kwValues != null) {
                for (int i = 0; i < kwNames.length; i++) {
                    kwargs.put(kwNames[i], kwValues[i]);
                }
            }
            return invokeRaw(self, posArgs, kwargs);
        }
        if ((kwNames == null || kwNames.length == 0) && fastProxy != null) {
            try {
                Object self = args.length > 0 ? args[0] : null;
                Object result = switch (paramCount) {
                    case 0 -> ((PyTypeExporter.PyFunc0) fastProxy).call(self);
                    case 1 -> ((PyTypeExporter.PyFunc1) fastProxy).call(
                            self,
                            args.length > 1 ? Transforms.fromPython(args[1], paramTypes[0]) : null
                    );
                    case 2 -> ((PyTypeExporter.PyFunc2) fastProxy).call(
                            self,
                            args.length > 1 ? Transforms.fromPython(args[1], paramTypes[0]) : null,
                            args.length > 2 ? Transforms.fromPython(args[2], paramTypes[1]) : null
                    );
                    default -> throw new RuntimeException("Unsupported param count in fast path");
                };
                return Transforms.toPython(result);
            } catch (Throwable e) {
                if (e instanceof PyUnwind) throw (PyUnwind) e;
                throw new RuntimeException("Failed to call exported method (fast): " + e.getMessage(), e);
            }
        }

        return Protocols.PyCallable.super.pyDanderCallFast(ctx, args, kwNames, kwValues);
    }

    @Override
    public String toString() {
        return "PyMethodProxy<%s>".formatted(name);
    }

    @Override
    public String pyDanderRepr() {
        return "<built-in method>";
    }
}
