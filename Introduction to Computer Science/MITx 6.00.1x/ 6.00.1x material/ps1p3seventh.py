ind0 = '' #master string
lis0 = [''] #paritioning substrings
lis00 = [] #master matrix of indices of lis0 alongside length of partitioned strings
counter = 0
for i in range(len(s)-1):
    ind1 = ' ' #inner string
    if (ord(s[i]) <= ord(s[i+1])):
        ind1 = s[i]
        lis0[counter] += s[i]
    else:
        lis00.append([counter,len(lis0[counter])])
        counter += 1
        lis0.append('')
    ind0 += ind1
if ord(s[len(s)-2]) <= ord(s[len(s)-1]):
    ind0 += s[len(s)-1]
else:
    ind0 += ' '

ans = 1
for i in range(len(lis00)):
    if lis00[i][1] >= ans:
        ans = lis00[i][1]
        
value = 0        
for i in range(len(lis00)):
    if ans == lis00[i][1]:
        value = lis00[i][0]
        break
sub = lis0[value]

index = 0
for i in range(len(s) - len(sub) + 1):
    if ( s[i:i+len(sub)] == sub ):
        index = i
print("Longest substring in alphabetical order is:", s[index:index+len(sub)+1])
