def deep_reverse(L):
    """ assumes L is a list of lists whose elements are ints
    Mutates L such that it reverses its elements and also 
    reverses the order of the int elements in every element of L. 
    It does not return anything.
    """
    global L
    lis1 = []
    for i in range(len(L)-1,-1,-1):
        lis1.append(L[i])
    lis2 = []
    for i in range(len(lis1)):
        lis3 = []
        for j in range(len(lis1[i])-1,-1,-1):
            lis3.append(lis1[i][j])
        lis2.append(lis3)
    L = lis2


L = [[0, 1, 2], [1, 2, 3], [3, 2, 1], [10, -10, 100]]
print(deep_reverse(L))
print(L)
