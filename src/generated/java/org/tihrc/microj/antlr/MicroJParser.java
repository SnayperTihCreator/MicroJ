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
		LAMBDA=10, RETURN=11, IMPORT=12, FROM=13, AS=14, BREAK=15, CONTINUE=16, 
		TRY=17, EXCEPT=18, FINALLY=19, PASS=20, WITH=21, DEL=22, ASSERT=23, GLOBAL=24, 
		NONLOCAL=25, YIELD=26, AND=27, OR=28, NOT=29, DOT=30, COLON=31, LBRACKET=32, 
		RBRACKET=33, LBRACE=34, RBRACE=35, AT=36, PLUS=37, MINUS=38, STAR=39, 
		SLASH=40, POW=41, EQUAL=42, EQ_EQ=43, NE=44, LT=45, GT=46, LE=47, GE=48, 
		LPAREN=49, RPAREN=50, COMMA=51, NAME=52, NUMBER=53, FLOAT=54, IMAG=55, 
		STRING=56, NEWLINE=57, WS=58, COMMENT=59, INDENT=60, DEDENT=61;
	public static final int
		RULE_file = 0, RULE_statement = 1, RULE_simpleStatement = 2, RULE_funcDef = 3, 
		RULE_classDef = 4, RULE_baseList = 5, RULE_decorator = 6, RULE_paramList = 7, 
		RULE_param = 8, RULE_returnStatement = 9, RULE_globalStatement = 10, RULE_nonlocalStatement = 11, 
		RULE_yieldStatement = 12, RULE_breakStatement = 13, RULE_continueStatement = 14, 
		RULE_tryStatement = 15, RULE_withStatement = 16, RULE_delStatement = 17, 
		RULE_assertStatement = 18, RULE_exceptClause = 19, RULE_ifStatement = 20, 
		RULE_whileStatement = 21, RULE_forStatement = 22, RULE_block = 23, RULE_assignment = 24, 
		RULE_expr = 25, RULE_or_expr = 26, RULE_and_expr = 27, RULE_not_expr = 28, 
		RULE_comparison = 29, RULE_add_expr = 30, RULE_mul_expr = 31, RULE_unary_expr = 32, 
		RULE_power = 33, RULE_compareOp = 34, RULE_addOp = 35, RULE_mulOp = 36, 
		RULE_target = 37, RULE_targetList = 38, RULE_atom = 39, RULE_exprList = 40, 
		RULE_dictList = 41, RULE_dictItem = 42, RULE_argList = 43, RULE_arg = 44, 
		RULE_importStatement = 45, RULE_importNames = 46;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "statement", "simpleStatement", "funcDef", "classDef", "baseList", 
			"decorator", "paramList", "param", "returnStatement", "globalStatement", 
			"nonlocalStatement", "yieldStatement", "breakStatement", "continueStatement", 
			"tryStatement", "withStatement", "delStatement", "assertStatement", "exceptClause", 
			"ifStatement", "whileStatement", "forStatement", "block", "assignment", 
			"expr", "or_expr", "and_expr", "not_expr", "comparison", "add_expr", 
			"mul_expr", "unary_expr", "power", "compareOp", "addOp", "mulOp", "target", 
			"targetList", "atom", "exprList", "dictList", "dictItem", "argList", 
			"arg", "importStatement", "importNames"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'class'", "'for'", "'in'", "'not in'", "'if'", "'elif'", "'else'", 
			"'while'", "'def'", "'lambda'", "'return'", "'import'", "'from'", "'as'", 
			"'break'", "'continue'", "'try'", "'except'", "'finally'", "'pass'", 
			"'with'", "'del'", "'assert'", "'global'", "'nonlocal'", "'yield'", "'and'", 
			"'or'", "'not'", "'.'", "':'", "'['", "']'", "'{'", "'}'", "'@'", "'+'", 
			"'-'", "'*'", "'/'", "'**'", "'='", "'=='", "'!='", "'<'", "'>'", "'<='", 
			"'>='", "'('", "')'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "CLASS", "FOR", "IN", "NOT_IN", "IF", "ELIF", "ELSE", "WHILE", 
			"DEF", "LAMBDA", "RETURN", "IMPORT", "FROM", "AS", "BREAK", "CONTINUE", 
			"TRY", "EXCEPT", "FINALLY", "PASS", "WITH", "DEL", "ASSERT", "GLOBAL", 
			"NONLOCAL", "YIELD", "AND", "OR", "NOT", "DOT", "COLON", "LBRACKET", 
			"RBRACKET", "LBRACE", "RBRACE", "AT", "PLUS", "MINUS", "STAR", "SLASH", 
			"POW", "EQUAL", "EQ_EQ", "NE", "LT", "GT", "LE", "GE", "LPAREN", "RPAREN", 
			"COMMA", "NAME", "NUMBER", "FLOAT", "IMAG", "STRING", "NEWLINE", "WS", 
			"COMMENT", "INDENT", "DEDENT"
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
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 284290229659221798L) != 0)) {
				{
				setState(96);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEWLINE:
					{
					setState(94);
					match(NEWLINE);
					}
					break;
				case CLASS:
				case FOR:
				case IF:
				case WHILE:
				case DEF:
				case LAMBDA:
				case RETURN:
				case IMPORT:
				case FROM:
				case BREAK:
				case CONTINUE:
				case TRY:
				case PASS:
				case WITH:
				case DEL:
				case ASSERT:
				case GLOBAL:
				case NONLOCAL:
				case YIELD:
				case NOT:
				case LBRACKET:
				case LBRACE:
				case AT:
				case PLUS:
				case MINUS:
				case LPAREN:
				case NAME:
				case NUMBER:
				case FLOAT:
				case IMAG:
				case STRING:
					{
					setState(95);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(101);
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
		public WithStatementContext withStatement() {
			return getRuleContext(WithStatementContext.class,0);
		}
		public DelStatementContext delStatement() {
			return getRuleContext(DelStatementContext.class,0);
		}
		public AssertStatementContext assertStatement() {
			return getRuleContext(AssertStatementContext.class,0);
		}
		public GlobalStatementContext globalStatement() {
			return getRuleContext(GlobalStatementContext.class,0);
		}
		public NonlocalStatementContext nonlocalStatement() {
			return getRuleContext(NonlocalStatementContext.class,0);
		}
		public YieldStatementContext yieldStatement() {
			return getRuleContext(YieldStatementContext.class,0);
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
			setState(117);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LAMBDA:
			case RETURN:
			case BREAK:
			case CONTINUE:
			case PASS:
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
				setState(103);
				simpleStatement();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(104);
				ifStatement();
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 3);
				{
				setState(105);
				whileStatement();
				}
				break;
			case DEF:
			case AT:
				enterOuterAlt(_localctx, 4);
				{
				setState(106);
				funcDef();
				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(107);
				forStatement();
				}
				break;
			case IMPORT:
			case FROM:
				enterOuterAlt(_localctx, 6);
				{
				setState(108);
				importStatement();
				}
				break;
			case TRY:
				enterOuterAlt(_localctx, 7);
				{
				setState(109);
				tryStatement();
				}
				break;
			case CLASS:
				enterOuterAlt(_localctx, 8);
				{
				setState(110);
				classDef();
				}
				break;
			case WITH:
				enterOuterAlt(_localctx, 9);
				{
				setState(111);
				withStatement();
				}
				break;
			case DEL:
				enterOuterAlt(_localctx, 10);
				{
				setState(112);
				delStatement();
				}
				break;
			case ASSERT:
				enterOuterAlt(_localctx, 11);
				{
				setState(113);
				assertStatement();
				}
				break;
			case GLOBAL:
				enterOuterAlt(_localctx, 12);
				{
				setState(114);
				globalStatement();
				}
				break;
			case NONLOCAL:
				enterOuterAlt(_localctx, 13);
				{
				setState(115);
				nonlocalStatement();
				}
				break;
			case YIELD:
				enterOuterAlt(_localctx, 14);
				{
				setState(116);
				yieldStatement();
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
		public TerminalNode PASS() { return getToken(MicroJParser.PASS, 0); }
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
			setState(125);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(119);
				assignment();
				}
				break;
			case 2:
				{
				setState(120);
				expr();
				}
				break;
			case 3:
				{
				setState(121);
				returnStatement();
				}
				break;
			case 4:
				{
				setState(122);
				breakStatement();
				}
				break;
			case 5:
				{
				setState(123);
				continueStatement();
				}
				break;
			case 6:
				{
				setState(124);
				match(PASS);
				}
				break;
			}
			setState(127);
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
			setState(132);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AT) {
				{
				{
				setState(129);
				decorator();
				}
				}
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(135);
			match(DEF);
			setState(136);
			match(NAME);
			setState(137);
			match(LPAREN);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4506348406439936L) != 0)) {
				{
				setState(138);
				paramList();
				}
			}

			setState(141);
			match(RPAREN);
			setState(142);
			match(COLON);
			setState(143);
			match(NEWLINE);
			setState(144);
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
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public TerminalNode COLON() { return getToken(MicroJParser.COLON, 0); }
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public BaseListContext baseList() {
			return getRuleContext(BaseListContext.class,0);
		}
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
			setState(146);
			match(CLASS);
			setState(147);
			match(NAME);
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(148);
				match(LPAREN);
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NAME) {
					{
					setState(149);
					baseList();
					}
				}

				setState(152);
				match(RPAREN);
				}
			}

			setState(155);
			match(COLON);
			setState(156);
			match(NEWLINE);
			setState(157);
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
	public static class BaseListContext extends ParserRuleContext {
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public BaseListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterBaseList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitBaseList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitBaseList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseListContext baseList() throws RecognitionException {
		BaseListContext _localctx = new BaseListContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_baseList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			match(NAME);
			setState(164);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(160);
				match(COMMA);
				setState(161);
				match(NAME);
				}
				}
				setState(166);
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
		enterRule(_localctx, 12, RULE_decorator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			match(AT);
			setState(168);
			expr();
			setState(169);
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
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
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
		enterRule(_localctx, 14, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			param();
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(172);
				match(COMMA);
				setState(173);
				param();
				}
				}
				setState(178);
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
	public static class ParamContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public TerminalNode EQUAL() { return getToken(MicroJParser.EQUAL, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode STAR() { return getToken(MicroJParser.STAR, 0); }
		public TerminalNode POW() { return getToken(MicroJParser.POW, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_param);
		int _la;
		try {
			setState(188);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(179);
				match(NAME);
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EQUAL) {
					{
					setState(180);
					match(EQUAL);
					setState(181);
					expr();
					}
				}

				}
				break;
			case STAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(184);
				match(STAR);
				setState(185);
				match(NAME);
				}
				break;
			case POW:
				enterOuterAlt(_localctx, 3);
				{
				setState(186);
				match(POW);
				setState(187);
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
		enterRule(_localctx, 18, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(RETURN);
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140174972730475520L) != 0)) {
				{
				setState(191);
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
	public static class GlobalStatementContext extends ParserRuleContext {
		public TerminalNode GLOBAL() { return getToken(MicroJParser.GLOBAL, 0); }
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public GlobalStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterGlobalStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitGlobalStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitGlobalStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalStatementContext globalStatement() throws RecognitionException {
		GlobalStatementContext _localctx = new GlobalStatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_globalStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(GLOBAL);
			setState(195);
			match(NAME);
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(196);
				match(COMMA);
				setState(197);
				match(NAME);
				}
				}
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(203);
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
	public static class NonlocalStatementContext extends ParserRuleContext {
		public TerminalNode NONLOCAL() { return getToken(MicroJParser.NONLOCAL, 0); }
		public List<TerminalNode> NAME() { return getTokens(MicroJParser.NAME); }
		public TerminalNode NAME(int i) {
			return getToken(MicroJParser.NAME, i);
		}
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MicroJParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MicroJParser.COMMA, i);
		}
		public NonlocalStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonlocalStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterNonlocalStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitNonlocalStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitNonlocalStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonlocalStatementContext nonlocalStatement() throws RecognitionException {
		NonlocalStatementContext _localctx = new NonlocalStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_nonlocalStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(NONLOCAL);
			setState(206);
			match(NAME);
			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(207);
				match(COMMA);
				setState(208);
				match(NAME);
				}
				}
				setState(213);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(214);
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
	public static class YieldStatementContext extends ParserRuleContext {
		public TerminalNode YIELD() { return getToken(MicroJParser.YIELD, 0); }
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public YieldStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yieldStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterYieldStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitYieldStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitYieldStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final YieldStatementContext yieldStatement() throws RecognitionException {
		YieldStatementContext _localctx = new YieldStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_yieldStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(YIELD);
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140174972730475520L) != 0)) {
				{
				setState(217);
				expr();
				}
			}

			setState(220);
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
		enterRule(_localctx, 26, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
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
		enterRule(_localctx, 28, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
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
		enterRule(_localctx, 30, RULE_tryStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			match(TRY);
			setState(227);
			match(COLON);
			setState(228);
			match(NEWLINE);
			setState(229);
			block();
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EXCEPT) {
				{
				{
				setState(230);
				match(EXCEPT);
				setState(232);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS || _la==NAME) {
					{
					setState(231);
					exceptClause();
					}
				}

				setState(234);
				match(COLON);
				setState(235);
				match(NEWLINE);
				setState(236);
				block();
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(242);
				match(ELSE);
				setState(243);
				match(COLON);
				setState(244);
				match(NEWLINE);
				setState(245);
				block();
				}
			}

			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINALLY) {
				{
				setState(248);
				match(FINALLY);
				setState(249);
				match(COLON);
				setState(250);
				match(NEWLINE);
				setState(251);
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
	public static class WithStatementContext extends ParserRuleContext {
		public TerminalNode WITH() { return getToken(MicroJParser.WITH, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode COLON() { return getToken(MicroJParser.COLON, 0); }
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode AS() { return getToken(MicroJParser.AS, 0); }
		public TerminalNode NAME() { return getToken(MicroJParser.NAME, 0); }
		public WithStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterWithStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitWithStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitWithStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WithStatementContext withStatement() throws RecognitionException {
		WithStatementContext _localctx = new WithStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_withStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			match(WITH);
			setState(255);
			expr();
			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(256);
				match(AS);
				setState(257);
				match(NAME);
				}
			}

			setState(260);
			match(COLON);
			setState(261);
			match(NEWLINE);
			setState(262);
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
	public static class DelStatementContext extends ParserRuleContext {
		public TerminalNode DEL() { return getToken(MicroJParser.DEL, 0); }
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public DelStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterDelStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitDelStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitDelStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DelStatementContext delStatement() throws RecognitionException {
		DelStatementContext _localctx = new DelStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_delStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(DEL);
			setState(265);
			targetList();
			setState(266);
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
	public static class AssertStatementContext extends ParserRuleContext {
		public TerminalNode ASSERT() { return getToken(MicroJParser.ASSERT, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode NEWLINE() { return getToken(MicroJParser.NEWLINE, 0); }
		public TerminalNode COMMA() { return getToken(MicroJParser.COMMA, 0); }
		public AssertStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterAssertStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitAssertStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitAssertStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssertStatementContext assertStatement() throws RecognitionException {
		AssertStatementContext _localctx = new AssertStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_assertStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			match(ASSERT);
			setState(269);
			expr();
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(270);
				match(COMMA);
				setState(271);
				expr();
				}
			}

			setState(274);
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
		enterRule(_localctx, 38, RULE_exceptClause);
		int _la;
		try {
			setState(283);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NAME:
				enterOuterAlt(_localctx, 1);
				{
				setState(276);
				match(NAME);
				setState(279);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(277);
					match(AS);
					setState(278);
					match(NAME);
					}
				}

				}
				break;
			case AS:
				enterOuterAlt(_localctx, 2);
				{
				setState(281);
				match(AS);
				setState(282);
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
		enterRule(_localctx, 40, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(IF);
			setState(286);
			expr();
			setState(287);
			match(COLON);
			setState(288);
			match(NEWLINE);
			setState(289);
			block();
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELIF) {
				{
				{
				setState(290);
				match(ELIF);
				setState(291);
				expr();
				setState(292);
				match(COLON);
				setState(293);
				match(NEWLINE);
				setState(294);
				block();
				}
				}
				setState(300);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(305);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(301);
				match(ELSE);
				setState(302);
				match(COLON);
				setState(303);
				match(NEWLINE);
				setState(304);
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
		enterRule(_localctx, 42, RULE_whileStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(WHILE);
			setState(308);
			expr();
			setState(309);
			match(COLON);
			setState(310);
			match(NEWLINE);
			setState(311);
			block();
			setState(316);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(312);
				match(ELSE);
				setState(313);
				match(COLON);
				setState(314);
				match(NEWLINE);
				setState(315);
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
		enterRule(_localctx, 44, RULE_forStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(318);
			match(FOR);
			setState(319);
			targetList();
			setState(320);
			match(IN);
			setState(321);
			expr();
			setState(322);
			match(COLON);
			setState(323);
			match(NEWLINE);
			setState(324);
			block();
			setState(329);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(325);
				match(ELSE);
				setState(326);
				match(COLON);
				setState(327);
				match(NEWLINE);
				setState(328);
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
		enterRule(_localctx, 46, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331);
			match(INDENT);
			setState(334); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(334);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case NEWLINE:
					{
					setState(332);
					match(NEWLINE);
					}
					break;
				case CLASS:
				case FOR:
				case IF:
				case WHILE:
				case DEF:
				case LAMBDA:
				case RETURN:
				case IMPORT:
				case FROM:
				case BREAK:
				case CONTINUE:
				case TRY:
				case PASS:
				case WITH:
				case DEL:
				case ASSERT:
				case GLOBAL:
				case NONLOCAL:
				case YIELD:
				case NOT:
				case LBRACKET:
				case LBRACE:
				case AT:
				case PLUS:
				case MINUS:
				case LPAREN:
				case NAME:
				case NUMBER:
				case FLOAT:
				case IMAG:
				case STRING:
					{
					setState(333);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(336); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 284290229659221798L) != 0) );
			setState(338);
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
	public static class GeneralAssignListContext extends AssignmentContext {
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode EQUAL() { return getToken(MicroJParser.EQUAL, 0); }
		public ExprListContext exprList() {
			return getRuleContext(ExprListContext.class,0);
		}
		public GeneralAssignListContext(AssignmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterGeneralAssignList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitGeneralAssignList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitGeneralAssignList(this);
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
		enterRule(_localctx, 48, RULE_assignment);
		try {
			setState(361);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				_localctx = new SubscriptAssignContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(340);
				atom(0);
				setState(341);
				match(LBRACKET);
				setState(342);
				expr();
				setState(343);
				match(RBRACKET);
				setState(344);
				match(EQUAL);
				setState(345);
				expr();
				}
				break;
			case 2:
				_localctx = new AttrAssignContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(347);
				atom(0);
				setState(348);
				match(DOT);
				setState(349);
				match(NAME);
				setState(350);
				match(EQUAL);
				setState(351);
				expr();
				}
				break;
			case 3:
				_localctx = new GeneralAssignContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(353);
				target();
				setState(354);
				match(EQUAL);
				setState(355);
				exprList();
				}
				break;
			case 4:
				_localctx = new GeneralAssignListContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(357);
				targetList();
				setState(358);
				match(EQUAL);
				setState(359);
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
		enterRule(_localctx, 50, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
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
		enterRule(_localctx, 52, RULE_or_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			and_expr();
			setState(370);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(366);
					match(OR);
					setState(367);
					and_expr();
					}
					} 
				}
				setState(372);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
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
		enterRule(_localctx, 54, RULE_and_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(373);
			not_expr();
			setState(378);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(374);
					match(AND);
					setState(375);
					not_expr();
					}
					} 
				}
				setState(380);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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
		enterRule(_localctx, 56, RULE_not_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NOT) {
				{
				{
				setState(381);
				match(NOT);
				}
				}
				setState(386);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(387);
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
		enterRule(_localctx, 58, RULE_comparison);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(389);
			add_expr();
			setState(395);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(390);
					compareOp();
					setState(391);
					add_expr();
					}
					} 
				}
				setState(397);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
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
		enterRule(_localctx, 60, RULE_add_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(398);
			mul_expr();
			setState(404);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,35,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(399);
					addOp();
					setState(400);
					mul_expr();
					}
					} 
				}
				setState(406);
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
		enterRule(_localctx, 62, RULE_mul_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(407);
			unary_expr();
			setState(413);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(408);
					mulOp();
					setState(409);
					unary_expr();
					}
					} 
				}
				setState(415);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
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
		enterRule(_localctx, 64, RULE_unary_expr);
		int _la;
		try {
			setState(419);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(416);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(417);
				unary_expr();
				}
				break;
			case LAMBDA:
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
				setState(418);
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
		enterRule(_localctx, 66, RULE_power);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(421);
			atom(0);
			setState(424);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(422);
				match(POW);
				setState(423);
				unary_expr();
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
		enterRule(_localctx, 68, RULE_compareOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 554153860399128L) != 0)) ) {
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
		enterRule(_localctx, 70, RULE_addOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(428);
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
		enterRule(_localctx, 72, RULE_mulOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
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
		enterRule(_localctx, 74, RULE_target);
		int _la;
		try {
			setState(458);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(432);
				match(NAME);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(433);
				match(LPAREN);
				setState(435);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140174559876744192L) != 0)) {
					{
					setState(434);
					targetList();
					}
				}

				setState(438);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(437);
					match(COMMA);
					}
				}

				setState(440);
				match(RPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(441);
				match(LBRACKET);
				setState(443);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140174559876744192L) != 0)) {
					{
					setState(442);
					targetList();
					}
				}

				setState(446);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(445);
					match(COMMA);
					}
				}

				setState(448);
				match(RBRACKET);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(449);
				atom(0);
				setState(450);
				match(LBRACKET);
				setState(451);
				expr();
				setState(452);
				match(RBRACKET);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(454);
				atom(0);
				setState(455);
				match(DOT);
				setState(456);
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
		enterRule(_localctx, 76, RULE_targetList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(460);
			target();
			setState(465);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(461);
					match(COMMA);
					setState(462);
					target();
					}
					} 
				}
				setState(467);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,44,_ctx);
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
	public static class SetComprehensionContext extends AtomContext {
		public TerminalNode LBRACE() { return getToken(MicroJParser.LBRACE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode FOR() { return getToken(MicroJParser.FOR, 0); }
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode IN() { return getToken(MicroJParser.IN, 0); }
		public TerminalNode RBRACE() { return getToken(MicroJParser.RBRACE, 0); }
		public List<TerminalNode> IF() { return getTokens(MicroJParser.IF); }
		public TerminalNode IF(int i) {
			return getToken(MicroJParser.IF, i);
		}
		public SetComprehensionContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterSetComprehension(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitSetComprehension(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitSetComprehension(this);
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
	public static class GenExpContext extends AtomContext {
		public TerminalNode LPAREN() { return getToken(MicroJParser.LPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode FOR() { return getToken(MicroJParser.FOR, 0); }
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode IN() { return getToken(MicroJParser.IN, 0); }
		public TerminalNode RPAREN() { return getToken(MicroJParser.RPAREN, 0); }
		public List<TerminalNode> IF() { return getTokens(MicroJParser.IF); }
		public TerminalNode IF(int i) {
			return getToken(MicroJParser.IF, i);
		}
		public GenExpContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterGenExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitGenExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitGenExp(this);
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
	public static class DictComprehensionContext extends AtomContext {
		public TerminalNode LBRACE() { return getToken(MicroJParser.LBRACE, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode COLON() { return getToken(MicroJParser.COLON, 0); }
		public TerminalNode FOR() { return getToken(MicroJParser.FOR, 0); }
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode IN() { return getToken(MicroJParser.IN, 0); }
		public TerminalNode RBRACE() { return getToken(MicroJParser.RBRACE, 0); }
		public List<TerminalNode> IF() { return getTokens(MicroJParser.IF); }
		public TerminalNode IF(int i) {
			return getToken(MicroJParser.IF, i);
		}
		public DictComprehensionContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterDictComprehension(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitDictComprehension(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitDictComprehension(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListComprehensionContext extends AtomContext {
		public TerminalNode LBRACKET() { return getToken(MicroJParser.LBRACKET, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode FOR() { return getToken(MicroJParser.FOR, 0); }
		public TargetListContext targetList() {
			return getRuleContext(TargetListContext.class,0);
		}
		public TerminalNode IN() { return getToken(MicroJParser.IN, 0); }
		public TerminalNode RBRACKET() { return getToken(MicroJParser.RBRACKET, 0); }
		public List<TerminalNode> IF() { return getTokens(MicroJParser.IF); }
		public TerminalNode IF(int i) {
			return getToken(MicroJParser.IF, i);
		}
		public ListComprehensionContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterListComprehension(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitListComprehension(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitListComprehension(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LambdaContext extends AtomContext {
		public TerminalNode LAMBDA() { return getToken(MicroJParser.LAMBDA, 0); }
		public TerminalNode COLON() { return getToken(MicroJParser.COLON, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public LambdaContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).enterLambda(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MicroJListener ) ((MicroJListener)listener).exitLambda(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MicroJVisitor ) return ((MicroJVisitor<? extends T>)visitor).visitLambda(this);
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

	public final AtomContext atom() throws RecognitionException {
		return atom(0);
	}

	private AtomContext atom(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AtomContext _localctx = new AtomContext(_ctx, _parentState);
		AtomContext _prevctx = _localctx;
		int _startState = 78;
		enterRecursionRule(_localctx, 78, RULE_atom, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(570);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				_localctx = new LambdaContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(469);
				match(LAMBDA);
				setState(471);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4506348406439936L) != 0)) {
					{
					setState(470);
					paramList();
					}
				}

				setState(473);
				match(COLON);
				setState(474);
				expr();
				}
				break;
			case 2:
				{
				_localctx = new NumberContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(475);
				match(NUMBER);
				}
				break;
			case 3:
				{
				_localctx = new FloatLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(476);
				match(FLOAT);
				}
				break;
			case 4:
				{
				_localctx = new ImagLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(477);
				match(IMAG);
				}
				break;
			case 5:
				{
				_localctx = new StringLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(478);
				match(STRING);
				}
				break;
			case 6:
				{
				_localctx = new VariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(479);
				match(NAME);
				}
				break;
			case 7:
				{
				_localctx = new TupleLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(480);
				match(LPAREN);
				setState(481);
				expr();
				setState(486);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(482);
						match(COMMA);
						setState(483);
						expr();
						}
						} 
					}
					setState(488);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,46,_ctx);
				}
				setState(490);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(489);
					match(COMMA);
					}
				}

				setState(492);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new ListComprehensionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(494);
				match(LBRACKET);
				setState(495);
				expr();
				setState(496);
				match(FOR);
				setState(497);
				targetList();
				setState(498);
				match(IN);
				setState(499);
				expr();
				setState(504);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==IF) {
					{
					{
					setState(500);
					match(IF);
					setState(501);
					expr();
					}
					}
					setState(506);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(507);
				match(RBRACKET);
				}
				break;
			case 9:
				{
				_localctx = new DictComprehensionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(509);
				match(LBRACE);
				setState(510);
				expr();
				setState(511);
				match(COLON);
				setState(512);
				expr();
				setState(513);
				match(FOR);
				setState(514);
				targetList();
				setState(515);
				match(IN);
				setState(516);
				expr();
				setState(521);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==IF) {
					{
					{
					setState(517);
					match(IF);
					setState(518);
					expr();
					}
					}
					setState(523);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(524);
				match(RBRACE);
				}
				break;
			case 10:
				{
				_localctx = new SetComprehensionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(526);
				match(LBRACE);
				setState(527);
				expr();
				setState(528);
				match(FOR);
				setState(529);
				targetList();
				setState(530);
				match(IN);
				setState(531);
				expr();
				setState(536);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==IF) {
					{
					{
					setState(532);
					match(IF);
					setState(533);
					expr();
					}
					}
					setState(538);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(539);
				match(RBRACE);
				}
				break;
			case 11:
				{
				_localctx = new GenExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(541);
				match(LPAREN);
				setState(542);
				expr();
				setState(543);
				match(FOR);
				setState(544);
				targetList();
				setState(545);
				match(IN);
				setState(546);
				expr();
				setState(551);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==IF) {
					{
					{
					setState(547);
					match(IF);
					setState(548);
					expr();
					}
					}
					setState(553);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(554);
				match(RPAREN);
				}
				break;
			case 12:
				{
				_localctx = new ListLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(556);
				match(LBRACKET);
				setState(558);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140174972730475520L) != 0)) {
					{
					setState(557);
					exprList();
					}
				}

				setState(560);
				match(RBRACKET);
				}
				break;
			case 13:
				{
				_localctx = new DictLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(561);
				match(LBRACE);
				setState(563);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140174972730475520L) != 0)) {
					{
					setState(562);
					dictList();
					}
				}

				setState(565);
				match(RBRACE);
				}
				break;
			case 14:
				{
				_localctx = new ParenAtomContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(566);
				match(LPAREN);
				setState(567);
				expr();
				setState(568);
				match(RPAREN);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(588);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(586);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
					case 1:
						{
						_localctx = new SubscriptAtomContext(new AtomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_atom);
						setState(572);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(573);
						match(LBRACKET);
						setState(574);
						expr();
						setState(575);
						match(RBRACKET);
						}
						break;
					case 2:
						{
						_localctx = new GetAttrAtomContext(new AtomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_atom);
						setState(577);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(578);
						match(DOT);
						setState(579);
						match(NAME);
						}
						break;
					case 3:
						{
						_localctx = new CallAtomContext(new AtomContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_atom);
						setState(580);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(581);
						match(LPAREN);
						setState(583);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 140174972730475520L) != 0)) {
							{
							setState(582);
							argList();
							}
						}

						setState(585);
						match(RPAREN);
						}
						break;
					}
					} 
				}
				setState(590);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
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
		enterRule(_localctx, 80, RULE_exprList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(591);
			expr();
			setState(596);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(592);
				match(COMMA);
				setState(593);
				expr();
				}
				}
				setState(598);
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
		enterRule(_localctx, 82, RULE_dictList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(599);
			dictItem();
			setState(604);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(600);
				match(COMMA);
				setState(601);
				dictItem();
				}
				}
				setState(606);
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
		enterRule(_localctx, 84, RULE_dictItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(607);
			expr();
			setState(608);
			match(COLON);
			setState(609);
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
		enterRule(_localctx, 86, RULE_argList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(611);
			arg();
			setState(616);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(612);
				match(COMMA);
				setState(613);
				arg();
				}
				}
				setState(618);
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
		enterRule(_localctx, 88, RULE_arg);
		try {
			setState(623);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,61,_ctx) ) {
			case 1:
				_localctx = new KwArgContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(619);
				match(NAME);
				setState(620);
				match(EQUAL);
				setState(621);
				expr();
				}
				break;
			case 2:
				_localctx = new PosArgContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(622);
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
		enterRule(_localctx, 90, RULE_importStatement);
		int _la;
		try {
			setState(638);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IMPORT:
				_localctx = new ImportModuleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(625);
				match(IMPORT);
				setState(626);
				match(NAME);
				setState(629);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(627);
					match(AS);
					setState(628);
					match(NAME);
					}
				}

				setState(631);
				match(NEWLINE);
				}
				break;
			case FROM:
				_localctx = new ImportFromContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(632);
				match(FROM);
				setState(633);
				match(NAME);
				setState(634);
				match(IMPORT);
				setState(635);
				importNames();
				setState(636);
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
		enterRule(_localctx, 92, RULE_importNames);
		int _la;
		try {
			setState(657);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STAR:
				enterOuterAlt(_localctx, 1);
				{
				setState(640);
				match(STAR);
				}
				break;
			case NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(641);
				match(NAME);
				setState(644);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==AS) {
					{
					setState(642);
					match(AS);
					setState(643);
					match(NAME);
					}
				}

				setState(654);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(646);
					match(COMMA);
					setState(647);
					match(NAME);
					setState(650);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==AS) {
						{
						setState(648);
						match(AS);
						setState(649);
						match(NAME);
						}
					}

					}
					}
					setState(656);
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
		case 39:
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
		"\u0004\u0001=\u0294\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
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
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007,\u0002"+
		"-\u0007-\u0002.\u0007.\u0001\u0000\u0001\u0000\u0005\u0000a\b\u0000\n"+
		"\u0000\f\u0000d\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001v\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u0002~\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0005\u0003\u0083\b\u0003\n\u0003\f\u0003\u0086\t\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003\u008c\b\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u0097\b\u0004\u0001\u0004\u0003"+
		"\u0004\u009a\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u00a3\b\u0005\n\u0005\f\u0005"+
		"\u00a6\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007\u00af\b\u0007\n\u0007\f\u0007\u00b2"+
		"\t\u0007\u0001\b\u0001\b\u0001\b\u0003\b\u00b7\b\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0003\b\u00bd\b\b\u0001\t\u0001\t\u0003\t\u00c1\b\t\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0005\n\u00c7\b\n\n\n\f\n\u00ca\t\n\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00d2\b"+
		"\u000b\n\u000b\f\u000b\u00d5\t\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0003\f\u00db\b\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0003\u000f\u00e9\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005"+
		"\u000f\u00ee\b\u000f\n\u000f\f\u000f\u00f1\t\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0003\u000f\u00f7\b\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0003\u000f\u00fd\b\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0003\u0010\u0103\b\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u0111\b\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013"+
		"\u0118\b\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u011c\b\u0013\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u0129"+
		"\b\u0014\n\u0014\f\u0014\u012c\t\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0003\u0014\u0132\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0003\u0015\u013d\b\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0003\u0016\u014a\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0004\u0017\u014f\b\u0017\u000b\u0017\f\u0017\u0150\u0001\u0017\u0001"+
		"\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u016a\b\u0018\u0001"+
		"\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0005\u001a\u0171"+
		"\b\u001a\n\u001a\f\u001a\u0174\t\u001a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0005\u001b\u0179\b\u001b\n\u001b\f\u001b\u017c\t\u001b\u0001\u001c\u0005"+
		"\u001c\u017f\b\u001c\n\u001c\f\u001c\u0182\t\u001c\u0001\u001c\u0001\u001c"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d\u0005\u001d\u018a\b\u001d"+
		"\n\u001d\f\u001d\u018d\t\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001"+
		"\u001e\u0005\u001e\u0193\b\u001e\n\u001e\f\u001e\u0196\t\u001e\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f\u019c\b\u001f\n\u001f"+
		"\f\u001f\u019f\t\u001f\u0001 \u0001 \u0001 \u0003 \u01a4\b \u0001!\u0001"+
		"!\u0001!\u0003!\u01a9\b!\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0001$\u0001"+
		"%\u0001%\u0001%\u0003%\u01b4\b%\u0001%\u0003%\u01b7\b%\u0001%\u0001%\u0001"+
		"%\u0003%\u01bc\b%\u0001%\u0003%\u01bf\b%\u0001%\u0001%\u0001%\u0001%\u0001"+
		"%\u0001%\u0001%\u0001%\u0001%\u0001%\u0003%\u01cb\b%\u0001&\u0001&\u0001"+
		"&\u0005&\u01d0\b&\n&\f&\u01d3\t&\u0001\'\u0001\'\u0001\'\u0003\'\u01d8"+
		"\b\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0005\'\u01e5\b\'\n\'\f\'\u01e8\t\'\u0001\'\u0003\'"+
		"\u01eb\b\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0005\'\u01f7\b\'\n\'\f\'\u01fa\t\'\u0001\'\u0001\'"+
		"\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0005\'\u0208\b\'\n\'\f\'\u020b\t\'\u0001\'\u0001\'\u0001\'"+
		"\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0005\'\u0217"+
		"\b\'\n\'\f\'\u021a\t\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'"+
		"\u0001\'\u0001\'\u0001\'\u0001\'\u0005\'\u0226\b\'\n\'\f\'\u0229\t\'\u0001"+
		"\'\u0001\'\u0001\'\u0001\'\u0003\'\u022f\b\'\u0001\'\u0001\'\u0001\'\u0003"+
		"\'\u0234\b\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0003\'\u023b\b\'"+
		"\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001"+
		"\'\u0001\'\u0001\'\u0003\'\u0248\b\'\u0001\'\u0005\'\u024b\b\'\n\'\f\'"+
		"\u024e\t\'\u0001(\u0001(\u0001(\u0005(\u0253\b(\n(\f(\u0256\t(\u0001)"+
		"\u0001)\u0001)\u0005)\u025b\b)\n)\f)\u025e\t)\u0001*\u0001*\u0001*\u0001"+
		"*\u0001+\u0001+\u0001+\u0005+\u0267\b+\n+\f+\u026a\t+\u0001,\u0001,\u0001"+
		",\u0001,\u0003,\u0270\b,\u0001-\u0001-\u0001-\u0001-\u0003-\u0276\b-\u0001"+
		"-\u0001-\u0001-\u0001-\u0001-\u0001-\u0001-\u0003-\u027f\b-\u0001.\u0001"+
		".\u0001.\u0001.\u0003.\u0285\b.\u0001.\u0001.\u0001.\u0001.\u0003.\u028b"+
		"\b.\u0005.\u028d\b.\n.\f.\u0290\t.\u0003.\u0292\b.\u0001.\u0000\u0001"+
		"N/\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a"+
		"\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\\u0000\u0003\u0001\u0000"+
		"%&\u0002\u0000\u0003\u0004+0\u0001\u0000\'(\u02cb\u0000b\u0001\u0000\u0000"+
		"\u0000\u0002u\u0001\u0000\u0000\u0000\u0004}\u0001\u0000\u0000\u0000\u0006"+
		"\u0084\u0001\u0000\u0000\u0000\b\u0092\u0001\u0000\u0000\u0000\n\u009f"+
		"\u0001\u0000\u0000\u0000\f\u00a7\u0001\u0000\u0000\u0000\u000e\u00ab\u0001"+
		"\u0000\u0000\u0000\u0010\u00bc\u0001\u0000\u0000\u0000\u0012\u00be\u0001"+
		"\u0000\u0000\u0000\u0014\u00c2\u0001\u0000\u0000\u0000\u0016\u00cd\u0001"+
		"\u0000\u0000\u0000\u0018\u00d8\u0001\u0000\u0000\u0000\u001a\u00de\u0001"+
		"\u0000\u0000\u0000\u001c\u00e0\u0001\u0000\u0000\u0000\u001e\u00e2\u0001"+
		"\u0000\u0000\u0000 \u00fe\u0001\u0000\u0000\u0000\"\u0108\u0001\u0000"+
		"\u0000\u0000$\u010c\u0001\u0000\u0000\u0000&\u011b\u0001\u0000\u0000\u0000"+
		"(\u011d\u0001\u0000\u0000\u0000*\u0133\u0001\u0000\u0000\u0000,\u013e"+
		"\u0001\u0000\u0000\u0000.\u014b\u0001\u0000\u0000\u00000\u0169\u0001\u0000"+
		"\u0000\u00002\u016b\u0001\u0000\u0000\u00004\u016d\u0001\u0000\u0000\u0000"+
		"6\u0175\u0001\u0000\u0000\u00008\u0180\u0001\u0000\u0000\u0000:\u0185"+
		"\u0001\u0000\u0000\u0000<\u018e\u0001\u0000\u0000\u0000>\u0197\u0001\u0000"+
		"\u0000\u0000@\u01a3\u0001\u0000\u0000\u0000B\u01a5\u0001\u0000\u0000\u0000"+
		"D\u01aa\u0001\u0000\u0000\u0000F\u01ac\u0001\u0000\u0000\u0000H\u01ae"+
		"\u0001\u0000\u0000\u0000J\u01ca\u0001\u0000\u0000\u0000L\u01cc\u0001\u0000"+
		"\u0000\u0000N\u023a\u0001\u0000\u0000\u0000P\u024f\u0001\u0000\u0000\u0000"+
		"R\u0257\u0001\u0000\u0000\u0000T\u025f\u0001\u0000\u0000\u0000V\u0263"+
		"\u0001\u0000\u0000\u0000X\u026f\u0001\u0000\u0000\u0000Z\u027e\u0001\u0000"+
		"\u0000\u0000\\\u0291\u0001\u0000\u0000\u0000^a\u00059\u0000\u0000_a\u0003"+
		"\u0002\u0001\u0000`^\u0001\u0000\u0000\u0000`_\u0001\u0000\u0000\u0000"+
		"ad\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000"+
		"\u0000ce\u0001\u0000\u0000\u0000db\u0001\u0000\u0000\u0000ef\u0005\u0000"+
		"\u0000\u0001f\u0001\u0001\u0000\u0000\u0000gv\u0003\u0004\u0002\u0000"+
		"hv\u0003(\u0014\u0000iv\u0003*\u0015\u0000jv\u0003\u0006\u0003\u0000k"+
		"v\u0003,\u0016\u0000lv\u0003Z-\u0000mv\u0003\u001e\u000f\u0000nv\u0003"+
		"\b\u0004\u0000ov\u0003 \u0010\u0000pv\u0003\"\u0011\u0000qv\u0003$\u0012"+
		"\u0000rv\u0003\u0014\n\u0000sv\u0003\u0016\u000b\u0000tv\u0003\u0018\f"+
		"\u0000ug\u0001\u0000\u0000\u0000uh\u0001\u0000\u0000\u0000ui\u0001\u0000"+
		"\u0000\u0000uj\u0001\u0000\u0000\u0000uk\u0001\u0000\u0000\u0000ul\u0001"+
		"\u0000\u0000\u0000um\u0001\u0000\u0000\u0000un\u0001\u0000\u0000\u0000"+
		"uo\u0001\u0000\u0000\u0000up\u0001\u0000\u0000\u0000uq\u0001\u0000\u0000"+
		"\u0000ur\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000ut\u0001\u0000"+
		"\u0000\u0000v\u0003\u0001\u0000\u0000\u0000w~\u00030\u0018\u0000x~\u0003"+
		"2\u0019\u0000y~\u0003\u0012\t\u0000z~\u0003\u001a\r\u0000{~\u0003\u001c"+
		"\u000e\u0000|~\u0005\u0014\u0000\u0000}w\u0001\u0000\u0000\u0000}x\u0001"+
		"\u0000\u0000\u0000}y\u0001\u0000\u0000\u0000}z\u0001\u0000\u0000\u0000"+
		"}{\u0001\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000~\u007f\u0001\u0000"+
		"\u0000\u0000\u007f\u0080\u00059\u0000\u0000\u0080\u0005\u0001\u0000\u0000"+
		"\u0000\u0081\u0083\u0003\f\u0006\u0000\u0082\u0081\u0001\u0000\u0000\u0000"+
		"\u0083\u0086\u0001\u0000\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000"+
		"\u0084\u0085\u0001\u0000\u0000\u0000\u0085\u0087\u0001\u0000\u0000\u0000"+
		"\u0086\u0084\u0001\u0000\u0000\u0000\u0087\u0088\u0005\t\u0000\u0000\u0088"+
		"\u0089\u00054\u0000\u0000\u0089\u008b\u00051\u0000\u0000\u008a\u008c\u0003"+
		"\u000e\u0007\u0000\u008b\u008a\u0001\u0000\u0000\u0000\u008b\u008c\u0001"+
		"\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008e\u0005"+
		"2\u0000\u0000\u008e\u008f\u0005\u001f\u0000\u0000\u008f\u0090\u00059\u0000"+
		"\u0000\u0090\u0091\u0003.\u0017\u0000\u0091\u0007\u0001\u0000\u0000\u0000"+
		"\u0092\u0093\u0005\u0001\u0000\u0000\u0093\u0099\u00054\u0000\u0000\u0094"+
		"\u0096\u00051\u0000\u0000\u0095\u0097\u0003\n\u0005\u0000\u0096\u0095"+
		"\u0001\u0000\u0000\u0000\u0096\u0097\u0001\u0000\u0000\u0000\u0097\u0098"+
		"\u0001\u0000\u0000\u0000\u0098\u009a\u00052\u0000\u0000\u0099\u0094\u0001"+
		"\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u009b\u0001"+
		"\u0000\u0000\u0000\u009b\u009c\u0005\u001f\u0000\u0000\u009c\u009d\u0005"+
		"9\u0000\u0000\u009d\u009e\u0003.\u0017\u0000\u009e\t\u0001\u0000\u0000"+
		"\u0000\u009f\u00a4\u00054\u0000\u0000\u00a0\u00a1\u00053\u0000\u0000\u00a1"+
		"\u00a3\u00054\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000\u00a3\u00a6"+
		"\u0001\u0000\u0000\u0000\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a4\u00a5"+
		"\u0001\u0000\u0000\u0000\u00a5\u000b\u0001\u0000\u0000\u0000\u00a6\u00a4"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a8\u0005$\u0000\u0000\u00a8\u00a9\u0003"+
		"2\u0019\u0000\u00a9\u00aa\u00059\u0000\u0000\u00aa\r\u0001\u0000\u0000"+
		"\u0000\u00ab\u00b0\u0003\u0010\b\u0000\u00ac\u00ad\u00053\u0000\u0000"+
		"\u00ad\u00af\u0003\u0010\b\u0000\u00ae\u00ac\u0001\u0000\u0000\u0000\u00af"+
		"\u00b2\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b0"+
		"\u00b1\u0001\u0000\u0000\u0000\u00b1\u000f\u0001\u0000\u0000\u0000\u00b2"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b3\u00b6\u00054\u0000\u0000\u00b4\u00b5"+
		"\u0005*\u0000\u0000\u00b5\u00b7\u00032\u0019\u0000\u00b6\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000\u0000\u00b7\u00bd\u0001\u0000"+
		"\u0000\u0000\u00b8\u00b9\u0005\'\u0000\u0000\u00b9\u00bd\u00054\u0000"+
		"\u0000\u00ba\u00bb\u0005)\u0000\u0000\u00bb\u00bd\u00054\u0000\u0000\u00bc"+
		"\u00b3\u0001\u0000\u0000\u0000\u00bc\u00b8\u0001\u0000\u0000\u0000\u00bc"+
		"\u00ba\u0001\u0000\u0000\u0000\u00bd\u0011\u0001\u0000\u0000\u0000\u00be"+
		"\u00c0\u0005\u000b\u0000\u0000\u00bf\u00c1\u0003P(\u0000\u00c0\u00bf\u0001"+
		"\u0000\u0000\u0000\u00c0\u00c1\u0001\u0000\u0000\u0000\u00c1\u0013\u0001"+
		"\u0000\u0000\u0000\u00c2\u00c3\u0005\u0018\u0000\u0000\u00c3\u00c8\u0005"+
		"4\u0000\u0000\u00c4\u00c5\u00053\u0000\u0000\u00c5\u00c7\u00054\u0000"+
		"\u0000\u00c6\u00c4\u0001\u0000\u0000\u0000\u00c7\u00ca\u0001\u0000\u0000"+
		"\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000"+
		"\u0000\u00c9\u00cb\u0001\u0000\u0000\u0000\u00ca\u00c8\u0001\u0000\u0000"+
		"\u0000\u00cb\u00cc\u00059\u0000\u0000\u00cc\u0015\u0001\u0000\u0000\u0000"+
		"\u00cd\u00ce\u0005\u0019\u0000\u0000\u00ce\u00d3\u00054\u0000\u0000\u00cf"+
		"\u00d0\u00053\u0000\u0000\u00d0\u00d2\u00054\u0000\u0000\u00d1\u00cf\u0001"+
		"\u0000\u0000\u0000\u00d2\u00d5\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u00d6\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00d7\u0005"+
		"9\u0000\u0000\u00d7\u0017\u0001\u0000\u0000\u0000\u00d8\u00da\u0005\u001a"+
		"\u0000\u0000\u00d9\u00db\u00032\u0019\u0000\u00da\u00d9\u0001\u0000\u0000"+
		"\u0000\u00da\u00db\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000"+
		"\u0000\u00dc\u00dd\u00059\u0000\u0000\u00dd\u0019\u0001\u0000\u0000\u0000"+
		"\u00de\u00df\u0005\u000f\u0000\u0000\u00df\u001b\u0001\u0000\u0000\u0000"+
		"\u00e0\u00e1\u0005\u0010\u0000\u0000\u00e1\u001d\u0001\u0000\u0000\u0000"+
		"\u00e2\u00e3\u0005\u0011\u0000\u0000\u00e3\u00e4\u0005\u001f\u0000\u0000"+
		"\u00e4\u00e5\u00059\u0000\u0000\u00e5\u00ef\u0003.\u0017\u0000\u00e6\u00e8"+
		"\u0005\u0012\u0000\u0000\u00e7\u00e9\u0003&\u0013\u0000\u00e8\u00e7\u0001"+
		"\u0000\u0000\u0000\u00e8\u00e9\u0001\u0000\u0000\u0000\u00e9\u00ea\u0001"+
		"\u0000\u0000\u0000\u00ea\u00eb\u0005\u001f\u0000\u0000\u00eb\u00ec\u0005"+
		"9\u0000\u0000\u00ec\u00ee\u0003.\u0017\u0000\u00ed\u00e6\u0001\u0000\u0000"+
		"\u0000\u00ee\u00f1\u0001\u0000\u0000\u0000\u00ef\u00ed\u0001\u0000\u0000"+
		"\u0000\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0\u00f6\u0001\u0000\u0000"+
		"\u0000\u00f1\u00ef\u0001\u0000\u0000\u0000\u00f2\u00f3\u0005\u0007\u0000"+
		"\u0000\u00f3\u00f4\u0005\u001f\u0000\u0000\u00f4\u00f5\u00059\u0000\u0000"+
		"\u00f5\u00f7\u0003.\u0017\u0000\u00f6\u00f2\u0001\u0000\u0000\u0000\u00f6"+
		"\u00f7\u0001\u0000\u0000\u0000\u00f7\u00fc\u0001\u0000\u0000\u0000\u00f8"+
		"\u00f9\u0005\u0013\u0000\u0000\u00f9\u00fa\u0005\u001f\u0000\u0000\u00fa"+
		"\u00fb\u00059\u0000\u0000\u00fb\u00fd\u0003.\u0017\u0000\u00fc\u00f8\u0001"+
		"\u0000\u0000\u0000\u00fc\u00fd\u0001\u0000\u0000\u0000\u00fd\u001f\u0001"+
		"\u0000\u0000\u0000\u00fe\u00ff\u0005\u0015\u0000\u0000\u00ff\u0102\u0003"+
		"2\u0019\u0000\u0100\u0101\u0005\u000e\u0000\u0000\u0101\u0103\u00054\u0000"+
		"\u0000\u0102\u0100\u0001\u0000\u0000\u0000\u0102\u0103\u0001\u0000\u0000"+
		"\u0000\u0103\u0104\u0001\u0000\u0000\u0000\u0104\u0105\u0005\u001f\u0000"+
		"\u0000\u0105\u0106\u00059\u0000\u0000\u0106\u0107\u0003.\u0017\u0000\u0107"+
		"!\u0001\u0000\u0000\u0000\u0108\u0109\u0005\u0016\u0000\u0000\u0109\u010a"+
		"\u0003L&\u0000\u010a\u010b\u00059\u0000\u0000\u010b#\u0001\u0000\u0000"+
		"\u0000\u010c\u010d\u0005\u0017\u0000\u0000\u010d\u0110\u00032\u0019\u0000"+
		"\u010e\u010f\u00053\u0000\u0000\u010f\u0111\u00032\u0019\u0000\u0110\u010e"+
		"\u0001\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u0112"+
		"\u0001\u0000\u0000\u0000\u0112\u0113\u00059\u0000\u0000\u0113%\u0001\u0000"+
		"\u0000\u0000\u0114\u0117\u00054\u0000\u0000\u0115\u0116\u0005\u000e\u0000"+
		"\u0000\u0116\u0118\u00054\u0000\u0000\u0117\u0115\u0001\u0000\u0000\u0000"+
		"\u0117\u0118\u0001\u0000\u0000\u0000\u0118\u011c\u0001\u0000\u0000\u0000"+
		"\u0119\u011a\u0005\u000e\u0000\u0000\u011a\u011c\u00054\u0000\u0000\u011b"+
		"\u0114\u0001\u0000\u0000\u0000\u011b\u0119\u0001\u0000\u0000\u0000\u011c"+
		"\'\u0001\u0000\u0000\u0000\u011d\u011e\u0005\u0005\u0000\u0000\u011e\u011f"+
		"\u00032\u0019\u0000\u011f\u0120\u0005\u001f\u0000\u0000\u0120\u0121\u0005"+
		"9\u0000\u0000\u0121\u012a\u0003.\u0017\u0000\u0122\u0123\u0005\u0006\u0000"+
		"\u0000\u0123\u0124\u00032\u0019\u0000\u0124\u0125\u0005\u001f\u0000\u0000"+
		"\u0125\u0126\u00059\u0000\u0000\u0126\u0127\u0003.\u0017\u0000\u0127\u0129"+
		"\u0001\u0000\u0000\u0000\u0128\u0122\u0001\u0000\u0000\u0000\u0129\u012c"+
		"\u0001\u0000\u0000\u0000\u012a\u0128\u0001\u0000\u0000\u0000\u012a\u012b"+
		"\u0001\u0000\u0000\u0000\u012b\u0131\u0001\u0000\u0000\u0000\u012c\u012a"+
		"\u0001\u0000\u0000\u0000\u012d\u012e\u0005\u0007\u0000\u0000\u012e\u012f"+
		"\u0005\u001f\u0000\u0000\u012f\u0130\u00059\u0000\u0000\u0130\u0132\u0003"+
		".\u0017\u0000\u0131\u012d\u0001\u0000\u0000\u0000\u0131\u0132\u0001\u0000"+
		"\u0000\u0000\u0132)\u0001\u0000\u0000\u0000\u0133\u0134\u0005\b\u0000"+
		"\u0000\u0134\u0135\u00032\u0019\u0000\u0135\u0136\u0005\u001f\u0000\u0000"+
		"\u0136\u0137\u00059\u0000\u0000\u0137\u013c\u0003.\u0017\u0000\u0138\u0139"+
		"\u0005\u0007\u0000\u0000\u0139\u013a\u0005\u001f\u0000\u0000\u013a\u013b"+
		"\u00059\u0000\u0000\u013b\u013d\u0003.\u0017\u0000\u013c\u0138\u0001\u0000"+
		"\u0000\u0000\u013c\u013d\u0001\u0000\u0000\u0000\u013d+\u0001\u0000\u0000"+
		"\u0000\u013e\u013f\u0005\u0002\u0000\u0000\u013f\u0140\u0003L&\u0000\u0140"+
		"\u0141\u0005\u0003\u0000\u0000\u0141\u0142\u00032\u0019\u0000\u0142\u0143"+
		"\u0005\u001f\u0000\u0000\u0143\u0144\u00059\u0000\u0000\u0144\u0149\u0003"+
		".\u0017\u0000\u0145\u0146\u0005\u0007\u0000\u0000\u0146\u0147\u0005\u001f"+
		"\u0000\u0000\u0147\u0148\u00059\u0000\u0000\u0148\u014a\u0003.\u0017\u0000"+
		"\u0149\u0145\u0001\u0000\u0000\u0000\u0149\u014a\u0001\u0000\u0000\u0000"+
		"\u014a-\u0001\u0000\u0000\u0000\u014b\u014e\u0005<\u0000\u0000\u014c\u014f"+
		"\u00059\u0000\u0000\u014d\u014f\u0003\u0002\u0001\u0000\u014e\u014c\u0001"+
		"\u0000\u0000\u0000\u014e\u014d\u0001\u0000\u0000\u0000\u014f\u0150\u0001"+
		"\u0000\u0000\u0000\u0150\u014e\u0001\u0000\u0000\u0000\u0150\u0151\u0001"+
		"\u0000\u0000\u0000\u0151\u0152\u0001\u0000\u0000\u0000\u0152\u0153\u0005"+
		"=\u0000\u0000\u0153/\u0001\u0000\u0000\u0000\u0154\u0155\u0003N\'\u0000"+
		"\u0155\u0156\u0005 \u0000\u0000\u0156\u0157\u00032\u0019\u0000\u0157\u0158"+
		"\u0005!\u0000\u0000\u0158\u0159\u0005*\u0000\u0000\u0159\u015a\u00032"+
		"\u0019\u0000\u015a\u016a\u0001\u0000\u0000\u0000\u015b\u015c\u0003N\'"+
		"\u0000\u015c\u015d\u0005\u001e\u0000\u0000\u015d\u015e\u00054\u0000\u0000"+
		"\u015e\u015f\u0005*\u0000\u0000\u015f\u0160\u00032\u0019\u0000\u0160\u016a"+
		"\u0001\u0000\u0000\u0000\u0161\u0162\u0003J%\u0000\u0162\u0163\u0005*"+
		"\u0000\u0000\u0163\u0164\u0003P(\u0000\u0164\u016a\u0001\u0000\u0000\u0000"+
		"\u0165\u0166\u0003L&\u0000\u0166\u0167\u0005*\u0000\u0000\u0167\u0168"+
		"\u0003P(\u0000\u0168\u016a\u0001\u0000\u0000\u0000\u0169\u0154\u0001\u0000"+
		"\u0000\u0000\u0169\u015b\u0001\u0000\u0000\u0000\u0169\u0161\u0001\u0000"+
		"\u0000\u0000\u0169\u0165\u0001\u0000\u0000\u0000\u016a1\u0001\u0000\u0000"+
		"\u0000\u016b\u016c\u00034\u001a\u0000\u016c3\u0001\u0000\u0000\u0000\u016d"+
		"\u0172\u00036\u001b\u0000\u016e\u016f\u0005\u001c\u0000\u0000\u016f\u0171"+
		"\u00036\u001b\u0000\u0170\u016e\u0001\u0000\u0000\u0000\u0171\u0174\u0001"+
		"\u0000\u0000\u0000\u0172\u0170\u0001\u0000\u0000\u0000\u0172\u0173\u0001"+
		"\u0000\u0000\u0000\u01735\u0001\u0000\u0000\u0000\u0174\u0172\u0001\u0000"+
		"\u0000\u0000\u0175\u017a\u00038\u001c\u0000\u0176\u0177\u0005\u001b\u0000"+
		"\u0000\u0177\u0179\u00038\u001c\u0000\u0178\u0176\u0001\u0000\u0000\u0000"+
		"\u0179\u017c\u0001\u0000\u0000\u0000\u017a\u0178\u0001\u0000\u0000\u0000"+
		"\u017a\u017b\u0001\u0000\u0000\u0000\u017b7\u0001\u0000\u0000\u0000\u017c"+
		"\u017a\u0001\u0000\u0000\u0000\u017d\u017f\u0005\u001d\u0000\u0000\u017e"+
		"\u017d\u0001\u0000\u0000\u0000\u017f\u0182\u0001\u0000\u0000\u0000\u0180"+
		"\u017e\u0001\u0000\u0000\u0000\u0180\u0181\u0001\u0000\u0000\u0000\u0181"+
		"\u0183\u0001\u0000\u0000\u0000\u0182\u0180\u0001\u0000\u0000\u0000\u0183"+
		"\u0184\u0003:\u001d\u0000\u01849\u0001\u0000\u0000\u0000\u0185\u018b\u0003"+
		"<\u001e\u0000\u0186\u0187\u0003D\"\u0000\u0187\u0188\u0003<\u001e\u0000"+
		"\u0188\u018a\u0001\u0000\u0000\u0000\u0189\u0186\u0001\u0000\u0000\u0000"+
		"\u018a\u018d\u0001\u0000\u0000\u0000\u018b\u0189\u0001\u0000\u0000\u0000"+
		"\u018b\u018c\u0001\u0000\u0000\u0000\u018c;\u0001\u0000\u0000\u0000\u018d"+
		"\u018b\u0001\u0000\u0000\u0000\u018e\u0194\u0003>\u001f\u0000\u018f\u0190"+
		"\u0003F#\u0000\u0190\u0191\u0003>\u001f\u0000\u0191\u0193\u0001\u0000"+
		"\u0000\u0000\u0192\u018f\u0001\u0000\u0000\u0000\u0193\u0196\u0001\u0000"+
		"\u0000\u0000\u0194\u0192\u0001\u0000\u0000\u0000\u0194\u0195\u0001\u0000"+
		"\u0000\u0000\u0195=\u0001\u0000\u0000\u0000\u0196\u0194\u0001\u0000\u0000"+
		"\u0000\u0197\u019d\u0003@ \u0000\u0198\u0199\u0003H$\u0000\u0199\u019a"+
		"\u0003@ \u0000\u019a\u019c\u0001\u0000\u0000\u0000\u019b\u0198\u0001\u0000"+
		"\u0000\u0000\u019c\u019f\u0001\u0000\u0000\u0000\u019d\u019b\u0001\u0000"+
		"\u0000\u0000\u019d\u019e\u0001\u0000\u0000\u0000\u019e?\u0001\u0000\u0000"+
		"\u0000\u019f\u019d\u0001\u0000\u0000\u0000\u01a0\u01a1\u0007\u0000\u0000"+
		"\u0000\u01a1\u01a4\u0003@ \u0000\u01a2\u01a4\u0003B!\u0000\u01a3\u01a0"+
		"\u0001\u0000\u0000\u0000\u01a3\u01a2\u0001\u0000\u0000\u0000\u01a4A\u0001"+
		"\u0000\u0000\u0000\u01a5\u01a8\u0003N\'\u0000\u01a6\u01a7\u0005)\u0000"+
		"\u0000\u01a7\u01a9\u0003@ \u0000\u01a8\u01a6\u0001\u0000\u0000\u0000\u01a8"+
		"\u01a9\u0001\u0000\u0000\u0000\u01a9C\u0001\u0000\u0000\u0000\u01aa\u01ab"+
		"\u0007\u0001\u0000\u0000\u01abE\u0001\u0000\u0000\u0000\u01ac\u01ad\u0007"+
		"\u0000\u0000\u0000\u01adG\u0001\u0000\u0000\u0000\u01ae\u01af\u0007\u0002"+
		"\u0000\u0000\u01afI\u0001\u0000\u0000\u0000\u01b0\u01cb\u00054\u0000\u0000"+
		"\u01b1\u01b3\u00051\u0000\u0000\u01b2\u01b4\u0003L&\u0000\u01b3\u01b2"+
		"\u0001\u0000\u0000\u0000\u01b3\u01b4\u0001\u0000\u0000\u0000\u01b4\u01b6"+
		"\u0001\u0000\u0000\u0000\u01b5\u01b7\u00053\u0000\u0000\u01b6\u01b5\u0001"+
		"\u0000\u0000\u0000\u01b6\u01b7\u0001\u0000\u0000\u0000\u01b7\u01b8\u0001"+
		"\u0000\u0000\u0000\u01b8\u01cb\u00052\u0000\u0000\u01b9\u01bb\u0005 \u0000"+
		"\u0000\u01ba\u01bc\u0003L&\u0000\u01bb\u01ba\u0001\u0000\u0000\u0000\u01bb"+
		"\u01bc\u0001\u0000\u0000\u0000\u01bc\u01be\u0001\u0000\u0000\u0000\u01bd"+
		"\u01bf\u00053\u0000\u0000\u01be\u01bd\u0001\u0000\u0000\u0000\u01be\u01bf"+
		"\u0001\u0000\u0000\u0000\u01bf\u01c0\u0001\u0000\u0000\u0000\u01c0\u01cb"+
		"\u0005!\u0000\u0000\u01c1\u01c2\u0003N\'\u0000\u01c2\u01c3\u0005 \u0000"+
		"\u0000\u01c3\u01c4\u00032\u0019\u0000\u01c4\u01c5\u0005!\u0000\u0000\u01c5"+
		"\u01cb\u0001\u0000\u0000\u0000\u01c6\u01c7\u0003N\'\u0000\u01c7\u01c8"+
		"\u0005\u001e\u0000\u0000\u01c8\u01c9\u00054\u0000\u0000\u01c9\u01cb\u0001"+
		"\u0000\u0000\u0000\u01ca\u01b0\u0001\u0000\u0000\u0000\u01ca\u01b1\u0001"+
		"\u0000\u0000\u0000\u01ca\u01b9\u0001\u0000\u0000\u0000\u01ca\u01c1\u0001"+
		"\u0000\u0000\u0000\u01ca\u01c6\u0001\u0000\u0000\u0000\u01cbK\u0001\u0000"+
		"\u0000\u0000\u01cc\u01d1\u0003J%\u0000\u01cd\u01ce\u00053\u0000\u0000"+
		"\u01ce\u01d0\u0003J%\u0000\u01cf\u01cd\u0001\u0000\u0000\u0000\u01d0\u01d3"+
		"\u0001\u0000\u0000\u0000\u01d1\u01cf\u0001\u0000\u0000\u0000\u01d1\u01d2"+
		"\u0001\u0000\u0000\u0000\u01d2M\u0001\u0000\u0000\u0000\u01d3\u01d1\u0001"+
		"\u0000\u0000\u0000\u01d4\u01d5\u0006\'\uffff\uffff\u0000\u01d5\u01d7\u0005"+
		"\n\u0000\u0000\u01d6\u01d8\u0003\u000e\u0007\u0000\u01d7\u01d6\u0001\u0000"+
		"\u0000\u0000\u01d7\u01d8\u0001\u0000\u0000\u0000\u01d8\u01d9\u0001\u0000"+
		"\u0000\u0000\u01d9\u01da\u0005\u001f\u0000\u0000\u01da\u023b\u00032\u0019"+
		"\u0000\u01db\u023b\u00055\u0000\u0000\u01dc\u023b\u00056\u0000\u0000\u01dd"+
		"\u023b\u00057\u0000\u0000\u01de\u023b\u00058\u0000\u0000\u01df\u023b\u0005"+
		"4\u0000\u0000\u01e0\u01e1\u00051\u0000\u0000\u01e1\u01e6\u00032\u0019"+
		"\u0000\u01e2\u01e3\u00053\u0000\u0000\u01e3\u01e5\u00032\u0019\u0000\u01e4"+
		"\u01e2\u0001\u0000\u0000\u0000\u01e5\u01e8\u0001\u0000\u0000\u0000\u01e6"+
		"\u01e4\u0001\u0000\u0000\u0000\u01e6\u01e7\u0001\u0000\u0000\u0000\u01e7"+
		"\u01ea\u0001\u0000\u0000\u0000\u01e8\u01e6\u0001\u0000\u0000\u0000\u01e9"+
		"\u01eb\u00053\u0000\u0000\u01ea\u01e9\u0001\u0000\u0000\u0000\u01ea\u01eb"+
		"\u0001\u0000\u0000\u0000\u01eb\u01ec\u0001\u0000\u0000\u0000\u01ec\u01ed"+
		"\u00052\u0000\u0000\u01ed\u023b\u0001\u0000\u0000\u0000\u01ee\u01ef\u0005"+
		" \u0000\u0000\u01ef\u01f0\u00032\u0019\u0000\u01f0\u01f1\u0005\u0002\u0000"+
		"\u0000\u01f1\u01f2\u0003L&\u0000\u01f2\u01f3\u0005\u0003\u0000\u0000\u01f3"+
		"\u01f8\u00032\u0019\u0000\u01f4\u01f5\u0005\u0005\u0000\u0000\u01f5\u01f7"+
		"\u00032\u0019\u0000\u01f6\u01f4\u0001\u0000\u0000\u0000\u01f7\u01fa\u0001"+
		"\u0000\u0000\u0000\u01f8\u01f6\u0001\u0000\u0000\u0000\u01f8\u01f9\u0001"+
		"\u0000\u0000\u0000\u01f9\u01fb\u0001\u0000\u0000\u0000\u01fa\u01f8\u0001"+
		"\u0000\u0000\u0000\u01fb\u01fc\u0005!\u0000\u0000\u01fc\u023b\u0001\u0000"+
		"\u0000\u0000\u01fd\u01fe\u0005\"\u0000\u0000\u01fe\u01ff\u00032\u0019"+
		"\u0000\u01ff\u0200\u0005\u001f\u0000\u0000\u0200\u0201\u00032\u0019\u0000"+
		"\u0201\u0202\u0005\u0002\u0000\u0000\u0202\u0203\u0003L&\u0000\u0203\u0204"+
		"\u0005\u0003\u0000\u0000\u0204\u0209\u00032\u0019\u0000\u0205\u0206\u0005"+
		"\u0005\u0000\u0000\u0206\u0208\u00032\u0019\u0000\u0207\u0205\u0001\u0000"+
		"\u0000\u0000\u0208\u020b\u0001\u0000\u0000\u0000\u0209\u0207\u0001\u0000"+
		"\u0000\u0000\u0209\u020a\u0001\u0000\u0000\u0000\u020a\u020c\u0001\u0000"+
		"\u0000\u0000\u020b\u0209\u0001\u0000\u0000\u0000\u020c\u020d\u0005#\u0000"+
		"\u0000\u020d\u023b\u0001\u0000\u0000\u0000\u020e\u020f\u0005\"\u0000\u0000"+
		"\u020f\u0210\u00032\u0019\u0000\u0210\u0211\u0005\u0002\u0000\u0000\u0211"+
		"\u0212\u0003L&\u0000\u0212\u0213\u0005\u0003\u0000\u0000\u0213\u0218\u0003"+
		"2\u0019\u0000\u0214\u0215\u0005\u0005\u0000\u0000\u0215\u0217\u00032\u0019"+
		"\u0000\u0216\u0214\u0001\u0000\u0000\u0000\u0217\u021a\u0001\u0000\u0000"+
		"\u0000\u0218\u0216\u0001\u0000\u0000\u0000\u0218\u0219\u0001\u0000\u0000"+
		"\u0000\u0219\u021b\u0001\u0000\u0000\u0000\u021a\u0218\u0001\u0000\u0000"+
		"\u0000\u021b\u021c\u0005#\u0000\u0000\u021c\u023b\u0001\u0000\u0000\u0000"+
		"\u021d\u021e\u00051\u0000\u0000\u021e\u021f\u00032\u0019\u0000\u021f\u0220"+
		"\u0005\u0002\u0000\u0000\u0220\u0221\u0003L&\u0000\u0221\u0222\u0005\u0003"+
		"\u0000\u0000\u0222\u0227\u00032\u0019\u0000\u0223\u0224\u0005\u0005\u0000"+
		"\u0000\u0224\u0226\u00032\u0019\u0000\u0225\u0223\u0001\u0000\u0000\u0000"+
		"\u0226\u0229\u0001\u0000\u0000\u0000\u0227\u0225\u0001\u0000\u0000\u0000"+
		"\u0227\u0228\u0001\u0000\u0000\u0000\u0228\u022a\u0001\u0000\u0000\u0000"+
		"\u0229\u0227\u0001\u0000\u0000\u0000\u022a\u022b\u00052\u0000\u0000\u022b"+
		"\u023b\u0001\u0000\u0000\u0000\u022c\u022e\u0005 \u0000\u0000\u022d\u022f"+
		"\u0003P(\u0000\u022e\u022d\u0001\u0000\u0000\u0000\u022e\u022f\u0001\u0000"+
		"\u0000\u0000\u022f\u0230\u0001\u0000\u0000\u0000\u0230\u023b\u0005!\u0000"+
		"\u0000\u0231\u0233\u0005\"\u0000\u0000\u0232\u0234\u0003R)\u0000\u0233"+
		"\u0232\u0001\u0000\u0000\u0000\u0233\u0234\u0001\u0000\u0000\u0000\u0234"+
		"\u0235\u0001\u0000\u0000\u0000\u0235\u023b\u0005#\u0000\u0000\u0236\u0237"+
		"\u00051\u0000\u0000\u0237\u0238\u00032\u0019\u0000\u0238\u0239\u00052"+
		"\u0000\u0000\u0239\u023b\u0001\u0000\u0000\u0000\u023a\u01d4\u0001\u0000"+
		"\u0000\u0000\u023a\u01db\u0001\u0000\u0000\u0000\u023a\u01dc\u0001\u0000"+
		"\u0000\u0000\u023a\u01dd\u0001\u0000\u0000\u0000\u023a\u01de\u0001\u0000"+
		"\u0000\u0000\u023a\u01df\u0001\u0000\u0000\u0000\u023a\u01e0\u0001\u0000"+
		"\u0000\u0000\u023a\u01ee\u0001\u0000\u0000\u0000\u023a\u01fd\u0001\u0000"+
		"\u0000\u0000\u023a\u020e\u0001\u0000\u0000\u0000\u023a\u021d\u0001\u0000"+
		"\u0000\u0000\u023a\u022c\u0001\u0000\u0000\u0000\u023a\u0231\u0001\u0000"+
		"\u0000\u0000\u023a\u0236\u0001\u0000\u0000\u0000\u023b\u024c\u0001\u0000"+
		"\u0000\u0000\u023c\u023d\n\u0004\u0000\u0000\u023d\u023e\u0005 \u0000"+
		"\u0000\u023e\u023f\u00032\u0019\u0000\u023f\u0240\u0005!\u0000\u0000\u0240"+
		"\u024b\u0001\u0000\u0000\u0000\u0241\u0242\n\u0003\u0000\u0000\u0242\u0243"+
		"\u0005\u001e\u0000\u0000\u0243\u024b\u00054\u0000\u0000\u0244\u0245\n"+
		"\u0002\u0000\u0000\u0245\u0247\u00051\u0000\u0000\u0246\u0248\u0003V+"+
		"\u0000\u0247\u0246\u0001\u0000\u0000\u0000\u0247\u0248\u0001\u0000\u0000"+
		"\u0000\u0248\u0249\u0001\u0000\u0000\u0000\u0249\u024b\u00052\u0000\u0000"+
		"\u024a\u023c\u0001\u0000\u0000\u0000\u024a\u0241\u0001\u0000\u0000\u0000"+
		"\u024a\u0244\u0001\u0000\u0000\u0000\u024b\u024e\u0001\u0000\u0000\u0000"+
		"\u024c\u024a\u0001\u0000\u0000\u0000\u024c\u024d\u0001\u0000\u0000\u0000"+
		"\u024dO\u0001\u0000\u0000\u0000\u024e\u024c\u0001\u0000\u0000\u0000\u024f"+
		"\u0254\u00032\u0019\u0000\u0250\u0251\u00053\u0000\u0000\u0251\u0253\u0003"+
		"2\u0019\u0000\u0252\u0250\u0001\u0000\u0000\u0000\u0253\u0256\u0001\u0000"+
		"\u0000\u0000\u0254\u0252\u0001\u0000\u0000\u0000\u0254\u0255\u0001\u0000"+
		"\u0000\u0000\u0255Q\u0001\u0000\u0000\u0000\u0256\u0254\u0001\u0000\u0000"+
		"\u0000\u0257\u025c\u0003T*\u0000\u0258\u0259\u00053\u0000\u0000\u0259"+
		"\u025b\u0003T*\u0000\u025a\u0258\u0001\u0000\u0000\u0000\u025b\u025e\u0001"+
		"\u0000\u0000\u0000\u025c\u025a\u0001\u0000\u0000\u0000\u025c\u025d\u0001"+
		"\u0000\u0000\u0000\u025dS\u0001\u0000\u0000\u0000\u025e\u025c\u0001\u0000"+
		"\u0000\u0000\u025f\u0260\u00032\u0019\u0000\u0260\u0261\u0005\u001f\u0000"+
		"\u0000\u0261\u0262\u00032\u0019\u0000\u0262U\u0001\u0000\u0000\u0000\u0263"+
		"\u0268\u0003X,\u0000\u0264\u0265\u00053\u0000\u0000\u0265\u0267\u0003"+
		"X,\u0000\u0266\u0264\u0001\u0000\u0000\u0000\u0267\u026a\u0001\u0000\u0000"+
		"\u0000\u0268\u0266\u0001\u0000\u0000\u0000\u0268\u0269\u0001\u0000\u0000"+
		"\u0000\u0269W\u0001\u0000\u0000\u0000\u026a\u0268\u0001\u0000\u0000\u0000"+
		"\u026b\u026c\u00054\u0000\u0000\u026c\u026d\u0005*\u0000\u0000\u026d\u0270"+
		"\u00032\u0019\u0000\u026e\u0270\u00032\u0019\u0000\u026f\u026b\u0001\u0000"+
		"\u0000\u0000\u026f\u026e\u0001\u0000\u0000\u0000\u0270Y\u0001\u0000\u0000"+
		"\u0000\u0271\u0272\u0005\f\u0000\u0000\u0272\u0275\u00054\u0000\u0000"+
		"\u0273\u0274\u0005\u000e\u0000\u0000\u0274\u0276\u00054\u0000\u0000\u0275"+
		"\u0273\u0001\u0000\u0000\u0000\u0275\u0276\u0001\u0000\u0000\u0000\u0276"+
		"\u0277\u0001\u0000\u0000\u0000\u0277\u027f\u00059\u0000\u0000\u0278\u0279"+
		"\u0005\r\u0000\u0000\u0279\u027a\u00054\u0000\u0000\u027a\u027b\u0005"+
		"\f\u0000\u0000\u027b\u027c\u0003\\.\u0000\u027c\u027d\u00059\u0000\u0000"+
		"\u027d\u027f\u0001\u0000\u0000\u0000\u027e\u0271\u0001\u0000\u0000\u0000"+
		"\u027e\u0278\u0001\u0000\u0000\u0000\u027f[\u0001\u0000\u0000\u0000\u0280"+
		"\u0292\u0005\'\u0000\u0000\u0281\u0284\u00054\u0000\u0000\u0282\u0283"+
		"\u0005\u000e\u0000\u0000\u0283\u0285\u00054\u0000\u0000\u0284\u0282\u0001"+
		"\u0000\u0000\u0000\u0284\u0285\u0001\u0000\u0000\u0000\u0285\u028e\u0001"+
		"\u0000\u0000\u0000\u0286\u0287\u00053\u0000\u0000\u0287\u028a\u00054\u0000"+
		"\u0000\u0288\u0289\u0005\u000e\u0000\u0000\u0289\u028b\u00054\u0000\u0000"+
		"\u028a\u0288\u0001\u0000\u0000\u0000\u028a\u028b\u0001\u0000\u0000\u0000"+
		"\u028b\u028d\u0001\u0000\u0000\u0000\u028c\u0286\u0001\u0000\u0000\u0000"+
		"\u028d\u0290\u0001\u0000\u0000\u0000\u028e\u028c\u0001\u0000\u0000\u0000"+
		"\u028e\u028f\u0001\u0000\u0000\u0000\u028f\u0292\u0001\u0000\u0000\u0000"+
		"\u0290\u028e\u0001\u0000\u0000\u0000\u0291\u0280\u0001\u0000\u0000\u0000"+
		"\u0291\u0281\u0001\u0000\u0000\u0000\u0292]\u0001\u0000\u0000\u0000D`"+
		"bu}\u0084\u008b\u0096\u0099\u00a4\u00b0\u00b6\u00bc\u00c0\u00c8\u00d3"+
		"\u00da\u00e8\u00ef\u00f6\u00fc\u0102\u0110\u0117\u011b\u012a\u0131\u013c"+
		"\u0149\u014e\u0150\u0169\u0172\u017a\u0180\u018b\u0194\u019d\u01a3\u01a8"+
		"\u01b3\u01b6\u01bb\u01be\u01ca\u01d1\u01d7\u01e6\u01ea\u01f8\u0209\u0218"+
		"\u0227\u022e\u0233\u023a\u0247\u024a\u024c\u0254\u025c\u0268\u026f\u0275"+
		"\u027e\u0284\u028a\u028e\u0291";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}