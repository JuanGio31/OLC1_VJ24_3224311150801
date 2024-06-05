package org.example.view;

import javax.swing.JTabbedPane;

/**
 *
 * @author giovanic
 */
public class GestionTab {

    private final JTabbedPane tabbedPane;

    public GestionTab(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public void addTab(String nombre, String contenido) {
        Tab tab = new Tab();
        tab.editarVista(contenido);
        tabbedPane.addTab(nombre, tab);
    }
}
