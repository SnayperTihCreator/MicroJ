package org.tihrc.microj.core.loaders;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.tihrc.microj.antlr.MicroJLexer;
import org.tihrc.microj.antlr.MicroJParser;
import org.tihrc.microj.compiler.IndentingLexer;
import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeLibrary;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.objects.PyModule;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResourceScriptLoader implements ScriptLoader {
    private final RuntimeLibrary library;

    public ResourceScriptLoader(RuntimeLibrary library) {
        this.library = library;
    }

    @Override
    public PyModule loadModule(String moduleName, RuntimeExecuter vm) throws Exception {
        InputStream stream = null;

        for (PyObject pathObj : vm.getVM().state.sysPath.getInner()) {

            String dir = Transforms.checkString(pathObj).value;
            String path = dir + moduleName + ".py";

            stream = ResourceScriptLoader.class.getClassLoader().getResourceAsStream(path);
            if (stream != null) {
                break;
            }
        }

        if (stream == null) {
            return null;
        }

        MicroJLexer lexer = new IndentingLexer(CharStreams.fromStream(stream, StandardCharsets.UTF_8));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MicroJParser parser = new MicroJParser(tokens);
        var tree = parser.file();

        InstructionGenerator generator = new InstructionGenerator();
        List<Instruction> bytecode = generator.visit(tree);

        RuntimeLibrary.PyFileModule scriptModule = new RuntimeLibrary.PyFileModule(moduleName);
        library.registerScript(scriptModule);

        RuntimeExecuter moduleVm = new RuntimeExecuter(vm.getVM());
        moduleVm.run(bytecode);
        scriptModule.importAttributesFromGlobals(moduleVm.getGlobals());

        return scriptModule;
    }
}
