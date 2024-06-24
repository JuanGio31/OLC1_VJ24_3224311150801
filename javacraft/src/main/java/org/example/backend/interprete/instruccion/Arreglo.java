package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.Tree;

/**
 *
 * @author giovanic
 */
public class Arreglo extends Instruccion {
    
    public Arreglo(Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
