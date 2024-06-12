package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.Tree;

/**
 *
 * @author giovanic
 */
public class Nativo extends Instruccion {

    public Object valor;

    public Nativo(Object valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.valor = valor;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        return this.valor;
    }

}
