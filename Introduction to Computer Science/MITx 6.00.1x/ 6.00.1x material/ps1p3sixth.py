s = 'tpzcsihlefpvrl'
ind0 = '' #master string
lis0 = ['']
counter = 0
for i in range(len(s)-1):
    ind1 = ' ' #inner string
    if (ord(s[i]) <= ord(s[i+1])):
        ind1 = s[i]
        lis0[counter] += s[i]
    else:
        counter += 1
        lis0.append('')
    ind0 += ind1
if ord(s[len(s)-2]) <= ord(s[len(s)-1]):
    ind0 += s[len(s)-1]
else:
    ind0 += ' '


sub = ''
for i in range(len(lis0)):
    if len(lis0[i]) >= len(sub):
        sub = lis0[i]

index = 0
for i in range(len(s) - len(sub) + 1):
    if ( s[i:i+len(sub)] == sub ):
        index = i
print("Longest substring in alphabetical order is:", s[index:index+len(sub)+1])
