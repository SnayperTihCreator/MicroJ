package org.tihrc.microj.backend.jvm;

import org.objectweb.asm.*;
import org.tihrc.microj.compiler.BinaryOperator;
import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.compiler.UnaryOperator;
import org.tihrc.microj.compiler.instruction.*;
import org.tihrc.microj.core.*;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.core.exceptions.PyBaseException;
import org.tihrc.microj.core.exceptions.PyUnwind;
import org.tihrc.microj.types.PyClass;
import org.tihrc.microj.types.collections.*;
import org.tihrc.microj.types.primitives.PyInt;
import org.tihrc.microj.types.primitives.PyNone;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class JvmCompiler implements Opcodes, AsmTypes {
    private static final AtomicLong CLASS_COUNTER = new AtomicLong(0);
    private static final AtomicLong FUNC_COUNTER = new AtomicLong(0);

    public JvmScript compile(InstructionGenerator.CompiledScript script){
        return compile(script.code(), script.constants());
    }

    public JvmScript compile(List<Instruction> code, PyObject[] constants) {
        String className = "%sgen.Script$%s".formatted(PREFIX2, CLASS_COUNTER.incrementAndGet());
        String internalClassName = className.replace('.', '/');

        MicroJClassLoader loader = new MicroJClassLoader(JvmCompiler.class.getClassLoader());

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V21, ACC_PUBLIC | ACC_FINAL, internalClassName, null, OBJECT, new String[]{JVM_SCRIPT});

        cw.visitField(ACC_PRIVATE | ACC_FINAL, "constants", PYARR_OBJECT, null, null).visitEnd();

        MethodVisitor init = cw.visitMethod(ACC_PUBLIC, "<init>", "(%s)V".formatted(PYARR_OBJECT), null, null);
        init.visitCode();
        init.visitVarInsn(ALOAD, 0);
        init.visitMethodInsn(INVOKESPECIAL, OBJECT, "<init>", "()V", false);
        init.visitVarInsn(ALOAD, 0);
        init.visitVarInsn(ALOAD, 1);
        init.visitFieldInsn(PUTFIELD, internalClassName, "constants", PYARR_OBJECT);
        init.visitInsn(RETURN);
        init.visitMaxs(2, 2);
        init.visitEnd();

        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "execute", "(" + CTX_DESC + ")" + PYOBJECT_DESC, null, null);
        mv.visitCode();

        Map<String, Integer> localSlots = new HashMap<>();
        int localsArraySize = 0;

        int localsSlot = 2;
        int closureSlot = 3;
        int lastResultSlot = 4;
        int tempSlot = 5;
        int nextSlot = 9;

        pushInt(mv, localsArraySize);
        mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
        mv.visitVarInsn(ASTORE, localsSlot);

        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, closureSlot);

        for (int i = lastResultSlot; i < nextSlot; i++) {
            mv.visitInsn(ACONST_NULL);
            mv.visitVarInsn(ASTORE, i);
        }

        compileBlock(cw, mv, code, internalClassName, localSlots, new HashSet<>(), new HashMap<>(),
                localsSlot, closureSlot, tempSlot, lastResultSlot, new HashSet<>(), new HashSet<>());

        if (code.isEmpty() || !(code.getLast() instanceof StackInstructions.ReturnValue)) {
            mv.visitVarInsn(ALOAD, lastResultSlot);
            mv.visitInsn(ARETURN);
        }

        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();
        Class<?> compiledClass = loader.defineClass(className, bytecode);

        try {
            return (JvmScript) compiledClass.getDeclaredConstructor(PyObject[].class).newInstance((Object) constants);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate compiled script " + className, e);
        }
    }

    private void compileBlock(ClassWriter cw, MethodVisitor mv, List<Instruction> code, String internalClassName,
                              Map<String, Integer> localSlots, Set<String> cellVars, Map<String, Integer> outerSlots,
                              int localsSlot, int closureSlot, int tempSlot, int lastResultSlot,
                              Set<String> globals, Set<String> nonlocals) {
        Map<Integer, Label> jumpTargets = new HashMap<>();
        for (Instruction instr : code) {
            final var target = getTarget(instr);

            if (target != null) {
                jumpTargets.computeIfAbsent(target, k -> new Label());
            }
        }

        List<Object[]> tryCatchBlocks = new ArrayList<>();
        for (int i = 0; i < code.size(); i++) {
            if (code.get(i) instanceof ErrorInstructions.SetupExcept(int target)) {
                Label tryStart = jumpTargets.computeIfAbsent(i + 1, k -> new Label());
                Label catchStart = jumpTargets.get(target);

                int popTryIndex = -1;
                for (int j = i + 1; j < code.size(); j++) {
                    if (code.get(j) instanceof ErrorInstructions.PopTry) {
                        popTryIndex = j;
                        break;
                    }
                }
                Label tryEnd;
                if (popTryIndex != -1) tryEnd = jumpTargets.computeIfAbsent(popTryIndex, k -> new Label());
                else tryEnd = jumpTargets.computeIfAbsent(code.size(), k -> new Label());
                tryCatchBlocks.add(new Object[]{tryStart, tryEnd, catchStart});
            }
        }

        for (int i = 0; i < code.size(); i++) {
            Instruction instr = code.get(i);
            if (jumpTargets.containsKey(i)) {
                mv.visitLabel(jumpTargets.get(i));
            }

            switch (instr) {
                case StackInstructions.LoadConst(int index) -> emitLoadConst(mv, internalClassName, index);
                case StackInstructions.StoreName(String name) -> emitStoreName(mv, name, localSlots, cellVars, localsSlot, closureSlot, outerSlots, nonlocals);
                case StackInstructions.LoadName(String name) -> emitLoadName(mv, name, localSlots, cellVars, outerSlots, localsSlot, closureSlot);
                case StackInstructions.PopTop ignore -> mv.visitVarInsn(ASTORE, lastResultSlot);
                case StackInstructions.ReturnValue ignore -> mv.visitInsn(ARETURN);
                case StackInstructions.DeleteName(String name) -> emitDeleteName(mv, name, localSlots, localsSlot);
                case StackInstructions.Global(String ignore) -> {}
                case StackInstructions.Nonlocal(String ignore) -> {}

                case OperatorInstructions.BinaryOp(var operator) -> emitBinaryOperator(mv, operator);
                case OperatorInstructions.UnaryOp(var operator) -> emitUnaryOperator(mv, operator);
                case OperatorInstructions.BinarySubscript() -> emitBinarySubscript(mv);
                case OperatorInstructions.BinaryIn(boolean inverted) -> emitBinaryIn(mv, inverted);
                case OperatorInstructions.UnaryNot ignore -> emitUnaryNot(mv);
                case OperatorInstructions.StoreSubscript() -> emitStoreSubscript(mv, tempSlot);

                case ImportInstructions.Import(String moduleName, String alias) -> emitImport(mv, moduleName, alias);
                case ImportInstructions.ImportFrom(String moduleName, List<String> names) -> emitImportFrom(mv, moduleName, names);

                case ErrorInstructions.SetupExcept(int ignore) -> {}
                case ErrorInstructions.PopTry() -> {}
                case ErrorInstructions.ReRaise() ->
                        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "reRaise", "(L%s;)V".formatted(OBJECT), false);
                case ErrorInstructions.CheckException(String typeName, int target) ->
                        emitCheckException(mv, typeName, target, jumpTargets);
                case ErrorInstructions.Assert() -> emitAssert(mv);
                case ErrorInstructions.RaiseException() -> emitRaiseException(mv);

                case BuilderInstructions.BuildList(int count) -> emitBuildList(mv, count, tempSlot);
                case BuilderInstructions.BuildClass(String name, String[] bases) -> emitBuildClass(mv, name, localSlots, localsSlot, bases);
                case BuilderInstructions.BuildTuple(int size) -> emitBuildTuple(mv, size, tempSlot);
                case BuilderInstructions.BuildMap(int size) -> emitBuildMap(mv, size, tempSlot);
                case BuilderInstructions.UnpackSequence(int count) -> emitUnpackSequence(mv, count, tempSlot);

                case ControlFlowInstructions.PopJumpIfFalse(int target) -> emitPopJumpIfFalse(mv, jumpTargets, target);
                case ControlFlowInstructions.JumpAbsolute(int target) -> mv.visitJumpInsn(GOTO, jumpTargets.get(target));
                case ControlFlowInstructions.GetIter() -> emitGetIter(mv);
                case ControlFlowInstructions.ForIter(int target) -> emitForIter(mv, jumpTargets, target);

                case CallInstructions.MakeFunction(String name, List<Instruction> body, List<String> params, String starArg, String kwArg, List<String> freeVars) ->
                        emitMakeFunction(cw, internalClassName, body, mv, params, name, starArg, kwArg, freeVars,
                                localSlots, outerSlots, nonlocals, localsSlot, closureSlot, tempSlot);
                case CallInstructions.CallFunction(int posCount, String[] kwNames) ->
                        emitCallFunction(mv, posCount, kwNames, tempSlot);
                case CallInstructions.MakeGenerator(String name, int codeIndex, List<String> params, String starArg, String kwArg, List<String> freeVars) ->
                        emitMakeGenerator(mv, internalClassName, name, codeIndex, localsSlot, closureSlot, tempSlot,
                                params, starArg, kwArg, freeVars, localSlots, outerSlots);

                case AttributeInstructions.GetAttr(String name) -> emitGetAttr(mv, name);
                case AttributeInstructions.SetAttr(String name) -> emitSetAttr(mv, name, tempSlot);
                default -> throw new UnsupportedOperationException("Instruction not supported: " + instr.getClass().getSimpleName());

            }
        }

        if (jumpTargets.containsKey(code.size())) {
            mv.visitLabel(jumpTargets.get(code.size()));
        }

        for (var tcb : tryCatchBlocks) {
            mv.visitTryCatchBlock((Label) tcb[0], (Label) tcb[1], (Label) tcb[2], PY_UNWIND);
        }
    }

    private static Integer getTarget(Instruction instr) {
        Integer target = null;
        if (instr instanceof ControlFlowInstructions.PopJumpIfFalse(int t)) target = t;
        else if (instr instanceof ControlFlowInstructions.JumpAbsolute(int t)) target = t;
        else if (instr instanceof ControlFlowInstructions.JumpIfFalseOrPop(int t)) target = t;
        else if (instr instanceof ControlFlowInstructions.JumpIfTrueOrPop(int t)) target = t;
        else if (instr instanceof ControlFlowInstructions.ForIter(int t)) target = t;
        else if (instr instanceof ErrorInstructions.SetupExcept(int t)) target = t;
        else if (instr instanceof ErrorInstructions.CheckException(String ignore, int t)) target = t;
        return target;
    }

    private static void emitGetAttr(MethodVisitor mv, String name){
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(INVOKEVIRTUAL, PY_OBJECT, "findAttribute", "(L%s;)L%s;".formatted(STRING, PY_OBJECT), false);
    }

    private static void emitSetAttr(MethodVisitor mv, String name, int tempSlot){
        mv.visitVarInsn(ASTORE, tempSlot + 2);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(ALOAD, tempSlot + 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, PY_OBJECT, "setAttribute", "(L%s;L%s;)V".formatted(STRING, PY_OBJECT), false);
    }

    private void compileFunctionBody(ClassWriter cw, String internalClassName, String methodName,
                                     List<Instruction> body, List<String> params, String starArg, String kwArg,
                                     List<String> freeVars) {
        MethodVisitor mv = cw.visitMethod(ACC_PRIVATE, methodName, FUNC_METHOD_DESC, null, null);
        mv.visitCode();

        Map<String, Integer> localSlots = new HashMap<>();
        Set<String> globals = new HashSet<>();
        Set<String> nonlocals = new HashSet<>();
        for (Instruction instr : body) {
            if (instr instanceof StackInstructions.Global(String n)) globals.add(n);
            else if (instr instanceof StackInstructions.Nonlocal(String n)) nonlocals.add(n);
        }

        int localsArraySize = 0;
        for (String param : params) {
            if (!globals.contains(param) && !nonlocals.contains(param)) {
                localSlots.put(param, localsArraySize++);
            }
        }
        if (starArg != null && !globals.contains(starArg) && !nonlocals.contains(starArg)) localSlots.put(starArg, localsArraySize++);
        if (kwArg != null && !globals.contains(kwArg) && !nonlocals.contains(kwArg)) localSlots.put(kwArg, localsArraySize++);

        for (Instruction instr : body) {
            if (instr instanceof StackInstructions.StoreName(String n)) {
                if (!localSlots.containsKey(n) && !globals.contains(n) && !nonlocals.contains(n))
                    localSlots.put(n, localsArraySize++);
            }
        }

        final var cellVars = getCellVars(body, localSlots, globals);

        Map<String, Integer> outerSlots = new HashMap<>();
        for (int j = 0; j < freeVars.size(); j++) outerSlots.put(freeVars.get(j), j);

        int closureSlot = 2;
        int defaultsSlot = 3;
        int argsSlot = 4;
        int kwNamesSlot = 5;
        int kwValuesSlot = 6;
        int localsSlot = 7;
        int lastResultSlot = 8;
        int tempSlot = 9;
        int nextSlot = 13;

        pushInt(mv, localsArraySize);
        mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
        mv.visitVarInsn(ASTORE, localsSlot);

        for (int slot = lastResultSlot; slot < nextSlot; slot++) {
            mv.visitInsn(ACONST_NULL);
            mv.visitVarInsn(ASTORE, slot);
        }

        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, localsSlot);
        mv.visitVarInsn(ALOAD, defaultsSlot);
        mv.visitVarInsn(ALOAD, argsSlot);
        mv.visitVarInsn(ALOAD, kwNamesSlot);
        mv.visitVarInsn(ALOAD, kwValuesSlot);

        pushInt(mv, params.size());
        mv.visitTypeInsn(ANEWARRAY, STRING);
        for (int i = 0; i < params.size(); i++) {
            mv.visitInsn(DUP);
            pushInt(mv, i);
            mv.visitLdcInsn(params.get(i));
            mv.visitInsn(AASTORE);
        }

        if (starArg != null) mv.visitLdcInsn(starArg); else mv.visitInsn(ACONST_NULL);
        if (kwArg != null) mv.visitLdcInsn(kwArg); else mv.visitInsn(ACONST_NULL);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "bindArgs",
                "(L%s;%sL%s;%s[L%s;[L%s;[L%s;L%s;L%s;)[L%s;".formatted(RUN_EXECUTER, PYARR_OBJECT, PY_OBJECT, PYARR_OBJECT, STRING, PY_OBJECT, STRING, STRING, STRING, PY_OBJECT), false);
        mv.visitInsn(POP);

        for (String f : cellVars) {
            int idx = localSlots.get(f);
            mv.visitVarInsn(ALOAD, localsSlot);
            pushInt(mv, idx);
            mv.visitTypeInsn(NEW, PY_CELL);
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, localsSlot);
            pushInt(mv, idx);
            mv.visitInsn(AALOAD);
            mv.visitMethodInsn(INVOKESPECIAL, PY_CELL, "<init>", "(" + PYOBJECT_DESC + ")V", false);
            mv.visitInsn(AASTORE);
        }

        compileBlock(cw, mv, body, internalClassName, localSlots, cellVars, outerSlots,
                localsSlot, closureSlot, tempSlot, lastResultSlot, globals, nonlocals);

        if (body.isEmpty() || !(body.getLast() instanceof StackInstructions.ReturnValue)) {
            mv.visitVarInsn(ALOAD, lastResultSlot);
            mv.visitInsn(ARETURN);
        }

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @SuppressWarnings("unused")
    private static Set<String> getCellVars(List<Instruction> body, Map<String, Integer> localSlots, Set<String> globals) {
        Set<String> cellVars = new HashSet<>();
        for (Instruction instr : body) {
            List<String> fv = switch (instr) {
                case CallInstructions.MakeFunction(var n, var b, var p, var s, var k, List<String> v) -> v;
                case CallInstructions.MakeGenerator(var n, var ci, var p, var s, var k, List<String> v) -> v;
                default -> List.of();
            };
            for (String f : fv) {
                if (localSlots.containsKey(f) && !globals.contains(f)) cellVars.add(f);
            }
        }
        return cellVars;
    }

    private static void emitLoadConst(MethodVisitor mv, String internalName, int index) {
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, internalName, "constants", PYARR_OBJECT);
        pushInt(mv, index);
        mv.visitInsn(AALOAD);
    }

    private static void emitStoreName(MethodVisitor mv, String name, Map<String, Integer> localSlots, Set<String> cellVars,
                                      int localsSlot, int closureSlot, Map<String, Integer> outerSlots, Set<String> nonlocals) {
        Integer index = localSlots.get(name);
        if (index != null) {
            if (cellVars.contains(name)) {
                mv.visitVarInsn(ALOAD, localsSlot);
                pushInt(mv, index);
                mv.visitInsn(AALOAD);
                mv.visitTypeInsn(CHECKCAST, PY_CELL);
                mv.visitInsn(SWAP);
                mv.visitFieldInsn(PUTFIELD, PY_CELL, "value", PYOBJECT_DESC);
            } else {
                mv.visitVarInsn(ALOAD, localsSlot);
                mv.visitInsn(SWAP);
                pushInt(mv, index);
                mv.visitInsn(SWAP);
                mv.visitInsn(AASTORE);
            }
        } else if (nonlocals.contains(name)) {
            Integer outerIndex = outerSlots.get(name);
            if (outerIndex == null)
                throw new IllegalStateException("nonlocal '" + name + "' has no binding in enclosing function");
            mv.visitVarInsn(ALOAD, closureSlot);
            pushInt(mv, outerIndex);
            mv.visitInsn(AALOAD);
            mv.visitTypeInsn(CHECKCAST, PY_CELL);
            mv.visitInsn(SWAP);
            mv.visitFieldInsn(PUTFIELD, PY_CELL, "value", PYOBJECT_DESC);
        } else {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, RUN_EXECUTER, "getGlobals", "()L%s;".formatted(MAP), false);
            mv.visitInsn(SWAP);
            mv.visitLdcInsn(name);
            mv.visitInsn(SWAP);
            mv.visitMethodInsn(INVOKEINTERFACE, MAP, "put", "(L%s;L%s;)L%s;".formatted(OBJECT, OBJECT, OBJECT), true);
            mv.visitInsn(POP);
        }
    }

    private static void emitLoadName(MethodVisitor mv, String name, Map<String, Integer> localSlots, Set<String> cellVars,
                                     Map<String, Integer> outerSlots, int localsSlot, int closureSlot) {
        Integer localIndex = localSlots.get(name);
        if (localIndex != null) {
            mv.visitVarInsn(ALOAD, localsSlot);
            pushInt(mv, localIndex);
            mv.visitInsn(AALOAD);
            if (cellVars.contains(name)) {
                mv.visitTypeInsn(CHECKCAST, PY_CELL);
                mv.visitFieldInsn(GETFIELD, PY_CELL, "value", PYOBJECT_DESC);
            }
            return;
        }

        Integer outerIndex = outerSlots.get(name);
        if (outerIndex != null) {
            mv.visitVarInsn(ALOAD, closureSlot);
            pushInt(mv, outerIndex);
            mv.visitInsn(AALOAD);
            mv.visitTypeInsn(CHECKCAST, PY_CELL);
            mv.visitFieldInsn(GETFIELD, PY_CELL, "value", PYOBJECT_DESC);
            return;
        }

        Label labelFound = new Label();
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, RUN_EXECUTER, "getGlobals", "()L%s;".formatted(MAP), false);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(INVOKEINTERFACE, MAP, "get", "(L%s;)L%s;".formatted(OBJECT, OBJECT), true);
        mv.visitTypeInsn(CHECKCAST, PY_OBJECT);
        mv.visitInsn(DUP);
        mv.visitJumpInsn(IFNONNULL, labelFound);
        mv.visitInsn(POP);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, RUN_EXECUTER, "getBuiltins", "()L%s;".formatted(PY_MODULE), false);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(INVOKEVIRTUAL, PY_MODULE, "findAttribute", "(L%s;)L%s;".formatted(STRING, PY_OBJECT), false);
        mv.visitInsn(DUP);
        mv.visitJumpInsn(IFNONNULL, labelFound);
        mv.visitInsn(POP);
        mv.visitLdcInsn(name);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "nameError",
                "(L%s;)L%s;".formatted(STRING, PY_OBJECT), false);
        mv.visitJumpInsn(GOTO, labelFound);

        mv.visitLabel(labelFound);
    }

    private static void emitBuildList(MethodVisitor mv, int count, int tempSlot) {
        pushInt(mv, count);
        mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
        mv.visitVarInsn(ASTORE, tempSlot);
        for (int i = count - 1; i >= 0; i--) {
            mv.visitVarInsn(ASTORE, tempSlot + 1);
            mv.visitVarInsn(ALOAD, tempSlot);
            pushInt(mv, i);
            mv.visitVarInsn(ALOAD, tempSlot + 1);
            mv.visitInsn(AASTORE);
        }
        mv.visitTypeInsn(NEW, PY_LIST);
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, tempSlot);
        mv.visitMethodInsn(INVOKESPECIAL, PY_LIST, "<init>", "(%s)V".formatted(PYARR_OBJECT), false);
    }

    private static void emitBuildClass(MethodVisitor mv, String name, Map<String, Integer> localSlots, int localsSlot, String[] bases){
        mv.visitTypeInsn(NEW, FAST_MAP);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, FAST_MAP, "<init>", "()V", false);

        for (var entry : localSlots.entrySet()) {
            mv.visitInsn(DUP);
            mv.visitLdcInsn(entry.getKey());
            mv.visitVarInsn(ALOAD, localsSlot);
            pushInt(mv, entry.getValue());
            mv.visitInsn(AALOAD);
            mv.visitMethodInsn(INVOKEINTERFACE, MAP, "put", "(L%s;L%s;)L%s;".formatted(OBJECT, OBJECT, OBJECT), true);
            mv.visitInsn(POP);
        }

        mv.visitLdcInsn(name);
        mv.visitInsn(SWAP);
        pushInt(mv, bases.length);
        mv.visitTypeInsn(ANEWARRAY, STRING);
        for (int i = 0; i < bases.length; i++) {
            mv.visitInsn(DUP);
            pushInt(mv, i);
            mv.visitLdcInsn(bases[i]);
            mv.visitInsn(AASTORE);
        }
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "buildClass",
                "(L%s;L%s;[L%s;L%s;)L%s;".formatted(STRING, MAP, STRING, RUN_EXECUTER, PY_OBJECT), false);
    }

    private static void emitBuildTuple(MethodVisitor mv, int size, int tempSlot) {
        pushInt(mv, size);
        mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
        mv.visitVarInsn(ASTORE, tempSlot);
        for (int i = size - 1; i >= 0; i--) {
            mv.visitVarInsn(ASTORE, tempSlot + 1);
            mv.visitVarInsn(ALOAD, tempSlot);
            pushInt(mv, i);
            mv.visitVarInsn(ALOAD, tempSlot + 1);
            mv.visitInsn(AASTORE);
        }
        mv.visitTypeInsn(NEW, PY_TUPLE);
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, tempSlot);
        mv.visitMethodInsn(INVOKESPECIAL, PY_TUPLE, "<init>", "([L%s;)V".formatted(PY_OBJECT), false);
    }

    private static void emitBuildMap(MethodVisitor mv, int size, int tempSlot) {
        mv.visitTypeInsn(NEW, PY_DICT);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, PY_DICT, "<init>", "()V", false);
        mv.visitVarInsn(ASTORE, tempSlot);

        for (int i = 0; i < size; i++) {
            mv.visitVarInsn(ASTORE, tempSlot + 1);
            mv.visitVarInsn(ASTORE, tempSlot + 2);
            mv.visitVarInsn(ALOAD, tempSlot);
            mv.visitVarInsn(ALOAD, tempSlot + 2);
            mv.visitVarInsn(ALOAD, tempSlot + 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, PY_DICT, "put", "(L%s;L%s;)V".formatted(PY_OBJECT, PY_OBJECT), false);
        }
        mv.visitVarInsn(ALOAD, tempSlot);
    }

    private static void emitUnpackSequence(MethodVisitor mv, int count, int tempSlot) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(SWAP);
        pushInt(mv, count);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "unpack",
                "(L%s;L%s;I)[L%s;".formatted(RUN_EXECUTER, PY_OBJECT, PY_OBJECT), false);
        mv.visitVarInsn(ASTORE, tempSlot);

        for (int i = 0; i < count; i++) {
            mv.visitVarInsn(ALOAD, tempSlot);
            pushInt(mv, i);
            mv.visitInsn(AALOAD);
        }
    }

    private static void emitStoreSubscript(MethodVisitor mv, int tempSlot) {
        mv.visitVarInsn(ASTORE, tempSlot + 2);
        mv.visitVarInsn(ASTORE, tempSlot + 1);
        mv.visitVarInsn(ASTORE, tempSlot);

        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, tempSlot);
        mv.visitVarInsn(ALOAD, tempSlot + 1);
        mv.visitVarInsn(ALOAD, tempSlot + 2);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "storeSubscript",
                "(L%s;L%s;L%s;L%s;)V".formatted(RUN_EXECUTER, PY_OBJECT, PY_OBJECT, PY_OBJECT), false);
    }

    private static void emitBinaryOperator(MethodVisitor mv, BinaryOperator operator) {
        switch (operator) {
            case ADD -> emitNumberOp(mv, "pyDanderAdd", "__add__");
            case SUB -> emitNumberOp(mv, "pyDanderSub", "__sub__");
            case MUL -> emitNumberOp(mv, "pyDanderMul", "__mul__");
            case DIV -> emitNumberOp(mv, "pyDanderTrueDiv", "__truediv__");
            case EQ -> emitCompOp(mv, "pyDanderEq", "__eq__");
            case NE -> emitCompOp(mv, "pyDanderNe", "__ne__");
            case LT -> emitCompOp(mv, "pyDanderLt", "__lt__");
            case LE -> emitCompOp(mv, "pyDanderLe", "__le__");
            case GT -> emitCompOp(mv, "pyDanderGt", "__gt__");
            case GE -> emitCompOp(mv, "pyDanderGe", "__ge__");
            case POW -> throw new UnsupportedOperationException("POW not implemented yet");
        }
    }

    private static void emitUnaryOperator(MethodVisitor mv, UnaryOperator operator) {
        switch (operator) {
            case NEG -> {
                mv.visitTypeInsn(CHECKCAST, PYP_NUMBER);
                mv.visitMethodInsn(INVOKEINTERFACE, PYP_NUMBER, "pyDanderNeg", "()L%s;".formatted(PY_OBJECT), true);
            }
            case NOT -> emitUnaryNot(mv);
        }
    }

    private static void emitBinarySubscript(MethodVisitor mv) {
        mv.visitInsn(SWAP);
        mv.visitTypeInsn(CHECKCAST, PYP_CONTAINER);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_CONTAINER, "pyDanderGetItem", "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), true);
    }

    private static void emitBinaryIn(MethodVisitor mv, boolean inverted) {
        mv.visitTypeInsn(CHECKCAST, PYP_CONTAINER);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_CONTAINER, "pyDanderContains", "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), true);
        if (inverted) emitUnaryNot(mv);
    }

    private static void emitNumberOp(MethodVisitor mv, String methodName, String dunderName) {
        Label isNumber = new Label();
        Label end = new Label();

        mv.visitInsn(DUP2);
        mv.visitInsn(POP);
        mv.visitTypeInsn(INSTANCEOF, PYP_NUMBER);
        mv.visitJumpInsn(IFNE, isNumber);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn(dunderName);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "overloadOperator",
                "(L%s;L%s;L%s;L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT, RUN_EXECUTER, STRING, PY_OBJECT), false);
        mv.visitJumpInsn(GOTO, end);

        mv.visitLabel(isNumber);
        mv.visitInsn(SWAP);
        mv.visitTypeInsn(CHECKCAST, PYP_NUMBER);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_NUMBER, methodName,
                "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), true);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "checkNotImplemented",
                "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), false);
        mv.visitLabel(end);
    }

    private static void emitCompOp(MethodVisitor mv, String methodName, String dunderName) {
        Label isComp = new Label();
        Label end = new Label();

        mv.visitInsn(DUP2);
        mv.visitInsn(POP);
        mv.visitTypeInsn(INSTANCEOF, PYP_COMPARABLE);
        mv.visitJumpInsn(IFNE, isComp);

        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn(dunderName);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "overloadOperator",
                "(L%s;L%s;L%s;L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT, RUN_EXECUTER, STRING, PY_OBJECT), false);
        mv.visitJumpInsn(GOTO, end);

        mv.visitLabel(isComp);
        mv.visitInsn(SWAP);
        mv.visitTypeInsn(CHECKCAST, PYP_COMPARABLE);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_COMPARABLE, methodName,
                "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), true);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "checkNotImplemented",
                "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), false);

        mv.visitLabel(end);
    }

    private static void emitUnaryNot(MethodVisitor mv) {
        mv.visitTypeInsn(CHECKCAST, PYP_COMPARABLE);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_COMPARABLE, "pyDanderBool", "()Z", true);
        Label labelTrue = new Label();
        Label labelEnd = new Label();
        mv.visitJumpInsn(IFNE, labelTrue);
        mv.visitFieldInsn(GETSTATIC, PY_BOOL, "TRUE", "L%s;".formatted(PY_BOOL));
        mv.visitJumpInsn(GOTO, labelEnd);
        mv.visitLabel(labelTrue);
        mv.visitFieldInsn(GETSTATIC, PY_BOOL, "FALSE", "L%s;".formatted(PY_BOOL));
        mv.visitLabel(labelEnd);
    }

    private static void emitPopJumpIfFalse(MethodVisitor mv, Map<Integer, Label> jumpTargets, int target) {
        mv.visitTypeInsn(CHECKCAST, PYP_COMPARABLE);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_COMPARABLE, "pyDanderBool", "()Z", true);
        mv.visitJumpInsn(IFEQ, jumpTargets.get(target));
    }

    private void emitMakeFunction(ClassWriter cw, String internalClassName, List<Instruction> body, MethodVisitor mv,
                                  List<String> params, String name, String starArg, String kwArg, List<String> freeVars,
                                  Map<String, Integer> localSlots, Map<String, Integer> outerSlots,
                                  Set<String> nonlocals, int localsSlot, int closureSlot, int tempSlot) {
        String funcMethodName = "func$" + FUNC_COUNTER.incrementAndGet();
        List<String> included = new ArrayList<>();
        for (String f : freeVars) {
            if (localSlots.containsKey(f) || outerSlots.containsKey(f)) included.add(f);
        }

        compileFunctionBody(cw, internalClassName, funcMethodName, body, params, starArg, kwArg, included);
        mv.visitVarInsn(ASTORE, tempSlot + 2);

        pushInt(mv, included.size());
        mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
        mv.visitVarInsn(ASTORE, tempSlot + 4);

        for (int j = 0; j < included.size(); j++) {
            String f = included.get(j);
            Integer localIdx = localSlots.get(f);
            mv.visitVarInsn(ALOAD, tempSlot + 4);
            pushInt(mv, j);
            if (localIdx != null) {
                mv.visitVarInsn(ALOAD, localsSlot);
                pushInt(mv, localIdx);
                mv.visitInsn(AALOAD);
            } else {
                mv.visitVarInsn(ALOAD, closureSlot);
                pushInt(mv, outerSlots.get(f));
                mv.visitInsn(AALOAD);
            }
            mv.visitTypeInsn(CHECKCAST, PY_CELL);
            mv.visitInsn(AASTORE);
        }

        mv.visitTypeInsn(NEW, JIT_FUNCTION);
        mv.visitInsn(DUP);
        mv.visitLdcInsn(name);
        mv.visitLdcInsn(new Handle(Opcodes.H_INVOKEVIRTUAL, internalClassName, funcMethodName, FUNC_METHOD_DESC, false));
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, METHOD_HANDLE, "bindTo", "(L%s;)L%s;".formatted(OBJECT, METHOD_HANDLE), false);
        mv.visitVarInsn(ALOAD, tempSlot + 4);
        mv.visitVarInsn(ALOAD, tempSlot + 2);
        mv.visitMethodInsn(INVOKESPECIAL, JIT_FUNCTION, "<init>",
                "(L%s;L%s;[L%s;L%s;)V".formatted(STRING, METHOD_HANDLE, PY_OBJECT, PY_OBJECT), false);
    }

    private static void emitCallFunction(MethodVisitor mv, int posCount, String[] kwNames, int tempSlot) {
        int kwCount = kwNames.length;

        mv.visitVarInsn(ASTORE, tempSlot + 3);

        if (kwCount == 0) {
            mv.visitFieldInsn(GETSTATIC, JIT_FUNCTION, "EMPTY_KW_VALS", "[L%s;".formatted(PY_OBJECT));
            mv.visitVarInsn(ASTORE, tempSlot + 1);
        } else {
            pushInt(mv, kwCount);
            mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
            mv.visitVarInsn(ASTORE, tempSlot + 1);
            for (int j = kwCount - 1; j >= 0; j--) {
                mv.visitVarInsn(ASTORE, tempSlot + 2);
                mv.visitVarInsn(ALOAD, tempSlot + 1);
                pushInt(mv, j);
                mv.visitVarInsn(ALOAD, tempSlot + 2);
                mv.visitInsn(AASTORE);
            }
        }

        if (posCount == 0) {
            mv.visitFieldInsn(GETSTATIC, JIT_FUNCTION, "EMPTY_ARGS", "[L%s;".formatted(PY_OBJECT));
            mv.visitVarInsn(ASTORE, tempSlot);
        } else {
            pushInt(mv, posCount);
            mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
            mv.visitVarInsn(ASTORE, tempSlot);
            for (int j = posCount - 1; j >= 0; j--) {
                mv.visitVarInsn(ASTORE, tempSlot + 2);
                mv.visitVarInsn(ALOAD, tempSlot);
                pushInt(mv, j);
                mv.visitVarInsn(ALOAD, tempSlot + 2);
                mv.visitInsn(AASTORE);
            }
        }

        mv.visitVarInsn(ALOAD, tempSlot + 3);
        mv.visitTypeInsn(CHECKCAST, PYP_CALLABLE);

        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, tempSlot);

        if (kwNames.length == 0) {
            mv.visitFieldInsn(GETSTATIC, JIT_FUNCTION, "EMPTY_KW_NAMES", "[L%s;".formatted(STRING));
        } else {
            pushInt(mv, kwNames.length);
            mv.visitTypeInsn(ANEWARRAY, STRING);
            for (int j = 0; j < kwNames.length; j++) {
                mv.visitInsn(DUP);
                pushInt(mv, j);
                mv.visitLdcInsn(kwNames[j]);
                mv.visitInsn(AASTORE);
            }
        }

        mv.visitVarInsn(ALOAD, tempSlot + 1);

        mv.visitMethodInsn(INVOKEINTERFACE, PYP_CALLABLE, "pyDanderCallFast",
                "(" + CTX_DESC + "[L%s;[L%s;[L%s;)".formatted(PY_OBJECT, STRING, PY_OBJECT) + PYOBJECT_DESC, true);
    }

    private static void emitMakeGenerator(MethodVisitor mv, String internalClassName, String name, int codeIndex,
                                          int localsSlot, int closureSlot, int tempSlot, List<String> params,
                                          String starArg, String kwArg, List<String> freeVars,
                                          Map<String, Integer> localSlots, Map<String, Integer> outerSlots) {
        mv.visitVarInsn(ALOAD, 1);
        emitLoadConst(mv, internalClassName, codeIndex);
        mv.visitLdcInsn(name);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, internalClassName, "constants", PYARR_OBJECT);

        pushInt(mv, params.size());
        mv.visitTypeInsn(ANEWARRAY, STRING);
        for (int j = 0; j < params.size(); j++) {
            mv.visitInsn(DUP);
            pushInt(mv, j);
            mv.visitLdcInsn(params.get(j));
            mv.visitInsn(AASTORE);
        }

        if (starArg != null) mv.visitLdcInsn(starArg); else mv.visitInsn(ACONST_NULL);
        if (kwArg != null) mv.visitLdcInsn(kwArg); else mv.visitInsn(ACONST_NULL);

        pushInt(mv, freeVars.size());
        mv.visitTypeInsn(ANEWARRAY, STRING);
        for (int j = 0; j < freeVars.size(); j++) {
            mv.visitInsn(DUP);
            pushInt(mv, j);
            mv.visitLdcInsn(freeVars.get(j));
            mv.visitInsn(AASTORE);
        }

        List<String> included = new ArrayList<>();
        for (String f : freeVars) {
            if (localSlots.containsKey(f) || outerSlots.containsKey(f)) included.add(f);
        }

        pushInt(mv, included.size());
        mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
        mv.visitVarInsn(ASTORE, tempSlot + 4);
        for (int j = 0; j < included.size(); j++) {
            String f = included.get(j);
            Integer localIdx = localSlots.get(f);
            mv.visitVarInsn(ALOAD, tempSlot + 4);
            pushInt(mv, j);
            if (localIdx != null) {
                mv.visitVarInsn(ALOAD, localsSlot);
                pushInt(mv, localIdx);
                mv.visitInsn(AALOAD);
            } else {
                mv.visitVarInsn(ALOAD, closureSlot);
                pushInt(mv, outerSlots.get(f));
                mv.visitInsn(AALOAD);
            }
            mv.visitTypeInsn(CHECKCAST, PY_CELL);
            mv.visitInsn(AASTORE);
        }
        mv.visitVarInsn(ALOAD, tempSlot + 4);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "makeGeneratorFunction",
                "(L%s;L%s;L%s;L%s;[L%s;%s;L%s;L%s;%s;[L%s;)L%s;".formatted(PY_OBJECT, RUN_EXECUTER, PY_OBJECT, STRING, PY_OBJECT, STRING_ARR, STRING, STRING, STRING_ARR, PY_OBJECT, PY_OBJECT), false);
    }

    private static void emitGetIter(MethodVisitor mv) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "getIter", "(L%s;L%s;)L%s;".formatted(RUN_EXECUTER, PY_OBJECT, PY_OBJECT), false);
    }

    private static void emitForIter(MethodVisitor mv, Map<Integer, Label> jumpTargets, int target) {
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "forIterNext", "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), false);

        mv.visitInsn(DUP);
        Label notNull = new Label();
        mv.visitJumpInsn(IFNONNULL, notNull);

        mv.visitInsn(POP);
        mv.visitInsn(POP);
        mv.visitJumpInsn(GOTO, jumpTargets.get(target));

        mv.visitLabel(notNull);
    }

    private static void emitImport(MethodVisitor mv, String moduleName, String alias) {
        mv.visitVarInsn(ALOAD, 1); // ctx
        mv.visitLdcInsn(moduleName);
        if (alias != null) {
            mv.visitLdcInsn(alias);
        } else {
            mv.visitInsn(ACONST_NULL);
        }
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "doImport",
                "(L%s;L%s;L%s;)V".formatted(RUN_EXECUTER, STRING, STRING), false);
    }

    private static void emitImportFrom(MethodVisitor mv, String moduleName, List<String> names){
        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn(moduleName);

        pushInt(mv, names.size());
        mv.visitTypeInsn(ANEWARRAY, STRING);
        for (int j = 0; j < names.size(); j++) {
            mv.visitInsn(DUP);
            pushInt(mv, j);
            mv.visitLdcInsn(names.get(j));
            mv.visitInsn(AASTORE);
        }

        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "doImportFrom",
                "(L%s;L%s;[L%s;)V".formatted(RUN_EXECUTER, STRING, STRING), false);
    }

    private static void emitCheckException(MethodVisitor mv, String typeName, int target, Map<Integer, Label> jumpTargets) {
        if (typeName == null) {
            mv.visitFieldInsn(GETFIELD, PY_UNWIND, "exception", "L%s;".formatted(PY_BASE_EXCEPTION));
            mv.visitJumpInsn(GOTO, jumpTargets.get(target));
        } else {
            mv.visitInsn(DUP);
            mv.visitFieldInsn(GETFIELD, PY_UNWIND, "exception", "L%s;".formatted(PY_BASE_EXCEPTION));
            mv.visitTypeInsn(INSTANCEOF, "%score/exceptions/Exceptions$Py%s".formatted(PREFIX, typeName));

            Label next = new Label();
            mv.visitJumpInsn(IFEQ, next);

            mv.visitInsn(POP);
            mv.visitFieldInsn(GETFIELD, PY_UNWIND, "exception", "L%s;".formatted(PY_BASE_EXCEPTION));
            mv.visitJumpInsn(GOTO, jumpTargets.get(target));

            mv.visitLabel(next);
        }
    }

    private static void emitRaiseException(MethodVisitor mv) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "raiseException",
                "(L%s;L%s;)V".formatted(RUN_EXECUTER, PY_OBJECT), false);
    }

    private static void emitDeleteName(MethodVisitor mv, String name, Map<String, Integer> localSlots, int localsSlot) {
        Integer index = localSlots.get(name);
        if (index != null) {
            mv.visitVarInsn(ALOAD, localsSlot);
            pushInt(mv, index);
            mv.visitInsn(ACONST_NULL);
            mv.visitInsn(AASTORE);
        } else {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, RUN_EXECUTER, "getGlobals", "()L%s;".formatted(OBJECT), false);
            mv.visitLdcInsn(name);
            mv.visitMethodInsn(INVOKEINTERFACE, MAP, "remove", "(L%s;)L%s;".formatted(OBJECT, OBJECT), true);
            mv.visitInsn(POP);
        }
    }

    private static void emitAssert(MethodVisitor mv) {
        mv.visitMethodInsn(INVOKESTATIC, JVM_COMPILER, "assertFail",
                "(L%s;L%s;)V".formatted(PY_OBJECT, PY_OBJECT), false);
    }

    private static void pushInt(MethodVisitor mv, int val) {
        if (val >= -1 && val <= 5) mv.visitInsn(ICONST_0 + val);
        else if (val >= Byte.MIN_VALUE && val <= Byte.MAX_VALUE) mv.visitIntInsn(BIPUSH, val);
        else if (val >= Short.MIN_VALUE && val <= Short.MAX_VALUE) mv.visitIntInsn(SIPUSH, val);
        else mv.visitLdcInsn(val);
    }

    @SuppressWarnings("unused")
    public static PyObject buildClass(String name, Map<String, PyObject> localsMap, String[] bases, RuntimeExecuter ctx) {
        List<PyClass> resolvedBases = new ArrayList<>();
        for (String baseName : bases) {
            PyObject baseObj = ctx.getGlobals().get(baseName);
            if (baseObj instanceof PyClass baseClass) {
                resolvedBases.add(baseClass);
            }
        }
        PyClass pyClass = new PyClass(name, localsMap, resolvedBases);
        for (Capability cap : Capability.values()) {
            if (cap.matches(localsMap)) pyClass.addCap(cap);
        }
        return pyClass;
    }

    @SuppressWarnings("unused")
    public static PyObject makeGeneratorFunction(PyObject defaults, RuntimeExecuter ctx, PyObject codeObj, String name,
                                                 PyObject[] constants, String[] params, String starArg,
                                                 String kwArg, String[] freeVars, PyObject[] closureArray) {
        PyCode code = (PyCode) codeObj;
        java.util.Map<String, PyObject> closureMap = new java.util.HashMap<>();
        for (int i = 0; i < freeVars.length; i++)
            closureMap.put(freeVars[i], closureArray[i]);
        return new PyGeneratorFunc(name, code.body, List.of(params), starArg, kwArg, closureMap, defaults, constants);
    }

    @SuppressWarnings("unused")
    public static PyObject overloadOperator(PyObject left, PyObject right, RuntimeExecuter ctx, String dunderName) {
        PyObject method = left.findAttribute(dunderName);
        if (method instanceof Protocols.PyCallable callable) {
            return callable.pyDanderCallFast(ctx, new PyObject[]{right}, JitFunction.EMPTY_KW_NAMES, JitFunction.EMPTY_KW_VALS);
        }
        throw new RuntimeException("Unsupported operand type for " + dunderName);
    }
    @SuppressWarnings("unused")
    public static PyObject getIter(RuntimeExecuter ctx, PyObject obj) {
        if (obj instanceof Protocols.PyIterable iter) {
            return (PyObject) iter.pyDanderIter();
        }
        PyObject method = obj.findAttribute("__iter__");
        if (method instanceof Protocols.PyCallable callable) {
            return callable.pyDanderCallFast(ctx, new PyObject[]{obj}, JitFunction.EMPTY_KW_NAMES, JitFunction.EMPTY_KW_VALS);
        }
        throw new RuntimeException("object is not iterable");
    }

    @SuppressWarnings("unused")
    public static PyObject forIterNext(PyObject iter) {
        try {
            if (iter instanceof Protocols.PyIterator pyIter) {
                return pyIter.pyDanderNext();
            }
            throw new RuntimeException("object is not an iterator");
        } catch (PyUnwind e) {
            if (e.exception instanceof Exceptions.PyStopIteration) {
                return null;
            }
            throw e;
        }
    }
    @SuppressWarnings("unused")
    public static void doImport(RuntimeExecuter ctx, String moduleName, String alias) {
        var module = ctx.getInterpreter().getLib().resolveModule(moduleName, ctx);
        if (module != null) {
            String storeName = (alias != null) ? alias : moduleName;
            ctx.getGlobals().put(storeName, module);
        }
    }

    @SuppressWarnings("unused")
    public static void doImportFrom(RuntimeExecuter ctx, String moduleName, String[] names) {
        var module = ctx.getInterpreter().getLib().resolveModule(moduleName, ctx);
        if (module != null) {
            for (String name : names) {
                PyObject attr = module.findAttribute(name);
                if (attr != null) {
                    ctx.getGlobals().put(name, attr);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public static void reRaise(Object obj) {
        if (obj instanceof PyUnwind unwind) {
            throw unwind;
        } else if (obj instanceof PyBaseException exc) {
            exc.raise();
        }
        throw new RuntimeException("Cannot re-raise " + obj);
    }

    @SuppressWarnings("unused")
    public static PyObject checkNotImplemented(PyObject result) {
        if (result instanceof PyNotImplemented) {
            return new Exceptions.PyTypeError("unsupported operand type").raise();
        }
        return result;
    }

    @SuppressWarnings("unused")
    public static PyObject[] unpack(RuntimeExecuter ctx, PyObject seq, int count) {
        PyObject[] result = new PyObject[count];
        if (seq instanceof Protocols.PyContainer container) {
            for (int i = 0; i < count; i++) {
                result[i] = container.pyDanderGetItem(PyInt.from(i));
            }
            return result;
        }
        throw new RuntimeException("cannot unpack non-sequence");
    }

    @SuppressWarnings("unused")
    public static void storeSubscript(RuntimeExecuter ctx, PyObject container, PyObject index, PyObject value) {
        if (container instanceof Protocols.PyContainer cont) {
            cont.pyDanderSetItem(index, value);
            return;
        }
        PyObject method = container.findAttribute("__setitem__");
        if (method instanceof Protocols.PyCallable callable) {
            callable.pyDanderCallFast(ctx, new PyObject[]{index, value}, JitFunction.EMPTY_KW_NAMES, JitFunction.EMPTY_KW_VALS);
            return;
        }
        throw new RuntimeException("object does not support item assignment");
    }

    @SuppressWarnings("unused")
    public static void assertFail(PyObject cond, PyObject msg) {
        boolean truthy = false;
        if (cond instanceof Protocols.PyComparable cmp) {
            truthy = cmp.pyDanderBool();
        }

        if (!truthy) {
            String msgStr = (msg == org.tihrc.microj.types.primitives.PyNone.INSTANCE) ? "assertion failed" : msg.toString();
            throw new PyUnwind(new Exceptions.PyAssertionError(msgStr));
        }
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public static PyObject[] bindArgs(RuntimeExecuter ctx, PyObject[] locals, PyObject defaults, PyObject[] args, String[] kwNames, PyObject[] kwValues, String[] params, String starArg, String kwArg) {
        int paramCount = params.length;
        int starOffset = starArg != null ? 1 : 0;
        int kwOffset = kwArg != null ? 1 : 0;

        for (int i = 0; i < paramCount; i++) {
            if (i < args.length) locals[i] = args[i];
        }

        if (kwNames != null && kwNames.length > 0) {
            for (int i = 0; i < kwNames.length; i++) {
                String name = kwNames[i];
                for (int j = 0; j < paramCount; j++) {
                    if (params[j].equals(name)) {
                        locals[j] = kwValues[i];
                        break;
                    }
                }
            }
        }

        if (defaults != null && defaults != PyNone.INSTANCE) {
            PyTuple defaultsTuple = (PyTuple) defaults;
            PyObject[] defaultVals = defaultsTuple.getInner();
            int defaultOffset = paramCount - defaultVals.length;
            for (int i = 0; i < defaultVals.length; i++) {
                int paramIdx = defaultOffset + i;
                if (locals[paramIdx] == null) locals[paramIdx] = defaultVals[i];
            }
        }

        for (int i = 0; i < paramCount; i++) {
            if (locals[i] == null) locals[i] = PyNone.INSTANCE;
        }

        if (starArg != null) {
            int extraCount = args.length > paramCount ? args.length - paramCount : 0;
            PyObject[] starArgs = new PyObject[extraCount];
            System.arraycopy(args, paramCount, starArgs, 0, extraCount);
            locals[paramCount] = new PyTuple(starArgs);
        }

        if (kwArg != null) {
            PyDict kwargsDict = new PyDict();
            if (kwNames != null && kwNames.length > 0) {
                for (int i = 0; i < kwNames.length; i++) {
                    String name = kwNames[i];
                    boolean isParam = false;
                    for (String param : params) {
                        if (param.equals(name)) {
                            isParam = true;
                            break;
                        }
                    }
                    if (!isParam) kwargsDict.put(new PyString(name), kwValues[i]);
                }
            }
            locals[paramCount + starOffset] = kwargsDict;
        }

        return locals;
    }

    @SuppressWarnings("unused")
    public static PyObject nameError(String name) {
        return new Exceptions.PyNameError("name '" + name + "' is not defined").raise();
    }

    @SuppressWarnings("unused")
    public static void raiseException(RuntimeExecuter ctx, PyObject exc) {
        if (exc instanceof PyClass) {
            exc = ((PyClass) exc).pyDanderCallFast(ctx, new PyObject[0], JitFunction.EMPTY_KW_NAMES, JitFunction.EMPTY_KW_VALS);
        }
        if (exc instanceof PyBaseException pyExc) {
            pyExc.raise();
        } else {
            throw new PyUnwind(new Exceptions.PyTypeError("exceptions must derive from BaseException"));
        }
    }
}