package org.tihrc.microj.common;

import org.junit.jupiter.api.Test;
import org.tihrc.microj.core.Interpreter;

public class MicroJInstructionBenchmarkTest extends MicroJBenchmarkBase{
    @Test
    void benchmarkInstructionDispatch() {
        String code = """
            for i in range(100000):
                0
            """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkInstructions(
                "InstructionDispatch",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkInstructionCounts() {
        Interpreter interpreter = new Interpreter();

        var whileScript = compile(interpreter, """
            i = 0
            while i < 100000:
                i = i + 1
            """);

        var nameScript = compile(interpreter, """
            x = 0
            i = 0
            while i < 100000:
                x = i
                i = i + 1
            """);

        var forScript = compile(interpreter, """
            for i in range(100000):
                i
            """);

        benchmarkInstructions(
                "WhileArithmetic",
                interpreter,
                whileScript,
                10,
                20
        );

        benchmarkInstructions(
                "NameLoop",
                interpreter,
                nameScript,
                10,
                20
        );

        benchmarkInstructions(
                "ForLoop",
                interpreter,
                forScript,
                10,
                20
        );
    }

    @Test
    void benchmarkWhileLoop() {
        String code = """
                i = 0
                while i < 100000:
                    i = i + 1
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "WhileLoop",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkBareForLoop() {
        String code = """
        for i in range(100000):
            0
        """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "BareForLoop",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkConstantCondition() {
        String code = """
                i = 0
                while 1:
                    i = i + 1
                    if i >= 100000:
                        break
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "ConstantCondition",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkEmptyLoop() {
        String code = """
            i = 0
            while i < 100000:
                i = i + 1
            """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "EmptyLoop",
                interpreter,
                script,
                10,
                20
        );
    }

    @Test
    void benchmarkForRange() {
        String code = """
                total = 0
                for i in range(100000):
                    total = total + i
                """;

        Interpreter interpreter = new Interpreter();
        var script = compile(interpreter, code);

        benchmarkMs(
                "ForRange",
                interpreter,
                script,
                10,
                20
        );
    }
}
