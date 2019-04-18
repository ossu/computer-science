def isIn(char, aStr):
    '''
    char: a single character
    aStr: an alphabetized string
    
    returns: True if char is in aStr; False otherwise
    '''
    L = len(aStr)
    if L < 2:
        if char == aStr:
            return True
        else:
            return False
    elif L%2 == 0:
        if char < aStr[L//2]:
            aStr = aStr[:L//2]
            return isIn(char, aStr)
        elif char > aStr[L//2]:
            aStr = aStr[L//2:]
            return isIn(char, aStr)
    else:
        if char < aStr[L//2+1]:
            aStr = aStr[:L//2+1]
            return isIn(char, aStr)
        elif char > aStr[L//2]:
            aStr = aStr[L//2:]
            return isIn(char, aStr)
        
print(isIn('a', '')) yu0-]\
