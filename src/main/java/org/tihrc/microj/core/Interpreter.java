package org.tihrc.microj.core;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tihrc.microj.antlr.MicroJLexer;
import org.tihrc.microj.antlr.MicroJParser;
import org.tihrc.microj.backend.jvm.JvmCompiler;
import org.tihrc.microj.backend.jvm.JvmScript;
import org.tihrc.microj.compiler.IndentingLexer;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.stl.PyModuleSys;
import org.tihrc.microj.types.core.PyContext;
import org.tihrc.microj.types.primitives.PyNone;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.WeakHashMap;

public class Interpreter {
    private final StandardLibrary stl;
    private final RuntimeLibrary lib;
    public int MAX_RECURSION_DEPTH = 1500;
    private final Map<InstructionGenerator.CompiledScript, JvmScript> jitCache = new WeakHashMap<>();

    private final PyModuleSys sysModule;

    public Interpreter() {
        stl = new StandardLibrary(this);
        lib = new RuntimeLibrary(stl);

        sysModule = (PyModuleSys)stl.getModule("sys");
    }

    public RuntimeLibrary getLib() {
        return lib;
    }

    public InstructionGenerator.CompiledScript compile(InputStream scriptStream) {
        if (scriptStream == null) {
            throw new IllegalArgumentException("Поток скрипта пуст (null)");
        }
        try {
            MicroJLexer lexer = new IndentingLexer(
                    CharStreams.fromStream(scriptStream, StandardCharsets.UTF_8)
            );

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MicroJParser parser = new MicroJParser(tokens);

            var tree = parser.file();

            InstructionGenerator generator = new InstructionGenerator();
            return generator.compile(tree);
        } catch (IOException e) {
            System.out.println("Пустой поток");
        }
        return null;
    }

    public PyObject run(InstructionGenerator.CompiledScript script) {
        if (script == null) throw new IllegalArgumentException("CompiledScript == null");

        RuntimeExecuter ctx = new RuntimeExecuter(this);

        return PyContext.with(ctx, () -> {
            JvmScript jitScript;
            try {
                jitScript = jitCache.get(script);
                if (jitScript == null) {
                    jitScript = new JvmCompiler().compile(script);
                    jitCache.put(script, jitScript);
                }
            } catch (Throwable t) {
                System.err.println("[microj] JIT compile failed → bytecode: " + t);
                sysModule.setBackend("bytecode");
                return ctx.run(script);
            }

            sysModule.setBackend("jit");
            try {
                return jitScript.execute(ctx);
            } catch (PyUnwind e) {
                return ctx.raised(e.exception);
            } catch (Exception e) {
                System.err.println("[microj] JIT execute failed → bytecode: "
                        + e.getClass().getSimpleName() + ": " + e.getMessage());
                sysModule.setBackend("bytecode");
                return ctx.run(script);
            }
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    public PyObject run(InputStream scriptStream) {
        try {
            return run(compile(scriptStream));
        } catch (Exception e) {
            System.err.println(
                    "Ошибка при выполнении скрипта: " + e.getMessage()
            );
            e.printStackTrace(System.err);
            return PyNone.INSTANCE;
        }
    }
}
