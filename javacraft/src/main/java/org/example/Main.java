package org.example;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tree;
import org.example.backend.interprete.analisis.*;
import org.example.view.VentanaPrincipal;

import java.io.StringReader;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class Main {

    @SuppressWarnings("ThrowablePrintedToSystemOut")
    public static void main(String[] args) {

        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
            ventanaPrincipal.setLocationRelativeTo(null);
            ventanaPrincipal.setVisible(true);
        }
        );

//        String ejemplo = "println( \"hola\" );";
//        try {
//            StringReader stringReader = new StringReader(ejemplo);
//            Parser parser = new Parser(new Scan(stringReader));
//            var resultado = parser.parse();
//            var ast = new Tree((LinkedList<Instruccion>) resultado.value);
//            var tabla = new TablaSimbolo();
//            tabla.setNombre("GLOBAL");
//            ast.setConsola("");
//            for (var a : ast.getInstrucciones()) {
//                var res = a.interpretar(ast, tabla);
//            }
//            System.out.println(ast.getConsola());
//        } catch (Exception ex) {
//            System.out.println("Algo salio mal");
//            //noinspection ThrowablePrintedToSystemOut
//            System.out.println(ex);
//
//        }
    }
}
