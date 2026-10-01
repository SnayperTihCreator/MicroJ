package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.objects.PyClass;
import org.tihrc.microj.types.collections.PyDict;
import org.tihrc.microj.types.collections.PyList;
import org.tihrc.microj.types.collections.PyTuple;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.units.Frame;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BuilderInstructions {
    public record BuildList(int size) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            if (size > 100) {
                System.err.println("[WARNING] Large list building detected: " + size + " elements");
            }
            PyObject[] items = new PyObject[size];
            for (int i = size - 1; i >= 0; i--) {
                items[i] = f.stack.pop();
            }
            f.stack.push(new PyList(items));
            return true;
        }
    }

    public record BuildMap(int size) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            Map<PyObject, PyObject> map = new LinkedHashMap<>();
            for (int i = 0; i < size; i++) {
                PyObject val = f.stack.pop();
                PyObject key = f.stack.pop();
                map.put(key, val);
            }
            f.stack.push(new PyDict(map));
            return true;
        }
    }

    public record BuildTuple(int size) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject[] items = new PyObject[size];
            for (int i = size - 1; i >= 0; i--) {
                items[i] = f.stack.pop();
            }
            f.stack.push(new PyTuple(items));
            return true;
        }
    }

    public record BuildClass(String name, String[] bases) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            List<PyClass> resolvedBases = new ArrayList<>();
            for (String baseName : bases) {
                PyObject baseObj = f.locals.get(baseName);
                if (baseObj == null) baseObj = vm.getGlobals().get(baseName);
                if (baseObj instanceof PyClass baseClass) {
                    resolvedBases.add(baseClass);
                }
            }
            PyClass pyClass = new PyClass(name, f.locals, resolvedBases);
            for (Capability cap: Capability.values())
                if (cap.matches(f.locals)) pyClass.addCap(cap);
            f.stack.push(pyClass);
            return true;
        }
    }

    public record UnpackSequence(int count) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter vm) {
            PyObject seq = f.stack.pop();
            if (seq instanceof Protocols.PyContainer pySeq) {
                for (int i = 0; i < count; i++) {
                    f.stack.push(pySeq.pyDanderGetItem(PyInt.from(i)));
                }
            } else {
                return new Exceptions.PyTypeError("cannot unpack non-sequence").raise();
            }
            return true;
        }
    }
}
