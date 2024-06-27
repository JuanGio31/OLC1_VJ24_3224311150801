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

public class AsignacionValorEnPosicion extends Instruccion {
    private String id;
    private Instruccion valor;
    private Instruccion posicion;

    public AsignacionValorEnPosicion(String id, Instruccion valor, Instruccion posicion, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.id = id;
        this.valor = valor;
        this.posicion = posicion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var arr = tabla.getVariable(id);
        if (arr == null) {
            return new Errores(TipoError.SEMANTICO, "No existente Coleccion: " + id, this.linea, this.columna);
        }
        if (arr.getTipo().getTipo() != TipoDeDato.LIST && arr.getTipo().getTipo() != TipoDeDato.VECTOR) {
            return new Errores(TipoError.SEMANTICO, "No es Coleccion: " + id, this.linea, this.columna);
        }
        var nuevoValor = this.valor.interpretar(arbol, tabla);
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }
        if (arr.isEsConstante()) {
            return new Errores(TipoError.SEMANTICO, "Arreglo constante: " + id, this.linea, this.columna);
        }
        if (arr.getTipoDato().getTipo() != this.valor.tipo.getTipo()) {
            return new Errores(TipoError.SEMANTICO,
                    "Tipos erroneos en asignacion ("
                            + arr.getTipoDato().getTipo()
                            + " != "
                            + this.valor.tipo.getTipo() + ")",
                    this.linea,
                    this.columna);
        }
        LinkedList<Instruccion> datos = (LinkedList<Instruccion>) arr.getValue();
        var index = posicion.interpretar(arbol, tabla);
        if (posicion.tipo.getTipo() != TipoDeDato.INT) {
            return new Errores(TipoError.SEMANTICO, "Indice no valido, debe ser entero", this.linea, this.columna);
        }

        if ((int) index > -1 && (int) index < datos.size()) {
            for (int i = 0; i < datos.size(); i++) {
                if (i == (int) index) {
                    datos.set(i, new Nativo(nuevoValor, tipo, this.linea, this.columna));
                    break;
                }
            }
            arr.setValue(datos);
        } else {
            return new Errores(TipoError.SEMANTICO, "ArrayIndexException", this.linea, this.columna);
        }
        return null;
    }
}