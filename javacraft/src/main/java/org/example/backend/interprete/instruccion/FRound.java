package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class FRound extends Instruccion {
    private Instruccion expresion;

    public FRound(Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDeDato.INT), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var obj = this.expresion.interpretar(arbol, tabla);
        if (obj instanceof Errores) {
            return obj;
        }
        if (this.expresion.tipo.getTipo() == TipoDeDato.DOUBLE) {
            double resto = (double) obj - Math.floor((double) obj);
            if (resto >= 0.5) {
                return (int) Math.ceil((double) obj);
            }
            return (int) Math.floor((double) obj);
        }
        if (this.expresion.tipo.getTipo() == TipoDeDato.INT) {
            return obj;
        }
        return new Errores(TipoError.SEMANTICO, "No es valor numerico: " + obj, this.linea, this.columna);
    }
}