package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class Break extends Instruccion {

    public Break(int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        return null;
    }
}
