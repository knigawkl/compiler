package com.thighcorp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

enum VarType{INT, DOUBLE, STRING, UNKNOWN}
enum VarScope {GLOBAL, LOCAL, NONE, BLOCK}

class Value {
    public String val;
    public String name;
    public String declaredName;
    public VarType type;
    public boolean isGlobal;
    public Value(String val, VarType type, boolean isGlobal) {
        this.val = val;
        this.type = type;
        this.isGlobal = isGlobal;
    }

    public Value(String name, VarType type) {
        this.val = name;
        this.type = type;
        this.isGlobal = false;
    }
}

public class ThighCustomListener extends ThighBaseListener {
    private final String out_file;

    HashMap<String, VarType> variables = new HashMap<>();
    HashMap<String, VarType> global_variables = new HashMap<>();
    HashMap<String, VarType> fun_variables = new HashMap<>();
    Stack<Value> stack = new Stack<>();

    boolean is_in_main = true;
    boolean is_in_block = false;

    Stack<String> blockStack = new Stack<>();
    Stack<Value> blockValues = new Stack<>();
    Stack<Integer> valCounter = new Stack<>();

    public ThighCustomListener(String ll_file) {
        out_file = ll_file;
    }

    @Override
    public void exitProgram(ThighParser.ProgramContext ctx) {
        String intermediate_representation = LLVMGenerator.generate();
        FileWriter llWriter = null;
        try {
            llWriter = new FileWriter(out_file);
            llWriter.write(intermediate_representation);
            llWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitPrint(ThighParser.PrintContext ctx) {
//        var variableName = getVariableName(ctx);
//        VarType type = variables.get(variableName);
//        boolean isVariable = (type != null);
//        if (isVariable) {
//            switch (type) {
//                case INT -> LLVMGenerator.printVariable(variableName, VarType.INT);
//                case DOUBLE -> LLVMGenerator.printVariable(variableName, VarType.DOUBLE);
//                case STRING -> LLVMGenerator.printString(strMemory.get(variableName));
//            }
//        } else if (ctx.value().INT() != null) {
//            LLVMGenerator.printString(ctx.value().INT().getText());
//        } else if (ctx.value().DOUBLE() != null) {
//            LLVMGenerator.printString(ctx.value().DOUBLE().getText());
//        } else if (ctx.value().STRING() != null) {
//            String str = ctx.value().STRING().getText();
//            LLVMGenerator.printString(str.substring(1, str.length()-1));
//        }
        String ID = getVariableName(ctx);
        VarScope varScope = checkVarScope(ID);

        if (varScope == VarScope.NONE) {
            printError(ctx.getStart().getLine(), "zmienna ta nie jest zadeklarowana: " + ID);
        }
        VarType varType = checkVarType(ID);
        if (varScope == VarScope.LOCAL) {
            if (varType == VarType.INT) {
                LLVMGenerator.printfInt(ID, is_in_main, false);
            }
            if (varType == VarType.DOUBLE) {
                LLVMGenerator.printfDouble(ID, is_in_main, false);
            }
        }
        if (varScope == VarScope.GLOBAL) {
            if (varType == VarType.INT) {
                LLVMGenerator.printfInt(ID, is_in_main, true);
            }
            if (varType == VarType.DOUBLE) {
                LLVMGenerator.printfDouble(ID, is_in_main, true);
            }
        }
        if (varScope == VarScope.BLOCK) {
            Value v = getValueFromBlock(ID);
            if (varType == VarType.INT) {
                LLVMGenerator.printfInt(v.declaredName, is_in_main, false);
            }
            if (varType == VarType.DOUBLE) {
                LLVMGenerator.printfDouble(v.declaredName, is_in_main, false);
            }
        }
    }

    @Override
    public void exitRead(ThighParser.ReadContext ctx) {
//        String variableName = ctx.ID().getText();
//        var input_type = ctx.type().getText();
//
//        if (!variables.containsKey(variableName)) {
//            switch (input_type) {
//                case "int" -> {
//                    variables.put(variableName, VarType.INT);
//                    LLVMGenerator.declareVariable(variableName, VarType.INT);
//                    LLVMGenerator.inputVariable(variableName, VarType.INT);
//                }
//                case "double" -> {
//                    variables.put(variableName, VarType.DOUBLE);
//                    LLVMGenerator.declareVariable(variableName, VarType.DOUBLE);
//                    LLVMGenerator.inputVariable(variableName, VarType.DOUBLE);
//                }
//                case "string" -> {
//                    variables.put(variableName, VarType.STRING);
//                    // TODO
//                }
//            }
//        }
        String ID = ctx.ID().getText();
        VarScope varScope;
        if(is_in_block){
            VarType type = null;
            if (checkVarScope(ID) == VarScope.NONE) {
                Integer counter = valCounter.pop();
                counter++;
                valCounter.push(counter);
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(1000000);
                type = null;
                if (ctx.type().getText().equals("double")){
                    type = VarType.DOUBLE;
                }
                if(ctx.type().getText().equals("int")){
                    type = VarType.INT;
                }
                Value v = new Value(ID, type, false);
                v.isGlobal = false;
                v.val = ID;
                v.declaredName = v.val + randomInt;
                ID = v.declaredName;
                if(type == VarType.INT){
                    LLVMGenerator.declareInt(v.declaredName, is_in_main, false);
                }
                if(type == VarType.DOUBLE){
                    LLVMGenerator.declareDouble(v.declaredName, is_in_main, false);
                }
                blockValues.push(v);
                varScope = VarScope.BLOCK;
            } else {
                // the variable is already declared
                if (ctx.type().getText().equals("double")){
                    type = VarType.DOUBLE;
                }
                if(ctx.type().getText().equals("int")){
                    type = VarType.INT;
                }
                if (checkVarType(ID) != type) {
                    printError(ctx.getStart().getLine(), ctx.ID().getText() + " : przedtem zmienna byla innego typu.");
                }
                varScope = checkVarScope(ID);
            }
            if(checkVarScope(ID) == VarScope.BLOCK){
                Value v = getValueFromBlock(ID);
                ID = v.declaredName;
            }
            if (ctx.type().getText().equals("double")){
                LLVMGenerator.scanfDouble(ID, varScope, is_in_main);
            }
            if(ctx.type().getText().equals("int")){
                LLVMGenerator.scanfInt(ID, varScope, is_in_main);
            }
        }else{
            if (ctx.type().getText().equals("int")) {
                if (checkVarScope(ID) == VarScope.NONE) {
                    if (is_in_main) {
                        varScope = VarScope.GLOBAL;
                    } else {
                        varScope = VarScope.LOCAL;
                    }
                    variables.put(ID, VarType.INT);
                    if (is_in_main) {
                        global_variables.put(ID, VarType.INT);
                    } else {
                        fun_variables.put(ID, VarType.INT);
                    }
                    LLVMGenerator.declareInt(ID, is_in_main);
                } else {
                    varScope = checkVarScope(ID);
                    if (checkVarType(ID) != VarType.INT) {
                        System.err.println("variable has a different type ");
                    }
                }
                LLVMGenerator.scanfInt(ID, varScope, is_in_main);
            }

            if (ctx.type().getText().equals("double")) {
                if (checkVarScope(ID) == VarScope.NONE) {
                    if (is_in_main) {
                        varScope = VarScope.GLOBAL;
                    } else {
                        varScope = VarScope.LOCAL;
                    }
                    variables.put(ID, VarType.DOUBLE);
                    if (is_in_main) {
                        global_variables.put(ID, VarType.DOUBLE);
                    } else {
                        fun_variables.put(ID, VarType.DOUBLE);
                    }
                    LLVMGenerator.declareDouble(ID, is_in_main);
                } else {
                    varScope = checkVarScope(ID);
                    if (checkVarType(ID) != VarType.DOUBLE) {
                        System.err.println("variable has a different type ");
                    }
                }
                LLVMGenerator.scanfDouble(ID, varScope, is_in_main);
            }
        }
    }

//    @Override
//    public void exitAssign(ThighParser.AssignContext ctx) {
//        var variableName = ctx.ID().getText();
//        Value v = stack.pop();
//        variables.put(variableName, v.type);
//        if (v.val.charAt(0) == '%') {
//            v.val = "%" + v.val;
//        }
//        if (v.type == VarType.INT) {
//            LLVMGenerator.declareVariable(variableName, VarType.INT);
//            LLVMGenerator.assign(variableName, v.val, VarType.INT);
//        } else if (v.type == VarType.DOUBLE) {
//            LLVMGenerator.declareVariable(variableName, VarType.DOUBLE);
//            LLVMGenerator.assign(variableName, v.val, VarType.DOUBLE);
//        } else if (v.type == VarType.STRING) {
//            String tmp = ctx.assign_value().getText();
//            tmp = tmp.substring(1, tmp.length()-1);
//            strMemory.put(variableName, tmp);
//        }
//    }

    @Override
    public void exitAssign(ThighParser.AssignContext ctx) {
        String ID = ctx.ID().getText();
        Value v = stack.pop();
        if (is_in_block) {
            if (v.type == VarType.INT) {
                if (checkVarScope(ID) == VarScope.NONE) {
                    Integer counter = valCounter.pop();
                    counter++;
                    valCounter.push(counter);
                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(1000000);
                    v.name = ID;
                    v.declaredName = v.name + randomInt;
                    LLVMGenerator.declareInt(v.declaredName, is_in_main, false);
                    blockValues.push(v);
                } else {
                    if (checkVarType(ID) != VarType.INT) {
                        printError(ctx.getStart().getLine(), ID + " : przedtem zmienna byla innego typu.");
                    }
                    if(checkVarScope(ID) == VarScope.BLOCK){
                        Value tmp = getValueFromBlock(ID);
                        v.declaredName = tmp.declaredName;
                    }else{
                        v.declaredName = ID;
                    }
                }
                LLVMGenerator.assignInt(v.declaredName, v.val, is_in_main, false);
            }
            if(v.type == VarType.DOUBLE){
                if (checkVarScope(ID) == VarScope.NONE) {
                    Integer counter = valCounter.pop();
                    counter++;
                    valCounter.push(counter);
                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(1000000);
                    v.name = ID;
                    v.declaredName = v.name + randomInt;
                    LLVMGenerator.declareDouble(v.declaredName, is_in_main, false);
                    blockValues.push(v);
                } else {
                    if (checkVarType(ID) != VarType.DOUBLE) {
                        printError(ctx.getStart().getLine(), ID + " : przedtem zmienna byla innego typu.");
                    }
                    if(checkVarScope(ID) == VarScope.BLOCK){
                        Value tmp = getValueFromBlock(ID);
                        v.declaredName = tmp.declaredName;
                    }else{
                        v.declaredName = ID;
                    }
                }
                LLVMGenerator.assignDouble(v.declaredName, v.val, is_in_main, false);
            }
        }else {
            if (v.type == VarType.INT) {
                if (checkVarScope(ID) == VarScope.NONE) {
                    LLVMGenerator.declareInt(ID, is_in_main);
                } else {
                    if (checkVarType(ID) != VarType.INT) {
                        printError(ctx.getStart().getLine(), ID + " : przedtem zmienna byla innego typu.");
                    }
                }
                if (v.isGlobal) {
                    global_variables.put(ID, VarType.INT);
                } else {
                    fun_variables.put(ID, VarType.INT);
                }
                boolean isGlobal;
                isGlobal = checkVarScope(ID) == VarScope.GLOBAL;
                LLVMGenerator.assignInt(ID, v.val, is_in_main, isGlobal);
            }
            if(v.type == VarType.DOUBLE){
                if (checkVarScope(ID) == VarScope.NONE) {
                    LLVMGenerator.declareDouble(ID, is_in_main);
                } else {
                    if (checkVarType(ID) != VarType.DOUBLE) {
                        printError(ctx.getStart().getLine(), ID + " : przedtem zmienna byla innego typu.");
                    }
                }
                if (v.isGlobal) {
                    global_variables.put(ID, VarType.DOUBLE);
                } else {
                    fun_variables.put(ID, VarType.DOUBLE);
                }
                boolean isGlobal;
                isGlobal = checkVarScope(ID) == VarScope.GLOBAL;
                LLVMGenerator.assignDouble(ID, v.val, is_in_main, isGlobal);
            }
        }
    }

    @Override
    public void exitArithmeticOperation(ThighParser.ArithmeticOperationContext ctx) {

    }

    @Override
    public void exitAdditionOperation(ThighParser.AdditionOperationContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();
        if (v1.type == v2.type) {
            if (v1.type == VarType.INT) {
                LLVMGenerator.add(v1.val, v2.val, VarType.INT);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.INT, is_in_main));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.add(v1.val, v2.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.DOUBLE, is_in_main));
            }
        } else {
            System.err.printf("add type mismatch at line %s%n", ctx.getStart().getLine());
        }
    }

    @Override
    public void exitSubtractionOperation(ThighParser.SubtractionOperationContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();
        if (v1.type == v2.type) {
            if (v1.type == VarType.INT) {
                LLVMGenerator.sub(v2.val, v1.val, VarType.INT);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.INT, is_in_main));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.sub(v2.val, v1.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.DOUBLE, is_in_main));
            }
        } else {
            System.err.printf("substraction type mismatch at line %s%n", ctx.getStart().getLine());
        }
    }

    @Override
    public void exitDivisionOperation(ThighParser.DivisionOperationContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();

        if (v1.val.matches("0(([.][0]+)|([.]))?")){
            System.err.printf("division by 0 at line %s%n", ctx.getStart().getLine());
            return;
        }

        if (v1.type == v2.type) {
            if (v1.type == VarType.INT) {
                LLVMGenerator.div(v2.val, v1.val, VarType.INT);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.INT, is_in_main));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.div(v2.val, v1.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.DOUBLE, is_in_main));
            }
        } else {
            System.err.printf("division type mismatch at line %s%n", ctx.getStart().getLine());
        }
    }

    @Override
    public void exitMultiplicationOperation(ThighParser.MultiplicationOperationContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();
        if (v1.type == v2.type) {
            if (v1.type == VarType.INT) {
                LLVMGenerator.mul(v1.val, v2.val, VarType.INT);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.INT, is_in_main));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.mul(v1.val, v2.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.DOUBLE, is_in_main));
            }
        } else {
            System.err.printf("multiplication type mismatch at line %s%n", ctx.getStart().getLine());
        }
    }

    @Override
    public void exitModuloOperation(ThighParser.ModuloOperationContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();
        if (v1.type == v2.type) {
            if (v1.type == VarType.INT) {
                LLVMGenerator.mod(v1.val, v2.val, VarType.INT);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.INT, is_in_main));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.mod(v1.val, v2.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.register -1), VarType.DOUBLE, is_in_main));
            }
        } else {
            System.err.printf("multiplication type mismatch at line %s%n", ctx.getStart().getLine());
        }
    }

    @Override
    public void exitCast(ThighParser.CastContext ctx) {
        if (ctx.TODOUBLE() != null){
            Value v = stack.pop();
            LLVMGenerator.castToDouble( v.val );
            stack.push(new Value("%"+(LLVMGenerator.register - 1), VarType.DOUBLE, is_in_main));
        }else if(ctx.TOINT() != null){
            Value v = stack.pop();
            LLVMGenerator.castToInt( v.val );
            stack.push(new Value("%"+(LLVMGenerator.register - 1), VarType.INT, is_in_main));
        }
    }

    @Override
    public void exitIncrement(ThighParser.IncrementContext ctx) {
        var variableName = ctx.ID().getText();
        switch (ctx.type().getText()) {
            case "int" -> LLVMGenerator.increase(variableName, VarType.INT, is_in_main);
            case "double" -> LLVMGenerator.increase(variableName, VarType.DOUBLE, is_in_main);
        }
    }

    @Override
    public void exitDecrement(ThighParser.DecrementContext ctx) {
        var variableName = ctx.ID().getText();
        switch (ctx.type().getText()) {
            case "int" -> LLVMGenerator.decrease(variableName, VarType.INT, is_in_main);
            case "double" -> LLVMGenerator.decrease(variableName, VarType.DOUBLE, is_in_main);
        }
    }

    @Override
    public void exitValue(ThighParser.ValueContext ctx) {
        if (ctx.INT() != null) {
            stack.push(new Value(ctx.INT().getText(), VarType.INT, is_in_main));
        } else if (ctx.DOUBLE() != null) {
            stack.push(new Value(ctx.DOUBLE().getText(), VarType.DOUBLE, is_in_main));
        } else if (ctx.STRING() != null) {
            String str = ctx.STRING().getText();
            stack.push(new Value(str.substring(1, str.length()-1), VarType.STRING));
        } else {
            // TODO: probably this block of code can be deleted
            ctx.ID();
            // value = variableMapOld.get(ctx.ID().getText());
            // then we retrieve value of this variable from variable map
        }
    }

    public String getVariableName(ThighParser.PrintContext ctx) {
        String ID;
        try {
            // accessing ctx.value().ID() is dangerous
            // if value is not a variable, NullPointer is what we would face
            ID = ctx.value().ID().getText();
        } catch (Exception e) {
            ID = "";
        }
        return ID;
    }

    @Override
    public void exitWhile_cond(ThighParser.While_condContext ctx) {
        is_in_block = true;
        blockStack.push("while");
        valCounter.push(0);

        if (ctx.compare_first().ID() != null && ctx.compare_second().INT() != null) {
            String id = ctx.compare_first().ID().getText();
            VarType varType = checkVarType(id);
            if (varType != VarType.INT) {
                System.err.println("Different types comparison");
            }
            String value = ctx.compare_second().INT().getText();
            Comparator sign = Comparator.GREATER;
            if (ctx.compare_sign().EQUALS() != null) {
                sign = Comparator.EQUAL;
            }
            if (ctx.compare_sign().GREATER() != null) {
                sign = Comparator.GREATER;
            }
            if (ctx.compare_sign().LESS() != null) {
                sign = Comparator.LESS;
            }
            LLVMGenerator.declareWhileCondInt(id, value, sign, is_in_main, checkVarScope(id));
        }

        if (ctx.compare_first().ID() != null && ctx.compare_second().DOUBLE() != null) {
            String id = ctx.compare_first().ID().getText();
            VarType varType = variables.get(id);
            if (varType != VarType.DOUBLE) {
                System.err.print("porownywanie roznych typow");
            }
            String value = ctx.compare_second().DOUBLE().getText();
            Comparator sign = Comparator.GREATER;
            if (ctx.compare_sign().EQUALS() != null) {
                sign = Comparator.EQUAL;
            }
            if (ctx.compare_sign().GREATER() != null) {
                sign = Comparator.GREATER;
            }
            if (ctx.compare_sign().LESS() != null) {
                sign = Comparator.LESS;
            }
            LLVMGenerator.declareWhileCondDouble(id, value, sign, is_in_main, checkVarScope(id));
        }
        if (ctx.compare_second().ID() != null && ctx.compare_first().INT() != null) {
            String id = ctx.compare_second().ID().getText();
            VarType varType = variables.get(id);
            if (varType != VarType.INT) {
                System.err.print("porownywanie roznych typow");
            }
            String value = ctx.compare_first().INT().getText();
            Comparator sign = Comparator.GREATER;
            if (ctx.compare_sign().EQUALS() != null) {
                sign = Comparator.EQUAL;
            }
            if (ctx.compare_sign().GREATER() != null) {
                sign = Comparator.LESS;
            }
            if (ctx.compare_sign().LESS() != null) {
                sign = Comparator.GREATER;
            }
            LLVMGenerator.declareWhileCondInt(id, value, sign, is_in_main, checkVarScope(id));
        }

        if (ctx.compare_second().ID() != null && ctx.compare_first().DOUBLE() != null) {
            String id = ctx.compare_second().ID().getText();
            VarType varType = variables.get(id);
            if (varType != VarType.DOUBLE) {
                System.err.print("porownywanie roznych typow");
            }
            String value = ctx.compare_first().DOUBLE().getText();
            Comparator sign = Comparator.GREATER;
            if (ctx.compare_sign().EQUALS() != null) {
                sign = Comparator.EQUAL;
            }
            if (ctx.compare_sign().GREATER() != null) {
                sign = Comparator.LESS;
            }
            if (ctx.compare_sign().LESS() != null) {
                sign = Comparator.GREATER;
            }
            LLVMGenerator.declareWhileCondDouble(id, value, sign, is_in_main, checkVarScope(id));
        }
    }

    @Override
    public void exitWhile_body(ThighParser.While_bodyContext ctx) {
        LLVMGenerator.endWhile(is_in_main);
        Integer numOfVars = valCounter.pop();
        for(int i = 0; i < numOfVars; i++){
            blockValues.pop();
        }
        blockStack.pop();
        if(blockStack.isEmpty()){
            is_in_block = false;
        }
    }

    @Override
    public void enterIf_body(ThighParser.If_bodyContext ctx) {
        is_in_block = true;
        blockStack.push("if");
        valCounter.push(0);
        LLVMGenerator.ifstart(is_in_main);
    }


    @Override
    public void exitIf_body(ThighParser.If_bodyContext ctx) {
        LLVMGenerator.ifend(is_in_main);
        Integer numOfVars = valCounter.pop();
        for(int i = 0; i < numOfVars; i++){
            blockValues.pop();
        }
        blockStack.pop();
        if (blockStack.isEmpty()){
            is_in_block = false;
        }
    }

    @Override
    public void exitIf_condition(ThighParser.If_conditionContext ctx) {
        if (ctx.value(0) == null || ctx.value(1) == null) {
            printError(ctx.getStart().getLine(), "brak dwóch warunków w pętli");
        }
        Comparator sign = Comparator.GREATER;
        if (ctx.compare_sign().EQUALS() != null) {
            sign = Comparator.EQUAL;
        }
        if (ctx.compare_sign().GREATER() != null) {
            sign = Comparator.GREATER;
        }
        if (ctx.compare_sign().LESS() != null) {
            sign = Comparator.LESS;
        }
        if (ctx.value(0).INT() != null && ctx.value(1).ID() != null) {
            String ID = ctx.value(1).ID().getText();
            String INT = ctx.value(0).INT().getText();
            VarScope scope = checkVarScope(ID);
            if (scope != VarScope.NONE) {
                if (sign == Comparator.EQUAL) {
                    LLVMGenerator.icmpIntEquall(ID, INT, scope, is_in_main);
                }
                if (sign == Comparator.LESS) {
                    LLVMGenerator.icmpIntMore(ID, INT, scope, is_in_main);
                }
                if (sign == Comparator.GREATER) {
                    LLVMGenerator.icmpIntLess(ID, INT, scope, is_in_main);
                }
            } else {
                System.err.println("Line " + ctx.getStart().getLine() + ", unknown variable: " + ID);
            }
        }
        if ((ctx.value(0).ID() != null) && (ctx.value(1).INT() != null)) {

            String ID = ctx.value(0).ID().getText();
            String INT = ctx.value(1).INT().getText();
            VarScope scope = checkVarScope(ID);
            if (scope != VarScope.NONE) {
                if (sign == Comparator.EQUAL) {
                    LLVMGenerator.icmpIntEquall(ID, INT, scope, is_in_main);
                }
                if (sign == Comparator.GREATER) {
                    LLVMGenerator.icmpIntMore(ID, INT, scope, is_in_main);
                }
                if (sign == Comparator.LESS) {
                    LLVMGenerator.icmpIntLess(ID, INT, scope, is_in_main);
                }
            } else {
                System.err.println("Line " + ctx.getStart().getLine() + ", unknown variable: " + ID);
            }
        }
        if ((ctx.value(0).ID() != null) && (ctx.value(1).DOUBLE() != null)) {
            String ID = ctx.value(0).ID().getText();
            String REAL = ctx.value(1).DOUBLE().getText();
            VarScope scope = checkVarScope(ID);
            if (scope != VarScope.NONE) {
                if (sign == Comparator.EQUAL) {
                    LLVMGenerator.icmpRealEquall(ID, REAL, scope, is_in_main);
                }
                if (sign == Comparator.GREATER) {
                    LLVMGenerator.icmpRealMore(ID, REAL, scope, is_in_main);
                }
                if (sign == Comparator.LESS) {
                    LLVMGenerator.icmpRealLess(ID, REAL, scope, is_in_main);
                }
            } else {
                System.err.println("Line " + ctx.getStart().getLine() + ", unknown variable: " + ID);
            }
        }
        if ((ctx.value(1).ID() != null) && (ctx.value(0).DOUBLE() != null)) {
            String ID = ctx.value(1).ID().getText();
            String REAL = ctx.value(0).DOUBLE().getText();
            VarScope scope = checkVarScope(ID);
            if (scope != VarScope.NONE) {
                if (sign == Comparator.EQUAL) {
                    LLVMGenerator.icmpRealEquall(ID, REAL, scope, is_in_main);
                }
                if (sign == Comparator.LESS) {
                    LLVMGenerator.icmpRealMore(ID, REAL, scope, is_in_main);
                }
                if (sign == Comparator.GREATER) {
                    LLVMGenerator.icmpRealLess(ID, REAL, scope, is_in_main);
                }
            } else {
                System.err.println("Line " + ctx.getStart().getLine() + ", unknown variable: " + ID);
            }
        }

        // ZMIENNA dowolny znak ZMIENNA
        if ((ctx.value(0).ID() != null) && (ctx.value(1).ID() != null)) {

            String ID_1 = ctx.value(0).ID().getText();
            String ID_2 = ctx.value(1).ID().getText();

            VarType var_1 = checkVarType(ID_1);
            VarType var_2 = checkVarType(ID_2);

            if (var_1 == null || var_2 == null) {
                System.err.println("Line " + ctx.getStart().getLine() + ", podana zmienna nie istnieje: " + var_1 + var_2);
            }

            if (var_1 != var_2) {
                System.err.println("Line " + ctx.getStart().getLine() + ", porownywane sa liczby z roznymi typami: ");
            }


            VarScope scope_1 = checkVarScope(ID_1);
            VarScope scope_2 = checkVarScope(ID_2);

            if (var_1 == VarType.DOUBLE) {
                if (sign == Comparator.EQUAL) {
                    LLVMGenerator.icmpRealEquallIdId(ID_1, ID_2, scope_1, scope_2, is_in_main);
                }
                if (sign == Comparator.LESS) {
                    LLVMGenerator.icmpRealMoreIdId(ID_1, ID_2, scope_1, scope_2, is_in_main);
                }
                if (sign == Comparator.GREATER) {
                    LLVMGenerator.icmpRealLessIdId(ID_1, ID_2, scope_1, scope_2, is_in_main);
                }
            }
            if (var_1 == VarType.INT) {
                if (sign == Comparator.EQUAL) {
                    LLVMGenerator.icmpIntEquallIdId(ID_1, ID_2, scope_1, scope_2, is_in_main);
                }
                if (sign == Comparator.LESS) {
                    LLVMGenerator.icmpIntMoreIdId(ID_1, ID_2, scope_1, scope_2, is_in_main);
                }
                if (sign == Comparator.GREATER) {
                    LLVMGenerator.icmpIntLessIdId(ID_1, ID_2, scope_1, scope_2, is_in_main);
                }
            }

        }
    }

    private VarType checkVarType(String ID) {

        Object [] vals =  blockValues.toArray();

        for (Object val : vals) {
            Value v = (Value) val;
            if (ID.equals(v.name)) {
                return v.type;
            }
        }

        VarType isGlobal = global_variables.get(ID);
        if (isGlobal != null) {
            return isGlobal;
        }

        VarType isLocal = fun_variables.get(ID);
        if (isLocal != null) {
            return isLocal;
        }

        return VarType.UNKNOWN;
    }

    private VarScope checkVarScope(String ID) {
        VarType isGLobal = global_variables.get(ID);
        if (isGLobal != null) {
            return VarScope.GLOBAL;
        }

        VarType isLocal = fun_variables.get(ID);
        if (isLocal != null) {
            return VarScope.LOCAL;
        }

        Object [] vals =  blockValues.toArray();
        for (Object val : vals) {
            Value v = (Value) val;
            if (ID.equals(v.name)) {
                return VarScope.BLOCK;
            }
        }

        return VarScope.NONE;
    }

    private Value getValueFromBlock(String ID){
        Object [] vals =  blockValues.toArray();
        for (Object val : vals) {
            Value v = (Value) val;
            if (ID.equals(v.name)) {
                return v;
            }
        }
        return null;
    }

    void printError(int line, String msg) {
        System.err.println("Blad w linii " + line + ", " + msg);
        System.exit(1);
    }
}
