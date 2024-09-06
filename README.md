# Cambios en la Rama `Numeros-Lucas`

Este documento detalla los cambios realizados en el compilador para soportar la funcionalidad de la sucesión de Lucas en la rama `Numeros-Lucas`.
Se incluye también un archivo `LUCAS.PL0` para poder probar y un PDF explicativo sobre la funcionalidad desarrollada.

## Números de Lucas y LUCAS.PL0
La sucesión de Lucas es similar a la de Fibonacci, pero comienza con los valores 2 y -1. 

El archivo 'LUCAS.PL0' permite al usuario definir cuántos términos de la sucesión quiere ver y maneja entradas incorrectas, asegurando que el usuario ingrese un número mínimo de 3 términos. 
El procedimiento 'lucas' realiza los cálculos de la sucesión de Lucas y el programa principal controla la entrada del usuario y la impresión de la secuencia.

## Analizador Sintáctico

### 1. Inicialización de Constantes

- **Ubicación**: Línea 220
- **Descripción**: Se integró la posibilidad de inicializar una constante con un signo igual ('=').

### 2. Soporte para Números Negativos

- **Ubicación**: Líneas 117-128
- **Descripción**: Se añadió la posibilidad de entrada de números negativos.

### 3. Funcionalidad del 'NOT'

- **Ubicación**: Líneas 389-411
- **Descripción**: Se agregó la funcionalidad del `NOT` como una forma de condición que niega la condición dentro de los paréntesis.

### 4. Funcionalidad del 'HALT'

- **Ubicación**: Líneas 378-383
- **Descripción**: Se agregó la funcionalidad de `HALT` para terminar el programa cuando se ejecuta.

## Analizador Léxico

### 1. Palabra Reservada 'NOT'

- **Ubicación**: Línea 57
- **Descripción**: Se agregó como palabra reservada la instrucción `NOT`.

### 2. Palabra Reservada 'HALT'

- **Ubicación**: Línea 63
- **Descripción**: Se agregó como palabra reservada la instrucción `HALT`.

## Analizador Semántico

### 1. Identificadores no 'Case Sensitive'
- **Ubicación**: Linea 40 y 43
- **Descripción**: Cambiado para que los identificadores no sean 'Case Sensitive' (sensible a mayúsculas y minúsculas)

## Terminal

### 1. Terminal 'HALT'
- **Ubicación**: Linea 11
- **Descripción**: Se agregó como terminal a `HALT`.

---

Cada uno de estos cambios permite al compilador manejar de manera correcta las nuevas funcionalidades requeridas para trabajar con la sucesión de Lucas y las nuevas instrucciones en PL/0.
