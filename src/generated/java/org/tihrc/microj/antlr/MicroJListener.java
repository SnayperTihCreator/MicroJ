// Generated from MicroJ.g4 by ANTLR 4.13.1
package org.tihrc.microj.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MicroJParser}.
 */
public interface MicroJListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MicroJParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(MicroJParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(MicroJParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MicroJParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MicroJParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(MicroJParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(MicroJParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStatement(MicroJParser.SimpleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStatement(MicroJParser.SimpleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(MicroJParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(MicroJParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MicroJParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MicroJParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#baseList}.
	 * @param ctx the parse tree
	 */
	void enterBaseList(MicroJParser.BaseListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#baseList}.
	 * @param ctx the parse tree
	 */
	void exitBaseList(MicroJParser.BaseListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(MicroJParser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(MicroJParser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(MicroJParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(MicroJParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(MicroJParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(MicroJParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MicroJParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MicroJParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#globalStatement}.
	 * @param ctx the parse tree
	 */
	void enterGlobalStatement(MicroJParser.GlobalStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#globalStatement}.
	 * @param ctx the parse tree
	 */
	void exitGlobalStatement(MicroJParser.GlobalStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#nonlocalStatement}.
	 * @param ctx the parse tree
	 */
	void enterNonlocalStatement(MicroJParser.NonlocalStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#nonlocalStatement}.
	 * @param ctx the parse tree
	 */
	void exitNonlocalStatement(MicroJParser.NonlocalStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#yieldStatement}.
	 * @param ctx the parse tree
	 */
	void enterYieldStatement(MicroJParser.YieldStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#yieldStatement}.
	 * @param ctx the parse tree
	 */
	void exitYieldStatement(MicroJParser.YieldStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(MicroJParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(MicroJParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(MicroJParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(MicroJParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void enterTryStatement(MicroJParser.TryStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#tryStatement}.
	 * @param ctx the parse tree
	 */
	void exitTryStatement(MicroJParser.TryStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#withStatement}.
	 * @param ctx the parse tree
	 */
	void enterWithStatement(MicroJParser.WithStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#withStatement}.
	 * @param ctx the parse tree
	 */
	void exitWithStatement(MicroJParser.WithStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#delStatement}.
	 * @param ctx the parse tree
	 */
	void enterDelStatement(MicroJParser.DelStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#delStatement}.
	 * @param ctx the parse tree
	 */
	void exitDelStatement(MicroJParser.DelStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#assertStatement}.
	 * @param ctx the parse tree
	 */
	void enterAssertStatement(MicroJParser.AssertStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#assertStatement}.
	 * @param ctx the parse tree
	 */
	void exitAssertStatement(MicroJParser.AssertStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#exceptClause}.
	 * @param ctx the parse tree
	 */
	void enterExceptClause(MicroJParser.ExceptClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#exceptClause}.
	 * @param ctx the parse tree
	 */
	void exitExceptClause(MicroJParser.ExceptClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#raiseStatement}.
	 * @param ctx the parse tree
	 */
	void enterRaiseStatement(MicroJParser.RaiseStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#raiseStatement}.
	 * @param ctx the parse tree
	 */
	void exitRaiseStatement(MicroJParser.RaiseStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(MicroJParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(MicroJParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MicroJParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MicroJParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MicroJParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MicroJParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MicroJParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MicroJParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubscriptAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptAssign(MicroJParser.SubscriptAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubscriptAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptAssign(MicroJParser.SubscriptAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttrAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAttrAssign(MicroJParser.AttrAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttrAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAttrAssign(MicroJParser.AttrAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GeneralAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterGeneralAssign(MicroJParser.GeneralAssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GeneralAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitGeneralAssign(MicroJParser.GeneralAssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GeneralAssignList}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterGeneralAssignList(MicroJParser.GeneralAssignListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GeneralAssignList}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitGeneralAssignList(MicroJParser.GeneralAssignListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MicroJParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MicroJParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#or_expr}.
	 * @param ctx the parse tree
	 */
	void enterOr_expr(MicroJParser.Or_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#or_expr}.
	 * @param ctx the parse tree
	 */
	void exitOr_expr(MicroJParser.Or_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#and_expr}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expr(MicroJParser.And_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#and_expr}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expr(MicroJParser.And_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#not_expr}.
	 * @param ctx the parse tree
	 */
	void enterNot_expr(MicroJParser.Not_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#not_expr}.
	 * @param ctx the parse tree
	 */
	void exitNot_expr(MicroJParser.Not_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(MicroJParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(MicroJParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#add_expr}.
	 * @param ctx the parse tree
	 */
	void enterAdd_expr(MicroJParser.Add_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#add_expr}.
	 * @param ctx the parse tree
	 */
	void exitAdd_expr(MicroJParser.Add_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#mul_expr}.
	 * @param ctx the parse tree
	 */
	void enterMul_expr(MicroJParser.Mul_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#mul_expr}.
	 * @param ctx the parse tree
	 */
	void exitMul_expr(MicroJParser.Mul_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#unary_expr}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expr(MicroJParser.Unary_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#unary_expr}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expr(MicroJParser.Unary_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#power}.
	 * @param ctx the parse tree
	 */
	void enterPower(MicroJParser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#power}.
	 * @param ctx the parse tree
	 */
	void exitPower(MicroJParser.PowerContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void enterCompareOp(MicroJParser.CompareOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void exitCompareOp(MicroJParser.CompareOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#addOp}.
	 * @param ctx the parse tree
	 */
	void enterAddOp(MicroJParser.AddOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#addOp}.
	 * @param ctx the parse tree
	 */
	void exitAddOp(MicroJParser.AddOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#mulOp}.
	 * @param ctx the parse tree
	 */
	void enterMulOp(MicroJParser.MulOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#mulOp}.
	 * @param ctx the parse tree
	 */
	void exitMulOp(MicroJParser.MulOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#compFor}.
	 * @param ctx the parse tree
	 */
	void enterCompFor(MicroJParser.CompForContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#compFor}.
	 * @param ctx the parse tree
	 */
	void exitCompFor(MicroJParser.CompForContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#compIf}.
	 * @param ctx the parse tree
	 */
	void enterCompIf(MicroJParser.CompIfContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#compIf}.
	 * @param ctx the parse tree
	 */
	void exitCompIf(MicroJParser.CompIfContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#compIter}.
	 * @param ctx the parse tree
	 */
	void enterCompIter(MicroJParser.CompIterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#compIter}.
	 * @param ctx the parse tree
	 */
	void exitCompIter(MicroJParser.CompIterContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTarget(MicroJParser.TargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTarget(MicroJParser.TargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#targetList}.
	 * @param ctx the parse tree
	 */
	void enterTargetList(MicroJParser.TargetListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#targetList}.
	 * @param ctx the parse tree
	 */
	void exitTargetList(MicroJParser.TargetListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#stringLit}.
	 * @param ctx the parse tree
	 */
	void enterStringLit(MicroJParser.StringLitContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#stringLit}.
	 * @param ctx the parse tree
	 */
	void exitStringLit(MicroJParser.StringLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SetComprehension}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterSetComprehension(MicroJParser.SetComprehensionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SetComprehension}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitSetComprehension(MicroJParser.SetComprehensionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterVariable(MicroJParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitVariable(MicroJParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(MicroJParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(MicroJParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GenExp}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterGenExp(MicroJParser.GenExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GenExp}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitGenExp(MicroJParser.GenExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterCallAtom(MicroJParser.CallAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitCallAtom(MicroJParser.CallAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DictLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterDictLiteral(MicroJParser.DictLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DictLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitDictLiteral(MicroJParser.DictLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(MicroJParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(MicroJParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Number}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumber(MicroJParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumber(MicroJParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(MicroJParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(MicroJParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterTupleLiteral(MicroJParser.TupleLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitTupleLiteral(MicroJParser.TupleLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GetAttrAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterGetAttrAtom(MicroJParser.GetAttrAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GetAttrAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitGetAttrAtom(MicroJParser.GetAttrAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParenAtom(MicroJParser.ParenAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParenAtom(MicroJParser.ParenAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DictComprehension}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterDictComprehension(MicroJParser.DictComprehensionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DictComprehension}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitDictComprehension(MicroJParser.DictComprehensionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListComprehension}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterListComprehension(MicroJParser.ListComprehensionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListComprehension}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitListComprehension(MicroJParser.ListComprehensionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterLambda(MicroJParser.LambdaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Lambda}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitLambda(MicroJParser.LambdaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubscriptAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptAtom(MicroJParser.SubscriptAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubscriptAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptAtom(MicroJParser.SubscriptAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImagLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterImagLiteral(MicroJParser.ImagLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImagLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitImagLiteral(MicroJParser.ImagLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#exprList}.
	 * @param ctx the parse tree
	 */
	void enterExprList(MicroJParser.ExprListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#exprList}.
	 * @param ctx the parse tree
	 */
	void exitExprList(MicroJParser.ExprListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#dictList}.
	 * @param ctx the parse tree
	 */
	void enterDictList(MicroJParser.DictListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#dictList}.
	 * @param ctx the parse tree
	 */
	void exitDictList(MicroJParser.DictListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#dictItem}.
	 * @param ctx the parse tree
	 */
	void enterDictItem(MicroJParser.DictItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#dictItem}.
	 * @param ctx the parse tree
	 */
	void exitDictItem(MicroJParser.DictItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(MicroJParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(MicroJParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code KwArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterKwArg(MicroJParser.KwArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code KwArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitKwArg(MicroJParser.KwArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GenExpArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterGenExpArg(MicroJParser.GenExpArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GenExpArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitGenExpArg(MicroJParser.GenExpArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PosArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterPosArg(MicroJParser.PosArgContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PosArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitPosArg(MicroJParser.PosArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImportModule}
	 * labeled alternative in {@link MicroJParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportModule(MicroJParser.ImportModuleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImportModule}
	 * labeled alternative in {@link MicroJParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportModule(MicroJParser.ImportModuleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImportFrom}
	 * labeled alternative in {@link MicroJParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportFrom(MicroJParser.ImportFromContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImportFrom}
	 * labeled alternative in {@link MicroJParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportFrom(MicroJParser.ImportFromContext ctx);
	/**
	 * Enter a parse tree produced by {@link MicroJParser#importNames}.
	 * @param ctx the parse tree
	 */
	void enterImportNames(MicroJParser.ImportNamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MicroJParser#importNames}.
	 * @param ctx the parse tree
	 */
	void exitImportNames(MicroJParser.ImportNamesContext ctx);
}