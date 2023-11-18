package compilador;

import static compilador.Constantes.*;

public class AnalizadorSemantico {
    /* DEFINICIONES */
    private IdentificadorBean identBean[];
    private IndicadorDeErrores auxError;
    private IndicadorDeErrores indicaErrores;


    /* CONSTRUCTOR */
    public AnalizadorSemantico(IndicadorDeErrores auxError) {
        identBean = new IdentificadorBean[Constantes.CANT_MAX_IDENT];
        this.auxError = auxError;
        indicaErrores = new IndicadorDeErrores();
    }


    /* METODOS */
    public int buscarIdentificador(int inicio, int fin, String nombreIdent) {
        int i = inicio;
        while (i >= fin) {
//            if(identBean[i].getNombre().equalsIgnoreCase(nombreIdent)) {return i;}
            if (identBean[i].getNombre().equals(nombreIdent)) {
                return i;
            }

            i--;
        }
        return -1;
    }

    public void cargarIdentificador(String nombreIdent, Terminal tipo, int valor, int pos) {
        if (pos > CANT_MAX_IDENT - 1) {
            indicaErrores.mostrarError(506, null, null);
        } else {
            identBean[pos] = new IdentificadorBean(nombreIdent, tipo, valor);
        }

    }

    public IdentificadorBean buscarInfo(int pos) {
        return identBean[pos];
    }
}
/* DOCUMENTACION */
/*
Se encarga de detectar errores como inconsistencia 
de tipos u operaciones con objetos no declarados. 

El análisis sintáctico no garantiza que un programa esté libre de 
errores. Inconsistencias de tipo.

Para el análisis semántico, los identificadores se buscarán en toda 
la tabla, comenzando en la posición BASE+DESPLAZAMIENTO-1 y retrocediendo 
hasta la posición 0. 
En caso de no encontrarse el identificador, deberá darse aviso de la falta de 
declaración. 

Una vez hallado el identificador en la tabla, con el campo TIPO podrá 
verificarse si el identificador es semánticamente correcto en la posición 
del programa donde fue encontrado.
*/