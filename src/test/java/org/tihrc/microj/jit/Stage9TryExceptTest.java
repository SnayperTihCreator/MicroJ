package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.primitives.PyInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage9TryExceptTest extends BaseStageTest{


    @Test
    void testTryExceptSimple() {
        String code = """
                try:
                    1 / 0
                except:
                    100
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(100), result);
    }

    @Test
    void testTryExceptTypeError() {
        String code = """
                try:
                    x = 1 / 0
                except:
                    200
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(200), result);
    }
}