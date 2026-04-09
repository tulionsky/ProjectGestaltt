package org.example.projectgestaltt.model;

import java.util.List;

/**
 * Agrupa todos los resultados del análisis léxico y sintáctico.
 */
public class CompilerResult {

    private final List<TokenInfo>   tokens;
    private final List<ErrorInfo>   errores;
    private final List<SimboloInfo> simbolos;
    private final String            arbolDerivacion;
    private final boolean           exitoso;

    public CompilerResult(List<TokenInfo> tokens,
                          List<ErrorInfo> errores,
                          List<SimboloInfo> simbolos,
                          String arbolDerivacion) {
        this.tokens          = tokens;
        this.errores         = errores;
        this.simbolos        = simbolos;
        this.arbolDerivacion = arbolDerivacion;
        this.exitoso         = errores.isEmpty();
    }

    public List<TokenInfo>   getTokens()          { return tokens; }
    public List<ErrorInfo>   getErrores()         { return errores; }
    public List<SimboloInfo> getSimbolos()        { return simbolos; }
    public String            getArbolDerivacion() { return arbolDerivacion; }
    public boolean           isExitoso()          { return exitoso; }
}