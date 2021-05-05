package com.thighcorp;

class LLVMGenerator {

    static String header_text = "";
    static String main_text = "";
    static String buffer = "";
    static int string_declaration_iter = 0;
    static int reg_iter = 1;

    static String generate() {
        String text = "";
        text += "declare i32 @printf(i8*, ...)\n";
        text += "declare i32 @__isoc99_scanf(i8*, ...)\n";
        text += "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";
        text += "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";
        text += "@strs = constant [3 x i8] c\"%d\\00\"\n";
        text += header_text;
        text += "define i32 @main() nounwind{\n";
        text += main_text;
        text += "ret i32 0 }\n";
        return text;
    }

    static void assign_int(String id, String value) {
        main_text += "store i32 " + value + ", i32* %" + id + "\n";
    }

    static void assign_double(String id, String value) {
        main_text += "store double " + value + ", double* %" + id + "\n";
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

    static void print_int_var(String id) {
        load_int(id);
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 %" + (reg_iter -1) + ")\n";
        reg_iter++;
    }
    /*
    elif var_type == "double":
        var_size = "[4 x i8]"
        string_declaration = f"@str.{self.str_decl_iter} = constant {var_size} c\"%f\\0A\\00\"\n"
    self.str_decl_iter += 1
    self.header_text += string_declaration

    self.buffer += f"%{self.reg_iter} = load {var_type}, {var_type}* %{var_id}\n"
    self.reg_iter += 1
    self.buffer += f"%{self.reg_iter} = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ({var_size}, {var_size}* @str.{self.str_decl_iter-1}, i32 0, i32 0), {var_type} %{self.reg_iter-1})\n"
    self.reg_iter += 1

     */
    static void print_double_var(String id) {
        header_text += "@str" + string_declaration_iter + " = constant [4 x i8] c\"%f\\0A\\00\"\n";
        load_double(id);
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + string_declaration_iter + ", i32 0, i32 0), double %" + (reg_iter-1) + ")\n";
        string_declaration_iter++;
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
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @strs, i32 0, i32 0), i32* %" + id + ")\n";
        reg_iter++;
    }

    static void input_double(String id) {
        main_text += "%" + reg_iter + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds ([6 x i8], [6 x i8]* @strpd, i32 0, i32 0), double* %" + id + ")\n";
        reg_iter++;
    }

    static void load_int(String id) {
        main_text += "%"+ reg_iter +" = load i32, i32* %"+id+"\n";
        reg_iter++;
    }

    static void load_double(String id) {
        main_text += "%" + reg_iter + " = load double, double* %" + id + "\n";
        reg_iter++;
    }

    static void declare_int(String id){
        main_text += "%" + id + " = alloca i32\n";
    }

    static void declare_double(String id){
        main_text += "%" + id + " = alloca double\n";
    }
}
