package com.thighcorp;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashMap;

public class ThighCustomListener extends ThighBaseListener {
    HashMap<String, String> variableMap = new HashMap();

    @Override
    public void enterProgram(ThighParser.ProgramContext ctx) {

    }

    @Override
    public void exitProgram(ThighParser.ProgramContext ctx) {

    }

    @Override
    public void enterStatement(ThighParser.StatementContext ctx) {

    }

    @Override
    public void exitStatement(ThighParser.StatementContext ctx) {

    }

    @Override
    public void enterPrint_statement(ThighParser.Print_statementContext ctx) {

    }

    @Override
    public void exitPrint_statement(ThighParser.Print_statementContext ctx) {
        if(ctx.value().INT() != null){
            System.out.println(ctx.value().INT().getText());
        }
        else if(ctx.value().ID() != null){
            System.out.println(this.variableMap.get(ctx.value().ID().getText()));
        }
    }

    @Override
    public void enterRead_statement(ThighParser.Read_statementContext ctx) {

    }

    @Override
    public void exitRead_statement(ThighParser.Read_statementContext ctx) {

    }

    @Override
    public void enterAssign_statement(ThighParser.Assign_statementContext ctx) {

    }

    @Override
    public void exitAssign_statement(ThighParser.Assign_statementContext ctx) {

        ThighParser.ValueContext a = ctx.assign_value().value();
        ThighParser.Arithmetic_operationContext b = ctx.assign_value().arithmetic_operation();

        if (a != null){
            if (a.INT() != null){
                this.variableMap.put(ctx.ID().getText(),
                        a.INT().getText());
            }else if (a.STRING() != null){
                this.variableMap.put(ctx.ID().getText(),
                        a.STRING().getText());

            }else if (a.REAL() != null){
                this.variableMap.put(ctx.ID().getText(),
                        a.REAL().getText());
            }
        }else if (b != null){
            this.variableMap.put(ctx.ID().getText(),
                    b.getText());
        }




    }

    @Override
    public void enterFunction_definition(ThighParser.Function_definitionContext ctx) {

    }

    @Override
    public void exitFunction_definition(ThighParser.Function_definitionContext ctx) {

    }

    @Override
    public void enterFunction_body(ThighParser.Function_bodyContext ctx) {

    }

    @Override
    public void exitFunction_body(ThighParser.Function_bodyContext ctx) {

    }

    @Override
    public void enterExpression(ThighParser.ExpressionContext ctx) {

    }

    @Override
    public void exitExpression(ThighParser.ExpressionContext ctx) {

    }

    @Override
    public void enterArithmetic_operation(ThighParser.Arithmetic_operationContext ctx) {

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
    public void enterValue(ThighParser.ValueContext ctx) {

    }

    @Override
    public void exitValue(ThighParser.ValueContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
