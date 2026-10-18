## Proyecto 2A
### agradecimientos a [Palladian](https://github.com/palladian1/)

- [x] Modo interactivo
- [x] Modo por lotes
- [x] exit
- [x] cd
- [x] path
- [x] Redirección
- [x] Comandos en paralelo

### Consejos

* Mira el video de la discusión 3 sobre el shell de Unix.
* Lee el capítulo 5 del libro de OSTEP.
* Empieza implementando un shell que solo haga una cosa: imprime el prompt y luego sale cuando escribes `exit`. Luego agrega `cd`, luego `path`. Luego implementa la capacidad de ejecutar comandos con `execv`, luego agrega el modo por lotes, luego la redirección y finalmente los comandos en paralelo.
* Todos los scripts de pruebas usarán el modo por lotes y la redirección, así que, hasta que los tengas listos, tendrás que probar tu shell manualmente.
* Cuando implementes el comando `path`, asegúrate de poder manejar tanto rutas absolutas como relativas (es decir, `path tests` así como `path /usr/bin`.
* Es difícil acertar con los mensajes de error, así que simplemente agrega mensajes de error donde parezca razonable, luego ejecuta los scripts de pruebas y modifica tu código hasta que reportes los errores exactamente cuando debas. Si estás ejecutando la prueba `i`, puedes revisar `tests/i.err` y `tests/i.rc` para ver cuántos errores debe generar tu shell y compararlos con `tests-out/i.err` y `tests-out/i.rc`.
* Si encuentras problemas con la prueba 3, donde la prueba espera algo como `ls: cannot access ...` y tu shell produce `/bin/ls: cannot access ...` o `/usr/bin/ls: cannot access ...`, intenta modificar tu variable de entorno $PATH para que empiece con `/bin`. Si eso no funciona, simplemente modifica `tests/3.err` para que coincida con la salida que da tu sistema. No puedes modificar la salida de tu sistema sin alterar la implementación de `ls` y/o `execv`, así que está bien omitir esta prueba siempre que funcione como corresponde.
* Tuve que editar `/tests/3.pre` para usar `/bin/ls` debido a cómo está configurado en mi sistema, a fin de pasar todas las pruebas. Alternativamente, puedes agregar `export PATH="/bin:$PATH"` a tu archivo `.profile` o `.bashrc`.

### Trampas y peligros de la gestión de memoria

* Esta tarea hace que sea muy fácil crear punteros a variables de la pila que dejarán de existir una vez que salgan de su ámbito, causando así una violación de segmento. Asegúrate de que, si asignas un puntero para que apunte a una cadena, esa cadena sea algo que asignaste en el heap y no en la pila.

* Dicho esto, si usas una cadena en la pila, puedes copiarla en una cadena asignada en el heap usando `strcpy()`, `strncpy()`, `strcat()` y `strncat()`.

* Usa `strcpy()` y `strcat()` solo con cadenas de tamaño fijo y asegúrate de que el buffer al que copias tenga suficiente espacio para contener la cadena, más un carácter extra para `\0`.

* Para `strncpy()` y `strncat()`, asegúrate de que `n` sea lo suficientemente grande para alojar el terminador `\0`, o agrégalo manualmente.

* Cuidado con los use-after-free, especialmente en la implementación de `path`.

* Asegúrate de liberar cualquier cadena proveniente de `getline()` y `strdup()`, pero cuidado con los dobles liberados; por ejemplo, no liberes una subcadena de una cadena que ya liberaste.

* Evita la función de la biblioteca de C `strtok()`; no es segura entre hilos. Usa `strsep()` en su lugar.

* Cuando uses `strsep()`, asegúrate de conservar una copia del puntero original a la cadena para poder liberarla más tarde, porque `strsep()` modificará el puntero; si lo liberas después, corromperás la tabla de páginas.

* Después de llamar a `strsep(&buf, delim)`, comprueba si `buf` es `NULL` antes de desreferenciarlo.
* Práctica general de codificación en C: si asignas memoria para una estructura de datos dentro de una función, deberías liberarla en la misma función. Si asignas memoria en una función dedicada `create_xxx`, deberías tener una función `destroy_xxx` correspondiente. Así, siempre asignas y liberas memoria a la misma profundidad de función, lo que facilita evitar errores de memoria.
* Después de cada llamada a `malloc`, `calloc` o `realloc`, comprueba si el resultado es `NULL`.
* Usa `calloc` en lugar de `malloc` si estás creando un arreglo de punteros, para evitar crear punteros a valores basura.
* en `update_path` tuve que corregir ese problema donde la mayoría de las pruebas hacen `path /bin /usr/bin`, pero una de ellas hacía `path tests`. Así que simplemente asumí que si tu path empieza con una barra es una ruta absoluta y debes copiarla tal como está; si no lo es, es una ruta relativa y debes agregar un ./ al principio.
