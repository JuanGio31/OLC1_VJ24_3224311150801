package org.example.backend;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author giovanic
 */
public class GestionArchivos {

    //key-> direccion del archivo
    private Map<String, File> archivos;

    /**
     * Constructor vacio de GestionArchivos
     */
    public GestionArchivos() {
        this.archivos = new HashMap<>();
    }

    /**
     * Metodo que sirve para agregar un archivo, primero comprueba que e archivo
     * no exista en la lista
     *
     * @param file Archivo
     */
    public void addFile(File file) {
        if (archivos.containsKey(file.getAbsolutePath())) {
            archivos.put(file.getAbsolutePath(), file);
        }
    }

    /**
     * Metodo que sirve para eliminar un archivo de la lista, primero comprueba
     * si existe dentro de la lista
     *
     * @param file archivo
     */
    public void removeFile(File file) {
        if (archivos.containsKey(file.getAbsolutePath())) {
            archivos.remove(file.getAbsolutePath());
        }
    }

    public File search(File file) {
        if (archivos.containsKey(file.getAbsolutePath())) {
            return archivos.get(file.getAbsolutePath());
        }
        return null;
    }

    public Map<String, File> getArchivos() {
        return archivos;
    }

    public void setArchivos(Map<String, File> archivos) {
        this.archivos = archivos;
    }
}
