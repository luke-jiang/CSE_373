// Generated from CalculatorGrammarParser.g4 by ANTLR 4.5.1
package calculator.parser.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CalculatorGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CalculatorGrammarParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CalculatorGrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CalculatorGrammarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link CalculatorGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(CalculatorGrammarParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link CalculatorGrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(CalculatorGrammarParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExprBin(CalculatorGrammarParser.AddExprBinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExprSingle(CalculatorGrammarParser.AddExprSingleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#multiplyExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExprSingle(CalculatorGrammarParser.MultExprSingleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#multiplyExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExprBin(CalculatorGrammarParser.MultExprBinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negExprUnary}
	 * labeled alternative in {@link CalculatorGrammarParser#negExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegExprUnary(CalculatorGrammarParser.NegExprUnaryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#negExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegExprSingle(CalculatorGrammarParser.NegExprSingleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#powExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowExprBin(CalculatorGrammarParser.PowExprBinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code powExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#powExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowExprSingle(CalculatorGrammarParser.PowExprSingleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code number}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(CalculatorGrammarParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rawString}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRawString(CalculatorGrammarParser.RawStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(CalculatorGrammarParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcName}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncName(CalculatorGrammarParser.FuncNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(CalculatorGrammarParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorGrammarParser#arglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArglist(CalculatorGrammarParser.ArglistContext ctx);
}