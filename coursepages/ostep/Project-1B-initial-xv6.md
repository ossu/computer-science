## Proyecto 1B

### agradecimientos a [palladian](https://github.com/palladian1)

### Instalación en Linux

* Asegúrate de tener un conjunto de herramientas de compilación compatible; si estás en Linux, gcc debería funcionar perfectamente.
* Instala qemu-system-x86 (puede llamarse qemu-system-i386 o qemu-system-x86_64; ten en cuenta que en algunas distribuciones qemu es el paquete equivocado).
* Instala Perl.
* Instala gawk.
* Instala expect.
* Crea un directorio src/ en el mismo directorio que el script de pruebas del proyecto.
* Clona el repositorio de Github de xv6 y copia el código fuente en tu directorio src/.
* Dentro de src/, ejecuta `make qemu-nox` para probar si xv6 está funcionando. Sal de xv6 con `Ctrl-a x`; si olvidas esto, también puedes matar el proceso qemu. Vale la pena revisar `top` o `htop` para asegurarte de que qemu ya no esté corriendo; a veces puede seguir activo después de que salgas y consumir muchos recursos.
* Modifica el Makefile para establecer `CPUS := 1`.
* Ejecuta `make qemu-nox` de nuevo para probar que xv6 sigue funcionando.

### Instrucciones

* Tu tarea es crear una nueva system call para xv6, `getreadcount()`, que devuelva el número de syscalls `read` que hayan tenido lugar previamente. Ten en cuenta que el conteo debe ser global, no por proceso.

### Enfoque sugerido

* Descarga el PDF del código fuente de xv6 (está mejor organizado ahí que en el código que descargaste). Lee el índice para entender cómo se numeran las hojas, páginas y líneas. Luego echa un vistazo a las referencias cruzadas que siguen, para que sepas cómo encontrar partes del código si lo necesitas.
* Echa un vistazo (muy) rápido a las porciones del código fuente de xv6 listadas bajo `processes` y `system calls` en el índice, así como `usys.S` en la sección `user-level`. No te preocupes por entenderlo aún; solo necesitas ver dónde está cada archivo en el PDF para poder seguir el video de discusión más adelante, ya que el código del profesor tiene una distribución de directorios diferente a la tuya.
* Mira el video de la discusión 2 sobre el Proyecto P1B, y sigue tu copia en PDF del código xv6. Anótala a medida que el profesor explica qué hace cada parte.
* Lee la sección de antecedentes enlazada en la página Github del proyecto, anotando el PDF del código xv6.
* Lee el PDF de xv6 una vez más, esta vez para obtener una comprensión general de las secciones `processes` y `system calls`, así como `usys.S` y `user.h` (NOTA: el último no está incluido en el PDF de xv6, así que tendrás que mirar el código real que descargaste). No te preocupes por entender cada última línea de código, solo asegúrate de saber dónde se definen las system calls, cómo se invocan, etc.
* Modifica el código fuente de xv6 para agregar la nueva syscall `getreadcount()`. Necesitarás modificar varios archivos; sugiero marcar tus modificaciones con `// OSTEP project` para que sea fácil encontrarlas más adelante al depurar.
* Hay un lugar más donde tendrás que agregar código, que no está incluido en el PDF de xv6: `user.h`.
* Cuando termines, ejecuta el script de pruebas. La prueba 1 ejecuta una función que hará varias llamadas `read`, y luego llama a `getreadcount`. Para que tu código funcione, debes llevar correctamente la cuenta del número total de llamadas `read` hechas por todos los procesos.
* Si tu código pasa la prueba 1, ¡felicidades! Has terminado por ahora. No te preocupes por la prueba 2 hasta después de haber visto las clases sobre concurrencia.
* Si tu código no pasó la prueba 1, puedes comparar la salida esperada en `tests/1.out` con la salida real de tu prueba en `tests-out/1.out`. También puedes ver `tests-out/1.err` para revisar si hay mensajes de error.
* También puedes probar tu código cargando xv6 en tu terminal con `make qemu-nox`. Escribe `ls` para ver todos los archivos; deberías ver `test_1` y `test_2`. Ejecuta la prueba 1 con `./test_1` para ver qué imprime; puedes compararla manualmente con la salida esperada.
* Cuando hayas visto las clases sobre hilos, concurrencia y locks: la prueba 2 verifica si tu implementación de `getreadcount` es segura entre hilos. Probablemente no lo era antes, así que, para corregirlo, tendrás que agregar un lock. Luego puedes ejecutar el script de pruebas de nuevo y comprobar que tu código ahora pasa ambas pruebas.
