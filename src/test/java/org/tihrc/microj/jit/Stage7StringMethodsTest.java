package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.types.collections.PyString;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stage7StringMethodsTest extends BaseStageTest{
    @Test
    void testStringUpper() {
        String code = """
                s = "hello"
                s.upper()
                """;
        PyObject result = compileAndRun(code);
        assertEquals(new PyString("HELLO"), result);
    }

    @Test
    void testStringSplit() {
        String code = """
                s = "a,b,c"
                s.split(",")
                """;
        PyObject result = compileAndRun(code);
        assertEquals("['a', 'b', 'c']", result.pyDanderStr());
    }

    @Test
    void testStringConcat() {
        String code = """
                "hello" + " " + "world"
                """;
        PyObject result = compileAndRun(code);
        assertEquals(new PyString("hello world"), result);
    }
}