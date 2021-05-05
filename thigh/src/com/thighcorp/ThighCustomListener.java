package com.thighcorp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class ThighCustomListener extends ThighBaseListener {
    HashMap<String, String> variableMap = new HashMap<>();
    HashSet<String> variables = new HashSet<>();
    Stack<String> stack = new Stack<>();
    String value;

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
    public void exitPrint_statement(ThighParser.Print_statementContext ctx) {
        String ID;
        try {
            // accessing ctx.value().ID() is dangerous
            // if value is not a variable, NullPointer is what we would face
            ID = ctx.value().ID().getText();
        } catch (Exception e) {
            ID = "";
        }
        boolean isVariable = !ID.equals("");
        if (isVariable) {
            if (this.variableMap.containsKey(ID)) {
                LLVMGenerator.print_string(this.variableMap.get(ID));
            } else {
                ctx.getStart().getLine();
                System.err.println("Line "+ ctx.getStart().getLine()+", unknown variable: "+ID);
            }
        } else {
            if (ctx.value().INT() != null) {
                LLVMGenerator.print_string(ctx.value().INT().getText());
            } else if (ctx.value().REAL() != null) {
                LLVMGenerator.print_string(ctx.value().REAL().getText());
            } else if (ctx.value().STRING() != null) {
                LLVMGenerator.print_string(value);
            }
        }
    }

    @Override
    public void exitRead_statement(ThighParser.Read_statementContext ctx) {
        String ID = ctx.ID().getText();
        if(!variables.contains(ID)) {
            variables.add(ID);
            LLVMGenerator.declare(ID);
        }
//        if (variableMap.containsKey(ID)) {
//            variableMap.put(ID, "");
//            LLVMGenerator.declare(ID);
//        }
//        LLVMGenerator.input(ID);
    }

    @Override
    public void exitAssign_statement(ThighParser.Assign_statementContext ctx) {
        var variableName = ctx.ID().getText();
        var assignedValue = ctx.assign_value().value();
        var assignedArithmeticOperation = ctx.assign_value().arithmetic_operation();

        if (assignedValue != null) {
            if (assignedValue.STRING() != null) {
                var tmp = assignedValue.STRING().getText();
                tmp = tmp.substring(1, tmp.length()-1);
                this.variableMap.put(variableName, tmp);
            } else if (assignedValue.INT() != null) {
                this.variableMap.put(variableName, assignedValue.INT().getText());
            } else if (assignedValue.REAL() != null) {
                this.variableMap.put(variableName, assignedValue.REAL().getText());
            }
        } else if (assignedArithmeticOperation != null) {
            this.variableMap.put(variableName, assignedArithmeticOperation.getText());
        }
    }

    @Override
    public void exitFunction_definition(ThighParser.Function_definitionContext ctx) {

    }

    @Override
    public void exitFunction_body(ThighParser.Function_bodyContext ctx) {

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
        if (ctx.ID() != null) {
            value = variableMap.get(ctx.ID().getText());
        }
        else if (ctx.STRING() != null) {
            String tmp = ctx.STRING().getText();
            value = tmp.substring(1, tmp.length()-1);
        }
        else if (ctx.INT() != null) {
            stack.push(ctx.INT().getText());
        }
        else if (ctx.REAL() != null) {
            stack.push(ctx.REAL().getText()); // TODO: verify this line
        }
    }
}
