package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.expresion.Nativo;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class AsignValorMatriz extends Instruccion {
    private String id;
    private Instruccion valor;
    private Instruccion posX;
    private Instruccion posY;

    public AsignValorMatriz(String id, Instruccion posY, Instruccion posX, Instruccion valor, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.id = id;
        this.posY = posY;
        this.posX = posX;
        this.valor = valor;

    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var variable = tabla.getVariable(id);
        if (variable == null) {
            return new Errores(TipoError.SEMANTICO, "La variable no existe.", this.linea, this.columna);
        }
        if (variable.getTipo().getTipo() != TipoDeDato.MATRIZ) {
            return new Errores(TipoError.SEMANTICO, "No es vector de 2-Dimensiones.", this.linea, this.columna);
        }
        var nuevoValor = this.valor.interpretar(arbol, tabla);
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }
        if (variable.isEsConstante()) {
            return new Errores(TipoError.SEMANTICO, "Arreglo constante: " + id, this.linea, this.columna);
        }
        if (variable.getTipoDato().getTipo() != this.valor.tipo.getTipo()) {
            return new Errores(TipoError.SEMANTICO,
                    "Tipos erroneos en asignacion ("
                            + variable.getTipoDato().getTipo()
                            + " != "
                            + this.valor.tipo.getTipo() + ")",
                    this.linea,
                    this.columna);
        }
        var datos = (LinkedList<LinkedList<Instruccion>>) variable.getValue();
        var indexI = posY.interpretar(arbol, tabla);
        var indexJ = posX.interpretar(arbol, tabla);
        if (posY.tipo.getTipo() != posX.tipo.getTipo()) {
            return new Errores(TipoError.SEMANTICO, "Indice no valido, debe ser entero", this.linea, this.columna);
        }
        if (posY.tipo.getTipo() != TipoDeDato.INT) {
            return new Errores(TipoError.SEMANTICO, "Indice invalido, debe ser entero", this.linea, this.columna);
        }
        if (((int) indexI > -1 && (int) indexI < datos.size())
                && ((int) indexJ > -1 && (int) indexJ < datos.getFirst().size())) {
            var temp = datos.get((int) indexI);
            temp.set((int) indexJ, new Nativo(nuevoValor, variable.getTipoDato(), this.linea, this.columna));
            variable.setValue(datos);
        } else {
            return new Errores(TipoError.SEMANTICO, "ArrayIndexException", this.linea, this.columna);
        }
        return null;
    }
}