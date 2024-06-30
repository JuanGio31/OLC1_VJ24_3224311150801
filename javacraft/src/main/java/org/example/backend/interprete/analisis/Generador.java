package org.example.backend.interprete.analisis;

public class Generador {

    public static void main(String[] args) {
        generarCompilador();
    }

    public static void generarCompilador() {
        try{
            String ruta = "/home/giovanic/Documentos/LenguajesProgra/Java/JAVA SE/OLC1_VJ24_3224311150801/javacraft/src/main/java/org/example/backend/interprete/analisis/";
            /*
                ruta -> ruta del los archivos
                -d -> ruta donde se genera la salida
                ruta salida
            */ 

           String[] Flex = {ruta + "lexico.flex", "-d", ruta};
           jflex.Main.generate(Flex);

           /*
            -destdir indica la ruta donde se generara la salida
            ruta de salida
            -parser indican el nombre del archivo
            parser
            ruta del archivo cup
           */

          String Cup[] = { "-destdir", ruta, "-parser", "parser", ruta + "parser.cup" };

            java_cup.Main.main(Cup);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
