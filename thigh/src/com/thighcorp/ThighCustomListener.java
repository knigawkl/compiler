package com.thighcorp;

import java.util.HashMap;
import java.util.HashSet;

public class ThighCustomListener extends ThighBaseListener {
    HashMap<String, String> variableMap = new HashMap();
    HashSet<String> variables = new HashSet<String>();
    Double num_val = null;
    String text_val = null;

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
        String ID = ctx.value().ID().getText();
        if( variables.contains(ID) ) {
            LLVMGenerator.print_variable(ID);
        } else if (ctx.value().INT() != null) {
            LLVMGenerator.print(ctx.value().INT().getText());
        } else if(ctx.value().REAL() != null) {
            LLVMGenerator.print(ctx.value().REAL().getText());
        } else if(ctx.value().STRING() != null) {
            LLVMGenerator.print(ctx.value().STRING().getText());
        } else {
            // TODO: Please handle this with some descriptive error message
        }
    }

    @Override
    public void exitRead_statement(ThighParser.Read_statementContext ctx) {
        String ID = ctx.ID().getText();
        if(!variables.contains(ID)) {
            variables.add(ID);
            LLVMGenerator.declare(ID);
        }
        LLVMGenerator.input(ID);
    }

    @Override
    public void enterAssign_statement(ThighParser.Assign_statementContext ctx) {

    }

    @Override
    public void exitAssign_statement(ThighParser.Assign_statementContext ctx) {

        ThighParser.ValueContext a = ctx.assign_value().value();
        ThighParser.Arithmetic_operationContext b = ctx.assign_value().arithmetic_operation();

        if (a != null) {
            if (a.INT() != null) {
                this.variableMap.put(ctx.ID().getText(),
                        a.INT().getText());
            } else if (a.STRING() != null) {
                this.variableMap.put(ctx.ID().getText(),
                        a.STRING().getText());
            } else if (a.REAL() != null) {
                this.variableMap.put(ctx.ID().getText(),
                        a.REAL().getText());
            }
        } else if (b != null) {
            this.variableMap.put(ctx.ID().getText(),
                    b.getText());
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

        resolveArithmeticOperation(ctx);

//        if (text_val != null){
//            System.out.println(true);
//        }else{
//            System.out.println(num_val);
//        }
    }

    private void resolveArithmeticOperation(ThighParser.Arithmetic_operationContext ctx){
        ThighParser.ValueContext v = ctx.value();
        ThighParser.Arithmetic_operationContext actx = ctx.arithmetic_operation(0);
        ThighParser.Arithmetic_operationContext actx_bis = ctx.arithmetic_operation(1);
        boolean txt = false;
        boolean num = false;

        if (v != null){
            // JUST VALUE
            if (v.STRING() != null) {
                text_val = v.STRING().getText();
            }else if (v.INT() != null){
                num_val = Double.parseDouble(v.INT().getText());
            }else if (v.REAL() != null){
                num_val = Double.parseDouble(v.INT().getText());
            }
        }else if (actx != null && actx_bis != null){
            //DOUBLE EXPRESSION
            String tmp_txt = "";
            Double tmp_num = 0.0;
            resolveArithmeticOperation(actx);
            if (text_val != null){
                tmp_txt = text_val;
                txt = true;
            }else{
                tmp_num = num_val;
                num = true;
            }
            resolveArithmeticOperation(actx_bis);

            ThighParser.Arithmetic_operatorContext op = ctx.arithmetic_operator();

            if (op.ADDITION() != null){
                if (num){
                    num_val += tmp_num;
                }else{
                    //CONCATE TEXT TODO
                }
            }else if (op.SUBSTITUTION() != null){
                if (num){
                    num_val = tmp_num - num_val;
                }else{
                    //NIE MOZNA TODO
                }
            }else if (op.DIVISION() != null){
                if (num){
                    num_val = tmp_num / num_val;
                }else{
                    //NIE MOZNA TODO
                }

            }else if (op.MULTIPLICATION() != null){
                if (num){
                    num_val = tmp_num * num_val;
                }else{
                    //NIE MOZNA TODO
                }
            }else if (op.MODULO() != null){
                if (num){
                    num_val = tmp_num % num_val;
                }else{
                    //NIE MOZNA TODO
                }
            }else if (op.POWER() != null){
                if (num){
                    //POTEGOWANIE TODO
                }else{
                    //NIE MOZNA TODO
                }
            }

        }else if (actx != null){
            //SINGLE EXPRESSION
            resolveArithmeticOperation(actx);
        }
    }


    @Override
    public void enterArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx) {

    }

    @Override
    public void exitArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx) {

    }

    @Override
    public void exitValue(ThighParser.ValueContext ctx) {
//        String tmp = ctx.STRING().getText();
//        tmp = tmp.substring(1, tmp.length()-1);
//        memory.put(ctx.ID().getText(), tmp);
    }
}
