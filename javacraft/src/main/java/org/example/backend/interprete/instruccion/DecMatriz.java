package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.*;

import java.util.LinkedList;

public class DecMatriz extends Instruccion {
    private LinkedList<LinkedList> valores;
    private String id;
    private boolean esConstante;
    private TipoDeDato tipoDato;

    public DecMatriz(TipoDeDato tipoDato, boolean esConstante, String id, LinkedList<LinkedList> valores,
                     Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.valores = valores;
        this.id = id;
        this.esConstante = esConstante;
        this.tipoDato = tipoDato;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        if (verificarDimensiones()) {
            Object valorInterpretado = new Object();
            if (verificarTipos(arbol, tabla, valorInterpretado)) {
                Simbolo sym = new Simbolo(this.tipo, this.id, valores, this.tipoDato);
                sym.setEsConstante(this.esConstante);
                boolean creacion = tabla.setVariable(sym);
                if (!creacion) {
                    return new Errores(TipoError.SEMANTICO, "Variable ya existente: " + sym.getId(), this.linea, this.columna);
                }
            } else {
                if (valorInterpretado instanceof Errores) {
                    return valorInterpretado;
                }
                return new Errores(TipoError.SEMANTICO, "Tipos erroneos", this.linea, this.columna);
            }
        } else {
            return new Errores(TipoError.SEMANTICO, "Dimensiones diferentes", this.linea, this.columna);
        }
        return null;
    }

    private boolean verificarDimensiones() {
        int length = this.valores.getFirst().size();
        for (var valore : this.valores) { //recorrer filas
            if (length != valore.size()) {
                return false;
            }
        }
        return true;
    }

    private boolean verificarTipos(Tree arbol, TablaSimbolo tabla, Object valorInterpretado) {
        for (int i = 0; i < this.valores.size(); i++) {
            for (int j = 0; j < this.valores.getFirst().size(); j++) {
                var temp = (Instruccion) this.valores.get(i).get(j);
                valorInterpretado = temp.interpretar(arbol, tabla);
                if (valorInterpretado instanceof Errores) {
                    return false;
                } else if (this.tipo.getTipo() != temp.tipo.getTipo()) {
                    return false;
                }
            }
        }
        return true;
    }
}