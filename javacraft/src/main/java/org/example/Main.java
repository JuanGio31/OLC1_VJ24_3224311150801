package org.example;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.analisis.Parser;
import org.example.backend.interprete.analisis.Scan;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tree;
import org.example.backend.util.FilesControl;

import java.io.StringReader;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
//        FlatMacLightLaf.setup();
//        java.awt.EventQueue.invokeLater(() -> {
//                    VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
//                    ventanaPrincipal.setLocationRelativeTo(null);
//                    ventanaPrincipal.setVisible(true);
//                }
//        );
//    }


        String ruta = "/home/giovanic/practicagit/CompiCapi/pruebas.jc";
        try {
            FilesControl control = new FilesControl();
            String cons = control.getContenido(ruta);
            StringReader rd = new StringReader(cons + "\n");
            Parser parser = new Parser(new Scan(rd));
            var resultado = parser.parse();
            var ast = new Tree((LinkedList<Instruccion>) resultado.value);
            var tabla = new TablaSimbolo();
            tabla.setNombre("GLOBAL");
            ast.setConsola("");
            for (var a : ast.getInstrucciones()) {
                var res = a.interpretar(ast, tabla);
            }
            System.out.println(ast.getConsola());
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //mostrar();
    }

    static void mostrar() {
        int a = 8, b = 3;
        int c = b / a;
        int d = a / b;
        System.out.println(b / a + " " + d);
    }
}
