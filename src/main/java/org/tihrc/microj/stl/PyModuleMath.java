package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.callables.PyBuiltinFunction;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.primitives.PyBool;
import org.tihrc.microj.types.primitives.PyFloat;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.units.SmartFloat;

public class PyModuleMath extends PyModule {

    public PyModuleMath(Interpreter interpreter) {
        super("math");

        // --- Константы ---
        registerAttribute("pi", new PyFloat(new SmartFloat(Math.PI)));
        registerAttribute("e", new PyFloat(new SmartFloat(Math.E)));
        registerAttribute("tau", new PyFloat(new SmartFloat(Math.PI * 2)));
        registerAttribute("inf", PyFloat.INF);
        registerAttribute("nan", PyFloat.NaN);

        // --- Степенные и логарифмические ---
        registerAttribute("sqrt", new PyBuiltinFunction((PyBuiltinFunction.Call1)(ctx, arg) ->
                new PyFloat(new SmartFloat(Math.sqrt(toDouble(arg))))));

        registerAttribute("pow", new PyBuiltinFunction((PyBuiltinFunction.Call2) (ctx, arg1, arg2) ->
                new PyFloat(new SmartFloat(Math.pow(toDouble(arg1), toDouble(arg2))))));

        registerAttribute("exp", new PyBuiltinFunction((PyBuiltinFunction.Call1)(ctx, arg) ->
                new PyFloat(new SmartFloat(Math.exp(toDouble(arg))))));

        registerAttribute("log", new PyBuiltinFunction((PyBuiltinFunction.CallVarArgs)(ctx, args) -> {
            if (args.length == 2) {
                return new PyFloat(new SmartFloat(Math.log(toDouble(args[0])) / Math.log(toDouble(args[1]))));
            }
            return new PyFloat(new SmartFloat(Math.log(toDouble(args[0]))));
        }));

        registerAttribute("log10", new PyBuiltinFunction((PyBuiltinFunction.Call1)(ctx, arg) ->
                new PyFloat(new SmartFloat(Math.log10(toDouble(arg))))));

        registerAttribute("log2", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.log(toDouble(arg)) / Math.log(2)))));

        // --- Тригонометрия ---
        registerAttribute("sin", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.sin(toDouble(arg))))));

        registerAttribute("cos", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.cos(toDouble(arg))))));

        registerAttribute("tan", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.tan(toDouble(arg))))));

        registerAttribute("asin", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.asin(toDouble(arg))))));

        registerAttribute("acos", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.acos(toDouble(arg))))));

        registerAttribute("atan", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.atan(toDouble(arg))))));

        registerAttribute("atan2", new PyBuiltinFunction((PyBuiltinFunction.Call2) (ctx, arg1, arg2) ->
                new PyFloat(new SmartFloat(Math.atan2(toDouble(arg1), toDouble(arg2))))));

        // --- Конвертация углов ---
        registerAttribute("radians", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.toRadians(toDouble(arg))))));

        registerAttribute("degrees", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.toDegrees(toDouble(arg))))));

        // --- Округление и модуль ---
        // В Python floor и ceil возвращают int, а не float!
        registerAttribute("floor", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                PyInt.from((long) Math.floor(toDouble(arg)))));

        registerAttribute("ceil", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                PyInt.from((long) Math.ceil(toDouble(arg)))));

        registerAttribute("trunc", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                PyInt.from((long) toDouble(arg))));

        registerAttribute("fabs", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                new PyFloat(new SmartFloat(Math.abs(toDouble(arg))))));

        registerAttribute("copysign", new PyBuiltinFunction((PyBuiltinFunction.Call2) (ctx, arg1, arg2) ->
                new PyFloat(new SmartFloat(Math.copySign(toDouble(arg1), toDouble(arg2))))));

        registerAttribute("isnan", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                PyBool.from(Double.isNaN(toDouble(arg)))));

        registerAttribute("isinf", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) ->
                PyBool.from(Double.isInfinite(toDouble(arg)))));

        registerAttribute("isclose", new PyBuiltinFunction((PyBuiltinFunction.Call2) (ctx, arg1, arg2) ->
                PyBool.from(Math.abs(toDouble(arg1) - toDouble(arg2)) < 1e-9)));
    }

    @SuppressWarnings("DataFlowIssue")
    private double toDouble(PyObject obj) {
        return Transforms.fromPython(obj, double.class);
    }
}