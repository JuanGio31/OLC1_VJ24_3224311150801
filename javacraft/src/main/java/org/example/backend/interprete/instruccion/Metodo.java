package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class Metodo extends Instruccion {
    private String id;
    private LinkedList<Instruccion> instruccions;

    public Metodo(String id, LinkedList<Instruccion> instruccions, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.instruccions = instruccions;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Instruccion> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(LinkedList<Instruccion> instruccions) {
        this.instruccions = instruccions;
    }
}
