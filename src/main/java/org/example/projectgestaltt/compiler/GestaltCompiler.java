package org.example.projectgestaltt.compiler;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.projectgestaltt.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Fachada principal del compilador.
 * Recibe el código fuente como String y devuelve un CompilerResult
 * con tokens, errores, tabla de símbolos y árbol de derivación.
 */
public class GestaltCompiler {

    /**
     * Analiza el código fuente y retorna todos los resultados.
     *
     * @param codigoFuente Texto del programa en lenguaje Gestalt.
     * @return             Resultado completo del análisis.
     */
    public CompilerResult analizar(String codigoFuente) {

        List<ErrorInfo>   errores  = new ArrayList<>();
        List<TokenInfo>   tokens   = new ArrayList<>();
        List<SimboloInfo> simbolos = new ArrayList<>();
        String            arbol    = "";

        // ── 1. Crear el stream de caracteres ──────────────────────────────
        CharStream input = CharStreams.fromString(codigoFuente);

        // ── 2. ANÁLISIS LÉXICO ────────────────────────────────────────────
        ProjectGestaltLexer lexer = new ProjectGestaltLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(
                new GestaltErrorListener(errores, ErrorInfo.TipoError.LEXICO));

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        tokenStream.fill(); // forzar lectura completa

        // Recolectar tokens visibles (excluir EOF y los que skip)
        for (Token t : tokenStream.getTokens()) {
            if (t.getType() == Token.EOF) continue;
            String nombreTipo = lexer.getVocabulary().getDisplayName(t.getType());
            tokens.add(new TokenInfo(
                    nombreTipo,
                    t.getText(),
                    t.getLine(),
                    t.getCharPositionInLine() + 1));
        }

        // ── 3. ANÁLISIS SINTÁCTICO ────────────────────────────────────────
        ProjectGestaltParser parser = new ProjectGestaltParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(
                new GestaltErrorListener(errores, ErrorInfo.TipoError.SINTACTICO));

        ParseTree tree = parser.programa(); // regla inicial

        // ── 4. Árbol de derivación ────────────────────────────────────────
        arbol = TreePrinter.print(tree, parser);

        // ── 5. Tabla de símbolos (solo si no hay errores graves) ──────────
        if (errores.stream().noneMatch(e -> e.getTipo() == ErrorInfo.TipoError.SINTACTICO)) {
            SymbolTableVisitor visitor = new SymbolTableVisitor();
            visitor.visit(tree);
            simbolos.addAll(visitor.getSimbolos());
        }

        return new CompilerResult(tokens, errores, simbolos, arbol);
    }
}