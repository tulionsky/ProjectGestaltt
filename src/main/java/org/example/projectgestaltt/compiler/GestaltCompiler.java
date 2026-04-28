package org.example.projectgestaltt.compiler;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import javafx.scene.control.TreeItem;
import org.example.projectgestaltt.model.*;

import java.util.ArrayList;
import java.util.List;

public class GestaltCompiler {

    public CompilerResult analizar(String codigoFuente) {

        List<ErrorInfo>   errores      = new ArrayList<>();
        List<TokenInfo>   tokens       = new ArrayList<>();
        List<SimboloInfo> simbolos     = new ArrayList<>();
        String            arbol        = "";
        TreeItem<String>  arbolGrafico = null;

        CharStream input = CharStreams.fromString(codigoFuente);

        // ── Léxico ────────────────────────────────────────────────────────
        ProjectGestaltLexer lexer = new ProjectGestaltLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(
                new GestaltErrorListener(errores, ErrorInfo.TipoError.LEXICO));

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        tokenStream.fill();

        for (Token t : tokenStream.getTokens()) {
            if (t.getType() == Token.EOF) continue;
            String nombreTipo = lexer.getVocabulary().getDisplayName(t.getType());
            tokens.add(new TokenInfo(
                    nombreTipo, t.getText(),
                    t.getLine(), t.getCharPositionInLine() + 1));
        }

        // ── Sintáctico ────────────────────────────────────────────────────
        ProjectGestaltParser parser = new ProjectGestaltParser(tokenStream);
        parser.removeErrorListeners();
        parser.addErrorListener(
                new GestaltErrorListener(errores, ErrorInfo.TipoError.SINTACTICO));

        ParseTree tree = parser.programa();

        // ── Tabla de símbolos ─────────────────────────────────────────────
        if (errores.stream().noneMatch(e ->
                e.getTipo() == ErrorInfo.TipoError.SINTACTICO)) {
            SymbolTableVisitor visitor = new SymbolTableVisitor();
            visitor.visit(tree);
            simbolos.addAll(visitor.getSimbolos());
        }

        // ── Devolver ParseTree y Parser para que el Controller
        //    cree el Canvas en el hilo de JavaFX ────────────────────────────
        return new CompilerResult(
                tokens, errores, simbolos, arbol, arbolGrafico, tree, parser);
    }
}