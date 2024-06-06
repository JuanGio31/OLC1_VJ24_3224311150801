package org.example.view;

import javax.swing.JTabbedPane;
import java.io.File;
import org.example.backend.GestionArchivos;

/**
 *
 * @author giovanic
 */
public class GestionTab {

    private final JTabbedPane tabbedPane;
    private final GestionArchivos gestionArchivos;

    public GestionTab(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        this.gestionArchivos = new GestionArchivos();
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
        gestionArchivos.removeFile(temp.getArchivo());
        tabbedPane.removeTabAt(indicePestania);
        gestionArchivos.mostrar();
    }
}
