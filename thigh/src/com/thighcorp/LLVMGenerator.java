package com.thighcorp;

import java.util.Stack;

class LLVMGenerator {

    private static String header = "";
    private static String content = "";
    private static int string_declaration_iter = 0;
    public static int register = 1;
    static int br = 0;
    static Stack<Integer> brStack = new Stack<>();
    private static final String integerStr = "i32";
    private static final String doubleStr = "double";
    static String fun = "";
    static int fun_reg = 1;

    static String generate() {
        String text = "";
        text += "declare i32 @printf(i8*, ...)\n";
        text += "declare i32 @__isoc99_scanf(i8*, ...)\n";
        text += "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";
        text += "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";
        text += header;
        text += "define i32 @main() nounwind{\n";
        text += content;
        text += "ret i32 0 }\n";
        reset();
        return text;
    }

//    static void assign(String id, String value, VarType type) {
//        String assignmentTemplate = "store %s " + value + ", %s* %%" + id + "\n";
//        switch (type) {
//            case INT -> content += String.format(assignmentTemplate, integerStr, integerStr);
//            case DOUBLE -> content += String.format(assignmentTemplate, doubleStr, doubleStr);
//        }
//    }

    static void assignInt(String id, String value, boolean main, boolean isGlobal){
        String globalOrLocal;
        if(isGlobal){
            globalOrLocal = "@";
        }else{
            globalOrLocal = "%";
        }
        if(main){
            content += "store i32 "+value+", i32* "+ globalOrLocal +id+"\n";
        }else{
            fun += "store i32 "+value+", i32* "+globalOrLocal+id+"\n";
        }
    }

    static void assignDouble(String id, String value, boolean main, boolean isGlobal){
        String globalOrLocal;
        if(isGlobal){
            globalOrLocal = "@";
        }else{
            globalOrLocal = "%";
        }
        if(main){
            content += "store double "+value+", double* "+ globalOrLocal +id+"\n";
        }else{
            fun += "store double "+value+", double* "+globalOrLocal+id+"\n";
        }
    }

//    static void printVariable(String id, VarType type) {
//        // TODO: create common string template for printing both ints and doubles
//        if (type == VarType.INT) {
//            declareString(VarType.INT);
//            loadVariable(id, VarType.INT);
//            content += "%" + register + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + (string_declaration_iter - 1) + ", i32 0, i32 0), i32 %" + (register -1) + ")\n";
//            register++;
//        } else if (type == VarType.DOUBLE) {
//            declareString(VarType.DOUBLE);
//            loadVariable(id, VarType.DOUBLE);
//            content += "%" + register + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @str" + (string_declaration_iter - 1) + ", i32 0, i32 0), double %" + (register -1) + ")\n";
//            register++;
//        }
//    }

    static void printString(String text) {
        int str_len = text.length();
        String str_type = "[" + (str_len+2) + " x i8]";
        header += "@str" + string_declaration_iter + " = constant" + str_type + " c\"" + text + "\\0A\\00\"\n";
        content += "%"+ register +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ( " + str_type + ", " + str_type + "* @str" + string_declaration_iter + ", i32 0, i32 0))\n";
        string_declaration_iter++;
        register++;
    }

//    static void inputVariable(String id, VarType type) {
//        var headerTemplate = "@str" + string_declaration_iter + " = constant %s";
//        var mainTemplate = "%%" + register + " = call i32 (i8*, ...) @__isoc99_scanf(i8* getelementptr inbounds (%s* @str"
//                + string_declaration_iter + ", i32 0, i32 0), %s* %%" + id + ")\n";
//        switch (type) {
//            case INT -> {
//                header += String.format(headerTemplate, "[3 x i8] c\"%d\\00\"\n");
//                content += String.format(mainTemplate, "[3 x i8], [3 x i8]", integerStr);
//            } case DOUBLE -> {
//                header += String.format(headerTemplate, "[4 x i8] c\"%lf\\00\"\n");;
//                content += String.format(mainTemplate, "[4 x i8], [4 x i8]", doubleStr);
//            } case STRING -> {
//                // TODO
//            }
//        }
//        string_declaration_iter++;
//        register++;
//    }

    static void loadVariable(String id, VarType type, boolean is_in_main) {
        String loadingTemplate = "%%"+ register +" = load %s, %s* " + getScopeIdentifier(is_in_main)+id+"\n";
        switch (type) {
            case INT -> content += String.format(loadingTemplate, integerStr, integerStr);
            case DOUBLE -> content += String.format(loadingTemplate, doubleStr, doubleStr);
        }
        register++;
    }

//    static void declareVariable(String id, VarType type) {
//        String declarationTemplate = "%%" + id + " = alloca %s\n";
//        switch (type) {
//            case INT -> content += String.format(declarationTemplate, integerStr);
//            case DOUBLE -> content += String.format(declarationTemplate, doubleStr);
//        }
//    }

    static void declareInt(String id, boolean main){
        if(main){
            header += "@"+id+" = common global i32 0, align 4\n";
        }else{
            fun += "%"+id+" = alloca i32\n";
        }
    }

    static void declareDouble(String id, boolean main){
        if(main){
            header += "@"+id+" = common global double 0.000000e+00, align 8\n";
        }else{
            fun += "%"+id+" = alloca double\n";
        }
    }

    static void declareInt(String id, boolean isInMain, boolean isGlobal){
        String globalOrLocal;
        if(isGlobal){
            globalOrLocal = "@";
        }else{
            globalOrLocal = "%";
        }
        if(isInMain && isGlobal){
            header += globalOrLocal+id+" = common global i32 0, align 4\n";
        }else if(isInMain){
            content += globalOrLocal+id+" = alloca i32\n";
        }else{
            fun += globalOrLocal+id+" = alloca i32\n";
        }
    }

    static void declareDouble(String id, boolean main, boolean isGlobal){
        String globalOrLocal;
        if(isGlobal){
            globalOrLocal = "@";
        }else{
            globalOrLocal = "%";
        }
        if(main && isGlobal){
            header += globalOrLocal+id+" = common global double 0.000000e+00, align 8\n";
        }else if(main){
            content += globalOrLocal+id+" = alloca double\n";
        }else{
            fun += globalOrLocal+id+" = alloca double\n";
        }
    }

    static void scanfInt(String id, VarScope scope, boolean isMain){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(isMain){
            content += "%" + register + " = call i32 (i8*, ...)* @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32* " + varType + id + ") \n";
            register++;
        }else{
            fun += "%" + fun_reg + " = call i32 (i8*, ...)* @__isoc99_scanf(i8* getelementptr inbounds ([3 x i8]* @.str, i32 0, i32 0), i32* " + varType + id + ") \n";
            fun_reg++;
        }
    }

    static void scanfDouble(String id, VarScope scope, boolean isMain){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(isMain){
            content += "%" + register + " = call i32 (i8*, ...)* @__isoc99_scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str1, i32 0, i32 0), double, double* " + varType + id + ") \n";
            register++;
        }else{
            fun += "%" + fun_reg + " = call i32 (i8*, ...)* @__isoc99_scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str1, i32 0, i32 0), double, double* " + varType + id + ") \n";
            fun_reg++;
        }

    }

    static void printfInt(String id, boolean main, boolean isGlobal){
        String varType;
        if(isGlobal){
            varType = "@";
        }else{
            varType = "%";
        }
        if(main){
            content += "%" + register +" = load i32, i32* " + varType +id+"\n";
            register++;
            content += "%"+ register +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 %" +(register -1)+")\n";
            register++;
        }else{
            fun += "%"+ fun_reg +" = load i32, i32* " + varType +id+"\n";
            fun_reg++;
            fun += "%"+ fun_reg +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 %" +(fun_reg -1)+")\n";
            fun_reg++;
        }
    }

    static void printfDouble(String id, boolean main, boolean isGlobal){
        String varType;
        if(isGlobal){
            varType = "@";
        }else{
            varType = "%";
        }

        if(main){
            content += "%" + register +" = load double, double* " + varType +id+"\n";
            register++;
            content += "%"+ register +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double %" +(register -1)+")\n";
            register++;
        }else{
            fun += "%"+ fun_reg +" = load double, double* " + varType +id+"\n";
            fun_reg++;
            fun += "%"+ fun_reg +" = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double %" +(fun_reg -1)+")\n";
            fun_reg++;
        }
    }

//    static void declareString(VarType type) {
//        String declarationTemplate = "@str" + string_declaration_iter + " = constant [4 x i8] %s";
//        switch (type) {
//            case INT -> header += String.format(declarationTemplate, "c\"%d\\0A\\00\"\n");
//            case DOUBLE -> header += String.format(declarationTemplate, "c\"%f\\0A\\00\"\n");
//        }
//        string_declaration_iter++;
//    }

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

    public static void increase(String id, VarType type, boolean is_in_main) {
        int intSize = 4;
        int doubleSize = 8;
        var increaseTemplate = "%%" + (register + 1) + " = %s %s %%" + register + ", %s\n" + "store %s %%" + (register + 1) + ", %s* " + getScopeIdentifier(is_in_main) + id + ", align %s\n";
        switch (type) {
            case INT -> {
                loadVariable(id, VarType.INT, is_in_main);
                content += String.format(increaseTemplate, "add nsw", integerStr, 1, integerStr, integerStr, intSize);
            }
            case DOUBLE -> {
                loadVariable(id, VarType.DOUBLE, is_in_main);
                content += String.format(increaseTemplate, "fadd ", doubleStr, "1.000000e+00", doubleStr, doubleStr, doubleSize);
            }
        }
        register++;
    }

    public static void decrease(String id, VarType type, boolean is_in_main) {
        int intSize = 4;
        int doubleSize = 8;
        var increaseTemplate = "%%" + (register + 1) + " = %s %s %%" + register + ", %s\n" + "store %s %%" + (register + 1) + ", %s* " + getScopeIdentifier(is_in_main) + id + ", align %s\n";
        switch (type) {
            case INT -> {
                loadVariable(id, VarType.INT, is_in_main);
                content += String.format(increaseTemplate, "sub nsw", integerStr, 1, integerStr, integerStr, intSize);
            }
            case DOUBLE -> {
                loadVariable(id, VarType.DOUBLE, is_in_main);
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

    public static void declareWhileCondInt(String id, String value, Comparator c, boolean is_in_main, VarScope scope) {
        br++;
        String signType = "";
        if (c == Comparator.EQUAL) {
            signType = "eq";
        }
        if (c == Comparator.GREATER) {
            signType = "sgt";
        }
        if (c == Comparator.LESS) {
            signType = "slt";
        }
        String varType;
        if (scope == VarScope.GLOBAL) {
            varType = "@";
        } else {
            varType = "%";
        }
        if (is_in_main) {
            content += "br label %cond" + br + "\n";
            content += "cond" + br + ":\n";

            content += "%" + register + " = load i32, i32* " + varType + id + "\n";
            register++;

            content += "%" + register + " = icmp " + signType + " i32 %" + (register - 1) + ", " + value + "\n";
            register++;

            content += "br i1 %" + (register - 1) + ", label %true" + br + ", label %false" + br + "\n";
            content += "true" + br + ":\n";
        } else {
            fun += "br label %cond" + br + "\n";
            fun += "cond" + br + ":\n";

            fun += "%" + fun_reg + " = load i32, i32* " + varType + id + "\n";
            fun_reg++;

            fun += "%" + fun_reg + " = icmp " + signType + " i32 %" + (fun_reg - 1) + ", " + value + "\n";
            fun_reg++;

            fun += "br i1 %" + (fun_reg - 1) + ", label %true" + br + ", label %false" + br + "\n";
            fun += "true" + br + ":\n";
        }
        brStack.push(br);
    }

    public static void declareWhileCondDouble(String id, String value, Comparator sign, boolean is_in_main, VarScope scope) {
        br++;
        String signType = "";
        if(sign == Comparator.EQUAL){
            signType = "oeq";
        }
        if(sign == Comparator.GREATER){
            signType = "ogt";
        }
        if(sign == Comparator.LESS){
            signType = "olt";
        }

        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }

        if(is_in_main){
            content += "br label %cond"+br+"\n";
            content += "cond"+br+":\n";

            content += "%"+register+" = load double* "+varType+id+"\n";
            register++;

            content += "%"+register+" = fcmp " + signType +  " double %"+(register-1)+", " + value + "\n";
            register++;

            content += "br i1 %"+(register-1)+", label %true"+br+", label %false"+br+"\n";
            content += "true"+br+":\n";
        }else{
            fun += "br label %cond"+br+"\n";
            fun += "cond"+br+":\n";

            fun += "%"+fun_reg+" = load double* "+varType+id+"\n";
            fun_reg++;

            fun += "%"+fun_reg+" = fcmp " + signType +  " double %"+(fun_reg-1)+", " + value + "\n";
            fun_reg++;

            fun += "br i1 %"+(fun_reg-1)+", label %true"+br+", label %false"+br+"\n";
            fun += "true"+br+":\n";

        }
        brStack.push(br);
    }

    public static void endWhile(boolean is_in_main) {
        int b = brStack.pop();
        if(is_in_main){
            content += "br label %cond"+b+"\n";
            content += "false"+b+":\n";
        }else{
            fun += "br label %cond"+b+"\n";
            fun += "false"+b+":\n";
        }
        br++;
    }

    static void icmpRealEquallIdId(String id_1, String id_2, VarScope s_1, VarScope s_2, boolean main){
        String varType_1;
        String varType_2;

        if(s_1 == VarScope.GLOBAL){
            varType_1 = "@";
        }else{
            varType_1 = "%";
        }

        if(s_2 == VarScope.GLOBAL){
            varType_2 = "@";
        }else{
            varType_2 = "%";
        }

        if(main){
            content += "%"+register+" = load double* " + varType_1 + id_1 + "\n";
            register++;
            content += "%"+register+" = load double* "+ varType_2 + id_2 +"\n";
            register++;
            content += "%"+register+" = fcmp oeq double %"+(register-1)+", %" +(register-2)+ "\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load double* " + varType_1 + id_1 + "\n";
            fun_reg++;
            fun += "%"+fun_reg+" = load double* "+ varType_2 + id_2 +"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = fcmp oeq double %"+(fun_reg-1)+", %" +(fun_reg-2)+ "\n";
            fun_reg++;
        }
    }

    static void icmpRealMoreIdId(String id_1, String id_2, VarScope s_1, VarScope s_2, boolean main){
        String varType_1;
        String varType_2;

        if(s_1 == VarScope.GLOBAL){
            varType_1 = "@";
        }else{
            varType_1 = "%";
        }

        if(s_2 == VarScope.GLOBAL){
            varType_2 = "@";
        }else{
            varType_2 = "%";
        }

        if(main){
            content += "%"+register+" = load double* " + varType_1 + id_1 + "\n";
            register++;
            content += "%"+register+" = load double* "+ varType_2 + id_2 +"\n";
            register++;
            content += "%"+register+" = fcmp ogt double %"+(register-1)+", %" +(register-2)+ "\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load double* " + varType_1 + id_1 + "\n";
            fun_reg++;
            fun += "%"+fun_reg+" = load double* "+ varType_2 + id_2 +"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = fcmp ogt double %"+(fun_reg-1)+", %" +(fun_reg-2)+ "\n";
            fun_reg++;
        }

    }

    static void icmpRealLessIdId(String id_1, String id_2, VarScope s_1, VarScope s_2, boolean main){
        String varType_1;
        String varType_2;

        if(s_1 == VarScope.GLOBAL){
            varType_1 = "@";
        }else{
            varType_1 = "%";
        }

        if(s_2 == VarScope.GLOBAL){
            varType_2 = "@";
        }else{
            varType_2 = "%";
        }

        if(main){
            content += "%"+register+" = load double* " + varType_1 + id_1 + "\n";
            register++;
            content += "%"+register+" = load double* "+ varType_2 + id_2 +"\n";
            register++;
            content += "%"+register+" = fcmp olt double %"+(register-1)+", %" +(register-2)+ "\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load double* " + varType_1 + id_1 + "\n";
            fun_reg++;
            fun += "%"+fun_reg+" = load double* "+ varType_2 + id_2 +"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = fcmp olt double %"+(fun_reg-1)+", %" +(fun_reg-2)+ "\n";
            fun_reg++;
        }

    }

    static void icmpRealEquall(String id, String value, VarScope scope, boolean main){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(main){
            content += "%"+register+" = load double* "+varType+id+"\n";
            register++;
            content += "%"+register+" = fcmp oeq double %"+(register-1)+", "+value+"\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load double* "+varType+id+"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = fcmp oeq double %"+(fun_reg-1)+", "+value+"\n";
            fun_reg++;
        }

    }

    static void icmpRealMore(String id, String value, VarScope scope, boolean main){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(main){
            content += "%"+register+" = load double* "+varType+id+"\n";
            register++;
            content += "%"+register+" = fcmp ogt double %"+(register-1)+", "+value+"\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load double* "+varType+id+"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = fcmp ogt double %"+(fun_reg-1)+", "+value+"\n";
            fun_reg++;
        }

    }

    static void icmpRealLess(String id, String value, VarScope scope, boolean main){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(main){
            content += "%"+register+" = load double* "+varType+id+"\n";
            register++;
            content += "%"+register+" = fcmp olt double %"+(register-1)+", "+value+"\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load double* "+varType+id+"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = fcmp olt double %"+(fun_reg-1)+", "+value+"\n";
            fun_reg++;
        }

    }

    static void icmpIntEquallIdId(String id_1, String id_2, VarScope s_1, VarScope s_2, boolean main){
        String varType_1;
        String varType_2;

        if(s_1 == VarScope.GLOBAL){
            varType_1 = "@";
        }else{
            varType_1 = "%";
        }

        if(s_2 == VarScope.GLOBAL){
            varType_2 = "@";
        }else{
            varType_2 = "%";
        }

        if(main){
            content += "%"+register+" = load i32, i32* " + varType_1 + id_1 + "\n";
            register++;
            content += "%"+register+" = load i32, i32* "+ varType_2 + id_2 +"\n";
            register++;
            content += "%"+register+" = icmp eq i32 %"+(register-1)+", %" +(register-2)+ "\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load i32, i32* " + varType_1 + id_1 + "\n";
            fun_reg++;
            fun += "%"+fun_reg+" = load i32, i32* "+ varType_2 + id_2 +"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = icmp eq i32 %"+(fun_reg-1)+", %" +(fun_reg-2)+ "\n";
            fun_reg++;
        }
    }

    static void icmpIntMoreIdId(String id_1, String id_2, VarScope s_1, VarScope s_2, boolean main){
        String varType_1;
        String varType_2;

        if(s_1 == VarScope.GLOBAL){
            varType_1 = "@";
        }else{
            varType_1 = "%";
        }

        if(s_2 == VarScope.GLOBAL){
            varType_2 = "@";
        }else{
            varType_2 = "%";
        }

        if(main){
            content += "%"+register+" = load i32, i32* " + varType_1 + id_1 + "\n";
            register++;
            content += "%"+register+" = load i32, i32* "+ varType_2 + id_2 +"\n";
            register++;
            content += "%"+register+" = icmp sgt i32 %"+(register-1)+", %" +(register-2)+ "\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load i32, i32* " + varType_1 + id_1 + "\n";
            fun_reg++;
            fun += "%"+fun_reg+" = load i32, i32* "+ varType_2 + id_2 +"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = icmp sgt i32 %"+(fun_reg-1)+", %" +(fun_reg-2)+ "\n";
            fun_reg++;
        }

    }

    static void icmpIntLessIdId(String id_1, String id_2, VarScope s_1, VarScope s_2, boolean main){
        String varType_1;
        String varType_2;

        if(s_1 == VarScope.GLOBAL){
            varType_1 = "@";
        }else{
            varType_1 = "%";
        }

        if(s_2 == VarScope.GLOBAL){
            varType_2 = "@";
        }else{
            varType_2 = "%";
        }

        if(main){
            content += "%"+register+" = load i32, i32* " + varType_1 + id_1 + "\n";
            register++;
            content += "%"+register+" = load i32, i32* "+ varType_2 + id_2 +"\n";
            register++;
            content += "%"+register+" = icmp slt i32 %"+(register-1)+", %" +(register-2)+ "\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load i32, i32* " + varType_1 + id_1 + "\n";
            fun_reg++;
            fun += "%"+fun_reg+" = load i32, i32* "+ varType_2 + id_2 +"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = icmp slt i32 %"+(fun_reg-1)+", %" +(fun_reg-2)+ "\n";
            fun_reg++;
        }
    }

    static void icmpIntEquall(String id, String value, VarScope scope, boolean main){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(main){
            content += "%"+register+" = load i32, i32* "+varType+id+"\n";
            register++;
            content += "%"+register+" = icmp eq i32 %"+(register-1)+", "+value+"\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load i32, i32* "+varType+id+"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = icmp eq i32 %"+(fun_reg-1)+", "+value+"\n";
            fun_reg++;
        }
    }

    static void icmpIntMore(String id, String value, VarScope scope, boolean main){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(main){
            content += "%"+register+" = load i32, i32* "+varType+id+"\n";
            register++;
            content += "%"+register+" = icmp sgt i32 %"+(register-1)+", "+value+"\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load i32, i32* "+varType+id+"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = icmp sgt i32 %"+(fun_reg-1)+", "+value+"\n";
            fun_reg++;
        }
    }

    static void icmpIntLess(String id, String value, VarScope scope, boolean main){
        String varType;
        if(scope == VarScope.GLOBAL){
            varType = "@";
        }else{
            varType = "%";
        }
        if(main){
            content += "%"+register+" = load i32, i32* "+varType+id+"\n";
            register++;
            content += "%"+register+" = icmp slt i32 %"+(register-1)+", "+value+"\n";
            register++;
        }else{
            fun += "%"+fun_reg+" = load i32, i32* "+varType+id+"\n";
            fun_reg++;
            fun += "%"+fun_reg+" = icmp slt i32 %"+(fun_reg-1)+", "+value+"\n";
            fun_reg++;
        }
    }

    static void ifstart(boolean main){
        br++;
        if(main){
            content += "br i1 %"+(register-1)+", label %true"+br+", label %false"+br+"\n";
            content += "true"+br+":\n";
        }else{
            fun += "br i1 %"+(fun_reg-1)+", label %true"+br+", label %false"+br+"\n";
            fun += "true"+br+":\n";
        }
        brStack.push(br);
    }

    static void ifend(boolean main){
        int b = brStack.pop();
        if(main){
            content += "br label %false"+b+"\n";
            content += "false"+b+":\n";
        }else{
            fun += "br label %false"+b+"\n";
            fun += "false"+b+":\n";
        }
        br++;
    }

    private static String getScopeIdentifier(boolean isInMain) {
        if (isInMain)
            return "@";
        else
            return "%%";
    }
}
