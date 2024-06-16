package org.example;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.analisis.Parser;
import org.example.backend.interprete.analisis.Scan;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tree;
import org.example.backend.util.FilesControl;

import java.io.StringReader;
import java.util.LinkedList;

public class Main {
//    public static void main(String[] args) {
//        System.out.println("hola");
//    }

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
            Scan scan = new Scan(rd);
            Parser parser = new Parser(scan);
            var resultado = parser.parse();
            var ast = new Tree((LinkedList<Instruccion>) resultado.value);
            var tabla = new TablaSimbolo();
            tabla.setNombre("GLOBAL");
            ast.setConsola("");
            LinkedList<Errores> lista = new LinkedList<>();
            lista.addAll(scan.listaErrores);
            lista.addAll(parser.listaErrores);
            for (var a : ast.getInstrucciones()) {
                if (a == null) {
                    continue;
                }
                var res = a.interpretar(ast, tabla);
                if (res instanceof Errores) {
                    lista.add((Errores) res);
                }
            }
            System.out.println(ast.getConsola());

            for (var i : lista) {
                System.out.println(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
