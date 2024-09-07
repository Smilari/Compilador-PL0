package compilador;

import static compilador.EntradaSalida.*;

/**
 * La clase {@code IndicadorDeErrores} gestiona y muestra los mensajes de error durante la compilación de un programa.
 * <p>
 * Cada error está asociado con un código de error único que identifica el tipo de problema encontrado, y se utiliza
 * para guiar al usuario hacia la corrección del código fuente.
 *
 * <p>Los errores se clasifican en diferentes categorías:
 * <ul>
 *     <li>0: Errores léxicos o del programa</li>
 *     <li>100: Errores de bloque</li>
 *     <li>200: Errores de proposición</li>
 *     <li>300: Errores de condición</li>
 *     <li>400: Errores de factor</li>
 *     <li>500: Errores semánticos</li>
 * </ul>
 */
public class IndicadorDeErrores {

    /**
     * Muestra un mensaje de error basado en el código de error proporcionado, el terminal y la cadena de entrada.
     * <p>
     * Si el código de error no es -1, finaliza la ejecución del programa.
     *
     * @param codError El código del error que se produjo.
     * @param terminal El símbolo terminal que se encontró cuando ocurrió el error.
     * @param cadena   La cadena adicional que proporciona contexto sobre el error.
     */
    public void mostrarError(int codError, Terminal terminal, String cadena){
        escribir("\t\t");
        switch (codError) {
            // Errores Léxicos / De Programa
            case 1 -> msjError("'[PROGRAMA]'", "'PUNTO'", terminal, cadena);
            case 2 -> msjError("'[PROGRAMA] -> (PUNTO)'", "'EOF'", terminal, cadena);
            case 3 -> escribirConSalto("Error en el analizador léxico (no se encuentra el fin de la cadena)");
            case 4 -> escribirConSalto("Error " + cadena + " no es un numero entero de 32 bits valido.");

            // Errores de Bloque
            case 101 -> msjError("'[BLOQUE] -> (CONST)'", "'IDENTIFICADOR'", terminal, cadena);
            case 102 -> msjError("'[BLOQUE] -> (CONST) -> (IDENTIFICADOR)", "'IGUAL O ASIGNACION'", terminal, cadena);
            case 103 -> msjError("'[BLOQUE] -> (CONST) -> (IDENTIFICADOR) -> (IGUAL)'", "'NUMERO'", terminal, cadena);
            case 104 -> msjError("'[BLOQUE] -> (CONST) -> (IDENTIFICADOR) -> (IGUAL) -> (NUMERO) '", "'PUNTO_Y_COMA' o 'COMA'", terminal, cadena);
            case 105 -> msjError("'[BLOQUE] -> (VAR)'", "'IDENTIFICADOR'", terminal, cadena);
            case 106 -> msjError("'[BLOQUE] -> (VAR) -> (IDENT)'", "'PUNTO_Y_COMA' o 'COMA'", terminal, cadena);
            case 107 -> msjError("'[BLOQUE] -> (PROCEDURE)'", "'IDENTIFICADOR'", terminal, cadena);
            case 108 -> msjError("'[BLOQUE] -> (PROCEDURE) -> (IDENTIFICADOR)'", "'PUNTO_Y_COMA'", terminal, cadena);
            case 109 -> msjError("'[BLOQUE] -> (PROCEDURE) -> (IDENTIFICADOR) -> (PUNTO_Y_COMA) -> [BLOQUE]'", "'PUNTO_Y_COMA'", terminal, cadena);

            // Errores de Proposición
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
            case 212 -> msjError("'[PROPOSICION] -> (FOR)'", "'IDENTIFICADOR'", terminal, cadena);
            case 213 -> msjError("'[PROPOSICION] -> (FOR) -> (IDENTIFICADOR)'", "'ASIGNACION'", terminal, cadena);
            case 214 -> msjError("'[PROPOSICION] -> (FOR) -> (IDENTIFICADOR) -> [EXPRESION]'", "'TO'", terminal, cadena);
            case 215 -> msjError("'[PROPOSICION] -> (FOR) -> (IDENTIFICADOR) -> [EXPRESION] -> TO -> [EXPRESION]'", "'DO'", terminal, cadena);

            // Errores de Condición
            case 301 -> msjError("'[CONDICION] -> (EXPRESION)'", "'IGUAL' o 'DISTINTO' o 'MENOR' o 'MENOR_IGUAL' o 'MAYOR' o 'MAYOR_IGUAL'", terminal, cadena);
            case 302 -> msjError("'[CONDICION] -> (NOT)'", "ABRE_PARENTESIS", terminal, cadena);
            case 303 -> msjError("'[CONDICION] -> (NOT)'", "CIERRA_PARENTESIS", terminal, cadena);

            // Errores de Factor
            case 401 -> msjError("'[FACTOR]'", "'IDENTIFICADOR' o 'NUMERO' o 'ABRE_PARENTESIS", terminal, cadena);
            case 402 -> msjError("'[FACTOR] -> (ABRE_PARENTESIS) -> [EXPRESION]'", "'CIERRA_PARENTESIS'", terminal, cadena);

            // Errores Semánticos
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

    /**
     * Muestra un mensaje de error detallado que indica el área del error, el símbolo esperado y el símbolo recibido.
     *
     * @param zonaError        El área del error en el código fuente.
     * @param simboloEsperado  El símbolo que se esperaba encontrar.
     * @param simboloRecibido  El símbolo que se recibió en su lugar.
     * @param cadenaS          Información adicional sobre el error.
     */
    void msjError(String zonaError, String simboloEsperado, Terminal simboloRecibido, String cadenaS){
        escribirConSalto("Ocurrió un error en " + zonaError + " // Se esperaba el simbolo: '" + simboloEsperado + "', pero se recibió: '" + simboloRecibido + "' - \"" + cadenaS + "\"");
    }
}
