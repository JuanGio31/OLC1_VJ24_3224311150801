package org.example.backend.util;

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
     * @return si se agrega el archivo devuelve true, de lo contrario false
     */
    public boolean addFile(File file) {
        if (!archivos.containsKey(file.getAbsolutePath())) {
            archivos.put(file.getAbsolutePath(), file);
            return true;
        }
        return false;
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

    public void mostrar() {
        for (Map.Entry<String, File> entry : archivos.entrySet()) {
            String key = entry.getKey();
            File value = entry.getValue();
            System.out.println(" --> " + value.getName());
        }
        System.out.println("\n\n");
    }

    public Map<String, File> getArchivos() {
        return archivos;
    }

    public void setArchivos(Map<String, File> archivos) {
        this.archivos = archivos;
    }
}
