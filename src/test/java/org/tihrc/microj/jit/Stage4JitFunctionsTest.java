package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.primitives.PyInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage4JitFunctionsTest extends BaseStageTest{

    @Test
    void testSimpleFunctionCall() {
        String code = """
                def f():
                    return 42

                f()
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(42), result);
    }

    @Test
    void testFunctionWithArgs() {
        String code = """
                def add(a, b):
                    return a + b

                add(10, 20)
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(30), result);
    }

    @Test
    void testFunctionWithKwargs() {
        String code = """
                def add(a, b):
                    return a + b

                add(a=10, b=20)
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(30), result);
    }

    @Test
    void testFunctionWithMixedArgs() {
        String code = """
                def add(a, b):
                    return a + b

                add(10, b=20)
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(30), result);
    }

    @Test
    void testIfElseControlFlow() {
        String code = """
                x = 5
                if x > 3:
                    100
                else:
                    200
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(100), result);
    }

    @Test
    void testWhileLoopWithFunction() {
        // Цикл while с вызовом JIT-функции
        String code = """
                def inc(i):
                    return i + 1

                i = 0
                while i < 100:
                    i = inc(i)
                i
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(100), result);
    }

    @Test
    void testNestedFunctionCalls() {
        String code = """
                def f(x):
                    return x + 1

                def g(x):
                    return f(x) * 2

                g(10)
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(22), result);
    }
    @Test
    void testDecorator() {
        String code = """
                def my_decorator(f):
                    def wrapper():
                        return f() + 1
                    return wrapper

                @my_decorator
                def my_func():
                    return 41

                my_func()
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(42), result);
    }
}