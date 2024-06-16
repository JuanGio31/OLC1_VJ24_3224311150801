package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.Simbolo;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.Tree;

public class DeclaracionVariable extends Instruccion {
    public String identificador;
    public Instruccion valor;
    private boolean esConstante;

    public DeclaracionVariable(boolean esConstante, String identificador, Instruccion valor, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.identificador = identificador;
        this.valor = valor;
        this.esConstante = esConstante;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var valorInterpretado = this.valor.interpretar(arbol, tabla);

        //validamos si es error
        if (valorInterpretado instanceof Errores) {
            return valorInterpretado;
        }

        //validamos los tipo
        if (this.valor.tipo.getTipo() != this.tipo.getTipo()) {
            return new Errores(TipoError.SEMANTICO, "Tipos erroneos", this.linea, this.columna);
        }

        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado);
        s.setEsConstante(this.esConstante);
        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores(TipoError.SEMANTICO, "Variable ya existente", this.linea, this.columna);
        }
        return null;
    }

    public boolean isConstante() {
        return esConstante;
    }
}

