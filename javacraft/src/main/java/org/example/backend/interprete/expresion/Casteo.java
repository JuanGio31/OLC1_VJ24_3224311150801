package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class Casteo extends Instruccion {
    private Instruccion exp;
    private Tipo castear;

    public Casteo(Tipo castear, Instruccion exp, int linea, int columna) {
        super(new Tipo(TipoDeDato.INT), linea, columna);
        this.castear = castear;
        this.exp = exp;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var operando = this.exp.interpretar(arbol, tabla);

        if (operando instanceof Errores) {
            return operando;
        }

        return switch (castear.getTipo()) {
            case INT -> this.convertirAInt(operando);
            case DOUBLE -> this.convertirADouble(operando);
            case CHAR -> this.convertirAChar(operando);
            default -> new Errores(TipoError.SEMANTICO, "No se puede castear", this.linea, this.columna);
        };
    }

    private Object convertirAChar(Object operando) {
        var aux = this.exp.tipo.getTipo();

        if (aux == TipoDeDato.INT) {
            this.tipo.setTipo(TipoDeDato.CHAR);
            return (char) Integer.parseInt(operando.toString());
        }
        return new Errores(TipoError.SEMANTICO, "No se puede castear", this.linea, this.columna);
    }

    private Object convertirADouble(Object operando) {
        var aux = this.exp.tipo.getTipo();

        if (aux == TipoDeDato.INT) {
            this.tipo.setTipo(TipoDeDato.DOUBLE);
            return Double.parseDouble(operando.toString());
        }

        if (aux == TipoDeDato.CHAR) {
            this.tipo.setTipo(TipoDeDato.DOUBLE);
            return (double) operando.toString().charAt(0);
        }
        return new Errores(TipoError.SEMANTICO, "No se puede castear", this.linea, this.columna);
    }

    private Object convertirAInt(Object operando) {
        var aux = this.exp.tipo.getTipo();

        if (aux == TipoDeDato.DOUBLE) {
            this.tipo.setTipo(TipoDeDato.INT);
            return (int) Double.parseDouble(operando.toString());
        }

        if (aux == TipoDeDato.CHAR) {
            this.tipo.setTipo(TipoDeDato.INT);
            return (int) operando.toString().charAt(0);
        }
        return new Errores(TipoError.SEMANTICO, "No se puede castear", this.linea, this.columna);
    }
}
