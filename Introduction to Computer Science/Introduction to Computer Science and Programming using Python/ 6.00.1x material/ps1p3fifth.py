s = 'azcbobobegghakl'
ind0 = '' #master string
for i in range(len(s)-1):
    ind1 = ' ' #inner string
    if (ord(s[i]) <= ord(s[i+1])):
        ind1 = s[i] 
    ind0 += ind1
if ord(s[len(s)-2]) <= ord(s[len(s)-1]):
    ind0 += s[len(s)-1]
else:
    ind0 += ' '
print(ind0)

print(max(['a','b','b','begg','aklx']))




















##ind2 = []
##if (ind0[0] != ' ') and (ind0[1] == ' '):
##    ind2.append(0)
##for j in range(1, len(ind0)-1):
##    if (ind0[j] != ' ') and ((ind0[j-1] == ' ') or (ind0[j+1] == ' ')):
##        ind2.append(j)
##if (ind0[len(ind0)-1] != ' ') and (ind0[len(ind0)-1] == ' '):
##    ind2.append(len(ind0)-1)
##print(ind2)

    

