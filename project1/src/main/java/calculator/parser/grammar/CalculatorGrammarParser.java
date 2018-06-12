// Generated from CalculatorGrammarParser.g4 by ANTLR 4.5.1
package calculator.parser.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalculatorGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LINE_BREAK=1, WS=2, COMMENT=3, LINE_CONTINUATION=4, IDENTIFIER=5, NUMBER=6, 
		STRING=7, ASSIGN=8, PLUS=9, MINUS=10, MULTIPLY=11, DIVIDE=12, POW=13, 
		COMMA=14, LPAREN=15, RPAREN=16, ERROR_TOKEN=17, IGNORE_NEWLINES_LINE_BREAK=18;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_addExpr = 2, RULE_multiplyExpr = 3, 
		RULE_negExpr = 4, RULE_powExpr = 5, RULE_atomExpr = 6, RULE_arglist = 7;
	public static final String[] ruleNames = {
		"program", "statement", "addExpr", "multiplyExpr", "negExpr", "powExpr", 
		"atomExpr", "arglist"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, "':='", "'+'", "'-'", 
		"'*'", "'/'", "'^'", "','", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LINE_BREAK", "WS", "COMMENT", "LINE_CONTINUATION", "IDENTIFIER", 
		"NUMBER", "STRING", "ASSIGN", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "POW", 
		"COMMA", "LPAREN", "RPAREN", "ERROR_TOKEN", "IGNORE_NEWLINES_LINE_BREAK"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CalculatorGrammarParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CalculatorGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public StatementContext statement;
		public List<StatementContext> statements = new ArrayList<StatementContext>();
		public TerminalNode EOF() { return getToken(CalculatorGrammarParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IDENTIFIER) | (1L << NUMBER) | (1L << STRING) | (1L << MINUS) | (1L << LPAREN))) != 0)) {
				{
				{
				setState(16);
				((ProgramContext)_localctx).statement = statement();
				((ProgramContext)_localctx).statements.add(((ProgramContext)_localctx).statement);
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprStmtContext extends StatementContext {
		public AddExprContext expr;
		public TerminalNode LINE_BREAK() { return getToken(CalculatorGrammarParser.LINE_BREAK, 0); }
		public AddExprContext addExpr() {
			return getRuleContext(AddExprContext.class,0);
		}
		public ExprStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterExprStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitExprStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitExprStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignStmtContext extends StatementContext {
		public Token varName;
		public AddExprContext expr;
		public TerminalNode ASSIGN() { return getToken(CalculatorGrammarParser.ASSIGN, 0); }
		public TerminalNode LINE_BREAK() { return getToken(CalculatorGrammarParser.LINE_BREAK, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CalculatorGrammarParser.IDENTIFIER, 0); }
		public AddExprContext addExpr() {
			return getRuleContext(AddExprContext.class,0);
		}
		public AssignStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitAssignStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(32);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				((AssignStmtContext)_localctx).varName = match(IDENTIFIER);
				setState(25);
				match(ASSIGN);
				setState(26);
				((AssignStmtContext)_localctx).expr = addExpr(0);
				setState(27);
				match(LINE_BREAK);
				}
				break;
			case 2:
				_localctx = new ExprStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(29);
				((ExprStmtContext)_localctx).expr = addExpr(0);
				setState(30);
				match(LINE_BREAK);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AddExprContext extends ParserRuleContext {
		public AddExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addExpr; }
	 
		public AddExprContext() { }
		public void copyFrom(AddExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AddExprBinContext extends AddExprContext {
		public AddExprContext left;
		public Token op;
		public MultiplyExprContext right;
		public AddExprContext addExpr() {
			return getRuleContext(AddExprContext.class,0);
		}
		public MultiplyExprContext multiplyExpr() {
			return getRuleContext(MultiplyExprContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(CalculatorGrammarParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(CalculatorGrammarParser.MINUS, 0); }
		public AddExprBinContext(AddExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterAddExprBin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitAddExprBin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitAddExprBin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddExprSingleContext extends AddExprContext {
		public MultiplyExprContext expr;
		public MultiplyExprContext multiplyExpr() {
			return getRuleContext(MultiplyExprContext.class,0);
		}
		public AddExprSingleContext(AddExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterAddExprSingle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitAddExprSingle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitAddExprSingle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddExprContext addExpr() throws RecognitionException {
		return addExpr(0);
	}

	private AddExprContext addExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AddExprContext _localctx = new AddExprContext(_ctx, _parentState);
		AddExprContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_addExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new AddExprSingleContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(35);
			((AddExprSingleContext)_localctx).expr = multiplyExpr(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(42);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new AddExprBinContext(new AddExprContext(_parentctx, _parentState));
					((AddExprBinContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_addExpr);
					setState(37);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(38);
					((AddExprBinContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
						((AddExprBinContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(39);
					((AddExprBinContext)_localctx).right = multiplyExpr(0);
					}
					} 
				}
				setState(44);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class MultiplyExprContext extends ParserRuleContext {
		public MultiplyExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplyExpr; }
	 
		public MultiplyExprContext() { }
		public void copyFrom(MultiplyExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MultExprSingleContext extends MultiplyExprContext {
		public NegExprContext expr;
		public NegExprContext negExpr() {
			return getRuleContext(NegExprContext.class,0);
		}
		public MultExprSingleContext(MultiplyExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterMultExprSingle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitMultExprSingle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitMultExprSingle(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultExprBinContext extends MultiplyExprContext {
		public MultiplyExprContext left;
		public Token op;
		public NegExprContext right;
		public MultiplyExprContext multiplyExpr() {
			return getRuleContext(MultiplyExprContext.class,0);
		}
		public NegExprContext negExpr() {
			return getRuleContext(NegExprContext.class,0);
		}
		public TerminalNode MULTIPLY() { return getToken(CalculatorGrammarParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(CalculatorGrammarParser.DIVIDE, 0); }
		public MultExprBinContext(MultiplyExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterMultExprBin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitMultExprBin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitMultExprBin(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplyExprContext multiplyExpr() throws RecognitionException {
		return multiplyExpr(0);
	}

	private MultiplyExprContext multiplyExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MultiplyExprContext _localctx = new MultiplyExprContext(_ctx, _parentState);
		MultiplyExprContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_multiplyExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new MultExprSingleContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(46);
			((MultExprSingleContext)_localctx).expr = negExpr();
			}
			_ctx.stop = _input.LT(-1);
			setState(53);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new MultExprBinContext(new MultiplyExprContext(_parentctx, _parentState));
					((MultExprBinContext)_localctx).left = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_multiplyExpr);
					setState(48);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(49);
					((MultExprBinContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==MULTIPLY || _la==DIVIDE) ) {
						((MultExprBinContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					} else {
						consume();
					}
					setState(50);
					((MultExprBinContext)_localctx).right = negExpr();
					}
					} 
				}
				setState(55);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NegExprContext extends ParserRuleContext {
		public NegExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negExpr; }
	 
		public NegExprContext() { }
		public void copyFrom(NegExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NegExprSingleContext extends NegExprContext {
		public PowExprContext expr;
		public PowExprContext powExpr() {
			return getRuleContext(PowExprContext.class,0);
		}
		public NegExprSingleContext(NegExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterNegExprSingle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitNegExprSingle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitNegExprSingle(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegExprUnaryContext extends NegExprContext {
		public NegExprContext expr;
		public TerminalNode MINUS() { return getToken(CalculatorGrammarParser.MINUS, 0); }
		public NegExprContext negExpr() {
			return getRuleContext(NegExprContext.class,0);
		}
		public NegExprUnaryContext(NegExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterNegExprUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitNegExprUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitNegExprUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegExprContext negExpr() throws RecognitionException {
		NegExprContext _localctx = new NegExprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_negExpr);
		try {
			setState(59);
			switch (_input.LA(1)) {
			case MINUS:
				_localctx = new NegExprUnaryContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				match(MINUS);
				setState(57);
				((NegExprUnaryContext)_localctx).expr = negExpr();
				}
				break;
			case IDENTIFIER:
			case NUMBER:
			case STRING:
			case LPAREN:
				_localctx = new NegExprSingleContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
				((NegExprSingleContext)_localctx).expr = powExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PowExprContext extends ParserRuleContext {
		public PowExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_powExpr; }
	 
		public PowExprContext() { }
		public void copyFrom(PowExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PowExprBinContext extends PowExprContext {
		public AtomExprContext left;
		public Token op;
		public PowExprContext right;
		public AtomExprContext atomExpr() {
			return getRuleContext(AtomExprContext.class,0);
		}
		public TerminalNode POW() { return getToken(CalculatorGrammarParser.POW, 0); }
		public PowExprContext powExpr() {
			return getRuleContext(PowExprContext.class,0);
		}
		public PowExprBinContext(PowExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterPowExprBin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitPowExprBin(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitPowExprBin(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowExprSingleContext extends PowExprContext {
		public AtomExprContext expr;
		public AtomExprContext atomExpr() {
			return getRuleContext(AtomExprContext.class,0);
		}
		public PowExprSingleContext(PowExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterPowExprSingle(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitPowExprSingle(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitPowExprSingle(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PowExprContext powExpr() throws RecognitionException {
		PowExprContext _localctx = new PowExprContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_powExpr);
		try {
			setState(66);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new PowExprBinContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				((PowExprBinContext)_localctx).left = atomExpr();
				setState(62);
				((PowExprBinContext)_localctx).op = match(POW);
				setState(63);
				((PowExprBinContext)_localctx).right = powExpr();
				}
				break;
			case 2:
				_localctx = new PowExprSingleContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				((PowExprSingleContext)_localctx).expr = atomExpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomExprContext extends ParserRuleContext {
		public AtomExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomExpr; }
	 
		public AtomExprContext() { }
		public void copyFrom(AtomExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NumberContext extends AtomExprContext {
		public Token value;
		public TerminalNode NUMBER() { return getToken(CalculatorGrammarParser.NUMBER, 0); }
		public NumberContext(AtomExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncNameContext extends AtomExprContext {
		public Token funcName;
		public ArglistContext args;
		public TerminalNode LPAREN() { return getToken(CalculatorGrammarParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CalculatorGrammarParser.RPAREN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CalculatorGrammarParser.IDENTIFIER, 0); }
		public ArglistContext arglist() {
			return getRuleContext(ArglistContext.class,0);
		}
		public FuncNameContext(AtomExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterFuncName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitFuncName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitFuncName(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RawStringContext extends AtomExprContext {
		public Token rawText;
		public TerminalNode STRING() { return getToken(CalculatorGrammarParser.STRING, 0); }
		public RawStringContext(AtomExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterRawString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitRawString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitRawString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableContext extends AtomExprContext {
		public Token varName;
		public TerminalNode IDENTIFIER() { return getToken(CalculatorGrammarParser.IDENTIFIER, 0); }
		public VariableContext(AtomExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprContext extends AtomExprContext {
		public AddExprContext expr;
		public TerminalNode LPAREN() { return getToken(CalculatorGrammarParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(CalculatorGrammarParser.RPAREN, 0); }
		public AddExprContext addExpr() {
			return getRuleContext(AddExprContext.class,0);
		}
		public ParenExprContext(AtomExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomExprContext atomExpr() throws RecognitionException {
		AtomExprContext _localctx = new AtomExprContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_atomExpr);
		try {
			setState(80);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				((NumberContext)_localctx).value = match(NUMBER);
				}
				break;
			case 2:
				_localctx = new RawStringContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				((RawStringContext)_localctx).rawText = match(STRING);
				}
				break;
			case 3:
				_localctx = new VariableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(70);
				((VariableContext)_localctx).varName = match(IDENTIFIER);
				}
				break;
			case 4:
				_localctx = new FuncNameContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(71);
				((FuncNameContext)_localctx).funcName = match(IDENTIFIER);
				setState(72);
				match(LPAREN);
				setState(73);
				((FuncNameContext)_localctx).args = arglist();
				setState(74);
				match(RPAREN);
				}
				break;
			case 5:
				_localctx = new ParenExprContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(76);
				match(LPAREN);
				setState(77);
				((ParenExprContext)_localctx).expr = addExpr(0);
				setState(78);
				match(RPAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArglistContext extends ParserRuleContext {
		public AddExprContext addExpr;
		public List<AddExprContext> values = new ArrayList<AddExprContext>();
		public List<AddExprContext> addExpr() {
			return getRuleContexts(AddExprContext.class);
		}
		public AddExprContext addExpr(int i) {
			return getRuleContext(AddExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(CalculatorGrammarParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(CalculatorGrammarParser.COMMA, i);
		}
		public ArglistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arglist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).enterArglist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CalculatorGrammarParserListener ) ((CalculatorGrammarParserListener)listener).exitArglist(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CalculatorGrammarParserVisitor ) return ((CalculatorGrammarParserVisitor<? extends T>)visitor).visitArglist(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArglistContext arglist() throws RecognitionException {
		ArglistContext _localctx = new ArglistContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_arglist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IDENTIFIER) | (1L << NUMBER) | (1L << STRING) | (1L << MINUS) | (1L << LPAREN))) != 0)) {
				{
				setState(82);
				((ArglistContext)_localctx).addExpr = addExpr(0);
				((ArglistContext)_localctx).values.add(((ArglistContext)_localctx).addExpr);
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(83);
					match(COMMA);
					setState(84);
					((ArglistContext)_localctx).addExpr = addExpr(0);
					((ArglistContext)_localctx).values.add(((ArglistContext)_localctx).addExpr);
					}
					}
					setState(89);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return addExpr_sempred((AddExprContext)_localctx, predIndex);
		case 3:
			return multiplyExpr_sempred((MultiplyExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean addExpr_sempred(AddExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean multiplyExpr_sempred(MultiplyExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\24_\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\7\2\24\n\2\f\2"+
		"\16\2\27\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3#\n\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\7\4+\n\4\f\4\16\4.\13\4\3\5\3\5\3\5\3\5\3\5\3\5\7\5\66"+
		"\n\5\f\5\16\59\13\5\3\6\3\6\3\6\5\6>\n\6\3\7\3\7\3\7\3\7\3\7\5\7E\n\7"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bS\n\b\3\t\3\t\3\t"+
		"\7\tX\n\t\f\t\16\t[\13\t\5\t]\n\t\3\t\2\4\6\b\n\2\4\6\b\n\f\16\20\2\4"+
		"\3\2\13\f\3\2\r\16b\2\25\3\2\2\2\4\"\3\2\2\2\6$\3\2\2\2\b/\3\2\2\2\n="+
		"\3\2\2\2\fD\3\2\2\2\16R\3\2\2\2\20\\\3\2\2\2\22\24\5\4\3\2\23\22\3\2\2"+
		"\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\30\3\2\2\2\27\25\3\2\2"+
		"\2\30\31\7\2\2\3\31\3\3\2\2\2\32\33\7\7\2\2\33\34\7\n\2\2\34\35\5\6\4"+
		"\2\35\36\7\3\2\2\36#\3\2\2\2\37 \5\6\4\2 !\7\3\2\2!#\3\2\2\2\"\32\3\2"+
		"\2\2\"\37\3\2\2\2#\5\3\2\2\2$%\b\4\1\2%&\5\b\5\2&,\3\2\2\2\'(\f\4\2\2"+
		"()\t\2\2\2)+\5\b\5\2*\'\3\2\2\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-\7\3\2\2"+
		"\2.,\3\2\2\2/\60\b\5\1\2\60\61\5\n\6\2\61\67\3\2\2\2\62\63\f\4\2\2\63"+
		"\64\t\3\2\2\64\66\5\n\6\2\65\62\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678"+
		"\3\2\2\28\t\3\2\2\29\67\3\2\2\2:;\7\f\2\2;>\5\n\6\2<>\5\f\7\2=:\3\2\2"+
		"\2=<\3\2\2\2>\13\3\2\2\2?@\5\16\b\2@A\7\17\2\2AB\5\f\7\2BE\3\2\2\2CE\5"+
		"\16\b\2D?\3\2\2\2DC\3\2\2\2E\r\3\2\2\2FS\7\b\2\2GS\7\t\2\2HS\7\7\2\2I"+
		"J\7\7\2\2JK\7\21\2\2KL\5\20\t\2LM\7\22\2\2MS\3\2\2\2NO\7\21\2\2OP\5\6"+
		"\4\2PQ\7\22\2\2QS\3\2\2\2RF\3\2\2\2RG\3\2\2\2RH\3\2\2\2RI\3\2\2\2RN\3"+
		"\2\2\2S\17\3\2\2\2TY\5\6\4\2UV\7\20\2\2VX\5\6\4\2WU\3\2\2\2X[\3\2\2\2"+
		"YW\3\2\2\2YZ\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2\\T\3\2\2\2\\]\3\2\2\2]\21\3\2"+
		"\2\2\13\25\",\67=DRY\\";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}