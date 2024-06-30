package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class Retorno extends Instruccion {

    private Instruccion valor;

    public Retorno(Instruccion valor, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.valor = valor;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var temp = this.valor.interpretar(arbol, tabla);
        if (valor == null) {
            this.tipo.setTipo(TipoDeDato.VOID);
            return valor;
        }
        if (temp instanceof Errores) {
            return temp;
        }
        if (valor.tipo != null && valor.tipo.getTipo() != null) {
            this.tipo.setTipo(valor.tipo.getTipo());
        }
        return temp;
    }

    public Instruccion getValor() {
        return valor;
    }

    public void setValor(Instruccion valor) {
        this.valor = valor;
    }
}