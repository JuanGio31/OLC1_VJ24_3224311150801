package org.example.backend.interprete.instruccion;

import org.apache.commons.text.StringEscapeUtils;
import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.simbol.TipoDeDato;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tipo;
import org.example.backend.interprete.simbol.Tree;

/**
 * @author giovanic
 */
public class Print extends Instruccion {

    private Instruccion expresion;

    public Print(Instruccion expresion, int linea, int col) {
        super(new Tipo(TipoDeDato.VOID), linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Tree arbol, TablaSimbolo tabla) {
        var resultado = this.expresion.interpretar(arbol, tabla);
        if (resultado instanceof Errores) {
            return resultado;
        }
        String res = StringEscapeUtils.unescapeJava(resultado.toString());
        arbol.Print(res);
        return null;
    }

}
