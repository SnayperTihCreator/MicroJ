package org.tihrc.microj.jit;

import org.tihrc.microj.backend.jvm.JvmCompiler;
import org.tihrc.microj.backend.jvm.JvmScript;
import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class BaseStageTest {
    protected PyObject compileAndRun(String source) {
        Interpreter interpreter = new Interpreter();
        var code = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
        var compiledCode = interpreter.compile(code);

        JvmCompiler jvmCompiler = new JvmCompiler();
        JvmScript script = jvmCompiler.compile(compiledCode.code(), compiledCode.constants());

        RuntimeExecuter ctx = new RuntimeExecuter(interpreter);
        return script.execute(ctx);
    }

    protected void runJitBenchmark(String name, String code) {
        try {
            Interpreter interpreter = new Interpreter();
            var compiled = interpreter.compile(new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8)));

            JvmCompiler jvmCompiler = new JvmCompiler();
            JvmScript script = jvmCompiler.compile(compiled.code(), compiled.constants());

            RuntimeExecuter ctx = new RuntimeExecuter(interpreter);

            // Warmup
            script.execute(ctx);
            script.execute(ctx);
            script.execute(ctx);

            // Measure
            long start = System.nanoTime();
            script.execute(ctx);
            long end = System.nanoTime();

            double ms = (end - start) / 1_000_000.0;
            System.out.printf("%-30s %10.3f ms (JIT)%n", name, ms);
        } catch (Exception e) {
            System.err.println("Error in benchmark " + name + ": " + e.getMessage());
        }
    }
}
