package com.thighcorp;

import java.util.HashMap;

class LLVMGenerator {

    static String header_text = "";
    static String main_text = "";
    static int str_i = 0;
    static int ch_i = 0;
    static int fun_i = 1;
    HashMap<Integer, Double> memory = new HashMap<Integer, Double>();


    static String generate() {
        String text;
        text = "declare i32 @printf(i8*, ...)\n";
        text += "declare i32 @getchar()\n";
        text += "@.str = global [17 x i8] c\"You entered: %c\\0A\\00\"\n";
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

    static void print(String text) {
        int str_len = text.length();
        String str_type = "[" + (str_len+2) + " x i8]";
        header_text += "@str"+str_i+" = constant" + str_type + " c\"" + text + "\\0A\\00\"\n";
        main_text += "%" + fun_i + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( "+str_type+", "+str_type+"* @str"+str_i+", i32 0, i32 0))\n";
        str_i++;
        fun_i++;
    }
}
