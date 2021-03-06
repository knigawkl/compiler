grammar Thigh;

program: statement* EOF;
statement: expression
         | function_definition
         | printStatement
         | read_statement
         | assign_statement
         | increment
         | decrement
         | comment;

printStatement: PRINT value SEMICOLON #print;
read_statement: READ type ID SEMICOLON #read;
assign_statement: ID ASSIGN assign_value SEMICOLON #assign;
assign_value: (value | arithmeticOperation) #assignVal;
increment: type ID ADDITION ADDITION SEMICOLON;
decrement: type ID SUBTRACTION SUBTRACTION SEMICOLON;
comment: COMMENT STRING SEMICOLON;

function_definition: FUNCTION_DEFINITION ID BRACKET_OPEN ID? (COMMA ID)* BRACKET_CLOSE function_body;
function_body: BRACE_OPEN expression* BRACE_CLOSE;
expression: arithmeticOperation SEMICOLON;

arithmeticOperation: additionOperation
                   | subtractionOperation
                   | divisionOperation
                   | multiplicationOperation
                   | moduloOperation;

multiplicationOperation: value | value MULTIPLICATION value;
divisionOperation: value | value DIVISION value;
subtractionOperation: value | value SUBTRACTION value;
additionOperation: value | value ADDITION value;
moduloOperation: value | value MODULO value;

arithmetic_operator: ADDITION
                   | SUBTRACTION
                   | MULTIPLICATION
                   | DIVISION
                   | MODULO
                   | POWER;

cast: TOINT | TODOUBLE;

value: ID | STRING | INT | DOUBLE | value cast;
type: 'int' | 'double' | 'string';

PRINT: 'print';
READ: 'input';
ASSIGN: '=';
FUNCTION_DEFINITION: 'def';

ID: ('a'..'z'|'A'..'Z')+;
STRING: '"'( ~('\\'|'"') )*'"';
INT: '-'?'0'..'9'+;
DOUBLE: '-'?'0'..'9'+('.''0'..'9'+)?;

TOINT: '(int)';
TODOUBLE: '(double)';

ADDITION: '+';
SUBTRACTION: '-';
MULTIPLICATION: '*';
DIVISION: '/';
POWER: '^';
MODULO: '%';

COMMA: ',';
SEMICOLON: ';';
BRACKET_OPEN: '(';
BRACKET_CLOSE: ')';
BRACE_OPEN: '{';
BRACE_CLOSE: '}';
WHITESPACE: [ \t\r\n]+ -> skip;
COMMENT: '#';

//arithmetic_operation: arithmetic_value
//                    | arithmetic_operation arithmetic_operator arithmetic_operation
//                    | BRACKET_OPEN arithmetic_operation BRACKET_CLOSE;
//arithmetic_operation: (arithmetic_value | arithmetic_operator)*;