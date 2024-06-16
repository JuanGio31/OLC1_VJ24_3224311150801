package org.example.backend.interprete.simbol;

import java.util.LinkedList;
import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;

/**
 *
 * @author giovanic
 */
public class Tree {

    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private TablaSimbolo tablaGlobal;
    private LinkedList<Errores> errores;

    public Tree(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new TablaSimbolo();
        this.errores = new LinkedList<>();
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public TablaSimbolo getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(TablaSimbolo tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public void Print(String valor) {
        this.consola += valor + "\n";
    }

}
