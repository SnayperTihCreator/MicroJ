package org.tihrc.microj;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.Interpreter;

public class MicroJHotspotBenchmarkTest extends MicroJBenchmarkBase{

    @Test
    void benchmarkHotspots() {
        Interpreter interpreter;

        // 1. LOAD/STORE NAME
        interpreter = new Interpreter();
        var nameScript = compile(interpreter, """
        i = 0
        x = 123
        while i < 100000:
            y = x
            x = y
            i = i + 1
        """);
        benchmarkMs("HOT NameLoadStore", interpreter, nameScript, 10, 20);

        // 2. GETATTR
        interpreter = new Interpreter();
        var attrScript = compile(interpreter, """
        class A:
            def __init__(self):
                self.x = 123

        a = A()
        i = 0
        while i < 100000:
            y = a.x
            i = i + 1
        """);
        benchmarkMs("HOT GetAttr", interpreter, attrScript, 10, 20);

        // 3. LIST SUBSCRIPT
        interpreter = new Interpreter();
        var listScript = compile(interpreter, """
        a = [1, 2, 3, 4, 5]
        i = 0
        while i < 100000:
            x = a[2]
            i = i + 1
        """);
        benchmarkMs("HOT ListGetItem", interpreter, listScript, 10, 20);

        // 4. TUPLE SUBSCRIPT
        interpreter = new Interpreter();
        var tupleScript = compile(interpreter, """
        a = (1, 2, 3, 4, 5)
        i = 0
        while i < 100000:
            x = a[2]
            i = i + 1
        """);
        benchmarkMs("HOT TupleGetItem", interpreter, tupleScript, 10, 20);

        // 5. DICT SUBSCRIPT
        interpreter = new Interpreter();
        var dictScript = compile(interpreter, """
        a = {"x": 123}
        i = 0
        while i < 100000:
            x = a["x"]
            i = i + 1
        """);
        benchmarkMs("HOT DictGetItem", interpreter, dictScript, 10, 20);

        // 6. LIST ITERATION
        interpreter = new Interpreter();
        var listIterScript = compile(interpreter, """
        a = [1,2,3,4,5,6,7,8,9,10]
        i = 0
        while i < 10000:
            for x in a:
                y = x
            i = i + 1
        """);
        benchmarkMs("HOT ListIteration", interpreter, listIterScript, 10, 20);

        // 7. TUPLE ITERATION
        interpreter = new Interpreter();
        var tupleIterScript = compile(interpreter, """
        a = (1,2,3,4,5,6,7,8,9,10)
        i = 0
        while i < 10000:
            for x in a:
                y = x
            i = i + 1
        """);
        benchmarkMs("HOT TupleIteration", interpreter, tupleIterScript, 10, 20);

        // 8. RANGE ITERATION
        interpreter = new Interpreter();
        var rangeIterScript = compile(interpreter, """
        i = 0
        while i < 10000:
            for x in range(10):
                y = x
            i = i + 1
        """);
        benchmarkMs("HOT RangeIteration", interpreter, rangeIterScript, 10, 20);

        // 9. STRING ITERATION
        interpreter = new Interpreter();
        var stringIterScript = compile(interpreter, """
        a = "abcdefghij"
        i = 0
        while i < 10000:
            for x in a:
                y = x
            i = i + 1
        """);
        benchmarkMs("HOT StringIteration", interpreter, stringIterScript, 10, 20);

        // 10. FUNCTION CALL
        interpreter = new Interpreter();
        var functionScript = compile(interpreter, """
        def f(x):
            return x + 1

        i = 0
        while i < 100000:
            y = f(i)
            i = i + 1
        """);
        benchmarkMs("HOT FunctionCall", interpreter, functionScript, 10, 20);

        // 11. METHOD CALL
        interpreter = new Interpreter();
        var methodScript = compile(interpreter, """
        class A:
            def f(self, x):
                return x + 1

        a = A()
        i = 0
        while i < 100000:
            y = a.f(i)
            i = i + 1
        """);
        benchmarkMs("HOT MethodCall", interpreter, methodScript, 10, 20);
    }
}
