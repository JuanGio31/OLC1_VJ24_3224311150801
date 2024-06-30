package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.HashMap;
import java.util.LinkedList;

public class Metodo extends Instruccion {

    private String id;
    private LinkedList<Instruccion> instruccions;
    private LinkedList<HashMap> parametros;

    public Metodo(String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instruccions, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.id = id;
        this.instruccions = instruccions;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        for (var index : this.instruccions) {
            var result = index.interpretar(arbol, tabla);
            if (result instanceof Errores) {
                return result;
            }
            if (this.tipo.getTipo() == TipoDeDato.VOID) {
                if (result instanceof Retorno retorno) {
                    if (retorno.tipo.getTipo() == TipoDeDato.VOID && retorno.getValor() == null) {
                        break;
                    }
                    return new Errores(TipoError.SEMANTICO, "Tipos incompatibles (void)", this.linea, this.columna);
                }
            }
            if (result instanceof Retorno retorno) {
                var rs = retorno.interpretar(arbol, tabla);
                if (this.tipo.getTipo() != retorno.getValor().tipo.getTipo()) {
                    return new Errores(TipoError.SEMANTICO, "Tipos incompatibles", this.linea, this.columna);
                }
                return rs;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Instruccion> getInstruccions() {
        return instruccions;
    }

    public void setInstruccions(LinkedList<Instruccion> instruccions) {
        this.instruccions = instruccions;
    }

    public LinkedList<HashMap> getParametros() {
        return parametros;
    }

    public void setParametros(LinkedList<HashMap> parametros) {
        this.parametros = parametros;
    }
}
