package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.*;

import java.util.HashMap;
import java.util.LinkedList;

public class DeclararEstructura extends Instruccion {
    private String id;
    private HashMap<String, Tipo> parametros;
    private HashMap<String, Simbolo> variables; // <id, simbolo>

    public DeclararEstructura(HashMap<String, Tipo> parametros, String id, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.parametros = parametros;
        this.variables = new HashMap<>();
        this.id = id;
    }


    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var variable = tabla.getVariable(id);
        if (variable != null) {
            return new Errores(TipoError.SEMANTICO, "Variable ya existente: " + id, this.linea, this.columna);
        }
        if (this.parametros == null) {
            return new Errores(TipoError.SEMANTICO, "Parametros vacios", this.linea, this.columna);
        }
        return null;
    }
}