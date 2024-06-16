package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.ErrorM;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.Objects;

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
        var opU = this.operandoUnico.tipo.getTipo();
        if (Objects.requireNonNull(opU) == TipoDeDato.BOOLEAN) {
            return !Boolean.parseBoolean(String.valueOf(unico));
        }
        return new ErrorM(TipoError.SEMANTICO, "Negacion erronea", this.linea, this.columna);
    }

    private Object opXor(Object expr1, Object expr2) {
        var op1 = this.operando1.tipo.getTipo();
        var op2 = this.operando2.tipo.getTipo();
        if (Objects.requireNonNull(op1) == TipoDeDato.BOOLEAN && Objects.requireNonNull(op2) == TipoDeDato.BOOLEAN) {
            return Boolean.parseBoolean(String.valueOf(expr1)) ^ Boolean.parseBoolean(String.valueOf(expr2));
        }
        return new ErrorM(TipoError.SEMANTICO, "Operador invalido", this.linea, this.columna);
    }

    private Object opOr(Object expr1, Object expr2) {
        var op1 = this.operando1.tipo.getTipo();
        var op2 = this.operando2.tipo.getTipo();
        if (Objects.requireNonNull(op1) == TipoDeDato.BOOLEAN && Objects.requireNonNull(op2) == TipoDeDato.BOOLEAN) {
            return Boolean.parseBoolean(String.valueOf(expr1)) || Boolean.parseBoolean(String.valueOf(expr2));
        }
        return new ErrorM(TipoError.SEMANTICO, "Operador invalido", this.linea, this.columna);
    }

    private Object opAnd(Object expr1, Object expr2) {
        var op1 = this.operando1.tipo.getTipo();
        var op2 = this.operando2.tipo.getTipo();
        if (Objects.requireNonNull(op1) == TipoDeDato.BOOLEAN && Objects.requireNonNull(op2) == TipoDeDato.BOOLEAN) {
            return Boolean.parseBoolean(String.valueOf(expr1)) && Boolean.parseBoolean(String.valueOf(expr2));
        }
        return new ErrorM(TipoError.SEMANTICO, "Operador invalido", this.linea, this.columna);
    }
}