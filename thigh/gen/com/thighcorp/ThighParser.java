// Generated from C:/Users/gak/Documents/Studies/magisterskie/zajecia/sem1/kompilatory/p2/compiler/thigh/src/com/thighcorp\Thigh.g4 by ANTLR 4.9.1
package com.thighcorp;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ThighParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRINT=1, READ=2, ASSIGN=3, FUNCTION_DEFINITION=4, ID=5, STRING=6, INT=7, 
		REAL=8, ADDITION=9, SUBSTITUTION=10, MULTIPLICATION=11, DIVISION=12, POWER=13, 
		MODULO=14, COMMA=15, SEMICOLON=16, BRACKET_OPEN=17, BRACKET_CLOSE=18, 
		BRACE_OPEN=19, BRACE_CLOSE=20, WHITESPACE=21;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_print_statement = 2, RULE_read_statement = 3, 
		RULE_assign_statement = 4, RULE_assign_value = 5, RULE_function_definition = 6, 
		RULE_function_body = 7, RULE_expression = 8, RULE_arithmetic_operation = 9, 
		RULE_arithmetic_operator = 10, RULE_value = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "statement", "print_statement", "read_statement", "assign_statement", 
			"assign_value", "function_definition", "function_body", "expression", 
			"arithmetic_operation", "arithmetic_operator", "value"
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

	@Override
	public String getGrammarFileName() { return "Thigh.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ThighParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ThighParser.EOF, 0); }
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
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitProgram(this);
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
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PRINT) | (1L << READ) | (1L << FUNCTION_DEFINITION) | (1L << ID) | (1L << STRING) | (1L << INT) | (1L << REAL) | (1L << BRACKET_OPEN))) != 0)) {
				{
				{
				setState(24);
				statement();
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Function_definitionContext function_definition() {
			return getRuleContext(Function_definitionContext.class,0);
		}
		public Print_statementContext print_statement() {
			return getRuleContext(Print_statementContext.class,0);
		}
		public Read_statementContext read_statement() {
			return getRuleContext(Read_statementContext.class,0);
		}
		public Assign_statementContext assign_statement() {
			return getRuleContext(Assign_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(37);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(32);
				expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				function_definition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(34);
				print_statement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(35);
				read_statement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(36);
				assign_statement();
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

	public static class Print_statementContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(ThighParser.PRINT, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ThighParser.SEMICOLON, 0); }
		public Print_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterPrint_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitPrint_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitPrint_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Print_statementContext print_statement() throws RecognitionException {
		Print_statementContext _localctx = new Print_statementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_print_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(PRINT);
			setState(40);
			value();
			setState(41);
			match(SEMICOLON);
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

	public static class Read_statementContext extends ParserRuleContext {
		public TerminalNode READ() { return getToken(ThighParser.READ, 0); }
		public TerminalNode ID() { return getToken(ThighParser.ID, 0); }
		public TerminalNode SEMICOLON() { return getToken(ThighParser.SEMICOLON, 0); }
		public Read_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterRead_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitRead_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitRead_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Read_statementContext read_statement() throws RecognitionException {
		Read_statementContext _localctx = new Read_statementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_read_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(READ);
			setState(44);
			match(ID);
			setState(45);
			match(SEMICOLON);
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

	public static class Assign_statementContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ThighParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(ThighParser.ASSIGN, 0); }
		public Assign_valueContext assign_value() {
			return getRuleContext(Assign_valueContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ThighParser.SEMICOLON, 0); }
		public Assign_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterAssign_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitAssign_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitAssign_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_statementContext assign_statement() throws RecognitionException {
		Assign_statementContext _localctx = new Assign_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assign_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(ID);
			setState(48);
			match(ASSIGN);
			setState(49);
			assign_value();
			setState(50);
			match(SEMICOLON);
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

	public static class Assign_valueContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public Arithmetic_operationContext arithmetic_operation() {
			return getRuleContext(Arithmetic_operationContext.class,0);
		}
		public Assign_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterAssign_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitAssign_value(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitAssign_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assign_valueContext assign_value() throws RecognitionException {
		Assign_valueContext _localctx = new Assign_valueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assign_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(52);
				value();
				}
				break;
			case 2:
				{
				setState(53);
				arithmetic_operation();
				}
				break;
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

	public static class Function_definitionContext extends ParserRuleContext {
		public TerminalNode FUNCTION_DEFINITION() { return getToken(ThighParser.FUNCTION_DEFINITION, 0); }
		public List<TerminalNode> ID() { return getTokens(ThighParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ThighParser.ID, i);
		}
		public TerminalNode BRACKET_OPEN() { return getToken(ThighParser.BRACKET_OPEN, 0); }
		public TerminalNode BRACKET_CLOSE() { return getToken(ThighParser.BRACKET_CLOSE, 0); }
		public Function_bodyContext function_body() {
			return getRuleContext(Function_bodyContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(ThighParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(ThighParser.COMMA, i);
		}
		public Function_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterFunction_definition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitFunction_definition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitFunction_definition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_definitionContext function_definition() throws RecognitionException {
		Function_definitionContext _localctx = new Function_definitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_function_definition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(FUNCTION_DEFINITION);
			setState(57);
			match(ID);
			setState(58);
			match(BRACKET_OPEN);
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(59);
				match(ID);
				}
			}

			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(62);
				match(COMMA);
				setState(63);
				match(ID);
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			match(BRACKET_CLOSE);
			setState(70);
			function_body();
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

	public static class Function_bodyContext extends ParserRuleContext {
		public TerminalNode BRACE_OPEN() { return getToken(ThighParser.BRACE_OPEN, 0); }
		public TerminalNode BRACE_CLOSE() { return getToken(ThighParser.BRACE_CLOSE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Function_bodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_body; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterFunction_body(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitFunction_body(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitFunction_body(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Function_bodyContext function_body() throws RecognitionException {
		Function_bodyContext _localctx = new Function_bodyContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_function_body);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(BRACE_OPEN);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << STRING) | (1L << INT) | (1L << REAL) | (1L << BRACKET_OPEN))) != 0)) {
				{
				{
				setState(73);
				expression();
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(79);
			match(BRACE_CLOSE);
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

	public static class ExpressionContext extends ParserRuleContext {
		public Arithmetic_operationContext arithmetic_operation() {
			return getRuleContext(Arithmetic_operationContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ThighParser.SEMICOLON, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			arithmetic_operation();
			setState(82);
			match(SEMICOLON);
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

	public static class Arithmetic_operationContext extends ParserRuleContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public Arithmetic_operatorContext arithmetic_operator() {
			return getRuleContext(Arithmetic_operatorContext.class,0);
		}
		public Arithmetic_operationContext arithmetic_operation() {
			return getRuleContext(Arithmetic_operationContext.class,0);
		}
		public TerminalNode BRACKET_OPEN() { return getToken(ThighParser.BRACKET_OPEN, 0); }
		public TerminalNode BRACKET_CLOSE() { return getToken(ThighParser.BRACKET_CLOSE, 0); }
		public Arithmetic_operationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterArithmetic_operation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitArithmetic_operation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitArithmetic_operation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Arithmetic_operationContext arithmetic_operation() throws RecognitionException {
		Arithmetic_operationContext _localctx = new Arithmetic_operationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_arithmetic_operation);
		try {
			setState(93);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				value();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				value();
				setState(86);
				arithmetic_operator();
				setState(87);
				arithmetic_operation();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(89);
				match(BRACKET_OPEN);
				setState(90);
				arithmetic_operation();
				setState(91);
				match(BRACKET_CLOSE);
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

	public static class Arithmetic_operatorContext extends ParserRuleContext {
		public TerminalNode ADDITION() { return getToken(ThighParser.ADDITION, 0); }
		public TerminalNode SUBSTITUTION() { return getToken(ThighParser.SUBSTITUTION, 0); }
		public TerminalNode MULTIPLICATION() { return getToken(ThighParser.MULTIPLICATION, 0); }
		public TerminalNode DIVISION() { return getToken(ThighParser.DIVISION, 0); }
		public TerminalNode MODULO() { return getToken(ThighParser.MODULO, 0); }
		public TerminalNode POWER() { return getToken(ThighParser.POWER, 0); }
		public Arithmetic_operatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic_operator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterArithmetic_operator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitArithmetic_operator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitArithmetic_operator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Arithmetic_operatorContext arithmetic_operator() throws RecognitionException {
		Arithmetic_operatorContext _localctx = new Arithmetic_operatorContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_arithmetic_operator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADDITION) | (1L << SUBSTITUTION) | (1L << MULTIPLICATION) | (1L << DIVISION) | (1L << POWER) | (1L << MODULO))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(ThighParser.ID, 0); }
		public TerminalNode STRING() { return getToken(ThighParser.STRING, 0); }
		public TerminalNode INT() { return getToken(ThighParser.INT, 0); }
		public TerminalNode REAL() { return getToken(ThighParser.REAL, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ThighListener ) ((ThighListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ThighVisitor ) return ((ThighVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ID) | (1L << STRING) | (1L << INT) | (1L << REAL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\27f\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\3\2\7\2\34\n\2\f\2\16\2\37\13\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\5\3(\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\5\79\n\7\3\b\3\b\3\b\3\b\5\b?\n\b\3\b\3\b\7\bC\n\b\f\b\16\bF\13\b"+
		"\3\b\3\b\3\b\3\t\3\t\7\tM\n\t\f\t\16\tP\13\t\3\t\3\t\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13`\n\13\3\f\3\f\3\r\3\r\3"+
		"\r\2\2\16\2\4\6\b\n\f\16\20\22\24\26\30\2\4\3\2\13\20\3\2\7\n\2d\2\35"+
		"\3\2\2\2\4\'\3\2\2\2\6)\3\2\2\2\b-\3\2\2\2\n\61\3\2\2\2\f8\3\2\2\2\16"+
		":\3\2\2\2\20J\3\2\2\2\22S\3\2\2\2\24_\3\2\2\2\26a\3\2\2\2\30c\3\2\2\2"+
		"\32\34\5\4\3\2\33\32\3\2\2\2\34\37\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2"+
		"\36 \3\2\2\2\37\35\3\2\2\2 !\7\2\2\3!\3\3\2\2\2\"(\5\22\n\2#(\5\16\b\2"+
		"$(\5\6\4\2%(\5\b\5\2&(\5\n\6\2\'\"\3\2\2\2\'#\3\2\2\2\'$\3\2\2\2\'%\3"+
		"\2\2\2\'&\3\2\2\2(\5\3\2\2\2)*\7\3\2\2*+\5\30\r\2+,\7\22\2\2,\7\3\2\2"+
		"\2-.\7\4\2\2./\7\7\2\2/\60\7\22\2\2\60\t\3\2\2\2\61\62\7\7\2\2\62\63\7"+
		"\5\2\2\63\64\5\f\7\2\64\65\7\22\2\2\65\13\3\2\2\2\669\5\30\r\2\679\5\24"+
		"\13\28\66\3\2\2\28\67\3\2\2\29\r\3\2\2\2:;\7\6\2\2;<\7\7\2\2<>\7\23\2"+
		"\2=?\7\7\2\2>=\3\2\2\2>?\3\2\2\2?D\3\2\2\2@A\7\21\2\2AC\7\7\2\2B@\3\2"+
		"\2\2CF\3\2\2\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\24\2\2HI\5"+
		"\20\t\2I\17\3\2\2\2JN\7\25\2\2KM\5\22\n\2LK\3\2\2\2MP\3\2\2\2NL\3\2\2"+
		"\2NO\3\2\2\2OQ\3\2\2\2PN\3\2\2\2QR\7\26\2\2R\21\3\2\2\2ST\5\24\13\2TU"+
		"\7\22\2\2U\23\3\2\2\2V`\5\30\r\2WX\5\30\r\2XY\5\26\f\2YZ\5\24\13\2Z`\3"+
		"\2\2\2[\\\7\23\2\2\\]\5\24\13\2]^\7\24\2\2^`\3\2\2\2_V\3\2\2\2_W\3\2\2"+
		"\2_[\3\2\2\2`\25\3\2\2\2ab\t\2\2\2b\27\3\2\2\2cd\t\3\2\2d\31\3\2\2\2\t"+
		"\35\'8>DN_";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}