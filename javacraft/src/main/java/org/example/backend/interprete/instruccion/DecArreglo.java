package org.example.backend.interprete.instruccion;

import org.apache.tools.ant.taskdefs.optional.sos.SOS;
import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.*;

import java.text.ParseException;
import java.util.LinkedList;

/**
 * @author giovanic
 */
public class DecArreglo extends Instruccion {

    private LinkedList<Instruccion> valores;
    private String id;
    private boolean esConstante;

    public DecArreglo(boolean esConstante, String id, LinkedList<Instruccion> valores, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.esConstante = esConstante;
        this.id = id;
        this.valores = valores;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        try {
            Object[] val = datos(arbol, tabla).toArray();
            Simbolo sym = new Simbolo(this.tipo, this.id, val);
            sym.setEsConstante(this.esConstante);
            boolean creacion = tabla.setVariable(sym);
            if (!creacion) {
                return new Errores(TipoError.SEMANTICO, "Variable ya existente: " + sym.getId(), this.linea, this.columna);
            }
        } catch (ErrorTipo err) {
            //System.out.println(err);
            return new Errores(TipoError.SEMANTICO, "Tipos erroneos", this.linea, this.columna);
        }
        return null;
    }


    private LinkedList<Instruccion> datos(Tree arbol, TablaSimbolo tablaSimbolo) throws ErrorTipo {
        LinkedList<Instruccion> temp = new LinkedList<>();
        for (Instruccion val : valores) {
            var valorInter = val.interpretar(arbol, tablaSimbolo);
            if (valorInter instanceof Errores) {
                throw new ErrorTipo("TIPOS INCOMPATIBLES-> " + this.tipo.getTipo() + " != " + val.tipo.getTipo() + ".");
            }

            if (this.tipo.getTipo() != val.tipo.getTipo()) {
                throw new ErrorTipo("TIPOS INCOMPATIBLES-> " + this.tipo.getTipo() + " != " + val.tipo.getTipo() + ".");
            }
            temp.add(val);
        }
        return temp;
    }
}