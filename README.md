# Cambios en la Rama `Numeros-Pell`

Este documento detalla los cambios realizados en el compilador para soportar la funcionalidad de la sucesión de Pell en la rama `Numeros-Pell`.
Se incluye también un archivo `PELL.PL0` para poder probar y un PDF explicativo sobre la funcionalidad desarrollada.

## Números de Pell y PELL.PL0
La sucesión de Pell es similar a la de Fibonacci, pero comienza con los valores 2 y -1.

El archivo `PELL.PL0` permite al usuario definir cuántos términos de la sucesión quiere ver y maneja entradas incorrectas, asegurando que el usuario ingrese un número mínimo de 3 términos.
El procedimiento `pell` realiza los cálculos de la sucesión de Pell y el programa principal controla la entrada del usuario y la impresión de la secuencia.

## Analizador Sintáctico

### 1. Soporte para Números Negativos

- **Ubicación**: Líneas 117-128
- **Descripción**: Se añadió la posibilidad de entrada de números negativos.

### 2. Funcionalidad del 'FOR'

- **Ubicación**: Líneas 383-469
- **Descripción**: Se agregó la funcionalidad del bucle `FOR` de la forma `for [cantidad] to [cantidad] do`

## Analizador Léxico

### 1. Palabra Reservada 'FOR'

- **Ubicación**: Línea 63
- **Descripción**: Se agregó como palabra reservada la instrucción `FOR` para habilitar su uso en los bucles definidos por el usuario.
### 2. Palabra Reservada 'TO'

- **Ubicación**: Línea 64
- **Descripción**: Se agregó como palabra reservada la instrucción `TO` para definir el límite superior de un bucle `FOR`.

## Analizador Semántico

### 1. Identificadores no 'Case Sensitive'
- **Ubicación**: Linea 40 y 43
- **Descripción**: Cambiado para que los identificadores no sean 'Case Sensitive' (sensible a mayúsculas y minúsculas)

## Terminal

### 1. Terminal 'FOR'
- **Ubicación**: Linea 11
- **Descripción**: Se agregó como terminal a `FOR`.

### 2. Terminal 'TO'
- **Ubicación**: Linea 12
- **Descripción**: Se agregó como terminal a `TO`.

---

Estos cambios permiten al compilador manejar de manera correcta las nuevas funcionalidades requeridas para trabajar con la sucesión de Pell y las nuevas construcciones de bucle en PL/0.