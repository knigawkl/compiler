grammar Thigh;

program: statement* EOF;
statement: expression
//         | function_definition
         | printStatement
         | read_statement
         | assign_statement
         | increment
         | decrement
         | comment
         |if_statement
         | function
         | call
         ;

printStatement: PRINT value SEMICOLON #print;
read_statement: READ type ID SEMICOLON #read;
assign_statement: ID ASSIGN assign_value SEMICOLON #assign;
assign_value: (value | arithmeticOperation) #assignVal;
increment: type ID ADDITION ADDITION SEMICOLON;
decrement: type ID SUBTRACTION SUBTRACTION SEMICOLON;
comment: COMMENT STRING SEMICOLON;
call: CALL ID_NAME;

CALL: 'call';

//function_definition: FUNCTION_DEFINITION ID BRACKET_OPEN ID? (COMMA ID)* BRACKET_CLOSE function_body;
//function_body: BRACE_OPEN expression* BRACE_CLOSE;
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

function : 'fun' var_type fname  funct_body;
fname : ID_NAME;

var_type : t_INT    |
           t_REAL   ;

funct_body : BRACE_OPEN ( statement? SEMICOLON )* BRACE_CLOSE;

if_statement :  'if' BRACKET_OPEN condition BRACKET_CLOSE if_body;
condition : value COMPARE value;
if_body: BRACE_OPEN statement BRACE_CLOSE;

PRINT: 'print';
READ: 'input';
ASSIGN: '=';
FUNCTION_DEFINITION: 'def';

ID: ('a'..'z'|'A'..'Z')+;
STRING: '"'( ~('\\'|'"') )*'"';
INT: '-'?'0'..'9'+;
DOUBLE: '-'?'0'..'9'+('.''0'..'9'+)?;
ID_NAME:   ('a'..'z'|'A'..'Z')+;

t_INT : 'int' ;
t_REAL : 'real';

COMPARE: '<' | '>' | '=';

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
