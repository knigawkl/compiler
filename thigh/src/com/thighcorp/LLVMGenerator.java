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

    static void printVariable(String id, VarType type) {
        String printingTemplate = "%%" + reg_iter + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + (string_declaration_iter - 1) + ", i32 0, i32 0), %s %%" + (reg_iter -1) + ")\n";
        switch (type) {
            case INT: {
                declareString(VarType.INT);
                loadVariable(id, VarType.INT);
                main_text += String.format(printingTemplate, integerStr);
            }
            case DOUBLE: {
                declareString(VarType.DOUBLE);
                loadVariable(id, VarType.DOUBLE);
                main_text += String.format(printingTemplate, doubleStr);
            }
        }
        reg_iter++;
    }

    static void print_string(String text) {
        int str_len = text.length();
        String str_type = "[" + (str_len+2) + " x i8]";
        header_text += "@str" + string_declaration_iter + " = constant" + str_type + " c\"" + text + "\\0A\\00\"\n";
        main_text += "%"+ reg_iter +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( " + str_type + ", " + str_type + "* @str" + string_declaration_iter + ", i32 0, i32 0))\n";
        string_declaration_iter++;
        reg_iter++;
    }

    static void input_int(String id) {
        header_text += "@str" + string_declaration_iter + " = constant [3 x i8] c\"%d\\00\"\n";
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @str" + string_declaration_iter + ", i32 0, i32 0), i32* %" + id + ")\n";
        string_declaration_iter++;
        reg_iter++;
    }

    static void input_double(String id) {
        header_text += "@str" + string_declaration_iter + " = constant [4 x i8] c\"%lf\\00\"\n";
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + string_declaration_iter + ", i32 0, i32 0), double* %" + id + ")\n";
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

    static void add_int(String val1, String val2){
        main_text += "%"+ reg_iter +" = add i32 "+val1+", "+val2+"\n";
        reg_iter++;
    }

    static void add_double(String val1, String val2){
        main_text += "%"+ reg_iter +" = fadd double "+val1+", "+val2+"\n";
        reg_iter++;
    }

    static void mult_i32(String val1, String val2){
        main_text += "%"+ reg_iter +" = mul i32 "+val1+", "+val2+"\n";
        reg_iter++;
    }

    static void mult_double(String val1, String val2){
        main_text += "%"+ reg_iter +" = fmul double "+val1+", "+val2+"\n";
        reg_iter++;
    }

    static void sitofp(String id){
        main_text += "%"+ reg_iter +" = sitofp i32 "+id+" to double\n";
        reg_iter++;
    }

    static void fptosi(String id){
        main_text += "%"+ reg_iter +" = fptosi double "+id+" to i32\n";
        reg_iter++;
    }

    private static void reset() {
        header_text = "";
        main_text = "";
        string_declaration_iter = 0;
        reg_iter = 1;
    }
}
