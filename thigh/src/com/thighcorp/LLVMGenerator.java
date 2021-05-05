package com.thighcorp;

class LLVMGenerator {

    private static String header_text = "";
    private static String main_text = "";
    private static int string_declaration_iter = 0;
    private static int reg_iter = 1;
    private static final String integerStr = "i32";
    private static final String doubleStr = "double";

    static String generate() {
        String text = "";
        text += "declare i32 @printf(i8*, ...)\n";
        text += "declare i32 @__isoc99_scanf(i8*, ...)\n";
        text += "@strs = constant [3 x i8] c\"%d\\00\"\n";
        text += header_text;
        text += "define i32 @main() nounwind{\n";
        text += main_text;
        text += "ret i32 0 }\n";
        reset();
        return text;
    }

    static void assign(String id, String value, VarType type) {
        String assignmentTemplate = "store %s " + value + ", %s* %%" + id + "\n";
        switch (type) {
            case INT -> main_text += String.format(assignmentTemplate, integerStr, integerStr);
            case DOUBLE -> main_text += String.format(assignmentTemplate, doubleStr, doubleStr);
        }
    }

    static void print_int_var(String id) {
        declareString(VarType.INT);
        loadVariable(id, VarType.INT);
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + (string_declaration_iter - 1) + ", i32 0, i32 0), i32 %" + (reg_iter -1) + ")\n";
        reg_iter++;
    }

    static void print_double_var(String id) {
        declareString(VarType.DOUBLE);
        loadVariable(id, VarType.DOUBLE);
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + (string_declaration_iter - 1) + ", i32 0, i32 0), double %" + (reg_iter-1) + ")\n";
        reg_iter++;
    }

    static void printString(String text) {
        int str_len = text.length();
        String str_type = "[" + (str_len+2) + " x i8]";
        header_text += "@str" + string_declaration_iter + " = constant" + str_type + " c\"" + text + "\\0A\\00\"\n";
        main_text += "%"+ reg_iter +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( " + str_type + ", " + str_type + "* @str" + string_declaration_iter + ", i32 0, i32 0))\n";
        string_declaration_iter++;
        reg_iter++;
    }

    static void inputVariable(String id, VarType type) {
        var headerTemplate = "@str" + string_declaration_iter + " = constant %s";
        var mainTemplate = "%%" + reg_iter + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds (%s* @str"
                + string_declaration_iter + ", i32 0, i32 0), %s* %%" + id + ")\n";
        switch (type) {
            case INT -> {
                header_text += String.format(headerTemplate, "[3 x i8] c\"%d\\00\"\n");
                main_text += String.format(mainTemplate, "[3 x i8], [3 x i8]", integerStr);
            } case DOUBLE -> {
                header_text += String.format(headerTemplate, "[4 x i8] c\"%lf\\00\"\n");;
                main_text += String.format(mainTemplate, "[4 x i8], [4 x i8]", doubleStr);
            } case STRING -> {
                // TODO
            }
        }
        string_declaration_iter++;
        reg_iter++;
    }

    static void loadVariable(String id, VarType type) {
        String loadingTemplate = "%%"+ reg_iter +" = load %s, %s* %%"+id+"\n";
        switch (type) {
            case INT -> main_text += String.format(loadingTemplate, integerStr, integerStr);
            case DOUBLE -> main_text += String.format(loadingTemplate, doubleStr, doubleStr);
        }
        reg_iter++;
    }

    static void declareVariable(String id, VarType type) {
        String declarationTemplate = "%%" + id + " = alloca %s\n";
        switch (type) {
            case INT -> main_text += String.format(declarationTemplate, integerStr);
            case DOUBLE -> main_text += String.format(declarationTemplate, doubleStr);
        }
    }

    static void declareString(VarType type) {
        String declarationTemplate = "@str" + string_declaration_iter + " = constant [4 x i8] %s";
        switch (type) {
            case INT -> header_text += String.format(declarationTemplate, "c\"%d\\0A\\00\"\n");
            case DOUBLE -> header_text += String.format(declarationTemplate, "c\"%f\\0A\\00\"\n");
        }
        string_declaration_iter++;
    }

    private static void reset() {
        header_text = "";
        main_text = "";
        string_declaration_iter = 0;
        reg_iter = 1;
    }
}
