package compilador;

/*IMPORTS*/

import static compilador.EntradaSalida.*;

import static compilador.Terminal.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.HashMap;
import java.io.File;


public class AnalizadorLexico {
    /* DEFINICIONES */
    private Terminal terminal;
    private File archivo;
    private BufferedReader lector;
    private HashMap reservadas;
    private String cadena;
    private boolean usado;
    private char caracter;
    private int contLinea;
    private IndicadorDeErrores identifiError;


    /* CONSTRUCTOR */
    public AnalizadorLexico(IndicadorDeErrores ideError, String nameArch) {
        this.identifiError = ideError;
        this.cadena = "";
        this.contLinea = 0;
        this.reservadas = new HashMap<>();

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
        archivo = new File(nameArch);

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


    /* METODOS */
    public Boolean compararTerminal(Terminal terminal) {
        return this.terminal == terminal;
    }

    public String getCadena() {
        return cadena;
    }

    public Terminal getTerminal() {
        return terminal;
    }

    public Terminal escanear() {
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
                    cadena = "=";
                    caracter = lectorLee();
                    yield IGUAL;
                }
                case '\'' -> {
                    StringBuilder builder = new StringBuilder("'");
                    while ((caracter = lectorLee()) != '\'') {
                        if ((caracter == '\n') || (caracter == -1) || (caracter == '\r')) {
                            terminal = NULO;
                            break;
                        } else {
                            builder.append((char) caracter);
                        }
                    }
                    builder.append((char) caracter);
                    cadena = builder.toString();
                    caracter = lectorLee();
                    yield cadena.endsWith("'") ? CADENA_LITERAL : NULO;
                }
                default -> {
                    yield lectorIdentica();
                }
            };
        }

        //escribirConSalto(s + " " + cad);
        if (terminal == null) {
            terminal = NULO;
            usado = true;
            caracter = lectorLee();
        }

        return terminal;
    }

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

    private Terminal lectorIdentica() {
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
                identifiError.mostrarError(4, null, cadena);
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
            symbol = (Terminal) reservadas.get(cadena.toUpperCase());

            if (symbol == null) {
                symbol = IDENTIFICADOR;
            }
        }
        return symbol;
    }
}


/*DOCUMENTACION*/
/*
    La tarea del analizador léxico consiste en: 
 a. Saltear los separadores (blancos, tabulaciones, comentarios). 
 b. Reconocer los símbolos válidos e informar sobre los no válidos. 
 c. Llevar la cuenta de los renglones del programa.
 d. Copiar los caracteres de entrada a la salida, generando un listado 
 con los renglones numerados.

Se encarga de reconocer los símbolos del lenguaje contenidos en el código 
fuente del programa a compilar.
*/
