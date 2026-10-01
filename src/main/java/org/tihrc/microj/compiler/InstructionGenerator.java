package org.tihrc.microj.compiler;

import org.tihrc.microj.antlr.MicroJBaseVisitor;
import org.tihrc.microj.antlr.MicroJParser;
import org.tihrc.microj.compiler.instruction.*;
import org.tihrc.microj.types.core.PyNone;
import org.tihrc.microj.types.runtime.PyCode;
import org.tihrc.microj.core.PyObject;
import org.tihrc.microj.core.exceptions.Exceptions;
import org.tihrc.microj.types.primitives.PyString;
import org.tihrc.microj.types.primitives.*;
import org.tihrc.microj.units.*;

import java.util.*;

public class InstructionGenerator extends MicroJBaseVisitor<List<Instruction>> {
    private List<Instruction> bytecode = new ArrayList<>();
    private Deque<LoopBlock> loopStack = new ArrayDeque<>();
    private final ConstantPool constants = new ConstantPool();
    private int tmpCounter = 0;
    private final Deque<String> excTemps = new ArrayDeque<>();
    private List<int[]> lineMarkers = new ArrayList<>();
    private final String filename;

    private final int INDEX_NONE = addConstant(PyNone.INSTANCE);
    private final int INDEX_TRUE = addConstant(PyBool.TRUE);
    private final int INDEX_FALSE = addConstant(PyBool.FALSE);


    private static class LoopBlock {
        int continueTarget;
        boolean isForLoop;
        List<Integer> breakJumps = new ArrayList<>();
    }

    public InstructionGenerator(String filename) {
        this.filename = filename;
    }

    public record CompiledScript(
            List<Instruction> code,
            PyObject[] constants,
            String filename,
            int[] lineTable
    ) {}

    public CompiledScript compile(MicroJParser.FileContext ctx) {
        visit(ctx);
        return new CompiledScript(bytecode, constants.toArray(), filename, LineTables.build(bytecode.size(), lineMarkers));
    }

    private int addConstant(PyObject value) {
        return constants.add(value);
    }

    private static List<String> freeVarsOf(List<Instruction> body, List<String> params,
                                           String starArg, String kwArg) {
        Set<String> bound = new HashSet<>(params);
        if (starArg != null) bound.add(starArg);
        if (kwArg != null) bound.add(kwArg);
        Set<String> loaded = new HashSet<>(), nonlocals = new HashSet<>(),
                globals = new HashSet<>(), nested = new HashSet<>();
        for (Instruction i : body) {
            switch (i) {
                case StackInstructions.StoreName(String n) -> bound.add(n);
                case StackInstructions.LoadName(String n)  -> loaded.add(n);
                case StackInstructions.Nonlocal(String n)  -> nonlocals.add(n);
                case StackInstructions.Global(String n)    -> globals.add(n);
                case CallInstructions.MakeFunction(var name, var b, var p, var s, var k, List<String> fv, var l) -> nested.addAll(fv);
                default -> { }
            }
        }
        bound.removeAll(nonlocals);
        bound.removeAll(globals);
        Set<String> free = new HashSet<>(loaded);
        free.addAll(nonlocals);
        free.addAll(nested);
        free.removeAll(bound);
        free.removeAll(globals);
        return new ArrayList<>(free);
    }

    private void emitCompFor(MicroJParser.CompForContext ctx, Runnable bodyEmitter) {
        visit(ctx.or_expr());
        bytecode.add(new ControlFlowInstructions.GetIter());

        int loopStart = bytecode.size();
        int exitJump = bytecode.size();
        bytecode.add(null);

        var targets = ctx.targetList().target();
        if (targets.size() == 1) visitTarget(targets.getFirst());
        else visitTargetList(ctx.targetList());

        emitCompIters(ctx.compIter(), bodyEmitter);

        bytecode.add(new ControlFlowInstructions.JumpAbsolute(loopStart));
        int endLoop = bytecode.size();
        bytecode.set(exitJump, new ControlFlowInstructions.ForIter(endLoop));
    }

    private void emitCompIters(List<MicroJParser.CompIterContext> iters, Runnable bodyEmitter) {
        if (iters.isEmpty()) {
            bodyEmitter.run();
            return;
        }

        MicroJParser.CompIterContext first = iters.getFirst();
        List<MicroJParser.CompIterContext> rest = iters.subList(1, iters.size());

        if (first.compFor() != null) {
            emitCompFor(first.compFor(), () -> emitCompIters(rest, bodyEmitter));
        } else {
            visit(first.compIf().or_expr());
            int condJump = bytecode.size();
            bytecode.add(null);
            emitCompIters(rest, bodyEmitter);
            int afterCond = bytecode.size();
            bytecode.set(condJump, new ControlFlowInstructions.PopJumpIfFalse(afterCond));
        }
    }

    @Override
    public List<Instruction> visitFile(MicroJParser.FileContext ctx) {
        for (var stmtCtx : ctx.statement()) {
            visit(stmtCtx);
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitBlock(MicroJParser.BlockContext ctx) {
        for (var stmtCtx : ctx.statement()) {
            visit(stmtCtx);
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitStatement(MicroJParser.StatementContext ctx) {
        lineMarkers.add(new int[]{ bytecode.size(), ctx.getStart().getLine() });
        return super.visitStatement(ctx);
    }

    @Override
    public List<Instruction> visitSimpleStatement(MicroJParser.SimpleStatementContext ctx) {
        if (ctx.PASS() != null) {
            return bytecode;
        }
        if (ctx.assignment() != null) {
            visit(ctx.assignment());
        } else if (ctx.returnStatement() != null) {
            visit(ctx.returnStatement());
        } else if (ctx.breakStatement() != null) {
            visit(ctx.breakStatement());
        } else if (ctx.continueStatement() != null) {
            visit(ctx.continueStatement());
        } else if (ctx.expr() != null) {
            visit(ctx.expr());
            bytecode.add(new StackInstructions.PopTop());
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitTargetList(MicroJParser.TargetListContext ctx) {
        var targets = ctx.target();
        int count = targets.size();
        bytecode.add(new BuilderInstructions.UnpackSequence(count));
        for (int i = count - 1; i >= 0; i--) visitTarget(targets.get(i));
        return bytecode;
    }

    @Override
    public List<Instruction> visitTarget(MicroJParser.TargetContext ctx) {
        if (ctx.NAME() != null && ctx.atom() == null) {
            bytecode.add(new StackInstructions.StoreName(ctx.NAME().getText()));

        } else if (ctx.atom() != null && ctx.LBRACKET() != null) {
            visit(ctx.atom());
            visit(ctx.expr());
            bytecode.add(new OperatorInstructions.StoreSubscript());

        } else if (ctx.atom() != null && ctx.DOT() != null) {
            visit(ctx.atom());

        } else if (ctx.LPAREN() != null || ctx.LBRACKET() != null) {
            if (ctx.targetList() != null) {
                visitTargetList(ctx.targetList());
            } else {
                bytecode.add(new StackInstructions.PopTop());
            }
        }

        return bytecode;
    }

    @Override
    public List<Instruction> visitReturnStatement(MicroJParser.ReturnStatementContext ctx) {
        if (ctx.exprList() != null) {
            int size = ctx.exprList().expr().size();
            for (var exprCtx : ctx.exprList().expr()) visit(exprCtx);
            if (size > 1) bytecode.add(new BuilderInstructions.BuildTuple(size));
        } else bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
        bytecode.add(new StackInstructions.ReturnValue());
        return bytecode;
    }

    @Override
    public List<Instruction> visitTryStatement(MicroJParser.TryStatementContext ctx) {
        int setupIndex = bytecode.size();
        bytecode.add(null);

        visit(ctx.block(0));

        bytecode.add(new ErrorInstructions.PopTry());

        int jumpToFinallyIndex = bytecode.size();
        bytecode.add(null);

        List<Integer> checkIndices = new ArrayList<>();
        List<String> typeNames = new ArrayList<>();
        List<Integer> bodyStarts = new ArrayList<>();
        List<Integer> nextCheckIndices = new ArrayList<>();

        for (int i = 0; i < ctx.EXCEPT().size(); i++) {
            int checkIndex = bytecode.size();
            bytecode.add(null);
            checkIndices.add(checkIndex);

            String typeName = null;
            if (ctx.exceptClause(i) != null) {
                var firstChild = ctx.exceptClause(i).getChild(0);
                if (firstChild instanceof org.antlr.v4.runtime.tree.TerminalNode terminalNode && terminalNode.getSymbol().getType() == MicroJParser.NAME) {
                    typeName = ctx.exceptClause(i).NAME(0).getText();
                }
            }
            typeNames.add(typeName);

            int nextCheckIndex = bytecode.size();
            bytecode.add(null);
            nextCheckIndices.add(nextCheckIndex);

            int bodyStart = bytecode.size();
            bodyStarts.add(bodyStart);

            String excTemp = "__exc_" + (tmpCounter++) + "__";
            bytecode.add(new StackInstructions.StoreName(excTemp));
            bytecode.add(new StackInstructions.LoadName(excTemp));

            if (ctx.exceptClause(i) != null && ctx.exceptClause(i).AS() != null) {
                int nameCount = ctx.exceptClause(i).NAME().size();
                String aliasName = ctx.exceptClause(i).NAME(nameCount - 1).getText();
                bytecode.add(new StackInstructions.StoreName(aliasName));
            } else {
                bytecode.add(new StackInstructions.PopTop());
            }
            excTemps.push(excTemp);
            visit(ctx.block(i + 1));
            excTemps.pop();
            bytecode.add(new ControlFlowInstructions.JumpAbsolute(jumpToFinallyIndex));
        }

        int reRaiseTarget = bytecode.size();
        if (!ctx.EXCEPT().isEmpty()) {
            for (int i = 0; i < checkIndices.size(); i++) {
                bytecode.set(checkIndices.get(i), new ErrorInstructions.CheckException(typeNames.get(i), bodyStarts.get(i)));
                int nextTarget = (i + 1 < checkIndices.size()) ? checkIndices.get(i + 1) : reRaiseTarget;
                bytecode.set(nextCheckIndices.get(i), new ControlFlowInstructions.JumpAbsolute(nextTarget));
            }
            bytecode.add(new ErrorInstructions.ReRaise());
        }

        if (ctx.ELSE() != null) {
            visit(ctx.block(ctx.EXCEPT().size() + 1));
        }
        int finallyTarget = bytecode.size();
        if (ctx.FINALLY() != null) {
            visit(ctx.block(ctx.block().size() - 1));
        }

        bytecode.set(jumpToFinallyIndex, new ControlFlowInstructions.JumpAbsolute(finallyTarget));
        int firstExceptTarget = !checkIndices.isEmpty() ? checkIndices.getFirst() : reRaiseTarget;

        bytecode.set(setupIndex, new ErrorInstructions.SetupExcept(firstExceptTarget));

        return bytecode;
    }

    @Override
    public List<Instruction> visitRaiseStatement(MicroJParser.RaiseStatementContext ctx) {
        if (ctx.expr() != null) {
            visit(ctx.expr());
            bytecode.add(new ErrorInstructions.RaiseException());
        } else if (!excTemps.isEmpty()) {
            bytecode.add(new StackInstructions.LoadName(excTemps.peek()));
            bytecode.add(new ErrorInstructions.RaiseException());
        } else {
            bytecode.add(new ErrorInstructions.ReRaise());
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitDelStatement(MicroJParser.DelStatementContext ctx) {
        for (var target : ctx.targetList().target()) {
            if (target.NAME() != null && target.atom() == null)
                bytecode.add(new StackInstructions.DeleteName(target.NAME().getText()));
            else
                return new Exceptions.PyRuntimeError("del with attr/subscript not supported yet").raise();
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitAssertStatement(MicroJParser.AssertStatementContext ctx) {
        visit(ctx.expr(0));
        if (ctx.expr().size() > 1) visit(ctx.expr(1));
        else bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
        bytecode.add(new ErrorInstructions.Assert());
        return bytecode;
    }

    @Override
    public List<Instruction> visitGlobalStatement(MicroJParser.GlobalStatementContext ctx) {
        for (var name : ctx.NAME())
            bytecode.add(new StackInstructions.Global(name.getText()));
        return bytecode;
    }

    @Override
    public List<Instruction> visitNonlocalStatement(MicroJParser.NonlocalStatementContext ctx) {
        for (var name : ctx.NAME())
            bytecode.add(new StackInstructions.Nonlocal(name.getText()));
        return bytecode;
    }

    @Override
    public List<Instruction> visitYieldStatement(MicroJParser.YieldStatementContext ctx) {
        if (ctx.expr() != null) visit(ctx.expr());
        else bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
        bytecode.add(new StackInstructions.Yield());
        return bytecode;
    }

    @Override
    public List<Instruction> visitIfStatement(MicroJParser.IfStatementContext ctx) {
        List<Integer> popJumpIndices = new ArrayList<>();
        List<Integer> jumpEndIndices = new ArrayList<>();

        visit(ctx.expr(0));
        popJumpIndices.add(bytecode.size());
        bytecode.add(null);

        visit(ctx.block(0));
        jumpEndIndices.add(bytecode.size());
        bytecode.add(null);
        int elifCount = ctx.ELIF().size();
        for (int i = 0; i < elifCount; i++) {
            int elifStart = bytecode.size();
            bytecode.set(popJumpIndices.get(i), new ControlFlowInstructions.PopJumpIfFalse(elifStart));
            visit(ctx.expr(i + 1));
            popJumpIndices.add(bytecode.size());
            bytecode.add(null);
            visit(ctx.block(i + 1));

            jumpEndIndices.add(bytecode.size());
            bytecode.add(null);
        }

        int elseOrEndIndex = bytecode.size();
        if (ctx.ELSE() != null) {
            bytecode.set(popJumpIndices.getLast(), new ControlFlowInstructions.PopJumpIfFalse(elseOrEndIndex));
            visit(ctx.block(1 + elifCount));
        } else {
            bytecode.set(popJumpIndices.getLast(), new ControlFlowInstructions.PopJumpIfFalse(elseOrEndIndex));
        }
        int endIndex = bytecode.size();

        for (int jumpIndex : jumpEndIndices) {
            bytecode.set(jumpIndex, new ControlFlowInstructions.JumpAbsolute(endIndex));
        }

        return bytecode;
    }

    @Override
    public List<Instruction> visitFuncDef(MicroJParser.FuncDefContext ctx) {
        List<String> params = new ArrayList<>();
        List<String> defaultNames = new ArrayList<>();
        String starArg = null;
        String kwArg = null;

        if (ctx.paramList() != null) {
            for (var p : ctx.paramList().param()) {
                if (p.STAR() != null) {
                    starArg = p.NAME().getText();
                } else if (p.POW() != null) {
                    kwArg = p.NAME().getText();
                } else if (p.NAME() != null) {
                    params.add(p.NAME().getText());
                    if (p.EQUAL() != null) {
                        visit(p.expr());
                        defaultNames.add(p.NAME().getText());
                    }
                }
            }
        }

        var mainBytecode = this.bytecode;
        var mainLoopStack = this.loopStack;
        var mainMarkers = this.lineMarkers;

        this.bytecode = new ArrayList<>();
        this.loopStack = new ArrayDeque<>();
        this.lineMarkers = new ArrayList<>();
        visit(ctx.block());

        if (bytecode.isEmpty() || !(bytecode.getLast() instanceof StackInstructions.ReturnValue)) {
            bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
            bytecode.add(new StackInstructions.ReturnValue());
        }

        List<int[]> funcMarkers = this.lineMarkers;
        List<Instruction> funcBody = this.bytecode;

        this.bytecode = mainBytecode;
        this.loopStack = mainLoopStack;
        this.lineMarkers = mainMarkers;


        boolean isGenerator = funcBody.stream().anyMatch(inst -> inst instanceof StackInstructions.Yield);

        if (!defaultNames.isEmpty())
            bytecode.add(new BuilderInstructions.BuildTuple(defaultNames.size()));
        else bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
        List<String> freeVars = freeVarsOf(funcBody, params, starArg, kwArg);
        if (isGenerator) {
            int codeIdx = addConstant(new PyCode(funcBody));
            bytecode.add(new CallInstructions.MakeGenerator(ctx.NAME().getText(), codeIdx, params, starArg, kwArg, freeVars));
        } else {
            bytecode.add(new CallInstructions.MakeFunction(ctx.NAME().getText(), funcBody, params,
                    starArg, kwArg, freeVars, funcMarkers));
        }

        for (int i = ctx.decorator().size() - 1; i >= 0; i--) {
            var decCtx = ctx.decorator(i);
            visit(decCtx.expr());
            bytecode.add(new CallInstructions.CallFunction(1, new String[0]));
        }

        bytecode.add(new StackInstructions.StoreName(ctx.NAME().getText()));
        return bytecode;
    }

    @Override
    public List<Instruction> visitLambda(MicroJParser.LambdaContext ctx) {
        List<String> params = new ArrayList<>();
        List<String> defaultNames = new ArrayList<>();
        String starArg = null;
        String kwArg = null;

        if (ctx.paramList() != null) {
            for (var p : ctx.paramList().param()) {
                if (p.STAR() != null) starArg = p.NAME().getText();
                else if (p.POW() != null) kwArg = p.NAME().getText();
                else if (p.NAME() != null) {
                    params.add(p.NAME().getText());
                    if (p.EQUAL() != null) {
                        visit(p.expr());
                        defaultNames.add(p.NAME().getText());
                    }
                }
            }
        }

        var mainBytecode = this.bytecode;
        var mainLoopStack = this.loopStack;

        this.bytecode = new ArrayList<>();
        this.loopStack = new ArrayDeque<>();
        visit(ctx.expr());

        bytecode.add(new StackInstructions.ReturnValue());
        List<Instruction> funcBody = this.bytecode;

        this.bytecode = mainBytecode;
        this.loopStack = mainLoopStack;
        if (!defaultNames.isEmpty())
            bytecode.add(new BuilderInstructions.BuildTuple(defaultNames.size()));
        else bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
        List<String> freeVars = freeVarsOf(funcBody, params, starArg, kwArg);
        bytecode.add(new CallInstructions.MakeFunction("<lambda>", funcBody, params, starArg, kwArg, freeVars, List.of()));
        return bytecode;
    }

    @Override
    public List<Instruction> visitClassDef(MicroJParser.ClassDefContext ctx) {
        List<String> bases = new ArrayList<>();
        if (ctx.baseList() != null) {
            for (var b : ctx.baseList().NAME()) {
                bases.add(b.getText());
            }
        }

        var mainBytecode = this.bytecode;
        var mainLoopStack = this.loopStack;
        var mainMarkers = this.lineMarkers;
        this.lineMarkers = new ArrayList<>();

        this.bytecode = new ArrayList<>();
        this.loopStack = new ArrayDeque<>();
        visit(ctx.block());
        bytecode.add(new BuilderInstructions.BuildClass(ctx.NAME().getText(), bases.toArray(new String[0])));
        bytecode.add(new StackInstructions.ReturnValue());

        List<Instruction> classBody = this.bytecode;

        this.bytecode = mainBytecode;
        this.loopStack = mainLoopStack;
        List<int[]> classMarkers = this.lineMarkers;
        this.lineMarkers = mainMarkers;

        String funcName = ctx.NAME().getText();
        List<String> freeVars = freeVarsOf(classBody, java.util.List.of(), null, null);
        bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
        bytecode.add(new CallInstructions.MakeFunction(funcName, classBody, java.util.List.of(), null, null, freeVars, classMarkers));
        bytecode.add(new CallInstructions.CallFunction(0, new String[0]));
        bytecode.add(new StackInstructions.StoreName(funcName));
        return bytecode;
    }

    @Override
    public List<Instruction> visitAttrAssign(MicroJParser.AttrAssignContext ctx) {
        visit(ctx.atom());
        visit(ctx.expr());
        bytecode.add(new AttributeInstructions.SetAttr(ctx.NAME().getText()));
        return bytecode;
    }

    @Override
    public List<Instruction> visitWhileStatement(MicroJParser.WhileStatementContext ctx) {
        LoopBlock block = new LoopBlock();
        block.isForLoop = false;
        block.continueTarget = bytecode.size();
        loopStack.push(block);

        int startIndex = bytecode.size();
        visit(ctx.expr());

        int exitJumpIndex = bytecode.size();
        bytecode.add(null);

        visit(ctx.block(0));
        bytecode.add(new ControlFlowInstructions.JumpAbsolute(startIndex));
        int elseOrEndIndex = bytecode.size();
        if (ctx.ELSE() != null) {
            visit(ctx.block(1));
        }

        int endIndex = bytecode.size();
        for (int breakIndex : block.breakJumps) {
            bytecode.set(breakIndex, new ControlFlowInstructions.JumpAbsolute(endIndex));
        }
        bytecode.set(exitJumpIndex, new ControlFlowInstructions.PopJumpIfFalse(elseOrEndIndex));

        loopStack.pop();
        return bytecode;
    }

    // ==================== ВЫРАЖЕНИЯ (новая иерархия) ====================

    @Override
    public List<Instruction> visitExpr(MicroJParser.ExprContext ctx) {
        return visit(ctx.or_expr());
    }

    @Override
    public List<Instruction> visitOr_expr(MicroJParser.Or_exprContext ctx) {
        List<MicroJParser.And_exprContext> operands = ctx.and_expr();
        visit(operands.getFirst());
        for (int i = 1; i < operands.size(); i++) {
            int jumpPlaceholder = bytecode.size();
            bytecode.add(new ControlFlowInstructions.JumpIfTrueOrPop(-1));
            visit(operands.get(i));
            int target = bytecode.size();
            bytecode.set(jumpPlaceholder, new ControlFlowInstructions.JumpIfTrueOrPop(target));
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitAnd_expr(MicroJParser.And_exprContext ctx) {
        List<MicroJParser.Not_exprContext> operands = ctx.not_expr();
        visit(operands.getFirst());
        for (int i = 1; i < operands.size(); i++) {
            int jumpPlaceholder = bytecode.size();
            bytecode.add(new ControlFlowInstructions.JumpIfFalseOrPop(-1));
            visit(operands.get(i));
            int target = bytecode.size();
            bytecode.set(jumpPlaceholder, new ControlFlowInstructions.JumpIfFalseOrPop(target));
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitNot_expr(MicroJParser.Not_exprContext ctx) {
        int notCount = ctx.NOT().size();
        visit(ctx.comparison());
        for (int i = 0; i < notCount; i++) {
            bytecode.add(new OperatorInstructions.UnaryNot());
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitComparison(MicroJParser.ComparisonContext ctx) {
        List<MicroJParser.Add_exprContext> operands = ctx.add_expr();
        List<MicroJParser.CompareOpContext> ops = ctx.compareOp();
        visit(operands.getFirst());
        for (int i = 0; i < ops.size(); i++) {
            visit(operands.get(i + 1));
            switch (ops.get(i).getStart().getType()) {
                case MicroJParser.IN -> bytecode.add(new OperatorInstructions.BinaryIn(false));
                case MicroJParser.NOT_IN -> bytecode.add(new OperatorInstructions.BinaryIn(true));
                default -> bytecode.add(new OperatorInstructions.BinaryOp(getBinaryOperator(ops, i)));
            }
        }
        return bytecode;
    }

    private static BinaryOperator getBinaryOperator(List<MicroJParser.CompareOpContext> ops, int i) {
        int tokenType = ops.get(i).getStart().getType();
        return switch (tokenType) {
            case MicroJParser.EQ_EQ -> BinaryOperator.EQ;
            case MicroJParser.NE -> BinaryOperator.NE;
            case MicroJParser.LT -> BinaryOperator.LT;
            case MicroJParser.GT -> BinaryOperator.GT;
            case MicroJParser.LE -> BinaryOperator.LE;
            case MicroJParser.GE -> BinaryOperator.GE;
            default -> throw new RuntimeException("Неизвестный оператор сравнения");
        };
    }

    @Override
    public List<Instruction> visitAdd_expr(MicroJParser.Add_exprContext ctx) {
        List<MicroJParser.Mul_exprContext> operands = ctx.mul_expr();
        List<MicroJParser.AddOpContext> ops = ctx.addOp();
        visit(operands.getFirst());
        for (int i = 0; i < ops.size(); i++) {
            visit(operands.get(i + 1));
            int tokenType = ops.get(i).getStart().getType();
            BinaryOperator op = switch (tokenType) {
                case MicroJParser.PLUS -> BinaryOperator.ADD;
                case MicroJParser.MINUS -> BinaryOperator.SUB;
                default -> throw new RuntimeException("Неизвестный аддитивный оператор");
            };
            bytecode.add(new OperatorInstructions.BinaryOp(op));
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitMul_expr(MicroJParser.Mul_exprContext ctx) {
        List<MicroJParser.Unary_exprContext> operands = ctx.unary_expr();
        List<MicroJParser.MulOpContext> ops = ctx.mulOp();
        visit(operands.getFirst());
        for (int i = 0; i < ops.size(); i++) {
            visit(operands.get(i + 1));
            int tokenType = ops.get(i).getStart().getType();
            BinaryOperator op = switch (tokenType) {
                case MicroJParser.STAR -> BinaryOperator.MUL;
                case MicroJParser.SLASH -> BinaryOperator.DIV;
                case MicroJParser.DOUBLE_SLASH -> BinaryOperator.FLOOR_DIV;
                case MicroJParser.PERCENT -> BinaryOperator.MOD;
                default -> throw new RuntimeException("Неизвестный мультипликативный оператор");
            };
            bytecode.add(new OperatorInstructions.BinaryOp(op));
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitUnary_expr(MicroJParser.Unary_exprContext ctx) {
        if (ctx.unary_expr() != null) {
            visit(ctx.unary_expr());
            int tokenType = ctx.getStart().getType();
            if (tokenType == MicroJParser.MINUS) {
                bytecode.add(new OperatorInstructions.UnaryOp(UnaryOperator.NEG));
            }
        } else {
            visit(ctx.power());
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitPower(MicroJParser.PowerContext ctx) {
        visit(ctx.atom());
        if (ctx.unary_expr() != null) {
            visit(ctx.unary_expr());
            bytecode.add(new OperatorInstructions.BinaryOp(BinaryOperator.POW));
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitWithStatement(MicroJParser.WithStatementContext ctx) {
        visit(ctx.expr());
        bytecode.add(new StackInstructions.StoreName("__with_mgr__"));
        bytecode.add(new StackInstructions.LoadName("__with_mgr__"));
        bytecode.add(new AttributeInstructions.GetAttr("__enter__"));
        bytecode.add(new CallInstructions.CallFunction(0, new String[0]));
        if (ctx.NAME() != null) {
            bytecode.add(new StackInstructions.StoreName(ctx.NAME().getText()));
        } else {
            bytecode.add(new StackInstructions.PopTop());
        }
        int setupIndex = bytecode.size();
        bytecode.add(null);
        visit(ctx.block());
        bytecode.add(new ErrorInstructions.PopTry());
        int jumpToNormalExit = bytecode.size();
        bytecode.add(null);
        int exceptStart = bytecode.size();
        bytecode.set(setupIndex, new ErrorInstructions.SetupExcept(exceptStart));
        bytecode.add(new StackInstructions.StoreName("__with_exc__"));
        bytecode.add(new StackInstructions.LoadName("__with_mgr__"));
        bytecode.add(new AttributeInstructions.GetAttr("__exit__"));
        bytecode.add(new CallInstructions.CallFunction(0, new String[0]));
        bytecode.add(new StackInstructions.PopTop());
        bytecode.add(new StackInstructions.LoadName("__with_exc__"));
        bytecode.add(new ErrorInstructions.ReRaise());
        int normalExit = bytecode.size();
        bytecode.set(jumpToNormalExit, new ControlFlowInstructions.JumpAbsolute(normalExit));
        bytecode.add(new StackInstructions.LoadName("__with_mgr__"));
        bytecode.add(new AttributeInstructions.GetAttr("__exit__"));
        bytecode.add(new CallInstructions.CallFunction(0, new String[0]));
        bytecode.add(new StackInstructions.PopTop());

        return bytecode;
    }

    // ==================== ATOM ====================

    @Override
    public List<Instruction> visitNumber(MicroJParser.NumberContext ctx) {
        int index = addConstant(PyInt.from(SmartInt.parse(ctx.NUMBER().getText())));
        bytecode.add(new StackInstructions.LoadConst(index));
        return bytecode;
    }

    @Override
    public List<Instruction> visitFloatLiteral(MicroJParser.FloatLiteralContext ctx) {
        int index = addConstant(new PyFloat(SmartFloat.parse(ctx.FLOAT().getText())));
        bytecode.add(new StackInstructions.LoadConst(index));
        return bytecode;
    }

    @Override
    public List<Instruction> visitImagLiteral(MicroJParser.ImagLiteralContext ctx) {
        String text = ctx.IMAG().getText();
        String numStr = text.substring(0, text.length() - 1);
        SmartFloat imagVal = SmartFloat.parse(numStr);
        SmartComplex complex = new SmartComplex(new SmartFloat(0.0), imagVal);
        int index = addConstant(new PyComplex(complex));
        bytecode.add(new StackInstructions.LoadConst(index));
        return bytecode;
    }

    @Override
    public List<Instruction> visitStringLiteral(MicroJParser.StringLiteralContext ctx) {
        String raw;
        boolean isRaw = false;
        boolean isBytes = false;
        String content;

        if (ctx.stringLit().TRIPLE_DOUBLE_STRING() != null) {
            raw = ctx.stringLit().TRIPLE_DOUBLE_STRING().getText();
        } else if (ctx.stringLit().TRIPLE_SINGLE_STRING() != null) {
            raw = ctx.stringLit().TRIPLE_SINGLE_STRING().getText();
        } else if (ctx.stringLit().SINGLE_DOUBLE_STRING() != null) {
            raw = ctx.stringLit().SINGLE_DOUBLE_STRING().getText();
        } else {
            raw = ctx.stringLit().SINGLE_SINGLE_STRING().getText();
        }

        int start = 0;
        while (start < raw.length()) {
            char c = Character.toLowerCase(raw.charAt(start));
            if (c == 'r') { isRaw = true; start++; }
            else if (c == 'b') { isBytes = true; start++; }
            else if (c == 'u') { start++; }
            else break;
        }

        String body = raw.substring(start);
        if (body.startsWith("\"\"\"") || body.startsWith("'''")) {
            content = body.substring(3, body.length() - 3);
        } else {
            content = body.substring(1, body.length() - 1);
        }

        if (!isRaw) {
            content = unescape(content);
        }
        int index = addConstant(new PyString(content));
        bytecode.add(new StackInstructions.LoadConst(index));
        return bytecode;
    }

    private static String unescape(String s) {
        return s.replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\'", "'")
                .replace("\\\\", "\\")
                .replace("\\0", "\0");
    }

    @Override
    public List<Instruction> visitVariable(MicroJParser.VariableContext ctx) {
        String name = ctx.NAME().getText();

        switch (name) {
            case "None" -> bytecode.add(new StackInstructions.LoadConst(INDEX_NONE));
            case "True" -> bytecode.add(new StackInstructions.LoadConst(INDEX_TRUE));
            case "False" -> bytecode.add(new StackInstructions.LoadConst(INDEX_FALSE));
            default -> bytecode.add(new StackInstructions.LoadName(name));
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitDictComprehension(MicroJParser.DictComprehensionContext ctx) {
        String tmpName = "__tmp_dict_" + (tmpCounter++) + "__";
        bytecode.add(new BuilderInstructions.BuildMap(0));
        bytecode.add(new StackInstructions.StoreName(tmpName));

        emitCompFor(ctx.compFor(), () -> {
            visit(ctx.expr(0));
            visit(ctx.expr(1));
            bytecode.add(new StackInstructions.LoadName(tmpName));
            bytecode.add(new AttributeInstructions.GetAttr("__setitem__"));
            bytecode.add(new CallInstructions.CallFunction(2, Constants.NO_KW_NAMES));
            bytecode.add(new StackInstructions.PopTop());
        });

        bytecode.add(new StackInstructions.LoadName(tmpName));
        return bytecode;
    }


    @Override
    public List<Instruction> visitSetComprehension(MicroJParser.SetComprehensionContext ctx) {
        String tmpName = "__tmp_set_" + (tmpCounter++) + "__";
        bytecode.add(new BuilderInstructions.BuildList(0));
        bytecode.add(new StackInstructions.StoreName(tmpName));

        emitCompFor(ctx.compFor(), () -> {
            visit(ctx.expr());
            bytecode.add(new StackInstructions.LoadName(tmpName));
            bytecode.add(new AttributeInstructions.GetAttr("append"));
            bytecode.add(new CallInstructions.CallFunction(1, Constants.NO_KW_NAMES));
            bytecode.add(new StackInstructions.PopTop());
        });

        bytecode.add(new StackInstructions.LoadName(tmpName));
        return bytecode;
    }

    @Override
    public List<Instruction> visitGenExp(MicroJParser.GenExpContext ctx) {
        String tmpName = "__tmp_gen_" + (tmpCounter++) + "__";
        bytecode.add(new BuilderInstructions.BuildList(0));
        bytecode.add(new StackInstructions.StoreName(tmpName));

        emitCompFor(ctx.compFor(), () -> {
            visit(ctx.expr());
            bytecode.add(new StackInstructions.LoadName(tmpName));
            bytecode.add(new AttributeInstructions.GetAttr("append"));
            bytecode.add(new CallInstructions.CallFunction(1, Constants.NO_KW_NAMES));
            bytecode.add(new StackInstructions.PopTop());
        });

        bytecode.add(new StackInstructions.LoadName(tmpName));
        return bytecode;
    }

    @Override
    public List<Instruction> visitTupleLiteral(MicroJParser.TupleLiteralContext ctx) {
        int size = ctx.expr().size();
        if (size == 1 && ctx.COMMA().isEmpty()) {
            return visit(ctx.expr(0));
        } else {
            for (var exprCtx : ctx.expr()) visit(exprCtx);
            bytecode.add(new BuilderInstructions.BuildTuple(size));
            return bytecode;
        }
    }

    @Override
    public List<Instruction> visitListLiteral(MicroJParser.ListLiteralContext ctx) {
        int size = (ctx.exprList() != null) ? ctx.exprList().expr().size() : 0;
        if (size > 0) {
            visit(ctx.exprList());
        }
        bytecode.add(new BuilderInstructions.BuildList(size));
        return bytecode;
    }

    @Override
    public List<Instruction> visitDictLiteral(MicroJParser.DictLiteralContext ctx) {
        if (ctx.dictList() != null) {
            int size = ctx.dictList().dictItem().size();
            for (var item : ctx.dictList().dictItem()) {
                visit(item.expr(0));
                visit(item.expr(1));
            }
            bytecode.add(new BuilderInstructions.BuildMap(size));
        } else {
            bytecode.add(new BuilderInstructions.BuildMap(0));
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitSubscriptAtom(MicroJParser.SubscriptAtomContext ctx) {
        visit(ctx.atom());
        visit(ctx.expr());
        bytecode.add(new OperatorInstructions.BinarySubscript());
        return bytecode;
    }

    @Override
    public List<Instruction> visitGetAttrAtom(MicroJParser.GetAttrAtomContext ctx) {
        visit(ctx.atom());
        bytecode.add(new AttributeInstructions.GetAttr(ctx.NAME().getText()));
        return bytecode;
    }

    @Override
    public List<Instruction> visitCallAtom(MicroJParser.CallAtomContext ctx) {
        int posCount = 0;
        List<String> keywordNames = new ArrayList<>();

        if (ctx.argList() != null) {
            for (var argCtx : ctx.argList().arg()) {
                if (argCtx instanceof MicroJParser.KwArgContext kwCtx) {
                    keywordNames.add(kwCtx.NAME().getText());
                } else {
                    posCount++;
                }
            }

            for (var argCtx : ctx.argList().arg()) {
                if (argCtx instanceof MicroJParser.GenExpArgContext genCtx) {
                    visit(genCtx);

                } else if (argCtx instanceof MicroJParser.KwArgContext kwCtx) {
                    visit(kwCtx.expr());

                } else if (argCtx instanceof MicroJParser.PosArgContext posCtx) {
                    visit(posCtx.expr());
                }
            }
        }

        if (ctx.atom() instanceof MicroJParser.GetAttrAtomContext attrCtx) {
            visit(attrCtx.atom());
            bytecode.add(new AttributeInstructions.GetAttr(attrCtx.NAME().getText()));
        } else {
            visit(ctx.atom());
        }

        bytecode.add(new CallInstructions.CallFunction(posCount, keywordNames.toArray(String[]::new)));
        return bytecode;
    }

    @Override
    public List<Instruction> visitParenAtom(MicroJParser.ParenAtomContext ctx) {
        return visit(ctx.expr());
    }

    // ==================== ОСТАЛЬНОЕ ====================

    @Override
    public List<Instruction> visitExprList(MicroJParser.ExprListContext ctx) {
        for (var exprCtx : ctx.expr()) {
            visit(exprCtx);
        }
        return bytecode;
    }

    @Override
    public List<Instruction> visitGeneralAssign(MicroJParser.GeneralAssignContext ctx) {
        int size = ctx.exprList().expr().size();
        for (var exprCtx : ctx.exprList().expr()) {
            visit(exprCtx);
        }
        if (size > 1) {
            bytecode.add(new BuilderInstructions.BuildTuple(size));
        }
        visitTarget(ctx.target());
        return bytecode;
    }

    @Override
    public List<Instruction> visitGeneralAssignList(MicroJParser.GeneralAssignListContext ctx) {
        int size = ctx.exprList().expr().size();
        for (var exprCtx : ctx.exprList().expr()) {
            visit(exprCtx);
        }
        if (size > 1) {
            bytecode.add(new BuilderInstructions.BuildTuple(size));
        }
        visitTargetList(ctx.targetList());
        return bytecode;
    }

    @Override
    public List<Instruction> visitListComprehension(MicroJParser.ListComprehensionContext ctx) {
        String tmpName = "__tmp_list_" + (tmpCounter++) + "__";
        bytecode.add(new BuilderInstructions.BuildList(0));
        bytecode.add(new StackInstructions.StoreName(tmpName));

        emitCompFor(ctx.compFor(), () -> {
            visit(ctx.expr());
            bytecode.add(new StackInstructions.LoadName(tmpName));
            bytecode.add(new AttributeInstructions.GetAttr("append"));
            bytecode.add(new CallInstructions.CallFunction(1, Constants.NO_KW_NAMES));
            bytecode.add(new StackInstructions.PopTop());
        });

        bytecode.add(new StackInstructions.LoadName(tmpName));
        return bytecode;
    }

    @Override
    public List<Instruction> visitGenExpArg(MicroJParser.GenExpArgContext ctx) {
        String tmpName = "__tmp_gen_" + (tmpCounter++) + "__";
        bytecode.add(new BuilderInstructions.BuildList(0));
        bytecode.add(new StackInstructions.StoreName(tmpName));

        emitCompFor(ctx.compFor(), () -> {
            visit(ctx.expr());
            bytecode.add(new StackInstructions.LoadName(tmpName));
            bytecode.add(new AttributeInstructions.GetAttr("append"));
            bytecode.add(new CallInstructions.CallFunction(1, new String[0]));
            bytecode.add(new StackInstructions.PopTop());
        });

        bytecode.add(new StackInstructions.LoadName(tmpName));
        return bytecode;
    }

    @Override
    public List<Instruction> visitForStatement(MicroJParser.ForStatementContext ctx) {
        LoopBlock block = new LoopBlock();
        block.isForLoop = true;
        visit(ctx.expr());
        bytecode.add(new ControlFlowInstructions.GetIter());
        block.continueTarget = bytecode.size();
        loopStack.push(block);

        int startIndex = bytecode.size();
        int exitJumpIndex = bytecode.size();
        bytecode.add(null);

        var targets = ctx.targetList().target();
        if (targets.size() == 1) visitTarget(targets.getFirst());
        else visitTargetList(ctx.targetList());

        visit(ctx.block(0));
        bytecode.add(new ControlFlowInstructions.JumpAbsolute(startIndex));

        int elseOrEndIndex = bytecode.size();
        if (ctx.ELSE() != null) {
            visit(ctx.block(1));
        }

        int endIndex = bytecode.size();
        for (int breakIndex : block.breakJumps) {
            bytecode.set(breakIndex, new ControlFlowInstructions.JumpAbsolute(endIndex));
        }
        bytecode.set(exitJumpIndex, new ControlFlowInstructions.ForIter(elseOrEndIndex));

        loopStack.pop();
        return bytecode;
    }

    @Override
    public List<Instruction> visitBreakStatement(MicroJParser.BreakStatementContext ctx) {
        if (loopStack.isEmpty()) {
            return new Exceptions.PySyntaxError("'break' outside loop").raise();
        }
        LoopBlock block = loopStack.peek();
        if (block.isForLoop) {
            bytecode.add(new StackInstructions.PopTop());
        }

        int breakIndex = bytecode.size();
        bytecode.add(null);
        block.breakJumps.add(breakIndex);
        return bytecode;
    }

    @Override
    public List<Instruction> visitContinueStatement(MicroJParser.ContinueStatementContext ctx) {
        if (loopStack.isEmpty()) {
            return new Exceptions.PySyntaxError("'continue' outside loop").raise();
        }
        LoopBlock block = loopStack.peek();
        bytecode.add(new ControlFlowInstructions.JumpAbsolute(block.continueTarget));
        return bytecode;
    }

    @Override
    public List<Instruction> visitSubscriptAssign(MicroJParser.SubscriptAssignContext ctx) {
        visit(ctx.atom());
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        bytecode.add(new OperatorInstructions.StoreSubscript());
        return bytecode;
    }

    @Override
    public List<Instruction> visitImportModule(MicroJParser.ImportModuleContext ctx) {
        String moduleName = ctx.NAME(0).getText();
        String alias = ctx.NAME().size() > 1 ? ctx.NAME(1).getText() : null;
        bytecode.add(new ImportInstructions.Import(moduleName, alias));
        return bytecode;
    }

    @Override
    public List<Instruction> visitImportFrom(MicroJParser.ImportFromContext ctx) {
        String moduleName = ctx.NAME().getText();
        List<String> names = new ArrayList<>();

        for (var nameCtx : ctx.importNames().NAME()) {
            names.add(nameCtx.getText());
        }

        bytecode.add(new ImportInstructions.ImportFrom(moduleName, names));
        return bytecode;
    }
}
