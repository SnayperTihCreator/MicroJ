package org.tihrc.microj;

import org.junit.jupiter.api.*;
import org.tihrc.microj.core.Interpreter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class MicroJTestDebug {

    private Interpreter interpreter;
    private final ByteArrayOutputStream outCapture = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        interpreter = new Interpreter();
        System.setOut(new PrintStream(outCapture));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private void run(String code) {
        if (!code.endsWith("\n")) code += "\n";
        interpreter.run(new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8)));
    }

    private String output() {
        return outCapture.toString(StandardCharsets.UTF_8)
                .replace("\r\n", "\n")
                .trim();
    }

    @Test
    @DisplayName("DEBUG: __eq__ step by step")
    void testEqDebug() {
        run("""
            class Vector:
                def __init__(self, x, y):
                    self.x = x
                    self.y = y
                def __eq__(self, other):
                    print("DEBUG: eq called")
                    a = self.x == other.x
                    print("DEBUG: x_eq =", a)
                    b = self.y == other.y
                    print("DEBUG: y_eq =", b)
                    c = a and b
                    print("DEBUG: and_result =", c)
                    return c
            v1 = Vector(1, 2)
            v2 = Vector(1, 2)
            print("FINAL:", v1 == v2)
            """);
        String out = output();
        System.out.println("=== OUTPUT ===");
        System.out.println(out);
        System.out.println("==============");
        assertTrue(out.contains("FINAL: True"), "Expected FINAL: True but got:\n" + out);
    }

    @Test
    @DisplayName("DEBUG: and without parens in return")
    void testAndNoParens() {
        run("""
            class Vector:
                def __init__(self, x, y):
                    self.x = x
                    self.y = y
                def __eq__(self, other):
                    return self.x == other.x and self.y == other.y
            v1 = Vector(1, 2)
            v2 = Vector(1, 2)
            print("RESULT:", v1 == v2)
            """);
        String out = output();
        System.out.println("=== OUTPUT ===");
        System.out.println(out);
        System.out.println("==============");
        assertTrue(out.contains("RESULT: True"), "Expected RESULT: True but got:\n" + out);
    }

    @Test
    @DisplayName("DEBUG: and with parens in return")
    void testAndWithParens() {
        run("""
            class Vector:
                def __init__(self, x, y):
                    self.x = x
                    self.y = y
                def __eq__(self, other):
                    return (self.x == other.x) and (self.y == other.y)
            v1 = Vector(1, 2)
            v2 = Vector(1, 2)
            print("RESULT:", v1 == v2)
            """);
        String out = output();
        System.out.println("=== OUTPUT ===");
        System.out.println(out);
        System.out.println("==============");
        assertTrue(out.contains("RESULT: True"), "Expected RESULT: True but got:\n" + out);
    }
}
