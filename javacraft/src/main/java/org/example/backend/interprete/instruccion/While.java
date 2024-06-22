package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class While extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instruccions;

    public While(Instruccion condicion, LinkedList<Instruccion> instruccions, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instruccions = instruccions;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var nuevaTabla = new TablaSimbolo(tabla);
        nuevaTabla.setNombre("TABLA WHILE");
        var cond = this.condicion.interpretar(arbol, nuevaTabla);
        if (cond instanceof Errores) {
            return cond;
        }
        System.out.println("+++++++++++++++++WHILE");
        // ver que cond sea booleano
        if (this.condicion.tipo.getTipo() != TipoDeDato.BOOLEAN) {
            return new Errores(TipoError.SEMANTICO, "Expresion invalida", this.linea, this.columna);
        }

        while ((boolean) this.condicion.interpretar(arbol, nuevaTabla)) {

            var nuevaTabla2 = new TablaSimbolo(tabla);
            for (var i : this.instruccions) {

                if (i instanceof Break) {
                    return null;
                }
                if (i instanceof Continue) {
                    break;
                }
                var resultado = i.interpretar(arbol, nuevaTabla2);
                if (resultado instanceof Break) {
                    return null;
                }
                if (resultado instanceof Continue) {
                    break;
                }
            }
        }
        return null;
    }
}

/*
do{
     var nuevaTabla2 = new TablaSimbolo(tabla);
            for (var i : this.instruccions) {

                if (i instanceof Break) {
                    return null;
                }
                if (i instanceof Continue) {
                    break;
                }
                var resultado = i.interpretar(arbol, nuevaTabla2);
                if (resultado instanceof Break) {
                    return null;
                }
                if (resultado instanceof Continue) {
                    break;
                }
            }
          }while ((boolean) this.condicion.interpretar(arbol, nuevaTabla))
 */
