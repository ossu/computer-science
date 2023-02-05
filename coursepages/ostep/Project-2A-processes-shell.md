## Project 2A
### all thanks to [Palladian](https://github.com/palladian1/)

- [x] Interactive mode
- [x] Batch mode
- [x] exit
- [x] cd
- [x] path
- [x] Redirection
- [x] Parallel commands

### Tips

* Watch the video for discussion 3 on the Unix shell.
* Read chapter 5 in the OSTEP book.
* Start by implementing a shell that only does one thing: prints the prompt, then exits when you type `exit`. Then add `cd`, then `path`. Then implement the ability to execute commands with `execv`, then add batch mode, then redirection, and finally parallel commands.
* All of the test scripts will use batch mode and redirection, so until you've got those done, you'll have to test your shell manually.
* When you implement the `path` command, make sure you can handle both absolute and relative paths (i.e., `path tests` as well as `path /usr/bin`.
* It's tricky to get the errors down right, so just add error messages wherever it seems reasonable, then run the test scripts and modify your code until you're reporting errors exactly when you're supposed to. If you're running test `i`, you can check `tests/i.err` and `tests/i.rc` to see how many errors your shell should generate and compare to `tests-out/i.err` and `tests-out/i.rc`.
* If you run into issues with test 3 where the test expects something like `ls: cannot access ...` and your shell ouputs `/bin/ls: cannot access ...` or `/usr/bin/ls: cannot access ...`, try modifying your $PATH environment variable to start with `/bin`. If that doesn't work, just modify `tests/3.err` to match the output your system gives. You can't modify your system's output without messing with the implementation of `ls` and/or `execv`, so it's okay to skip this test as long as it's working in spirit.
* I had to edit `/tests/3.pre` to use `/bin/ls` due to how it's set up on my system, in order to pass all the tests. Alternatively you can add `export PATH="/bin:$PATH"` to your `.profile` or `.bashrc` file.

### Memory Management Traps and Pitfalls

* This assignment makes it really easy to create pointers to stack variables that will no longer exist once they're out of scope, thus causing a segmentation fault. Make sure that if you set a pointer to point to a string, that string is something you allocated on the heap, and not on the stack.

* That said, if you do use a string on the stack, you can copy it into a heap-allocated string using `strcpy()`, `strncpy()`, `strcat()`, and `strncat()`.

* Only use `strcpy()` and `strcat()` for fixed-size strings and make sure the buffer you're copying into has enough space to hold the string, plus an extra character for `\0`.

* For `strncpy()` and `strncat()`, make sure `n` is large enough to fit the `\0` terminator, or add it manually.

* Watch out for use-after-frees, especially in the implementation of `path`.

* Make sure you free any strings from `getline()` and `strdup()`, but watch out for double-frees, e.g. don't free a substring of a string you already freed.

* Avoid the C library function `strtok()`; it's not thread-safe. Use `strsep()` instead.

* When you use `strsep()`, make sure you keep a copy of the original pointer to the string around so that you can free it later, because `strsep()` will modify the pointer, so if you free that later on, you'll corrupt the page table.

* After calling `strsep(&buf, delim)`, check whether `buf` is `NULL` before dereferencing it.
* General C coding practice: if you allocate memory for a data structure inside a function, you should free it in the same function. If you allocate memory in a dedicated `create_xxx` function, you should have a corresponding `destroy_xxx` function. That way, you always allocate and free memory at the same function depth, which makes it easier to avoid memory errors.
* After every call to `malloc`, `calloc`, or `realloc`, check whether the result is `NULL`.
* Use `calloc` instead of `malloc` if you're creating an array of pointers to avoid creating pointers to garbage values.
* in `update_path` I had to fix that issue where most of the tests do `path /bin /usr/bin`, but one of them did `path tests`. So i just assumed that if your path starts with a slash, it's an absolute path and you should copy it in as is; if it doesn't, it's a relative path and you should add a ./ at the beginning.
