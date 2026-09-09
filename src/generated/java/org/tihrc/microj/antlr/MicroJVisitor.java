// Generated from MicroJ.g4 by ANTLR 4.13.1
package org.tihrc.microj.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MicroJParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MicroJVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MicroJParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(MicroJParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MicroJParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#simpleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStatement(MicroJParser.SimpleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#funcDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDef(MicroJParser.FuncDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MicroJParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(MicroJParser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(MicroJParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MicroJParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(MicroJParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(MicroJParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#tryStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStatement(MicroJParser.TryStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#exceptClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptClause(MicroJParser.ExceptClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MicroJParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MicroJParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MicroJParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MicroJParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubscriptAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptAssign(MicroJParser.SubscriptAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrAssign(MicroJParser.AttrAssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeneralAssign}
	 * labeled alternative in {@link MicroJParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeneralAssign(MicroJParser.GeneralAssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(MicroJParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#or_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr_expr(MicroJParser.Or_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#and_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expr(MicroJParser.And_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#not_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNot_expr(MicroJParser.Not_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(MicroJParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#add_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd_expr(MicroJParser.Add_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#mul_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMul_expr(MicroJParser.Mul_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#unary_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expr(MicroJParser.Unary_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#power}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(MicroJParser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#compareOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareOp(MicroJParser.CompareOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#addOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddOp(MicroJParser.AddOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#mulOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulOp(MicroJParser.MulOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTarget(MicroJParser.TargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#targetList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTargetList(MicroJParser.TargetListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(MicroJParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(MicroJParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(MicroJParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(MicroJParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(MicroJParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleLiteral(MicroJParser.TupleLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GetAttrAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGetAttrAtom(MicroJParser.GetAttrAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallAtom(MicroJParser.CallAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenAtom(MicroJParser.ParenAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubscriptAtom}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptAtom(MicroJParser.SubscriptAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImagLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImagLiteral(MicroJParser.ImagLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DictLiteral}
	 * labeled alternative in {@link MicroJParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLiteral(MicroJParser.DictLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#exprList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprList(MicroJParser.ExprListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#dictList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictList(MicroJParser.DictListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#dictItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictItem(MicroJParser.DictItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(MicroJParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code KwArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKwArg(MicroJParser.KwArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PosArg}
	 * labeled alternative in {@link MicroJParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPosArg(MicroJParser.PosArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImportModule}
	 * labeled alternative in {@link MicroJParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportModule(MicroJParser.ImportModuleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImportFrom}
	 * labeled alternative in {@link MicroJParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportFrom(MicroJParser.ImportFromContext ctx);
	/**
	 * Visit a parse tree produced by {@link MicroJParser#importNames}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportNames(MicroJParser.ImportNamesContext ctx);
}