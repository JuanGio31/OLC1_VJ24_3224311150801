package org.example;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.view.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            ventanaPrincipal.setLocationRelativeTo(null);
            ventanaPrincipal.setVisible(true);
        }
        );
    }
}
