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

public class FBuscar extends Instruccion {
    private String id;
    private Instruccion expresion;

    public FBuscar(String id, Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDeDato.BOOLEAN), linea, columna);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var variable = tabla.getVariable(id);
        if (variable == null) {
            return new Errores(TipoError.SEMANTICO, "Variable no existente: " + id, this.linea, this.columna);
        }
        if (variable.getTipo().getTipo() == TipoDeDato.VECTOR
                || variable.getTipo().getTipo() == TipoDeDato.LIST) {
            var valor = this.expresion.interpretar(arbol, tabla);
            if (valor instanceof Errores) {
                return valor;
            }
            if (valor instanceof LinkedList) {
                return new Errores(TipoError.SEMANTICO, "Tipo de datos erroneos.", this.linea, this.columna);
            }
            if (variable.getTipoDato().getTipo() != this.expresion.tipo.getTipo()) {
                return new Errores(TipoError.SEMANTICO, "Tipo de datos erroneos.", this.linea, this.columna);
            }
            LinkedList temp = (LinkedList) variable.getValue();
            for (Object o : temp) {
                var x = (Nativo) o;
                var evaluar = x.interpretar(arbol, tabla);
                if (evaluar instanceof Errores) {
                    return x;
                }
                if (valor.equals(evaluar)) {
                    return true;
                }
            }
        }
        return false;
    }
}
