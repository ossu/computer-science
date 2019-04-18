def main():
    i = 6.0
    while i > 5:
        print("x =",round(i,1),"       f(x) =",round(fact(i),1),"       f'(x) =",round((fact(i)-fact(i-.025))/.025,1) )
        i -= .025
def fact(n):
    if n <= 1:
        return 1
    else:
        return n*fact(n-1)
main()
