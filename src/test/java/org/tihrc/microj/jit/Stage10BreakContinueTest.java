package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.primitives.PyInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage10BreakContinueTest extends BaseStageTest{

    @Test
    void testBreak() {
        String code = """
                i = 0
                while i < 10:
                    if i == 5:
                        break
                    i = i + 1
                i
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(5), result);
    }

    @Test
    void testContinue() {
        String code = """
                total = 0
                for i in range(10):
                    if i == 5:
                        continue
                    total = total + i
                total
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(40), result);
    }

    @Test
    void testTypeError() {
        String code = """
                try:
                    x = 1 + "a"
                except:
                    200
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(200), result);
    }
}