package com.thighcorp;

import java.util.HashMap;
import java.util.Stack;

enum VarType{INT, REAL, STRING, UNKNOWN}

class Value {
    public String name;
    public VarType type;
    public Value(String name, VarType type){
        this.name = name;
        this.type = type;
    }
}

public class ThighCustomListener extends ThighBaseListener {
    HashMap<String, VarType> variables = new HashMap<>();
    Stack<Value> stack = new Stack<>();

    @Override
    public void enterProgram(ThighParser.ProgramContext ctx) {

    }

    @Override
    public void exitProgram(ThighParser.ProgramContext ctx) {
        System.out.println(LLVMGenerator.generate());
    }

    @Override
    public void exitStatement(ThighParser.StatementContext ctx) {

    }

    @Override
    public void exitPrint(ThighParser.PrintContext ctx) {
        var variableName = getVariableName(ctx);
        VarType type = variables.get(variableName);
        boolean isVariable = (type != null);
        if (isVariable) {
            switch (type) {
                case INT -> LLVMGenerator.print_int_var(variableName);
                case REAL -> LLVMGenerator.print_double_var(variableName);
                case STRING -> LLVMGenerator.print_string_var(variableName);
            }
        } else if (ctx.value().INT() != null) {
            LLVMGenerator.print_string(ctx.value().INT().getText());
        } else if (ctx.value().REAL() != null) {
            LLVMGenerator.print_string(ctx.value().REAL().getText());
        } else if (ctx.value().STRING() != null) {
            String str = ctx.value().STRING().getText();
            LLVMGenerator.print_string(str.substring(1, str.length()-1));
        }
    }

    @Override
    public void exitRead(ThighParser.ReadContext ctx) {
//        String ID = ctx.ID().getText();
//        if(!variablesOLd.contains(ID)) {
//            variablesOLd.add(ID);
//            LLVMGenerator.declare_int(ID);
//        }
//        LLVMGenerator.input(ID);
//        if (variableMap.containsKey(ID)) {
//            variableMap.put(ID, "");
//            LLVMGenerator.declare(ID);
//        }
//        LLVMGenerator.input(ID);
    }

    @Override
    public void exitAssign(ThighParser.AssignContext ctx) {
        var variableName = ctx.ID().getText();
        Value v = stack.pop();
        variables.put(variableName, v.type);
        if (v.type == VarType.INT) {
            LLVMGenerator.declare_int(variableName);
            LLVMGenerator.assign_int(variableName, v.name);
        } else if (v.type == VarType.REAL) {
            LLVMGenerator.declare_double(variableName);
            LLVMGenerator.assign_double(variableName, v.name);
        } else if (v.type == VarType.STRING) {
            LLVMGenerator.declare_string(variableName);
            LLVMGenerator.assign_string(variableName, v.name);
        }
    }

    @Override
    public void exitFunction_definition(ThighParser.Function_definitionContext ctx) {
        // TODO: 2nd project
    }

    @Override
    public void exitFunction_body(ThighParser.Function_bodyContext ctx) {
        // TODO: 2nd project
    }

    @Override
    public void exitExpression(ThighParser.ExpressionContext ctx) {

    }

    @Override
    public void exitArithmetic_operation(ThighParser.Arithmetic_operationContext ctx) {

    }

    @Override
    public void enterArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx) {

    }

    @Override
    public void exitArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx) {

    }

    @Override
    public void exitValue(ThighParser.ValueContext ctx) {
        if (ctx.INT() != null) {
            stack.push(new Value(ctx.INT().getText(), VarType.INT));
        } else if (ctx.REAL() != null) {
            stack.push(new Value(ctx.REAL().getText(), VarType.REAL));
        } else if (ctx.STRING() != null) {
            String str = ctx.STRING().getText();
            stack.push(new Value(str.substring(1, str.length()-1), VarType.STRING));
        } else if (ctx.ID() != null) {
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
}
