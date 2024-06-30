package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class AccesoValorVector extends Instruccion {
    private String id;
    private Instruccion posicion;

    public AccesoValorVector(String id, Instruccion posicion, int linea, int columna) {
        super(new Tipo(TipoDeDato.INT), linea, columna);
        this.id = id;
        this.posicion = posicion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var valor = tabla.getVariable(id);
        if (valor == null) {
            return new Errores(TipoError.SEMANTICO, "No existe el arreglo", this.linea, this.columna);
        }
        var pos = this.posicion.interpretar(arbol, tabla);
        if (pos instanceof Errores) {
            return pos;
        }
        if (posicion.tipo.getTipo() != TipoDeDato.INT) {
            return new Errores(TipoError.SINTACTICO, "Indice incorrecto debe ser del tipo ENTERO", this.linea, this.columna);
        }
        LinkedList<Instruccion> list = null;
        var value = valor.getValue();
        if (value instanceof LinkedList) {
            list = (LinkedList<Instruccion>) value;
        } else {
            System.out.println("no es");
        }
        if ((int) pos > -1) {
            assert list != null;
            if ((int) pos < list.size()) {
                this.tipo.setTipo(valor.getTipoDato().getTipo());
                return list.get((int) pos).interpretar(arbol, tabla);
            }
        }
        return new Errores(TipoError.SEMANTICO, "ArrayIndexException", this.linea, this.columna);
    }

    public String getId() {
        return id;
    }
}