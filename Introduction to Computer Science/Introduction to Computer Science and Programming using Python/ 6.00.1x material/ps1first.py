def main(s):
    vowels = "aeiou"
    number_of_vowels = 0
    for i in range(len(s)):
        for j in range(len(vowels)):
            if ( s[i] == vowels[j] ):
                number_of_vowels += 1
    print("Number of vowels: ", number_of_vowels)

