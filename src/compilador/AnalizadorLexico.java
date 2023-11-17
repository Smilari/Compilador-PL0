package compilador;

/*IMPORTS*/

import static compilador.EntradaSalida.*;

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

        reservadas.put("CALL", Terminal.CALL);
        reservadas.put("BEGIN", Terminal.BEGIN);
        reservadas.put("IF", Terminal.IF);
        reservadas.put("CONST", Terminal.CONST);
        reservadas.put("VAR", Terminal.VAR);
        reservadas.put("PROCEDURE", Terminal.PROCEDURE);
        reservadas.put("END", Terminal.END);
        reservadas.put("THEN", Terminal.THEN);
        reservadas.put("WHILE", Terminal.WHILE);
        reservadas.put("NOT", Terminal.NOT);
        reservadas.put("DO", Terminal.DO);
        reservadas.put("ODD", Terminal.ODD);
        reservadas.put("READLN", Terminal.READLN);
        reservadas.put("WRITELN", Terminal.WRITELN);
        reservadas.put("WRITE", Terminal.WRITE);
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
            terminal = Terminal.EOF;
            cadena = "";
        } else {
            terminal = switch (caracter) {
                case ':' -> {
                    caracter = lectorLee();
                    if (caracter == '=') {
                        cadena = ":=";
                        caracter = lectorLee();
                        yield Terminal.ASIGNACION;
                    } else {
                        cadena = ":";
                        usado = true;
                        yield Terminal.NULO;
                    }
                }
                case '<' -> {
                    caracter = lectorLee();
                    yield switch (caracter) {
                        case '=' -> {
                            cadena = "<=";
                            caracter = lectorLee();
                            yield Terminal.MENOR_IGUAL;
                        }
                        case '>' -> {
                            cadena = "<>";
                            caracter = lectorLee();
                            yield Terminal.DISTINTO;
                        }
                        default -> {
                            cadena = "<";
                            usado = true;
                            yield Terminal.MENOR;
                        }
                    };
                }
                case '>' -> {
                    caracter = lectorLee();
                    if (caracter == '=') {
                        cadena = ">=";
                        caracter = lectorLee();
                        yield Terminal.MAYOR_IGUAL;
                    } else {
                        cadena = ">";
                        usado = true;
                        yield Terminal.MAYOR;
                    }
                }
                case '+' -> {
                    cadena = "+";
                    caracter = lectorLee();
                    yield Terminal.MAS;
                }
                case '-' -> {
                    cadena = "-";
                    caracter = lectorLee();
                    yield Terminal.MENOS;
                }
                case '*' -> {
                    cadena = "*";
                    caracter = lectorLee();
                    yield Terminal.POR;
                }
                case '/' -> {
                    cadena = "/";
                    caracter = lectorLee();
                    yield Terminal.DIVIDIDO;
                }
                case '(' -> {
                    cadena = "(";
                    caracter = lectorLee();
                    yield Terminal.ABRE_PARENTESIS;
                }
                case ')' -> {
                    cadena = ")";
                    caracter = lectorLee();
                    yield Terminal.CIERRA_PARENTESIS;
                }
                case '.' -> {
                    cadena = ".";
                    caracter = lectorLee();
                    yield Terminal.PUNTO;
                }
                case ',' -> {
                    cadena = ",";
                    caracter = lectorLee();
                    yield Terminal.COMA;
                }
                case ';' -> {
                    cadena = ";";
                    caracter = lectorLee();
                    yield Terminal.PUNTO_Y_COMA;
                }
                case '=' -> {
                    cadena = "=";
                    caracter = lectorLee();
                    yield Terminal.IGUAL;
                }
                case '\'' -> {
                    StringBuilder builder = new StringBuilder("'");
                    while ((caracter = lectorLee()) != '\'') {
                        if ((caracter == '\n') || (caracter == -1) || (caracter == '\r')) {
                            terminal = Terminal.NULO;
                            break;
                        } else {
                            builder.append((char) caracter);
                        }
                    }
                    builder.append((char) caracter);
                    cadena = builder.toString();
                    caracter = lectorLee();
                    yield cadena.endsWith("'") ? Terminal.CADENA_LITERAL : Terminal.NULO;
                }
                default -> {
                    yield lectorIdentica();
                }
            };
        }

        //escribirConSalto(s + " " + cad);
        if (terminal == null) {
            terminal = Terminal.NULO;
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
                symbol = Terminal.NUMERO;
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
                symbol = Terminal.IDENTIFICADOR;
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
