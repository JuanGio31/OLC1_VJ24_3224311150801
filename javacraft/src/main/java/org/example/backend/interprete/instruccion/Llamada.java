package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.LinkedList;

public class Llamada extends Instruccion {
    private String id;
    private LinkedList<Instruccion> parametros;

    public Llamada(String id, LinkedList<Instruccion> parametros, int linea, int col) {
        super(new Tipo(TipoDeDato.VOID), linea, col);
        this.id = id;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var busqueda = arbol.getFun(this.id);
        if (busqueda == null) {
            return new Errores(TipoError.SEMANTICO, "Funcion no existente",
                    this.linea, this.columna);
        }

        if (busqueda instanceof Metodo metodo) {
            var newTabla = new TablaSimbolo(arbol.getTablaGlobal());
            newTabla.setNombre("LLAMADA METODO " + this.id);
            if (metodo.getParametros().size() != this.parametros.size()) {
                return new Errores(TipoError.SEMANTICO, "Parametros Erroneos",
                        this.linea, this.columna);
            }


            for (int i = 0; i < this.parametros.size(); i++) {
                var identificador = (String) metodo.getParametros().get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo) metodo.getParametros().get(i).get("tipo");
                var declaracionParametro = new DeclaracionVariable(false, identificador, tipo2, this.linea, this.columna);

                var resultado = declaracionParametro.interpretar(arbol, newTabla);
                if (resultado instanceof Errores) {
                    return resultado;
                }

                var valorInterpretado = valor.interpretar(arbol, tabla);
                if (valorInterpretado instanceof Errores) {
                    return valorInterpretado;
                }

                var variable = newTabla.getVariable(identificador);
                if (variable == null) {
                    return new Errores(TipoError.SEMANTICO, "Error declaracion parametros",
                            this.linea, this.columna);
                }
                if (variable.getTipoDato().getTipo() != valor.tipo.getTipo()) {
                    return new Errores(TipoError.SEMANTICO, "Error en tipo de parametro",
                            this.linea, this.columna);
                }
                variable.setValue(valorInterpretado);
            }

            var resultadoFuncion = metodo.interpretar(arbol, newTabla);
            if (resultadoFuncion instanceof Errores) {
                return resultadoFuncion;
            }
            if (resultadoFuncion != null) {
                this.tipo.setTipo(metodo.tipo.getTipo());
                return resultadoFuncion;
            }
        }
        return null;
    }
}
