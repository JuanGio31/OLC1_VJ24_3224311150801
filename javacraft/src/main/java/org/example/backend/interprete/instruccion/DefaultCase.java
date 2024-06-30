package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.expresion.Nativo;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class DefaultCase extends Instruccion {

    private LinkedList<Instruccion> instruccions;

    public DefaultCase(LinkedList<Instruccion> instruccions, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.instruccions = instruccions;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var nuevaTabla = new TablaSimbolo(tabla);
        for (var i : this.instruccions) {
            if (i instanceof Break) {
                return i;
            }
            var resultado = i.interpretar(arbol, nuevaTabla);
            if (resultado instanceof Break) {
                return resultado;
            }
        }
        return null;
    }
}
