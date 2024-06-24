package org.example.backend.interprete.expresion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.Objects;

public class AccesoValorVector extends Instruccion {
    private String id;
    private Instruccion posicion;

    public AccesoValorVector(String id, Instruccion posicion, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
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
        if ((int) pos > -1 && (int) pos < ((Object[]) valor.getValue()).length) {
            var v = ((Object[]) valor.getValue());
            Nativo nativo = (Nativo) v[(int) pos];
            return nativo.interpretar(arbol, tabla);
        }
        return new Errores(TipoError.SEMANTICO, "ArrayIndexException", this.linea, this.columna);
    }
}
