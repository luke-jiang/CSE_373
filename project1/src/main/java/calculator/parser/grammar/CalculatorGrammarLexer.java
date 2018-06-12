// Generated from CalculatorGrammarLexer.g4 by ANTLR 4.5.1
package calculator.parser.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CalculatorGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LINE_BREAK=1, WS=2, COMMENT=3, LINE_CONTINUATION=4, IDENTIFIER=5, NUMBER=6, 
		STRING=7, ASSIGN=8, PLUS=9, MINUS=10, MULTIPLY=11, DIVIDE=12, POW=13, 
		COMMA=14, LPAREN=15, RPAREN=16, ERROR_TOKEN=17, IGNORE_NEWLINES_LINE_BREAK=18;
	public static final int
		LINE_CONTINUATION_CHANNEL=2;
	public static final int IGNORE_NEWLINES = 1;
	public static String[] modeNames = {
		"DEFAULT_MODE", "IGNORE_NEWLINES"
	};

	public static final String[] ruleNames = {
		"LINE_BREAK", "WS", "COMMENT", "LINE_CONTINUATION", "IDENTIFIER", "NUMBER", 
		"DIGIT", "STRING", "STRING_CHAR", "ASSIGN", "PLUS", "MINUS", "MULTIPLY", 
		"DIVIDE", "POW", "COMMA", "LPAREN", "RPAREN", "ERROR_TOKEN", "IGNORE_NEWLINES_LPAREN", 
		"IGNORE_NEWLINES_RPAREN", "IGNORE_NEWLINES_LINE_BREAK", "IGNORE_NEWLINE_WS", 
		"IGNORE_NEWLINE_COMMENT", "IGNORE_NEWLINE_LINE_CONTINUATION", "IGNORE_NEWLINE_IDENTIFIER", 
		"IGNORE_NEWLINE_NUMBER", "IGNORE_NEWLINE_STRING", "IGNORE_NEWLINE_ASSIGN", 
		"IGNORE_NEWLINE_PLUS", "IGNORE_NEWLINE_MINUS", "IGNORE_NEWLINE_MULTIPLY", 
		"IGNORE_NEWLINE_DIVIDE", "IGNORE_NEWLINE_POW", "IGNORE_NEWLINE_COMMA", 
		"IGNORE_ERROR_TOKEN"
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


	public CalculatorGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CalculatorGrammarLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\24\u00f0\b\1\b\1"+
		"\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t"+
		"\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4"+
		"\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4"+
		"\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4"+
		" \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\6\2N\n\2\r\2\16\2O\3\3\6\3S\n"+
		"\3\r\3\16\3T\3\3\3\3\3\4\3\4\7\4[\n\4\f\4\16\4^\13\4\3\4\3\4\3\5\3\5\6"+
		"\5d\n\5\r\5\16\5e\3\5\3\5\3\6\3\6\7\6l\n\6\f\6\16\6o\13\6\3\7\6\7r\n\7"+
		"\r\7\16\7s\3\7\3\7\6\7x\n\7\r\7\16\7y\5\7|\n\7\3\b\3\b\3\t\3\t\7\t\u0082"+
		"\n\t\f\t\16\t\u0085\13\t\3\t\3\t\3\n\3\n\3\n\5\n\u008c\n\n\3\13\3\13\3"+
		"\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\3\26\3\27\6\27\u00b0\n\27\r\27\16\27\u00b1\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3"+
		"\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3"+
		"\36\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3#\3#"+
		"\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%\2\2&\4\3\6\4\b\5\n\6\f\7\16\b\20\2\22\t"+
		"\24\2\26\n\30\13\32\f\34\r\36\16 \17\"\20$\21&\22(\23*\2,\2.\24\60\2\62"+
		"\2\64\2\66\28\2:\2<\2>\2@\2B\2D\2F\2H\2J\2\4\2\3\n\4\2\f\f\17\17\4\2\13"+
		"\13\"\"\5\2C\\aac|\6\2\62;C\\aac|\3\2\60\60\3\2\62;\6\2\f\f\17\17$$^^"+
		"\4\2$$^^\u00f7\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2"+
		"\2\2\2\16\3\2\2\2\2\22\3\2\2\2\2\26\3\2\2\2\2\30\3\2\2\2\2\32\3\2\2\2"+
		"\2\34\3\2\2\2\2\36\3\2\2\2\2 \3\2\2\2\2\"\3\2\2\2\2$\3\2\2\2\2&\3\2\2"+
		"\2\2(\3\2\2\2\3*\3\2\2\2\3,\3\2\2\2\3.\3\2\2\2\3\60\3\2\2\2\3\62\3\2\2"+
		"\2\3\64\3\2\2\2\3\66\3\2\2\2\38\3\2\2\2\3:\3\2\2\2\3<\3\2\2\2\3>\3\2\2"+
		"\2\3@\3\2\2\2\3B\3\2\2\2\3D\3\2\2\2\3F\3\2\2\2\3H\3\2\2\2\3J\3\2\2\2\4"+
		"M\3\2\2\2\6R\3\2\2\2\bX\3\2\2\2\na\3\2\2\2\fi\3\2\2\2\16q\3\2\2\2\20}"+
		"\3\2\2\2\22\177\3\2\2\2\24\u008b\3\2\2\2\26\u008d\3\2\2\2\30\u0090\3\2"+
		"\2\2\32\u0092\3\2\2\2\34\u0094\3\2\2\2\36\u0096\3\2\2\2 \u0098\3\2\2\2"+
		"\"\u009a\3\2\2\2$\u009c\3\2\2\2&\u00a0\3\2\2\2(\u00a2\3\2\2\2*\u00a4\3"+
		"\2\2\2,\u00a9\3\2\2\2.\u00af\3\2\2\2\60\u00b5\3\2\2\2\62\u00ba\3\2\2\2"+
		"\64\u00bf\3\2\2\2\66\u00c4\3\2\2\28\u00c8\3\2\2\2:\u00cc\3\2\2\2<\u00d0"+
		"\3\2\2\2>\u00d4\3\2\2\2@\u00d8\3\2\2\2B\u00dc\3\2\2\2D\u00e0\3\2\2\2F"+
		"\u00e4\3\2\2\2H\u00e8\3\2\2\2J\u00ec\3\2\2\2LN\t\2\2\2ML\3\2\2\2NO\3\2"+
		"\2\2OM\3\2\2\2OP\3\2\2\2P\5\3\2\2\2QS\t\3\2\2RQ\3\2\2\2ST\3\2\2\2TR\3"+
		"\2\2\2TU\3\2\2\2UV\3\2\2\2VW\b\3\2\2W\7\3\2\2\2X\\\7%\2\2Y[\n\2\2\2ZY"+
		"\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_`\b\4\2"+
		"\2`\t\3\2\2\2ac\7^\2\2bd\t\2\2\2cb\3\2\2\2de\3\2\2\2ec\3\2\2\2ef\3\2\2"+
		"\2fg\3\2\2\2gh\b\5\3\2h\13\3\2\2\2im\t\4\2\2jl\t\5\2\2kj\3\2\2\2lo\3\2"+
		"\2\2mk\3\2\2\2mn\3\2\2\2n\r\3\2\2\2om\3\2\2\2pr\5\20\b\2qp\3\2\2\2rs\3"+
		"\2\2\2sq\3\2\2\2st\3\2\2\2t{\3\2\2\2uw\t\6\2\2vx\5\20\b\2wv\3\2\2\2xy"+
		"\3\2\2\2yw\3\2\2\2yz\3\2\2\2z|\3\2\2\2{u\3\2\2\2{|\3\2\2\2|\17\3\2\2\2"+
		"}~\t\7\2\2~\21\3\2\2\2\177\u0083\7$\2\2\u0080\u0082\5\24\n\2\u0081\u0080"+
		"\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"\u0086\3\2\2\2\u0085\u0083\3\2\2\2\u0086\u0087\7$\2\2\u0087\23\3\2\2\2"+
		"\u0088\u008c\n\b\2\2\u0089\u008a\7^\2\2\u008a\u008c\t\t\2\2\u008b\u0088"+
		"\3\2\2\2\u008b\u0089\3\2\2\2\u008c\25\3\2\2\2\u008d\u008e\7<\2\2\u008e"+
		"\u008f\7?\2\2\u008f\27\3\2\2\2\u0090\u0091\7-\2\2\u0091\31\3\2\2\2\u0092"+
		"\u0093\7/\2\2\u0093\33\3\2\2\2\u0094\u0095\7,\2\2\u0095\35\3\2\2\2\u0096"+
		"\u0097\7\61\2\2\u0097\37\3\2\2\2\u0098\u0099\7`\2\2\u0099!\3\2\2\2\u009a"+
		"\u009b\7.\2\2\u009b#\3\2\2\2\u009c\u009d\7*\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u009f\b\22\4\2\u009f%\3\2\2\2\u00a0\u00a1\7+\2\2\u00a1\'\3\2\2\2\u00a2"+
		"\u00a3\13\2\2\2\u00a3)\3\2\2\2\u00a4\u00a5\5$\22\2\u00a5\u00a6\3\2\2\2"+
		"\u00a6\u00a7\b\25\5\2\u00a7\u00a8\b\25\4\2\u00a8+\3\2\2\2\u00a9\u00aa"+
		"\5&\23\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\b\26\6\2\u00ac\u00ad\b\26\7\2"+
		"\u00ad-\3\2\2\2\u00ae\u00b0\t\2\2\2\u00af\u00ae\3\2\2\2\u00b0\u00b1\3"+
		"\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3"+
		"\u00b4\b\27\2\2\u00b4/\3\2\2\2\u00b5\u00b6\5\6\3\2\u00b6\u00b7\3\2\2\2"+
		"\u00b7\u00b8\b\30\b\2\u00b8\u00b9\b\30\2\2\u00b9\61\3\2\2\2\u00ba\u00bb"+
		"\5\b\4\2\u00bb\u00bc\3\2\2\2\u00bc\u00bd\b\31\t\2\u00bd\u00be\b\31\2\2"+
		"\u00be\63\3\2\2\2\u00bf\u00c0\5\n\5\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2"+
		"\b\32\n\2\u00c2\u00c3\b\32\3\2\u00c3\65\3\2\2\2\u00c4\u00c5\5\f\6\2\u00c5"+
		"\u00c6\3\2\2\2\u00c6\u00c7\b\33\13\2\u00c7\67\3\2\2\2\u00c8\u00c9\5\16"+
		"\7\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\b\34\f\2\u00cb9\3\2\2\2\u00cc\u00cd"+
		"\5\22\t\2\u00cd\u00ce\3\2\2\2\u00ce\u00cf\b\35\r\2\u00cf;\3\2\2\2\u00d0"+
		"\u00d1\5\26\13\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\b\36\16\2\u00d3=\3\2"+
		"\2\2\u00d4\u00d5\5\30\f\2\u00d5\u00d6\3\2\2\2\u00d6\u00d7\b\37\17\2\u00d7"+
		"?\3\2\2\2\u00d8\u00d9\5\32\r\2\u00d9\u00da\3\2\2\2\u00da\u00db\b \20\2"+
		"\u00dbA\3\2\2\2\u00dc\u00dd\5\34\16\2\u00dd\u00de\3\2\2\2\u00de\u00df"+
		"\b!\21\2\u00dfC\3\2\2\2\u00e0\u00e1\5\36\17\2\u00e1\u00e2\3\2\2\2\u00e2"+
		"\u00e3\b\"\22\2\u00e3E\3\2\2\2\u00e4\u00e5\5 \20\2\u00e5\u00e6\3\2\2\2"+
		"\u00e6\u00e7\b#\23\2\u00e7G\3\2\2\2\u00e8\u00e9\5\"\21\2\u00e9\u00ea\3"+
		"\2\2\2\u00ea\u00eb\b$\24\2\u00ebI\3\2\2\2\u00ec\u00ed\5(\24\2\u00ed\u00ee"+
		"\3\2\2\2\u00ee\u00ef\b%\25\2\u00efK\3\2\2\2\17\2\3OT\\emsy{\u0083\u008b"+
		"\u00b1\26\b\2\2\2\4\2\7\3\2\t\21\2\t\22\2\6\2\2\t\4\2\t\5\2\t\6\2\t\7"+
		"\2\t\b\2\t\t\2\t\n\2\t\13\2\t\f\2\t\r\2\t\16\2\t\17\2\t\20\2\t\23\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}