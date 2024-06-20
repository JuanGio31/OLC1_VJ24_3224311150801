package org.example;

import java.util.LinkedList;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.analisis.Parser;
import org.example.backend.interprete.analisis.Scan;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tree;

import java.io.StringReader;

public class Main {

    public static void main(String[] args) {
        /*
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
                    VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                    ventanaPrincipal.setLocationRelativeTo(null);
                    ventanaPrincipal.setVisible(true);
                }
        );
    */

        //llamadaParser(pruebaPrint());
        //llamadaParser(pruebaIncremento());
        llamadaParser(match());
    }

    static String pruebaPrint() {
        return "println(\"hola mundo\");";
    }

    static String pruebaIncremento() {
        return """
                println("PRUEBA INCREMENTO");
                var a: int = 5;
                println(a==5);
                a++;
                println( a==6);
                a++;
                println( a==7);
                a--;
                println( a==6);
                a--;
                println( a==5);
                a--;
                println( a==4);""";
    }

    static void llamadaParser(String texto) {
        try {
            StringReader stringReader = new StringReader(texto);
            Scan scan = new Scan(stringReader);
            Parser parser = new Parser(scan);
            var resultado = parser.parse();
            var ast = new Tree((LinkedList<Instruccion>) resultado.value);
            var tabla = new TablaSimbolo();
            tabla.setNombre("GLOBAL");
            ast.setConsola("");
            for (var a : ast.getInstrucciones()) {
                if (a == null) {
                    continue;
                }
                var res = a.interpretar(ast, tabla);
            }
            System.out.println(ast.getConsola());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static String match() {
        String tmp = """
                var op: int = 1;
                match(op){
                    2 => {
                        println("caso 1");
                    }
                    4 => {
                        println("caso 4");
                    }
                    5 => {
                        println("caso 5");
                    }
                    _=> {
                        println("DEFAULT");
                    }
                }
                """;
        return tmp;
    }
}
