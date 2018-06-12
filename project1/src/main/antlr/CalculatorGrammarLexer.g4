lexer grammar CalculatorGrammarLexer;

channels {
    LINE_CONTINUATION_CHANNEL
}

// We say a line ends when we encounter any combination of the '\n' and '\r' characters
LINE_BREAK
    : [\n\r]+
    ;

// Ignore and skip all whitespace in our language.
WS
    : [ \t]+ -> skip
    ;

// We ignore comments as well.
COMMENT
    : '#' ~[\r\n]* -> skip
    ;

// We ignore the backslash (e.g. line continuation) character
LINE_CONTINUATION
    : '\\' [\n\r]+ -> channel(LINE_CONTINUATION_CHANNEL)
    ;

// We use the same rule for identifiers as Java.
IDENTIFIER
    : [a-zA-Z_][a-zA-Z_0-9]*
    ;

// A number is a bunch of digits, followed optionally by some decimals.
NUMBER
    : DIGIT+ ([.] DIGIT+)?
    ;

// A digit is the characters 0 through 0.
fragment DIGIT
    : [0-9]
    ;

// We define our string to be identical to Java's strings. However, we need to
// do some extra work to account for things like escape sequences and whitespace.
STRING
    : '"' STRING_CHAR* '"'
    ;

fragment STRING_CHAR
    : ~('"' | '\\' | '\r' | '\n') | '\\' ('"' | '\\')
    ;

ASSIGN      : ':=' ;

PLUS        : '+' ;
MINUS       : '-' ;
MULTIPLY    : '*' ;
DIVIDE      : '/' ;
POW         : '^' ;
COMMA       : ',' ;

LPAREN      : '(' -> pushMode(IGNORE_NEWLINES);
RPAREN      : ')' ;

// Matches all unrecognized symbols
ERROR_TOKEN : . ;


// Start special "ignoring newlines" mode:

mode IGNORE_NEWLINES;

// Special handling

IGNORE_NEWLINES_LPAREN      : LPAREN -> type(LPAREN), pushMode(IGNORE_NEWLINES);
IGNORE_NEWLINES_RPAREN      : RPAREN -> type(RPAREN), popMode;
IGNORE_NEWLINES_LINE_BREAK
    : [\n\r]+ -> skip
    ;

// Copied tokens

IGNORE_NEWLINE_WS                   : WS -> type(WS), skip;
IGNORE_NEWLINE_COMMENT              : COMMENT -> type(COMMENT), skip;
IGNORE_NEWLINE_LINE_CONTINUATION    : LINE_CONTINUATION -> type(LINE_CONTINUATION), channel(LINE_CONTINUATION_CHANNEL);
IGNORE_NEWLINE_IDENTIFIER           : IDENTIFIER -> type(IDENTIFIER);
IGNORE_NEWLINE_NUMBER               : NUMBER -> type(NUMBER);
IGNORE_NEWLINE_STRING               : STRING -> type(STRING);
IGNORE_NEWLINE_ASSIGN               : ASSIGN -> type(ASSIGN);
IGNORE_NEWLINE_PLUS                 : PLUS -> type(PLUS);
IGNORE_NEWLINE_MINUS                : MINUS -> type(MINUS);
IGNORE_NEWLINE_MULTIPLY             : MULTIPLY -> type(MULTIPLY);
IGNORE_NEWLINE_DIVIDE               : DIVIDE -> type(DIVIDE);
IGNORE_NEWLINE_POW                  : POW -> type(POW);
IGNORE_NEWLINE_COMMA                : COMMA -> type(COMMA);
IGNORE_ERROR_TOKEN                  : ERROR_TOKEN -> type(ERROR_TOKEN);

