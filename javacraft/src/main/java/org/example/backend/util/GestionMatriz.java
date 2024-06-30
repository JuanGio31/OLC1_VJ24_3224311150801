package org.example.backend.util;

import java.util.LinkedList;

public class GestionMatriz<T> {

    private LinkedList<LinkedList<T>> matriz;
    private int filas;
    private int columnas;

    public GestionMatriz() {
        filas = 10;
        columnas = 10;
    }

    public GestionMatriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
    }

    public GestionMatriz(LinkedList<LinkedList<T>> matriz) {
        this.matriz = matriz;
        this.filas = matriz.size();
        this.columnas = matriz.get(0).size();
    }

    public void add(T valor) {
        System.out.println("ssss");
    }

    public T obtenerValor(int fila, int columna) {
        return matriz.get(fila).get(columna);
    }

    public void modificar(T nuevoValor, int fila, int columna) {
        LinkedList<T> filaTemp = matriz.get(fila);
        filaTemp.set(columna, nuevoValor);
    }

    public LinkedList<LinkedList<T>> getMatriz() {
        return matriz;
    }

    public void setMatriz(LinkedList<LinkedList<T>> matriz) {
        this.matriz = matriz;
    }
}