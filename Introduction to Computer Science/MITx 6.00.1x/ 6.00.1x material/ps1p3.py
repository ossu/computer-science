s = 'azcbobobegghakl'
print(len(s))
all_substrings = []
for i in range(len(s)):
    for j in range(i):
        all_substrings.append(s[j:i])
##long_strings = []
##long = len(all_substrings[0])
##for q in range (1,len(all_substrings)):
##    long = max(long,len(all_substrings[q]))
##    print(long)
##print(all_substrings)        
##print(sorted(all_substrings))
##print("Number of all substrings is: ", all_substrings)
##ans = '' #Longest substring in alphebetical order
##
test = 'z'
for i in range(len(all_substrings)):
    print(test, all_substrings[i] < test,len(all_substrings[i]) > len(test))
    if (all_substrings[i] > test) and (len(all_substrings[i]) > len(test)):
        test = all_substrings[i]
        print(test)
