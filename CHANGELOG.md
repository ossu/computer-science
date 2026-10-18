# Registro de Cambios

**Nota**: El plan de estudios está actualmente en revisión para la v9. Esto consiste en gran parte en verificar nuestras recomendaciones contra nuestras [directrices curriculares](CURRICULAR_GUIDELINES.md), agregar temas faltantes y eliminar cursos redundantes o fuera de alcance. A medida que se completan los Requests for Comment de este esfuerzo, los cambios se realizan de inmediato en el plan de estudios. Cuando la revisión general esté completa, anotaremos el salto de versión a la v9.

Todos los cambios notables de este proyecto se documentarán en este archivo.
Este proyecto se adhiere *en espíritu* a [Versionamiento Semántico](http://semver.org/):
- Las actualizaciones "MAJOR" corresponden a cambiar los temas estudiados dentro de una asignatura
- Las actualizaciones "MINOR" corresponden a cambiar cursos sin cambiar los temas
- Las actualizaciones "PATCH" corresponden a adiciones/eliminaciones estéticas y no esenciales, o a cambiar el orden de las clases para una mejor progresión

## [8.0.0] 2017-11-01
### Agregado
- extras/readings: "The System Design Primer"
- extras/readings: "Category Theory for Programmers: The Preface"
- extras/readings: "Programming Languages: Application and Interpretation"
- extras/readings: "Programming and Programming Languages"
- CONTRIBUTING: sección "Learning Git" en la página de directrices para colaboradores
- Core Math: se agregó "Essence of Linear Algebra" como requisito previo de "Linear Algebra: Foundations to Frontiers"

### Actualizado
- Se movió "Introduction to Mathematical Thinking" a extras/courses
- Se movió "Hack the Kernel" (ops-class) de Sistemas Avanzados a Sistemas Básicos
- Core Systems: "Operating Systems: Three Easy Pieces" ya no es obligatorio, pero se recomienda como texto complementario de "Hack the Kernel"
- Core Theory: se reemplazó Coursera por Lagunita como alojamiento de Stanford Algorithms, ya que Coursera usa patrones oscuros para engañar a los usuarios y hacerlos pagar

## [7.2.2] 2017-07-02
### Agregado
- Libro "Haskell Programming from First Principles" como alternativa de pago para aprender Haskell
- "Think Python" en extras/readings
- Entradas y enlaces de la FAQ bajo los cursos relevantes
- "Category Theory: A Gentle Introduction" en extras/readings

## [7.2.1] 2017-05-14
### Actualizado
- El curso de redes debería tomar 8 semanas en completarse
- Se corrigió un error ortográfico

### Agregado
- Curso Introduction to Haskell en [extras/courses](extras/courses.md)

## [7.2.0] 2017-04-28
### Agregado
- Curso de pruebas de software
- Enlace a Algorithms: Design and Analysis de Stanford Lagunita
- Se agregó un enlace a la sección de ecuaciones paramétricas y coordenadas polares del curso de Cálculo de una Variable del MIT para preparar adecuadamente a los estudiantes para Cálculo Multivariable

## [7.1.2] 2017-04-22
### Actualizado
- Se agregó un enlace a la Mega Project List en la introducción de la sección de Proyectos

## [7.1.1] 2017-04-11
### Actualizado
- Toque final para la publicación

## [7.1.0] 2017-04-10
### Actualizado
- Se revirtió el reformato del curso de lenguajes de programación

### Agregado
- Cursos de Algoritmos Distribuidos Confiables
- Nuevo curso de Introducción a las Ciencias de la Computación

## [7.0.2] 2017-03-30
### Actualizado
- Se movieron los cursos opcionales de aprendizaje en línea a extras/courses en una nueva sección
- Se movió el curso alternativo de arquitectura de computadoras a extras/courses

### Agregado
- Especialización en Scala dentro de Aplicaciones Avanzadas

### Eliminado
- Se eliminaron todas las opciones menos una para las lecturas obligatorias a fin de simplificar el plan de estudios

## [7.0.1] 2017-03-11
### Actualizado
- Se corrigió el enlace a la página de ciencias de la computación DIY de Bradfield

### Agregado
- Nota bajo Calculus One con enlaces a erratas y recomendaciones de progresión del curso
- Cursos opcionales dentro de extras:
  - Curso de Strang sobre álgebra lineal
  - Structure and Interpretation of Computer Programs de Berkeley
- Lecturas opcionales dentro de extras:
  - Libro avanzado de programación de Van Roy
  - Libro de arquitectura de computadoras de P&H
  - Libro de algoritmos de Skiena
  - Libro de álgebra lineal de Strang
  - Libro de Database Management Systems
  - Libro de Tarr sobre cómo crear tu propio lenguaje específico de dominio
  - Lecturas de diversos autores sobre sistemas distribuidos

## [7.0] 2017-03-09
Reestructuración completa de la estructura del programa

### Actualizado
- Se aclararon las directrices para colaboradores y se movieron a un archivo separado
- Se pasó de muchas asignaturas a solo cuatro asignaturas con muchos temas
- Se consolidaron free-books.md y paid-books.md en readings.md
- Se consolidaron free-courses.md y paid-courses.md en courses.md
- Se reemplazó el antiguo "How to Code" por el nuevo "How to Code" (Software Development MicroMasters)
- Se reemplazó Princeton Algorithms (movido a [alternative courses](#extras/courses.md)) por Stanford Algorithms

### Agregado
- Se indican los requisitos previos de todos los cursos
- Requisitos: requisitos de asignatura/tema y requisitos de proyectos
- Lecturas obligatorias sobre Haskell, Prolog, Sistemas Operativos
- Cursos: Programming Languages de Dan Grossman
- Cursos: From Nand to Tetris
- Curso optativo: Intro to Parallel Programming
- Curso optativo: LAFF: Programming for Correctness
- Curso optativo: Introduction to Mathematical Thinking
- Cursos optativos: Electricidad y Magnetismo
- Cursos optativos: Computation Structures del MIT
- Curso optativo: Multivariable Calculus
- Curso optativo: ops-class.org
- Curso optativo: Automata Theory
- Curso optativo: Introduction to Logic
- Curso optativo: Computational Geometry
- Curso optativo: Formal Concept Analysis
- Curso optativo: Game Theory
- Especializaciones optativas:
  - Robótica
  - Minería de datos
  - Big Data
  - Internet de las cosas
  - Computación en la nube
  - Desarrollo web full stack
  - Ciencia de datos
- Especializaciones profesionales:
  - Mastering Software Development in R
  - Artificial Intelligence Engineer
  - Machine Learning Engineer
  - Ciberseguridad
  - Android Developer

### Eliminado
- Se eliminaron muchos enlaces muertos y cursos obsoletos
- Se eliminó el requisito de proyecto por curso
- Curso: Object-Oriented Programming in Java
- Curso: Functional Programming in Scala
- Curso: Computer Architecture (pero se dejó como una nota al pie)
- Curso: Intro to Theoretical Computer Science
- Curso: Software Processes and Agile Practices
- Curso: Operating Systems & System Programming
- Curso: Introduction to Cyber Security
- Curso: Parallel Computer Architecture and Programming
- Curso: UX Design for Mobile Developers

## [6.0] 2016-10-09
### Actualizado
- Se colocó Calculus One antes y junto con Mathematics for Computer Science
- Se mejoró el texto en "Orden de las clases"

### Agregado
- Se creó un tablero público de Trello con la nueva versión del plan de estudios
- Se creó la sección "Cómo seguir y mostrar tu progreso" en "Cómo usar esta guía"
- Se agregó el archivo PROJECTS.md
- Se copiaron todas las secciones del plan de estudios a PROJECTS.md

### Eliminado
- Se eliminó la sección "Next Goals"
- Se eliminó la referencia a la aplicación web de OSSU

## [5.1.0] 2016-08-20
Actualización a la última versión de Mathematics for Computer Science:

### Actualizado
- Sección: **Matemáticas (Matemáticas Discretas)**
  - Mathematics for Computer Science

## [5.0.0] 2016-08-20
Debido a un curso eliminado, tuvimos las siguientes actualizaciones:

### Eliminado
- Sección: **Procesamiento de Lenguaje Natural**
  - Natural Language Processing

### Agregado
- Sección: **Procesamiento de Lenguaje Natural**
  - Introduction to Natural Language Processing

## [4.1.0] 2016-08-05
Debido a los cambios de plataforma de Coursera, tuvimos las siguientes actualizaciones:
### Corregido
- Sección: **Big Data**
  - Introduction to Big Data

## [4.0.0] 2016-07-30
Debido a los cambios de plataforma de Coursera, tuvimos las siguientes actualizaciones:

### Eliminado
- Sección: **Teoría**
  - Automata
- Sección: **Matemáticas (Álgebra Lineal)**
  - Coding the Matrix: Linear Algebra through Computer Science Applications
- Sección: **Computación Paralela**
  - Heterogeneous Parallel Programming
- Sección: **Procesamiento de Lenguaje Natural**
  - Natural Language Processing

### Corregido
- Sección: **Redes de Computadoras**
  - Computer Networks
- Sección: **Compiladores**
  - Compilers

### Agregado
- Sección: **Teoría**
  - Intro to Theoretical Computer Science
- Sección: **Matemáticas (Álgebra Lineal)**
  - Linear Algebra - Foundations to Frontiers
- Sección: **Computación Paralela**
  - Parallel Computer Architecture and Programming
- Sección: **Procesamiento de Lenguaje Natural**
  - Natural Language Processing

## [3.0.0] 2016-05-04
### Eliminado
- Sección: **Introducción a las Ciencias de la Computación**:
	- Introduction to Computer Science and Programming Using Python
	- From Nand to Tetris (Part 1)

### Agregado
- Sección: **Introducción a las Ciencias de la Computación**:
	- Introduction to Computer Science - CS50

## [2.0.1] 2016-04-04
### Corregido
- Ahora los estudiantes deben inscribirse a través de nuestra [aplicación web](https://ossu.firebaseapp.com).

## [2.0.0] 2016-03-17
### Corregido
- Nombres y enlaces de los cursos de la sección Program Design

### Eliminado
- **Introducción a las Ciencias de la Computación**:
	- Introduction to Computer Science
	- Introduction to Computational Thinking and Data Science
- **Algoritmos**:
	- Analysis of Algorithms
- **Paradigmas de Programación**:
	- Principles of Reactive Programming
- **Matemáticas (Cálculo)**:
	- Multivariable Calculus
- **Arquitectura de Software**:
	- Web Application Architectures
- **Ingeniería de Software**:
	- Agile Development Using Ruby on Rails - Basics
	- Agile Development Using Ruby on Rails - Advanced
	- Startup Engineering
- **Arquitectura de Computadoras**:
	- The Hardware/Software Interface
- **Sistemas Operativos**:
	- Operating System Engineering
- **Redes de Computadoras**:
	- Introduction to Computer Networking
- **Criptografía**:
	- Applied Cryptography

**pd**: Estos cursos eliminados ahora están en la sección [extras](https://github.com/ossu/computer-science/tree/master/extras).

## [1.3.12] 2016-03-17
### Agregado
- Cómo colaborar: enviar nuevos enlaces a la sección de extras

## [1.3.11] 2016-03-06
### Corregido
- Nand to Tetris: cambio de nombre y url
- UC Berkeley Agile development: cambio de nombre y url
- Enlaces directos a las especializaciones

## [1.3.10] 2016-03-06
### Corregido
- Enlace del curso Systematic Program Design Part 2

## [1.3.9] 2015-11-09
### Corregido
- Enlace al curso correcto de Natural Language Processing

## [1.3.8] 2015-11-07
### Agregado
- Se agregó la sección "Project Suggestions" con más referencias

## [1.3.7] 2015-11-01
### Eliminado
- Se eliminó el archivo project.md, movido al repositorio **help**

## [1.3.6] 2015-10-22
### Agregado
- Última versión de CS 162, Operating Systems and System Programming

## [1.2.6] 2015-10-19
### Agregado
- Insignia/enlace a la lista Awesome

## [1.2.5] 2015-10-16
### Corregido
- Se corrigió el nombre de la sección y se agregó un hipervínculo a ella.

## [1.2.4] 2015-10-14
### Eliminado
- Se eliminó la citación sobre el compromiso público

## [1.2.3] 2015-10-12
### Cambiado
- Se actualizó la sección de requisitos previos para mayor claridad

## [1.2.2] 2015-10-12
### Corregido
- Nuevo enlace a la issue destinada a la inscripción de estudiantes

## [1.2.1] 2015-10-11
### Agregado
- Artículo Git - the simple guide en la sección de requisitos previos

##[1.1.1] 2015-10-11
### Corregido
- Se corrigieron erratas
  - Dado que MOOC significa "Massive Open Online Course", decir curso MOOC es redundante
  - Se amplió la explicación de "real problem"
  - Se corrigieron algunos pequeños errores gramaticales y de redacción

## [1.1.0] 2015-10-08
### Agregado
- Sección de Motivación y Preparación (recursos opcionales)
  - Artículo: MIT Challenge
  - Curso: Learning How to Learn

## [1.0.0] 2015-10-08

Publicación de la primera versión **completa** del plan de estudios de Ciencias de la Computación
