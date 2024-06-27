package org.example.backend.util;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileNotFoundException;

/**
 * Clase que gestiona los ficheros
 *
 * @author giovanic
 */
public class FilesControl {

    /**
     * Metodo para obtener un file
     *
     * @param filtro la extension predeterminada que se muestra en el
     *               filechooser
     * @return File
     */
    private File seleccionarArchivo(FileNameExtensionFilter filtro) throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser("src");
        fileChooser.setFileFilter(filtro);
        int respuesta = fileChooser.showOpenDialog(null);
        switch (respuesta) {
            case JFileChooser.APPROVE_OPTION -> {
                return fileChooser.getSelectedFile();
            }
            case JFileChooser.CANCEL_OPTION, JFileChooser.ERROR_OPTION -> {
                return null;
            }
        }
        return null;
    }

    /**
     * Metodo para obtener el contenido de un archivio
     *
     * @param path el path del archivo
     * @return string con el contenido del archivo
     */
    public String getContenido(String path) {
        String cont = "";
        try {
            cont = getArchivo(path);
        } catch (NullPointerException e) {
            System.out.println("error -> No se pudo leer el archivo");
        }
        return cont;
    }

    public File getFile() {
        File myObj = null;
        try {
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.jc", "JC");
            myObj = seleccionarArchivo(filtro);
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("error -> No se pudo leer el archivo");
        }
        return myObj;
    }

    //-------------------------Se obtiene el contenido del Archivo----------------//
    private String getArchivo(String ruta) {
        FileReader fr = null;
        BufferedReader br = null;
        //Cadena de texto donde se guardara el contenido del archivo
        StringBuilder contenido = new StringBuilder();
        try {
            //ruta puede ser de tipo String
            fr = new FileReader(ruta);
            br = new BufferedReader(fr);

            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }

        } catch (IOException ignored) {
        } //finally se utiliza para que si todo ocurre correctamente o si ocurre
        //algun error se cierre el archivo que anteriormente abrimos
        finally {
            try {
                br.close();
            } catch (IOException ignored) {
            }
        }
        return contenido.toString();
    }

    /**
     * Metodo para crear un archivo, en este caso html
     *
     * @return retornara el archivo creado
     */
    private File crearArchivo(String path) {
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            return myObj;
        } catch (IOException ignored) {
        }
        return null;
    }

    /**
     * Metodo para escribir en un archivo
     *
     * @param contenido cadena de caracteres
     * @param ruta      el path del archivo
     */
    public void sobreEscribir(String contenido, String ruta) {
        //antes -> escribirEnFile
        try {
            FileWriter myWriter = new FileWriter(ruta);
            myWriter.write(contenido);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Metodo que sirve para comprobar si el archivo a sido sobreescrito.
     *
     * @param file      archivo del cual se obtendra el contenido
     * @param contenido texto a evaluar con el contenido del archivo
     * @return true: si el archivo esta sobreescrito, de lo contrario false
     */
    public boolean estaSobreEscrito(File file, String contenido) {
        return !getContenido(file.getAbsolutePath()).trim().replaceAll("[\r\n]+$", "").equals(contenido);
    }

    public File crearArchivo() {
        String textoPredefinido = "nuevo_archivo.jc";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Archivo");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.jc", "JC");
        fileChooser.setFileFilter(filtro);
        File predeterminado = new File(textoPredefinido);
        fileChooser.setSelectedFile(predeterminado);
        //abrir el dialog para guardar
        int respuesta = fileChooser.showSaveDialog(null);
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getSelectedFile().exists()) {
                return fileChooser.getSelectedFile();
            } else {
                return crearArchivo(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
        return null;
    }
}
