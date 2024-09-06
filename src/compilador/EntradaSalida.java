package compilador;

import java.util.Scanner;

/**
 * Proporciona métodos estáticos para facilitar la
 * entrada y salida de datos en la consola. Incluye métodos para leer y
 * escribir datos de diferentes tipos como String, int, double, char, y boolean.
 */
public class EntradaSalida {
    
    private static final Scanner input = new Scanner(System.in);

    /**
     * Constructor privado para evitar la instanciación de la clase,
     * ya que todos los métodos son estáticos.
     */
    private EntradaSalida() {
    }

    /**
     * Escribe un objeto en la salida estándar (consola) seguido de un salto de línea.
     *
     * @param x El objeto a escribir en la consola.
     */
    public static void escribirConSalto(Object x) {
        System.out.println(x);
    }

    /**
     * Escribe un objeto en la salida estándar (consola) sin salto de línea.
     *
     * @param x El objeto a escribir en la consola.
     */
    public static void escribir(Object x) {
        System.out.print(x);
    }

    /**
     * Escribe una línea de guiones ("-") en la salida estándar (consola).
     *
     * @param largo La cantidad de guiones a escribir.
     */
    public static void linea(int largo) {
        for (int i = 0; i < largo; i++) {
            escribir("-");
        }
        escribirConSalto("");
    }

    /**
     * Lee una línea completa desde la entrada estándar (consola).
     *
     * @return La cadena leída desde la consola.
     */
    public static String leer() {
        return input.nextLine();
    }

    /**
     * Escribe un mensaje en la consola y luego lee una línea completa.
     *
     * @param msj El mensaje a mostrar antes de la entrada.
     * @return La cadena leída desde la consola.
     */
    public static String leer(String msj) {
        escribir(msj);
        return input.nextLine();
    }

    /**
     * Lee un número entero desde la entrada estándar (consola).
     *
     * @return El número entero leído.
     * @throws NumberFormatException Si la entrada no es un número entero válido.
     */
    public static int leerEntero() {
        return Integer.parseInt(leer());
    }

    /**
     * Escribe un mensaje en la consola y lee un número entero desde la entrada.
     *
     * @param msj El mensaje a mostrar antes de la entrada.
     * @return El número entero leído.
     * @throws NumberFormatException Si la entrada no es un número entero válido.
     */
    public static int leerEntero(String msj) {
        return Integer.parseInt(leer(msj));
    }

    /**
     * Lee un número de tipo double desde la entrada estándar (consola).
     *
     * @return El número double leído.
     * @throws NumberFormatException Si la entrada no es un número double válido.
     */
    public static double leerDouble() {
        return Double.parseDouble(leer());
    }

    /**
     * Escribe un mensaje en la consola y lee un número de tipo double desde la entrada.
     *
     * @param msj El mensaje a mostrar antes de la entrada.
     * @return El número double leído.
     * @throws NumberFormatException Si la entrada no es un número double válido.
     */
    public static double leerDouble(String msj) {
        return Double.parseDouble(leer(msj));
    }

    /**
     * Lee un carácter desde la entrada estándar (consola).
     *
     * @return El primer carácter de la línea leída.
     */
    public static char leerCaracter() {
        return leer().charAt(0);
    }

    /**
     * Escribe un mensaje en la consola y lee un carácter desde la entrada.
     *
     * @param msj El mensaje a mostrar antes de la entrada.
     * @return El primer carácter de la línea leída.
     */
    public static char leerCaracter(String msj) {
        return leer(msj).charAt(0);
    }

    /**
     * Escribe un mensaje en la consola y lee un valor booleano desde la entrada.
     * Este método espera un "1" para true o un "2" para false, y repetirá el
     * proceso hasta que se ingrese un valor válido.
     *
     * @param msj El mensaje a mostrar antes de la entrada.
     * @return El valor booleano leído (true si se ingresa "1", false si se ingresa "2").
     */
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
