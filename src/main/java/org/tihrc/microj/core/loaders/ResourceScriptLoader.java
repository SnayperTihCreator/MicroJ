package org.tihrc.microj.core.loaders;

import org.tihrc.microj.core.Interpreter;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeLibrary;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.core.transforms.Transforms;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.types.primitives.PyString;

import java.io.InputStream;

public class ResourceScriptLoader implements ScriptLoader {
    private final RuntimeLibrary library;

    public ResourceScriptLoader(RuntimeLibrary library) {
        this.library = library;
    }

    @Override
    public PyModule loadModule(String moduleName, RuntimeExecuter ctx) throws Exception {
        String foundPath = null;
        InputStream stream = null;
        Interpreter vm = ctx.getVM();
        try {
            for (PyObject pathObj : vm.state.sysPath.getInner()) {
                String dir = Transforms.checkString(pathObj).value;
                String path = dir.endsWith("/") ? dir + moduleName + ".py" : dir + "/" + moduleName + ".py";
                InputStream s = ResourceScriptLoader.class.getClassLoader().getResourceAsStream(path);
                if (s != null) { stream = s; foundPath = path; break; }
            }
            if (stream == null) return null;

            var script = vm.compile(stream, foundPath);

            RuntimeLibrary.PyFileModule scriptModule = new RuntimeLibrary.PyFileModule(moduleName);
            library.registerScript(scriptModule);
            try {
                var rr = vm.runWithCtx(script);
                scriptModule.importAttributesFromGlobals(rr.ctx().getGlobals());
                return scriptModule;
            } catch (PyUnwind e) {
                vm.state.modules.getInner().remove(new PyString(moduleName));
                throw e;
            }
        } finally {
            if (stream != null) stream.close();
        }
    }
}