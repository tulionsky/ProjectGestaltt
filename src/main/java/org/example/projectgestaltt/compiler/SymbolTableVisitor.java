package org.example.projectgestaltt.compiler;

import org.example.projectgestaltt.model.SimboloInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Recorre el árbol de derivación generado por ANTLR para construir
 * la tabla de símbolos (variables, arreglos y funciones declaradas).
 *
 * NOTA: Los métodos usan los nombres exactos que genera ANTLR a partir
 *       del archivo ProjectGestalt.g4.  Si renombras reglas en el .g4
 *       debes actualizar los nombres de los métodos visitXxx aquí también.
 *
 * Este visitor extiende ProjectGestaltBaseVisitor<Void> que ANTLR genera
 * automáticamente durante el build (mvn compile).
 */
public class SymbolTableVisitor extends ProjectGestaltBaseVisitor<Void> {

    private final List<SimboloInfo> simbolos = new ArrayList<>();
    private String ambitoActual = "global";

    // ── Programa ────────────────────────────────────────────────────────────
    @Override
    public Void visitPrograma(ProjectGestaltParser.ProgramaContext ctx) {
        ambitoActual = ctx.IDENTIFICADOR().getText();
        return visitChildren(ctx);
    }

    // ── Declaración de variable: ´unit´ tipo ID ':=' expr ';' ──────────────────────
    @Override
    public Void visitDeclaracionVariable(ProjectGestaltParser.DeclaracionVariableContext ctx) {
        String tipo   = ctx.tipo().getText();
        String nombre = ctx.IDENTIFICADOR().getText();
        int    linea  = ctx.IDENTIFICADOR().getSymbol().getLine();
        String valor  = ctx.expresion().getText();

        simbolos.add(new SimboloInfo(nombre, tipo, ambitoActual, linea, valor));
        return visitChildren(ctx);
    }

    // ── Declaración de arreglo: 'yorha' tipo ID '[' ENTERO ']' ';' ──────────
    @Override
    public Void visitDeclaracionArreglo(ProjectGestaltParser.DeclaracionArregloContext ctx) {
        String tipo   = ctx.tipo().getText() + "[]";
        String nombre = ctx.IDENTIFICADOR().getText();
        int    linea  = ctx.IDENTIFICADOR().getSymbol().getLine();
        String valor  = "arreglo[" + ctx.ENTERO().getText() + "]";

        simbolos.add(new SimboloInfo(nombre, tipo, ambitoActual, linea, valor));
        return visitChildren(ctx);
    }

    // ── Declaración de función: ('model'|'weiss') ID '(' params? ')' ────────
    @Override
    public Void visitDeclaracionFuncion(ProjectGestaltParser.DeclaracionFuncionContext ctx) {
        String nombre   = ctx.IDENTIFICADOR().getText();
        int    linea    = ctx.IDENTIFICADOR().getSymbol().getLine();
        String keyword  = ctx.getStart().getText(); // 'model' o 'weiss'
        String tipo     = keyword.equals("weiss") ? "void" : "funcion";

        simbolos.add(new SimboloInfo(nombre, tipo, ambitoActual, linea, "función"));

        // Entramos al ámbito de la función para registrar sus parámetros
        String ambitoPadre = ambitoActual;
        ambitoActual = nombre;
        visitChildren(ctx);
        ambitoActual = ambitoPadre;
        return null;
    }

    // ── Parámetros de función ────────────────────────────────────────────────
    @Override
    public Void visitParametros(ProjectGestaltParser.ParametrosContext ctx) {
        // La gramática define: tipo IDENTIFICADOR (',' tipo IDENTIFICADOR)*
        // ctx.tipo()          → lista de tipos
        // ctx.IDENTIFICADOR() → lista de identificadores (mismo orden)
        for (int i = 0; i < ctx.IDENTIFICADOR().size(); i++) {
            String tipo   = ctx.tipo(i).getText();
            String nombre = ctx.IDENTIFICADOR(i).getText();
            int    linea  = ctx.IDENTIFICADOR(i).getSymbol().getLine();
            simbolos.add(new SimboloInfo(nombre, tipo, ambitoActual, linea, "parámetro"));
        }
        return null; // no visitar hijos (ya procesamos todo)
    }

    // ── Getter ───────────────────────────────────────────────────────────────
    public List<SimboloInfo> getSimbolos() {
        return simbolos;
    }
}