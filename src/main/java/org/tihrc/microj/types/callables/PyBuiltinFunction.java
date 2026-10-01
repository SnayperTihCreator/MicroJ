package org.tihrc.microj.types.callables;

import org.tihrc.microj.core.Protocols;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyExport;
import org.tihrc.microj.units.FastMap;

import java.util.Map;

public class PyBuiltinFunction extends PyObject implements Protocols.PyCallable {
    public interface Callable {}
    public interface Call0 extends Callable { PyObject call(RuntimeExecuter ctx); }
    public interface Call1 extends Callable { PyObject call(RuntimeExecuter ctx, PyObject arg); }
    public interface Call2 extends Callable { PyObject call(RuntimeExecuter ctx, PyObject arg1, PyObject arg2); }
    public interface Call3 extends Callable { PyObject call(RuntimeExecuter ctx, PyObject arg1, PyObject arg2, PyObject arg3); }
    public interface CallVarArgs extends Callable { PyObject call(RuntimeExecuter ctx, PyObject[] args); }
    public interface CallVarKwArgs extends Callable { PyObject call(RuntimeExecuter ctx, PyObject[] args, Map<String, PyObject> kwargs); }

    public enum CallableType{
        CALL0(0), CALL1(1), CALL2(2), CALL3(3), CALLVARARGS(-1), CALLVARKWARGS(-1);

        public final int count;
        CallableType(int count) { this.count = count; }
    }
    private final Callable func;
    private final CallableType type;

    public PyBuiltinFunction(Call0 func) {this.func = func; type = CallableType.CALL0; }
    public PyBuiltinFunction(Call1 func) {this.func = func; type = CallableType.CALL1; }
    public PyBuiltinFunction(Call2 func) {this.func = func; type = CallableType.CALL2; }
    public PyBuiltinFunction(Call3 func) {this.func = func; type = CallableType.CALL3; }
    public PyBuiltinFunction(CallVarArgs func) {this.func = func; type = CallableType.CALLVARARGS; }
    public PyBuiltinFunction(CallVarKwArgs func) {this.func = func; type = CallableType.CALLVARKWARGS; }


    @Override
    @PyExport(name = "__call__")
    public PyObject pyDanderCall(RuntimeExecuter ctx, Map<String, PyObject> kwargs, PyObject... args) {
        if (type == CallableType.CALLVARKWARGS) {
            return ((CallVarKwArgs) func).call(ctx, args, kwargs);
        }
        return pyDanderCallFast(ctx, args, null, null);
    }

    @Override
    public PyObject pyDanderCallFast(RuntimeExecuter ctx, PyObject[] args, String[] kwNames, PyObject[] kwValues) {
        if (kwNames != null && kwNames.length > 0) {
            if (type == CallableType.CALLVARKWARGS) {
                Map<String, PyObject> kwargs = new FastMap<>();
                for (int i = 0; i < kwNames.length; i++) {
                    kwargs.put(kwNames[i], kwValues[i]);
                }
                return ((CallVarKwArgs) func).call(ctx, args, kwargs);
            }
            return new Exceptions.PyTypeError("takes no keyword arguments").raise();
        }

        if (type.count >= 0){
            if (type.count != args.length)
                return new Exceptions.PyTypeError("expected %s arguments, got %s".formatted(type.count, args.length)).raise();
        }
        return switch (type){
            case CALL0 -> ((Call0)func).call(ctx);
            case CALL1 -> ((Call1)func).call(ctx, args[0]);
            case CALL2 -> ((Call2)func).call(ctx, args[0], args[1]);
            case CALL3 -> ((Call3)func).call(ctx, args[0], args[1], args[2]);
            case CALLVARARGS -> ((CallVarArgs)func).call(ctx, args);
            case CALLVARKWARGS -> ((CallVarKwArgs)func).call(ctx, args, FastMap.empty());
        };
    }

    @Override
    public String pyDanderRepr() {
        return "<built-in function>";
    }

    @Override
    public String toString() {
        return "PyBuiltinFunction";
    }
}
