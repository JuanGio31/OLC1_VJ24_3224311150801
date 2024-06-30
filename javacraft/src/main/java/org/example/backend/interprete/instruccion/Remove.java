package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class Remove extends Instruccion {
    private Instruccion posicion;
    private String id;

    public Remove(String id, Instruccion posicion, int linea, int columna) {
        super(new Tipo(TipoDeDato.INT), linea, columna);
        this.posicion = posicion;
        this.id = id;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var variable = tabla.getVariable(id);
        if (variable == null) {
            return new Errores(TipoError.SEMANTICO, "No existente Coleccion: " + id, this.linea, this.columna);
        }
        if (this.posicion.tipo.getTipo() != TipoDeDato.INT) {
            return new Errores(TipoError.SEMANTICO, "Posicion no valida: " + id, this.linea, this.columna);
        }

        if (variable.getTipo().getTipo() != TipoDeDato.LIST) {
            return new Errores(TipoError.SINTACTICO, "Instruccion invalida: " + id + ".remove", this.linea, this.columna);
        }
        var index = this.posicion.interpretar(arbol, tabla);
        LinkedList<Instruccion> temp = (LinkedList<Instruccion>) variable.getValue();
        if ((int) index > -1 && (int) index < temp.size()) {
            for (int i = 0; i < temp.size(); i++) {
                if ((int) index == i) {
                    var valor = temp.remove(i).interpretar(arbol, tabla);
                    this.tipo.setTipo(variable.getTipoDato().getTipo());
                    return valor;
                }
            }
        }
        return new Errores(TipoError.SEMANTICO, "ArrayIndexException: " + ((int) index), this.linea, this.columna);
    }
}