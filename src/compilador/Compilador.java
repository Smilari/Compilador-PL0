package compilador;

/*IMPORTS*/
import static compilador.EntradaSalida.*;

public class Compilador {

    public static void main(String[] args) {
        String nameArch = leer("Por favor ingrese el nombre del archivo a compilar: ");
//        String nameArch = args[0];
        IndicadorDeErrores indicaErrores = new IndicadorDeErrores();
        AnalizadorLexico aLexico = new AnalizadorLexico(indicaErrores, nameArch + ".PL0");
        GeneradorDeCodigo gCodigo = new GeneradorDeCodigo(nameArch + ".exe", indicaErrores);
        AnalizadorSintactico aSintactico = new AnalizadorSintactico(indicaErrores, aLexico, gCodigo);

        aSintactico.programa();
    }
}