package org.example.backend.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
     * filechooser
     * @return File
     * @throws java.io.FileNotFoundException
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
        String contenido = "";
        try {
            //ruta puede ser de tipo String
            fr = new FileReader(ruta);
            br = new BufferedReader(fr);

            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while ((linea = br.readLine()) != null) {
                contenido += linea + "\n";
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
        return contenido;
    }

    /**
     * Metodo para crear un archivo, en este caso html
     *
     * @param name la ruta del archivo
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
     * @param ruta el path del archivo
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
     * @param file archivo del cual se obtendra el contenido
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

    /**
     * Metodo para eliminar un archivo.
     *
     * @param file_name ruta del archivo y nombre
     */
    public void eliminarArchivo(String file_name) {
        File file = new File(file_name);
        file.delete();
    }

    /**
     * Elimina los archivos con una determinada extensión de una carpeta
     *
     * @param path Carpeta de la cual eliminar los archivos
     * @param extension Extensión de los archivos a eliminar
     */
    public void eliminarPorExtension(String path, final String extension) {
        File[] archivos = new File(path).listFiles((File archivo) -> {
            if (archivo.isFile()) {
                return archivo.getName().endsWith('.' + extension);
            }
            return false;
        });
        for (File archivo : archivos) {
            archivo.delete();
        }
    }

    /**
     * Metodo para comprobar la existencia de un archivo
     *
     * @param path ruta del archivo
     * @return regresa un valor booleando en caso de que es archivo exista o no
     */
    public boolean existencia(String path) {
        File fr = new File(path);
        return fr.exists();
    }
}
