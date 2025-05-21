grammar cobol;
import CobolTokens;
options { caseInsensitive = true; }

program
    : identification_division (data_division)? (procedure_division) EOF
    ;

identification_division
    : IDENTIFICATION DIVISION DOT (IDENTIFIER DOT LITERAL DOT)*
    ;

data_division
    : DATA DIVISION DOT variables*
    ;

variables
    : (level IDENTIFIER (picture | like)? (occurs)? DOT)
    ;

occurs
    : OCCURS INT TIMES
    ;

level
    : INT
    ;

picture
    : PICTURE IS REPRESENTATION
    ;

like
    : LIKE identifiers
    ;

procedure_division
    : PROCEDURE DIVISION (using)? DOT sentence+
    ;

using
    : USING use+
    ;

use
    : BY REFERENCE identifiers
    | BY CONTENT atomic
    | BY VALUE atomic
    ;

sentence
    : (proc DOT)? statement+ DOT
    ;

proc
    : IDENTIFIER
    ;

statement
    : display
    | add
    | accept
    | multiply
    | subtract
    | divide
    | if
    | evaluate
    | stop
    | next_sentence
    | perform
    | copy
    | loop
    | goto
    | move
    | alter
    | signal
    ;

// Define the parser rules
display
    : DISPLAY atomic+ withnoadvancing?
    ;

add
    : ADD additions+=INT+ TO identifiers
    | ADD additions+=INT+ TO base=INT giving
    ;
multiply
    : MULTIPLY multiplier=INT BY identifiers+
    | MULTIPLY multiplier=INT BY base=INT giving
    ;

divide
    : DIVIDE divisor=INT INTO identifiers+
    | DIVIDE divisor=INT INTO base=INT giving
    | DIVIDE divisor=INT INTO base=INT giving remainder
    ;
    
subtract
    : SUBTRACT subtractors+=INT+ FROM identifiers
    | SUBTRACT subtractors+=INT+ FROM base=INT giving
    ;

accept
    : ACCEPT identifiers+
    ;

withnoadvancing
    : WITH NO ADVANCING
    ;

atomic
    : identifiers
    | INT
    | LITERAL
    ;
    
giving
    : GIVING identifiers
    ;

identifiers
    : IDENTIFIER (OF IDENTIFIER)* ('(' INT ')')?
    ;
remainder
    :   REMAINDER identifiers
    ;

if
    : IF boolean THEN i+=statement+ (ELSE e+=statement+)? END
    ;

arithmetic_expression
    :   atomic
    |   arithmetic_expression ARITHMETIC_OPERATOR arithmetic_expression
    ;

string_expression
    :   atomic
    |   string_expression '+' string_expression
    ;

boolean
    :   TRUE
    |   FALSE
    |   arithmetic_expression COMPARISON_OPERATOR arithmetic_expression
    |   NOT boolean
    |   boolean BOOLEAN_OPERATOR boolean
    ;

expressions
    : boolean
    | arithmetic_expression
    | string_expression
    ;

evaluate
    : EVALUATE expressions (ALSO expressions)* when_block* END
    ;

when_block
    : WHEN atomic (ALSO atomic)* statement+
    | WHEN OTHER statement+
    ;

stop
    : STOP
    ;

next_sentence
    : NEXT SENTENCE
    ;

perform
    : PERFORM proc through? times?
    ;

through
    : THROUGH proc
    ;

times
    : INT TIMES
    ;

copy
    : COPY LITERAL replacing?
    ;

replacing
    : REPLACING replacements+
    ;

replacements
    : COPY_LITERAL BY COPY_LITERAL
    ;

loop
    : LOOP loop_expression* END
    ;

loop_expression
    : VARYING identifiers? (FROM from=atomic)? (TO to=atomic)? (BY by=atomic)?    #loop_varying_expression
    | WHILE boolean                                                          #loop_while_expression
    | UNTIL boolean                                                          #loop_until_expression
    | statement                                                                           #loop_statement_expession
    ;

goto
    : GO TO IDENTIFIER
    ;

move
    : MOVE (INT | singlevar | HIGH_VALUES | LOW_VALUES | SPACES) TO multivar
    ;

singlevar
    : identifiers+
    ;

multivar
    : identifiers+
    ;

alter
    : ALTER proc 'TO PROCEED TO' proc
    ;

signal
    : SIGNAL (OFF | proc) ON ERROR
    ;
