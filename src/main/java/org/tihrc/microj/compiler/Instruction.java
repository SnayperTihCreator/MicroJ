package org.tihrc.microj.compiler;

import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.units.Frame;

public interface Instruction {
    boolean execute(Frame frame, RuntimeExecuter ctx) throws PyUnwind;
}