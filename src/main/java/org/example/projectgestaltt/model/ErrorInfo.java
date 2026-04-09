package org.example.projectgestaltt.model;

/**
 * Representa un error léxico o sintáctico encontrado durante el análisis.
 */
public class ErrorInfo {

    public enum TipoError { LEXICO, SINTACTICO }

    private final TipoError tipo;
    private final int       linea;
    private final int       columna;
    private final String    descripcion;

    public ErrorInfo(TipoError tipo, int linea, int columna, String descripcion) {
        this.tipo        = tipo;
        this.linea       = linea;
        this.columna     = columna;
        this.descripcion = descripcion;
    }

    public TipoError getTipo()        { return tipo; }
    public int       getLinea()       { return linea; }
    public int       getColumna()     { return columna; }
    public String    getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return String.format("[%s] L:%d C:%d — %s", tipo, linea, columna, descripcion);
    }
}