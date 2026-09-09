// Generated from MicroJ.g4 by ANTLR 4.13.1
package org.tihrc.microj.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MicroJParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CLASS=1, FOR=2, IN=3, NOT_IN=4, IF=5, ELIF=6, ELSE=7, WHILE=8, DEF=9, 
		RETURN=10, IMPORT=11, FROM=12, AS=13, BREAK=14, CONTINUE=15, TRY=16, EXCEPT=17, 
		FINALLY=18, AT=19, AND=20, OR=21, NOT=22, DOT=23, COLON=24, LBRACKET=25, 
		RBRACKET=26, LBRACE=27, RBRACE=28, PLUS=29, MINUS=30, STAR=31, SLASH=32, 
		POW=33, EQUAL=34, EQ_EQ=35, NE=36, LT=37, GT=38, LE=39, GE=40, LPAREN=41, 
		RPAREN=42, COMMA=43, NAME=44, NUMBER=45, FLOAT=46, IMAG=47, STRING=48, 
		NEWLINE=49, WS=50, COMMENT=51, INDENT=52, DEDENT=53;
	public static final int
		RULE_file = 0, RULE_statement = 1, RULE_simpleStatement = 2, RULE_funcDef = 3, 
		RULE_classDef = 4, RULE_decorator = 5, RULE_paramList = 6, RULE_returnStatement = 7, 
		RULE_breakStatement = 8, RULE_continueStatement = 9, RULE_tryStatement = 10, 
		RULE_exceptClause = 11, RULE_ifStatement = 12, RULE_whileStatement = 13, 
		RULE_forStatement = 14, RULE_block = 15, RULE_assignment = 16, RULE_expr = 17, 
		RULE_or_expr = 18, RULE_and_expr = 19, RULE_not_expr = 20, RULE_comparison = 21, 
		RULE_add_expr = 22, RULE_mul_expr = 23, RULE_unary_expr = 24, RULE_power = 25, 
		RULE_compareOp = 26, RULE_addOp = 27, RULE_mulOp = 28, RULE_target = 29, 
		RULE_targetList = 30, RULE_atom = 31, RULE_exprList = 32, RULE_dictList = 33, 
		RULE_dictItem = 34, RULE_argList = 35, RULE_arg = 36, RULE_importStatement = 37, 
		RULE_importNames = 38;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "statement", "simpleStatement", "funcDef", "classDef", "decorator", 
			"paramList", "returnStatement", "breakStatement", "continueStatement", 
			"tryStatement", "exceptClause", "ifStatement", "whileStatement", "forStatement", 
			"block", "assignment", "expr", "or_expr", "and_expr", "not_expr", "comparison", 
			"add_expr", "mul_expr", "unary_expr", "power", "compareOp", "addOp", 
			"mulOp", "target", "targetList", "atom", "exprList", "dictList", "dictItem", 
			"argList", "arg", "importStatement", "importNames"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'for'", "'in'", "'not in'", "'if'", "'elif'", "'else'", 
			"'while'", "'def'", "'return'", "'import'", "'from'", "'as'", "'break'", 
			"'continue'", "'try'", "'except'", "'finally'", "'@'", "'and'", "'or'", 
			"'not'", "'.'", "':'", "'['", "']'", "'{'", "'}'", "'+'", "'-'", "'*'", 
			"'/'", "'**'", "'='", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", "'('", 
			"')'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "CLASS", "FOR", "IN", "NOT_IN", "IF", "ELIF", "ELSE", "WHILE", 
			"DEF", "RETURN", "IMPORT", "FROM", "AS", "BREAK", "CONTINUE", "TRY", 
			"EXCEPT", "FINALLY", "AT", "AND", "OR", "NOT", "DOT", "COLON", "LBRACKET", 
			"RBRACKET", "LBRACE", "RBRACE", "PLUS", "MINUS", "STAR", "SLASH", "POW", 
			"EQUAL", "EQ_EQ", "NE", "LT", "GT", "LE", "GE", "LPAREN", "RPAREN", "COMMA", 
			"NAME", "NUMBER", "FLOAT", "IMAG", "STRING", "NEWLINE", "WS", "COMMENT", 
			"INDENT", "DEDENT"
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
	public String getGrammarFileName() { return "MicroJ.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MicroJParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MicroJParser.EOF, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(MicroJParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(MicroJParser.NEWLINE, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1110508527279910L) != 0)) {
				{
				setState(80);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEWLINE:
					{
					setState(78);
					match(NEWLINE);
					}
					break;
				case CLASS:
				case FOR:
				case IF:
				case WHILE:
				case DEF:
				case RETURN:
				case IMPORT:
				case FROM:
				case BREAK:
				case CONTINUE:
				case TRY:
				case AT:
				case NOT:
				case LBRACKET:
				case LBRACE:
				case PLUS:
				case MINUS:
				case LPAREN:
				case NAME:
				case NUMBER:
				case FLOAT:
				case IMAG:
				case STRING:
					{
					setState(79);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(85);
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

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public SimpleStatementContext simpleStatement() {
			return getRuleContext(SimpleStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public FuncDefContext funcDef() {
			return getRuleContext(FuncDefContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public ImportStatementContext importStatement() {
			return getRuleContext(ImportStatementContext.class,0);
		}
		public TryStatementContext tryStatement() {
			return getRuleContext(TryStatementContext.class,0);
		}
		public ClassDefContext classDef() {
			return getRuleContext(ClassDefContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RETURN:
			case BREAK:
			case CONTINUE:
			case NOT:
			case LBRACKET:
			case LBRACE:
			case PLUS:
			case MINUS:
			case LPAREN:
			case NAME:
			case NUMBER:
			case FLOAT:
			case IMAG:
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				simpleStatement();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(88);
				ifStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(89);
				whileStatement();
				}
				break;
			case DEF:
			case AT:
				enterOuterAlt(_localctx, 4);
				{
				setState(90);
				funcDef();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(91);
				forStatement();
				}
				break;
			case IMPORT:
			case FROM:
				enterOuterAlt(_localctx, 6);
				{
				setState(92);
				importStatement();
				}
				break;
			case TRY:
				enterOuterAlt(_localctx, 7);
				{
				setState(93);
				tryStatement();
				}
				break;
			case CLASS:
				enterOuterAlt(_localctx, 8);
				{
				setState(94);
				classDef();
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

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleStatementContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public SimpleStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterSimpleStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitSimpleStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitSimpleStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleStatementContext simpleStatement() throws RecognitionException {
		SimpleStatementContext _localctx = new SimpleStatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_simpleStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(97);
				assignment();
				}
				break;
			case 2:
				{
				setState(98);
				expr();
				}
				break;
			case 3:
				{
				setState(99);
				returnStatement();
				}
				break;
			case 4:
				{
				setState(100);
				breakStatement();
				}
				break;
			case 5:
				{
				setState(101);
				continueStatement();
				}
				break;
			}
			setState(104);
			match(NEWLINE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FuncDefContext extends ParserRuleContext {
		public TerminalNode DEF() { return getToken(MicroJParser.DEF, 0); }
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public TerminalNode COLON() { return getToken(MicroJParser.COLON, 0); }
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public List<DecoratorContext> decorator() {
			return getRuleContexts(DecoratorContext.class);
		}
		public DecoratorContext decorator(int i) {
			return getRuleContext(DecoratorContext.class,i);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public FuncDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterFuncDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitFuncDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitFuncDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncDefContext funcDef() throws RecognitionException {
		FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_funcDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(106);
				decorator();
				}
				}
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(112);
			match(DEF);
			setState(113);
			match(NAME);
			setState(114);
			match(LPAREN);
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NAME) {
				{
				setState(115);
				paramList();
				}
			}

			setState(118);
			match(RPAREN);
			setState(119);
			match(COLON);
			setState(120);
			match(NEWLINE);
			setState(121);
			block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDefContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(MicroJParser.CLASS, 0); }
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public TerminalNode COLON() { return getToken(MicroJParser.COLON, 0); }
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterClassDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitClassDef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitClassDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(CLASS);
			setState(124);
			match(NAME);
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(125);
				match(LPAREN);
				setState(126);
				match(NAME);
				setState(127);
				match(RPAREN);
				}
			}

			setState(130);
			match(COLON);
			setState(131);
			match(NEWLINE);
			setState(132);
			block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class DecoratorContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(MicroJParser.AT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public DecoratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decorator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterDecorator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitDecorator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitDecorator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecoratorContext decorator() throws RecognitionException {
		DecoratorContext _localctx = new DecoratorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_decorator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(AT);
			setState(135);
			expr();
			setState(136);
			match(NEWLINE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ParamListContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitParamList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(NAME);
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(139);
				match(COMMA);
				setState(140);
				match(NAME);
				}
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(MicroJParser.RETURN, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(RETURN);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 547558573211648L) != 0)) {
				{
				setState(147);
				exprList();
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

	@SuppressWarnings("CheckReturnValue")
	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(MicroJParser.BREAK, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			match(BREAK);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(MicroJParser.CONTINUE, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(CONTINUE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class TryStatementContext extends ParserRuleContext {
		public TerminalNode TRY() { return getToken(MicroJParser.TRY, 0); }
		public List<TerminalNode> COLON() { return getTokens(MicroJParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MicroJParser.COLON, i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(MicroJParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(MicroJParser.NEWLINE, i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<TerminalNode> EXCEPT() { return getTokens(MicroJParser.EXCEPT); }
		public TerminalNode EXCEPT(int i) {
			return getToken(MicroJParser.EXCEPT, i);
		}
		public TerminalNode ELSE() { return getToken(MicroJParser.ELSE, 0); }
		public TerminalNode FINALLY() { return getToken(MicroJParser.FINALLY, 0); }
		public List<ExceptClauseContext> exceptClause() {
			return getRuleContexts(ExceptClauseContext.class);
		}
		public ExceptClauseContext exceptClause(int i) {
			return getRuleContext(ExceptClauseContext.class,i);
		}
		public TryStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterTryStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitTryStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitTryStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TryStatementContext tryStatement() throws RecognitionException {
		TryStatementContext _localctx = new TryStatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			match(TRY);
			setState(155);
			match(COLON);
			setState(156);
			match(NEWLINE);
			setState(157);
			block();
			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EXCEPT) {
				{
				{
				setState(158);
				match(EXCEPT);
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS || _la==NAME) {
					{
					setState(159);
					exceptClause();
					}
				}

				setState(162);
				match(COLON);
				setState(163);
				match(NEWLINE);
				setState(164);
				block();
				}
				}
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(170);
				match(ELSE);
				setState(171);
				match(COLON);
				setState(172);
				match(NEWLINE);
				setState(173);
				block();
				}
			}

			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINALLY) {
				{
				setState(176);
				match(FINALLY);
				setState(177);
				match(COLON);
				setState(178);
				match(NEWLINE);
				setState(179);
				block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExceptClauseContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public TerminalNode AS() { return getToken(MicroJParser.AS, 0); }
		public ExceptClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterExceptClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitExceptClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitExceptClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExceptClauseContext exceptClause() throws RecognitionException {
		ExceptClauseContext _localctx = new ExceptClauseContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_exceptClause);
		int _la;
		try {
			setState(189);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(182);
				match(NAME);
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(183);
					match(AS);
					setState(184);
					match(NAME);
					}
				}

				}
				break;
			case AS:
				enterOuterAlt(_localctx, 2);
				{
				setState(187);
				match(AS);
				setState(188);
				match(NAME);
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

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(MicroJParser.IF, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(MicroJParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MicroJParser.COLON, i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(MicroJParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(MicroJParser.NEWLINE, i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<TerminalNode> ELIF() { return getTokens(MicroJParser.ELIF); }
		public TerminalNode ELIF(int i) {
			return getToken(MicroJParser.ELIF, i);
		}
		public TerminalNode ELSE() { return getToken(MicroJParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(IF);
			setState(192);
			expr();
			setState(193);
			match(COLON);
			setState(194);
			match(NEWLINE);
			setState(195);
			block();
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELIF) {
				{
				{
				setState(196);
				match(ELIF);
				setState(197);
				expr();
				setState(198);
				match(COLON);
				setState(199);
				match(NEWLINE);
				setState(200);
				block();
				}
				}
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(207);
				match(ELSE);
				setState(208);
				match(COLON);
				setState(209);
				match(NEWLINE);
				setState(210);
				block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(MicroJParser.WHILE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<TerminalNode> COLON() { return getTokens(MicroJParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MicroJParser.COLON, i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(MicroJParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(MicroJParser.NEWLINE, i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(MicroJParser.ELSE, 0); }
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_whileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(WHILE);
			setState(214);
			expr();
			setState(215);
			match(COLON);
			setState(216);
			match(NEWLINE);
			setState(217);
			block();
			setState(222);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(218);
				match(ELSE);
				setState(219);
				match(COLON);
				setState(220);
				match(NEWLINE);
				setState(221);
				block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(MicroJParser.FOR, 0); }
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode IN() { return getToken(MicroJParser.IN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<TerminalNode> COLON() { return getTokens(MicroJParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MicroJParser.COLON, i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(MicroJParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(MicroJParser.NEWLINE, i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(MicroJParser.ELSE, 0); }
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			match(FOR);
			setState(225);
			targetList();
			setState(226);
			match(IN);
			setState(227);
			expr();
			setState(228);
			match(COLON);
			setState(229);
			match(NEWLINE);
			setState(230);
			block();
			setState(235);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(231);
				match(ELSE);
				setState(232);
				match(COLON);
				setState(233);
				match(NEWLINE);
				setState(234);
				block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode INDENT() { return getToken(MicroJParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(MicroJParser.DEDENT, 0); }
		public List<TerminalNode> NEWLINE() { return getTokens(MicroJParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(MicroJParser.NEWLINE, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(INDENT);
			setState(240); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(240);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEWLINE:
					{
					setState(238);
					match(NEWLINE);
					}
					break;
				case CLASS:
				case FOR:
				case IF:
				case WHILE:
				case DEF:
				case RETURN:
				case IMPORT:
				case FROM:
				case BREAK:
				case CONTINUE:
				case TRY:
				case AT:
				case NOT:
				case LBRACKET:
				case LBRACE:
				case PLUS:
				case MINUS:
				case LPAREN:
				case NAME:
				case NUMBER:
				case FLOAT:
				case IMAG:
				case STRING:
					{
					setState(239);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(242); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 1110508527279910L) != 0) );
			setState(244);
			match(DEDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
	 
		public AssignmentContext() { }
		public void copyFrom(AssignmentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubscriptAssignContext extends AssignmentContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(MicroJParser.LBRACKET, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RBRACKET() { return getToken(MicroJParser.RBRACKET, 0); }
		public TerminalNode EQUAL() { return getToken(MicroJParser.EQUAL, 0); }
		public SubscriptAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterSubscriptAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitSubscriptAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitSubscriptAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GeneralAssignContext extends AssignmentContext {
		public TargetContext target() {
			return getRuleContext(TargetContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(MicroJParser.EQUAL, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public GeneralAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterGeneralAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitGeneralAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitGeneralAssign(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AttrAssignContext extends AssignmentContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode DOT() { return getToken(MicroJParser.DOT, 0); }
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public TerminalNode EQUAL() { return getToken(MicroJParser.EQUAL, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AttrAssignContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterAttrAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitAttrAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitAttrAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_assignment);
		try {
			setState(263);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new SubscriptAssignContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(246);
				atom(0);
				setState(247);
				match(LBRACKET);
				setState(248);
				expr();
				setState(249);
				match(RBRACKET);
				setState(250);
				match(EQUAL);
				setState(251);
				expr();
				}
				break;
			case 2:
				_localctx = new AttrAssignContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(253);
				atom(0);
				setState(254);
				match(DOT);
				setState(255);
				match(NAME);
				setState(256);
				match(EQUAL);
				setState(257);
				expr();
				}
				break;
			case 3:
				_localctx = new GeneralAssignContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(259);
				target();
				setState(260);
				match(EQUAL);
				setState(261);
				exprList();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public Or_exprContext or_expr() {
			return getRuleContext(Or_exprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			or_expr();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Or_exprContext extends ParserRuleContext {
		public List<And_exprContext> and_expr() {
			return getRuleContexts(And_exprContext.class);
		}
		public And_exprContext and_expr(int i) {
			return getRuleContext(And_exprContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(MicroJParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(MicroJParser.OR, i);
		}
		public Or_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_or_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterOr_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitOr_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitOr_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Or_exprContext or_expr() throws RecognitionException {
		Or_exprContext _localctx = new Or_exprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_or_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			and_expr();
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(268);
				match(OR);
				setState(269);
				and_expr();
				}
				}
				setState(274);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class And_exprContext extends ParserRuleContext {
		public List<Not_exprContext> not_expr() {
			return getRuleContexts(Not_exprContext.class);
		}
		public Not_exprContext not_expr(int i) {
			return getRuleContext(Not_exprContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(MicroJParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(MicroJParser.AND, i);
		}
		public And_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterAnd_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitAnd_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitAnd_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final And_exprContext and_expr() throws RecognitionException {
		And_exprContext _localctx = new And_exprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_and_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			not_expr();
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(276);
				match(AND);
				setState(277);
				not_expr();
				}
				}
				setState(282);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Not_exprContext extends ParserRuleContext {
		public ComparisonContext comparison() {
			return getRuleContext(ComparisonContext.class,0);
		}
		public List<TerminalNode> NOT() { return getTokens(MicroJParser.NOT); }
		public TerminalNode NOT(int i) {
			return getToken(MicroJParser.NOT, i);
		}
		public Not_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_not_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterNot_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitNot_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitNot_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Not_exprContext not_expr() throws RecognitionException {
		Not_exprContext _localctx = new Not_exprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_not_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NOT) {
				{
				{
				setState(283);
				match(NOT);
				}
				}
				setState(288);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(289);
			comparison();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonContext extends ParserRuleContext {
		public List<Add_exprContext> add_expr() {
			return getRuleContexts(Add_exprContext.class);
		}
		public Add_exprContext add_expr(int i) {
			return getRuleContext(Add_exprContext.class,i);
		}
		public List<CompareOpContext> compareOp() {
			return getRuleContexts(CompareOpContext.class);
		}
		public CompareOpContext compareOp(int i) {
			return getRuleContext(CompareOpContext.class,i);
		}
		public ComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonContext comparison() throws RecognitionException {
		ComparisonContext _localctx = new ComparisonContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_comparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			add_expr();
			setState(297);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2164663517208L) != 0)) {
				{
				{
				setState(292);
				compareOp();
				setState(293);
				add_expr();
				}
				}
				setState(299);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Add_exprContext extends ParserRuleContext {
		public List<Mul_exprContext> mul_expr() {
			return getRuleContexts(Mul_exprContext.class);
		}
		public Mul_exprContext mul_expr(int i) {
			return getRuleContext(Mul_exprContext.class,i);
		}
		public List<AddOpContext> addOp() {
			return getRuleContexts(AddOpContext.class);
		}
		public AddOpContext addOp(int i) {
			return getRuleContext(AddOpContext.class,i);
		}
		public Add_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_add_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterAdd_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitAdd_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitAdd_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Add_exprContext add_expr() throws RecognitionException {
		Add_exprContext _localctx = new Add_exprContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_add_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			mul_expr();
			setState(306);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(301);
				addOp();
				setState(302);
				mul_expr();
				}
				}
				setState(308);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Mul_exprContext extends ParserRuleContext {
		public List<Unary_exprContext> unary_expr() {
			return getRuleContexts(Unary_exprContext.class);
		}
		public Unary_exprContext unary_expr(int i) {
			return getRuleContext(Unary_exprContext.class,i);
		}
		public List<MulOpContext> mulOp() {
			return getRuleContexts(MulOpContext.class);
		}
		public MulOpContext mulOp(int i) {
			return getRuleContext(MulOpContext.class,i);
		}
		public Mul_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mul_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterMul_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitMul_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitMul_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Mul_exprContext mul_expr() throws RecognitionException {
		Mul_exprContext _localctx = new Mul_exprContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_mul_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			unary_expr();
			setState(315);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==STAR || _la==SLASH) {
				{
				{
				setState(310);
				mulOp();
				setState(311);
				unary_expr();
				}
				}
				setState(317);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Unary_exprContext extends ParserRuleContext {
		public Unary_exprContext unary_expr() {
			return getRuleContext(Unary_exprContext.class,0);
		}
		public TerminalNode PLUS() { return getToken(MicroJParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MicroJParser.MINUS, 0); }
		public PowerContext power() {
			return getRuleContext(PowerContext.class,0);
		}
		public Unary_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterUnary_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitUnary_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitUnary_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Unary_exprContext unary_expr() throws RecognitionException {
		Unary_exprContext _localctx = new Unary_exprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_unary_expr);
		int _la;
		try {
			setState(321);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(318);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(319);
				unary_expr();
				}
				break;
			case LBRACKET:
			case LBRACE:
			case LPAREN:
			case NAME:
			case NUMBER:
			case FLOAT:
			case IMAG:
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(320);
				power();
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

	@SuppressWarnings("CheckReturnValue")
	public static class PowerContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode POW() { return getToken(MicroJParser.POW, 0); }
		public Unary_exprContext unary_expr() {
			return getRuleContext(Unary_exprContext.class,0);
		}
		public PowerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_power; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterPower(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitPower(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitPower(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PowerContext power() throws RecognitionException {
		PowerContext _localctx = new PowerContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_power);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(323);
			atom(0);
			setState(326);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==POW) {
				{
				setState(324);
				match(POW);
				setState(325);
				unary_expr();
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

	@SuppressWarnings("CheckReturnValue")
	public static class CompareOpContext extends ParserRuleContext {
		public TerminalNode EQ_EQ() { return getToken(MicroJParser.EQ_EQ, 0); }
		public TerminalNode NE() { return getToken(MicroJParser.NE, 0); }
		public TerminalNode LT() { return getToken(MicroJParser.LT, 0); }
		public TerminalNode GT() { return getToken(MicroJParser.GT, 0); }
		public TerminalNode LE() { return getToken(MicroJParser.LE, 0); }
		public TerminalNode GE() { return getToken(MicroJParser.GE, 0); }
		public TerminalNode IN() { return getToken(MicroJParser.IN, 0); }
		public TerminalNode NOT_IN() { return getToken(MicroJParser.NOT_IN, 0); }
		public CompareOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compareOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterCompareOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitCompareOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitCompareOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompareOpContext compareOp() throws RecognitionException {
		CompareOpContext _localctx = new CompareOpContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_compareOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(328);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2164663517208L) != 0)) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class AddOpContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(MicroJParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MicroJParser.MINUS, 0); }
		public AddOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterAddOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitAddOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitAddOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddOpContext addOp() throws RecognitionException {
		AddOpContext _localctx = new AddOpContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_addOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class MulOpContext extends ParserRuleContext {
		public TerminalNode STAR() { return getToken(MicroJParser.STAR, 0); }
		public TerminalNode SLASH() { return getToken(MicroJParser.SLASH, 0); }
		public MulOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mulOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterMulOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitMulOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitMulOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MulOpContext mulOp() throws RecognitionException {
		MulOpContext _localctx = new MulOpContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_mulOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			_la = _input.LA(1);
			if ( !(_la==STAR || _la==SLASH) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class TargetContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(MicroJParser.COMMA, 0); }
		public TerminalNode LBRACKET() { return getToken(MicroJParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(MicroJParser.RBRACKET, 0); }
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DOT() { return getToken(MicroJParser.DOT, 0); }
		public TargetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_target; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterTarget(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitTarget(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitTarget(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TargetContext target() throws RecognitionException {
		TargetContext _localctx = new TargetContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_target);
		int _la;
		try {
			setState(360);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(334);
				match(NAME);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(335);
				match(LPAREN);
				setState(337);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 547556958404608L) != 0)) {
					{
					setState(336);
					targetList();
					}
				}

				setState(340);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(339);
					match(COMMA);
					}
				}

				setState(342);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(343);
				match(LBRACKET);
				setState(345);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 547556958404608L) != 0)) {
					{
					setState(344);
					targetList();
					}
				}

				setState(348);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(347);
					match(COMMA);
					}
				}

				setState(350);
				match(RBRACKET);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(351);
				atom(0);
				setState(352);
				match(LBRACKET);
				setState(353);
				expr();
				setState(354);
				match(RBRACKET);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(356);
				atom(0);
				setState(357);
				match(DOT);
				setState(358);
				match(NAME);
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

	@SuppressWarnings("CheckReturnValue")
	public static class TargetListContext extends ParserRuleContext {
		public List<TargetContext> target() {
			return getRuleContexts(TargetContext.class);
		}
		public TargetContext target(int i) {
			return getRuleContext(TargetContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public TargetListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_targetList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterTargetList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitTargetList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitTargetList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TargetListContext targetList() throws RecognitionException {
		TargetListContext _localctx = new TargetListContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_targetList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
			target();
			setState(367);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(363);
					match(COMMA);
					setState(364);
					target();
					}
					} 
				}
				setState(369);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListLiteralContext extends AtomContext {
		public TerminalNode LBRACKET() { return getToken(MicroJParser.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(MicroJParser.RBRACKET, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public ListLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitListLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitListLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends AtomContext {
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public VariableContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends AtomContext {
		public TerminalNode NUMBER() { return getToken(MicroJParser.NUMBER, 0); }
		public NumberContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends AtomContext {
		public TerminalNode STRING() { return getToken(MicroJParser.STRING, 0); }
		public StringLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatLiteralContext extends AtomContext {
		public TerminalNode FLOAT() { return getToken(MicroJParser.FLOAT, 0); }
		public FloatLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterFloatLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitFloatLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitFloatLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TupleLiteralContext extends AtomContext {
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public TupleLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterTupleLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitTupleLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitTupleLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GetAttrAtomContext extends AtomContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode DOT() { return getToken(MicroJParser.DOT, 0); }
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public GetAttrAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterGetAttrAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitGetAttrAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitGetAttrAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallAtomContext extends AtomContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public ArgListContext argList() {
			return getRuleContext(ArgListContext.class,0);
		}
		public CallAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterCallAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitCallAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitCallAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenAtomContext extends AtomContext {
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public ParenAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterParenAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitParenAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitParenAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubscriptAtomContext extends AtomContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TerminalNode LBRACKET() { return getToken(MicroJParser.LBRACKET, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(MicroJParser.RBRACKET, 0); }
		public SubscriptAtomContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterSubscriptAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitSubscriptAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitSubscriptAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImagLiteralContext extends AtomContext {
		public TerminalNode IMAG() { return getToken(MicroJParser.IMAG, 0); }
		public ImagLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterImagLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitImagLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitImagLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DictLiteralContext extends AtomContext {
		public TerminalNode LBRACE() { return getToken(MicroJParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(MicroJParser.RBRACE, 0); }
		public DictListContext dictList() {
			return getRuleContext(DictListContext.class,0);
		}
		public DictLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterDictLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitDictLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitDictLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		return atom(0);
	}

	private AtomContext atom(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AtomContext _localctx = new AtomContext(_ctx, _parentState);
		AtomContext _prevctx = _localctx;
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_atom, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				{
				_localctx = new NumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(371);
				match(NUMBER);
				}
				break;
			case 2:
				{
				_localctx = new FloatLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(372);
				match(FLOAT);
				}
				break;
			case 3:
				{
				_localctx = new ImagLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(373);
				match(IMAG);
				}
				break;
			case 4:
				{
				_localctx = new StringLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(374);
				match(STRING);
				}
				break;
			case 5:
				{
				_localctx = new VariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(375);
				match(NAME);
				}
				break;
			case 6:
				{
				_localctx = new TupleLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(376);
				match(LPAREN);
				setState(377);
				expr();
				setState(382);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(378);
						match(COMMA);
						setState(379);
						expr();
						}
						} 
					}
					setState(384);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
				}
				setState(386);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(385);
					match(COMMA);
					}
				}

				setState(388);
				match(RPAREN);
				}
				break;
			case 7:
				{
				_localctx = new ListLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(390);
				match(LBRACKET);
				setState(392);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 547558573211648L) != 0)) {
					{
					setState(391);
					exprList();
					}
				}

				setState(394);
				match(RBRACKET);
				}
				break;
			case 8:
				{
				_localctx = new DictLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(395);
				match(LBRACE);
				setState(397);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 547558573211648L) != 0)) {
					{
					setState(396);
					dictList();
					}
				}

				setState(399);
				match(RBRACE);
				}
				break;
			case 9:
				{
				_localctx = new ParenAtomContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(400);
				match(LPAREN);
				setState(401);
				expr();
				setState(402);
				match(RPAREN);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(422);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(420);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
					case 1:
						{
						_localctx = new SubscriptAtomContext(new AtomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_atom);
						setState(406);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(407);
						match(LBRACKET);
						setState(408);
						expr();
						setState(409);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new GetAttrAtomContext(new AtomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_atom);
						setState(411);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(412);
						match(DOT);
						setState(413);
						match(NAME);
						}
						break;
					case 3:
						{
						_localctx = new CallAtomContext(new AtomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_atom);
						setState(414);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(415);
						match(LPAREN);
						setState(417);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 547558573211648L) != 0)) {
							{
							setState(416);
							argList();
							}
						}

						setState(419);
						match(RPAREN);
						}
						break;
					}
					} 
				}
				setState(424);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExprListContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public ExprListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterExprList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitExprList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitExprList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprListContext exprList() throws RecognitionException {
		ExprListContext _localctx = new ExprListContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(425);
			expr();
			setState(430);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(426);
				match(COMMA);
				setState(427);
				expr();
				}
				}
				setState(432);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DictListContext extends ParserRuleContext {
		public List<DictItemContext> dictItem() {
			return getRuleContexts(DictItemContext.class);
		}
		public DictItemContext dictItem(int i) {
			return getRuleContext(DictItemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public DictListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterDictList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitDictList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitDictList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictListContext dictList() throws RecognitionException {
		DictListContext _localctx = new DictListContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_dictList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(433);
			dictItem();
			setState(438);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(434);
				match(COMMA);
				setState(435);
				dictItem();
				}
				}
				setState(440);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DictItemContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COLON() { return getToken(MicroJParser.COLON, 0); }
		public DictItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dictItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterDictItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitDictItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitDictItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DictItemContext dictItem() throws RecognitionException {
		DictItemContext _localctx = new DictItemContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_dictItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(441);
			expr();
			setState(442);
			match(COLON);
			setState(443);
			expr();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgListContext extends ParserRuleContext {
		public List<ArgContext> arg() {
			return getRuleContexts(ArgContext.class);
		}
		public ArgContext arg(int i) {
			return getRuleContext(ArgContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public ArgListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterArgList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitArgList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitArgList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgListContext argList() throws RecognitionException {
		ArgListContext _localctx = new ArgListContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(445);
			arg();
			setState(450);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(446);
				match(COMMA);
				setState(447);
				arg();
				}
				}
				setState(452);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArgContext extends ParserRuleContext {
		public ArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg; }
	 
		public ArgContext() { }
		public void copyFrom(ArgContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PosArgContext extends ArgContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PosArgContext(ArgContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterPosArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitPosArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitPosArg(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class KwArgContext extends ArgContext {
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public TerminalNode EQUAL() { return getToken(MicroJParser.EQUAL, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public KwArgContext(ArgContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterKwArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitKwArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitKwArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgContext arg() throws RecognitionException {
		ArgContext _localctx = new ArgContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_arg);
		try {
			setState(457);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				_localctx = new KwArgContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(453);
				match(NAME);
				setState(454);
				match(EQUAL);
				setState(455);
				expr();
				}
				break;
			case 2:
				_localctx = new PosArgContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(456);
				expr();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ImportStatementContext extends ParserRuleContext {
		public ImportStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importStatement; }
	 
		public ImportStatementContext() { }
		public void copyFrom(ImportStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImportFromContext extends ImportStatementContext {
		public TerminalNode FROM() { return getToken(MicroJParser.FROM, 0); }
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public TerminalNode IMPORT() { return getToken(MicroJParser.IMPORT, 0); }
		public ImportNamesContext importNames() {
			return getRuleContext(ImportNamesContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public ImportFromContext(ImportStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterImportFrom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitImportFrom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitImportFrom(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImportModuleContext extends ImportStatementContext {
		public TerminalNode IMPORT() { return getToken(MicroJParser.IMPORT, 0); }
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public TerminalNode AS() { return getToken(MicroJParser.AS, 0); }
		public ImportModuleContext(ImportStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterImportModule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitImportModule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitImportModule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportStatementContext importStatement() throws RecognitionException {
		ImportStatementContext _localctx = new ImportStatementContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_importStatement);
		int _la;
		try {
			setState(472);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IMPORT:
				_localctx = new ImportModuleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(459);
				match(IMPORT);
				setState(460);
				match(NAME);
				setState(463);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(461);
					match(AS);
					setState(462);
					match(NAME);
					}
				}

				setState(465);
				match(NEWLINE);
				}
				break;
			case FROM:
				_localctx = new ImportFromContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(466);
				match(FROM);
				setState(467);
				match(NAME);
				setState(468);
				match(IMPORT);
				setState(469);
				importNames();
				setState(470);
				match(NEWLINE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ImportNamesContext extends ParserRuleContext {
		public TerminalNode STAR() { return getToken(MicroJParser.STAR, 0); }
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public List<TerminalNode> AS() { return getTokens(MicroJParser.AS); }
		public TerminalNode AS(int i) {
			return getToken(MicroJParser.AS, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public ImportNamesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importNames; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterImportNames(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitImportNames(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitImportNames(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportNamesContext importNames() throws RecognitionException {
		ImportNamesContext _localctx = new ImportNamesContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_importNames);
		int _la;
		try {
			setState(491);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(474);
				match(STAR);
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(475);
				match(NAME);
				setState(478);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(476);
					match(AS);
					setState(477);
					match(NAME);
					}
				}

				setState(488);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(480);
					match(COMMA);
					setState(481);
					match(NAME);
					setState(484);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(482);
						match(AS);
						setState(483);
						match(NAME);
						}
					}

					}
					}
					setState(490);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 31:
			return atom_sempred((AtomContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean atom_sempred(AtomContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00015\u01ee\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0001\u0000\u0001\u0000"+
		"\u0005\u0000Q\b\u0000\n\u0000\f\u0000T\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u0001`\b\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002g\b\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0005\u0003l\b\u0003\n\u0003\f\u0003o\t\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003u\b\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u0081\b\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006"+
		"\u008e\b\u0006\n\u0006\f\u0006\u0091\t\u0006\u0001\u0007\u0001\u0007\u0003"+
		"\u0007\u0095\b\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\n\u00a1\b\n\u0001\n\u0001\n\u0001\n\u0005"+
		"\n\u00a6\b\n\n\n\f\n\u00a9\t\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n"+
		"\u00af\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00b5\b\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u00ba\b\u000b\u0001\u000b\u0001\u000b"+
		"\u0003\u000b\u00be\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u00cb\b\f\n\f\f\f\u00ce"+
		"\t\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00d4\b\f\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00df"+
		"\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003"+
		"\u000e\u00ec\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0004\u000f\u00f1"+
		"\b\u000f\u000b\u000f\f\u000f\u00f2\u0001\u000f\u0001\u000f\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u0108\b\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0005\u0012"+
		"\u010f\b\u0012\n\u0012\f\u0012\u0112\t\u0012\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0005\u0013\u0117\b\u0013\n\u0013\f\u0013\u011a\t\u0013\u0001\u0014"+
		"\u0005\u0014\u011d\b\u0014\n\u0014\f\u0014\u0120\t\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u0128"+
		"\b\u0015\n\u0015\f\u0015\u012b\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0005\u0016\u0131\b\u0016\n\u0016\f\u0016\u0134\t\u0016\u0001"+
		"\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017\u013a\b\u0017\n"+
		"\u0017\f\u0017\u013d\t\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0003"+
		"\u0018\u0142\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019\u0147"+
		"\b\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001"+
		"\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u0152\b\u001d\u0001"+
		"\u001d\u0003\u001d\u0155\b\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0003"+
		"\u001d\u015a\b\u001d\u0001\u001d\u0003\u001d\u015d\b\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u0169\b\u001d\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0005\u001e\u016e\b\u001e\n\u001e\f\u001e\u0171"+
		"\t\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		"\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u017d"+
		"\b\u001f\n\u001f\f\u001f\u0180\t\u001f\u0001\u001f\u0003\u001f\u0183\b"+
		"\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0003\u001f\u0189"+
		"\b\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0003\u001f\u018e\b\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0003\u001f"+
		"\u0195\b\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0003\u001f\u01a2\b\u001f\u0001\u001f\u0005\u001f\u01a5\b\u001f\n\u001f"+
		"\f\u001f\u01a8\t\u001f\u0001 \u0001 \u0001 \u0005 \u01ad\b \n \f \u01b0"+
		"\t \u0001!\u0001!\u0001!\u0005!\u01b5\b!\n!\f!\u01b8\t!\u0001\"\u0001"+
		"\"\u0001\"\u0001\"\u0001#\u0001#\u0001#\u0005#\u01c1\b#\n#\f#\u01c4\t"+
		"#\u0001$\u0001$\u0001$\u0001$\u0003$\u01ca\b$\u0001%\u0001%\u0001%\u0001"+
		"%\u0003%\u01d0\b%\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0003"+
		"%\u01d9\b%\u0001&\u0001&\u0001&\u0001&\u0003&\u01df\b&\u0001&\u0001&\u0001"+
		"&\u0001&\u0003&\u01e5\b&\u0005&\u01e7\b&\n&\f&\u01ea\t&\u0003&\u01ec\b"+
		"&\u0001&\u0000\u0001>\'\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJL\u0000\u0003"+
		"\u0001\u0000\u001d\u001e\u0002\u0000\u0003\u0004#(\u0001\u0000\u001f "+
		"\u0211\u0000R\u0001\u0000\u0000\u0000\u0002_\u0001\u0000\u0000\u0000\u0004"+
		"f\u0001\u0000\u0000\u0000\u0006m\u0001\u0000\u0000\u0000\b{\u0001\u0000"+
		"\u0000\u0000\n\u0086\u0001\u0000\u0000\u0000\f\u008a\u0001\u0000\u0000"+
		"\u0000\u000e\u0092\u0001\u0000\u0000\u0000\u0010\u0096\u0001\u0000\u0000"+
		"\u0000\u0012\u0098\u0001\u0000\u0000\u0000\u0014\u009a\u0001\u0000\u0000"+
		"\u0000\u0016\u00bd\u0001\u0000\u0000\u0000\u0018\u00bf\u0001\u0000\u0000"+
		"\u0000\u001a\u00d5\u0001\u0000\u0000\u0000\u001c\u00e0\u0001\u0000\u0000"+
		"\u0000\u001e\u00ed\u0001\u0000\u0000\u0000 \u0107\u0001\u0000\u0000\u0000"+
		"\"\u0109\u0001\u0000\u0000\u0000$\u010b\u0001\u0000\u0000\u0000&\u0113"+
		"\u0001\u0000\u0000\u0000(\u011e\u0001\u0000\u0000\u0000*\u0123\u0001\u0000"+
		"\u0000\u0000,\u012c\u0001\u0000\u0000\u0000.\u0135\u0001\u0000\u0000\u0000"+
		"0\u0141\u0001\u0000\u0000\u00002\u0143\u0001\u0000\u0000\u00004\u0148"+
		"\u0001\u0000\u0000\u00006\u014a\u0001\u0000\u0000\u00008\u014c\u0001\u0000"+
		"\u0000\u0000:\u0168\u0001\u0000\u0000\u0000<\u016a\u0001\u0000\u0000\u0000"+
		">\u0194\u0001\u0000\u0000\u0000@\u01a9\u0001\u0000\u0000\u0000B\u01b1"+
		"\u0001\u0000\u0000\u0000D\u01b9\u0001\u0000\u0000\u0000F\u01bd\u0001\u0000"+
		"\u0000\u0000H\u01c9\u0001\u0000\u0000\u0000J\u01d8\u0001\u0000\u0000\u0000"+
		"L\u01eb\u0001\u0000\u0000\u0000NQ\u00051\u0000\u0000OQ\u0003\u0002\u0001"+
		"\u0000PN\u0001\u0000\u0000\u0000PO\u0001\u0000\u0000\u0000QT\u0001\u0000"+
		"\u0000\u0000RP\u0001\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000SU\u0001"+
		"\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000UV\u0005\u0000\u0000\u0001"+
		"V\u0001\u0001\u0000\u0000\u0000W`\u0003\u0004\u0002\u0000X`\u0003\u0018"+
		"\f\u0000Y`\u0003\u001a\r\u0000Z`\u0003\u0006\u0003\u0000[`\u0003\u001c"+
		"\u000e\u0000\\`\u0003J%\u0000]`\u0003\u0014\n\u0000^`\u0003\b\u0004\u0000"+
		"_W\u0001\u0000\u0000\u0000_X\u0001\u0000\u0000\u0000_Y\u0001\u0000\u0000"+
		"\u0000_Z\u0001\u0000\u0000\u0000_[\u0001\u0000\u0000\u0000_\\\u0001\u0000"+
		"\u0000\u0000_]\u0001\u0000\u0000\u0000_^\u0001\u0000\u0000\u0000`\u0003"+
		"\u0001\u0000\u0000\u0000ag\u0003 \u0010\u0000bg\u0003\"\u0011\u0000cg"+
		"\u0003\u000e\u0007\u0000dg\u0003\u0010\b\u0000eg\u0003\u0012\t\u0000f"+
		"a\u0001\u0000\u0000\u0000fb\u0001\u0000\u0000\u0000fc\u0001\u0000\u0000"+
		"\u0000fd\u0001\u0000\u0000\u0000fe\u0001\u0000\u0000\u0000gh\u0001\u0000"+
		"\u0000\u0000hi\u00051\u0000\u0000i\u0005\u0001\u0000\u0000\u0000jl\u0003"+
		"\n\u0005\u0000kj\u0001\u0000\u0000\u0000lo\u0001\u0000\u0000\u0000mk\u0001"+
		"\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000np\u0001\u0000\u0000\u0000"+
		"om\u0001\u0000\u0000\u0000pq\u0005\t\u0000\u0000qr\u0005,\u0000\u0000"+
		"rt\u0005)\u0000\u0000su\u0003\f\u0006\u0000ts\u0001\u0000\u0000\u0000"+
		"tu\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vw\u0005*\u0000\u0000"+
		"wx\u0005\u0018\u0000\u0000xy\u00051\u0000\u0000yz\u0003\u001e\u000f\u0000"+
		"z\u0007\u0001\u0000\u0000\u0000{|\u0005\u0001\u0000\u0000|\u0080\u0005"+
		",\u0000\u0000}~\u0005)\u0000\u0000~\u007f\u0005,\u0000\u0000\u007f\u0081"+
		"\u0005*\u0000\u0000\u0080}\u0001\u0000\u0000\u0000\u0080\u0081\u0001\u0000"+
		"\u0000\u0000\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0083\u0005\u0018"+
		"\u0000\u0000\u0083\u0084\u00051\u0000\u0000\u0084\u0085\u0003\u001e\u000f"+
		"\u0000\u0085\t\u0001\u0000\u0000\u0000\u0086\u0087\u0005\u0013\u0000\u0000"+
		"\u0087\u0088\u0003\"\u0011\u0000\u0088\u0089\u00051\u0000\u0000\u0089"+
		"\u000b\u0001\u0000\u0000\u0000\u008a\u008f\u0005,\u0000\u0000\u008b\u008c"+
		"\u0005+\u0000\u0000\u008c\u008e\u0005,\u0000\u0000\u008d\u008b\u0001\u0000"+
		"\u0000\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000"+
		"\u0000\u0000\u008f\u0090\u0001\u0000\u0000\u0000\u0090\r\u0001\u0000\u0000"+
		"\u0000\u0091\u008f\u0001\u0000\u0000\u0000\u0092\u0094\u0005\n\u0000\u0000"+
		"\u0093\u0095\u0003@ \u0000\u0094\u0093\u0001\u0000\u0000\u0000\u0094\u0095"+
		"\u0001\u0000\u0000\u0000\u0095\u000f\u0001\u0000\u0000\u0000\u0096\u0097"+
		"\u0005\u000e\u0000\u0000\u0097\u0011\u0001\u0000\u0000\u0000\u0098\u0099"+
		"\u0005\u000f\u0000\u0000\u0099\u0013\u0001\u0000\u0000\u0000\u009a\u009b"+
		"\u0005\u0010\u0000\u0000\u009b\u009c\u0005\u0018\u0000\u0000\u009c\u009d"+
		"\u00051\u0000\u0000\u009d\u00a7\u0003\u001e\u000f\u0000\u009e\u00a0\u0005"+
		"\u0011\u0000\u0000\u009f\u00a1\u0003\u0016\u000b\u0000\u00a0\u009f\u0001"+
		"\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a3\u0005\u0018\u0000\u0000\u00a3\u00a4\u0005"+
		"1\u0000\u0000\u00a4\u00a6\u0003\u001e\u000f\u0000\u00a5\u009e\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a9\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8\u00ae\u0001\u0000"+
		"\u0000\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00aa\u00ab\u0005\u0007"+
		"\u0000\u0000\u00ab\u00ac\u0005\u0018\u0000\u0000\u00ac\u00ad\u00051\u0000"+
		"\u0000\u00ad\u00af\u0003\u001e\u000f\u0000\u00ae\u00aa\u0001\u0000\u0000"+
		"\u0000\u00ae\u00af\u0001\u0000\u0000\u0000\u00af\u00b4\u0001\u0000\u0000"+
		"\u0000\u00b0\u00b1\u0005\u0012\u0000\u0000\u00b1\u00b2\u0005\u0018\u0000"+
		"\u0000\u00b2\u00b3\u00051\u0000\u0000\u00b3\u00b5\u0003\u001e\u000f\u0000"+
		"\u00b4\u00b0\u0001\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000"+
		"\u00b5\u0015\u0001\u0000\u0000\u0000\u00b6\u00b9\u0005,\u0000\u0000\u00b7"+
		"\u00b8\u0005\r\u0000\u0000\u00b8\u00ba\u0005,\u0000\u0000\u00b9\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba\u00be"+
		"\u0001\u0000\u0000\u0000\u00bb\u00bc\u0005\r\u0000\u0000\u00bc\u00be\u0005"+
		",\u0000\u0000\u00bd\u00b6\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000"+
		"\u0000\u0000\u00be\u0017\u0001\u0000\u0000\u0000\u00bf\u00c0\u0005\u0005"+
		"\u0000\u0000\u00c0\u00c1\u0003\"\u0011\u0000\u00c1\u00c2\u0005\u0018\u0000"+
		"\u0000\u00c2\u00c3\u00051\u0000\u0000\u00c3\u00cc\u0003\u001e\u000f\u0000"+
		"\u00c4\u00c5\u0005\u0006\u0000\u0000\u00c5\u00c6\u0003\"\u0011\u0000\u00c6"+
		"\u00c7\u0005\u0018\u0000\u0000\u00c7\u00c8\u00051\u0000\u0000\u00c8\u00c9"+
		"\u0003\u001e\u000f\u0000\u00c9\u00cb\u0001\u0000\u0000\u0000\u00ca\u00c4"+
		"\u0001\u0000\u0000\u0000\u00cb\u00ce\u0001\u0000\u0000\u0000\u00cc\u00ca"+
		"\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd\u00d3"+
		"\u0001\u0000\u0000\u0000\u00ce\u00cc\u0001\u0000\u0000\u0000\u00cf\u00d0"+
		"\u0005\u0007\u0000\u0000\u00d0\u00d1\u0005\u0018\u0000\u0000\u00d1\u00d2"+
		"\u00051\u0000\u0000\u00d2\u00d4\u0003\u001e\u000f\u0000\u00d3\u00cf\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u0019\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d6\u0005\b\u0000\u0000\u00d6\u00d7\u0003\""+
		"\u0011\u0000\u00d7\u00d8\u0005\u0018\u0000\u0000\u00d8\u00d9\u00051\u0000"+
		"\u0000\u00d9\u00de\u0003\u001e\u000f\u0000\u00da\u00db\u0005\u0007\u0000"+
		"\u0000\u00db\u00dc\u0005\u0018\u0000\u0000\u00dc\u00dd\u00051\u0000\u0000"+
		"\u00dd\u00df\u0003\u001e\u000f\u0000\u00de\u00da\u0001\u0000\u0000\u0000"+
		"\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u001b\u0001\u0000\u0000\u0000"+
		"\u00e0\u00e1\u0005\u0002\u0000\u0000\u00e1\u00e2\u0003<\u001e\u0000\u00e2"+
		"\u00e3\u0005\u0003\u0000\u0000\u00e3\u00e4\u0003\"\u0011\u0000\u00e4\u00e5"+
		"\u0005\u0018\u0000\u0000\u00e5\u00e6\u00051\u0000\u0000\u00e6\u00eb\u0003"+
		"\u001e\u000f\u0000\u00e7\u00e8\u0005\u0007\u0000\u0000\u00e8\u00e9\u0005"+
		"\u0018\u0000\u0000\u00e9\u00ea\u00051\u0000\u0000\u00ea\u00ec\u0003\u001e"+
		"\u000f\u0000\u00eb\u00e7\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000"+
		"\u0000\u0000\u00ec\u001d\u0001\u0000\u0000\u0000\u00ed\u00f0\u00054\u0000"+
		"\u0000\u00ee\u00f1\u00051\u0000\u0000\u00ef\u00f1\u0003\u0002\u0001\u0000"+
		"\u00f0\u00ee\u0001\u0000\u0000\u0000\u00f0\u00ef\u0001\u0000\u0000\u0000"+
		"\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u00f0\u0001\u0000\u0000\u0000"+
		"\u00f2\u00f3\u0001\u0000\u0000\u0000\u00f3\u00f4\u0001\u0000\u0000\u0000"+
		"\u00f4\u00f5\u00055\u0000\u0000\u00f5\u001f\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f7\u0003>\u001f\u0000\u00f7\u00f8\u0005\u0019\u0000\u0000\u00f8\u00f9"+
		"\u0003\"\u0011\u0000\u00f9\u00fa\u0005\u001a\u0000\u0000\u00fa\u00fb\u0005"+
		"\"\u0000\u0000\u00fb\u00fc\u0003\"\u0011\u0000\u00fc\u0108\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fe\u0003>\u001f\u0000\u00fe\u00ff\u0005\u0017\u0000"+
		"\u0000\u00ff\u0100\u0005,\u0000\u0000\u0100\u0101\u0005\"\u0000\u0000"+
		"\u0101\u0102\u0003\"\u0011\u0000\u0102\u0108\u0001\u0000\u0000\u0000\u0103"+
		"\u0104\u0003:\u001d\u0000\u0104\u0105\u0005\"\u0000\u0000\u0105\u0106"+
		"\u0003@ \u0000\u0106\u0108\u0001\u0000\u0000\u0000\u0107\u00f6\u0001\u0000"+
		"\u0000\u0000\u0107\u00fd\u0001\u0000\u0000\u0000\u0107\u0103\u0001\u0000"+
		"\u0000\u0000\u0108!\u0001\u0000\u0000\u0000\u0109\u010a\u0003$\u0012\u0000"+
		"\u010a#\u0001\u0000\u0000\u0000\u010b\u0110\u0003&\u0013\u0000\u010c\u010d"+
		"\u0005\u0015\u0000\u0000\u010d\u010f\u0003&\u0013\u0000\u010e\u010c\u0001"+
		"\u0000\u0000\u0000\u010f\u0112\u0001\u0000\u0000\u0000\u0110\u010e\u0001"+
		"\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111%\u0001\u0000"+
		"\u0000\u0000\u0112\u0110\u0001\u0000\u0000\u0000\u0113\u0118\u0003(\u0014"+
		"\u0000\u0114\u0115\u0005\u0014\u0000\u0000\u0115\u0117\u0003(\u0014\u0000"+
		"\u0116\u0114\u0001\u0000\u0000\u0000\u0117\u011a\u0001\u0000\u0000\u0000"+
		"\u0118\u0116\u0001\u0000\u0000\u0000\u0118\u0119\u0001\u0000\u0000\u0000"+
		"\u0119\'\u0001\u0000\u0000\u0000\u011a\u0118\u0001\u0000\u0000\u0000\u011b"+
		"\u011d\u0005\u0016\u0000\u0000\u011c\u011b\u0001\u0000\u0000\u0000\u011d"+
		"\u0120\u0001\u0000\u0000\u0000\u011e\u011c\u0001\u0000\u0000\u0000\u011e"+
		"\u011f\u0001\u0000\u0000\u0000\u011f\u0121\u0001\u0000\u0000\u0000\u0120"+
		"\u011e\u0001\u0000\u0000\u0000\u0121\u0122\u0003*\u0015\u0000\u0122)\u0001"+
		"\u0000\u0000\u0000\u0123\u0129\u0003,\u0016\u0000\u0124\u0125\u00034\u001a"+
		"\u0000\u0125\u0126\u0003,\u0016\u0000\u0126\u0128\u0001\u0000\u0000\u0000"+
		"\u0127\u0124\u0001\u0000\u0000\u0000\u0128\u012b\u0001\u0000\u0000\u0000"+
		"\u0129\u0127\u0001\u0000\u0000\u0000\u0129\u012a\u0001\u0000\u0000\u0000"+
		"\u012a+\u0001\u0000\u0000\u0000\u012b\u0129\u0001\u0000\u0000\u0000\u012c"+
		"\u0132\u0003.\u0017\u0000\u012d\u012e\u00036\u001b\u0000\u012e\u012f\u0003"+
		".\u0017\u0000\u012f\u0131\u0001\u0000\u0000\u0000\u0130\u012d\u0001\u0000"+
		"\u0000\u0000\u0131\u0134\u0001\u0000\u0000\u0000\u0132\u0130\u0001\u0000"+
		"\u0000\u0000\u0132\u0133\u0001\u0000\u0000\u0000\u0133-\u0001\u0000\u0000"+
		"\u0000\u0134\u0132\u0001\u0000\u0000\u0000\u0135\u013b\u00030\u0018\u0000"+
		"\u0136\u0137\u00038\u001c\u0000\u0137\u0138\u00030\u0018\u0000\u0138\u013a"+
		"\u0001\u0000\u0000\u0000\u0139\u0136\u0001\u0000\u0000\u0000\u013a\u013d"+
		"\u0001\u0000\u0000\u0000\u013b\u0139\u0001\u0000\u0000\u0000\u013b\u013c"+
		"\u0001\u0000\u0000\u0000\u013c/\u0001\u0000\u0000\u0000\u013d\u013b\u0001"+
		"\u0000\u0000\u0000\u013e\u013f\u0007\u0000\u0000\u0000\u013f\u0142\u0003"+
		"0\u0018\u0000\u0140\u0142\u00032\u0019\u0000\u0141\u013e\u0001\u0000\u0000"+
		"\u0000\u0141\u0140\u0001\u0000\u0000\u0000\u01421\u0001\u0000\u0000\u0000"+
		"\u0143\u0146\u0003>\u001f\u0000\u0144\u0145\u0005!\u0000\u0000\u0145\u0147"+
		"\u00030\u0018\u0000\u0146\u0144\u0001\u0000\u0000\u0000\u0146\u0147\u0001"+
		"\u0000\u0000\u0000\u01473\u0001\u0000\u0000\u0000\u0148\u0149\u0007\u0001"+
		"\u0000\u0000\u01495\u0001\u0000\u0000\u0000\u014a\u014b\u0007\u0000\u0000"+
		"\u0000\u014b7\u0001\u0000\u0000\u0000\u014c\u014d\u0007\u0002\u0000\u0000"+
		"\u014d9\u0001\u0000\u0000\u0000\u014e\u0169\u0005,\u0000\u0000\u014f\u0151"+
		"\u0005)\u0000\u0000\u0150\u0152\u0003<\u001e\u0000\u0151\u0150\u0001\u0000"+
		"\u0000\u0000\u0151\u0152\u0001\u0000\u0000\u0000\u0152\u0154\u0001\u0000"+
		"\u0000\u0000\u0153\u0155\u0005+\u0000\u0000\u0154\u0153\u0001\u0000\u0000"+
		"\u0000\u0154\u0155\u0001\u0000\u0000\u0000\u0155\u0156\u0001\u0000\u0000"+
		"\u0000\u0156\u0169\u0005*\u0000\u0000\u0157\u0159\u0005\u0019\u0000\u0000"+
		"\u0158\u015a\u0003<\u001e\u0000\u0159\u0158\u0001\u0000\u0000\u0000\u0159"+
		"\u015a\u0001\u0000\u0000\u0000\u015a\u015c\u0001\u0000\u0000\u0000\u015b"+
		"\u015d\u0005+\u0000\u0000\u015c\u015b\u0001\u0000\u0000\u0000\u015c\u015d"+
		"\u0001\u0000\u0000\u0000\u015d\u015e\u0001\u0000\u0000\u0000\u015e\u0169"+
		"\u0005\u001a\u0000\u0000\u015f\u0160\u0003>\u001f\u0000\u0160\u0161\u0005"+
		"\u0019\u0000\u0000\u0161\u0162\u0003\"\u0011\u0000\u0162\u0163\u0005\u001a"+
		"\u0000\u0000\u0163\u0169\u0001\u0000\u0000\u0000\u0164\u0165\u0003>\u001f"+
		"\u0000\u0165\u0166\u0005\u0017\u0000\u0000\u0166\u0167\u0005,\u0000\u0000"+
		"\u0167\u0169\u0001\u0000\u0000\u0000\u0168\u014e\u0001\u0000\u0000\u0000"+
		"\u0168\u014f\u0001\u0000\u0000\u0000\u0168\u0157\u0001\u0000\u0000\u0000"+
		"\u0168\u015f\u0001\u0000\u0000\u0000\u0168\u0164\u0001\u0000\u0000\u0000"+
		"\u0169;\u0001\u0000\u0000\u0000\u016a\u016f\u0003:\u001d\u0000\u016b\u016c"+
		"\u0005+\u0000\u0000\u016c\u016e\u0003:\u001d\u0000\u016d\u016b\u0001\u0000"+
		"\u0000\u0000\u016e\u0171\u0001\u0000\u0000\u0000\u016f\u016d\u0001\u0000"+
		"\u0000\u0000\u016f\u0170\u0001\u0000\u0000\u0000\u0170=\u0001\u0000\u0000"+
		"\u0000\u0171\u016f\u0001\u0000\u0000\u0000\u0172\u0173\u0006\u001f\uffff"+
		"\uffff\u0000\u0173\u0195\u0005-\u0000\u0000\u0174\u0195\u0005.\u0000\u0000"+
		"\u0175\u0195\u0005/\u0000\u0000\u0176\u0195\u00050\u0000\u0000\u0177\u0195"+
		"\u0005,\u0000\u0000\u0178\u0179\u0005)\u0000\u0000\u0179\u017e\u0003\""+
		"\u0011\u0000\u017a\u017b\u0005+\u0000\u0000\u017b\u017d\u0003\"\u0011"+
		"\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017d\u0180\u0001\u0000\u0000"+
		"\u0000\u017e\u017c\u0001\u0000\u0000\u0000\u017e\u017f\u0001\u0000\u0000"+
		"\u0000\u017f\u0182\u0001\u0000\u0000\u0000\u0180\u017e\u0001\u0000\u0000"+
		"\u0000\u0181\u0183\u0005+\u0000\u0000\u0182\u0181\u0001\u0000\u0000\u0000"+
		"\u0182\u0183\u0001\u0000\u0000\u0000\u0183\u0184\u0001\u0000\u0000\u0000"+
		"\u0184\u0185\u0005*\u0000\u0000\u0185\u0195\u0001\u0000\u0000\u0000\u0186"+
		"\u0188\u0005\u0019\u0000\u0000\u0187\u0189\u0003@ \u0000\u0188\u0187\u0001"+
		"\u0000\u0000\u0000\u0188\u0189\u0001\u0000\u0000\u0000\u0189\u018a\u0001"+
		"\u0000\u0000\u0000\u018a\u0195\u0005\u001a\u0000\u0000\u018b\u018d\u0005"+
		"\u001b\u0000\u0000\u018c\u018e\u0003B!\u0000\u018d\u018c\u0001\u0000\u0000"+
		"\u0000\u018d\u018e\u0001\u0000\u0000\u0000\u018e\u018f\u0001\u0000\u0000"+
		"\u0000\u018f\u0195\u0005\u001c\u0000\u0000\u0190\u0191\u0005)\u0000\u0000"+
		"\u0191\u0192\u0003\"\u0011\u0000\u0192\u0193\u0005*\u0000\u0000\u0193"+
		"\u0195\u0001\u0000\u0000\u0000\u0194\u0172\u0001\u0000\u0000\u0000\u0194"+
		"\u0174\u0001\u0000\u0000\u0000\u0194\u0175\u0001\u0000\u0000\u0000\u0194"+
		"\u0176\u0001\u0000\u0000\u0000\u0194\u0177\u0001\u0000\u0000\u0000\u0194"+
		"\u0178\u0001\u0000\u0000\u0000\u0194\u0186\u0001\u0000\u0000\u0000\u0194"+
		"\u018b\u0001\u0000\u0000\u0000\u0194\u0190\u0001\u0000\u0000\u0000\u0195"+
		"\u01a6\u0001\u0000\u0000\u0000\u0196\u0197\n\u0004\u0000\u0000\u0197\u0198"+
		"\u0005\u0019\u0000\u0000\u0198\u0199\u0003\"\u0011\u0000\u0199\u019a\u0005"+
		"\u001a\u0000\u0000\u019a\u01a5\u0001\u0000\u0000\u0000\u019b\u019c\n\u0003"+
		"\u0000\u0000\u019c\u019d\u0005\u0017\u0000\u0000\u019d\u01a5\u0005,\u0000"+
		"\u0000\u019e\u019f\n\u0002\u0000\u0000\u019f\u01a1\u0005)\u0000\u0000"+
		"\u01a0\u01a2\u0003F#\u0000\u01a1\u01a0\u0001\u0000\u0000\u0000\u01a1\u01a2"+
		"\u0001\u0000\u0000\u0000\u01a2\u01a3\u0001\u0000\u0000\u0000\u01a3\u01a5"+
		"\u0005*\u0000\u0000\u01a4\u0196\u0001\u0000\u0000\u0000\u01a4\u019b\u0001"+
		"\u0000\u0000\u0000\u01a4\u019e\u0001\u0000\u0000\u0000\u01a5\u01a8\u0001"+
		"\u0000\u0000\u0000\u01a6\u01a4\u0001\u0000\u0000\u0000\u01a6\u01a7\u0001"+
		"\u0000\u0000\u0000\u01a7?\u0001\u0000\u0000\u0000\u01a8\u01a6\u0001\u0000"+
		"\u0000\u0000\u01a9\u01ae\u0003\"\u0011\u0000\u01aa\u01ab\u0005+\u0000"+
		"\u0000\u01ab\u01ad\u0003\"\u0011\u0000\u01ac\u01aa\u0001\u0000\u0000\u0000"+
		"\u01ad\u01b0\u0001\u0000\u0000\u0000\u01ae\u01ac\u0001\u0000\u0000\u0000"+
		"\u01ae\u01af\u0001\u0000\u0000\u0000\u01afA\u0001\u0000\u0000\u0000\u01b0"+
		"\u01ae\u0001\u0000\u0000\u0000\u01b1\u01b6\u0003D\"\u0000\u01b2\u01b3"+
		"\u0005+\u0000\u0000\u01b3\u01b5\u0003D\"\u0000\u01b4\u01b2\u0001\u0000"+
		"\u0000\u0000\u01b5\u01b8\u0001\u0000\u0000\u0000\u01b6\u01b4\u0001\u0000"+
		"\u0000\u0000\u01b6\u01b7\u0001\u0000\u0000\u0000\u01b7C\u0001\u0000\u0000"+
		"\u0000\u01b8\u01b6\u0001\u0000\u0000\u0000\u01b9\u01ba\u0003\"\u0011\u0000"+
		"\u01ba\u01bb\u0005\u0018\u0000\u0000\u01bb\u01bc\u0003\"\u0011\u0000\u01bc"+
		"E\u0001\u0000\u0000\u0000\u01bd\u01c2\u0003H$\u0000\u01be\u01bf\u0005"+
		"+\u0000\u0000\u01bf\u01c1\u0003H$\u0000\u01c0\u01be\u0001\u0000\u0000"+
		"\u0000\u01c1\u01c4\u0001\u0000\u0000\u0000\u01c2\u01c0\u0001\u0000\u0000"+
		"\u0000\u01c2\u01c3\u0001\u0000\u0000\u0000\u01c3G\u0001\u0000\u0000\u0000"+
		"\u01c4\u01c2\u0001\u0000\u0000\u0000\u01c5\u01c6\u0005,\u0000\u0000\u01c6"+
		"\u01c7\u0005\"\u0000\u0000\u01c7\u01ca\u0003\"\u0011\u0000\u01c8\u01ca"+
		"\u0003\"\u0011\u0000\u01c9\u01c5\u0001\u0000\u0000\u0000\u01c9\u01c8\u0001"+
		"\u0000\u0000\u0000\u01caI\u0001\u0000\u0000\u0000\u01cb\u01cc\u0005\u000b"+
		"\u0000\u0000\u01cc\u01cf\u0005,\u0000\u0000\u01cd\u01ce\u0005\r\u0000"+
		"\u0000\u01ce\u01d0\u0005,\u0000\u0000\u01cf\u01cd\u0001\u0000\u0000\u0000"+
		"\u01cf\u01d0\u0001\u0000\u0000\u0000\u01d0\u01d1\u0001\u0000\u0000\u0000"+
		"\u01d1\u01d9\u00051\u0000\u0000\u01d2\u01d3\u0005\f\u0000\u0000\u01d3"+
		"\u01d4\u0005,\u0000\u0000\u01d4\u01d5\u0005\u000b\u0000\u0000\u01d5\u01d6"+
		"\u0003L&\u0000\u01d6\u01d7\u00051\u0000\u0000\u01d7\u01d9\u0001\u0000"+
		"\u0000\u0000\u01d8\u01cb\u0001\u0000\u0000\u0000\u01d8\u01d2\u0001\u0000"+
		"\u0000\u0000\u01d9K\u0001\u0000\u0000\u0000\u01da\u01ec\u0005\u001f\u0000"+
		"\u0000\u01db\u01de\u0005,\u0000\u0000\u01dc\u01dd\u0005\r\u0000\u0000"+
		"\u01dd\u01df\u0005,\u0000\u0000\u01de\u01dc\u0001\u0000\u0000\u0000\u01de"+
		"\u01df\u0001\u0000\u0000\u0000\u01df\u01e8\u0001\u0000\u0000\u0000\u01e0"+
		"\u01e1\u0005+\u0000\u0000\u01e1\u01e4\u0005,\u0000\u0000\u01e2\u01e3\u0005"+
		"\r\u0000\u0000\u01e3\u01e5\u0005,\u0000\u0000\u01e4\u01e2\u0001\u0000"+
		"\u0000\u0000\u01e4\u01e5\u0001\u0000\u0000\u0000\u01e5\u01e7\u0001\u0000"+
		"\u0000\u0000\u01e6\u01e0\u0001\u0000\u0000\u0000\u01e7\u01ea\u0001\u0000"+
		"\u0000\u0000\u01e8\u01e6\u0001\u0000\u0000\u0000\u01e8\u01e9\u0001\u0000"+
		"\u0000\u0000\u01e9\u01ec\u0001\u0000\u0000\u0000\u01ea\u01e8\u0001\u0000"+
		"\u0000\u0000\u01eb\u01da\u0001\u0000\u0000\u0000\u01eb\u01db\u0001\u0000"+
		"\u0000\u0000\u01ecM\u0001\u0000\u0000\u00006PR_fmt\u0080\u008f\u0094\u00a0"+
		"\u00a7\u00ae\u00b4\u00b9\u00bd\u00cc\u00d3\u00de\u00eb\u00f0\u00f2\u0107"+
		"\u0110\u0118\u011e\u0129\u0132\u013b\u0141\u0146\u0151\u0154\u0159\u015c"+
		"\u0168\u016f\u017e\u0182\u0188\u018d\u0194\u01a1\u01a4\u01a6\u01ae\u01b6"+
		"\u01c2\u01c9\u01cf\u01d8\u01de\u01e4\u01e8\u01eb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}