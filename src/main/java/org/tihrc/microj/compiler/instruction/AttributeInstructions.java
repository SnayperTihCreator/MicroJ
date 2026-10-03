package org.tihrc.microj.compiler.instruction;

import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.RuntimeExecuter;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.transforms.PyMethodProxy;
import org.tihrc.microj.types.callables.PyBoundMethod;
import org.tihrc.microj.types.callables.PyFunction;
import org.tihrc.microj.types.objects.PyClass;
import org.tihrc.microj.types.objects.PyInstance;
import org.tihrc.microj.types.objects.PyModule;
import org.tihrc.microj.units.Frame;

public final class AttributeInstructions {
    private  AttributeInstructions() {}
    public record GetAttr(String name) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            PyObject obj = f.stack.pop();
            PyObject attr = obj.findAttribute(name);
            if (attr != null) {
                if ((attr instanceof PyFunction || attr instanceof PyMethodProxy)
                        && !(obj instanceof PyModule)
                        && !(obj instanceof PyClass)) {
                    f.stack.push(new PyBoundMethod(obj, attr));
                } else {
                    f.stack.push(attr);
                }
                return true;
            }

            return new Exceptions.PyAttributeError("'" + obj + "' object has no attribute '" + name + "'").raise();
        }
    }

    public record SetAttr(String name) implements Instruction {
        public boolean execute(Frame f, RuntimeExecuter ctx) {
            PyObject value = f.stack.pop();
            PyObject obj = f.stack.pop();

            if (obj instanceof PyInstance inst) {
                inst.setAttribute(name, value);
            } else if (obj instanceof PyClass cls) {
                cls.setAttribute(name, value);
            } else {
                return new Exceptions.PyAttributeError("object has no attribute '" + name + "'").raise();
            }
            return true;
        }
    }
}
