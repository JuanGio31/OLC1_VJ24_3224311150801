package org.example.backend.interprete.simbol;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;

import java.util.LinkedList;

/**
 * @author giovanic
 */
public class Simbolo {

    private Tipo tipoDato;
    private String id;
    private Object value;
    private Tipo tipo;
    private boolean esConstante;

    /**
     * Contructor para inicializar variables con su valor, tipo, valor
     *
     * @param tipoDato tipo de dato de la variable
     * @param id       identificador de la variable
     * @param valor    valor de la variable
     */
    public Simbolo(Tipo tipoDato, String id, Object valor) {
        this.tipoDato = tipoDato;
        this.id = id;
        this.value = valor;
        this.tipo = new Tipo(TipoDeDato.VARIABLE);
        this.esConstante = false;
    }

    /**
     * Constructor para inicializar la variable con sus valores por defecto, segun tipo de dato
     *
     * @param tipoDato tipo de dato de la variable
     * @param id       identificador de la variable
     */
    public Simbolo(Tipo tipoDato, String id) {
        this.tipoDato = tipoDato;
        this.id = id;
        this.esConstante = false;
        this.tipo = new Tipo(TipoDeDato.VARIABLE);
        assignAuto();
    }

    /**
     * Construcor para inicializar vectores, listas, y metodos
     *
     * @param tipoDato tipo de dato del simbolo
     * @param id       identificador
     * @param value    valor de la funcion, vector, lista
     * @param tipo     tipo de dato
     */
    public Simbolo(Tipo tipoDato, String id, Object value, TipoDeDato tipo) {
        this.tipoDato = tipoDato;
        this.id = id;
        this.value = value;
        this.tipo = new Tipo(tipo);
    }

    public void assignAuto() {
        var aux = this.tipoDato.getTipo();
        switch (aux) {
            case INT -> this.setValue(0);
            case DOUBLE -> this.setValue(0.0);
            case BOOLEAN -> this.setValue(true);
            case CHAR -> this.setValue('\u0000');
            case STRING -> this.setValue("");
        }
    }

    /**
     * metodo para retornar el tipo de dato (INT, DOUBLE...STRING)
     *
     * @return tipo de dato
     */
    public Tipo getTipoDato() {
        return tipoDato;
    }

    /**
     * modificar el tipo de dato (INT, DOUBLE...STRING)
     */
    public void setTipoDato(Tipo tipoDato) {
        this.tipoDato = tipoDato;
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

    /**
     * metodo para retornar el tipo de dato (VAR..., VECTOR...FUN)
     *
     * @return EL TIPO
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * MODIFICAR el tipo de dato (VAR..., VECTOR...FUN)
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Simbolo{" +
                "tipo=" + tipoDato.getTipo() +
                ", id='" + id + '\'' +
                ", value=" + getVal() +
                ", tipoDeDato=" + tipo.getTipo() +
                ", esConstante=" + esConstante +
                '}';
    }

    private Object getVal() {
        if (tipo.getTipo() == TipoDeDato.VARIABLE) {
            return value;
        } else if (tipo.getTipo() == TipoDeDato.VECTOR ||
                tipo.getTipo() == TipoDeDato.LIST) {
            LinkedList<Instruccion> datos = (LinkedList<Instruccion>) getValue();
            LinkedList<Object> valores = new LinkedList<>();
            for (Instruccion dato : datos) {
                var temp = dato.interpretar(null, null);
                if (temp instanceof String) {
                    valores.add("\"" + temp + "\"");
                    //valores.add(temp);
                } else if (temp instanceof Character) {
                    valores.add("'" + temp + "'");
                } else {
                    valores.add(temp);
                }
            }
            return valores;
        } else if (tipo.getTipo() == TipoDeDato.MATRIZ) {
            LinkedList<LinkedList> valores = (LinkedList<LinkedList>) this.getValue();
            var datos = new LinkedList<LinkedList>();
            for (int i = 0; i < valores.size(); i++) {
                LinkedList obj = new LinkedList();
                for (int j = 0; j < valores.getFirst().size(); j++) {
                    var temp = (Instruccion) valores.get(i).get(j);
                    var valorInterpretado = temp.interpretar(null, null);
                    if (valorInterpretado instanceof String) {
                        obj.add("\"" + valorInterpretado + "\"");
                    } else if (valorInterpretado instanceof Character) {
                        obj.add("'" + valorInterpretado + "'");
                    } else {
                        obj.add(valorInterpretado);
                    }
                }
                datos.add(obj);
            }
            return datos;
        }
        return null;
    }
}