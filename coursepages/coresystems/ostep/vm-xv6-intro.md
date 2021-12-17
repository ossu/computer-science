## Intro To xv6 Virtual Memory

### WARNING:

***The project doesn't match the currently available xv6 source code, which already has this project implemented in it!***

@palladian tracked down a different xv6 source from the Github page of a U of Wisconsin student. We had to edit the `Makefile` to find the QEMU executable correctly. We added `null.c` to the `user` folder (also edited `makefile.mk` there), which demonstrates the lack of memory safety.

Start with the code in `vm-xv6-intro.zip`. Extract it and run `make clean` and `make qemu-nox`. Then inside the xv6 system run `null` to see the lack of safety! If you want to compare the results of `null` with the actual machine code, you can run `objdump -d user/null.o`.

You might have to manually run `make clean` and `make qemu-nox` every time you make a change to the code.
