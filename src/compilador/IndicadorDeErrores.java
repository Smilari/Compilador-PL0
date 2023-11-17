package compilador;

import static compilador.EntradaSalida.*;

public class IndicadorDeErrores { // Cada error se identifica con un numero entero
    public void mostrarError(int codError, Terminal terminal, String cadena){
        escribir("\t\t");
        switch (codError) {
            //0 -> Errores Lexicos/De Programa | 100 -> Errores de [bloque] | 200 -> Errores de [proposicion]
            // 300 -> Errores de [condicion] | 400 -> Errores de [factor] | 500 -> Errores Semanticos
            case 1 -> msjError("'[PROGRAMA]'", "'PUNTO'", terminal, cadena);
            case 2 -> msjError("'[PROGRAMA] -> (PUNTO)'", "'EOF'", terminal, cadena);
            case 3 -> escribirConSalto("Error en el analizador léxico (no se encuentra el fin de la cadena)");
            case 4 -> escribirConSalto("Error " + cadena + " no es un numero entero de 32 bits valido.");
            case 101 -> msjError("'[BLOQUE] -> (CONST)'", "'IDENTIFICADOR'", terminal, cadena);
            case 102 -> msjError("'[BLOQUE] -> (CONST) -> (IDENTIFICADOR)", "'IGUAL O ASIGNACION'", terminal, cadena);
            case 103 -> msjError("'[BLOQUE] -> (CONST) -> (IDENTIFICADOR) -> (IGUAL)'", "'NUMERO'", terminal, cadena);
            case 104 -> msjError("'[BLOQUE] -> (CONST) -> (IDENTIFICADOR) -> (IGUAL) -> (NUMERO) '", "'PUNTO_Y_COMA' o 'COMA'", terminal, cadena);
            case 105 -> msjError("'[BLOQUE] -> (VAR)'", "'IDENTIFICADOR'", terminal, cadena);
            case 106 -> msjError("'[BLOQUE] -> (VAR) -> (IDENT)'", "'PUNTO_Y_COMA' o 'COMA'", terminal, cadena);
            case 107 -> msjError("'[BLOQUE] -> (PROCEDURE)'", "'IDENTIFICADOR'", terminal, cadena);
            case 108 -> msjError("'[BLOQUE] -> (PROCEDURE) -> (IDENTIFICADOR)'", "'PUNTO_Y_COMA'", terminal, cadena);
            case 109 -> msjError("'[BLOQUE] -> (PROCEDURE) -> (IDENTIFICADOR) -> (PUNTO_Y_COMA) -> [BLOQUE]'", "'PUNTO_Y_COMA'", terminal, cadena);
            case 201 -> msjError("'[PROPOSICION] -> (IDENTIFICADOR)'", "'ASIGNACION'", terminal, cadena);
            case 202 -> msjError("'[PROPOSICION] -> (CALL)'", "'IDENTIFICADOR'", terminal, cadena);
            case 203 -> msjError("'[PROPOSICION] -> (BEGIN) -> [PROPOSICION]'", "'END' o 'PUNTO_Y_COMA'", terminal, cadena);
            case 204 -> msjError("'[PROPOSICION] -> (IF) -> [CONDICION]'", "'THEN'", terminal, cadena);
            case 205 -> msjError("'[PROPOSICION] -> (WHILE) -> [CONDICION]'", "'DO'", terminal, cadena);
            case 206 -> msjError("'[PROPOSICION] -> (READLN)'", "'ABRE_PARENTESIS'", terminal, cadena);
            case 207 -> msjError("'[PROPOSICION] -> (READLN) -> (ABRE_PARENTESIS)'", "'IDENTIFICADOR'", terminal, cadena);
            case 208 -> msjError("'[PROPOSICION] -> (READLN) -> (ABRE_PARENTESIS) -> (IDENTIFICADOR)'", "'CIERRA_PARENTESIS' o 'COMA'", terminal, cadena);
            case 209 -> msjError("'[PROPOSICION] -> (READLN) -> (ABRE_PARENTESIS) -> (IDENTIFICADOR) -> (COMA)'", "'IDENTIFICADOR'", terminal, cadena);
            case 210 -> msjError("'[PROPOSICION] -> (WRITE)'", "'ABRE_PARENTESIS'", terminal, cadena);
            case 211 -> msjError("'[PROPOSICION] -> (WRITE) -> (CADENA) o [EXPRESION]'", "'CIERRA_PARENTESIS' o 'COMA'", terminal, cadena);
            case 301 -> msjError("'[CONDICION] -> (EXPRESION)'", "'IGUAL' o 'DISTINTO' o 'MENOR' o 'MENOR_IGUAL' o 'MAYOR' o 'MAYOR_IGUAL'", terminal, cadena);
            case 302 -> msjError("'[CONDICION] -> (NOT)'", "ABRE_PARENTESIS", terminal, cadena);
            case 303 -> msjError("'[CONDICION] -> (NOT)'", "CIERRA_PARENTESIS", terminal, cadena);
            case 401 -> msjError("'[FACTOR]'", "'IDENTIFICADOR' o 'NUMERO' o 'ABRE_PARENTESIS", terminal, cadena);
            case 402 -> msjError("'[FACTOR] -> (ABRE_PARENTESIS) -> [EXPRESION]'", "'CIERRA_PARENTESIS'", terminal, cadena);
            case 501 -> escribirConSalto("El identificador '" + cadena + "', de tipo " + terminal + " ya se encuentra instanciado");
            case 502 -> escribirConSalto("El identificador '" + cadena + "' no se encuentra");
            case 503 -> escribirConSalto("El identificador '" + cadena + "' es de un tipo incompatible (se esperaba 'VAR' y se recibio '" + terminal + "')");
            case 504 -> escribirConSalto("El identificador '" + cadena + "' es de un tipo incompatible (se esperaba 'PROCEDURE' y se recibio '" + terminal + "')");
            case 505 -> escribirConSalto("El identificador '" + cadena + "' es de un tipo incompatible (se esperaba 'VAR' o 'CONST' y se recibio '" + terminal + "')");
            case 506 -> escribirConSalto("Se ha superado la cantidad permitida de IDENTIFICADORES");
        }
        if (codError != -1){
            escribirConSalto("Compilacion fallida");
        }
        System.exit(codError);
    }
    void msjError(String zonaError, String simboloEsperado, Terminal simboloRecibido, String cadenaS){
        escribirConSalto("Ocurrió un error en " + zonaError + " // Se esperaba el simbolo: '" + simboloEsperado + "', pero se recibió: '" + simboloRecibido + "' - \"" + cadenaS + "\"");
    }
}
