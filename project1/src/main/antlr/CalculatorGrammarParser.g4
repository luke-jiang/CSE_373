parser grammar CalculatorGrammarParser;
options {
    tokenVocab=CalculatorGrammarLexer;
}

program
    : (statements+=statement)* EOF
    ;

statement
    : varName=IDENTIFIER ASSIGN expr=addExpr LINE_BREAK    # assignStmt
    | expr=addExpr LINE_BREAK                              # exprStmt
    ;

addExpr
    : left=addExpr op=(PLUS | MINUS) right=multiplyExpr    # addExprBin
    | expr=multiplyExpr                                    # addExprSingle
    ;

multiplyExpr
    : left=multiplyExpr op=(MULTIPLY | DIVIDE) right=negExpr    # multExprBin
    | expr=negExpr                                              # multExprSingle
    ;

negExpr
    : MINUS expr=negExpr                                   # negExprUnary
    | expr=powExpr                                         # negExprSingle
    ;

// The exponentiation operator is right-associative
powExpr
    : left=atomExpr op=POW right=powExpr                   # powExprBin
    | expr=atomExpr                                        # powExprSingle
    ;


atomExpr
    : value=NUMBER                                      # number
    | rawText=STRING                                    # rawString
    | varName=IDENTIFIER                                # variable
    | funcName=IDENTIFIER LPAREN args=arglist RPAREN    # funcName
    | LPAREN expr=addExpr RPAREN                        # parenExpr
    ;

arglist
    : (values+=addExpr (COMMA values+=addExpr)*)?
    ;
