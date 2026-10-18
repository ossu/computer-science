## agradecimientos a [palladian](https://github.com/palladian1)

### Consejos generales

* Lee el capítulo 9 del libro de OSTEP y mira el video de la discusión 5. Los planificadores de tipo lotería no se discuten en las clases, así que de verdad tienes que leer el libro para este.

* En general, no puedes usar funciones de la biblioteca estándar de C dentro del kernel, porque el kernel tiene que inicializarse antes de poder ejecutar los binarios de la biblioteca.

* El kernel de xv6 tiene una "versión de kernel" de `printf`; recibe un argumento entero adicional que le indica si imprimir a `stdout` o a `stderr`. Ten en cuenta que solo puede manejar cadenas de formato básicas como `"%d"` y no otras más complejas como `"%6.3g"`; puedes lidiar con esto agregando espacios manualmente. También tiene otra función similar, `cprintf`.

* Si quieres usar otras funciones de la biblioteca que no están disponibles dentro del kernel (generadores de números pseudoaleatorios), puedes ver cómo están implementadas esas funciones en el libro de P.J. Plauger, The Standard C Library, y luego implementarlas tú mismo.

  ### Implementación

  * Tendrás que modificar los mismos archivos que modificaste en el Proyecto 1b para agregar las dos nuevas system calls.
  * Para entender cómo se crean los procesos, recuerda que inician en el estado `EMBRYO` antes de convertirse en `RUNNABLE`--tendrás que encontrar dónde ocurre eso.
  * Las system calls siempre tienen el tipo de argumento `void`, así que echa un vistazo a cómo system calls como `kill` y `read` logran sortear esa limitación y obtener argumentos (como enteros y punteros) desde el espacio de usuario. Podrías tener que retroceder unos pasos en la cadena que las ejecuta.
  * Asegúrate de incluir `types.h` y `defs.h` dondequiera que necesites acceder a código de otras partes del kernel.
  * Para crear el comando `ps` de xv6, mira cómo están implementados `cat`, `ls` y `ln`. Asegúrate de modificar el Makefile para incluir el código fuente de tu comando `ps`.


## ¡Spoilers a continuación!

### Recorrido de la solución

- Empieza con una copia nueva del código fuente de `xv6`.

- `argint` y `argptr` son funciones importantes. Así que los `syscall` no reciben argumentos, pero en la realidad, en el código de usuario quieres pasarles argumentos.

- La forma de hacerlo es que el kernel llamará al `syscall`, digamos, `sys_kill()` sin argumentos; luego `sys_kill` usará `argint()` para obtener los argumentos de la pila de llamadas y luego los pasará a una función `kill(int pid)`.

- Puedes ver que hay un montón de declaraciones de funciones `extern int sys_whatever` debajo de eso; eso significa que estas funciones están definidas en otro archivo y deberían importarse desde ahí como punteros a funciones.

- Y estas funciones `sys_whatever` son básicamente solo envoltorios del `syscall` real, que no tiene el `sys_` al principio. Así que necesitas agregar `sys_settickets` y `sys_getpinfo` a esa lista de declaraciones de funciones.

- Luego hay un arreglo de punteros a funciones; usa esa manera antigua de C de inicializar arreglos donde puedes hacer `int arr[] = { [0] 5, [1] 7}`.

- Y los nombres dentro de los corchetes `SYS_fork`, etc., están definidos como macros del preprocesador en otro archivo de cabecera, `syscall.h`.

- Así que necesitas agregar dos entradas más en el arreglo con punteros a funciones hacia `sys_settickets` y `sys_getpinfo`, y luego necesitas definir `SYS_settickets` y `SYS_getpinfo` en el archivo de cabecera correspondiente.

- Luego, todas estas funciones envoltorio `sys_` están definidas en `sysproc.c`.

- Ahí necesitas crear `int sys_settickets(void)` y `int sys_getpinfo(void)`.

- La función `settickets` real necesitará un argumento int, así que necesitas usar `argint` ahí para obtenerlo de la pila de llamadas y pasárselo a `settickets`; de manera similar, `getpinfo` necesitará un puntero, así que usarás `argptr`.

- Además, hay una condición extra en la sentencia if para `sys_settickets`; eso es porque no se te permite usar un número de tickets menor que 1.

- Luego hay algo de código de ensamblador que necesita ejecutarse para cada una de las system calls; afortunadamente, es solo una macro preescrita, así que no tienes que escribir ensamblador. Eso está en `usys.S`.

- Así que solo agregas dos líneas al final para crear macros para `SYSCALL(settickets)` y `SYSCALL(getpinfo)`

- Última parte para los `syscalls`: necesitas declararlos en un archivo de cabecera para que el código de usuario pueda llamarlos. Eso está en `user.h`.

- Así que `struct pstat` estará correctamente definido en `pstat.h`, pero necesitas declararlo también en `user.h` para que el código de usuario no se queje cuando lo vea.

- Básicamente, cualquier código de usuario que use `syscalls` o funciones de la biblioteca estándar de C (en realidad, de `xv6`) tendrá que incluir `user.h`.

- Así que, hasta ahora, eso es todo en cuanto a las dos system calls para el sistema operativo; ahora solo tenemos que implementarlas realmente con las funciones regulares `settickets` y `getpinfo`, luego implementar el scheduler y el programa `ps`.

- `pstat.h` no es para el scheduler, sino para el programa `ps`, que funcionará de manera algo similar al `ps` de Linux. `pstat.h` es solo para definir el `struct pstat`, pero no hay archivo `.c` que lo acompañe.

- El scheduler funcionará asignando 1 ticket por defecto a cada proceso cuando este se cree; luego los procesos pueden configurar sus propios tickets usando la system call `settickets`.

- primero necesitamos asegurarnos de que cada proceso rastree sus propios tickets, luego necesitamos asignar un valor predeterminado de 1 ticket al crearlos, luego necesitamos escribir `settickets`.

- la primera parte está en `proc.h`: los procesos se representan como un `struct proc`, así que agregamos un nuevo miembro para `int tickets`.

- el miembro `int ticks` es para `ps`; volveré a eso.

- Otra cosa a notar en `proc.h` es el `enum procstate`: ahí puedes ver todos los posibles estados del proceso. `EMBRYO` significa que está en proceso de creación; así que lo que hice fue hacer `grep` de `EMBRYO` para encontrar dónde se creaba el proceso, con el fin de establecer los tickets predeterminados en 1. Resulta que está en `proc.c`.

- Dentro de `proc.c` hay una función `allocproc`, que inicializa un proceso.

- Hay una tabla de procesos llamada `ptable`, y `allocproc` la recorre para encontrar un proceso sin usar.

- Luego, cuando lo encuentra, pasa a crearlo; ahí agregué `p->tickets = 1;`.

- bien, el siguiente cambio es para cumplir uno de los requisitos: los procesos hijos necesitan heredar el número de tickets de su proceso padre.

- Así que los procesos hijos se crean con `fork`, que está en el mismo archivo.

- En `fork`, `curproc` es el proceso actual, y `np` es el nuevo proceso.

- Así que establecí `np->tickets = curproc->tickets`.

- El scheduler necesita generar un número pseudoaleatorio; luego debería iterar a través de la tabla de procesos con un contador inicializado en 0, sumando al contador el número de tickets de cada proceso. una vez que el contador sea mayor que el número pseudoaleatorio, se detiene y ejecuta ese proceso.

- Así que terminé buscando en The Standard C Library de P.J. Plauger, que es simplemente un libro enorme con todo el código fuente de la biblioteca de C con comentarios. Es bastante bueno; no sé si todavía está escrito de esa manera, porque el libro es de los 80.

- Así que simplemente implementé las funciones `rand` y `srand` de C. `srand` establece una semilla aleatoria (no tan aleatoria, como verás más adelante), y luego `rand` la convierte en un entero pseudoaleatorio.

- Hay un montón de magia de tipos ahí, con cambios de ida y vuelta entre enteros y enteros sin signo; eso es para evitar el desbordamiento de enteros con signo, que causa comportamiento indefinido. El desbordamiento de enteros sin signo sí está bien.

- Solo hice un cambio para hacerlo más rápido, que fue escribir `& 32767` en lugar de `% 32768`.

- Verás la semilla "aleatoria" que usé: el número de `ticks`, que creo que cuenta el número de interrupciones de temporizador hasta el momento.

- Que no es nada aleatoria en absoluto, ya que la primera vez que se ejecute este programa será 0, luego 1, luego 2, etc.

- Hay algunas líneas sobre contar `ticks`; eso era para `ps`, no para el scheduler.

- El cambio principal para convertirlo en un scheduler de lotería es la variable contador.

- Y agregar un bucle for para contar el número total de tickets que se han distribuido.

- Luego, al final mismo de este archivo está la implementación de `settickets` y `getpinfo`.

- Después de inicializar `counter` y `totaltickets`, hay un bucle for que cuenta el número total de tickets que se han repartido a los procesos.

- Luego obtenemos el ticket ganador.

- Analicemos primero el código fuente original. Primero adquieres el lock. Lo liberarás al final. Pero en medio, tienes un bucle for que itera sobre todos los procesos de `ptable`.

- Específicamente, itera solo sobre los procesos en estado `RUNNABLE`; si un proceso no es `RUNNABLE`, simplemente hace `continue` para pasar al siguiente. (Esto es para el mecanismo de planificación round-robin que ya existe en el código.)

- Ahora va a cambiar al primer proceso `RUNNABLE` que encuentre. Es decir, cambiará a ejecutarlo.

- Primero, `c` representa la CPU actual. Así que establece la CPU actual para que ejecute el proceso que encontró con `c->proc = p;`.

- Luego llama a esta función, `switchuvm(p)`, que configura el espacio de direcciones de memoria virtual para `p`. Luego establece el estado del proceso en `RUNNING`.

- Y luego `swtch` es donde ocurre la magia: ese intercambia el contenido de los registros del sistema operativo y del scheduler por el contenido de los registros del proceso `p` guardado en memoria.

- Tan pronto como `swtch` se ejecute, la CPU continuará ejecutando instrucciones, pero ahora son las instrucciones del proceso. Así que esta función del scheduler simplemente se queda ahí suspendida.

- Eventualmente, cuando se produzca una interrupción de temporizador, el procesador usará otra llamada `swtch` pero con los argumentos invertidos, para intercambiar el contenido de los registros del scheduler desde la memoria hacia los registros de la CPU y guardar el contenido de los registros del proceso. En ese punto, la ejecución continuará exactamente en este mismo punto.

- Ahora `switchkvm` configurará el espacio de direcciones de memoria virtual del kernel.

- Estas 5 líneas son el cambio de contexto:

  ```c
  c->proc = p;
  switchuvm(p);
  p->state = RUNNING;

  swtch(&(c->scheduler), p->context);
  switchkvm();
  ```

- Luego pasamos a la siguiente iteración del bucle for interno, que encuentra el siguiente proceso `RUNNABLE` y repite.

- Solo cuando hemos ejecutado todos los procesos `RUNNABLE` salimos del bucle for interno y liberamos el lock.

- El código fuente original está estructurado así (esto es pseudocódigo):

  ```python
  while (1) {
    iterate over processes:
      if not runnable:
        continue
      run it
  ```

- El código nuevo está estructurado así (esto es pseudocódigo):

  ```python
  while (1) {
    count the total tickets allotted to all processes // one for loop here
    get the winning ticket number
    iterate over processes: // another for loop here
      if not runnable:
        continue
      add its tickets to counter
      if counter <= winning ticket number:
        continue
      run it
  ```

- Ignoramos los tickets de los procesos que no están en estado RUNNABLE.

- Así que los tickets no están numerados; cada proceso solo tiene una cantidad fija de tickets, y simplemente contamos hasta que hemos pasado `n` tickets, donde `n` es el ganador.

- Por ejemplo, si el proceso A tiene 5 tickets, el proceso B tiene 7 y el proceso C tiene 2. Si el número ganador es 3, entonces correría A; si es 8, correría B; si es 12, correría C.

- Un ganador entre 0-4 sería A, entre 5-11 sería B, y entre 12-13 sería C.

- Así que `settickets` es bastante básico: simplemente adquieres un lock, estableces los tickets para el proceso y liberas el lock.

- Para `getpinfo` básicamente funciona así:

- `p` es un puntero a un `struct pstat`, tal como se define en `pstat.h`. Cada uno de sus miembros es un arreglo, con una entrada por proceso.

- Comprueba si hay un puntero nulo.

- Itera sobre la tabla de procesos y establece `proc_i` en el i-ésimo proceso.

- Establece la i-ésima entrada de cada miembro de `p` con el valor de este proceso.

- Un último detalle de libro mayor: necesitamos agregar las declaraciones de `struct pstat` y de las system calls `settickets` y `getpinfo` en `defs.h`.

- Y luego el último archivo es `ps.c`, que implementa el programa `ps`, similar al `ps` de Linux. simplemente llama a `getpinfo` para llenar un `struct pstat` y luego imprime la información de cada proceso en uso.

- Y luego solo modificas el Makefile para incluir `ps.c` en la compilación, ¡y listo!

- Ah, y esta es la razón por la que necesitábamos los ticks en el scheduler: `ps` imprimirá cuánto tiempo ha corrido cada proceso.

- Así que necesita cronometrar el número de ticks que realmente ejecutó.

- FINALMENTE ejecuta `make qemu` en el directorio `/src` para asegurarte de que todo funciona.
