package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class If extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public If(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }

        // ver que cond sea booleano
        if (this.condicion.tipo.getTipo() != TipoDeDato.BOOLEAN) {
            return new Errores(TipoError.SEMANTICO, "Expresion invalida",
                    this.linea, this.columna);
        }

        var newTabla = new TablaSimbolo(tabla);
        if ((boolean) cond) {
            for (var i : this.instrucciones) {
                if (i instanceof Break) {
                    return i;
                }
                var resultado = i.interpretar(arbol, newTabla);
                if (resultado instanceof Break) {
                    return resultado;
                }
                /*
                    Manejo de errores
                 */
            }
        }
        return null;
    }
}
