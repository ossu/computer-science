number_of_bobs = 0
for i in range(len(s) - 2):
    if ( s[i:i+3] == "bob" ):
        number_of_bobs += 1
print("Number of bobs: ", number_of_bobs)
