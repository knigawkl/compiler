// Generated from C:/Users/lk/OneDrive - Politechnika Warszawska/JFIK/projekt/compiler/thigh/src/com/thighcorp\Thigh.g4 by ANTLR 4.9.1
package com.thighcorp;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ThighParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ThighVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ThighParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(ThighParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ThighParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#print_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_statement(ThighParser.Print_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#read_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead_statement(ThighParser.Read_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#assign_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign_statement(ThighParser.Assign_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#function_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_definition(ThighParser.Function_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#function_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_body(ThighParser.Function_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ThighParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#arithmetic_operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic_operation(ThighParser.Arithmetic_operationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#assignment_operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_operation(ThighParser.Assignment_operationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#arithmetic_operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic_operator(ThighParser.Arithmetic_operatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ThighParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ThighParser.ValueContext ctx);
}