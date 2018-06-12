package calculator.parser;

import calculator.ast.AstNode;
import calculator.errors.IncompleteInputError;
import calculator.errors.ParseError;
import calculator.parser.grammar.CalculatorGrammarLexer;
import calculator.parser.grammar.CalculatorGrammarParser;
import calculator.parser.grammar.CalculatorGrammarParserBaseVisitor;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.io.StringReader;

public class Parser {
    public AstNode parse(String rawInput) {
        CharStream input;
        try {
            input = new ANTLRInputStream(new StringReader(rawInput));
        } catch (IOException ex) {
            throw new ParseError(
                    "Unexpected fatal error loading text. This should never happen!",
                    ex);
        }

        CalculatorGrammarLexer lexer = new CalculatorGrammarLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new ThrowingErrorListener());

        CommonTokenStream tokenStream = this.ensureIsComplete(new CommonTokenStream(lexer));

        CalculatorGrammarParser parser = new CalculatorGrammarParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(new ThrowingErrorListener());

        CalculatorGrammarParser.ProgramContext entryPoint = parser.program();
        return new AstConverter().visitProgram(entryPoint);
    }

    public CommonTokenStream ensureIsComplete(CommonTokenStream stream) {
        int balanceCount = 0;
        int lastLine = 0;
        boolean lastWasLineContinuation = false;
        stream.fill();
        for (Token token : stream.getTokens()) {
            int type = token.getType();
            lastLine = token.getLine();

            // Check for parenthesis nesting
            if (type == CalculatorGrammarLexer.LPAREN) {
                balanceCount += 1;
            } else if (type == CalculatorGrammarLexer.RPAREN) {
                balanceCount -= 1;
            } else if (type == CalculatorGrammarLexer.LINE_BREAK) {
                this.verifyBalanceCount(token.getLine(), balanceCount);
            }

            if (type != CalculatorGrammarLexer.EOF) {
                lastWasLineContinuation = (type == CalculatorGrammarLexer.LINE_CONTINUATION);
            }
        }

        this.verifyBalanceCount(lastLine, balanceCount);

        if (lastWasLineContinuation) {
            throw new IncompleteInputError("Line continuation at end of file at line " + lastLine);
        }

        return stream;
    }

    private void verifyBalanceCount(int lineno, int balanceCount) {
        if (balanceCount > 0) {
            // If the balance count is 0, the lexer and parser itself will catch it.
            throw new IncompleteInputError(
                    String.format("Parens on line %d are unbalanced; missing %d closing parens",
                            lineno, balanceCount));
        } else if (balanceCount < 0) {
            throw new IncompleteInputError(
                    String.format("Parens on line %d are unbalanced; missing %d opening parens",
                            lineno, -balanceCount));
        }
    }

    private static class AstConverter extends CalculatorGrammarParserBaseVisitor<AstNode> {
        private IList<AstNode> asList(AstNode... nodes) {
            IList<AstNode> list = new DoubleLinkedList<>();
            for (AstNode node : nodes) {
                list.add(node);
            }
            return list;
        }

        @Override
        public AstNode visitProgram(CalculatorGrammarParser.ProgramContext ctx) {
            IList<AstNode> params = new DoubleLinkedList<>();
            for (CalculatorGrammarParser.StatementContext stmt : ctx.statements) {
                params.add(this.visit(stmt));
            }
            return new AstNode("block", params);
        }

        @Override
        public AstNode visitAssignStmt(CalculatorGrammarParser.AssignStmtContext ctx) {
            return new AstNode(
                    "assign",
                    this.asList(
                            new AstNode(ctx.varName.getText()),
                            this.visit(ctx.expr)));
        }

        @Override
        public AstNode visitExprStmt(CalculatorGrammarParser.ExprStmtContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitPowExprBin(CalculatorGrammarParser.PowExprBinContext ctx) {
            return new AstNode(
                    ctx.op.getText(),
                    this.asList(this.visit(ctx.left), this.visit(ctx.right)));
        }

        @Override
        public AstNode visitPowExprSingle(CalculatorGrammarParser.PowExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitNegExprUnary(CalculatorGrammarParser.NegExprUnaryContext ctx) {
            return new AstNode("negate", this.asList(this.visit(ctx.expr)));
        }

        @Override
        public AstNode visitNegExprSingle(CalculatorGrammarParser.NegExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitAddExprBin(CalculatorGrammarParser.AddExprBinContext ctx) {
            return new AstNode(
                    ctx.op.getText(),
                    this.asList(this.visit(ctx.left), this.visit(ctx.right)));
        }

        @Override
        public AstNode visitAddExprSingle(CalculatorGrammarParser.AddExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitMultExprBin(CalculatorGrammarParser.MultExprBinContext ctx) {
            return new AstNode(
                    ctx.op.getText(),
                    this.asList(this.visit(ctx.left), this.visit(ctx.right)));
        }

        @Override
        public AstNode visitMultExprSingle(CalculatorGrammarParser.MultExprSingleContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitNumber(CalculatorGrammarParser.NumberContext ctx) {
            return new AstNode(Double.parseDouble(ctx.value.getText()));
        }

        @Override
        public AstNode visitRawString(CalculatorGrammarParser.RawStringContext ctx) {
            throw new UnsupportedOperationException();
        }

        @Override
        public AstNode visitVariable(CalculatorGrammarParser.VariableContext ctx) {
            return new AstNode(ctx.getText());
        }

        @Override
        public AstNode visitFuncName(CalculatorGrammarParser.FuncNameContext ctx) {
            IList<AstNode> params = this.parseArgList(ctx.args);
            return new AstNode(ctx.funcName.getText(), params);
        }

        @Override
        public AstNode visitParenExpr(CalculatorGrammarParser.ParenExprContext ctx) {
            return this.visit(ctx.expr);
        }

        @Override
        public AstNode visitArglist(CalculatorGrammarParser.ArglistContext ctx) {
            throw new UnsupportedOperationException();
        }

        private IList<AstNode> parseArgList(CalculatorGrammarParser.ArglistContext args) {
            IList<AstNode> out = new DoubleLinkedList<>();
            for (CalculatorGrammarParser.AddExprContext item : args.values) {
                out.add(this.visit(item));
            }
            return out;
        }
    }

    public static class ThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line,
                                int charPositionInLine,
                                String msg,
                                RecognitionException e) {
            String error;
            if (e == null) {
                error = msg;
            } else {
                String tokenText = this.escape(e.getOffendingToken().getText());
                error = String.format("Unxpected '%s'", tokenText);
            }
            throw new ParseError(
                    String.format(
                            "Line %d, col %d: %s",
                            line,
                            charPositionInLine,
                            error),
                    e);
        }

        private String escape(String str) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (ch == '\n') {
                    builder.append("\\n");
                } else if (ch == '\r') {
                    builder.append("\\r");
                } else if (ch == '\t') {
                    builder.append("\\t");
                } else {
                    builder.append(ch);
                }
            }
            return builder.toString();
        }
    }
}
