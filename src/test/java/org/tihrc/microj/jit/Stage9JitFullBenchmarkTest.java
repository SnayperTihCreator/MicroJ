package org.tihrc.microj.jit;

import org.junit.jupiter.api.Test;

public class Stage9JitFullBenchmarkTest extends BaseStageTest {

    @Test
    void benchmarkJitAll() {
        System.out.println("=== FULL JIT BENCHMARK ===");

        runJitBenchmark("Call Empty", """
                def f():
                    return None
                i = 0
                while i < 100000:
                    f()
                    i = i + 1
                """);

        runJitBenchmark("Call 1 Arg", """
                def f(x):
                    return None
                i = 0
                while i < 100000:
                    f(i)
                    i = i + 1
                """);

        runJitBenchmark("Call 2 Args", """
                def f(x, y):
                    return None
                i = 0
                while i < 100000:
                    f(i, 1)
                    i = i + 1
                """);

        runJitBenchmark("Call Arithmetic", """
                def f(x):
                    return x + 1
                i = 0
                while i < 100000:
                    f(i)
                    i = i + 1
                """);

        runJitBenchmark("Call 2 Kwargs", """
                def f(x, y):
                    return None
                i = 0
                while i < 100000:
                    f(x=i, y=1)
                    i = i + 1
                """);

        runJitBenchmark("Method Empty", """
                class A:
                    def f(self):
                        return None
                a = A()
                i = 0
                while i < 100000:
                    a.f()
                    i = i + 1
                """);

        runJitBenchmark("For Loop Range", """
                total = 0
                for i in range(100000):
                    total = total + i
                """);

        System.out.println("==========================");
    }
}