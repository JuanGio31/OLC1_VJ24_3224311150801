package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.*;

import java.util.LinkedList;

public class FLength extends Instruccion {
    private Instruccion expr;

    public FLength(Instruccion expr, int linea, int columna) {
        super(new Tipo(TipoDeDato.INT), linea, columna);
        this.expr = expr;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var obj = this.expr.interpretar(arbol, tabla);
        if (obj instanceof Errores) {
            return obj;
        }
        if (obj instanceof String cadena) {
            return cadena.length();
        }
        if (obj instanceof LinkedList coleccion) {
            return coleccion.size();
        }
        return new Errores(TipoError.SEMANTICO, "Tipo de dato erroneo ", this.linea, this.columna);
    }
}