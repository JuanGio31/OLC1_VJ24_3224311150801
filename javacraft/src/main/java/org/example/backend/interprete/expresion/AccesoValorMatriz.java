package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class AccesoValorMatriz extends Instruccion {
    private String id;
    private Instruccion posX;   //se refiere a las columnas
    private Instruccion posY;   //se refiere a las filas

    public AccesoValorMatriz(String id, Instruccion posX, Instruccion posY, int linea, int columna) {
        super(new Tipo(TipoDeDato.INT), linea, columna);
        this.id = id;
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var variable = tabla.getVariable(id);
        if (variable == null) {
            return new Errores(TipoError.SEMANTICO, "No existe la variable: " + id, this.linea, this.columna);
        }
        var indexI = this.posY.interpretar(arbol, tabla);
        var indexJ = this.posX.interpretar(arbol, tabla);
        if (this.posX.tipo.getTipo() != this.posY.tipo.getTipo()) {
            return new Errores(TipoError.SEMANTICO, "Indice no aceptado.", this.linea, this.columna);
        }
        LinkedList<LinkedList> datos = (LinkedList<LinkedList>) variable.getValue();
        if (((int) indexI > -1 && (int) indexI < datos.size())
                && ((int) indexJ > -1 && (int) indexJ < datos.getFirst().size())) {
            Instruccion nativo = (Instruccion) datos.get((int) indexI).get((int) indexJ);
            this.tipo.setTipo(variable.getTipoDato().getTipo());
            return nativo.interpretar(arbol, tabla);
        }
        return new Errores(TipoError.SEMANTICO, "ArrayIndexException.", this.linea, this.columna);
    }
}