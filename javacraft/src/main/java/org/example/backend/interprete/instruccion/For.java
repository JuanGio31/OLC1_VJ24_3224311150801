package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class For extends Instruccion {
    private Instruccion condicion;
    private Instruccion asignacion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> instrucciones;

    public For(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion,
               LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.condicion = condicion;
        this.asignacion = asignacion;
        this.actualizacion = actualizacion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var newTabla = new TablaSimbolo(tabla);

        // asignacion/declaracion
        var res1 = this.asignacion.interpretar(arbol, newTabla);
        if (res1 instanceof Errores) {
            return res1;
        }

        //validar la condicion -> Booleano
        var cond = this.condicion.interpretar(arbol, newTabla);
        if (cond instanceof Errores) {
            return cond;
        }

        if (this.condicion.tipo.getTipo() != TipoDeDato.BOOLEAN) {
            return new Errores(TipoError.SEMANTICO, "La condicion debe ser bool", this.linea, this.columna);
        }

        while ((boolean) this.condicion.interpretar(arbol, newTabla)) {
            //nuevo entorno
            var newTabla2 = new TablaSimbolo(newTabla);

            //ejecutar instrucciones
            for (var i : this.instrucciones) {
                if (i instanceof Break) {
                    return null;
                }
                var resIns = i.interpretar(arbol, newTabla2);
                if (resIns instanceof Break) {
                    return null;
                }
            }

            //actualizar la variable
            var act = this.actualizacion.interpretar(arbol, newTabla);
            if (act instanceof Errores) {
                return act;
            }
        }
        return null;
    }

}
