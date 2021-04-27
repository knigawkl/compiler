// Generated from C:/Users/lk/OneDrive - Politechnika Warszawska/JFIK/projekt/compiler/thigh/src/com/thighcorp\Thigh.g4 by ANTLR 4.9.1
package com.thighcorp;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ThighLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRINT=1, READ=2, ASSIGN=3, FUNCTION_DEFINITION=4, ID=5, STRING=6, INT=7, 
		REAL=8, ADDITION=9, SUBSTITUTION=10, MULTIPLICATION=11, DIVISION=12, POWER=13, 
		MODULO=14, COMMA=15, SEMICOLON=16, BRACKET_OPEN=17, BRACKET_CLOSE=18, 
		BRACE_OPEN=19, BRACE_CLOSE=20, WHITESPACE=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PRINT", "READ", "ASSIGN", "FUNCTION_DEFINITION", "ID", "STRING", "INT", 
			"REAL", "ADDITION", "SUBSTITUTION", "MULTIPLICATION", "DIVISION", "POWER", 
			"MODULO", "COMMA", "SEMICOLON", "BRACKET_OPEN", "BRACKET_CLOSE", "BRACE_OPEN", 
			"BRACE_CLOSE", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'print'", "'input'", "'='", "'def'", null, null, null, null, "'+'", 
			"'-'", "'*'", "'/'", "'^'", "'%'", "','", "';'", "'('", "')'", "'{'", 
			"'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PRINT", "READ", "ASSIGN", "FUNCTION_DEFINITION", "ID", "STRING", 
			"INT", "REAL", "ADDITION", "SUBSTITUTION", "MULTIPLICATION", "DIVISION", 
			"POWER", "MODULO", "COMMA", "SEMICOLON", "BRACKET_OPEN", "BRACKET_CLOSE", 
			"BRACE_OPEN", "BRACE_CLOSE", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public ThighLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Thigh.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\27\u0084\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\6\6\6A\n\6\r\6\16\6"+
		"B\3\7\3\7\7\7G\n\7\f\7\16\7J\13\7\3\7\3\7\3\b\5\bO\n\b\3\b\6\bR\n\b\r"+
		"\b\16\bS\3\t\5\tW\n\t\3\t\6\tZ\n\t\r\t\16\t[\3\t\3\t\6\t`\n\t\r\t\16\t"+
		"a\5\td\n\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\6\26\177"+
		"\n\26\r\26\16\26\u0080\3\26\3\26\2\2\27\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27"+
		"\3\2\5\4\2C\\c|\4\2$$^^\5\2\13\f\17\17\"\"\2\u008c\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5\63\3\2\2\2\79\3\2\2\2\t;\3\2"+
		"\2\2\13@\3\2\2\2\rD\3\2\2\2\17N\3\2\2\2\21V\3\2\2\2\23e\3\2\2\2\25g\3"+
		"\2\2\2\27i\3\2\2\2\31k\3\2\2\2\33m\3\2\2\2\35o\3\2\2\2\37q\3\2\2\2!s\3"+
		"\2\2\2#u\3\2\2\2%w\3\2\2\2\'y\3\2\2\2){\3\2\2\2+~\3\2\2\2-.\7r\2\2./\7"+
		"t\2\2/\60\7k\2\2\60\61\7p\2\2\61\62\7v\2\2\62\4\3\2\2\2\63\64\7k\2\2\64"+
		"\65\7p\2\2\65\66\7r\2\2\66\67\7w\2\2\678\7v\2\28\6\3\2\2\29:\7?\2\2:\b"+
		"\3\2\2\2;<\7f\2\2<=\7g\2\2=>\7h\2\2>\n\3\2\2\2?A\t\2\2\2@?\3\2\2\2AB\3"+
		"\2\2\2B@\3\2\2\2BC\3\2\2\2C\f\3\2\2\2DH\7$\2\2EG\n\3\2\2FE\3\2\2\2GJ\3"+
		"\2\2\2HF\3\2\2\2HI\3\2\2\2IK\3\2\2\2JH\3\2\2\2KL\7$\2\2L\16\3\2\2\2MO"+
		"\7/\2\2NM\3\2\2\2NO\3\2\2\2OQ\3\2\2\2PR\4\62;\2QP\3\2\2\2RS\3\2\2\2SQ"+
		"\3\2\2\2ST\3\2\2\2T\20\3\2\2\2UW\7/\2\2VU\3\2\2\2VW\3\2\2\2WY\3\2\2\2"+
		"XZ\4\62;\2YX\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\c\3\2\2\2]_\7\60"+
		"\2\2^`\4\62;\2_^\3\2\2\2`a\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2\2c]\3\2"+
		"\2\2cd\3\2\2\2d\22\3\2\2\2ef\7-\2\2f\24\3\2\2\2gh\7/\2\2h\26\3\2\2\2i"+
		"j\7,\2\2j\30\3\2\2\2kl\7\61\2\2l\32\3\2\2\2mn\7`\2\2n\34\3\2\2\2op\7\'"+
		"\2\2p\36\3\2\2\2qr\7.\2\2r \3\2\2\2st\7=\2\2t\"\3\2\2\2uv\7*\2\2v$\3\2"+
		"\2\2wx\7+\2\2x&\3\2\2\2yz\7}\2\2z(\3\2\2\2{|\7\177\2\2|*\3\2\2\2}\177"+
		"\t\4\2\2~}\3\2\2\2\177\u0080\3\2\2\2\u0080~\3\2\2\2\u0080\u0081\3\2\2"+
		"\2\u0081\u0082\3\2\2\2\u0082\u0083\b\26\2\2\u0083,\3\2\2\2\f\2BHNSV[a"+
		"c\u0080\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}