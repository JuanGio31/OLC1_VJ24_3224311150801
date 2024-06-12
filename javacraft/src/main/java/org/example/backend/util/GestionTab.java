package org.example.backend.util;

import javax.swing.*;
import java.io.File;

import org.example.view.Tab;

/**
 * @author giovanic
 */
public class GestionTab {

    private final JTabbedPane tabbedPane;
    private final GestionArchivos gestionArchivos;
    private final FilesControl control;

    public GestionTab(JTabbedPane tabbedPane, FilesControl control) {
        this.tabbedPane = tabbedPane;
        this.gestionArchivos = new GestionArchivos();
        this.control = control;
    }

    public void addTab(File archivo, String contenido) {
        if (gestionArchivos.addFile(archivo)) {
            Tab tab = new Tab(archivo);
            tab.editarVista(contenido);
            tabbedPane.addTab(archivo.getName(), tab);
            gestionArchivos.mostrar();
        }
    }

    public void deleteTab(int indicePestania) {
        Tab temp = (Tab) tabbedPane.getComponentAt(indicePestania);
        sobreEscribir(indicePestania);
        gestionArchivos.removeFile(temp.getArchivo());
        tabbedPane.removeTabAt(indicePestania);
        gestionArchivos.mostrar();
    }

    public void sobreEscribir(int indicePestania) {
        Tab temp = (Tab) tabbedPane.getComponentAt(indicePestania);
        if (control.estaSobreEscrito(temp.getArchivo(), temp.getVistaContenido())) {
            int option = JOptionPane.showInternalConfirmDialog(null, "Desea guardar los cambios?");
            if (option == 0) {
                control.sobreEscribir(temp.getVistaContenido(), temp.getArchivo().getAbsolutePath());
            }
        }
    }
}