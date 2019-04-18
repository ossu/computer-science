##s = 'azcbobobegghakl'
##ind0 = [] #master index
##for i in range(len(s)-1):
##    ind1 = []
##    counter = 0
##    while (i+1+counter<len(s)):
##        if (ord(s[i+counter]) <= ord(s[i+1+counter])):
##            ind1.append(s[i])
##            counter += 1
##    ind1.insert(0,counter)
##    ind0.append(ind1)
##print(ind0)

s = 'azcbobobegghakl'
ind0 = [] #master index
for i in range(len(s)-1):
    ind1 = [] #inner list
    if (ord(s[i]) <= ord(s[i+1])):
        ind1.append(s[i])
    ind0.append(ind1)
if ord(s[len(s)-2]) <= ord(s[len(s)-1]):
    ind0.append([s[len(s)-1]])
else:
    ind0.append([])

##ind2 = [] # index of substring
##for q in range(len(ind0)):
##    if ind0[q] == []:
##        ind2.append(q+1)
##
##ind3 = [] #difference list
##for r in range(len(ind2)-1):
##    ind3.append(ind2[r+1]-ind2[r])    
##ind3.append(len(s)-ind2[len(ind2)-1])

ind2 = []
for q in range(len(ind0)):
    if ind0[q] != []:
        ind2.append(q) ##start HERE
ind3 = []
for r in range(len(ind2)-1):
    for m in range(1,len(ind2)-1-r):
        print(ind2[r]+m,ind2[r+m])
        if ind2[r]+m == ind2[r+m]:
            ind3.append(r)


    
        

print(len(s),len(ind0))

    
print(ind3)        

print(ind2)    
        

print(ind0)        
        

##s = 'azcbobobegghakl'
##ind0 = [] #master index
##for i in range(len(s)-1,-1,-1):
##    ind1 = []
##    counter = 0
##    if (ord(s[i]) >= ord(s[i-1])):
##        ind1.append(s[i])
##    ind0.append(ind1)
##print(ind0)
