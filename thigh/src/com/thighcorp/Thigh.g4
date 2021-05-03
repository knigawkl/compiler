grammar Thigh;

program: statement* EOF;
statement: expression
         | function_definition
         | print_statement
         | read_statement
         | assign_statement;

print_statement: PRINT value SEMICOLON;
read_statement: READ ID SEMICOLON;
assign_statement: ID ASSIGN assign_value SEMICOLON;
assign_value: (value | arithmetic_operation);

function_definition: FUNCTION_DEFINITION ID BRACKET_OPEN ID? (COMMA ID)* BRACKET_CLOSE function_body;
function_body: BRACE_OPEN expression* BRACE_CLOSE;
expression: arithmetic_operation SEMICOLON;

arithmetic_operation: value
                    | value arithmetic_operator arithmetic_operation
                    | BRACKET_OPEN arithmetic_operation BRACKET_CLOSE;

arithmetic_operator: ADDITION
                   | SUBSTITUTION
                   | MULTIPLICATION
                   | DIVISION
                   | MODULO
                   | POWER;
value: ID | STRING | INT | REAL;

PRINT: 'print';
READ: 'input';
ASSIGN: '=';
FUNCTION_DEFINITION: 'def';

ID: ('a'..'z'|'A'..'Z')+;
STRING: '"'( ~('\\'|'"') )*'"';
INT: '-'?'0'..'9'+;
REAL: '-'?'0'..'9'+('.''0'..'9'+)?;

ADDITION: '+';
SUBSTITUTION: '-';
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
