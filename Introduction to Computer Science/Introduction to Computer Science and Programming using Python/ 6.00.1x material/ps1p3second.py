s = 'azcbobobegghakl'
OrderedChars = ['a']
ind = []
for i in range(len(s)):
    if s[i] == OrderedChars[0]:
        for j in range(len(s)-i-1):
            if s[i+j] <= s[i+j+1]:
                ind.append([i,i+j+1])
            else:
                break
print(ind)
                




##    
##    for i in range(len(OrderedChars)):
##        if char == OrderedChars[0]:
            
    
        
        
