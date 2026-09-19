package org.tihrc.microj.core;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tihrc.microj.antlr.MicroJLexer;
import org.tihrc.microj.antlr.MicroJParser;
import org.tihrc.microj.backend.jvm.JvmCompiler;
import org.tihrc.microj.backend.jvm.JvmScript;
import org.tihrc.microj.compiler.IndentingLexer;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.stl.PyModuleSys;
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
        if (script == null) {
            throw new IllegalArgumentException("CompiledScript == null");
        }

        RuntimeExecuter vm = new RuntimeExecuter(this);

        try {
            JvmScript jitScript = jitCache.get(script);
            if (jitScript == null) {
                JvmCompiler compiler = new JvmCompiler();
                jitScript = compiler.compile(script.code(), script.constants());
                jitCache.put(script, jitScript);
            }
            sysModule.setBackend("jit");
            return jitScript.execute(vm);
        } catch (Exception e) {
            sysModule.setBackend("interpreter");
            return vm.run(script);
        }
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
