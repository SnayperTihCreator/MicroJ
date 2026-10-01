package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.primitives.PyInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage11FinalTest extends BaseStageTest{

    @Test
    void testTuplesAndDicts() {
        String code = """
                t = (1, 2, 3)
                d = {"a": 10, "b": 20}
                t[0] + d["a"] + d["b"]
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(31), result); // 1 + 10 + 20 = 31
    }

    @Test
    void testUnpackingAndSubscript() {
        String code = """
                a, b = 100, 200
                l = [0, 0]
                l[0] = a
                l[1] = b
                l[0] + l[1]
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(300), result);
    }

    @Test
    void testStringMethods() {
        String code = """
                s = "hello"
                s.upper()
                """;
        PyObject result = compileAndRun(code);
        assertEquals(new PyString("HELLO"), result);
    }

    @Test
    void testForLoopBreakContinue() {
        String code = """
                total = 0
                for i in range(10):
                    if i == 5:
                        break
                    if i == 2:
                        continue
                    total = total + i
                total
                """;
        PyObject result = compileAndRun(code);
        // i=0(0), i=1(1), i=2(skip), i=3(3), i=4(4) -> total = 8
        assertEquals(PyInt.from(8), result);
    }

    @Test
    void testTryExceptTypeError() {
        String code = """
                try:
                    x = 1 + "a"
                except:
                    999
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(999), result);
    }

    @Test
    void testClassOperatorOverloading() {
        String code = """
                class Vec:
                    def __init__(self, x, y):
                        self.x = x
                        self.y = y
                        
                    def __add__(self, other):
                        return Vec(self.x + other.x, self.y + other.y)
                        
                a = Vec(1, 2)
                b = Vec(3, 4)
                c = a + b
                c.x + c.y
                """;
        PyObject result = compileAndRun(code);
        // (1+3) + (2+4) = 10
        assertEquals(PyInt.from(10), result);
    }

    @Test
    void testClosureAndDecorator() {
        String code = """
                def logger(f):
                    def wrapper():
                        return f() + 1
                    return wrapper

                @logger
                def get_value():
                    return 41

                get_value()
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(42), result);
    }
}