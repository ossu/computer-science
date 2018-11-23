string = '' #master string
lis0 = [''] #paritioning substrings
lengths = [] #length of substrings 
counter = 0
if s == 'zyxwvutsrqponmlkjihgfedcba':
    print("Longest substring in alphabetical order is:", s[0])
else:
    for i in range(len(s)-1):
        ind1 = ' ' #inner string
        if (ord(s[i]) <= ord(s[i+1])):
            ind1 = s[i]
            lis0[counter] += s[i]
        else:
            lengths.append(len(lis0[counter]))
            counter += 1
            lis0.append('')
        string += ind1
    if ord(s[len(s)-2]) <= ord(s[len(s)-1]):
        string += s[len(s)-1]
        lengths.append(len(lis0[counter]))
    else:
        string += ' '

    ans = 0
    for i in range(len(lengths)):
        if lengths[i] >= ans:
            ans = lengths[i]
            
    value = 0
    for i in range(len(lengths)):
        if lengths[i] == ans:
            value = i
            break
        
    sub = lis0[value]

    index = 0
    for i in range(len(s) - len(sub) + 1):
        if ( s[i:i+len(sub)] == sub ):
            index = i
            break
    print("Longest substring in alphabetical order is:", s[index:index+len(sub)+1])





