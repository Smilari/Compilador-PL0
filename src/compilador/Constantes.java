package compilador;

/**
 * Clase que contiene la mayoría de las constantes utilizadas en el compilador,
 * incluyendo códigos de operación (opcodes) y configuraciones de memoria.
 */
public class Constantes {

    // Configuración de memoria
    public static final int CANT_MAX_IDENT = 1000;
    public static final int TOPE_MEMORIA_INICIAL = 1792;
    public static final int TOPE_MEMORIA_MAX = 8192;
    public static final int TAMANO_HEADER = 512;

    // Códigos de operación (opcodes)

    // Copia el segundo operando en el primero
    public static final int EDI_OPCODE = 0xBF; // MOV EDI, ...
    public static final int MOV_EAX_VAR_OPCODE = 0x8B; // MOV EAX, [EDI + ...]
    public static final int MOV_EAX_VAR_OPCODE2 = 0x87;
    public static final int MOV_VAR_EAX_OPCODE = 0x89; // MOV [EDI + ...], EAX
    public static final int MOV_VAR_EAX_OPCODE2 = 0x87;
    public static final int MOV_EAX_CONST_OPCODE = 0xB8; // MOV EAX, ...

    // Intercambia los valores de los operandos
    public static final int XCHG_OPCODE = 0x93; // XCHG EAX, EBX

    // Manda el valor del operando a la pila
    public static final int PUSH_EAX_OPCODE = 0x50; // PUSH EAX

    // Extrae el valor de la pila y lo coloca en el operando
    public static final int POP_EAX_OPCODE = 0x58; // POP EAX
    public static final int POP_EBX_OPCODE = 0x5B; // POP EBX

    // Suma ambos operandos y coloca el resultado en el primero
    public static final int ADD_OPCODE = 0x01; // ADD EAX, EBX
    public static final int ADD_OPCODE2 = 0xD8;

    // Le resta el segundo operando al primero y coloca el resultado en el primer operando
    public static final int SUB_OPCODE = 0x29; // SUB EAX, EBX
    public static final int SUB_OPCODE2 = 0xD8;

    // Coloca en EDX:EAX el producto de EAX por EBX
    public static final int IMUL_OPCODE = 0xF7; // IMUL EBX
    public static final int IMUL_OPCODE2 = 0xEB;

    // Divide EDX:EAX por el operando y coloca el cociente en EAX y el resto en EDX
    public static final int IDIV_OPCODE = 0xF7; // IDIV EBX
    public static final int IDIV_OPCODE2 = 0xFB;

    // Llena todos los bits de EDX con el valor del bit del signo de EAX
    public static final int CDQ_OPCODE = 0x99; // CDQ (Convert Double to Quad)

    // Cambia el signo de EAX
    public static final int NEG_OPCODE = 0xF7; // NEG EAX
    public static final int NEG_OPCODE2 = 0xD8;

    // Compara el primer operando con el segundo para que, según el resultado
    // de la comparación puedan hacerse saltos condicionales a continuación
    public static final int CMP_OPCODE = 0x39; // CMP EBX, EAX
    public static final int CMP_OPCODE2 = 0xC3;

    // Según el resultado de una comparación, salta a la dirección
    // ubicada 'ab' bytes antes o después de la dirección actual
    public static final int JE_OPCODE = 0x74; // JE ab (Jump if Equal)
    public static final int JNE_OPCODE = 0x75; // JNE ab (Jump if Not Equal)
    public static final int JG_OPCODE = 0x7F; // JG ab (Jump if Greater)
    public static final int JGE_OPCODE = 0x7D; // JGE ab (Jump if Greater or Equal)
    public static final int JL_OPCODE = 0x7C; // JL ab (Jump if Less)
    public static final int JLE_OPCODE = 0x7E; // JLE ab (Jump if Less or Equal)
    public static final int JP_OPCODE = 0x7A; // JP ab (Jump if Parity)
    public static final int JPO_OPCODE = 0x7B; // JO ab (Jump if Overflow)

    // Salta a la dirección ubicada 'abcdefgh' bytes antes o después
    // de la dirección actual
    public static final int JMP_OPCODE = 0xE9; // JMP dir gh ef cd ab

    // Invoca la subrutina ubicada 'abcdefgh' bytes antes o después
    // de la dirección actual
    public static final int CALL_OPCODE = 0xE8; // CALL dir gh ef cd ab

    // Retorna al punto desde donde se llamó una subrutina
    public static final int RET_OPCODE = 0xC3; // RET
}
