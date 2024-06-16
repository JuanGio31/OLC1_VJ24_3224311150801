package org.example.backend.interprete.simbol;

/**
 * @author giovanic
 */
public class Simbolo {

    private Tipo tipo;
    private String id;
    private Object value;
    private boolean esConstante;

    public Simbolo(Tipo tipo, String id, Object valor) {
        this.tipo = tipo;
        this.id = id;
        this.value = valor;
        this.esConstante = false;
    }

    public Simbolo(Tipo tipo, String id) {
        this.tipo = tipo;
        this.id = id;
        this.esConstante = false;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isEsConstante() {
        return esConstante;
    }

    public void setEsConstante(boolean esConstante) {
        this.esConstante = esConstante;
    }
}