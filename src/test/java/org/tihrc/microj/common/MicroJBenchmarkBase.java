package org.tihrc.microj.common;

import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.RuntimeExecuter;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class MicroJBenchmarkBase {
    protected InstructionGenerator.CompiledScript compile(
            Interpreter interpreter,
            String code
    ) {
        if (!code.endsWith("\n"))
            code += "\n";

        return interpreter.compile(
                new ByteArrayInputStream(
                        code.getBytes(StandardCharsets.UTF_8)
                )
        );
    }

    protected void benchmark(
            String name,
            Interpreter interpreter,
            InstructionGenerator.CompiledScript script,
            int warmup,
            int iterations
    ) {
        for (int i = 0; i < warmup; i++)
            interpreter.run(script);

        long start = System.nanoTime();

        for (int i = 0; i < iterations; i++)
            interpreter.run(script);

        long elapsed = System.nanoTime() - start;
        double avgNs = (double) elapsed / iterations;

        System.out.printf(
                "%-28s %10.3f µs/run%n",
                name,
                avgNs / 1_000.0
        );
    }

    protected void benchmarkInstructions(
            String name,
            Interpreter interpreter,
            InstructionGenerator.CompiledScript script,
            int warmup,
            int iterations
    ) {
        for (int i = 0; i < warmup; i++) {
            new RuntimeExecuter(interpreter).runInstructions(script);
        }

        long totalNanos = 0;
        long instructions = 0;

        for (int i = 0; i < iterations; i++) {
            RuntimeExecuter vm = new RuntimeExecuter(interpreter);

            long start = System.nanoTime();
            instructions = vm.runInstructions(script);
            totalNanos += System.nanoTime() - start;
        }

        double avgNanos = (double) totalNanos / iterations;

        System.out.printf(
                "%-28s %10.3f ms/run | %10d instr | %8.3f ns/instr%n",
                name,
                avgNanos / 1_000_000.0,
                instructions,
                avgNanos / instructions
        );
    }

    protected void benchmarkMs(
            String name,
            Interpreter interpreter,
            InstructionGenerator.CompiledScript script,
            int warmup,
            int iterations
    ) {
        for (int i = 0; i < warmup; i++)
            interpreter.run(script);

        long start = System.nanoTime();

        for (int i = 0; i < iterations; i++)
            interpreter.run(script);

        long elapsed = System.nanoTime() - start;
        double avgMs = (double) elapsed / 1_000_000.0 / iterations;

        System.out.printf(
                "%-28s %10.3f ms/run%n",
                name,
                avgMs
        );
    }

    protected void benchmarkJava(
            String name,
            Runnable operation,
            int warmup,
            int iterations
    ) {
        for (int i = 0; i < warmup; i++)
            operation.run();

        long start = System.nanoTime();

        for (int i = 0; i < iterations; i++)
            operation.run();

        long elapsed = System.nanoTime() - start;
        double avgNs = (double) elapsed / iterations;

        System.out.printf(
                "%-28s %10.3f ns/op%n",
                name,
                avgNs
        );
    }


}
