package org.example.backend.interprete.simbol;

import java.util.LinkedList;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.instruccion.Metodo;

/**
 * @author giovanic
 */
public class Tree {

    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private TablaSimbolo tablaGlobal;
    private LinkedList<Errores> errores;
    private LinkedList<Instruccion> funciones;

    public Tree(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new TablaSimbolo();
        this.errores = new LinkedList<>();
        this.funciones = new LinkedList<>();
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

    public LinkedList<Instruccion> getFunciones() {
        return funciones;
    }

    public void setFunciones(LinkedList<Instruccion> funciones) {
        this.funciones = funciones;
    }

    public LinkedList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.errores = errores;
    }

    public void setTablaGlobal(TablaSimbolo tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public void Print(String valor) {
        this.consola += valor + "\n";
    }

    public void addFun(Instruccion funcion) {
//        if(this.ge){
//
//        }
        this.funciones.add(funcion);
    }

    public Instruccion getFun(String id) {
        //obtener fun por id
        for (var index : this.funciones) {
            if (index instanceof Metodo) {
                if (((Metodo) index).getId().equalsIgnoreCase(id)) {
                    return index;
                }
            }
        }
        return null;
    }
}
