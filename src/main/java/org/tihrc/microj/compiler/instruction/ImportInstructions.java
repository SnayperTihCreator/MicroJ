package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.units.Frame;

import java.util.List;

public class ImportInstructions {
    public record Import(String moduleName, String alias) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            RuntimeLibrary library = vm.getVM().getLib();
            PyModule module = library.resolveModule(moduleName, vm);

            if (module == null) {
                return new Exceptions.PyImportError("No module named '" + moduleName + "'").raise();
            }

            String storeName = alias != null ? alias : moduleName;
            vm.getGlobals().put(storeName, module);
            return true;
        }
    }
    public record ImportFrom(String moduleName, List<String> names) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            RuntimeLibrary library = vm.getVM().getLib();
            PyModule module = library.resolveModule(moduleName, vm);

            if (module == null) {
                return new Exceptions.PyImportError("No module named '" + moduleName + "'").raise();
            }

            for (String name : names) {
                PyObject attr = module.findAttribute(name);
                if (attr == null) {
                    return new Exceptions.PyImportError("cannot import name '" + name + "' from '" + moduleName + "'").raise();
                }
                vm.getGlobals().put(name, attr);
            }
            return true;
        }
    }
}
