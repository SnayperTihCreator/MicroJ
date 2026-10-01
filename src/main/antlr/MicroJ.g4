grammar MicroJ;

tokens { INDENT, DEDENT }

fragment SINGLE_Q_BODY: ~['\r\n]*;
fragment DOUBLE_Q_BODY: ~["\r\n]*;
fragment TRIPLE_DOUBLE_BODY: .*?;
fragment TRIPLE_SINGLE_BODY: .*?;

fragment STR_PREFIX: 'r' | 'R' | 'b' | 'B' | 'u' | 'U' | 'rb' | 'rB' | 'Rb' | 'RB' | 'br' | 'bR' | 'Br' | 'BR';

TRIPLE_DOUBLE_STRING : STR_PREFIX? '"""' TRIPLE_DOUBLE_BODY '"""' ;
TRIPLE_SINGLE_STRING : STR_PREFIX? '\'\'\'' TRIPLE_SINGLE_BODY '\'\'\'' ;
SINGLE_DOUBLE_STRING : STR_PREFIX? '"' DOUBLE_Q_BODY '"' ;
SINGLE_SINGLE_STRING : STR_PREFIX? '\'' SINGLE_Q_BODY '\'' ;

file: (NEWLINE | statement)* EOF;

statement: simpleStatement
    | ifStatement
    | whileStatement
    | funcDef
    | forStatement
    | importStatement
    | tryStatement
    | raiseStatement
    | classDef
    | withStatement
    | delStatement
    | assertStatement
    | globalStatement
    | nonlocalStatement
    | yieldStatement
    ;

simpleStatement:
    (assignment
    | expr
    | returnStatement
    | breakStatement
    | continueStatement
    | PASS) NEWLINE;

funcDef: decorator* DEF NAME LPAREN paramList? RPAREN COLON NEWLINE block;
classDef: CLASS NAME (LPAREN baseList? RPAREN)? COLON NEWLINE block;
baseList: NAME (COMMA NAME)* ;
decorator: AT expr NEWLINE;
paramList: param (COMMA param)* ;
param: NAME (EQUAL expr)? | STAR NAME | POW NAME ;
returnStatement: RETURN exprList?;
globalStatement: GLOBAL NAME (COMMA NAME)* NEWLINE;
nonlocalStatement: NONLOCAL NAME (COMMA NAME)* NEWLINE;
yieldStatement: YIELD expr? NEWLINE;
breakStatement: BREAK;
continueStatement: CONTINUE;

tryStatement: TRY COLON NEWLINE block
             (EXCEPT exceptClause? COLON NEWLINE block)*
             (ELSE COLON NEWLINE block)?
             (FINALLY COLON NEWLINE block)?;

withStatement: WITH expr (AS NAME)? COLON NEWLINE block;
delStatement: DEL targetList NEWLINE;
assertStatement: ASSERT expr (COMMA expr)? NEWLINE;
exceptClause: NAME (AS NAME)? | AS NAME;
raiseStatement: RAISE expr? NEWLINE;

ifStatement: IF expr COLON NEWLINE block
           (ELIF expr COLON NEWLINE block)*
           (ELSE COLON NEWLINE block)?;
whileStatement: WHILE expr COLON NEWLINE block (ELSE COLON NEWLINE block)?;
forStatement: FOR targetList IN expr COLON NEWLINE block (ELSE COLON NEWLINE block)?;
block: INDENT (NEWLINE | statement)+ DEDENT;

assignment
    : atom LBRACKET expr RBRACKET EQUAL expr # SubscriptAssign
    | atom DOT NAME EQUAL expr               # AttrAssign
    | target EQUAL exprList                  # GeneralAssign
    | targetList EQUAL exprList              # GeneralAssignList
    ;

expr: or_expr;

or_expr: and_expr (OR and_expr)*;
and_expr: not_expr (AND not_expr)*;
not_expr: (NOT)* comparison;
comparison: add_expr (compareOp add_expr)*;
add_expr: mul_expr (addOp mul_expr)*;
mul_expr: unary_expr (mulOp unary_expr)*;
unary_expr: (PLUS | MINUS) unary_expr | power;
power: atom (POW unary_expr)?;

compareOp: EQ_EQ | NE | LT | GT | LE | GE | IN | NOT_IN;
addOp: PLUS | MINUS;
mulOp: STAR | SLASH | DOUBLE_SLASH | PERCENT;

compFor: FOR targetList IN or_expr compIter*;
compIf: IF or_expr;
compIter: compFor | compIf;

target: NAME
      | LPAREN targetList? COMMA? RPAREN
      | LBRACKET targetList? COMMA? RBRACKET
      | atom LBRACKET expr RBRACKET
      | atom DOT NAME
      ;

targetList: target (COMMA target)* ;

stringLit
    : TRIPLE_DOUBLE_STRING
    | TRIPLE_SINGLE_STRING
    | SINGLE_DOUBLE_STRING
    | SINGLE_SINGLE_STRING
    ;

atom: LAMBDA (paramList)? COLON expr # Lambda
    | NUMBER          # Number
    | FLOAT           # FloatLiteral
    | IMAG            # ImagLiteral
    | stringLit       # StringLiteral
    | NAME            # Variable
    | LPAREN expr (COMMA expr)* COMMA? RPAREN # TupleLiteral
    | LBRACKET expr compFor RBRACKET # ListComprehension
    | LBRACE expr COLON expr compFor RBRACE # DictComprehension
    | LBRACE expr compFor RBRACE # SetComprehension
    | LPAREN expr compFor RPAREN # GenExp
    | LBRACKET exprList? RBRACKET # ListLiteral
    | LBRACE dictList? RBRACE   # DictLiteral
    | atom LBRACKET expr RBRACKET # SubscriptAtom
    | atom DOT NAME # GetAttrAtom
    | atom LPAREN argList? RPAREN # CallAtom
    | LPAREN expr RPAREN # ParenAtom
    ;

exprList: expr (COMMA expr)*;
dictList: dictItem (COMMA dictItem)*;
dictItem: expr COLON expr;

argList: arg (COMMA arg)*;
arg: NAME EQUAL expr        # KwArg
   | expr compFor           # GenExpArg
   | expr                   # PosArg
   ;

importStatement: IMPORT NAME (AS NAME)? NEWLINE                 # ImportModule
               | FROM NAME IMPORT importNames NEWLINE           # ImportFrom
               ;
importNames: '*' | NAME (AS NAME)? (COMMA NAME (AS NAME)?)*;

// Лексер
CLASS: 'class';
FOR: 'for';
IN: 'in';
NOT_IN: 'not in';
IF: 'if';
ELIF: 'elif';
ELSE: 'else';
WHILE: 'while';
DEF: 'def';
LAMBDA: 'lambda';
RETURN: 'return';
IMPORT: 'import';
FROM: 'from';
AS: 'as';
BREAK: 'break';
CONTINUE: 'continue';
TRY: 'try';
EXCEPT: 'except';
FINALLY: 'finally';
RAISE: 'raise';
PASS: 'pass';
WITH: 'with';
DEL: 'del';
ASSERT: 'assert';
GLOBAL: 'global';
NONLOCAL: 'nonlocal';
YIELD: 'yield';

AND: 'and';
OR: 'or';
NOT: 'not';

DOT: '.';
COLON: ':';
LBRACKET: '[';
RBRACKET: ']';
LBRACE: '{';
RBRACE: '}';

AT: '@';
PLUS: '+';
MINUS: '-';
STAR: '*';
SLASH: '/';
DOUBLE_SLASH: '//';
POW: '**';
PERCENT: '%';

EQUAL: '=';
EQ_EQ: '==';
NE: '!=';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';

LPAREN: '(';
RPAREN: ')';
COMMA: ',';
NAME: [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]* | '.' [0-9]+ ;
IMAG: FLOAT 'j' | [0-9]+ 'j' ;
NEWLINE: '\r'?'\n';
WS: [ \t]+ -> skip;
COMMENT: '#' ~[\r\n]* -> skip;
