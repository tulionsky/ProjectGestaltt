// Generated from C:/Users/tulio/IdeaProjects/ProjectGestaltt/src/main/antlr4/ProjectGestalt.g4 by ANTLR 4.13.2

package org.example.projectgestaltt.compiler;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ProjectGestaltParser}.
 */
public interface ProjectGestaltListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(ProjectGestaltParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(ProjectGestaltParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void enterSentencia(ProjectGestaltParser.SentenciaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#sentencia}.
	 * @param ctx the parse tree
	 */
	void exitSentencia(ProjectGestaltParser.SentenciaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#declaracionVariable}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionVariable(ProjectGestaltParser.DeclaracionVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#declaracionVariable}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionVariable(ProjectGestaltParser.DeclaracionVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#declaracionArreglo}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionArreglo(ProjectGestaltParser.DeclaracionArregloContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#declaracionArreglo}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionArreglo(ProjectGestaltParser.DeclaracionArregloContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(ProjectGestaltParser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(ProjectGestaltParser.AsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#impresion}.
	 * @param ctx the parse tree
	 */
	void enterImpresion(ProjectGestaltParser.ImpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#impresion}.
	 * @param ctx the parse tree
	 */
	void exitImpresion(ProjectGestaltParser.ImpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#lectura}.
	 * @param ctx the parse tree
	 */
	void enterLectura(ProjectGestaltParser.LecturaContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#lectura}.
	 * @param ctx the parse tree
	 */
	void exitLectura(ProjectGestaltParser.LecturaContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#condicional}.
	 * @param ctx the parse tree
	 */
	void enterCondicional(ProjectGestaltParser.CondicionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#condicional}.
	 * @param ctx the parse tree
	 */
	void exitCondicional(ProjectGestaltParser.CondicionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#cicloMission}.
	 * @param ctx the parse tree
	 */
	void enterCicloMission(ProjectGestaltParser.CicloMissionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#cicloMission}.
	 * @param ctx the parse tree
	 */
	void exitCicloMission(ProjectGestaltParser.CicloMissionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#cicloSortie}.
	 * @param ctx the parse tree
	 */
	void enterCicloSortie(ProjectGestaltParser.CicloSortieContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#cicloSortie}.
	 * @param ctx the parse tree
	 */
	void exitCicloSortie(ProjectGestaltParser.CicloSortieContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#asignacionSortie}.
	 * @param ctx the parse tree
	 */
	void enterAsignacionSortie(ProjectGestaltParser.AsignacionSortieContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#asignacionSortie}.
	 * @param ctx the parse tree
	 */
	void exitAsignacionSortie(ProjectGestaltParser.AsignacionSortieContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracionFuncion(ProjectGestaltParser.DeclaracionFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#declaracionFuncion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracionFuncion(ProjectGestaltParser.DeclaracionFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(ProjectGestaltParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(ProjectGestaltParser.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#llamadaFuncion}.
	 * @param ctx the parse tree
	 */
	void enterLlamadaFuncion(ProjectGestaltParser.LlamadaFuncionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#llamadaFuncion}.
	 * @param ctx the parse tree
	 */
	void exitLlamadaFuncion(ProjectGestaltParser.LlamadaFuncionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void enterArgumentos(ProjectGestaltParser.ArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void exitArgumentos(ProjectGestaltParser.ArgumentosContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#retorno}.
	 * @param ctx the parse tree
	 */
	void enterRetorno(ProjectGestaltParser.RetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#retorno}.
	 * @param ctx the parse tree
	 */
	void exitRetorno(ProjectGestaltParser.RetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#halt}.
	 * @param ctx the parse tree
	 */
	void enterHalt(ProjectGestaltParser.HaltContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#halt}.
	 * @param ctx the parse tree
	 */
	void exitHalt(ProjectGestaltParser.HaltContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProjectGestaltParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(ProjectGestaltParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProjectGestaltParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(ProjectGestaltParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprArreglo}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprArreglo(ProjectGestaltParser.ExprArregloContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprArreglo}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprArreglo(ProjectGestaltParser.ExprArregloContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprSuma}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprSuma(ProjectGestaltParser.ExprSumaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprSuma}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprSuma(ProjectGestaltParser.ExprSumaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprParentesis}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprParentesis(ProjectGestaltParser.ExprParentesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprParentesis}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprParentesis(ProjectGestaltParser.ExprParentesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprComparacion}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprComparacion(ProjectGestaltParser.ExprComparacionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprComparacion}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprComparacion(ProjectGestaltParser.ExprComparacionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprLogica}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprLogica(ProjectGestaltParser.ExprLogicaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprLogica}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprLogica(ProjectGestaltParser.ExprLogicaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprFalso}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprFalso(ProjectGestaltParser.ExprFalsoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprFalso}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprFalso(ProjectGestaltParser.ExprFalsoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprLlamada}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprLlamada(ProjectGestaltParser.ExprLlamadaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprLlamada}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprLlamada(ProjectGestaltParser.ExprLlamadaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprNegacion}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprNegacion(ProjectGestaltParser.ExprNegacionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprNegacion}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprNegacion(ProjectGestaltParser.ExprNegacionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprCadena}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprCadena(ProjectGestaltParser.ExprCadenaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprCadena}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprCadena(ProjectGestaltParser.ExprCadenaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprEntero}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprEntero(ProjectGestaltParser.ExprEnteroContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprEntero}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprEntero(ProjectGestaltParser.ExprEnteroContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprVerdadero}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprVerdadero(ProjectGestaltParser.ExprVerdaderoContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprVerdadero}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprVerdadero(ProjectGestaltParser.ExprVerdaderoContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprId(ProjectGestaltParser.ExprIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprId(ProjectGestaltParser.ExprIdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprMulDiv}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprMulDiv(ProjectGestaltParser.ExprMulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprMulDiv}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprMulDiv(ProjectGestaltParser.ExprMulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprDecimal}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExprDecimal(ProjectGestaltParser.ExprDecimalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprDecimal}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExprDecimal(ProjectGestaltParser.ExprDecimalContext ctx);
}