t = 'abcdefghijklmnopqrstuvwxyz'
s = 'azcbobobegghakl'
test = 'a'
for i in range(1,len(s)):
    for j in range(i):
        if (s[j:i] <= test) and (len(s[j:i]) > len(test)):
            test = s[j:i]
            print(test)
print(test)
        

    
