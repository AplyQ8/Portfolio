lexer grammar CobolTokens;

REPRESENTATION
    : REPR+
    ;

ARITHMETIC_OPERATOR
    : '+'
    | '-'
    | '*'
    | '/'
    | '**'
    ;

COMPARISON_OPERATOR
    : '='
    | '<='
    | '<'
    | '>'
    | '>='
    ;

BOOLEAN_OPERATOR
    : 'OR'
    | 'AND'
    | 'XOR'
    ;

IDENTIFICATION : 'I' SPACE* 'D' SPACE* 'E' SPACE* 'N' SPACE* 'T' SPACE* 'I' SPACE* 'F' SPACE* 'I' SPACE* 'C' SPACE* 'A' SPACE* 'T' SPACE* 'I' SPACE* 'O' SPACE* 'N';
DIVISION : 'D' SPACE* 'I' SPACE* 'V' SPACE* 'I' SPACE* 'S' SPACE* 'I' SPACE* 'O' SPACE* 'N';
OCCURS: 'O' SPACE* 'C' SPACE* 'C' SPACE* 'U' SPACE* 'R' SPACE* 'S';
TIMES: 'T' SPACE* 'I' SPACE* 'M' SPACE* 'E' SPACE* 'S';
PICTURE: 'P' SPACE* 'I' SPACE* 'C' SPACE* 'T' SPACE* 'U' SPACE* 'R' SPACE* 'E';
IS: 'I' SPACE* 'S';
PROCEDURE: 'P' SPACE* 'R' SPACE* 'O' SPACE* 'C' SPACE* 'E' SPACE* 'D' SPACE* 'U' SPACE* 'R' SPACE* 'E';
USING: 'U' SPACE* 'S' SPACE* 'I' SPACE* 'N' SPACE* 'G';
BY: 'B' SPACE* 'Y';
FROM: 'F' SPACE* 'R' SPACE* 'O' SPACE* 'M' ;
REFERENCE: 'R' SPACE* 'E' SPACE* 'F' SPACE* 'E' SPACE* 'R' SPACE* 'E' SPACE* 'N' SPACE* 'C' SPACE* 'E';
CONTENT: 'C' SPACE* 'O' SPACE* 'N' SPACE* 'T' SPACE* 'E' SPACE* 'N' SPACE* 'T';
VALUE: 'V' SPACE* 'A' SPACE* 'L' SPACE* 'U' SPACE* 'E';
LIKE: 'L' SPACE* 'I' SPACE* 'K' SPACE* 'E';
DISPLAY : 'D' SPACE* 'I' SPACE* 'S' SPACE* 'P' SPACE* 'L' SPACE* 'A' SPACE* 'Y' ;
OF: 'O' SPACE* 'F';
WITH: 'W' SPACE* 'I' SPACE* 'T' SPACE* 'H';
NO: 'N' SPACE* 'O';
ADVANCING: 'A' SPACE* 'D' SPACE* 'V' SPACE* 'A' SPACE* 'N' SPACE* 'C' SPACE* 'I' SPACE* 'N' SPACE* 'G';
DATA: 'D' SPACE* 'A' SPACE* 'T' SPACE* 'A';
ADD : 'A' SPACE* 'D' SPACE* 'D' ;
TO : 'T' SPACE* 'O';
INTO : 'I' SPACE* 'N' SPACE* 'T' SPACE* 'O';
GIVING : 'G' SPACE* 'I' SPACE* 'V' SPACE* 'I' SPACE* 'N' SPACE* 'G';
REMAINDER : 'R' SPACE* 'E' SPACE* 'M' SPACE* 'A' SPACE* 'I' SPACE* 'N' SPACE* 'D' SPACE* 'E' SPACE* 'R';
ACCEPT : 'A' SPACE* 'C' SPACE* 'C' SPACE* 'E' SPACE* 'P' SPACE* 'T';
MULTIPLY : 'M' SPACE* 'U' SPACE* 'L' SPACE* 'T' SPACE* 'I' SPACE* 'P' SPACE* 'L' SPACE* 'Y';
SUBTRACT : 'S' SPACE* 'U' SPACE* 'B' SPACE* 'T' SPACE* 'R' SPACE* 'A' SPACE* 'C' SPACE* 'T';
DIVIDE : 'D' SPACE* 'I' SPACE* 'V' SPACE* 'I' SPACE* 'D' SPACE* 'E';
IF : 'I' SPACE* 'F';
ELSE: 'E' SPACE* 'L' SPACE* 'S' SPACE* 'E';
THEN: 'T' SPACE* 'H' SPACE* 'E' SPACE* 'N';
END: 'E' SPACE* 'N' SPACE* 'D';
TRUE: 'T' SPACE* 'R' SPACE* 'U' SPACE* 'E';
FALSE: 'F' SPACE* 'A' SPACE* 'L' SPACE* 'S' SPACE* 'E';
NOT: 'N' SPACE* 'O' SPACE* 'T';
WHEN: 'W' SPACE* 'H' SPACE* 'E' SPACE* 'N';
ALSO: 'A' SPACE* 'L' SPACE* 'S' SPACE* 'O';
OTHER: 'O' SPACE* 'T' SPACE* 'H' SPACE* 'E' SPACE* 'R';
EVALUATE: 'E' SPACE* 'V' SPACE* 'A' SPACE* 'L' SPACE* 'U' SPACE* 'A' SPACE* 'T' SPACE* 'E';
STOP: 'S' SPACE* 'T' SPACE* 'O' SPACE* 'P';
NEXT: 'N' SPACE* 'E' SPACE* 'X' SPACE* 'T';
SENTENCE: 'S' SPACE* 'E' SPACE* 'N' SPACE* 'T' SPACE* 'E' SPACE* 'N' SPACE* 'C' SPACE* 'E';
PERFORM: 'P' SPACE* 'E' SPACE* 'R' SPACE* 'F' SPACE* 'O' SPACE* 'R' SPACE* 'M';
THROUGH: 'T' SPACE* 'H' SPACE* 'R' SPACE* 'O' SPACE* 'U' SPACE* 'G' SPACE* 'H';
COPY: 'C' SPACE* 'O' SPACE* 'P' SPACE* 'Y';
REPLACING: 'R' SPACE* 'E' SPACE* 'P' SPACE* 'L' SPACE* 'A' SPACE* 'C' SPACE* 'I' SPACE* 'N' SPACE* 'G';
LOOP: 'L' SPACE* 'O' SPACE* 'O' SPACE* 'P';
VARYING: 'V' SPACE* 'A' SPACE* 'R' SPACE* 'Y' SPACE* 'I' SPACE* 'N' SPACE* 'G';
WHILE: 'W' SPACE* 'H' SPACE* 'I' SPACE* 'L' SPACE* 'E';
UNTIL: 'U' SPACE* 'N' SPACE* 'T' SPACE* 'I' SPACE* 'L';
GO: 'G' SPACE* 'O';
MOVE: 'M' SPACE* 'O' SPACE* 'V' SPACE* 'E';
SPACES: 'S' SPACE* 'P' SPACE* 'A' SPACE* 'C' SPACE* 'E' SPACE* 'S';
HIGH_VALUES: 'H' SPACE* 'I' SPACE* 'G' SPACE* 'H' SPACE* '-' SPACE* 'V' SPACE* 'A' SPACE* 'L' SPACE* 'U' SPACE* 'E' SPACE* 'S';
LOW_VALUES: 'L' SPACE* 'O' SPACE* 'W' SPACE* '-' SPACE* 'V' SPACE* 'A' SPACE* 'L' SPACE* 'U' SPACE* 'E' SPACE* 'S';
ALTER: 'A' SPACE* 'L' SPACE* 'T' SPACE* 'E' SPACE* 'R';
SIGNAL: 'S' SPACE* 'I' SPACE* 'G' SPACE* 'N' SPACE* 'A' SPACE* 'L';
OFF: 'O' SPACE* 'F' SPACE* 'F';
ON: 'O' SPACE* 'N';
ERROR: 'E' SPACE* 'R' SPACE* 'R' SPACE* 'O' SPACE* 'R';


IDENTIFIER
    : LETTER ( LETTER | DIGIT | '-' )* ( '(' DIGIT ')' )?
    ;

INT
    : DIGIT+
    ;

SPACE
    : [ \t\r\n\f] -> skip
    ;

DOT
    : '.'
    ;

LITERAL
    : '"' ~'"'+ '"'
    ;

COPY_LITERAL
    : '===' ~'='+ '==='
    ;

fragment LETTER
    : [a-zA-Z]
    ;

fragment DIGIT
    : [0-9]
    ;

fragment REPR
    : '9'
    | 'A'
    | 'X'
    | 'Z'
    | 'S'
    | 'V'
    ;