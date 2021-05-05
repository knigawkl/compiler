package com.thighcorp;

class LLVMGenerator {

    static String header_text = "";
    static String main_text = "";
    static int str_i = 0;
    static int ch_i = 0;
    static int fun_i = 1;
    static int reg = 1;

    static String generate() {
        String text = "";
        text += "declare i32 @printf(i8*, ...)\n";
        text += "declare i32 @__isoc99_scanf(i8*, ...)\n";
//        text += "@strp = constant [4 x i8] c\"%d\\0A\\00\"\n";
        text += "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";
        text += "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";
        text += "@strs = constant [3 x i8] c\"%d\\00\"\n";
        text += header_text;
        text += "define i32 @main() nounwind{\n";
        text += main_text;
        text += "ret i32 0 }\n";
        return text;
    }

    static void read(String id) {
        header_text += "@ch" + ch_i + " = common global i8 0\n";
        main_text += "%" + fun_i + " = call i32 @getchar()\n";
        fun_i++;
        main_text += "%" + fun_i + " = trunc i32 %" + (fun_i-1) + " to i8\n";
        main_text += "store i8 %" + fun_i + ", i8* @ch" + ch_i + "\n";
        fun_i++;
        main_text += "%" + fun_i + " = load i8, i8* @ch" + ch_i + "\n";
        fun_i++;
        main_text += "%" + fun_i + " = sext i8 %" + (fun_i-1) + " to i32\n";
        fun_i++;
        main_text += "%" + fun_i + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([17 x i8], [17 x i8]* @.str, i32 0, i32 0), i32 %" + (fun_i-1) +")\n";
        fun_i++;
        ch_i++;
    }

    static void assign_int(String id, String value) {
        main_text += "store i32 "+value+", i32* %"+id+"\n";
    }

    static void assign_double(String id, String value) {
        main_text += "store double "+value+", double* %"+id+"\n";
    }

    static void assign_string(String id, String value) {
        // TODO: check if this is sufficient for strings
        //       this is same as for ints atm
        main_text += "store i32 "+value+", i32* %"+id+"\n";
    }

    static void print(String text) {
        int str_len = text.length();
        String str_type = "[" + (str_len+2) + " x i8]";
        header_text += "@str"+str_i+" = constant" + str_type + " c\"" + text + "\\0A\\00\"\n";
        main_text += "%" + fun_i + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( "+str_type+", "+str_type+"* @str"+str_i+", i32 0, i32 0))\n";
        str_i++;
        fun_i++;
    }

    static void load_i32(String id) {
        main_text += "%"+reg+" = load i32, i32* %"+id+"\n";
        reg++;
    }

    static void load_double(String id) {
        main_text += "%"+reg+" = load double, double* %"+id+"\n";
        reg++;
    }

    static void add_int(String val1, String val2){
        main_text += "%"+reg+" = add i32 "+val1+", "+val2+"\n";
        reg++;
    }

    static void add_double(String val1, String val2){
        main_text += "%"+reg+" = fadd double "+val1+", "+val2+"\n";
        reg++;
    }

    static void mult_i32(String val1, String val2){
        main_text += "%"+reg+" = mul i32 "+val1+", "+val2+"\n";
        reg++;
    }

    static void mult_double(String val1, String val2){
        main_text += "%"+reg+" = fmul double "+val1+", "+val2+"\n";
        reg++;
    }

    static void sitofp(String id){
        main_text += "%"+reg+" = sitofp i32 "+id+" to double\n";
        reg++;
    }

    static void fptosi(String id){
        main_text += "%"+reg+" = fptosi double "+id+" to i32\n";
        reg++;
    }

    static void print_int_var(String id) {
        main_text += "%"+reg+" = load i32, i32* %"+id+"\n";
        reg++;
        main_text += "%"+reg+" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strp, i32 0, i32 0), i32 %"+(reg-1)+")\n";
        reg++;
    }

    static void print_double_var(String id) {
        main_text += "%"+reg+" = load double, double* %"+id+"\n";
        reg++;
        main_text += "%"+reg+" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double %"+(reg-1)+")\n";
        reg++;
    }

    static void print_string_var(String id) {
        // TODO: this probably won't work
        main_text += "%"+reg+" = load i32, i32* %"+id+"\n";
        reg++;
        main_text += "%"+reg+" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strp, i32 0, i32 0), i32 %"+(reg-1)+")\n";
        reg++;
    }

    static void print_string(String text) {
        int str_len = text.length();
        String str_type = "[" + (str_len+2) + " x i8]";
        header_text += "@str" + str_i + " = constant" + str_type + " c\"" + text + "\\0A\\00\"\n";
        main_text += "call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( " + str_type + ", " + str_type + "* @str" + str_i + ", i32 0, i32 0))\n";
        str_i++;
    }

    static void input(String id) {
        main_text += "%"+reg+" = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @strs, i32 0, i32 0), i32* %"+id+")\n";
        reg++;
    }

    static void declare_int(String id){
        main_text += "%"+id+" = alloca i32\n";
    }

    static void declare_double(String id){
        main_text += "%"+id+" = alloca double\n";
    }

    static void declare_string(String id){
        // TODO: check if such declaration is sufficient for strings
        //       this is same as int declaration atm
        main_text += "%"+id+" = alloca i32\n";
    }
}
