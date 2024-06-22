package org.example;

public class Prueba {
    public static void main(String[] args) {
        String cadena = "El factorial de ";
        int factorial = 7;
        System.out.println("\n------------------ While ----------------");
        while (factorial > 0) {
            int i = factorial;
            int fact = 1;
            while (i > 0) {
                fact = fact * i;
                i--;
            }
            System.out.println(cadena + factorial + " = " + fact);
            factorial--;
        }


//        int n = 7;
//        double i = (double) -3 * n / 2;
//        while (i <= n) {
//            String cadenaFigura = "";
//            double j = (double) -3 * n / 2;
//            while (j <= (double) (3 * n) / 2) {
//                double absolutoi = i;
//                double absolutoj = j;
//                if (i < 0) {
//                    absolutoi = -i;
//                }
//                if (j < 0) {
//                    absolutoj = -j;
//                }
//                System.out.println(absolutoi + " " + absolutoj);
//                if ((absolutoi + absolutoj) <= n) {
//                    cadenaFigura = cadenaFigura + "* ";
//                }
//                if ((absolutoi + absolutoj) > n) {
//                    cadenaFigura = cadenaFigura + ". ";
//                }
//                j++;
//            }
//            //System.out.println(cadenaFigura);
//            i++;
//        }

    }
}
