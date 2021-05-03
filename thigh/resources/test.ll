declare i32 @printf(i8*, ...)
declare i32 @getchar()
@.str = global [17 x i8] c"You entered: %c\0A\00"
@ch0 = common global i8 0
define i32 @main() nounwind{
%1 = call i32 @getchar()
%2 = trunc i32 %1 to i8
store i8 %2, i8* @ch0
%3 = load i8, i8* @ch0
%4 = sext i8 %3 to i32
%5 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([17 x i8], [17 x i8]* @.str, i32 0, i32 0), i32 %4)
ret i32 0 }

