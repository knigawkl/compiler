package com.thighcorp;

class LLVMGenerator {

    private static String header = "";
    private static String content = "";
    private static int string_declaration_iter = 0;
    public static int register = 1;
    private static final String integerStr = "i32";
    private static final String doubleStr = "double";

    static String generate() {
        String text = "";
        text += "declare i32 @printf(i8*, ...)\n";
        text += "declare i32 @__isoc99_scanf(i8*, ...)\n";
        text += header;
        text += "define i32 @main() nounwind{\n";
        text += content;
        text += "ret i32 0 }\n";
        reset();
        return text;
    }

    static void assign(String id, String value, VarType type) {
        String assignmentTemplate = "store %s " + value + ", %s* %%" + id + "\n";
        switch (type) {
            case INT -> content += String.format(assignmentTemplate, integerStr, integerStr);
            case DOUBLE -> content += String.format(assignmentTemplate, doubleStr, doubleStr);
        }
    }

    static void printVariable(String id, VarType type) {
        // TODO: create common string template for printing both ints and doubles
        if (type == VarType.INT) {
            declareString(VarType.INT);
            loadVariable(id, VarType.INT);
            content += "%" + register + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + (string_declaration_iter - 1) + ", i32 0, i32 0), i32 %" + (register -1) + ")\n";
            register++;
        } else if (type == VarType.DOUBLE) {
            declareString(VarType.DOUBLE);
            loadVariable(id, VarType.DOUBLE);
            content += "%" + register + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + (string_declaration_iter - 1) + ", i32 0, i32 0), double %" + (register -1) + ")\n";
            register++;
        }
    }

    static void printString(String text) {
        int str_len = text.length();
        String str_type = "[" + (str_len+2) + " x i8]";
        header += "@str" + string_declaration_iter + " = constant" + str_type + " c\"" + text + "\\0A\\00\"\n";
        content += "%"+ register +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( " + str_type + ", " + str_type + "* @str" + string_declaration_iter + ", i32 0, i32 0))\n";
        string_declaration_iter++;
        register++;
    }

    static void inputVariable(String id, VarType type) {
        var headerTemplate = "@str" + string_declaration_iter + " = constant %s";
        var mainTemplate = "%%" + register + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds (%s* @str"
                + string_declaration_iter + ", i32 0, i32 0), %s* %%" + id + ")\n";
        switch (type) {
            case INT -> {
                header += String.format(headerTemplate, "[3 x i8] c\"%d\\00\"\n");
                content += String.format(mainTemplate, "[3 x i8], [3 x i8]", integerStr);
            } case DOUBLE -> {
                header += String.format(headerTemplate, "[4 x i8] c\"%lf\\00\"\n");;
                content += String.format(mainTemplate, "[4 x i8], [4 x i8]", doubleStr);
            } case STRING -> {
                // TODO
            }
        }
        string_declaration_iter++;
        register++;
    }

    static void loadVariable(String id, VarType type) {
        String loadingTemplate = "%%"+ register +" = load %s, %s* %%"+id+"\n";
        switch (type) {
            case INT -> content += String.format(loadingTemplate, integerStr, integerStr);
            case DOUBLE -> content += String.format(loadingTemplate, doubleStr, doubleStr);
        }
        register++;
    }

    static void declareVariable(String id, VarType type) {
        String declarationTemplate = "%%" + id + " = alloca %s\n";
        switch (type) {
            case INT -> content += String.format(declarationTemplate, integerStr);
            case DOUBLE -> content += String.format(declarationTemplate, doubleStr);
        }
    }

    static void declareString(VarType type) {
        String declarationTemplate = "@str" + string_declaration_iter + " = constant [4 x i8] %s";
        switch (type) {
            case INT -> header += String.format(declarationTemplate, "c\"%d\\0A\\00\"\n");
            case DOUBLE -> header += String.format(declarationTemplate, "c\"%f\\0A\\00\"\n");
        }
        string_declaration_iter++;
    }

    public static void castToInt(String id) {
        content += "%" + register + " = fptosi double " + id + " to i32\n";
        register++;
    }

    public static void castToDouble(String id) {
        content += "%" + register + " = sitofp i32 " + id + " to double\n";
        register++;
    }

    public static void add(String val1, String val2, VarType type) {
        var addingTemplate = "%%" + register + " = %s %s " + val1 + ", " + val2 + "\n";
        switch (type) {
            case INT -> content += String.format(addingTemplate, "add", integerStr);
            case DOUBLE -> content += String.format(addingTemplate, "fadd", doubleStr);
        }
        register++;
    }

    public static void sub(String val1, String val2, VarType type) {
        var subtractingTemplate = "%%" + register + " = %s %s " + val1 + ", " + val2 + "\n";
        switch (type) {
            case INT -> content += String.format(subtractingTemplate, "sub", integerStr);
            case DOUBLE -> content += String.format(subtractingTemplate, "fsub", doubleStr);
        }
        register++;
    }

    public static void mul(String val1, String val2, VarType type) {
        var multiplyingTemplate = "%%" + register + " = %s %s " + val1 + ", " + val2 + "\n";
        switch (type) {
            case INT -> content += String.format(multiplyingTemplate, "mul", integerStr);
            case DOUBLE -> content += String.format(multiplyingTemplate, "fmul", doubleStr);
        }
        register++;
    }

    public static void div(String val1, String val2, VarType type) {
        var divisionTemplate = "%%" + register + " = %s %s " + val1 + ", " + val2 + "\n";
        switch (type) {
            case INT -> content += String.format(divisionTemplate, "sdiv", integerStr);
            case DOUBLE -> content += String.format(divisionTemplate, "fdiv", doubleStr);
        }
        register++;
    }

    public static void mod(String val1, String val2, VarType type) {
        var moduloTemplate = "%%" + register + " = %s %s " + val2 + ", " + val1 + "\n";
        switch (type) {
            case INT -> content += String.format(moduloTemplate, "srem", integerStr);
            case DOUBLE -> content += String.format(moduloTemplate, "frem", doubleStr);
        }
        register++;
    }

    public static void increase(String id, VarType type) {
        int intSize = 4;
        int doubleSize = 8;
        var increaseTemplate = "%%" + (register + 1) + " = %s %s %%" + register + ", %s\n" + "store %s %%" + (register + 1) + ", %s* %%" + id + ", align %s\n";
        switch (type) {
            case INT -> {
                loadVariable(id, VarType.INT);
                content += String.format(increaseTemplate, "add nsw", integerStr, 1, integerStr, integerStr, intSize);
            }
            case DOUBLE -> {
                loadVariable(id, VarType.DOUBLE);
                content += String.format(increaseTemplate, "fadd ", doubleStr, "1.000000e+00", doubleStr, doubleStr, doubleSize);
            }
        }
        register++;
    }

    public static void decrease(String id, VarType type) {
        int intSize = 4;
        int doubleSize = 8;
        var increaseTemplate = "%%" + (register + 1) + " = %s %s %%" + register + ", %s\n" + "store %s %%" + (register + 1) + ", %s* %%" + id + ", align %s\n";
        switch (type) {
            case INT -> {
                loadVariable(id, VarType.INT);
                content += String.format(increaseTemplate, "sub nsw", integerStr, 1, integerStr, integerStr, intSize);
            }
            case DOUBLE -> {
                loadVariable(id, VarType.DOUBLE);
                content += String.format(increaseTemplate, "fsub ", doubleStr, "1.000000e+00", doubleStr, doubleStr, doubleSize);
            }
        }
        register++;
    }

    private static void reset() {
        header = "";
        content = "";
        string_declaration_iter = 0;
        register = 1;
    }
}
