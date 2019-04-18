def convert_to_mandarin(us_num):
    '''
    us_num, a string representing a US number 0 to 99
    returns the string mandarin representation of us_num
    '''
    trans = {'0':'ling', '1':'yi', '2':'er', '3':'san', '4': 'si',
          '5':'wu', '6':'liu', '7':'qi', '8':'ba', '9':'jiu', '10': 'shi'}
    n = int(us_num)
    if n < 11:
        return trans[us_num]
    elif n < 20:
        return "shi " + trans[str(n%10)]
    elif n < 100:
        if n%10 != 0:
            return trans[str(n//10)] + " shi " + trans[str(n%10)]
        else:
            return trans[str(n//10)] + " shi"

print(convert_to_mandarin('36'))
print(convert_to_mandarin('20'))
print(convert_to_mandarin('16'))
