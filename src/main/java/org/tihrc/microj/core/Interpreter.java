package org.tihrc.microj.core;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tihrc.microj.antlr.MicroJLexer;
import org.tihrc.microj.antlr.MicroJParser;
import org.tihrc.microj.backend.jvm.JvmCompiler;
import org.tihrc.microj.backend.jvm.JvmScript;
import org.tihrc.microj.compiler.IndentingLexer;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.compiler.ThrowingErrorListener;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.ExceptionsRegistry;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.types.core.PyContext;
import org.tihrc.microj.types.core.PyNone;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.WeakHashMap;

public class Interpreter {
    @SuppressWarnings("FieldCanBeLocal")
    private final StandardLibrary stl;
    private final RuntimeLibrary lib;
    public final InterpreterState state;
    private final Map<InstructionGenerator.CompiledScript, JvmScript> jitCache = new WeakHashMap<>();
    private static final boolean JIT_DISABLE = Boolean.getBoolean("microj.nojit");

    public record RunResult(PyObject result, RuntimeExecuter ctx) {}

    public Interpreter() {
        stl = new StandardLibrary(this);
        lib = new RuntimeLibrary(this, stl);
        state = new InterpreterState(stl.getModules());
    }

    public RuntimeLibrary getLib() {
        return lib;
    }

    public InstructionGenerator.CompiledScript compile(InputStream scriptStream, String filename) {
        if (scriptStream == null) {
            throw new IllegalArgumentException("Поток скрипта пуст (null)");
        }
        try {

            MicroJLexer lexer = new IndentingLexer(CharStreams.fromStream(scriptStream, StandardCharsets.UTF_8));

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MicroJParser parser = new MicroJParser(tokens);

            lexer.removeErrorListeners();
            parser.removeErrorListeners();
            lexer.addErrorListener(ThrowingErrorListener.INSTANCE);
            parser.addErrorListener(ThrowingErrorListener.INSTANCE);

            var tree = parser.file();

            InstructionGenerator generator = new InstructionGenerator(filename);
            return generator.compile(tree);
        } catch (IOException e) {
            System.out.println("Пустой поток");
        }
        return null;
    }

    public InstructionGenerator.CompiledScript compile(InputStream scriptStream) {
        return compile(scriptStream, "<input>");
    }

    public RunResult runWithCtx(InstructionGenerator.CompiledScript script) {
        if (script == null) throw new IllegalArgumentException("CompiledScript == null");
        RuntimeExecuter ctx = new RuntimeExecuter(this);
        ctx.setCurrentFile(script.filename());
        PyObject result = PyContext.with(ctx, () -> {

            if (JIT_DISABLE){
                state.recordBytecode();
                return ctx.run(script);
            }

            JvmScript jitScript;
            try {
                jitScript = jitCache.get(script);
                if (jitScript == null) {
                    jitScript = new JvmCompiler().compile(script);
                    jitCache.put(script, jitScript);
                }
            } catch (PyUnwind e) {
                throw e;
            } catch (Throwable t) {
                System.err.println("[microj] JIT compile failed → bytecode: " + t);
                state.recordBytecode();
                return ctx.run(script);
            }

            state.recordJit();
            try {
                return jitScript.execute(ctx);
            } catch (PyUnwind e) {
                return ctx.raised(e);
            } catch (Throwable t) {
                System.err.println("[microj] JIT internal error: " + t);
                return new Exceptions.PySystemError("internal error (JIT): " + t).raise();
            }
        });
        return new RunResult(result, ctx);
    }

    public PyObject run(InstructionGenerator.CompiledScript script) {
        return runWithCtx(script).result();
    }

    @SuppressWarnings("UnusedReturnValue")
    public PyObject run(InputStream scriptStream, String filename) {
        try {
            return run(compile(scriptStream, filename));
        } catch (PyUnwind e) {
            System.err.println(ExceptionsRegistry.formatError(e.payload));
            return PyNone.INSTANCE;
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении скрипта: " + e.getMessage());
            e.printStackTrace(System.err);
            return PyNone.INSTANCE;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public PyObject run(InputStream scriptStream) {
        return run(scriptStream, "<input>");
    }
}
