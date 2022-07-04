## Project 1B

### all thanks to [palladian](https://github.com/palladian1)

### Linux Installation

* Make sure you have a compatible compiler toolchain; if you're on Linux, gcc should work perfectly.
* Install qemu-system-x86 (may be called qemu-system-i386 or qemu-system-x86_64; note that on some distros, qemu is the wrong package).
* Install Perl.
* Install gawk.
* Install expect.
* Make a src/ directory in the same directory as the project's test script.
* Clone xv6 github repo and copy the source code in to your src/ directory.
* Inside src/, run `make qemu-nox` to test whether xv6 is working. Exit xv6 with `Ctrl-a x`; if you forget this, you can also kill the qemu process. It's worth checking `top` or `htop` to make sure qemu isn't running anymore; sometimes it can keep going after you exit and consume a lot of resources.
* Modify the Makefile to set `CPUS := 1`.
* Run `make qemu-nox` again to test that xv6 still works.

### Instructions

* Your task is to create a new system call for xv6, `getreadcount()`, that will return the number of `read` syscalls that have previously taken place. Note that the count should be a global count, not a per-process count.

### Suggested Approach

* Download the xv6 source code PDF (it's better organized there than in the code you downloaded). Read the table of contents to understand how sheets, pages, and lines are numbered. Then glance at the cross-references after that so you know how to find parts of the code if you need to.
* Take a (very) quick look at the portions of the xv6 source code listed under `processes` and `system calls` on the table of contents, as well as `usys.S` in the `user-level` section. Don't worry about understanding it yet; you just need to see where each file is in the PDF so that you can follow along with the discussion video later, since the professor's code has a different directory layout than yours will.
* Watch the video for discussion 2 on Project P1B, and follow along with your PDF copy of the xv6 code. Annotate it as the professor explains what each part does.
* Read the background section linked on the project's Github page, annotating the xv6 code PDF.
* Read through the xv6 PDF one more time, this time to get a general understanding of the `processes` and `system calls` sections, as well as `usys.S` and `user.h` (NOTE: the last one isn't included in the xv6 PDF, so you'll have to look at the actual code you downloaded). Don't worry about understanding every last line of code, just make sure you know where system calls are defined, how they're called, etc.
* Modify the xv6 source code to add the new `getreadcount()` syscall. You will need to modify several files; I suggest marking your modifications with `// OSTEP project` to make it easy to find them later for debugging.
* There is one other place you'll have to add code, which isn't included in the xv6 PDF: `user.h`.
* Once you're done, run the test script. Test 1 runs a function that will make several `read` calls, then calls `getreadcount`. In order for your code to work, you must correctly keep track of the total number of `read` calls made by all processes.
* If your code passes test 1, congratulations! You're done for now. Don't worry about test 2 until after you've watched the lectures on concurrency.
* If your code didn't pass test 1, you can compare the expected output in `tests/1.out` with your test's actual output in `tests-out/1.out`. You can also look at `tests-out/1.err` to check for any error messages.
* You can also test your code by loading up xv6 in your terminal with `make qemu-nox`. Type `ls` to see all files; you should see `test_1` and `test_2`. Run test 1 with `./test_1` to see what it prints out; you can compare that manually with the expected output.
* Once you've watched the lectures on threads, concurrency, and locks: test 2 checks whether your implementation of `getreadcount` is thread-safe. It probably wasn't before, so in order to fix that, you'll have to add a lock. Then you can run the test script again and check that your code now passes both tests.
