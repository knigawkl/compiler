// Generated from C:/Users/gak/Documents/Studies/magisterskie/zajecia/sem1/kompilatory/p2/compiler/thigh/src/com/thighcorp\Thigh.g4 by ANTLR 4.9.1
package com.thighcorp;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ThighParser}.
 */
public interface ThighListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ThighParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(ThighParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(ThighParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ThighParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ThighParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#print_statement}.
	 * @param ctx the parse tree
	 */
	void enterPrint_statement(ThighParser.Print_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#print_statement}.
	 * @param ctx the parse tree
	 */
	void exitPrint_statement(ThighParser.Print_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#read_statement}.
	 * @param ctx the parse tree
	 */
	void enterRead_statement(ThighParser.Read_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#read_statement}.
	 * @param ctx the parse tree
	 */
	void exitRead_statement(ThighParser.Read_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#assign_statement}.
	 * @param ctx the parse tree
	 */
	void enterAssign_statement(ThighParser.Assign_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#assign_statement}.
	 * @param ctx the parse tree
	 */
	void exitAssign_statement(ThighParser.Assign_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#assign_value}.
	 * @param ctx the parse tree
	 */
	void enterAssign_value(ThighParser.Assign_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#assign_value}.
	 * @param ctx the parse tree
	 */
	void exitAssign_value(ThighParser.Assign_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void enterFunction_definition(ThighParser.Function_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#function_definition}.
	 * @param ctx the parse tree
	 */
	void exitFunction_definition(ThighParser.Function_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#function_body}.
	 * @param ctx the parse tree
	 */
	void enterFunction_body(ThighParser.Function_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#function_body}.
	 * @param ctx the parse tree
	 */
	void exitFunction_body(ThighParser.Function_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ThighParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ThighParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#arithmetic_operation}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_operation(ThighParser.Arithmetic_operationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#arithmetic_operation}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_operation(ThighParser.Arithmetic_operationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#arithmetic_operator}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#arithmetic_operator}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ThighParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ThighParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ThighParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ThighParser.ValueContext ctx);
}