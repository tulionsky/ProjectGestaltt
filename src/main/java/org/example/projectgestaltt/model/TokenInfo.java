package org.example.projectgestaltt.model;

/**
 * Representa un token identificado por el analizador léxico.
 */
public class TokenInfo {

    private final String tipo;
    private final String valor;
    private final int linea;
    private final int columna;

    public TokenInfo(String tipo, String valor, int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    public String getTipo()    { return tipo; }
    public String getValor()   { return valor; }
    public int    getLinea()   { return linea; }
    public int    getColumna() { return columna; }

    @Override
    public String toString() {
        return String.format("[%s] '%s'  L:%d C:%d", tipo, valor, linea, columna);
    }
}