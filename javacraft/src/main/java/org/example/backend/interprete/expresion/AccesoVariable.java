package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

public class AccesoVariable extends Instruccion {
    private String id;

    public AccesoVariable(String id, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.id = id;
    }


    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var valor = tabla.getVariable(this.id);
        if (valor == null) {
            return new Errores(TipoError.SEMANTICO, "Variable no existente", this.linea, this.columna);
        }
        this.tipo.setTipo(valor.getTipo().getTipo());
        return valor.getValue();
    }
}
