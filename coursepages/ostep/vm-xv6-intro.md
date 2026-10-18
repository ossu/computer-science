Agradecimientos a [palladian](https://github.com/palladian1)

## Introducción a la memoria virtual de xv6

### ADVERTENCIA:

***¡El proyecto no coincide con el código fuente de xv6 disponible actualmente, el cual ya tiene este proyecto implementado!***

[palladian](https://github.com/palladian1) rastreó un código fuente de xv6 distinto desde la página de Github de un estudiante de la University of Wisconsin. Tuvimos que editar el `Makefile` para que encontrara correctamente el ejecutable de QEMU. Agregamos `null.c` a la carpeta `user` (también editamos `makefile.mk` allí), que demuestra la falta de seguridad de memoria.

Empieza con el código en [`start.zip`](https://github.com/spamegg1/reviews/raw/master/courses/OSTEP/ostep-projects/vm-xv6-intro/start.zip). Extráelo y ejecuta `make clean` y `make qemu-nox`. Luego, dentro del sistema xv6, ejecuta `null` para ver la falta de seguridad. Si quieres comparar los resultados de `null` con el código máquina real, puedes ejecutar `objdump -d user/null.o`.

Puede que tengas que ejecutar manualmente `make clean` y `make qemu-nox` cada vez que hagas un cambio en el código.
