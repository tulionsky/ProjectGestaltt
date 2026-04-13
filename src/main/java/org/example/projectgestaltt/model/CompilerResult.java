package org.example.projectgestaltt.model;

import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.List;

public class CompilerResult {

    private final List<TokenInfo>   tokens;
    private final List<ErrorInfo>   errores;
    private final List<SimboloInfo> simbolos;
    private final String            arbolTexto;
    private final TreeItem<String>  arbolGrafico;
    private final ParseTree         parseTree;
    private final Parser            parser;
    private final boolean           exitoso;

    public CompilerResult(List<TokenInfo> tokens,
                          List<ErrorInfo> errores,
                          List<SimboloInfo> simbolos,
                          String arbolTexto,
                          TreeItem<String> arbolGrafico,
                          ParseTree parseTree,
                          Parser parser) {
        this.tokens       = tokens;
        this.errores      = errores;
        this.simbolos     = simbolos;
        this.arbolTexto   = arbolTexto;
        this.arbolGrafico = arbolGrafico;
        this.parseTree    = parseTree;
        this.parser       = parser;
        this.exitoso      = errores.isEmpty();
    }

    public List<TokenInfo>   getTokens()       { return tokens; }
    public List<ErrorInfo>   getErrores()      { return errores; }
    public List<SimboloInfo> getSimbolos()     { return simbolos; }
    public String            getArbolTexto()   { return arbolTexto; }
    public TreeItem<String>  getArbolGrafico() { return arbolGrafico; }
    public ParseTree         getParseTree()    { return parseTree; }
    public Parser            getParser()       { return parser; }
    public boolean           isExitoso()       { return exitoso; }
}