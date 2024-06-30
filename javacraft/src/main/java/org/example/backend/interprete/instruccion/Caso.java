package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class Caso extends Instruccion {

    private Instruccion condicion;
    protected LinkedList<Instruccion> instruccions;

    public Caso(Instruccion condicion, LinkedList<Instruccion> instruccions, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instruccions = instruccions;
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

    public Instruccion getCondicion() {
        return condicion;
    }
}