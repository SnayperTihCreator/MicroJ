package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.units.Frame;

import java.util.List;

public final class ImportInstructions {
    private ImportInstructions() {}

    public record Import(String moduleName, String alias) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            RuntimeLibrary library = ctx.getVM().getLib();
            PyModule module = library.resolveModule(moduleName, ctx);

            if (module == null) {
                return new Exceptions.PyImportError("No module named '" + moduleName + "'").raise();
            }

            String storeName = alias != null ? alias : moduleName;
            ctx.getGlobals().put(storeName, module);
            return true;
        }
    }
    public record ImportFrom(String moduleName, List<String> names) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            RuntimeLibrary library = ctx.getVM().getLib();
            PyModule module = library.resolveModule(moduleName, ctx);

            if (module == null) {
                return new Exceptions.PyImportError("No module named '" + moduleName + "'").raise();
            }

            for (String name : names) {
                PyObject attr = module.findAttribute(name);
                if (attr == null) {
                    return new Exceptions.PyImportError("cannot import name '" + name + "' from '" + moduleName + "'").raise();
                }
                ctx.getGlobals().put(name, attr);
            }
            return true;
        }
    }
}
