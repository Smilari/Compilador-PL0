# Cambios en la Rama `Numeros-Perrin`

Este documento detalla los cambios realizados en el compilador para soportar la funcionalidad de la sucesión de Perrin en la rama `Numeros-Perrin`.
Se incluye también un archivo `PERRIN.PL0` para poder probar y un PDF explicativo sobre la funcionalidad desarrollada.

## Números de Perrin y PERRIN.PL0
La sucesión de Perrin es una secuencia matemática definida por la relación de recurrencia: P(n) = P(n − 2) + P(n − 3), con los valores iniciales de P(0) = 3, P(1) = 0, y P(2) = 2.

El archivo `PERRIN.PL0` muestra en pantalla los 15 primeros términos de la sucesión de Perrin. 
El procedimiento `perrin` realiza los cálculos de la sucesión de Perrin y el programa principal realiza la impresión de la secuencia.

## Analizador Sintáctico

### 1. Inicialización de Constantes

- **Ubicación**: Línea 220
- **Descripción**: Se integró la posibilidad de inicializar una constante con un signo igual ('=').

### 2. Soporte para Números Negativos

- **Ubicación**: Líneas 117-128
- **Descripción**: Se añadió la posibilidad de entrada de números negativos.

### 3. Funcionalidad del 'ELSE'

- **Ubicación**: Líneas 273-294
- **Descripción**: Se agregó la funcionalidad de `ELSE` para manejar condiciones alternativas dentro de las estructuras `IF`.

### 4. Funcionalidad del 'HALT'

- **Ubicación**: Líneas 398-404
- **Descripción**: Se agregó la funcionalidad de `HALT` para terminar el programa cuando se ejecuta.

### 5. Comportamiento del doble igual '=='
- **Ubicación**: Líneas 456 y 465
- **Descripción**: Se maneja el comportamiento del símbolo `==` (mismo comportamiento que el símbolo `=`) .

## Analizador Léxico

### 1. Palabra Reservada 'HALT'

- **Ubicación**: Línea 63
- **Descripción**: Se agregó como palabra reservada la instrucción `HALT`.
### 2. Palabra Reservada 'ELSE'

- **Ubicación**: Línea 64
- **Descripción**: Se agregó como palabra reservada la instrucción `ELSE`.

### 3. Soporte para el doble igual '==' 
- **Ubicación**: Líneas 219-230
- **Descripción**: Cuando se lee un `=` se acepta la lectura de un segundo.

## Analizador Semántico

### 1. Identificadores no 'Case Sensitive'
- **Ubicación**: Lineas 40 y 43
- **Descripción**: Cambiado para que los identificadores no sean 'Case Sensitive' (sensible a mayúsculas y minúsculas)

## Terminal

### 1. Terminal 'HALT'
- **Ubicación**: Linea 11
- **Descripción**: Se agregó como terminal a `HALT`.

### 2. Terminal 'ELSE'
- **Ubicación**: Linea 12
- **Descripción**: Se agregó como terminal a `ELSE`.

### 3. Terminal 'DOBLE_IGUAL'
- **Ubicación**: Linea 13
- **Descripción**: Se agregó como terminal a `DOBLE_IGUAL`.

---

Estos cambios permiten al compilador manejar de manera correcta las nuevas funcionalidades requeridas para trabajar con la sucesión de Perrin y las nuevas instrucciones y operadores en PL/0.