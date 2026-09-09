package org.tihrc.microj;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;

public class MicroJBenchmarkTest extends MicroJBenchmarkBase {

    // =========================================================
    // CONSTANTS
    // =========================================================

    @Test
    void benchmarkConstants() {
        String code = """
                x = 123456789
                y = 123456789
                z = "hello"
                a = "hello"
                b = 123456789
                c = "hello"
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        System.out.println(
                "Constants in pool: " + script.constants().length
        );

        benchmark(
                "Constants",
                interpreter,
                script,
                20,
                10000
        );
    }

    @Test
    void benchmarkLoadConst() {
        String code = """
                x = 123456789
                x = 123456789
                x = 123456789
                x = 123456789
                x = 123456789
                x = 123456789
                x = 123456789
                x = 123456789
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "LoadConst",
                interpreter,
                script,
                20,
                10000
        );
    }

    // =========================================================
    // NAME ACCESS
    // =========================================================

    @Test
    void benchmarkNameAccess() {
        String code = """
                x = 0
                y = x
                y = x
                y = x
                y = x
                y = x
                y = x
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "NameAccess",
                interpreter,
                script,
                20,
                10000
        );
    }

    @Test
    void benchmarkNameLoop() {
        String code = """
                x = 0
                i = 0
                while i < 100000:
                    x = i
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "NameLoop",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkLoadNameLoop() {
        String code = """
                x = 123
                i = 0

                while i < 100000:
                    y = x
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "LoadNameLoop",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkStoreNameLoop() {
        String code = """
                x = 123
                i = 0

                while i < 100000:
                    x = i
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "StoreNameLoop",
                interpreter,
                script,
                10,
                20
        );
    }

    // =========================================================
    // ARITHMETIC
    // =========================================================

    @Test
    void benchmarkArithmetic() {
        String code = """
                x = 0
                while x < 100000:
                    x = x + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "Arithmetic",
                interpreter,
                script,
                10,
                30
        );
    }

    @Test
    void benchmarkArithmeticChain() {
        String code = """
                a = 1
                b = 2
                c = a + b
                d = c + 3
                e = d + 4
                f = e + 5
                g = f * 2
                h = g - 3
                i = h / 2
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "ArithmeticChain",
                interpreter,
                script,
                20,
                10000
        );
    }

    @Test
    void benchmarkIntegerOperations() {
        String code = """
                a = 123
                b = 456
                c = a + b
                d = a - b
                e = a * b
                f = b / a
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "IntegerOperations",
                interpreter,
                script,
                20,
                10000
        );
    }

    // =========================================================
    // FLOAT
    // =========================================================

    @Test
    void benchmarkFloatArithmetic() {
        String code = """
                a = 1.5
                b = 2.5
                c = a + b
                d = a - b
                e = a * b
                f = a / b
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "FloatArithmetic",
                interpreter,
                script,
                20,
                10000
        );
    }

    // =========================================================
    // STRING
    // =========================================================

    @Test
    void benchmarkStringConstants() {
        String code = """
                a = "hello world"
                b = "hello world"
                c = "hello world"
                d = "hello world"
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        System.out.println(
                "String constants: " + script.constants().length
        );

        benchmark(
                "StringConstants",
                interpreter,
                script,
                20,
                10000
        );
    }

    @Test
    void benchmarkStringOperations() {
        String code = """
                s = "hello world"
                a = s.upper()
                b = s.lower()
                c = s.strip()
                d = s.find("world")
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "StringOperations",
                interpreter,
                script,
                20,
                10000
        );
    }

    // =========================================================
    // GETATTR
    // =========================================================

    @Test
    void benchmarkGetAttr() {
        String code = """
                class A:
                    def __init__(self):
                        self.x = 42

                a = A()
                i = 0

                while i < 100000:
                    x = a.x
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "GetAttr",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkGetAttrMethod() {
        String code = """
                class A:
                    def foo(self):
                        return 42

                a = A()
                i = 0

                while i < 100000:
                    x = a.foo
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "GetAttrMethod",
                interpreter,
                script,
                10,
                20
        );
    }

    // =========================================================
    // SUBSCRIPT
    // =========================================================

    @Test
    void benchmarkListAccess() {
        String code = """
                a = [1, 2, 3, 4, 5]
                i = 0

                while i < 100000:
                    x = a[2]
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "ListAccess",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkDictAccess() {
        String code = """
                d = {
                    "a": 1,
                    "b": 2,
                    "c": 3
                }

                i = 0
                while i < 100000:
                    x = d["b"]
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "DictAccess",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkTupleAccess() {
        String code = """
                a = (1, 2, 3, 4, 5)
                i = 0

                while i < 100000:
                    x = a[2]
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "TupleAccess",
                interpreter,
                script,
                10,
                20
        );
    }


    @Test
    void benchmarkListCreate() {
        String code = """
                a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "ListCreate",
                interpreter,
                script,
                20,
                10000
        );
    }

    // =========================================================
    // DICT
    // =========================================================

    @Test
    void benchmarkDictCreate() {
        String code = """
                d = {
                    "a": 1,
                    "b": 2,
                    "c": 3,
                    "d": 4,
                    "e": 5
                }
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "DictCreate",
                interpreter,
                script,
                20,
                10000
        );
    }

    // =========================================================
    // TUPLE
    // =========================================================

    @Test
    void benchmarkTupleCreate() {
        String code = """
                a = (1, 2, 3, 4, 5)
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "TupleCreate",
                interpreter,
                script,
                20,
                10000
        );
    }

    // =========================================================
    // BOOLEAN / CONDITIONS
    // =========================================================

    @Test
    void benchmarkCondition() {
        String code = """
                i = 0
                x = 0

                while i < 100000:
                    if i < 50000:
                        x = 1
                    else:
                        x = 2
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "Condition",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkAndOr() {
        String code = """
                a = True
                b = False
                i = 0

                while i < 100000:
                    x = a and b
                    y = a or b
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "AndOr",
                interpreter,
                script,
                10,
                20
        );
    }

    // =========================================================
    // CLASS CREATION
    // =========================================================

    @Test
    void benchmarkClassCreation() {
        String code = """
                class A:
                    def foo(self):
                        return 42

                a = A()
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmark(
                "ClassCreation",
                interpreter,
                script,
                20,
                10000
        );
    }

    @Test
    void benchmarkDirectFindAttribute() {
        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, """
                class A:
                    def foo(self):
                        return 42

                a = A()
                """);

        // Получаем объект a через globals после одного запуска.
        interpreter.run(script);

        PyObject obj = interpreter
                .getLib()
                .getBuiltins();

        benchmarkJava(
                "Java findAttribute",
                () -> obj.findAttribute("__repr__"),
                10000,
                1_000_000
        );
    }
}