# Operating Systems: Three Easy Pieces

Agradecimientos a [palladian](https://github.com/palladian1)
## Introducción

Primero, seamos francos: es realmente difícil encontrar un buen curso en línea y autocontenido sobre sistemas operativos. OSTEP es el mejor curso que hemos encontrado hasta ahora. Describimos a continuación dos enfoques del curso: un enfoque "Base", adecuado para la mayoría de los estudiantes, y un enfoque "Extendido", apropiado para estudiantes que desean especializarse en programación de sistemas.

El enfoque "base" cubre todos los requisitos de nuestro currículo de sistemas operativos y debería llevar unas 80 horas de trabajo.

El enfoque "extendido" contiene todo el trabajo del enfoque base y más. Implica aprender C y ensamblador x86 muy serios, y adentrarse a fondo en la programación de kernels. Toma bastante más tiempo (más de 200 horas) y es mucho más desafiante. Para esos estudiantes interesados en esta área de la computación, también es muy gratificante.

## Enfoque base

1. Lee el libro de texto en línea gratuito Operating Systems: Three Easy Pieces
2. Completa las preguntas de tarea al final de cada capítulo. (Hay un [repositorio de Github](https://github.com/remzi-arpacidusseau/ostep-homework) asociado para las tareas.)

Esto debería llevar unas 8 semanas, 10 horas/semana. ¡Eso es todo lo que necesitas hacer!

Necesitarás un sistema Unix/Linux, algunas herramientas básicas de línea de comandos y un compilador de C (como GCC o Clang). En Windows, puedes instalar Ubuntu en una máquina virtual o usar WSL (Windows Subsystem for Linux). Mac OS es tipo Unix, así que debería funcionar bien usarlo.

### Enlaces del curso

* [Libro](https://pages.cs.wisc.edu/~remzi/OSTEP/)
* [Tareas](https://pages.cs.wisc.edu/~remzi/OSTEP/Homework/homework.html)
* [Repositorio de código fuente de las tareas](https://github.com/remzi-arpacidusseau/ostep-homework)
* [Soluciones de las tareas](https://github.com/xxyzz/ostep-hw)

### C

**Pregunta**: Veo algo de código C en este libro. ¿Cuánto C necesito saber?

**Respuesta**: Necesitarás leer y entender algo de código C en este libro. Necesitarás una comprensión básica de arreglos, punteros y formato de impresión. Puedes consultar el libro gratuito [Dive into Systems: Capítulo 1 y 2](https://diveintosystems.org/book/C1-C_intro/index.html). Las [páginas del manual de CS50](https://manual.cs50.io) también son útiles para buscar funciones. No deberías dedicar demasiado tiempo a aprender C.

El código que leerás es bastante simple y se presenta en fragmentos cortos. El libro te ayuda bastante presentando manualmente muchas APIs de C, como la Process API, la Thread API, y así sucesivamente. Puedes escribir, compilar y ejecutar los fragmentos de código y leer las explicaciones correspondientes. El libro los explica con gran detalle, en un estilo conversacional que es divertido de leer.

También escribirás un poco de código C. Solo una minoría de los capítulos (unos 10 de 50) te piden escribir algo de código C (mientras que los demás capítulos requieren que ejecutes el código de simulación proporcionado y respondas preguntas). Estos suelen ser programas C simples y cortos que imitan el código presentado en ese capítulo, con pequeñas modificaciones.
Cuando estés listo para empezar la tarea, empieza con el [proyecto reverse](https://github.com/billmei/ostep-projects/tree/billmei/patch-1/initial-reverse); esta será una buena prueba para ver si estás listo para el resto de los proyectos. Para el resto de los proyectos de tarea, usa el enlace que está bajo los enlaces del curso.

Si te atascas en estos, por favor no dediques demasiado tiempo. Hay un excelente conjunto de soluciones [aquí](https://github.com/xxyzz/ostep-hw). No hay código de honor para esto, así que eres libre de usar las soluciones. Si te encuentras dedicando demasiado tiempo, siéntete libre de leer y entender las soluciones. Tu prioridad principal debe ser comprender los conceptos de los sistemas operativos, no dominar la codificación en C.

## Enfoque extendido

Si has elegido esta opción, entonces este es el primer curso del OSSU curriculum para el que necesitarás aprender algunos prerrequisitos por tu cuenta antes de empezarlo, además de los cursos que lo anteceden en el currículo. También podrías encontrar algunos problemas al ejecutar los scripts de demostración de las tareas y para probar tus soluciones a los proyectos (aunque esperamos haber resuelto la mayoría de ellos para ahora).

Dicho esto, si puedes destinar el tiempo requerido a los prerrequisitos, creemos que la recompensa merece mucho el esfuerzo: este curso es emocionante, interesante y bastante útil para otros campos de la ciencia de la computación y la programación. Una de las grandes atracciones de este curso es la oportunidad de ver un sistema operativo tipo Unix simplificado pero totalmente funcional en acción, y comprender los conceptos y las decisiones de diseño que se aplicaron en él, así como los detalles de implementación de bajo nivel.

Deberías ver todos los videos de las clases o leer los capítulos 1 a 47 del libro de texto (no te preocupes, los capítulos suelen tener solo unas pocas páginas), así como terminar los proyectos listados abajo. También te animamos encarecidamente a hacer los ejercicios de tarea tal como se asignan en el sitio web del curso o en los capítulos del libro; piensa en ellos como las preguntas de "verifica tu comprensión" que aparecen en medio de los videos de clases en sitios como Coursera o edX.

### Prerrequisitos

Esta clase requiere mucha experiencia programando en C. Debes terminar uno de los libros de C listados en los [recursos a continuación](#c-1) *antes* de empezar este curso; si intentas aprender C al mismo tiempo que el material del curso, probablemente te sentirás abrumado. Si nunca has usado C antes, espera dedicarle mucho tiempo; es difícil prever cuánto tiempo le llevará a cada persona, pero una estimación aproximada podría ser de 8 a 10 horas por semana durante 3 a 5 semanas. Siempre puedes aprender C junto con otro curso de OSSU o incluso volver a hacer los ejercicios de otros cursos en C para ganar práctica con él.

También deberías terminar las dos partes de Nand2Tetris antes de empezar este curso. OSTEP se centra en las arquitecturas reales x86 y x86_64, así que tendrás que llenar algunos vacíos para trasladar los conceptos que aprendiste en Nand2Tetris a una nueva arquitectura. Puedes hacerlo con los recursos de x86 listados abajo, pero ten en cuenta que todos asumen que sabes C, así que aprende eso primero. Esto debería llevar alrededor de 6 a 8 horas en total.

### Enlaces del curso

* [Sitio web del curso](https://pages.cs.wisc.edu/~remzi/Classes/537/Spring2018/)
* [Libro](https://pages.cs.wisc.edu/~remzi/OSTEP/)
* [Videos de las clases](https://pages.cs.wisc.edu/~remzi/Classes/537/Spring2018/Discussion/videos.html)
* [Tareas](https://pages.cs.wisc.edu/~remzi/OSTEP/Homework/homework.html)
* [Repositorio de código fuente de las tareas](https://github.com/remzi-arpacidusseau/ostep-homework)
* [Soluciones de las tareas](https://github.com/xxyzz/ostep-hw)
* [Proyectos](https://github.com/remzi-arpacidusseau/ostep-projects)
* [xv6](https://github.com/mit-pdos/xv6-public)

### Hoja de ruta

Este curso fue impartido originalmente como CS 537 en la University of Wisconsin por el autor del libro de texto de OSTEP, por lo que los proyectos se asignan en el curso de acuerdo con los mejores momentos para dar a los estudiantes de UWisconsin acceso a recursos del campus como secciones de recitación y horas de oficina. Eso significa que no coinciden perfectamente con el material que se cubre en ese momento en las clases o en los capítulos del libro. Recomendamos hacer el curso en el siguiente orden.

[Reading order](Reading-order.md)

* Lee los capítulos 1 y 2 del libro de texto de OSTEP y mira la primera mitad (la introducción) de la clase 1.
* Haz el proyecto `initial-utilities`; está pensado como una prueba para que compruebes que estás suficientemente cómodo con C antes de tomar esta clase. Puedes ver la discusión 1 para ayudarte. Si te lleva más de 2 horas escribir el código (sin contar el tiempo de la discusión ni el tiempo de depuración), deberías considerar dedicar más tiempo a aprender C antes de continuar en el curso. (Si quieres más práctica, también puedes hacer `initial-reverse`, pero no es obligatorio.)
* Mira las clases 1 a 5 y lee los capítulos 3 a 24 del libro de texto de OSTEP. Recomendamos hacer las tareas a medida que aparecen en el calendario del curso o en los capítulos del libro.
* Mira la discusión 3 y vuelve a leer el capítulo 5, y luego haz el proyecto `processes-shell`.
* Lee la guía anotada de xv6 enlazada en la sección de recursos de abajo, empezando desde el principio y deteniéndote después de la sección `System Calls: Processes`.
* Mira la discusión 2 y luego haz el proyecto `initial-xv6`.
* Mira la discusión 5 y luego haz el proyecto `scheduling-xv6-lottery`.
* Mira la discusión 7 y luego haz el proyecto `vm-xv6-intro`.
* Mira las clases 6 a 9 (y opcionalmente, la clase de repaso) y lee los capítulos 25 a 34; de nuevo, se te anima a hacer las tareas.
* Mira la discusión 10 y luego haz el proyecto `concurrency-xv6-threads`.
* Mira las discusiones 11 y 12 y luego haz el proyecto `concurrency-mapreduce`.
* Mira las clases 10 a 14 (y opcionalmente, la segunda clase de repaso) y lee los capítulos 35 a 47; recuerda hacer las tareas junto con las clases o los capítulos.
* Haz el proyecto `filesystems-checker`.

### Ejecución de los proyectos

Este curso fue impartido originalmente como CS 537 en la University of Wisconsin por el autor del libro de texto de OSTEP, lo que significa que las tareas y los proyectos fueron escritos con esos estudiantes como público objetivo y diseñados para ejecutarse en las computadoras del laboratorio de UWisconsin con versiones de software específicas preinstaladas para los estudiantes. Esperamos que esta sección resuelva eso para que puedas ejecutarlos en otras computadoras, pero no lo hemos probado en todas, así que si encuentras otros problemas, avísanos en el [canal de Discord](https://discord.gg/MJ9YXyV) e intentaremos ayudarte.

Para ejecutar las tareas y los proyectos en Linux o macOS, necesitarás tener instalados todos los siguientes programas:

* `gcc`
* `gas`
* `ld`
* `gdb`
* `make`
* `objcopy`
* `objdump`
* `dd`
* `python`
* `perl`
* `gawk`
* `expect`
* `git`

También necesitarás instalar `qemu`, pero recomendamos usar la versión parchada proporcionada por los autores de xv6; consulta [este enlace](https://pdos.csail.mit.edu/6.828/2018/tools.html) para más detalles.

En macOS, necesitarás instalar un conjunto de compilador cruzado `gcc` capaz de producir binarios ELF de x86; consulta el enlace de arriba para los detalles también.

En Windows, puedes usar una máquina virtual de Linux para las tareas y los proyectos. Algunos de estos paquetes aún no están soportados en computadoras Apple M1, y el software de máquinas virtuales aún no ha sido portado a la nueva arquitectura de procesadores; algunos estudiantes han usado un VPS para hacer las tareas y los proyectos.

A continuación, clona los repositorios `ostep-homework` y `ostep-projects`:
```sh
git clone https://github.com/remzi-arpacidusseau/ostep-homework/
git clone https://github.com/remzi-arpacidusseau/ostep-projects/
cd ostep-projects
```

Tendrás que clonar [el repositorio `xv6-public`](https://github.com/mit-pdos/xv6-public) dentro del directorio de cada proyecto de OSTEP relacionado con xv6. Podrías usar la misma copia para todos los proyectos, pero recomendamos usar copias separadas para evitar que los proyectos anteriores causen errores en los posteriores. Ejecuta los siguientes comandos en *cada* uno de los directorios `initial-xv6`, `scheduling-xv6-lottery`, `vm-xv6-intro`, `concurrency-xv6-threads` y `filesystems-checker`.

```sh
mkdir src
git clone https://github.com/mit-pdos/xv6-public src
```

### Pistas y consejos para los proyectos

- `initial-reverse`: ¡los mensajes de error que se necesitaban para pasar las pruebas estaban mal! El texto proporcionado decía `"error: ..."` pero las pruebas esperaban `"reverse: ..."`, así que asegúrate de cumplir las expectativas de las pruebas en tu código.
- [pistas y consejos para `processes-shell`](Project-2A-processes-shell.md)
- [pistas para el Proyecto 1B: `initial-xv6`](Project-1B-initial-xv6.md)
- [pistas para `scheduling-xv6-lottery`](Scheduling-xv6-lottery.md)
- [pistas para `vm-xv6-intro`](vm-xv6-intro.md)

### Recursos

#### C

Por favor, no intentes aprender C de sitios como GeeksforGeeks, TutorialsPoint o Hackr.io (ni siquiera vamos a enlazarlos aquí). Esos son excelentes recursos para otros lenguajes, pero C tiene demasiados peligros, y los tutoriales de C en línea a menudo están llenos de errores peligrosos y malas prácticas de codificación. Revisamos muchos recursos de C para las recomendaciones de abajo y desafortunadamente encontramos *muchos* malos o inseguros; solo incluiremos los mejores aquí, ¡así que no busques más!

Recomendamos aprender C trabajando (a fondo) *Modern C* de Jens Gustedt, que está [disponible gratuitamente en línea](https://inria.hal.science/hal-02383654v2/file/modernC.pdf). Este libro es relativamente corto y te pondrá al día tanto en el lenguaje C en sí como en las prácticas de codificación modernas para él. ¡Asegúrate de hacer todos los ejercicios en las notas al pie!

Aunque el libro de arriba es nuestra recomendación por defecto, también recomendamos [*C Programming: A Modern Approach*](http://www.knking.com/books/c2/) de K.N. King como una segunda opción, más amigable para principiantes. Tiene algunas desventajas: es mucho más largo (casi 850 páginas), no está disponible de forma gratuita (y las copias pueden ser difíciles de encontrar), y no es tan reciente como *Modern C* (aunque sigue siendo relevante). Dicho esto, tiene más ejercicios si quieres práctica extra, y las secciones de preguntas y respuestas al final de cada capítulo están llenas de joyas de sabiduría sobre C y de respuestas a las preguntas frecuentes de C. También cubre casi la totalidad del lenguaje C y de la biblioteca estándar, así que también sirve como libro de referencia.

CS 50 no cubre suficiente C para OSTEP, pero si ya has cursado CS 50, puedes complementarlo con los libros de arriba.

Recursos adicionales (***opcionales***) incluyen:
* [CS 50 Manual Pages](https://manual.cs50.io): una gran referencia para buscar funciones de la biblioteca de C; la mayoría de las funciones incluyen tanto el manual habitual como una opción más amigable para principiantes, "less comfortable" (simplemente ten en cuenta que la versión "less comfortable" usa `string` como alias de `char *`.)
* [cdecl](https://cdecl.org): una herramienta para traducir acertijos de C al inglés.
* [C track on exercism.org](https://exercism.org/tracks/C): ejercicios de práctica adicionales.
* [Secure Coding Practices in C and C++](https://www.amazon.com/dp/0321822137): si quieres entender por qué otros recursos de C son tan inseguros.
* [*The C Programming Language*](https://www.amazon.com/dp/0131103628): el libro original sobre C, escrito por sus creadores. Demasiado anticuado para OSTEP, pero una buena lectura si logras encontrar una copia.

#### Arquitectura x86 y lenguaje ensamblador

Nand2Tetris ya ha introducido la mayoría de los conceptos que necesitarás para entender los sistemas y las arquitecturas de computadoras, así que ahora solo necesitas trasladar ese conocimiento a la arquitectura x86 real (de 32 bits).

La forma más fácil de hacerlo es viendo un subconjunto de las clases del curso *Computer Systems: A Programmer's Perspective* (o leyendo los capítulos correspondientes del [libro](https://www.amazon.com/dp/013409266X) del mismo nombre). Las clases que necesitarás son:

* [Machine-Level Programming I: Basics](https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=6e410255-3858-4e85-89c7-812c5845d197)
* [Machine-Level Programming II: Control](https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=fc93c499-8fc9-4652-9a99-711058054afb)
* [Machine-Level Programming III: Procedures](https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=2994255f-923b-4ad4-8fb4-5def7fd802cd)
* [Machine-Level Programming IV: Data](https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=03308c94-fc20-40d8-8978-1a9b81c344ed)
* [Machine-Level Programming V: Advanced Topics](https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=3f0bf9ca-d640-4798-b91a-73aed656a10a)
* [Linking](https://scs.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=0aef84fc-a53b-49c6-bb43-14cb2b175249)

Además, se recomienda hacer los siguientes labs. Estos labs están pensados para enseñarte a trabajar con ensamblador:
* **Bomb Lab**: [Guía](http://csapp.cs.cmu.edu/3e/bomblab.pdf), [material para estudio independiente](https://csapp.cs.cmu.edu/3e/bomb.tar).
  > Una "bomba binaria" es un programa que se proporciona a los estudiantes como un archivo de código objeto. Al ejecutarse, le pide al usuario que escriba 6 cadenas diferentes. Si alguna de ellas es incorrecta, la bomba "explota", imprimiendo un mensaje de error y registrando el evento en un servidor de calificación. Los estudiantes deben "desactivar" su propia bomba única mediante el desensamblado del programa y la ingeniería inversa, para determinar cuáles deben ser las 6 cadenas. El lab enseña a los estudiantes a entender el lenguaje ensamblador y también los obliga a aprender a usar un debugger. También es muy divertido. Un lab legendario entre los estudiantes de pregrado de la CMU.
* **Attack Lab**: [Guía](http://csapp.cs.cmu.edu/3e/attacklab.pdf), [material para estudio independiente](https://csapp.cs.cmu.edu/3e/target1.tar).
  > Se les da a los estudiantes un par de ejecutables binarios únicos de x86-64 generados de forma personalizada, llamados targets, que tienen errores de desbordamiento de búfer. Un target es vulnerable a ataques de inyección de código. El otro es vulnerable a ataques de programación orientada a retornos. Se les pide a los estudiantes modificar el comportamiento de los targets desarrollando exploits basados en inyección de código o en programación orientada a retornos. Este lab enseña a los estudiantes la disciplina de la pila y les enseña el peligro de escribir código vulnerable a ataques de desbordamiento de búfer. **Nota:** ejecuta los targets con la bandera -q para evitar que intenten contactar con un servidor de calificación inexistente.

Recursos adicionales (***opcionales***) incluyen:
* [CPU Registers x86](https://wiki.osdev.org/CPU_Registers_x86): bueno para buscar registros específicos.
* [*PC Assembly Language*](https://pdos.csail.mit.edu/6.828/2018/readings/pcasm-book.pdf): un corto libro sobre ensamblador x86.
* [GCC Inline Assembly HOWTO](https://www.ibiblio.org/gferg/ldp/GCC-Inline-Assembly-HOWTO.html): una guía para escribir código de ensamblador dentro de un programa en C.
* [*Intel 80386 Programmer's Reference Manual*](https://pdos.csail.mit.edu/6.828/2018/readings/i386.pdf): el recurso oficial (y enorme) de Intel.

#### xv6

No necesitas leer nada sobre xv6 hasta después de empezar OSTEP; de hecho, recomendamos dejar los proyectos relacionados con xv6 para cuando hayas terminado toda la sección de virtualización. Después de eso, necesitarás una guía que te acompañe a través del código fuente.

Los autores de xv6 proporcionan un [libro](https://pdos.csail.mit.edu/6.828/2018/xv6/book-rev11.pdf) que puedes leer junto con el código fuente. También hay una práctica [versión en PDF](https://pdos.csail.mit.edu/6.828/2018/xv6/xv6-rev11.pdf) del código, con números de línea y un índice para ver exactamente dónde se usa cada función o constante.

Sin embargo, ese libro pasa por alto muchos de los detalles del código que podrían parecerte desafiantes, incluidas las funciones avanzadas de C que se usan, las instrucciones específicas de la arquitectura x86 y los aspectos de concurrencia (si no has terminado esa sección de OSTEP antes de empezar los proyectos de xv6). Para resolver este problema, proporcionamos una [guía anotada de xv6](https://github.com/palladian1/xv6-annotated) que recorre todo el código de xv6 y lo analiza línea por línea, con explicaciones de las funciones de C, las especificaciones del hardware y las convenciones de x86 que se usan. Eso significa que es más larga que el libro oficial de xv6, así que no tienes que leerla toda (y probablemente puedas omitir las secciones opcionales, a menos que te interesen los controladores de dispositivos), pero puedes usarla como referencia si te rascas la cabeza con alguna parte del código.

[Aquí](https://github.com/YehudaShapira/xv6-explained) hay otra explicación en profundidad del código de xv6.

También [aquí](https://www.youtube.com/playlist?list=PLbtzT1TYeoMhTPzyTZboW_j7TPAnjv9XB) hay una excelente serie de videos que recorre gran parte del código de xv6.

#### Misceláneos

Necesitarás una noción general de cómo funcionan los Makefiles para poder usar el Makefile de xv6. [Este tutorial](https://makefiletutorial.com) cubre mucho más de lo que necesitas; solo lee las secciones "Getting Started" y "Targets" y vuelve al resto más adelante si necesitas buscar algo (aunque no debería ser necesario).

Recursos adicionales (opcionales) incluyen:
* [GCC Command Options](https://gcc.gnu.org/onlinedocs/gcc-11.1.0/gcc/Invoking-GCC.html#Invoking-GCC): una guía de los indicadores de línea de comandos para el compilador de C de GNU `gcc`.
* [Linker Scripts](https://sourceware.org/binutils/docs/ld/Scripts.html#Scripts): una guía para escribir scripts para el enlazador de GNU `ld`.
* [OSDev Wiki](https://wiki.osdev.org): un gran recurso para todo tipo de conceptos de sistemas operativos y detalles de implementación.
* [*Linux Kernel Development*](https://www.amazon.com/dp/0672329468): si quieres aplicar tu conocimiento de xv6 para contribuir al kernel de Linux, esta es una gran lectura después de OSTEP.
