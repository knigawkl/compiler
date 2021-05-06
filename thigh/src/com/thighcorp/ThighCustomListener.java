package com.thighcorp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

enum VarType{INT, DOUBLE, STRING}

class Value {
    public String val;
    public VarType type;
    public Value(String name, VarType type){
        this.val = name;
        this.type = type;
    }
}

public class ThighCustomListener extends ThighBaseListener {
    private final String out_file;
    HashMap<String, VarType> variables = new HashMap<>();
    HashMap<String, String> strMemory = new HashMap<>();
    Stack<Value> stack = new Stack<>();

    public ThighCustomListener(String ll_file) {
        out_file = ll_file;
    }

    @Override
    public void exitProgram(ThighParser.ProgramContext ctx) {
        String intermesiate_representation = LLVMGenerator.generate();
        FileWriter llWriter = null;
        try {
            llWriter = new FileWriter(out_file);
            llWriter.write(intermesiate_representation);
            llWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exitPrint(ThighParser.PrintContext ctx) {
        var variableName = getVariableName(ctx);
        VarType type = variables.get(variableName);
        boolean isVariable = (type != null);
        if (isVariable) {
            switch (type) {
                case INT -> LLVMGenerator.printVariable(variableName, VarType.INT);
                case DOUBLE -> LLVMGenerator.printVariable(variableName, VarType.DOUBLE);
                case STRING -> LLVMGenerator.printString(strMemory.get(variableName));
            }
        } else if (ctx.value().INT() != null) {
            LLVMGenerator.printString(ctx.value().INT().getText());
        } else if (ctx.value().DOUBLE() != null) {
            LLVMGenerator.printString(ctx.value().DOUBLE().getText());
        } else if (ctx.value().STRING() != null) {
            String str = ctx.value().STRING().getText();
            LLVMGenerator.printString(str.substring(1, str.length()-1));
        }
    }

    @Override
    public void exitRead(ThighParser.ReadContext ctx) {
        String variableName = ctx.ID().getText();
        var input_type = ctx.type().getText();

        if (!variables.containsKey(variableName)) {
            switch (input_type) {
                case "int" -> {
                    variables.put(variableName, VarType.INT);
                    LLVMGenerator.declareVariable(variableName, VarType.INT);
                    LLVMGenerator.inputVariable(variableName, VarType.INT);
                }
                case "double" -> {
                    variables.put(variableName, VarType.DOUBLE);
                    LLVMGenerator.declareVariable(variableName, VarType.DOUBLE);
                    LLVMGenerator.inputVariable(variableName, VarType.DOUBLE);
                }
                case "string" -> {
                    variables.put(variableName, VarType.STRING);
                    // TODO
                }
            }
        }
    }

    @Override
    public void exitAssign(ThighParser.AssignContext ctx) {
        var variableName = ctx.ID().getText();
        Value v = stack.pop();
        variables.put(variableName, v.type);
        if (v.val.charAt(0) == '%') {
            v.val = "%" + v.val;
        }
        if (v.type == VarType.INT) {
            LLVMGenerator.declareVariable(variableName, VarType.INT);
            LLVMGenerator.assign(variableName, v.val, VarType.INT);
        } else if (v.type == VarType.DOUBLE) {
            LLVMGenerator.declareVariable(variableName, VarType.DOUBLE);
            LLVMGenerator.assign(variableName, v.val, VarType.DOUBLE);
        } else if (v.type == VarType.STRING) {
            String tmp = ctx.assign_value().getText();
            tmp = tmp.substring(1, tmp.length()-1);
            strMemory.put(variableName, tmp);
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
    public void exitArithmeticOperation(ThighParser.ArithmeticOperationContext ctx) {

    }

    @Override
    public void exitAdditionOperation(ThighParser.AdditionOperationContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();
        if (v1.type == v2.type) {
            if (v1.type == VarType.INT) {
                LLVMGenerator.add(v1.val, v2.val, VarType.INT);
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.INT));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.add(v1.val, v2.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.DOUBLE));
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
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.INT));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.sub(v2.val, v1.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.DOUBLE));
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
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.INT));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.div(v2.val, v1.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.DOUBLE));
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
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.INT));
            }
            if (v1.type == VarType.DOUBLE) {
                LLVMGenerator.mul(v1.val, v2.val, VarType.DOUBLE);
                stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.DOUBLE));
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
            stack.push( new Value("%"+(LLVMGenerator.reg_iter-1), VarType.DOUBLE) );
        }else if(ctx.TOINT() != null){
            Value v = stack.pop();
            LLVMGenerator.castToInt( v.val );
            stack.push( new Value("%"+(LLVMGenerator.reg_iter-1), VarType.INT) );
        }
    }

    @Override
    public void exitArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx) {
        // TODO
    }

//    @Override
//    public void exitToint_cast(ThighParser.Toint_castContext ctx) {
//        Value v = stack.pop();
//        LLVMGenerator.fptosi(v.val);
//        stack.push(new Value("%"+(LLVMGenerator.reg_iter-1), VarType.INT));
//    }

    @Override
    public void exitValue(ThighParser.ValueContext ctx) {
        if (ctx.INT() != null) {
            stack.push(new Value(ctx.INT().getText(), VarType.INT));
        } else if (ctx.DOUBLE() != null) {
            stack.push(new Value(ctx.DOUBLE().getText(), VarType.DOUBLE));
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
