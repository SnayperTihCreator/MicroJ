package org.tihrc.microj;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.ExceptionsRegistry;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.callables.PyBuiltinFunction;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.core.PyNone;

import java.io.InputStream;

public class Main {
    public record Vec2(int x, int y) {
        public Vec2 add(Vec2 o) { return new Vec2(x + o.x, y + o.y); }
    }

    @SuppressWarnings("DataFlowIssue")
    public static void main(String[] ignored) {
        Interpreter interpreter = new Interpreter();
        PyModule gameModule = new PyModule("game");
        gameModule.registerAttribute("print", new PyBuiltinFunction((PyBuiltinFunction.Call1) (ctx, arg) -> {
            System.out.println("[GAME API]: " + arg);
            return PyNone.INSTANCE;
        }));
        gameModule.registerAttribute("spawn_cow", new PyBuiltinFunction((PyBuiltinFunction.CallVarArgs) (ctx, args) -> {
            int x = Transforms.fromPython(args[0], int.class);
            int y = Transforms.fromPython(args[1], int.class);
            int z = Transforms.fromPython(args[2], int.class);
            System.out.println("Spawning cow at " + x + ", " + y + ", " + z);
            return PyNone.INSTANCE;
        }));
        gameModule.registerAttribute("version", new PyString("1.0.0"));
        gameModule.registerAttribute("log_calls", new PyBuiltinFunction((PyBuiltinFunction.CallVarKwArgs) (ctx, args, kwargs) -> {
            PyObject originalFunc = args[0];
            return new PyBuiltinFunction((PyBuiltinFunction.CallVarKwArgs)(ctx2, args2, kwargs2) -> {
                System.out.println("[Java Decorator]: Вызов функции...");
                PyObject result = ctx2.callSync(originalFunc, args2);
                System.out.println("[Java Decorator]: Функция отработала! Результат: " + result);
                return result;
            });
        }));
        interpreter.getLib().registerScript(gameModule);

        interpreter.javaClass("Bridge", Bridge.class);
        interpreter.javaClass("Vec2", Vec2.class);
        interpreter.bind("v", new Vec2(1, 1));

        String scriptPath = "scripts/main.py";
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(scriptPath);
        interpreter.run(stream);
    }
}