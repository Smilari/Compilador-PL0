package compilador;

/*IMPORTS*/

import static compilador.Terminal.*;
import static compilador.Constantes.*;
import static compilador.EntradaSalida.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AnalizadorSintactico {
    /* DEFINICIONES */
    private AnalizadorLexico aLexico;
    private IndicadorDeErrores indicaErrores;
    private AnalizadorSemantico aSemantico;
    private GeneradorDeCodigo gCodigo;
    private int contVariables;


    /* CONSTRUCTOR */
    public AnalizadorSintactico(IndicadorDeErrores indicaErrores, AnalizadorLexico aLexico, GeneradorDeCodigo gCodigo) {
        this.aLexico = aLexico;
        this.gCodigo = gCodigo;
        this.indicaErrores = indicaErrores;
        aSemantico = new AnalizadorSemantico(indicaErrores);
        contVariables = 0;
    }


    /* METODOS */
    public void programa() {
        /*while (aLexico.getTerminal() != EOF) {
            EntradaSalida.escribir("\n[" + aLexico.getCadena() + "]:" + aLexico.getTerminal() + "\n");
            aLexico.escanear();
        }*/
        aLexico.escanear();
        gCodigo.cargarByte(EDI_OPCODE);
        gCodigo.cargarEntero(0);
        bloque(0);

        verificarTerminal(PUNTO, 1);
        finalizarPrograma();
    }

    private void finalizarPrograma(){
        aLexico.escanear();
        gCodigo.fixUp(contVariables);
        gCodigo.volcar();

        verificarTerminal(EOF, 2);

        escribirConSalto("\nEl programa ha compilado correctamente.");
    }

    private void verificarTerminal(Terminal terminalEsperado, int codigoError) {
        if (!aLexico.compararTerminal(terminalEsperado)) {
            indicaErrores.mostrarError(codigoError, aLexico.getTerminal(), aLexico.getCadena());
        }
    }

    private void verificarTerminal(List<Terminal> terminalesEsperados, int codigoError) {
        if (!terminalesEsperados.contains(aLexico.getTerminal())) {
            indicaErrores.mostrarError(codigoError, aLexico.getTerminal(), aLexico.getCadena());
        }
    }

    public void bloque(int base) {
        int desplazamiento = 0;
        int resultadoBusqueda;
        String nombreDelIdent = "";
        gCodigo.cargarByte(JMP_OPCODE);
        gCodigo.cargarEntero(0x00);
        int inicioBloque = gCodigo.getTopeMemoria();

        if (aLexico.compararTerminal(CONST)) {
            do {
                aLexico.escanear();
                verificarTerminal(IDENTIFICADOR, 101);

                nombreDelIdent = aLexico.getCadena();
                resultadoBusqueda = aSemantico.buscarIdentificador(base + desplazamiento - 1, base, nombreDelIdent);

                if (resultadoBusqueda != -1) {
                    indicaErrores.mostrarError(501, CONST, aLexico.getCadena());
                }

                aLexico.escanear();
                verificarTerminal(List.of(IGUAL, ASIGNACION), 102);

                aLexico.escanear();
                boolean negativo;
                if (aLexico.compararTerminal(MENOS)) {
                    negativo = true;
                    aLexico.escanear();
                } else {
                    negativo = false;
                }

                verificarTerminal(NUMERO, 103);

                int numero = Integer.parseInt(aLexico.getCadena());
                numero = (negativo) ? -numero : numero;
                aSemantico.cargarIdentificador(nombreDelIdent, CONST, numero, base + desplazamiento);
                desplazamiento++;
                aLexico.escanear();
            } while (aLexico.compararTerminal(COMA));

            verificarTerminal(PUNTO_Y_COMA, 104);
            aLexico.escanear();
        }

        if (aLexico.compararTerminal(VAR)) {
            do {
                aLexico.escanear();
                verificarTerminal(IDENTIFICADOR, 105);

                nombreDelIdent = aLexico.getCadena();
                resultadoBusqueda = aSemantico.buscarIdentificador(base + desplazamiento - 1, base, nombreDelIdent);

                if (resultadoBusqueda != -1) {
                    indicaErrores.mostrarError(501, VAR, aLexico.getCadena());
                }

                aSemantico.cargarIdentificador(nombreDelIdent, VAR, contVariables, base + desplazamiento);
                contVariables = contVariables + 4;
                desplazamiento++;
                aLexico.escanear();
            } while (aLexico.compararTerminal(COMA));

            verificarTerminal(PUNTO_Y_COMA, 106);
            aLexico.escanear();
        }

        while (aLexico.compararTerminal(PROCEDURE)) {
            aLexico.escanear();

            verificarTerminal(IDENTIFICADOR, 107);
            nombreDelIdent = aLexico.getCadena();
            resultadoBusqueda = aSemantico.buscarIdentificador(base + desplazamiento - 1, base, nombreDelIdent);

            if (resultadoBusqueda != -1) {
                indicaErrores.mostrarError(501, PROCEDURE, aLexico.getCadena());
            }

            aSemantico.cargarIdentificador(nombreDelIdent, PROCEDURE, gCodigo.getTopeMemoria(), base + desplazamiento);
            desplazamiento++;
            aLexico.escanear();

            verificarTerminal(PUNTO_Y_COMA, 108);
            aLexico.escanear();

            bloque(base + desplazamiento);
            gCodigo.cargarByte(RET_OPCODE);

            verificarTerminal(PUNTO_Y_COMA, 109);
            aLexico.escanear();
        }

        int origen = inicioBloque;
        int destino = gCodigo.getTopeMemoria();
        int distancia = destino - origen;
        gCodigo.cargarEnteroEn(distancia, origen - 4);
        proposicion(base, desplazamiento);
    }

    public void proposicion(int base, int desplazamiento) {
        int resultadoBusqueda = 0;
        String nombreDelIdent = "";
        IdentificadorBean resultadoBean;
        Terminal terminalIngresado = aLexico.getTerminal();

        switch (terminalIngresado) {
            case IDENTIFICADOR:
                nombreDelIdent = aLexico.getCadena();
                resultadoBusqueda = aSemantico.buscarIdentificador(base + desplazamiento - 1, 0, nombreDelIdent);

                if (resultadoBusqueda == -1) {
                    indicaErrores.mostrarError(502, null, aLexico.getCadena());
                }

                resultadoBean = aSemantico.buscarInfo(resultadoBusqueda);
                if (resultadoBean.getTipo() != VAR) {
                    indicaErrores.mostrarError(503, resultadoBean.getTipo(), aLexico.getCadena());
                }
                aLexico.escanear();

                verificarTerminal(List.of(ASIGNACION, IGUAL), 201);
                aLexico.escanear();

                expresion(base, desplazamiento);
                gCodigo.cargarPOP();
                gCodigo.cargarByte(MOV_VAR_EAX_OPCODE);
                gCodigo.cargarByte(MOV_VAR_EAX_OPCODE2);
                gCodigo.cargarEntero(resultadoBean.getValor());
                break;

            case CALL:
                aLexico.escanear();
                verificarTerminal(IDENTIFICADOR, 202);

                nombreDelIdent = aLexico.getCadena();
                resultadoBusqueda = aSemantico.buscarIdentificador(base + desplazamiento - 1, 0, nombreDelIdent);

                if (resultadoBusqueda == -1) {
                    indicaErrores.mostrarError(502, null, aLexico.getCadena());
                }
                if (aSemantico.buscarInfo(resultadoBusqueda).getTipo() != PROCEDURE) {
                    indicaErrores.mostrarError(504, aSemantico.buscarInfo(resultadoBusqueda).getTipo(), aLexico.getCadena());
                }

                aLexico.escanear();
                int origen = gCodigo.getTopeMemoria() + 5;
                int destino = aSemantico.buscarInfo(resultadoBusqueda).getValor();
                int distancia = destino - origen;
                gCodigo.cargarByte(CALL_OPCODE);
                gCodigo.cargarEntero(distancia);
                break;

            case BEGIN:
                aLexico.escanear();
                proposicion(base, desplazamiento);
                while (aLexico.compararTerminal(PUNTO_Y_COMA)) {
                    aLexico.escanear();
                    proposicion(base, desplazamiento);
                }
                if(aLexico.compararTerminal(HALT)){
                    gCodigo.cargarByte(JMP_OPCODE);
                    gCodigo.cargarEntero(0x588 - (gCodigo.getTopeMemoria() + 4)); //Fin del programa
                    aLexico.escanear();
                }

                verificarTerminal(END, 203);
                aLexico.escanear();
                break;

            case IF:
                aLexico.escanear();
                condicion(base, desplazamiento);
                int origenSalto = gCodigo.getTopeMemoria();

                verificarTerminal(THEN, 204);
                aLexico.escanear();

                proposicion(base, desplazamiento);
                int destinoSalto = gCodigo.getTopeMemoria();
                int distanciaSalto = destinoSalto - origenSalto;
                gCodigo.cargarEnteroEn(distanciaSalto, origenSalto - 4);// Fix Up
                break;

            case WHILE:
                aLexico.escanear();
                int inicioCondicion = gCodigo.getTopeMemoria();
                condicion(base, desplazamiento);
                origenSalto = gCodigo.getTopeMemoria();

                verificarTerminal(DO, 205);
                aLexico.escanear();

                proposicion(base, desplazamiento);
                origen = gCodigo.getTopeMemoria() + 5;
                destino = inicioCondicion;
                distancia = destino - origen;
                gCodigo.cargarByte(JMP_OPCODE);
                gCodigo.cargarEntero(distancia);
                destinoSalto = gCodigo.getTopeMemoria();
                distanciaSalto = destinoSalto - origenSalto;
                gCodigo.cargarEnteroEn(distanciaSalto, origenSalto - 4);// Fix Up
                break;

            case READLN:
                aLexico.escanear();
                verificarTerminal(ABRE_PARENTESIS, 206);
                do {
                    aLexico.escanear();
                    verificarTerminal(IDENTIFICADOR, 207);

                    nombreDelIdent = aLexico.getCadena();
                    resultadoBusqueda = aSemantico.buscarIdentificador(base + desplazamiento - 1, base, nombreDelIdent);

                    if (resultadoBusqueda == -1 || aSemantico.buscarInfo(resultadoBusqueda).getTipo() != VAR) {
                        indicaErrores.mostrarError(502, null, aLexico.getCadena());
                    }
                    aLexico.escanear();

                    origen = gCodigo.getTopeMemoria() + 5;
                    destino = 0x0590; // Lee por consola un número entero y lo deja guardado en EAX.
                    gCodigo.cargarByte(CALL_OPCODE);
                    gCodigo.cargarEntero(destino - origen);
                    gCodigo.cargarByte(MOV_VAR_EAX_OPCODE);
                    gCodigo.cargarByte(0x87);
                    gCodigo.cargarEntero(aSemantico.buscarInfo(resultadoBusqueda).getValor());
                } while (aLexico.compararTerminal(COMA));

                verificarTerminal(CIERRA_PARENTESIS, 208);
                aLexico.escanear();
                break;

            case WRITELN, WRITE:
                aLexico.escanear();
                if (aLexico.compararTerminal(ABRE_PARENTESIS)) {
                    do {
                        aLexico.escanear();
                        if (aLexico.compararTerminal(CADENA_LITERAL)) {
                            int ubCad = gCodigo.getUbicacionCadena();
                            gCodigo.cargarByte(MOV_EAX_CONST_OPCODE);
                            gCodigo.cargarEntero(ubCad);
                            origen = gCodigo.getTopeMemoria() + 5;
                            destino = 0x3E0;
                            distancia = destino - origen;
                            gCodigo.cargarByte(CALL_OPCODE);
                            gCodigo.cargarEntero(distancia);

                            String cadena = aLexico.getCadena().substring(1, aLexico.getCadena().length() - 1);
                            gCodigo.cargarByte(JMP_OPCODE);
                            gCodigo.cargarEntero(cadena.length() + 1);
                            for (byte b : cadena.getBytes()) {
                                gCodigo.cargarByte(b);
                            }
                            gCodigo.cargarByte(0x00);
                            aLexico.escanear();
                        } else {
                            expresion(base, desplazamiento);
                            gCodigo.cargarPOP();
                            origen = gCodigo.getTopeMemoria() + 5;
                            destino = 0x420;
                            distancia = destino - origen;
                            gCodigo.cargarByte(CALL_OPCODE);
                            gCodigo.cargarEntero(distancia);
                        }
                    } while (aLexico.compararTerminal(COMA));

                    if (aLexico.compararTerminal(CIERRA_PARENTESIS)) {
                        aLexico.escanear();
                    } else {
                        indicaErrores.mostrarError(211, aLexico.getTerminal(), aLexico.getCadena());
                    }
                } else if (terminalIngresado == WRITE) {
                    indicaErrores.mostrarError(210, aLexico.getTerminal(), aLexico.getCadena());
                }

                if (terminalIngresado == WRITELN) {
                    origen = gCodigo.getTopeMemoria() + 5;
                    destino = 0x410;
                    distancia = destino - origen;
                    gCodigo.cargarByte(CALL_OPCODE);
                    gCodigo.cargarEntero(distancia);
                }

                break;
        }
    }

    public void condicion(int base, int desplazamiento) {

        if (aLexico.compararTerminal(NOT)) {
            aLexico.escanear();
            verificarTerminal(ABRE_PARENTESIS, 302);

            aLexico.escanear();
            condicion(base, desplazamiento);

            int posSaltoACorregir = gCodigo.getTopeMemoria() - 7;
            byte saltoACorregir = gCodigo.descargarByteVon(posSaltoACorregir);
            Map<Byte, Byte> correccionSalto = Map.of(
                    (byte) JE_OPCODE, (byte) JNE_OPCODE,
                    (byte) JNE_OPCODE, (byte) JE_OPCODE,
                    (byte) JG_OPCODE, (byte) JLE_OPCODE,
                    (byte) JGE_OPCODE, (byte) JL_OPCODE,
                    (byte) JL_OPCODE, (byte) JGE_OPCODE,
                    (byte) JLE_OPCODE, (byte) JG_OPCODE,
                    (byte) JPO_OPCODE, (byte) JP_OPCODE
            );
            gCodigo.cargarByteEn(correccionSalto.get(saltoACorregir), posSaltoACorregir);

            verificarTerminal(CIERRA_PARENTESIS, 303);
            aLexico.escanear();

        } else if (aLexico.compararTerminal(ODD)) {
            aLexico.escanear();
            expresion(base, desplazamiento);
            gCodigo.cargarPOP();
            gCodigo.cargarByte(0xA8); // TEST AL, 01
            gCodigo.cargarByte(0x01);
            gCodigo.cargarByte(JPO_OPCODE);
            gCodigo.cargarByte(0x05);
            gCodigo.cargarByte(JMP_OPCODE);
            gCodigo.cargarEntero(0x00);

        } else {
            expresion(base, desplazamiento);
            Terminal operador = aLexico.getTerminal();

            verificarTerminal(List.of(IGUAL, DISTINTO, MENOR, MENOR_IGUAL, MAYOR, MAYOR_IGUAL), 301);
            aLexico.escanear();
            expresion(base, desplazamiento);

            gCodigo.cargarPOP();
            gCodigo.cargarByte(POP_EBX_OPCODE);
            gCodigo.cargarByte(CMP_OPCODE);
            gCodigo.cargarByte(CMP_OPCODE2);
            Map<Terminal, Byte> operadoresCodigo = Map.of(
                    IGUAL, (byte) JE_OPCODE,
                    DISTINTO, (byte) JNE_OPCODE,
                    MENOR, (byte) JL_OPCODE,
                    MENOR_IGUAL, (byte) JLE_OPCODE,
                    MAYOR, (byte) JG_OPCODE,
                    MAYOR_IGUAL, (byte) JGE_OPCODE
            );
            gCodigo.cargarByte(operadoresCodigo.get(operador)); // Salta dado el operador
            gCodigo.cargarByte(0x05); // Distancia del salto anterior
            gCodigo.cargarByte(JMP_OPCODE);
            gCodigo.cargarEntero(0x00);
        }
    }

    public void expresion(int base, int desplazamiento) {
        Terminal simbolo = null;

        Terminal terminalActual = aLexico.getTerminal();
        if (terminalActual == MAS || terminalActual == MENOS) {
            simbolo = terminalActual;
            aLexico.escanear();
        }

        termino(base, desplazamiento);
        if (simbolo == MENOS) {
            gCodigo.cargarPOP();
            gCodigo.cargarByte(NEG_OPCODE);
            gCodigo.cargarByte(NEG_OPCODE2);
            gCodigo.cargarByte(PUSH_EAX_OPCODE);
        }
        while (aLexico.compararTerminal(MAS) || aLexico.compararTerminal(MENOS)) {
            simbolo = aLexico.getTerminal();
            aLexico.escanear();
            termino(base, desplazamiento);
            gCodigo.cargarPOP();
            gCodigo.cargarByte(POP_EBX_OPCODE);
            switch (simbolo) {
                case MAS:
                    gCodigo.cargarByte(ADD_OPCODE);
                    gCodigo.cargarByte(ADD_OPCODE2);
                    break;
                case MENOS:
                    gCodigo.cargarByte(XCHG_OPCODE);
                    gCodigo.cargarByte(SUB_OPCODE);
                    gCodigo.cargarByte(SUB_OPCODE2);
                    break;
            }
            gCodigo.cargarByte(PUSH_EAX_OPCODE);
        }
    }

    public void termino(int base, int desplazamiento) {
        factor(base, desplazamiento);
        while (aLexico.compararTerminal(POR) || aLexico.compararTerminal(DIVIDIDO)) {
            Terminal operador = aLexico.getTerminal();
            aLexico.escanear();
            factor(base, desplazamiento);
            gCodigo.cargarPOP();
            gCodigo.cargarByte(POP_EBX_OPCODE);

            if (operador == POR) {
                gCodigo.cargarByte(IMUL_OPCODE);
                gCodigo.cargarByte(IMUL_OPCODE2);
            } else {
                // Para DIVIDIDO (IDIV)
                gCodigo.cargarByte(XCHG_OPCODE);
                gCodigo.cargarByte(CDQ_OPCODE);
                gCodigo.cargarByte(IDIV_OPCODE);
                gCodigo.cargarByte(IDIV_OPCODE2);
            }

            gCodigo.cargarByte(PUSH_EAX_OPCODE);
        }
    }

    public void factor(int base, int desplazamiento) {
        int resultadoBusqueda;
        String nombreDelIndent = "";

        switch (aLexico.getTerminal()) {
            case IDENTIFICADOR:
                nombreDelIndent = aLexico.getCadena();
                resultadoBusqueda = aSemantico.buscarIdentificador(base + desplazamiento - 1, 0, nombreDelIndent);

                if (resultadoBusqueda == -1) {
                    indicaErrores.mostrarError(502, null, aLexico.getCadena());
                }

                IdentificadorBean resultadoBusBean = aSemantico.buscarInfo(resultadoBusqueda);
                switch (resultadoBusBean.getTipo()) {
                    case PROCEDURE:
                        indicaErrores.mostrarError(505, resultadoBusBean.getTipo(), aLexico.getCadena());
                        break;
                    case CONST:
                        gCodigo.cargarByte(MOV_EAX_CONST_OPCODE);
                        gCodigo.cargarEntero(resultadoBusBean.getValor()); // Numero
                        gCodigo.cargarByte(PUSH_EAX_OPCODE);
                        break;
                    case VAR:
                        gCodigo.cargarByte(MOV_EAX_VAR_OPCODE);
                        gCodigo.cargarByte(MOV_VAR_EAX_OPCODE2);
                        gCodigo.cargarEntero(resultadoBusBean.getValor()); // Numero
                        gCodigo.cargarByte(PUSH_EAX_OPCODE);
                }
                aLexico.escanear();
                break;

            case NUMERO:
                gCodigo.cargarByte(MOV_EAX_CONST_OPCODE);
                gCodigo.cargarEntero(Integer.parseInt(aLexico.getCadena())); // Numero
                gCodigo.cargarByte(PUSH_EAX_OPCODE);
                aLexico.escanear();
                break;

            case ABRE_PARENTESIS:
                aLexico.escanear();
                expresion(base, desplazamiento);

                verificarTerminal(CIERRA_PARENTESIS, 402);
                aLexico.escanear();
                break;

            default:
                indicaErrores.mostrarError(401, aLexico.getTerminal(), aLexico.getCadena());
                break;
        }
    }

}
/* DOCUMENTACION */
/*
Se encarga de revisar si los símbolos detectados durante el análisis léxico
aparecen en el orden correcto como para constituir un programa válido.

El proceso de determinar si una frase puede ser generada a partir de
un conjunto de producciones se denomina parsing.

---------------------------------REGLAS---------------------------------
R1. Reducir el sistema de grafos a la menor cantidad de grafos que sea posible,
realizando para ello las sustituciones que sean necesarias.

R2. Declarar para cada grafo un procedimiento que contenga las
sentencias resultantes de aplicarle al grafo las reglas R3 a R7.

R3. Una secuencia de elementos se traduce como una sentencia compuesta:
    begin
        T(S1); T(S2); ... T(Sn)
    end
(Donde T(Si) es la sentencia obtenida al traducir el grafo Si)

R4. Una opción entre elementos se traduce como una sentencia condicional:
    if SIM in L1 then T(S1) else
    if SIM in L2 then T(S2) else
    if SIM in Ln then T(Sn);
Donde SIM es el símbolo devuelto por el analizador léxico y Li es
el conjunto de símbolos iniciales de Si. Siempre que Li conste de
un único símbolo a, "SIM in Li" podrá expresarse como "SIM = a"

R5. Un bucle de la forma se traduce como la sentencia:
    while SIM in L do T(S)

R6. Una referencia a otro grafo A se traduce como una sentencia
de llamada al procedimiento A.

R7. Una referencia a un símbolo terminal x se traduce como la sentencia:
    if SIM = x then SCANNER(SIM) else ERROR
Donde ERROR es un procedimiento encargado del tratamiento de los errores.
-----------------------------------------------------------------------

El parser funciona haciendo una llamada al scanner (para tener un
símbolo leído de antemano) y una llamada al procedimiento correspondiente
al primero de los grafos. A partir de este procedimiento se irán
realizando llamadas a los demás, hasta que aparezca algún error o se
termine reconociendo satisfactoriamente el programa leído.
*/