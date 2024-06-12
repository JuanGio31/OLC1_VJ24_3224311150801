package org.example.backend.interprete.abstracto;

import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.Tree;

/**
 * @author giovanic
 */
abstract public class Instruccion {

    public Tipo tipo;
    public int linea;
    public int columna;

    public Instruccion(Tipo tipo, int linea, int columna) {
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    public abstract Object interpretar(Tree arbol, TablaSimbolo tabla);

}
