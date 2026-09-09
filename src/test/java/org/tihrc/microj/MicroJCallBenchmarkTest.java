package org.tihrc.microj;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.primitives.PyInt;

public class MicroJCallBenchmarkTest extends MicroJBenchmarkBase {

    @Test
    void benchmarkCallEmpty() {
        String code = """
                def f():
                    return None

                i = 0
                while i < 100000:
                    f()
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call Empty", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallOneArg() {
        String code = """
                def f(x):
                    return None

                i = 0
                while i < 100000:
                    f(i)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call 1 Arg", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallTwoArgs() {
        String code = """
                def f(x, y):
                    return None

                i = 0
                while i < 100000:
                    f(i, 1)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call 2 Args", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallThreeArgs() {
        String code = """
                def f(x, y, z):
                    return None

                i = 0
                while i < 100000:
                    f(i, 1, 2)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call 3 Args", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallTwoKwargs() {
        String code = """
                def f(x, y):
                    return None

                i = 0
                while i < 100000:
                    f(x=i, y=1)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call 2 Kwargs", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallOneKwarg() {
        String code = """
                def f(x, y):
                    return None

                i = 0
                while i < 100000:
                    f(i, y=1)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call 1 Kwarg", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallThreeKwargs() {
        String code = """
                def f(x, y, z):
                    return None

                i = 0
                while i < 100000:
                    f(x=i, y=1, z=2)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call 3 Kwargs", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallArithmetic() {
        String code = """
                def f(x):
                    return x + 1

                i = 0
                while i < 100000:
                    f(i)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call Arithmetic", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallLoadArg() {
        String code = """
                def f(x):
                    return x

                i = 0
                while i < 100000:
                    f(i)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call LoadArg", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkMethodEmpty() {
        String code = """
                class A:
                    def f(self):
                        return None

                a = A()

                i = 0
                while i < 100000:
                    a.f()
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Method Empty", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkMethodOneArg() {
        String code = """
                class A:
                    def f(self, x):
                        return None

                a = A()

                i = 0
                while i < 100000:
                    a.f(i)
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Method 1 Arg", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkCallUnknownKwarg() {
        String code = """
                def f(x):
                    return None

                i = 0
                while i < 1000:
                    try:
                        f(bad=i)
                    except:
                        0
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs("Call Bad Kwarg", interpreter, script, 10, 20);
    }

    @Test
    void benchmarkFrameWithStack() {
        Interpreter interpreter = new Interpreter();

        benchmarkJava(
                "Frame + Stack",
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        new org.tihrc.microj.units.Frame(
                                java.util.List.of(),
                                new org.tihrc.microj.core.PyObject[0]
                        );
                    }
                },
                100,
                1000
        );
    }
    @Test
    void benchmarkCallPositionalVsKeyword() {
        String positional = """
            def f(x, y):
                return None

            i = 0
            while i < 100000:
                f(i, 1)
                i = i + 1
            """;

        String mixed = """
            def f(x, y):
                return None

            i = 0
            while i < 100000:
                f(i, y=1)
                i = i + 1
            """;

        String keyword = """
            def f(x, y):
                return None

            i = 0
            while i < 100000:
                f(x=i, y=1)
                i = i + 1
            """;

        Interpreter interpreter = new Interpreter();

        var positionalScript = compile(interpreter, positional);
        var mixedScript = compile(interpreter, mixed);
        var keywordScript = compile(interpreter, keyword);

        benchmarkMs(
                "2 Positional",
                interpreter,
                positionalScript,
                10,
                20
        );

        benchmarkMs(
                "1 Pos + 1 Kw",
                interpreter,
                mixedScript,
                10,
                20
        );

        benchmarkMs(
                "2 Keywords",
                interpreter,
                keywordScript,
                10,
                20
        );
    }

    @Test
    void benchmarkKeywordCount() {
        String one = """
            def f(x):
                return None

            i = 0
            while i < 100000:
                f(x=i)
                i = i + 1
            """;

        String two = """
            def f(x, y):
                return None

            i = 0
            while i < 100000:
                f(x=i, y=1)
                i = i + 1
            """;

        String three = """
            def f(x, y, z):
                return None

            i = 0
            while i < 100000:
                f(x=i, y=1, z=2)
                i = i + 1
            """;

        Interpreter interpreter = new Interpreter();

        benchmarkMs(
                "1 Keyword",
                interpreter,
                compile(interpreter, one),
                10,
                20
        );

        benchmarkMs(
                "2 Keywords",
                interpreter,
                compile(interpreter, two),
                10,
                20
        );

        benchmarkMs(
                "3 Keywords",
                interpreter,
                compile(interpreter, three),
                10,
                20
        );
    }

    @Test
    void benchmarkFunctionBodyCost() {
        String empty = """
            def f():
                return None

            i = 0
            while i < 100000:
                f()
                i = i + 1
            """;

        String load = """
            def f(x):
                return x

            i = 0
            while i < 100000:
                f(i)
                i = i + 1
            """;

        String arithmetic = """
            def f(x):
                return x + 1

            i = 0
            while i < 100000:
                f(i)
                i = i + 1
            """;

        Interpreter interpreter = new Interpreter();

        benchmarkMs(
                "Function Empty",
                interpreter,
                compile(interpreter, empty),
                10,
                20
        );

        benchmarkMs(
                "Function Load",
                interpreter,
                compile(interpreter, load),
                10,
                20
        );

        benchmarkMs(
                "Function Arithmetic",
                interpreter,
                compile(interpreter, arithmetic),
                10,
                20
        );
    }

    @Test
    void benchmarkMethodOverhead() {
        String method = """
            class A:
                def f(self):
                    return None

            a = A()

            i = 0
            while i < 100000:
                a.f()
                i = i + 1
            """;

        String function = """
            def f():
                return None

            i = 0
            while i < 100000:
                f()
                i = i + 1
            """;

        Interpreter interpreter = new Interpreter();

        benchmarkMs(
                "Raw Function",
                interpreter,
                compile(interpreter, function),
                10,
                20
        );

        benchmarkMs(
                "Bound Method",
                interpreter,
                compile(interpreter, method),
                10,
                20
        );
    }

    @Test
    void benchmarkFastMapPut() {
        benchmarkJava(
                "FastMap put",
                () -> {
                    var map = new org.tihrc.microj.units.FastMap<org.tihrc.microj.core.PyObject>();

                    for (int i = 0; i < 100000; i++) {
                        map.put("x", org.tihrc.microj.types.primitives.PyInt.from(i));
                    }
                },
                100,
                1000
        );
    }

    @Test
    void benchmarkFastMapGet() {
        var map = new org.tihrc.microj.units.FastMap<org.tihrc.microj.core.PyObject>();
        var value = org.tihrc.microj.types.primitives.PyInt.from(123);

        map.put("x", value);

        benchmarkJava(
                "FastMap get",
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        map.get("x");
                    }
                },
                100,
                1000
        );
    }

    @Test
    void benchmarkFastMapContains() {
        var map = new org.tihrc.microj.units.FastMap<org.tihrc.microj.core.PyObject>();
        map.put("x", org.tihrc.microj.types.primitives.PyInt.from(123));

        benchmarkJava(
                "FastMap contains",
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        map.containsKey("x");
                    }
                },
                100,
                1000
        );
    }

    @Test
    void benchmarkParamNamesContains() {
        var params = java.util.Set.of("x", "y", "z");

        benchmarkJava(
                "ParamNames contains",
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        params.contains("x");
                    }
                },
                100,
                1000
        );
    }

    @Test
    void benchmarkFastMapCreation() {
        benchmarkJava(
                "FastMap creation",
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        new org.tihrc.microj.units.FastMap<>();
                    }
                },
                100,
                1000
        );
    }

    @Test
    void benchmarkFastMapBinding() {
        benchmarkJava(
                "FastMap binding",
                () -> {
                    var map = new org.tihrc.microj.units.FastMap<org.tihrc.microj.core.PyObject>();

                    var x = org.tihrc.microj.types.primitives.PyInt.from(1);
                    var y = org.tihrc.microj.types.primitives.PyInt.from(2);

                    for (int i = 0; i < 100000; i++) {
                        map.put("x", x);
                        map.put("y", y);
                        map.get("x");
                        map.get("y");
                    }
                },
                100,
                1000
        );
    }

    @Test
    void benchmarkCallPreparation() {
        PyInt x = PyInt.from(123);
        PyInt y = PyInt.from(456);

        benchmarkJava(
                "Prepare kwargs",
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        var kwargs = new org.tihrc.microj.units.FastMap<PyObject>();
                        kwargs.put("x", x);
                        kwargs.put("y", y);
                    }
                },
                100,
                1000
        );

        benchmarkJava(
                "Prepare args + kwargs",
                () -> {
                    for (int i = 0; i < 100000; i++) {
                        PyObject[] args = new PyObject[2];
                        args[0] = x;
                        args[1] = y;

                        var kwargs = new org.tihrc.microj.units.FastMap<PyObject>();
                        kwargs.put("x", x);
                        kwargs.put("y", y);
                    }
                },
                100,
                1000
        );
    }

    @Test
    void dumpCallBytecode() {
        Interpreter interpreter = new Interpreter();

        dump("EMPTY", compile(interpreter, """
            def f():
                return None

            f()
            """));

        dump("ONE ARG", compile(interpreter, """
            def f(x):
                return x

            f(123)
            """));

        dump("TWO POSITIONAL", compile(interpreter, """
            def f(x, y):
                return None

            f(123, 456)
            """));

        dump("ONE KWARG", compile(interpreter, """
            def f(x):
                return None

            f(x=123)
            """));

        dump("TWO KWARGS", compile(interpreter, """
            def f(x, y):
                return None

            f(x=123, y=456)
            """));

        dump("MIXED", compile(interpreter, """
            def f(x, y):
                return None

            f(123, y=456)
            """));
    }

    private void dump(String name, InstructionGenerator.CompiledScript script) {
        System.out.println();
        System.out.println("===== " + name + " =====");

        for (int i = 0; i < script.code().size(); i++) {
            System.out.printf("%3d: %s%n", i, script.code().get(i));
        }

        System.out.println("----- constants -----");

        for (int i = 0; i < script.constants().length; i++) {
            System.out.printf(
                    "%3d: %s%n",
                    i,
                    script.constants()[i]
            );
        }
    }
}