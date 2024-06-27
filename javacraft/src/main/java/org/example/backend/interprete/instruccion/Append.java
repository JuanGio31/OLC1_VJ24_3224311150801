package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.*;

import java.util.LinkedList;

public class Append extends Instruccion {
    private Instruccion valor;
    private String id;

    public Append(String id, Instruccion valor, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.id = id;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var lista = tabla.getVariable(id);
        if (lista == null) {
            return new Errores(TipoError.SEMANTICO, "No existente Coleccion: " + id, this.linea, this.columna);
        }
        if (lista.getTipo().getTipo() == TipoDeDato.LIST) {
            var nuevoValor = this.valor.interpretar(arbol, tabla);
            if (nuevoValor instanceof Errores) {
                return nuevoValor;
            }
            if (lista.getTipoDato().getTipo() != this.valor.tipo.getTipo()) {
                return new Errores(TipoError.SEMANTICO,
                        "Tipos erroneos en asignacion ("
                                + lista.getTipoDato().getTipo()
                                + " != "
                                + this.valor.tipo.getTipo() + ")",
                        this.linea,
                        this.columna);
            }
            LinkedList<Instruccion> temp = (LinkedList<Instruccion>) lista.getValue();
            temp.add(valor);
            lista.setValue(temp);
            return null;
        }
        return new Errores(TipoError.SINTACTICO, "Instruccion invalida: " + id + ".append ", this.linea, this.columna);
    }
}
