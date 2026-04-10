// Generated from C:/Users/tulio/IdeaProjects/ProjectGestaltt/src/main/antlr4/ProjectGestalt.g4 by ANTLR 4.13.2
package org.example.projectgestaltt.compiler;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ProjectGestaltParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ProjectGestaltVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(ProjectGestaltParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#sentencia}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSentencia(ProjectGestaltParser.SentenciaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#declaracionVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionVariable(ProjectGestaltParser.DeclaracionVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#declaracionArreglo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionArreglo(ProjectGestaltParser.DeclaracionArregloContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacion(ProjectGestaltParser.AsignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#impresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImpresion(ProjectGestaltParser.ImpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#lectura}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLectura(ProjectGestaltParser.LecturaContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#condicional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondicional(ProjectGestaltParser.CondicionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#cicloMission}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCicloMission(ProjectGestaltParser.CicloMissionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#cicloSortie}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCicloSortie(ProjectGestaltParser.CicloSortieContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#asignacionSortie}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacionSortie(ProjectGestaltParser.AsignacionSortieContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#declaracionFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracionFuncion(ProjectGestaltParser.DeclaracionFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros(ProjectGestaltParser.ParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#llamadaFuncion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLlamadaFuncion(ProjectGestaltParser.LlamadaFuncionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#argumentos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentos(ProjectGestaltParser.ArgumentosContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#retorno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetorno(ProjectGestaltParser.RetornoContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#halt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHalt(ProjectGestaltParser.HaltContext ctx);
	/**
	 * Visit a parse tree produced by {@link ProjectGestaltParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(ProjectGestaltParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprArreglo}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprArreglo(ProjectGestaltParser.ExprArregloContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprSuma}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprSuma(ProjectGestaltParser.ExprSumaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprParentesis}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprParentesis(ProjectGestaltParser.ExprParentesisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprComparacion}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprComparacion(ProjectGestaltParser.ExprComparacionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprLogica}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprLogica(ProjectGestaltParser.ExprLogicaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprFalso}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprFalso(ProjectGestaltParser.ExprFalsoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprLlamada}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprLlamada(ProjectGestaltParser.ExprLlamadaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprNegacion}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprNegacion(ProjectGestaltParser.ExprNegacionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprCadena}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprCadena(ProjectGestaltParser.ExprCadenaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprEntero}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprEntero(ProjectGestaltParser.ExprEnteroContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprVerdadero}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprVerdadero(ProjectGestaltParser.ExprVerdaderoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprId}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprId(ProjectGestaltParser.ExprIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprMulDiv}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMulDiv(ProjectGestaltParser.ExprMulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprDecimal}
	 * labeled alternative in {@link ProjectGestaltParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprDecimal(ProjectGestaltParser.ExprDecimalContext ctx);
}