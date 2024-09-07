package compilador;

/**
 * Representa los diferentes símbolos terminales que pueden aparecer en el lenguaje PL/0.
 * <p>
 * Esta enumeración define una lista de palabras clave, operadores, y símbolos que son reconocidos
 * por el compilador durante el proceso de análisis léxico y sintáctico.
 */

public enum Terminal {
        HALT,
        ELSE,
        DOBLE_IGUAL,
        PRED,
        SUCC,
        IF,
        CALL,
        CADENA_LITERAL,
        NUMERO,
        ASIGNACION,
        NULO,
        IDENTIFICADOR,
        EOF,
        MAS,
        CONST,
        VAR,
        PROCEDURE,
        BEGIN,
        END,
        THEN,
        WHILE,
        NOT,
        DO,
        ODD,
        MENOS,
        POR,
        DIVIDIDO,
        IGUAL,
        COMA,
        PUNTO_Y_COMA,
        MAYOR,
        MENOR,
        MENOR_IGUAL,
        MAYOR_IGUAL,
        DISTINTO,
        PUNTO,
        READLN,
        WRITELN,
        WRITE,
        ABRE_PARENTESIS,
        CIERRA_PARENTESIS
}
