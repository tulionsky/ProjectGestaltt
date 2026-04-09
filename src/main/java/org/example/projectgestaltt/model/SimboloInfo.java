package org.example.projectgestaltt.model;

/**
 * Entrada de la tabla de símbolos.
 */
public class SimboloInfo {

    private final String nombre;
    private final String tipo;
    private final String ambito;
    private final int    linea;
    private String valor;

    public SimboloInfo(String nombre, String tipo, String ambito, int linea, String valor) {
        this.nombre = nombre;
        this.tipo   = tipo;
        this.ambito = ambito;
        this.linea  = linea;
        this.valor  = valor;
    }

    public String getNombre() { return nombre; }
    public String getTipo()   { return tipo; }
    public String getAmbito() { return ambito; }
    public int    getLinea()  { return linea; }
    public String getValor()  { return valor; }
    public void   setValor(String v) { this.valor = v; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | L:%d | %s", nombre, tipo, ambito, linea, valor);
    }
}