package org.example.backend.interprete.instruccion;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.error.TipoError;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.Tree;

import java.util.IllegalFormatCodePointException;
import java.util.LinkedList;
import java.util.Objects;

public class MetodoStart extends Instruccion {
    private String identificador;
    private LinkedList<Instruccion> parametros;

    public MetodoStart(String identificador, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(TipoDeDato.VOID), linea, columna);
        this.identificador = identificador;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var busqueda = arbol.getFun(identificador);
        if (Objects.isNull(busqueda)) {
            return new Errores(TipoError.SEMANTICO, "Funcion no existente" + identificador, this.linea, this.columna);
        }
        if (busqueda instanceof Metodo metodo) {
            var nuevaTabla = new TablaSimbolo(arbol.getTablaGlobal());
            nuevaTabla.setNombre("START_WITH");

            if (metodo.getParametros().size() != this.parametros.size()) {
                return new Errores(TipoError.SEMANTICO, "Los parametros no concuerdan.", this.linea, this.columna);
            }

            for (int i = 0; i < this.parametros.size(); i++) {
                var id = (String) metodo.getParametros().get(i).get("id");
                var valor = this.parametros.get(i);
                var tipoParametro = (Tipo) metodo.getParametros().get(i).get("tipo");

                var declaracionParametro = new DeclaracionVariable(false, id, valor, tipoParametro, this.linea, this.columna);
                var resultadoDeclaracion = declaracionParametro.interpretar(arbol, nuevaTabla);
                if (resultadoDeclaracion instanceof Errores) {
                    return resultadoDeclaracion;
                }
            }

            var restultFun = metodo.interpretar(arbol, nuevaTabla);
            if (restultFun instanceof Errores) {
                return restultFun;
            }
        }
        return null;
    }
}
