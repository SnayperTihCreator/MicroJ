package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;

public class Stage5JitBenchmarkTest extends BaseStageTest{

    @Test
    void benchmarkJitFunctions() {
        System.out.println("=== JIT BENCHMARK RESULTS ===");

        runJitBenchmark("JIT Call Empty", """
                def f():
                    return None
                i = 0
                while i < 100000:
                    f()
                    i = i + 1
                """);

        runJitBenchmark("JIT Call 1 Arg", """
                def f(x):
                    return None
                i = 0
                while i < 100000:
                    f(i)
                    i = i + 1
                """);

        runJitBenchmark("JIT Call 2 Args", """
                def f(x, y):
                    return None
                i = 0
                while i < 100000:
                    f(i, 1)
                    i = i + 1
                """);

        runJitBenchmark("JIT Call Arithmetic", """
                def f(x):
                    return x + 1
                i = 0
                while i < 100000:
                    f(i)
                    i = i + 1
                """);

        runJitBenchmark("JIT Call 2 Kwargs", """
                def f(x, y):
                    return None
                i = 0
                while i < 100000:
                    f(x=i, y=1)
                    i = i + 1
                """);

        System.out.println("=============================");
    }
}