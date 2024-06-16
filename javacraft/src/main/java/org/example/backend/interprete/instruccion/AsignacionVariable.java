package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.ErrorM;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class AsignacionVariable extends Instruccion {
    private String id;
    private Instruccion expresion;

    public AsignacionVariable(String id, Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        //variable exista
        var variable = tabla.getVariable(id);
        if (variable == null) {
            return new ErrorM(TipoError.SEMANTICO, "Variable no exitente", this.linea, this.columna);
        }

        // interpretar el nuevo valor a asignar
        var newValor = this.expresion.interpretar(arbol, tabla);
        if (newValor instanceof ErrorM) {
            return newValor;
        }

        //verificar si es constante
        if (variable.isEsConstante()) {
            return new ErrorM(TipoError.SEMANTICO, "Variable constante", this.linea, this.columna);
        }

        //validar tipos
        if (variable.getTipo().getTipo() != this.expresion.tipo.getTipo()) {
            return new ErrorM(TipoError.SEMANTICO, "Tipos erroneos en asignacion", this.linea, this.columna);
        }
        //this.tipo.setTipo(variable.getTipo().getTipo());
        variable.setValue(newValor);
        return null;
    }
}
