package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.*;

import java.util.LinkedList;

/**
 * @author giovanic
 */
public class DecColeccion extends Instruccion {

    private LinkedList<Instruccion> valores;
    private String id;
    private boolean esConstante;
    private TipoDeDato tipoDato;

    /**
     * Constructor para vectores unidimensionales
     *
     * @param tipoDato    tipo -> vector
     * @param esConstante definir si el vector es constante
     * @param id          identificador
     * @param valores     lista de valores
     * @param tipo        tipo de dato
     * @param linea       posicion -> linea
     * @param columna     posicion -> columna
     */
    public DecColeccion(TipoDeDato tipoDato, boolean esConstante, String id, LinkedList<Instruccion> valores, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.esConstante = esConstante;
        this.id = id;
        this.valores = valores;
        this.tipoDato = tipoDato;
    }

    public DecColeccion(TipoDeDato tipoDato, String id, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.esConstante = false;
        this.id = id;
        this.valores = new LinkedList<>();
        this.tipoDato = tipoDato;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        if (tipoDato == TipoDeDato.VECTOR) {
            try {
                var valor = datos(arbol, tabla);
                Simbolo sym = new Simbolo(this.tipo, this.id, valor, this.tipoDato);
                sym.setEsConstante(this.esConstante);
                boolean creacion = tabla.setVariable(sym);
                if (!creacion) {
                    return new Errores(TipoError.SEMANTICO, "Variable ya existente: " + sym.getId(), this.linea, this.columna);
                }
            } catch (ErrorTipo err) {
                return new Errores(TipoError.SEMANTICO, "Tipos erroneos", this.linea, this.columna);
            }
        }
        if (tipoDato == TipoDeDato.LIST) {
            Simbolo sym = new Simbolo(this.tipo, this.id, this.valores, this.tipoDato);
            sym.setEsConstante(this.esConstante);
            boolean creacion = tabla.setVariable(sym);
            if (!creacion) {
                return new Errores(TipoError.SEMANTICO, "Variable ya existente: " + sym.getId(), this.linea, this.columna);
            }
        }
        return null;
    }

    private Object datos(Tree arbol, TablaSimbolo tablaSimbolo) throws ErrorTipo {
        LinkedList<Instruccion> temp = new LinkedList<>();
        for (Instruccion val : valores) {
            var valorInter = val.interpretar(arbol, tablaSimbolo);
            if (valorInter instanceof Errores) {
                throw new ErrorTipo("TIPOS INCOMPATIBLES-> " + this.tipo.getTipo() + " != " + val.tipo.getTipo() + ".");
            }

            if (this.tipo.getTipo() != val.tipo.getTipo()) {
                throw new ErrorTipo("TIPOS INCOMPATIBLES-> " + this.tipo.getTipo() + " != " + val.tipo.getTipo() + ".");
            }
            temp.add(val);
        }
        return temp;
    }
}