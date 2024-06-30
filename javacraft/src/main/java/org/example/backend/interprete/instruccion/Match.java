package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.expresion.OpRelacional;
import org.example.backend.interprete.expresion.Relacionales;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class Match extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> casos;

    public Match(Instruccion condicion, LinkedList<Instruccion> casos, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.condicion = condicion;
        this.casos = casos;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }
        if (this.condicion.tipo.getTipo() == TipoDeDato.BOOLEAN) {
            return new Errores(TipoError.SEMANTICO, "Expresion invalida", this.linea, this.columna);
        }

        var nuevaTabla = new TablaSimbolo(tabla);
        while (casos.size() > 1) {
            //Instruccion condicion1, Instruccion condicion2, OpRelacional relacion, int linea, int columna
            Caso temp = (Caso) casos.getFirst();
            Relacionales rl = new Relacionales(condicion, temp.getCondicion(), OpRelacional.IGUAL, this.linea, this.columna);
            if (Boolean.parseBoolean(rl.interpretar(arbol, tabla).toString())) {
                casos.getFirst().interpretar(arbol, nuevaTabla);
                break;
            }
            casos.pollFirst();
        }
        if (casos.size() == 1) {
            casos.getFirst().interpretar(arbol, nuevaTabla);
            casos.pollFirst();
        }

        return null;
    }
}
/*
abiguedad -> una gramatica que el compilador lo puede interpretar de distintas formas
 */