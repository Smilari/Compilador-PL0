package compilador;

public class Compilador {


    /**
     * Método principal que inicia la ejecución del compilador.
     * <p>
     * Este método solicita al usuario que ingrese el nombre del archivo fuente a compilar.
     * A partir de ese nombre, inicializa los componentes del compilador y comienza el
     * proceso de compilación.
     * </p>
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
//        String nameArch = leer("Ingrese el nombre del archivo a compilar: ");
        String nameArch = args[0];
        String filePath = "resources/" + nameArch;

        IndicadorDeErrores indicaErrores = new IndicadorDeErrores();
        AnalizadorLexico aLexico = new AnalizadorLexico(indicaErrores, filePath + ".PL0");
        GeneradorDeCodigo gCodigo = new GeneradorDeCodigo(filePath + ".exe", indicaErrores);
        AnalizadorSintactico aSintactico = new AnalizadorSintactico(indicaErrores, aLexico, gCodigo);

        aSintactico.programa();
    }
}