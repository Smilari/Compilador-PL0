package compilador;

public class Constantes {
    public static int CANT_MAX_IDENT = 1000;
    public static int TOPE_MEMORIA_INICIAL = 1792;
    public static int TOPE_MEMORIA_MAX = 8192;
    public static int TAMAÑO_HEADER = 512;
    public static int EDI_OPCODE = 0xBF; // MOV EDI, ...
    public static int POP_EAX_OPCODE = 0x58;
    public static int POP_EBX_OPCODE = 0x5B;
    public static int MOV_VAR_EAX_OPCODE = 0x89; // MOV [EDI + ...], EAX
    public static int MOV_VAR_EAX_OPCODE2 = 0x87;
    public static int CALL_OPCODE = 0xE8; // CALL dir
    public static int JMP_OPCODE = 0xE9;
    public static int RET_OPCODE = 0xC3;
    public static int JPO_OPCODE = 0x7B;
    public static int JE_OPCODE = 0x74;
    public static int JNE_OPCODE = 0x75;
    public static int JP_OPCODE = 0x7A;
    public static int JL_OPCODE = 0x7C;
    public static int JLE_OPCODE = 0x7E;
    public static int JG_OPCODE = 0x7F;
    public static int JGE_OPCODE = 0x7D;
    public static int ADD_OPCODE = 0x01; // ADD EAX, EBX
    public static int ADD_OPCODE2 = 0xD8;
    public static int SUB_OPCODE = 0x29; // SUB EAX, EBX
    public static int SUB_OPCODE2 = 0xD8;
    public static int NEG_OPCODE = 0xF7; // NEG EAX
    public static int NEG_OPCODE2 = 0xD8;
    public static int XCHG_OPCODE = 0x93; // XCHG EAX, EBX
    public static int IMUL_OPCODE = 0xF7; // IMUL EBX
    public static int IMUL_OPCODE2 = 0xEB;
    public static int IDIV_OPCODE = 0xF7; // IDIV EBX
    public static int IDIV_OPCODE2 = 0xFB;
    public static int CDQ_OPCODE = 0x99;
    public static int CMP_OPCODE = 0x39; //CMP EBX, EAX
    public static int CMP_OPCODE2 = 0xC3;
    public static int PUSH_EAX_OPCODE = 0x50;
    public static int MOV_EAX_VAR_OPCODE = 0x8B; //MOV EAX, [EDI + ...]
    public static int MOV_EAX_CONST_OPCODE = 0xB8; // MOV EAX, ...
}
