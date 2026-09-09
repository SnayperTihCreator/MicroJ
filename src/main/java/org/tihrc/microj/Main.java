package org.tihrc.microj;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.*;
import org.tihrc.microj.types.collections.PyString;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyNone;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        PyModule gameModule = new PyModule("game");
        gameModule.registerAttribute("print", new PyBuiltinFunction((ctx, kwargs, args2) -> {
            System.out.println("[GAME API]: " + args2[0]);
            return PyNone.INSTANCE;
        }));
        gameModule.registerAttribute("spawn_cow", new PyBuiltinFunction((ctx, kwargs, args2) -> {
            int x = ((PyInt) args2[0]).value.toInt();
            int y = ((PyInt) args2[1]).value.toInt();
            int z = ((PyInt) args2[2]).value.toInt();
            System.out.println("Spawning cow at " + x + ", " + y + ", " + z);
            return PyNone.INSTANCE;
        }));
        gameModule.registerAttribute("version", new PyString("1.0.0"));
        gameModule.registerAttribute("log_calls", new PyBuiltinFunction((ctx, kwargs, args1) -> {
            PyObject originalFunc = args1[0];
            return new PyBuiltinFunction((ctx2, kwargs2, args2) -> {
                System.out.println("[Java Decorator]: Вызов функции...");
                PyObject result = ctx2.callSync(originalFunc, args2);
                System.out.println("[Java Decorator]: Функция отработала! Результат: " + result);
                return result;
            });
        }));
        interpreter.getLib().registerScript(gameModule);
        String scriptPath = "scripts/main.py";
        InputStream stream = Main.class.getClassLoader().getResourceAsStream(scriptPath);
        interpreter.run(stream);
    }
}