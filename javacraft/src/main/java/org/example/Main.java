package org.example;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.analisis.Parser;
import org.example.backend.interprete.analisis.Scan;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tree;
import org.example.view.VentanaPrincipal;

import java.io.StringReader;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

//        FlatMacLightLaf.setup();
//        java.awt.EventQueue.invokeLater(() -> {
//            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
//            ventanaPrincipal.setLocationRelativeTo(null);
//            ventanaPrincipal.setVisible(true);
//        });

//        llamadaParser(pruebaPrint());
        //llamadaParser(pruebaIncremento());
        //llamadaParser(match());
        // llamadaParser(swhile());
        //llamadaParser(sfor());
        //llamadaParser(breakContinue());
        llamadaParser(decArr());
    }

    private static String decArr() {
        return """
                const hola: int[] = [5, 4, 3, 8, 10  ];
                println(hola[4]);
                const ho: char[] = ['o','b','c'];
                println(ho[0]);""";
    }

    static String pruebaPrint() {
        return "println(-3*7/2);";
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
            StringReader stringReader = new StringReader(texto + "\n");
            Scan scan = new Scan(stringReader);
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

    static String match() {
        String tmp = """
                var op: int = 5;
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

    static String sfor() {
        return "for(var a: int =1; a<5 ; a = a+1){" + "println(a); a++;" + "}";
    }

    static String swhile() {
        String tmp = """
                println("\\n----------- While -----------");
                var factorial: int = 7;
                var cadena: string = "El factorial de ";
                while (factorial > 0) {
                    var i: int = factorial;
                    var fact: int = 1;
                    while (i > 0) {
                        fact = fact * i;
                        i--;
                    }
                    println((cadena + factorial) + " = " + fact);
                    factorial--;
                }
                """;
        return tmp;
    }

    static String breakContinue() {
        return """
                //break y continue
                println("\\n----------- Sentencias de Tranferencia -----------");
                var k: iNT = 0;
                while (k < 3) {
                    println("Entramos al ciclo1 con k = " + k);
                    var l: int = 0;
                    while (l < 3) {
                        println("Entramos al ciclo2 con l = " + l);
                        if (k == 1 && l == 1) {
                            println("Hacemos break al ciclo2");
                            break;
                        }
                        if (k == 2 && l == 1) {
                            println("Hacemos continue al ciclo2");
                            l++;
                            continue;
                        }
                        l++;
                    }
                    if (k == 2 && l == 2) {
                        println("Hacemos break al ciclo1");
                        break;
                    }
                    k++;
                }
                """;
    }
}
