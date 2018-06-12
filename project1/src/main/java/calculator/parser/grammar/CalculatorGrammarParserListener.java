// Generated from CalculatorGrammarParser.g4 by ANTLR 4.5.1
package calculator.parser.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalculatorGrammarParser}.
 */
public interface CalculatorGrammarParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CalculatorGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CalculatorGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CalculatorGrammarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link CalculatorGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(CalculatorGrammarParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignStmt}
	 * labeled alternative in {@link CalculatorGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(CalculatorGrammarParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link CalculatorGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(CalculatorGrammarParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link CalculatorGrammarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(CalculatorGrammarParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExprBin(CalculatorGrammarParser.AddExprBinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExprBin(CalculatorGrammarParser.AddExprBinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExprSingle(CalculatorGrammarParser.AddExprSingleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExprSingle(CalculatorGrammarParser.AddExprSingleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#multiplyExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultExprSingle(CalculatorGrammarParser.MultExprSingleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#multiplyExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultExprSingle(CalculatorGrammarParser.MultExprSingleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#multiplyExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultExprBin(CalculatorGrammarParser.MultExprBinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#multiplyExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultExprBin(CalculatorGrammarParser.MultExprBinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negExprUnary}
	 * labeled alternative in {@link CalculatorGrammarParser#negExpr}.
	 * @param ctx the parse tree
	 */
	void enterNegExprUnary(CalculatorGrammarParser.NegExprUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negExprUnary}
	 * labeled alternative in {@link CalculatorGrammarParser#negExpr}.
	 * @param ctx the parse tree
	 */
	void exitNegExprUnary(CalculatorGrammarParser.NegExprUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#negExpr}.
	 * @param ctx the parse tree
	 */
	void enterNegExprSingle(CalculatorGrammarParser.NegExprSingleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#negExpr}.
	 * @param ctx the parse tree
	 */
	void exitNegExprSingle(CalculatorGrammarParser.NegExprSingleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#powExpr}.
	 * @param ctx the parse tree
	 */
	void enterPowExprBin(CalculatorGrammarParser.PowExprBinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExprBin}
	 * labeled alternative in {@link CalculatorGrammarParser#powExpr}.
	 * @param ctx the parse tree
	 */
	void exitPowExprBin(CalculatorGrammarParser.PowExprBinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#powExpr}.
	 * @param ctx the parse tree
	 */
	void enterPowExprSingle(CalculatorGrammarParser.PowExprSingleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExprSingle}
	 * labeled alternative in {@link CalculatorGrammarParser#powExpr}.
	 * @param ctx the parse tree
	 */
	void exitPowExprSingle(CalculatorGrammarParser.PowExprSingleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code number}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void enterNumber(CalculatorGrammarParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code number}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void exitNumber(CalculatorGrammarParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rawString}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void enterRawString(CalculatorGrammarParser.RawStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rawString}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void exitRawString(CalculatorGrammarParser.RawStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variable}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void enterVariable(CalculatorGrammarParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void exitVariable(CalculatorGrammarParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcName}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void enterFuncName(CalculatorGrammarParser.FuncNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcName}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void exitFuncName(CalculatorGrammarParser.FuncNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(CalculatorGrammarParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link CalculatorGrammarParser#atomExpr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(CalculatorGrammarParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorGrammarParser#arglist}.
	 * @param ctx the parse tree
	 */
	void enterArglist(CalculatorGrammarParser.ArglistContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorGrammarParser#arglist}.
	 * @param ctx the parse tree
	 */
	void exitArglist(CalculatorGrammarParser.ArglistContext ctx);
}