package compilador;

import java.util.Scanner;

public class EntradaSalida {
    
    /*DEFINICION*/
    private static Scanner input = new Scanner(System.in);

    /*CONSTRUCTOR*/
    private EntradaSalida() {
    }

    /*METODOS*/
    public static void escribirConSalto(Object x) {
        System.out.println(x);
    }

    public static void escribir(Object x) {
        System.out.print(x);
    }

    public static void linea(int largo) {
        for (int i = 0; i < largo; i++) {
            escribir("-");
        }
        escribirConSalto("");
    }

    public static String leer() {
        return input.nextLine();
    }

    public static String leer(String msj) {
        escribir(msj);
        return input.nextLine();
    }

    public static int leerEntero() {
        return Integer.parseInt(leer());
    }

    public static int leerEntero(String msj) {
        return Integer.parseInt(leer(msj));
    }

    public static double leerDouble() {
        return Double.parseDouble(leer());
    }

    public static double leerDouble(String msj) {
        return Double.parseDouble(leer(msj));
    }

    public static char leerCaracter() {
        return leer().charAt(0);
    }

    public static char leerCaracter(String msj) {
        return leer(msj).charAt(0);
    }

    public static boolean leerBoolean(String msj) {
        String r = leer(msj);
        boolean salida = true;
        boolean respuestaCorrecta = false;
        while (!respuestaCorrecta) {
            if (r.equals("1")) {
                salida = true;
                respuestaCorrecta = true;
            } else if (r.equals("2")) {
                salida = false;
                respuestaCorrecta = true;
            } else {
                EntradaSalida.escribir("\t\t***ERROR: La opcion ingresada no es valida.***");
                EntradaSalida.escribir("");
                r = EntradaSalida.leer(msj);
            }
        }
        return salida;
    }
}
