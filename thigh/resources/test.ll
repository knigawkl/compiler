declare i32 @printf(i8*, ...)
declare i32 @getchar()
@.str = global [17 x i8] c"You entered: %c\0A\00"
@str0 = constant[4 x i8] c"10\0A\00"
@str1 = constant[2 x i8] c"\0A\00"
@str2 = constant[2 x i8] c"\0A\00"
@str3 = constant[5 x i8] c"3+4\0A\00"
@str4 = constant[3 x i8] c"1\0A\00"
define i32 @main() nounwind{
%1 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( [4 x i8], [4 x i8]* @str0, i32 0, i32 0))
%2 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( [2 x i8], [2 x i8]* @str1, i32 0, i32 0))
%3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( [2 x i8], [2 x i8]* @str2, i32 0, i32 0))
%4 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( [5 x i8], [5 x i8]* @str3, i32 0, i32 0))
%5 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( [3 x i8], [3 x i8]* @str4, i32 0, i32 0))
ret i32 0 }

