package org.example;

import org.example.backend.interprete.abstracto.Instruccion;
import org.example.backend.interprete.analisis.Parser;
import org.example.backend.interprete.analisis.Scan;
import org.example.backend.interprete.error.Errores;
import org.example.backend.interprete.simbol.TablaSimbolo;
import org.example.backend.interprete.simbol.Tree;

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

        //llamadaParser(pruebaPrint());
        //llamadaParser(pruebaIncremento());
        //llamadaParser(match());
        // llamadaParser(swhile());
        //llamadaParser(sfor());
        //llamadaParser(breakContinue());
        //llamadaParser(pruebaVector1());
        //llamadaParser(decArr());
        llamadaParser(pruebaList());
    }

    private static String decArr() {
        return """
                var vector1 : string [] = ["Hola", "Mundo"];
                var vector2 : int [] = [2, 5, 3, 1, 4];
                const vector3 : double [] = [(double)1, 2.0, 3.5, 4.5, 5.2];
                const vector4 : char [] = [(char)97, '2', 'b', (char)10, '\n'];
                const vector5 : bool [] = [true, false, false,true, true];

                println("Hola " + vector1[1]);
                vector1[1] = "World";
                println("Hola " + vector1[1]);
                                
                for(var i: int = 0; i<4 ; i++){
                  for(var j: int = i+1; j<5; j++){
                    if(vector2[i] < vector2[j]){
                      var temp : int = vector2[i];
                      vector2[i] = vector2[j];
                      vector2[j] = temp;
                    }
                  }
                }

                println("Vector ordenado:");
                println(vector2[0]);
                println(vector2[1]);
                println(vector2[2]);
                println(vector2[3]);
                """;
    }

    private static String pruebaList() {
        return """
                var vector2 : int [] = [2, 5, 3, 1, 4];
                List<int> lista = new List();
                lista.append(1);
                lista.append(2);
                lista.append(3);
                lista.append(4);
                vector2[0] = lista[0];
                lista.remove(0);
                println(lista.find(3));
                println(lista.Find(vector2));
                println(lista[0]);
                println(lista[1]);
                println(lista[2]);
                //println(lista[3]);
                //println(lista[4]);
                """;
    }

    static String pruebaPrint() {
        return """
                    const arreglo2: int[] = [0, 0, 1, 2, 0, 0, 5, 1, 0, 0, 8, 0, 0];
                    var temporal: int = 0;
                    var suma: int = 0;
                    var ceros: int = 0;
                    var i: int = 0;
                    for (i = 0; i < length(arreglo2); i++) {
                        temporal = arreglo2[i];
                        if (temporal == 0) {
                            ceros = ceros + 1;
                            continue;
                        }
                        suma = suma + temporal;
                    }
                    println("La suma de los elementos del arreglo es: " + suma);
                    println("La cantidad de ceros en el arreglo es: " + ceros);
                """;
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
            System.out.println("\n\n\n");
            tabla.getTablaActual().forEach((key, value) -> System.out.println(key + " : " + value));
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
