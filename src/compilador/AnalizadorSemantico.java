package compilador;

import static compilador.Constantes.*;

/**
 * Detecta errores como inconsistencias de tipos u operaciones con objetos no declarados.
 */
public class AnalizadorSemantico {

    /**
     * Arreglo que representa la tabla de identificadores utilizados en el programa.
     * Cada entrada contiene un objeto IdentificadorBean que guarda el nombre, tipo y valor del identificador.
     */
    private final IdentificadorBean[] identBean;
    private final IndicadorDeErrores indicaErrores;

    /**
     * Inicializa la tabla de identificadores (identBean) y el manejador de errores.
     *
     * @param indicaErrores Instancia de IndicadorDeErrores utilizada para gestionar errores semánticos.
     */
    public AnalizadorSemantico(IndicadorDeErrores indicaErrores) {
        identBean = new IdentificadorBean[Constantes.CANT_MAX_IDENT];
        this.indicaErrores = indicaErrores;
    }

    /**
     * Busca un identificador en la tabla de identificadores (identBean[]), comenzando desde una posición 'inicio'
     * y retrocediendo hasta la posición 'fin'.
     *
     * @param inicio      Posición inicial desde donde se comienza la búsqueda.
     * @param fin         Posición final hasta donde se realiza la búsqueda.
     * @param nombreIdent Nombre del identificador a buscar.
     * @return La posición del identificador si es encontrado; de lo contrario, retorna -1.
     */
    public int buscarIdentificador(int inicio, int fin, String nombreIdent) {
        int i = inicio;
        while (i >= fin) {
            // Compara el nombre del identificador con el nombre buscador (ignorando mayúsculas y minúsculas)
            // if(identBean[i].getNombre().equalsIgnoreCase(nombreIdent)) return i;

            // Compara el nombre del identificador con el nombre buscado.
            if (identBean[i].getNombre().equals(nombreIdent)) return i;
            i--;
        }
        return -1;
    }

    /**
     * Carga un identificador en la tabla de identificadores (identBean[]) en una posición específica.
     * Si la posición especificada excede el tamaño máximo de la tabla (CANT_MAX_IDENT), se muestra un error.
     *
     * @param nombreIdent Nombre del identificador a cargar.
     * @param tipo Tipo del identificador.
     * @param valor Valor del identificador.
     * @param pos Posición en la tabla de identificadores donde se cargará el identificador.
     */
    public void cargarIdentificador(String nombreIdent, Terminal tipo, int valor, int pos) {
        if (pos > CANT_MAX_IDENT - 1) {
            indicaErrores.mostrarError(506, null, null);
        } else {
            identBean[pos] = new IdentificadorBean(nombreIdent, tipo, valor);
        }
    }

    /**
     * Retorna el IdentificadorBean en la posición @ de la tabla de identificadores.
     *
     * @param pos Posición en la tabla de identificadores.
     * @return Objeto IdentificadorBean correspondiente a la posición dada.
     */
    public IdentificadorBean buscarInfo(int pos) {
        return identBean[pos];
    }
}
