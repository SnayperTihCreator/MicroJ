grammar MicroJ;

tokens { INDENT, DEDENT }

file: (NEWLINE | statement)* EOF;

statement: simpleStatement | ifStatement | whileStatement | funcDef | forStatement | importStatement | tryStatement | classDef;
simpleStatement:
    (assignment
    | expr
    | returnStatement
    | breakStatement
    | continueStatement) NEWLINE;

funcDef: decorator* DEF NAME LPAREN paramList? RPAREN COLON NEWLINE block;
classDef: CLASS NAME (LPAREN NAME RPAREN)? COLON NEWLINE block;
decorator: AT expr NEWLINE;
paramList: NAME (COMMA NAME)*;
returnStatement: RETURN exprList?;
breakStatement: BREAK;
continueStatement: CONTINUE;

tryStatement: TRY COLON NEWLINE block
             (EXCEPT exceptClause? COLON NEWLINE block)*
             (ELSE COLON NEWLINE block)?
             (FINALLY COLON NEWLINE block)?;

exceptClause: NAME (AS NAME)? | AS NAME;

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
mulOp: STAR | SLASH;

target: NAME
      | LPAREN targetList? COMMA? RPAREN
      | LBRACKET targetList? COMMA? RBRACKET
      | atom LBRACKET expr RBRACKET
      | atom DOT NAME
      ;

targetList: target (COMMA target)* ;

atom: NUMBER          # Number
    | FLOAT           # FloatLiteral
    | IMAG            # ImagLiteral
    | STRING          # StringLiteral
    | NAME            # Variable
    | LPAREN expr (COMMA expr)* COMMA? RPAREN # TupleLiteral
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
arg: NAME EQUAL expr # KwArg
   | expr            # PosArg
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
RETURN: 'return';
IMPORT: 'import';
FROM: 'from';
AS: 'as';
BREAK: 'break';
CONTINUE: 'continue';
TRY: 'try';
EXCEPT: 'except';
FINALLY: 'finally';
AT: '@';

AND: 'and';
OR: 'or';
NOT: 'not';

DOT: '.';
COLON: ':';
LBRACKET: '[';
RBRACKET: ']';
LBRACE: '{';
RBRACE: '}';

PLUS: '+';
MINUS: '-';
STAR: '*';
SLASH: '/';
POW: '**';

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
STRING: '"' ~["\r\n]* '"' | '\'' ~['\r\n]* '\'';
NEWLINE: '\r'?'\n' | ';';
WS: [ \t]+ -> skip;
COMMENT: '#' ~[\r\n]* -> skip;
