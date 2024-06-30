package org.example;

public class Prueba {
    public static void main(String[] args) {
        int m = 3;
        int n = 4;
        int result = ackermann(m, n);
        System.out.println("Ackermann(" + m + ", " + n + ") = " + result);

    }

    public static int ackermann(int m, int n) {
        if (m == 0) {
            return n + 1;
        } else if (m > 0 && n == 0) {
            return ackermann(m - 1, 1);
        } else {
            return ackermann(m - 1, ackermann(m, n - 1));
        }
    }
}
