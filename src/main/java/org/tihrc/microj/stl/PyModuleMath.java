package org.tihrc.microj.stl;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.*;
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
        registerAttribute("sqrt", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.sqrt(toDouble(args[0]))))));

        registerAttribute("pow", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.pow(toDouble(args[0]), toDouble(args[1]))))));

        registerAttribute("exp", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.exp(toDouble(args[0]))))));

        registerAttribute("log", new PyBuiltinFunction((ctx, kwargs, args) -> {
            if (args.length == 2) {
                return new PyFloat(new SmartFloat(Math.log(toDouble(args[0])) / Math.log(toDouble(args[1]))));
            }
            return new PyFloat(new SmartFloat(Math.log(toDouble(args[0]))));
        }));

        registerAttribute("log10", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.log10(toDouble(args[0]))))));

        registerAttribute("log2", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.log(toDouble(args[0])) / Math.log(2)))));

        // --- Тригонометрия ---
        registerAttribute("sin", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.sin(toDouble(args[0]))))));

        registerAttribute("cos", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.cos(toDouble(args[0]))))));

        registerAttribute("tan", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.tan(toDouble(args[0]))))));

        registerAttribute("asin", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.asin(toDouble(args[0]))))));

        registerAttribute("acos", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.acos(toDouble(args[0]))))));

        registerAttribute("atan", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.atan(toDouble(args[0]))))));

        registerAttribute("atan2", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.atan2(toDouble(args[0]), toDouble(args[1]))))));

        // --- Конвертация углов ---
        registerAttribute("radians", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.toRadians(toDouble(args[0]))))));

        registerAttribute("degrees", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.toDegrees(toDouble(args[0]))))));

        // --- Округление и модуль ---
        // В Python floor и ceil возвращают int, а не float!
        registerAttribute("floor", new PyBuiltinFunction((ctx, kwargs, args) ->
                PyInt.from((long) Math.floor(toDouble(args[0])))));

        registerAttribute("ceil", new PyBuiltinFunction((ctx, kwargs, args) ->
                PyInt.from((long) Math.ceil(toDouble(args[0])))));

        registerAttribute("trunc", new PyBuiltinFunction((ctx, kwargs, args) ->
                PyInt.from((long) toDouble(args[0]))));

        registerAttribute("fabs", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.abs(toDouble(args[0]))))));

        registerAttribute("copysign", new PyBuiltinFunction((ctx, kwargs, args) ->
                new PyFloat(new SmartFloat(Math.copySign(toDouble(args[0]), toDouble(args[1]))))));

        registerAttribute("isnan", new PyBuiltinFunction((ctx, kwargs, args) ->
                PyBool.from(Double.isNaN(toDouble(args[0])))));

        registerAttribute("isinf", new PyBuiltinFunction((ctx, kwargs, args) ->
                PyBool.from(Double.isInfinite(toDouble(args[0])))));

        registerAttribute("isclose", new PyBuiltinFunction((ctx, kwargs, args) ->
                PyBool.from(Math.abs(toDouble(args[0]) - toDouble(args[1])) < 1e-9)));
    }

    // Вспомогательный метод: безопасно достает double из PyInt или PyFloat
    private double toDouble(PyObject obj) {
        return Transforms.fromPython(obj, double.class);
    }
}