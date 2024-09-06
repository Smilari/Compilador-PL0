package compilador;

/**
 * Representa un identificador dentro del contexto de un compilador, incluyendo su nombre,
 * tipo y valor asociado.
 * <p>
 * Esta clase es utilizada para almacenar información sobre
 * identificadores que son procesados durante la compilación de código fuente.
 */
public class IdentificadorBean {

    private String nombre;
    private Terminal tipo;
    private int valor;

    /**
     * Constructor que inicializa un objeto IdentificadorBean con los parámetros especificados.
     *
     * @param nombre El nombre del identificador.
     * @param tipo El tipo del identificador, representado como una terminal del lenguaje.
     * @param valor El valor asociado al identificador.
     */
    public IdentificadorBean(String nombre, Terminal tipo, int valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Terminal getTipo() {
        return tipo;
    }

    public void setTipo(Terminal tipo) {
        this.tipo = tipo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

}
