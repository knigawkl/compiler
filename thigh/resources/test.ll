declare i32 @printf(i8*, ...)
declare i32 @getchar()
@.str = global [17 x i8] c"You entered: %c\0A\00"
define i32 @main() nounwind{
%a = alloca i32
%1 = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @strs, i32 0, i32 0), i32* %a)
ret i32 0 }

