package org.example.projectgestaltt.compiler;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.example.projectgestaltt.model.ErrorInfo;

import java.util.List;

/**
 * Captura los errores léxicos y sintácticos reportados por ANTLR
 * y los convierte en objetos ErrorInfo.
 */
public class GestaltErrorListener extends BaseErrorListener {

    private final List<ErrorInfo>     listaErrores;
    private final ErrorInfo.TipoError tipoError;

    public GestaltErrorListener(List<ErrorInfo> listaErrores, ErrorInfo.TipoError tipoError) {
        this.listaErrores = listaErrores;
        this.tipoError    = tipoError;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object           offendingSymbol,
                            int              line,
                            int              charPositionInLine,
                            String           msg,
                            RecognitionException e) {

        // Mensaje más amigable en español
        String descripcion = traducirMensaje(msg);
        listaErrores.add(new ErrorInfo(tipoError, line, charPositionInLine + 1, descripcion));
    }

    // ── Traducciones básicas de mensajes ANTLR ──────────────────────────────
    private String traducirMensaje(String msg) {
        if (msg == null) return "Error desconocido";

        if (msg.contains("token recognition error at"))
            return "Carácter no reconocido: " + extraerToken(msg);

        if (msg.contains("missing") && msg.contains("at"))
            return "Falta símbolo esperado: " + extraerToken(msg);

        if (msg.contains("extraneous input"))
            return "Token inesperado: " + extraerToken(msg);

        if (msg.contains("no viable alternative"))
            return "Estructura sintáctica inválida en: " + extraerToken(msg);

        if (msg.contains("mismatched input"))
            return "Token incorrecto: " + extraerToken(msg);

        return msg;
    }

    private String extraerToken(String msg) {
        // Extrae el token entre comillas si existe
        int start = msg.indexOf('\'');
        int end   = msg.lastIndexOf('\'');
        if (start >= 0 && end > start) {
            return msg.substring(start, end + 1);
        }
        return msg;
    }
}