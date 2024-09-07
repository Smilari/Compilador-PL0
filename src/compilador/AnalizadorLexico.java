package compilador;

import static compilador.EntradaSalida.*;
import static compilador.Terminal.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.io.File;

/**
 * La tarea principal del analizador lexico es leer el archivo fuente de entrada y reconocer los distintos símbolos y
 * token válidos del lenguaje PL0. También se encarga de manejar los errores léxicos, como caracteres no válidos,
 * y de llevar un seguimiento del número de líneas en el código fuente.
 * <p>
 * Las principales responsabilidades del analizador léxico incluyen:
 * <ul>
 *   <li>Ignorar separadores como espacios en blanco, tabulaciones y comentarios.</li>
 *   <li>Reconocer símbolos válidos e informar sobre los inválidos.</li>
 *   <li>Llevar la cuenta de las líneas en el programa fuente.</li>
 *   <li>Generar un listado numerado de las líneas de código a medida que se leen.</li>
 * </ul>
 */
public class AnalizadorLexico {

    private Terminal terminal;
    private BufferedReader lector;
    private final HashMap<String, Terminal> reservadas;
    private String cadena;
    private boolean usado;
    private char caracter;
    private int contLinea;
    private final IndicadorDeErrores indicaErrores;

    /**
     * Inicializa los componentes necesarios para la lectura y análisis del archivo fuente.
     *
     * @param ideError Instancia de la clase IndicadorDeErrores para manejar los errores durante el análisis.
     * @param nameArch Nombre del archivo fuente que se va a leer y analizar.
     */
    public AnalizadorLexico(IndicadorDeErrores ideError, String nameArch) {
        this.indicaErrores = ideError;
        this.cadena = "";
        this.contLinea = 0;
        this.reservadas = new HashMap<>();

        // Inicialización del mapa de palabras reservadas
        reservadas.put("CALL", CALL);
        reservadas.put("BEGIN", BEGIN);
        reservadas.put("IF", IF);
        reservadas.put("CONST", CONST);
        reservadas.put("VAR", VAR);
        reservadas.put("PROCEDURE", PROCEDURE);
        reservadas.put("END", END);
        reservadas.put("THEN", THEN);
        reservadas.put("WHILE", WHILE);
        reservadas.put("NOT", NOT);
        reservadas.put("DO", DO);
        reservadas.put("ODD", ODD);
        reservadas.put("READLN", READLN);
        reservadas.put("WRITELN", WRITELN);
        reservadas.put("WRITE", WRITE);
        reservadas.put("HALT", HALT);
        reservadas.put("ELSE", ELSE);
        reservadas.put("PRED", PRED);
        reservadas.put("SUCC", SUCC);
        File archivo = new File(nameArch);

        if (archivo.exists()) {
            escribirConSalto("\nSe abrio con exito el archivo: " + nameArch + ".\n\n");
        } else {
            escribirConSalto("ERROR: El archivo, " + nameArch + " no existe.\n");
        }
        try {
            lector = new BufferedReader(new FileReader(archivo));
        } catch (IOException ex) {
            escribirConSalto("ERROR: No se pudo crear el Buffered Reader para " + nameArch + ".");
        }
        usado = false;
        escribir("  " + contLinea + ": ");
    }

    /**
     * Compara la terminal actual con una terminal dada.
     *
     * @param terminal Terminal con la cual comparar.
     * @return {@code true} si la terminal actual es igual a la dada; de lo contrario, {@code false}.
     */
    public Boolean compararTerminal(Terminal terminal) {
        return this.terminal == terminal;
    }

    /**
     * Devuelve la cadena del último token identificado.
     *
     * @return La cadena que representa el token identificado.
     */
    public String getCadena() {
        return cadena;
    }

    /**
     * Devuelve la terminal actual identificada.
     *
     * @return Terminal actual.
     */
    public Terminal getTerminal() {
        return terminal;
    }

    /**
     * Método principal que lee caracteres del archivo fuente y
     * determina los tokens correspondientes.
     */
    public void escanear() {
        cadena = "";

        while (caracter == ' ' || caracter == '\t' || caracter == '\n' || caracter == '\r') {
            caracter = lectorLee();
        }

        if (!usado) {
            caracter = lectorLee();
            usado = false;
        }

        if (caracter == '\uffff') {
            terminal = EOF;
            cadena = "";
        } else {
            terminal = switch (caracter) {
                case ':' -> {
                    caracter = lectorLee();
                    if (caracter == '=') {
                        cadena = ":=";
                        caracter = lectorLee();
                        yield ASIGNACION;
                    } else {
                        cadena = ":";
                        usado = true;
                        yield NULO;
                    }
                }
                case '<' -> {
                    caracter = lectorLee();
                    yield switch (caracter) {
                        case '=' -> {
                            cadena = "<=";
                            caracter = lectorLee();
                            yield MENOR_IGUAL;
                        }
                        case '>' -> {
                            cadena = "<>";
                            caracter = lectorLee();
                            yield DISTINTO;
                        }
                        default -> {
                            cadena = "<";
                            usado = true;
                            yield MENOR;
                        }
                    };
                }
                case '>' -> {
                    caracter = lectorLee();
                    if (caracter == '=') {
                        cadena = ">=";
                        caracter = lectorLee();
                        yield MAYOR_IGUAL;
                    } else {
                        cadena = ">";
                        usado = true;
                        yield MAYOR;
                    }
                }
                case '+' -> {
                    cadena = "+";
                    caracter = lectorLee();
                    yield MAS;
                }
                case '-' -> {
                    cadena = "-";
                    caracter = lectorLee();
                    yield MENOS;
                }
                case '*' -> {
                    cadena = "*";
                    caracter = lectorLee();
                    yield POR;
                }
                case '/' -> {
                    cadena = "/";
                    caracter = lectorLee();
                    yield DIVIDIDO;
                }
                case '(' -> {
                    cadena = "(";
                    caracter = lectorLee();
                    yield ABRE_PARENTESIS;
                }
                case ')' -> {
                    cadena = ")";
                    caracter = lectorLee();
                    yield CIERRA_PARENTESIS;
                }
                case '.' -> {
                    cadena = ".";
                    caracter = lectorLee();
                    yield PUNTO;
                }
                case ',' -> {
                    cadena = ",";
                    caracter = lectorLee();
                    yield COMA;
                }
                case ';' -> {
                    cadena = ";";
                    caracter = lectorLee();
                    yield PUNTO_Y_COMA;
                }
                case '=' -> {
                    caracter = lectorLee();
                    if (caracter == '=') {
                        cadena = "==";
                        caracter = lectorLee();
                        yield DOBLE_IGUAL;
                    } else {
                        cadena = "=";
                        usado = true;
                        yield IGUAL;
                    }
                }
                case '\'' -> {
                    StringBuilder builder = new StringBuilder("'");
                    while ((caracter = lectorLee()) != '\'') {
                        if (caracter == '\n' || caracter == '\r') {
                            terminal = NULO;
                            break;
                        } else {
                            builder.append(caracter);
                        }
                    }
                    builder.append(caracter);
                    cadena = builder.toString();
                    caracter = lectorLee();
                    yield cadena.endsWith("'") ? CADENA_LITERAL : NULO;
                }
                default -> lectorIdentifica();
            };
        }

        //escribirConSalto(s + " " + cad);
        if (terminal == null) {
            terminal = NULO;
            usado = true;
            caracter = lectorLee();
        }

    }

    /**
     * Lee el siguiente carácter del archivo fuente.
     *
     * @return El siguiente carácter leído del archivo.
     */
    public char lectorLee() {
        char caracter = 0;
        try {
            caracter = (char) (lector.read());
            if (caracter != '\uffff') {
                escribir(caracter);
                if (caracter == '\n') {
                    contLinea++;
                    escribir((contLinea < 10 ? "  " : contLinea < 100 ? " " : "") + contLinea + ": ");
                }
            }
        } catch (IOException ex) {
            escribirConSalto(ex.getMessage());
        }
        return caracter;
    }

    /**
     * Método auxiliar que maneja el reconocimiento de identificadores y números en el código fuente.
     *
     * @return Terminal identificada.
     */
    private Terminal lectorIdentifica() {
        cadena = "";
        Terminal symbol = null;
        boolean valido = true;
        cadena = cadena + caracter;

        if (Character.isDigit(caracter)) {
            do {
                caracter = lectorLee();
                if (Character.isDigit(caracter)) {
                    cadena = cadena + caracter;
                } else {
                    valido = false;
                }
            } while (valido);
            try {
                Integer.parseInt(cadena);
                symbol = NUMERO;
                usado = true;
            } catch (Exception e) {
                indicaErrores.mostrarError(4, null, cadena);
            }

        } else if (Character.isLetter(caracter)) {
            do {
                caracter = lectorLee();
                if (Character.isLetterOrDigit(caracter)) {
                    cadena = cadena + caracter;
                } else {
                    valido = false;
                }
            } while (valido);
            usado = true;
            symbol = reservadas.get(cadena.toUpperCase());

            if (symbol == null) {
                symbol = IDENTIFICADOR;
            }
        }
        return symbol;
    }
}