package org.example.view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("JavaCraft");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1200, 750);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        init();
    }

    private void init() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(0, 0, this.getWidth(), this.getHeight());
        panel.setVisible(true);


        add(panel);
    }
}
