package org.example.backend.interprete.simbol;

import java.util.HashMap;

/**
 * @author giovanic
 */
public class TablaSimbolo {

    private TablaSimbolo tablaAnterior;
    private HashMap<String, Object> tablaActual;
    private String nombre;

    public TablaSimbolo() {
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }

    public TablaSimbolo(TablaSimbolo tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
        this.tablaActual = new HashMap<>();
        this.nombre = "";
    }

    public TablaSimbolo getTablaAnterior() {
        return tablaAnterior;
    }

    public void setTablaAnterior(TablaSimbolo tablaAnterior) {
        this.tablaAnterior = tablaAnterior;
    }

    public HashMap<String, Object> getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(HashMap<String, Object> tablaActual) {
        this.tablaActual = tablaActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean setVariable(Simbolo simbolo) {
        Simbolo busqueda = (Simbolo) this.tablaActual.get(simbolo.getId().toLowerCase());
        if (busqueda == null) {
            this.tablaActual.put(simbolo.getId().toLowerCase(), simbolo);
            return true;
        }
        return false;
    }

    public Simbolo getVariable(String id) {
        Simbolo busqueda = (Simbolo) this.tablaActual.get(id.toLowerCase());
        if (busqueda != null) {
            return busqueda;
        }
        return null;
    }
}
