package org.tihrc.microj;

import org.junit.jupiter.api.*;
import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.types.primitives.*;
import org.tihrc.microj.units.SmartFloat;
import org.tihrc.microj.units.SmartInt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class MicroJTest {

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
        // Добавляем \n в конец если нет — парсер требует NEWLINE
        if (!code.endsWith("\n")) code += "\n";
        interpreter.run(new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8)));
    }

    private String output() {
        // Нормализуем \r\n -> \n для Windows
        return outCapture.toString(StandardCharsets.UTF_8)
                .replace("\r\n", "\n")
                .trim();
    }

    // ==================== ПРИМИТИВЫ ====================

    @Test
    @DisplayName("PyInt.__truediv__ возвращает PyFloat")
    void testIntTrueDiv() {
        PyInt a = new PyInt(new SmartInt(5));
        PyInt b = new PyInt(new SmartInt(2));
        PyObject res = a.pyDanderTrueDiv(b);
        assertInstanceOf(PyFloat.class, res, "5 / 2 должен быть float");
        assertEquals(2.5, ((PyFloat) res).value.toDouble(), 0.0001);
    }

    @Test
    @DisplayName("PyInt.__truediv__ by zero кидает ZeroDivisionError")
    void testIntTrueDivByZero() {
        PyInt a = new PyInt(new SmartInt(5));
        PyInt b = new PyInt(new SmartInt(0));
        assertThrows(PyUnwind.class, () -> a.pyDanderTrueDiv(b));
    }

    @Test
    @DisplayName("True == 1 -> True")
    void testBoolEqInt() {
        PyBool t = PyBool.TRUE;
        PyInt one = PyInt.from(1);
        PyObject res = t.pyDanderEq(one);
        assertSame(PyBool.TRUE, res);
    }

    @Test
    @DisplayName("False == 0 -> True")
    void testBoolEqZero() {
        PyBool f = PyBool.FALSE;
        PyInt zero = PyInt.from(0);
        PyObject res = f.pyDanderEq(zero);
        assertSame(PyBool.TRUE, res);
    }

    @Test
    @DisplayName("1 == True -> True")
    void testIntEqBool() {
        PyInt one = PyInt.from(1);
        PyBool t = PyBool.TRUE;
        PyObject res = one.pyDanderEq(t);
        assertSame(PyBool.TRUE, res);
    }

    @Test
    @DisplayName("not True -> False")
    void testNotTrue() {
        run("print(not True)");
        assertEquals("False", output());
    }

    @Test
    @DisplayName("not False -> True")
    void testNotFalse() {
        run("print(not False)");
        assertEquals("True", output());
    }

    @Test
    @DisplayName("not 0 -> True")
    void testNotZero() {
        run("print(not 0)");
        assertEquals("True", output());
    }

    @Test
    @DisplayName("not 1 -> False")
    void testNotOne() {
        run("print(not 1)");
        assertEquals("False", output());
    }

    @Test
    @DisplayName("not '' -> True")
    void testNotEmptyString() {
        run("print(not '')");
        assertEquals("True", output());
    }

    @Test
    @DisplayName("not 'hello' -> False")
    void testNotString() {
        run("print(not 'hello')");
        assertEquals("False", output());
    }

    @Test
    @DisplayName("not [] -> True")
    void testNotEmptyList() {
        run("print(not [])");
        assertEquals("True", output());
    }

    @Test
    @DisplayName("not [1] -> False")
    void testNotList() {
        run("print(not [1])");
        assertEquals("False", output());
    }

    // ==================== АРИФМЕТИКА ====================

    @Test
    @DisplayName("Базовая арифметика")
    void testBasicMath() {
        run("print(2 + 3 * 4)");
        assertEquals("14", output());
    }

    @Test
    @DisplayName("Унарный минус")
    void testUnaryMinus() {
        run("print(-5)");
        assertEquals("-5", output());
    }

    @Test
    @DisplayName("Деление int/int -> float")
    void testDivision() {
        run("print(5 / 2)");
        assertEquals("2.5", output());
    }

    // ==================== RANGE ====================

    @Test
    @DisplayName("range(2) -> 0, 1")
    void testRangeOneArg() {
        run("for i in range(2):\n    print(i)");
        assertEquals("0\n1", output());
    }

    @Test
    @DisplayName("range(1, 3) -> 1, 2")
    void testRangeTwoArgs() {
        run("for i in range(1, 3):\n    print(i)");
        assertEquals("1\n2", output());
    }

    // ==================== КЛАССЫ ====================

    @Test
    @DisplayName("Класс с __init__ и __repr__")
    void testClassInitRepr() {
        run("""
            class Vector:
                def __init__(self, x, y):
                    self.x = x
                    self.y = y
                def __repr__(self):
                    return "Vector(" + str(self.x) + ", " + str(self.y) + ")"
            v = Vector(10, 20)
            print(v)
            """);
        assertEquals("Vector(10, 20)", output());
    }

    @Test
    @DisplayName("__add__ у класса")
    void testClassAdd() {
        run("""
            class Vector:
                def __init__(self, x, y):
                    self.x = x
                    self.y = y
                def __add__(self, other):
                    return Vector(self.x + other.x, self.y + other.y)
                def __repr__(self):
                    return "Vector(" + str(self.x) + ", " + str(self.y) + ")"
            v1 = Vector(10, 20)
            v2 = Vector(5, 5)
            print(v1 + v2)
            """);
        assertEquals("Vector(15, 25)", output());
    }

    @Test
    @DisplayName("__eq__ у класса без скобок")
    void testClassEqNoParens() {
        run("""
            class Vector:
                def __init__(self, x, y):
                    self.x = x
                    self.y = y
                def __eq__(self, other):
                    return self.x == other.x and self.y == other.y
            v1 = Vector(1, 2)
            v2 = Vector(1, 2)
            print(v1 == v2)
            """);
        assertEquals("True", output());
    }

    @Test
    @DisplayName("__call__ у объекта")
    void testClassCall() {
        run("""
            class Multiplier:
                def __init__(self, factor):
                    self.factor = factor
                def __call__(self, value):
                    return value * self.factor
            m = Multiplier(3)
            print(m(10))
            """);
        assertEquals("30", output());
    }

    @Test
    @DisplayName("__len__ и __getitem__")
    void testClassLenGetItem() {
        run("""
            class Matrix:
                def __init__(self, data):
                    self.data = data
                def __len__(self):
                    return len(self.data)
                def __getitem__(self, index):
                    return self.data[index]
            mat = Matrix([100, 200, 300])
            print(len(mat))
            print(mat[1])
            """);
        assertEquals("3\n200", output());
    }

    @Test
    @DisplayName("Кастомный итератор")
    void testCustomIterator() {
        run("""
            class Range:
                def __init__(self, start, end):
                    self.start = start
                    self.end = end
                def __iter__(self):
                    self.current = self.start
                    return self
                def __next__(self):
                    if self.current < self.end:
                        val = self.current
                        self.current = self.current + 1
                        return val
                    return None
            for i in Range(0, 3):
                print(i)
            """);
        assertEquals("0\n1\n2", output());
    }

    // ==================== ЛОГИКА ====================

    @Test
    @DisplayName("and / or")
    void testAndOr() {
        run("print(True and False)");
        assertEquals("False", output());

        outCapture.reset();
        run("print(True or False)");
        assertEquals("True", output());
    }

    @Test
    @DisplayName("if / else")
    void testIfElse() {
        run("""
            if 1 == 1:
                print('yes')
            else:
                print('no')
            """);
        assertEquals("yes", output());
    }

    @Test
    @DisplayName("while loop")
    void testWhile() {
        run("""
            i = 0
            while i < 3:
                print(i)
                i = i + 1
            """);
        assertEquals("0\n1\n2", output());
    }

    // ==================== СТРУКТУРЫ ДАННЫХ ====================

    @Test
    @DisplayName("Список")
    void testList() {
        run("""
            a = [1, 2, 3]
            print(len(a))
            print(a[1])
            """);
        assertEquals("3\n2", output());
    }

    @Test
    @DisplayName("Словарь")
    void testDict() {
        run("""
            d = {'a': 1, 'b': 2}
            print(d['a'])
            """);
        assertEquals("1", output());
    }

    @Test
    @DisplayName("Кортеж")
    void testTuple() {
        run("""
            t = (1, 2, 3)
            print(len(t))
            print(t[0])
            """);
        assertEquals("3\n1", output());
    }

    // ==================== ОШИБКИ ====================

    @Test
    @DisplayName("NameError")
    void testNameError() {
        ByteArrayOutputStream errCapture = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errCapture));

        try {
            run("print(undefined_var)");
            String err = errCapture.toString(StandardCharsets.UTF_8);
            assertTrue(err.contains("NameError") || err.contains("undefined_var"));
        } finally {
            System.setErr(originalErr);
        }
    }
}
