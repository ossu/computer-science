def longest_run(L):
    """
    Assumes L is a list of integers containing at least 2 elements.
    Finds the longest run of numbers in L, where the longest run can
    either be monotonically increasing or monotonically decreasing.
    In case of a tie for the longest run, choose the longest run
    that occurs first.
    Does not modify the list.
    Returns the sum of the longest run.
    """
    first = mono_inc(L)
    second = mono_dec(L)
    result = 0
    if len(first) > len(second) :
        for i in first:
            result += i
    elif len(first) == len(second) :
        for i in L:
            if sum(first) == sum(second):
                for r in first:
                    result += r
                break
            if i in first and not i in second:
                for j in first:
                    result += j
                break
            elif i in second and not i in first:
                for k in second:
                    result += k
                break
 
    elif len(first) < len(second):
        for i in second:
            result += i
    return result
 
 
def mono_inc(L):
    current_set = L[:]
    temp_set = [current_set[0]]
    inc = []
 
    for i in range(len(current_set)-1):
        if current_set[i] <= current_set[i+1]:
            temp_set.append(current_set[i+1])
            if len(temp_set) > len(inc):
                inc = temp_set[:]
        elif current_set[i] > current_set[i+1]:
            temp_set = [current_set[i+1]]
    return inc
 
 
def mono_dec(L):
    current_set = L[:]
    temp_set = [current_set[0]]
    dec = []
    for i in range(len(current_set)-1):
        if current_set[i] >= current_set[i+1]:
            temp_set.append(current_set[i+1])
            if len(temp_set) > len(dec):
                dec = temp_set[:]
        elif current_set[i] < current_set[i+1]:
            temp_set = [current_set[i+1]]
    return dec
