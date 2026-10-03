package org.tihrc.microj.backend.jvm;

import org.objectweb.asm.*;
import org.tihrc.microj.compiler.BinaryOperator;
import org.tihrc.microj.compiler.Instruction;
import org.tihrc.microj.compiler.InstructionGenerator;
import org.tihrc.microj.compiler.UnaryOperator;
import org.tihrc.microj.compiler.instruction.*;
import org.tihrc.microj.core.*;
import org.tihrc.microj.units.LineTables;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public final class JvmCompiler implements Opcodes, AsmTypes {
    private static final AtomicLong CLASS_COUNTER = new AtomicLong(0);
    private static final AtomicLong FUNC_COUNTER = new AtomicLong(0);
    private static final Boolean DEBUG_BLOCKS = Boolean.getBoolean("microj.debug_block");

    private static final int JIT_BLOCK_SIZE = 50;

    private record JitBlock(int id, int start, int end) {}
    private record Jump(int from, int to) {}

    public JvmScript compile(InstructionGenerator.CompiledScript script){
        return compile(script.code(), script.constants(), script.lineTable());
    }

    public JvmScript compile(List<Instruction> code, PyObject[] constants, int[] lineTable) {
        String className = "%sgen.Script$%s".formatted(PREFIX2, CLASS_COUNTER.incrementAndGet());
        String internalClassName = className.replace('.', '/');

        MicroJClassLoader loader = new MicroJClassLoader(JvmCompiler.class.getClassLoader());

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
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

        List<JitBlock> blocks = splitIntoBlocks(code);

        Map<String, Integer> localSlots = new HashMap<>();
        int localsArraySize = 0;

        int localsSlot = 2;
        int closureSlot = 3;
        int lastResultSlot = 4;
        int tempSlot = 5;

        String blockMethodDesc = "(L%s;[L%s;[L%s;L%s;)I".formatted(RUN_EXECUTER, PY_OBJECT, PY_OBJECT, PY_OBJECT);

        for (JitBlock block : blocks) {
            String blockMethodName = "block$" + block.id();
            MethodVisitor bmv = cw.visitMethod(ACC_PRIVATE, blockMethodName, blockMethodDesc, null, null);
            bmv.visitCode();

            List<Instruction> subCode = code.subList(block.start(), block.end());

            int blockCtxSlot = 1;
            int blockLocalsSlot = 2;
            int blockClosureSlot = 3;
            int blockLastResultSlot = 4;
            int blockTempSlot = 5;

            bmv.visitVarInsn(ALOAD, 1);
            bmv.visitLdcInsn("<module>");
            bmv.visitMethodInsn(INVOKEVIRTUAL, RUN_EXECUTER, "setCurrentFunction", "(Ljava/lang/String;)V", false);

            compileBlock(cw, bmv, subCode, block.start(), block.end(), internalClassName, localSlots, new HashSet<>(),
                    new HashMap<>(), blockLocalsSlot, blockClosureSlot, blockTempSlot, blockLastResultSlot,
                    new HashSet<>(), new HashSet<>(), false, lineTable, "<module>");

            if (subCode.isEmpty() || !(subCode.getLast() instanceof StackInstructions.ReturnValue)) {
                pushInt(bmv, block.end() < code.size() ? block.end() : -1);
                bmv.visitInsn(IRETURN);
            }

            bmv.visitMaxs(0, 0);
            bmv.visitEnd();
        }

        // 4. Генерируем главный метод execute(...) с циклом диспетчеризации
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "execute", "(" + CTX_DESC + ")" + PYOBJECT_DESC, null, null);
        mv.visitCode();

        pushInt(mv, localsArraySize);
        mv.visitTypeInsn(ANEWARRAY, PY_OBJECT);
        mv.visitVarInsn(ASTORE, localsSlot);

        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, closureSlot);
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, lastResultSlot);

        int pcSlot = 5;
        pushInt(mv, 0); // pc = 0
        mv.visitVarInsn(ISTORE, pcSlot);

        Label loopStart = new Label();
        Label loopEnd = new Label();

        mv.visitLabel(loopStart);
        mv.visitVarInsn(ILOAD, pcSlot);
        mv.visitJumpInsn(IFLT, loopEnd); // while (pc >= 0)

        Label defaultLabel = new Label();
        Label[] switchLabels = new Label[blocks.size()];
        int[] keys = new int[blocks.size()];

        for (int i = 0; i < blocks.size(); i++) {
            switchLabels[i] = new Label();
            keys[i] = blocks.get(i).start();
        }

        mv.visitVarInsn(ILOAD, pcSlot);
        mv.visitLookupSwitchInsn(defaultLabel, keys, switchLabels);

        for (int i = 0; i < blocks.size(); i++) {
            JitBlock block = blocks.get(i);
            mv.visitLabel(switchLabels[i]);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, localsSlot);
            mv.visitVarInsn(ALOAD, closureSlot);
            mv.visitVarInsn(ALOAD, lastResultSlot);
            mv.visitMethodInsn(INVOKEVIRTUAL, internalClassName, "block$" + block.id(), blockMethodDesc, false);

            mv.visitVarInsn(ISTORE, pcSlot);
            mv.visitJumpInsn(GOTO, loopStart);
        }

        mv.visitLabel(defaultLabel);
        pushInt(mv, -1);
        mv.visitVarInsn(ISTORE, pcSlot);
        mv.visitJumpInsn(GOTO, loopStart);

        mv.visitLabel(loopEnd);
        mv.visitVarInsn(ALOAD, lastResultSlot);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cw.visitEnd();

        byte[] bytecode1 = cw.toByteArray();

        try {
            ClassReader cr = new ClassReader(bytecode1);
            ClassWriter cw2 = new ClassWriter(ClassWriter.COMPUTE_FRAMES) {
                @Override
                protected String getCommonSuperClass(String type1, String type2) {
                    return "java/lang/Object";
                }
            };

            cr.accept(cw2, ClassReader.SKIP_FRAMES);
            byte[] bytecode = cw2.toByteArray();

            Class<?> compiledClass = loader.defineClass(className, bytecode);
            return (JvmScript) compiledClass.getDeclaredConstructor(PyObject[].class).newInstance((Object) constants);

        } catch (Exception e) {
            System.err.println("[microj] JIT compile failed → bytecode: " + e);
            e.printStackTrace();
            throw new RuntimeException("Failed to instantiate compiled script " + className, e);
        }
    }

    private List<JitBlock> splitIntoBlocks(List<Instruction> code) {
        if (code.size() <= JIT_BLOCK_SIZE) {
            return List.of(new JitBlock(0, 0, code.size()));
        }

        List<Jump> jumps = collectJumps(code);
        List<JitBlock> blocks = new ArrayList<>();

        int start = 0;
        int id = 0;

        while (start < code.size()) {
            int target = Math.min(start + JIT_BLOCK_SIZE, code.size());

            if (target == code.size()) {
                blocks.add(new JitBlock(id++, start, code.size()));
                break;
            }

            int boundary = findBoundary(code, jumps, start, target);

            if (boundary == -1) {
                boundary = findBoundaryForward(code, jumps, target);
            }

            if (boundary == -1) {
                blocks.add(new JitBlock(id++, start, code.size()));
                break;
            }

            blocks.add(new JitBlock(id++, start, boundary));
            start = boundary;
        }
        if (DEBUG_BLOCKS)
            debugBlocks(blocks);
        return blocks;
    }

    private int findBoundary(List<Instruction> code, List<Jump> jumps, int start, int target) {
        for (int boundary = target; boundary > start; boundary--) {
            if (!isStackEmptyBoundary(code.get(boundary - 1))) {
                continue;
            }
            if (crossesBoundary(jumps, boundary)) {
                continue;
            }
            return boundary;
        }
        return -1;
    }

    private int findBoundaryForward(List<Instruction> code, List<Jump> jumps, int target) {
        for (int boundary = target + 1; boundary < code.size(); boundary++) {
            if (!isStackEmptyBoundary(code.get(boundary - 1))) {
                continue;
            }
            if (crossesBoundary(jumps, boundary)) {
                continue;
            }
            return boundary;
        }
        return -1;
    }

    private List<Jump> collectJumps(List<Instruction> code) {
        List<Jump> jumps = new ArrayList<>();
        for (int i = 0; i < code.size(); i++) {
            Integer target = getTarget(code.get(i));
            if (target != null) {
                jumps.add(new Jump(i, target));
            }
        }
        return jumps;
    }

    private boolean crossesBoundary(List<Jump> jumps, int boundary) {
        for (Jump jump : jumps) {
            boolean fromBefore = jump.from() < boundary;
            boolean toBefore = jump.to() < boundary;
            if (fromBefore != toBefore) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isStackEmptyBoundary(Instruction instruction) {
        return switch (instruction) {
            case StackInstructions.PopTop ignore -> true;
            case StackInstructions.StoreName ignore -> true;
            case OperatorInstructions.StoreSubscript ignore ->  true;
            case StackInstructions.DeleteName ignore -> true;
            case AttributeInstructions.SetAttr ignore -> true;
            case ImportInstructions.Import ignore -> true;
            case ImportInstructions.ImportFrom ignore -> true;
            default -> false;
        };
    }

    private void debugBlocks(List<JitBlock> blocks) {
        System.err.println("[JIT] Split script into " + blocks.size() + " block(s):");
        for (JitBlock block : blocks) {
            System.err.printf("  [JIT] block$%d: %d..%d (%d instructions)%n",
                    block.id(), block.start(), block.end() - 1, block.end() - block.start());
        }
    }

    private void compileBlock(ClassWriter cw, MethodVisitor mv, List<Instruction> code, int blockStart, int blockEnd,
                              String internalClassName, Map<String, Integer> localSlots, Set<String> cellVars,
                              Map<String, Integer> outerSlots, int localsSlot, int closureSlot, int tempSlot,
                              int lastResultSlot, Set<String> globals, Set<String> nonlocals, boolean functionBody,
                              int[] lineTable, String pyFuncName) {

        Map<Integer, Label> jumpTargets = new HashMap<>();
        int lastLine = -1;

        for (int i = 0; i < code.size(); i++) {
            int absIndex = blockStart + i;
            Integer target = getTarget(code.get(i));
            if (target != null) {
                jumpTargets.computeIfAbsent(target, k -> new Label());
            }
        }

        Set<Integer> catchTargets = new HashSet<>();
        for (Instruction instruction : code) {
            if (instruction instanceof ErrorInstructions.SetupExcept(int target)) {
                catchTargets.add(target);
            }
        }

        List<Object[]> tryCatchBlocks = new ArrayList<>();
        for (int i = 0; i < code.size(); i++) {
            int absIndex = blockStart + i;
            if (code.get(i) instanceof ErrorInstructions.SetupExcept(int target)) {
                Label tryStart = jumpTargets.computeIfAbsent(absIndex + 1, k -> new Label());
                Label catchStart = jumpTargets.computeIfAbsent(target, k -> new Label());

                int popTryIndex = -1;
                int depth = 0;
                for (int j = i + 1; j < code.size(); j++) {
                    if (code.get(j) instanceof ErrorInstructions.SetupExcept) depth++;
                    else if (code.get(j) instanceof ErrorInstructions.PopTry) {
                        if (depth == 0) { popTryIndex = blockStart + j; break; }
                        depth--;
                    }
                }
                Label tryEnd;
                if (popTryIndex != -1) tryEnd = jumpTargets.computeIfAbsent(popTryIndex, k -> new Label());
                else tryEnd = jumpTargets.computeIfAbsent(blockEnd, k -> new Label());
                tryCatchBlocks.add(new Object[]{tryStart, tryEnd, catchStart});
            }
        }

        for (int i = 0; i < code.size(); i++) {
            int absIndex = blockStart + i;
            Instruction instr = code.get(i);

            if (jumpTargets.containsKey(absIndex)) {
                mv.visitLabel(jumpTargets.get(absIndex));
                if (catchTargets.contains(absIndex)) {
                    mv.visitFieldInsn(GETFIELD, PY_UNWIND, "payload", "L%s;".formatted(PY_OBJECT));
                }
            }

            if (lineTable != null && absIndex < lineTable.length) {
                int ln = lineTable[absIndex];
                if (ln > 0 && (ln != lastLine || jumpTargets.containsKey(absIndex))) {
                    mv.visitVarInsn(ALOAD, 1);
                    pushInt(mv, ln);
                    mv.visitMethodInsn(INVOKEVIRTUAL, RUN_EXECUTER, "setCurrentLine", "(I)V", false);
                    lastLine = ln;
                }
            }

            switch (instr) {
                case StackInstructions.LoadConst(int index) -> emitLoadConst(mv, internalClassName, index);
                case StackInstructions.StoreName(String name) -> emitStoreName(mv, name, localSlots, cellVars, localsSlot, closureSlot, outerSlots, nonlocals);
                case StackInstructions.LoadName(String name) -> emitLoadName(mv, name, localSlots, cellVars, outerSlots, localsSlot, closureSlot);
                case StackInstructions.PopTop ignore -> mv.visitVarInsn(ASTORE, lastResultSlot);
                case StackInstructions.ReturnValue ignore -> {
                    if (functionBody) {
                        mv.visitInsn(ARETURN);
                    } else {
                        mv.visitVarInsn(ASTORE, lastResultSlot);
                        pushInt(mv, -1);
                        mv.visitInsn(IRETURN);
                    }
                }
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
                        mv.visitMethodInsn(INVOKESTATIC, HELPER, "reRaise", JvmHelper.SreRaise, false);
                case ErrorInstructions.CheckException(String typeName, int target) ->
                        emitCheckException(mv, typeName, target, jumpTargets, blockStart, blockEnd);
                case ErrorInstructions.Assert() -> emitAssert(mv);
                case ErrorInstructions.RaiseException() -> emitRaiseException(mv);

                case BuilderInstructions.BuildList(int count) -> emitBuildList(mv, count, tempSlot);
                case BuilderInstructions.BuildClass(String name, String[] bases) -> emitBuildClass(mv, name, localSlots, localsSlot, bases);
                case BuilderInstructions.BuildTuple(int size) -> emitBuildTuple(mv, size, tempSlot);
                case BuilderInstructions.BuildMap(int size) -> emitBuildMap(mv, size, tempSlot);
                case BuilderInstructions.UnpackSequence(int count) -> emitUnpackSequence(mv, count, tempSlot);

                case ControlFlowInstructions.PopJumpIfFalse(int target) -> emitPopJumpIfFalse(mv, jumpTargets, target, blockStart, blockEnd);
                case ControlFlowInstructions.JumpAbsolute(int target) -> emitJumpAbsolute(mv, jumpTargets, target, blockStart, blockEnd);
                case ControlFlowInstructions.GetIter() -> emitGetIter(mv);
                case ControlFlowInstructions.ForIter(int target) -> emitForIter(mv, jumpTargets, target, blockStart, blockEnd);
                case ControlFlowInstructions.JumpIfFalseOrPop(int t) -> emitJumpIfXOrPop(mv, t, jumpTargets, blockStart, blockEnd, false, tempSlot+5);
                case ControlFlowInstructions.JumpIfTrueOrPop(int t)  -> emitJumpIfXOrPop(mv, t, jumpTargets, blockStart, blockEnd, true, tempSlot+5);

                case CallInstructions.MakeFunction(String name, List<Instruction> body, List<String> params,
                                                   String starArg, String kwArg, List<String> freeVars, List<int[]> funcLines) ->
                        emitMakeFunction(cw, internalClassName, body, mv, params, name, starArg, kwArg, freeVars,
                                localSlots, outerSlots, nonlocals, localsSlot, closureSlot, tempSlot, funcLines, cellVars);
                case CallInstructions.CallFunction(int posCount, String[] kwNames) ->
                        emitCallFunction(mv, posCount, kwNames, tempSlot);
                case CallInstructions.MakeGenerator(String name, int codeIndex, List<String> params, String starArg, String kwArg, List<String> freeVars) ->
                        emitMakeGenerator(mv, internalClassName, name, codeIndex, localsSlot, closureSlot, tempSlot,
                                params, starArg, kwArg, freeVars, localSlots, outerSlots, cellVars);

                case AttributeInstructions.GetAttr(String name) -> emitGetAttr(mv, name);
                case AttributeInstructions.SetAttr(String name) -> emitSetAttr(mv, name, tempSlot);
                default -> throw new UnsupportedOperationException("Instruction not supported: " + instr.getClass().getSimpleName());
            }
        }

        if (jumpTargets.containsKey(blockEnd)) {
            mv.visitLabel(jumpTargets.get(blockEnd));
        }

        for (int k = tryCatchBlocks.size() - 1; k >= 0; k--) {
            var tcb = tryCatchBlocks.get(k);
            mv.visitTryCatchBlock((Label) tcb[0], (Label) tcb[1], (Label) tcb[2], PY_UNWIND);
        }
    }

    private static void emitJumpAbsolute(MethodVisitor mv, Map<Integer, Label> jumpTargets, int target, int blockStart, int blockEnd) {
        if (target >= blockStart && target < blockEnd) {
            mv.visitJumpInsn(GOTO, jumpTargets.get(target));
        } else {
            pushInt(mv, target);
            mv.visitInsn(IRETURN);
        }
    }

    private static void emitForIter(MethodVisitor mv, Map<Integer, Label> jumpTargets, int target, int blockStart, int blockEnd) {
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "forIterNext", JvmHelper.SforIterNext, false);

        mv.visitInsn(DUP);
        Label notNull = new Label();
        mv.visitJumpInsn(IFNONNULL, notNull);

        mv.visitInsn(POP);
        mv.visitInsn(POP);

        if (target >= blockStart && target < blockEnd) {
            mv.visitJumpInsn(GOTO, jumpTargets.get(target));
        } else {
            pushInt(mv, target);
            mv.visitInsn(IRETURN);
        }

        mv.visitLabel(notNull);
    }

    private static void emitCheckException(MethodVisitor mv, String typeName, int target, Map<Integer, Label> jumpTargets, int blockStart, int blockEnd) {
        if (typeName == null) {
            emitJumpAbsolute(mv, jumpTargets, target, blockStart, blockEnd);
        } else {
            mv.visitInsn(DUP);
            mv.visitLdcInsn(typeName);
            mv.visitMethodInsn(INVOKESTATIC, HELPER, "matchesException", JvmHelper.SmatchesException, false);
            Label next = new Label();
            mv.visitJumpInsn(IFEQ, next);
            emitJumpAbsolute(mv, jumpTargets, target, blockStart, blockEnd);
            mv.visitLabel(next);
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
                                     List<String> freeVars, String pyName, List<int[]> funcLines) {
        MethodVisitor mv = cw.visitMethod(ACC_PRIVATE, methodName, FUNC_METHOD_DESC, null, null);
        mv.visitCode();

        int[] lineTable = LineTables.build(body.size(), funcLines);

        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn(pyName);
        mv.visitMethodInsn(INVOKEVIRTUAL, RUN_EXECUTER, "setCurrentFunction", "(Ljava/lang/String;)V", false);

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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "bindArgs", JvmHelper.SbindArgs, false);
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

        compileBlock(cw, mv, body, 0, body.size(), internalClassName, localSlots, cellVars, outerSlots,
                localsSlot, closureSlot, tempSlot, lastResultSlot, globals, nonlocals, true,
                lineTable, pyName);

        if (body.isEmpty() || !(body.getLast() instanceof StackInstructions.ReturnValue)) {
            mv.visitVarInsn(ALOAD, lastResultSlot);
            mv.visitInsn(ARETURN);
        }

        mv.visitEnd();
    }

    @SuppressWarnings("unused")
    private static Set<String> getCellVars(List<Instruction> body, Map<String, Integer> localSlots, Set<String> globals) {
        Set<String> cellVars = new HashSet<>();
        for (Instruction instr : body) {
            List<String> fv = switch (instr) {
                case CallInstructions.MakeFunction(var n, var b, var p, var s, var k, List<String> v, var l) -> v;
                case CallInstructions.MakeGenerator(var n, var ci, var p, var s, var k, List<String> v) -> v;
                default -> List.of();
            };
            for (String f : fv) {
                if (localSlots.containsKey(f) && !globals.contains(f)) cellVars.add(f);
            }
        }
        return cellVars;
    }

    private static void emitPopJumpIfFalse(MethodVisitor mv, Map<Integer, Label> jumpTargets,
                                           int target, int blockStart, int blockEnd) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "truthy", JvmHelper.Struthy, false);
        if (target >= blockStart && target < blockEnd) {
            mv.visitJumpInsn(IFEQ, jumpTargets.get(target));
        } else {
            Label skip = new Label();
            mv.visitJumpInsn(IFNE, skip);
            pushInt(mv, target);
            mv.visitInsn(IRETURN);
            mv.visitLabel(skip);
        }
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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "nameError", JvmHelper.SnameError, false);
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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "buildClass", JvmHelper.SbuildClass, false);
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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "unpack", JvmHelper.Sunpack, false);
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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "storeSubscript", JvmHelper.SstoreSubscript, false);
    }

    private static void emitBinaryOperator(MethodVisitor mv, BinaryOperator operator) {
        switch (operator) {
            case ADD -> emitNumberOp(mv, "pyDanderAdd", "__add__");
            case SUB -> emitNumberOp(mv, "pyDanderSub", "__sub__");
            case MUL -> emitNumberOp(mv, "pyDanderMul", "__mul__");
            case POW -> emitNumberOp(mv, "pyDanderPow", "__pow__");
            case DIV -> emitNumberOp(mv, "pyDanderTrueDiv", "__truediv__");
            case FLOOR_DIV -> emitNumberOp(mv, "pyDanderFloorDiv", "__floordiv__");
            case MOD -> emitNumberOp(mv, "pyDanderMod", "__mod__");
            case EQ -> emitCompOp(mv, "pyDanderEq", "__eq__");
            case NE -> emitCompOp(mv, "pyDanderNe", "__ne__");
            case LT -> emitCompOp(mv, "pyDanderLt", "__lt__");
            case LE -> emitCompOp(mv, "pyDanderLe", "__le__");
            case GT -> emitCompOp(mv, "pyDanderGt", "__gt__");
            case GE -> emitCompOp(mv, "pyDanderGe", "__ge__");
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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "overloadOperator", JvmHelper.SoverloadOperator, false);
        mv.visitJumpInsn(GOTO, end);

        mv.visitLabel(isNumber);
        mv.visitInsn(SWAP);
        mv.visitTypeInsn(CHECKCAST, PYP_NUMBER);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_NUMBER, methodName,
                "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), true);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "checkNotImplemented", JvmHelper.ScheckNotImplemented, false);
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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "overloadOperator", JvmHelper.SoverloadOperator, false);
        mv.visitJumpInsn(GOTO, end);

        mv.visitLabel(isComp);
        mv.visitInsn(SWAP);
        mv.visitTypeInsn(CHECKCAST, PYP_COMPARABLE);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKEINTERFACE, PYP_COMPARABLE, methodName,
                "(L%s;)L%s;".formatted(PY_OBJECT, PY_OBJECT), true);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "checkNotImplemented", JvmHelper.ScheckNotImplemented, false);

        mv.visitLabel(end);
    }

    private static void emitUnaryNot(MethodVisitor mv) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "truthy", JvmHelper.Struthy, false);
        Label labelTrue = new Label();
        Label labelEnd = new Label();
        mv.visitJumpInsn(IFNE, labelTrue);
        mv.visitFieldInsn(GETSTATIC, PY_BOOL, "TRUE", "L%s;".formatted(PY_BOOL));
        mv.visitJumpInsn(GOTO, labelEnd);
        mv.visitLabel(labelTrue);
        mv.visitFieldInsn(GETSTATIC, PY_BOOL, "FALSE", "L%s;".formatted(PY_BOOL));
        mv.visitLabel(labelEnd);
    }

    private static void emitJumpIfXOrPop(MethodVisitor mv, int target, Map<Integer, Label> jumpTargets,
                                         int blockStart, int blockEnd, boolean jumpOnTrue, int valueSlot) {
        mv.visitVarInsn(ASTORE, valueSlot);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, valueSlot);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "truthy", JvmHelper.Struthy, false);

        Label loadAndGo = new Label();
        Label notTaken = new Label();
        mv.visitJumpInsn(jumpOnTrue ? IFNE : IFEQ, loadAndGo);
        mv.visitJumpInsn(GOTO, notTaken);

        mv.visitLabel(loadAndGo);
        mv.visitVarInsn(ALOAD, valueSlot);
        if (target >= blockStart && target < blockEnd)
            mv.visitJumpInsn(GOTO, jumpTargets.get(target));
        else
            throw new IllegalStateException("JumpIfXOrPop crossing block boundary — boundary logic broken");

        mv.visitLabel(notTaken);
    }

    private void emitMakeFunction(ClassWriter cw, String internalClassName, List<Instruction> body, MethodVisitor mv,
                                  List<String> params, String name, String starArg, String kwArg, List<String> freeVars,
                                  Map<String, Integer> localSlots, Map<String, Integer> outerSlots,
                                  Set<String> nonlocals, int localsSlot, int closureSlot, int tempSlot, List<int[]> funcLines, Set<String> cellVars) {
        String funcMethodName = "func$" + FUNC_COUNTER.incrementAndGet();
        List<String> included = new ArrayList<>();
        for (String f : freeVars) {
            if (cellVars.contains(f) || outerSlots.containsKey(f)) included.add(f);
        }

        compileFunctionBody(cw, internalClassName, funcMethodName, body, params, starArg, kwArg, included,
                name, funcLines);
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
                                          Map<String, Integer> localSlots, Map<String, Integer> outerSlots, Set<String> cellVars) {
        mv.visitVarInsn(ASTORE, tempSlot + 3);

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
            if (cellVars.contains(f) || outerSlots.containsKey(f)) included.add(f);
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
        mv.visitVarInsn(ALOAD, tempSlot + 3);
        mv.visitVarInsn(ALOAD, tempSlot + 4);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "makeGeneratorFunction", JvmHelper.SmakeGeneratorFunction, false);
    }

    private static void emitGetIter(MethodVisitor mv) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "getIter", JvmHelper.SgetIter, false);
    }

    private static void emitImport(MethodVisitor mv, String moduleName, String alias) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitLdcInsn(moduleName);
        if (alias != null) {
            mv.visitLdcInsn(alias);
        } else {
            mv.visitInsn(ACONST_NULL);
        }
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "doImport", JvmHelper.SdoImport, false);
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

        mv.visitMethodInsn(INVOKESTATIC, HELPER, "doImportFrom", JvmHelper.SdoImportFrom, false);
    }

    private static void emitRaiseException(MethodVisitor mv) {
        mv.visitVarInsn(ALOAD, 1);
        mv.visitInsn(SWAP);
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "raiseException", JvmHelper.SraiseException, false);
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
        mv.visitMethodInsn(INVOKESTATIC, HELPER, "assertFail", JvmHelper.SassertFail, false);
    }

    private static void pushInt(MethodVisitor mv, int val) {
        if (val >= -1 && val <= 5) mv.visitInsn(ICONST_0 + val);
        else if (val >= Byte.MIN_VALUE && val <= Byte.MAX_VALUE) mv.visitIntInsn(BIPUSH, val);
        else if (val >= Short.MIN_VALUE && val <= Short.MAX_VALUE) mv.visitIntInsn(SIPUSH, val);
        else mv.visitLdcInsn(val);
    }
}