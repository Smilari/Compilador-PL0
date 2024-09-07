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
Esto generará un archivo ejecutable con el mismo nombre (y distinta extensión) y el código generado a partir del archivo fuente PL/0 proporcionado.

### Ejemplo
Dentro de la carpeta `resources` vienen múltiples ejemplos prácticos para practicar, siendo los que empiezan con **"BIEN-"** los que compilan correctamente (ya que carecen de errores de compilación) y
**"MAL-"** los que al intentar compilar, lanzan un error en tiempo de compilación.

```bash
java -cp src compilador.Compilador BIEN-01
```

## Estructura del Proyecto

El proyecto está dividido en varias clases que representan las diferentes etapas del proceso de compilación del lenguaje PL/0. 

A continuación, se proporciona una descripción de cada componente:

### Analizador Lexico
El **Analizador Léxico** se encarga de procesar el código fuente y transformarlo en una secuencia de tokens que el compilador pueda entender. 

Su tarea principal incluye:

- Saltar separadores como espacios en blanco, tabulaciones y comentarios.
- Reconocer símbolos válidos e informar sobre los no válidos.
- Mantener un conteo de los renglones del programa. 
- Generar una copia de los caracteres de entrada en la salida, creando un listado de los renglones numerados.

Esta etapa es fundamental para poder identificar los elementos léxicos del lenguaje, como palabras clave, operadores, y delimitadores,
asegurando que el flujo de entrada esté libre de errores básicos antes de pasar al análisis sintáctico.

### Analizador Sintáctico
El **Analizador Sintáctico** verifica la estructura gramatical del código fuente para asegurarse de que los tokens detectados durante el análisis léxico están en el orden correcto y conforman un programa válido.

Este proceso, conocido como "parsing", determina si una secuencia de tokens puede ser generada a partir de un conjunto de producciones del lenguaje.

El análisis sintáctico sigue una serie de reglas que definen cómo deben manejarse los grafos y estructuras del lenguaje. Algunas de estas reglas incluyen:

- Reducción de grafos y sustituciones para simplificar la representación del código. 
- Declaraciones de procedimientos para cada grafo. 
- Sentencias compuestas para secuencias de elementos y sentencias condicionales para opciones entre elementos. 
- Bucles y referencias a otros grafos o símbolos terminales.

### Analizador Semántico 
El **Analizador Semántico** valida que el programa no solo sea gramaticalmente correcto, sino que también tenga sentido desde el punto de vista lógico. 

Esto incluye:

- Verificar la correcta utilización de variables y tipos de datos.
- Detectar inconsistencias como operaciones con objetos no declarados o incompatibilidades de tipos.

Durante esta etapa, los identificadores son buscados en una tabla desde una posición específica hacia atrás. 

Si el identificador no se encuentra, se genera un error de declaración faltante. Este análisis asegura que el programa sea semánticamente válido.

### Generador de Código
El **Generador de Código** toma el código fuente analizado y lo convierte en código objeto que puede ser ejecutado en una máquina específica. 

Este módulo puede ser reemplazado para generar código objeto para diferentes plataformas a partir del mismo código intermedio, permitiendo la flexibilidad de adaptación a diferentes entornos de ejecución.

### Constantes
La clase **Constantes** define valores constantes utilizados a lo largo del compilador, estandarizando los elementos repetitivos del código y facilitando su mantenimiento.

### Entrada y Salida
El módulo **EntradaSalida** proporciona funciones auxiliares para la gestión de entrada y salida de datos durante la compilación. 

Esto incluye la lectura del código fuente y la impresión de resultados.

### Identificador Bean
El **IdentificadorBean** es una estructura de datos que representa los identificadores utilizados en el código fuente, incluyendo información sobre su tipo, valor, y ámbito. 

Esta clase facilita el manejo y validación de identificadores durante el análisis semántico.

### Indicador de errores
El **IndicadorDeErrores** gestiona la detección, presentación, y manejo de errores durante el proceso de compilación. 

Los errores son clasificados en diferentes categorías (léxicos, sintácticos, semánticos, etc.) y cada uno es asociado con un mensaje descriptivo que facilita la corrección por parte del usuario.

### Terminal
La clase Terminal define un conjunto de terminales (tokens) que el compilador puede reconocer en el lenguaje PL/0. 

Estos incluyen palabras clave como IF, CALL, operadores aritméticos y lógicos como MAS y MENOR_IGUAL, y otros símbolos relevantes para el análisis sintáctico del código fuente.

## Ramas del Repositorio

Este repositorio contiene diferentes ramas que añaden funcionalidades adicionales al compilador:

- **master**: Rama principal con la funcionalidad básica del compilador.
- **Numeros-Lucas**: Implementa la funcionalidad para calcular la sucesión de Lucas.
- **Numeros-Pell**: Proporciona la capacidad de calcular la secuencia de Pell.
- **Numeros-Perrin**: Extiende el compilador para calcular la secuencia de Perrin.

Cada rama puede ser consultada para detalles específicos sobre las características implementadas y el código correspondiente.
