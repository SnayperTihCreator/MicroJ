package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.primitives.PyInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage6JitClassTest extends BaseStageTest{
    @Test
    void testSimpleClassAndMethod() {
        String code = """
                class A:
                    def f(self, x):
                        return x + 1

                a = A()
                a.f(10)
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(11), result);
    }

    @Test
    void benchmarkBuiltins() {
        System.out.println("=== BUILTIN BENCHMARK ===");

        // Проверяем вызов встроенной функции len
        runJitBenchmark("JIT Call len(x)", """
                x = [1, 2, 3]
                i = 0
                while i < 100000:
                    len(x)
                    i = i + 1
                """);

        // Проверяем вызов встроенной функции abs
        runJitBenchmark("JIT Call abs(x)", """
                x = -1
                i = 0
                while i < 100000:
                    abs(x)
                    i = i + 1
                """);

        System.out.println("=========================");
    }

    @Test
    void testOperatorOverloading() {
        String code = """
                class MyNum:
                    def __init__(self, val):
                        self.val = val
                    def __add__(self, other):
                        return MyNum(self.val + other.val)

                a = MyNum(10)
                b = MyNum(20)
                c = a + b
                c.val
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(30), result);
    }

}
