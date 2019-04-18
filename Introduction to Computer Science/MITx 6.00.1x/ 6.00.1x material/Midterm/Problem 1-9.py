def f(x):
    a = []
    if x == 0:
        return f(x)
    while x > 0:
        a.append(x)
        print(x)
        f(x-1)

f(3)
