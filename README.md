# Compilador PL/0 en Java

Este repositorio contiene un **compilador para el lenguaje PL/0** desarrollado en Java. PL/0 es un pequeño lenguaje de programación de propósito educativo utilizado para enseñar los conceptos básicos de compiladores. Este proyecto incluye las funcionalidades de análisis léxico, sintáctico y semántico, así como la generación de código.

## Tabla de Contenidos

- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Ramas del Repositorio](#ramas-del-repositorio)

## Requisitos Previos

- **Java Development Kit (JDK)**: Asegúrate de tener instalado OpenJDK 22 o superior. Recomiendo [OpenJDK 22.0.2](https://www.oracle.com/java/technologies/downloads/#java22).
- **Git**: Necesario para clonar el repositorio.
- **Sistema Operativo**: Compatible con Windows.

### Verificar Versión de Java

Para verificar la versión instalada de Java, ejecuta en la terminal:

```bash
java -version
```

## Instalación

1. **Clonar el repositorio**:

    ```bash
    git clone https://github.com/Smilari/Compilador-PL0.git
    cd Compilador-PL0
    ```

2. **Compilar el proyecto**: *Teniendo el JDK instalado*
 
    ```bash
    javac src/compilador/*.java
    ```
    
## Uso
Para ejecutar el compilador:
- Primero asegúrate de tener un archivo con extension '.PL0' en la carpeta raiz del proyecto (la carpeta contenedora del proyecto).
- Luego usa el siguiente comando, reemplazando donde dice "nombre-del-archivo-PL0" por el nombre del archivo que vos hayas elegido **SIN EL .PL0**.

```bash
java -cp src compilador.Compilador nombre-del-archivo-PL0
```
Esto generará un archivo de salida con el código generado a partir del archivo fuente PL/0 proporcionado.

### Ejemplo
Dentro del proyecto vienen múltiples ejemplos prácticos para practicar, siendo los que empiezan con **"BIEN-"** los que compilan correctamente (ya que carecen de errores de compilación) y 
**"MAL-"** los que al intentar compilar, lanzan un error.

```bash
java -cp src compilador.Compilador BIEN-01
```

## Estructura del Proyecto

El proyecto está dividido en varias clases que representan las diferentes etapas del proceso de compilación:

- **AnalizadorLexico.java**:
  - Realiza el análisis léxico, transformando la entrada del código fuente en una secuencia de tokens.
- **AnalizadorSintactico.java**:
  - Realiza el análisis sintáctico, verificando la estructura gramatical del código fuente.
- **AnalizadorSemantico.java**:
  - Verifica la semántica del código, como la correcta utilización de variables y tipos de datos.
- **Compilador.java**:
  - Clase principal que coordina todo el proceso de compilación.
- **Constantes.java**:
  - Define las constantes utilizadas a lo largo del compilador.
- **EntradaSalida.java**:
  - Funciones útiles para facilitar la entrada y salida.
- **GeneradorDeCodigo.java**:
  - Genera el código objeto a partir del código fuente analizado.
- **IdentificadorBean.java**:
  - Estructura de datos para representar los identificadores utilizados en el código.
- **IndicadorDeErrores.java**:
  - Maneja la detección y presentación de errores durante el proceso de compilación.
- **Terminal.java**:
  - Define un conjunto de terminales (tokens) que el compilador puede reconocer en el lenguaje PL/0.
  Estos incluyen palabras clave como IF, CALL, operadores aritméticos y lógicos como MAS y MENOR_IGUAL, y otros símbolos relevantes para el análisis sintáctico del código fuente.

## Ramas del Repositorio

Este repositorio contiene diferentes ramas que añaden funcionalidades adicionales al compilador:

- **master**: Rama principal con la funcionalidad básica del compilador.
- **Numeros-Lucas**: Implementa la funcionalidad para calcular la sucesión de Lucas. El usuario puede especificar cuántos términos desea ver de la sucesión, con un mínimo de 3 términos.
- **Pell**: Proporciona la capacidad de calcular la secuencia de Pell utilizando una relación de recurrencia específica. Permite al usuario ingresar la cantidad de términos a mostrar.
- **Perrin**: Extiende el compilador para calcular la secuencia de Perrin. Utiliza una relación de recurrencia específica y maneja un número limitado de términos.
- **Recu**: Similar a `Perrin`, pero añade el uso de funciones predeterminadas de predicado como `pred` y `succ` para calcular el predecesor y sucesor de un número.

Cada rama puede ser consultada para detalles específicos sobre las características implementadas y el código correspondiente.
