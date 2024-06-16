package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.ErrorM;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class Logicos extends Instruccion {
    private Instruccion operando1;
    private Instruccion operando2;
    private OpLogicos operacion;
    private Instruccion operandoUnico;

    public Logicos(Instruccion operando1, Instruccion operando2, OpLogicos operacion, int linea, int columna) {
        super(new Tipo(TipoDeDato.BOOLEAN), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    public Logicos(Instruccion operandoUnico, OpLogicos operacion, int linea, int columna) {
        super(new Tipo(TipoDeDato.BOOLEAN), linea, columna);
        this.operacion = operacion;
        this.operandoUnico = operandoUnico;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        Object expr1 = null, expr2 = null, unico = null;
        if (this.operandoUnico != null) {
            unico = this.operandoUnico.interpretar(arbol, tabla);
            if (unico instanceof ErrorM) {
                return unico;
            }
        } else {
            expr1 = this.operando1.interpretar(arbol, tabla);
            if (expr1 instanceof ErrorM) {
                return expr1;
            }
            expr2 = this.operando2.interpretar(arbol, tabla);
            if (expr2 instanceof ErrorM) {
                return expr2;
            }
        }

        return switch (operacion) {
            case AND -> this.opAnd(expr1, expr2);
            case OR -> this.opOr(expr1, expr2);
            case NOT -> this.opNot(unico);
            case XOR -> this.opXor(expr1, expr2);
            default -> new ErrorM(TipoError.SEMANTICO, "Operador invalido", this.linea, this.columna);
        };
    }

    private Object opNot(Object unico) {

        return null;
    }

    private Object opXor(Object expr1, Object expr2) {
        return null;
    }

    private Object opOr(Object expr1, Object expr2) {
        return null;
    }

    private Object opAnd(Object expr1, Object expr2) {
        return null;
    }
}
/*
    //negacion
    public Aritmeticas(Instruccion operandoUnico, OperadoresAritmeticos operacion, int linea, int col) {
        super(new Tipo(TipoDeDato.INT), linea, col);
        this.operacion = operacion;
        this.operandoUnico = operandoUnico;
 */