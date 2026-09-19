package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.primitives.PyInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage8ForLoopTest extends BaseStageTest {

    @Test
    void testForLoopList() {
        String code = """
                total = 0
                for x in [1, 2, 3, 4, 5]:
                    total = total + x
                total
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(15), result);
    }

    @Test
    void testForLoopRange() {
        String code = """
                total = 0
                for i in range(5):
                    total = total + i
                total
                """;
        PyObject result = compileAndRun(code);
        assertEquals(PyInt.from(10), result); // 0+1+2+3+4 = 10
    }
}