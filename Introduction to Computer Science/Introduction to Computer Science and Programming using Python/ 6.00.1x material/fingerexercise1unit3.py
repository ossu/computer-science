print("Please think of a number between 0 and 100!")
low = 0
high = 100
inp = ''
while inp != 'c':
    num = int((low + high)/2)
    print("Is your secret number %i?"% num)
    inp = input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly. ")
    if inp == 'l':
        low = num
    elif inp == 'h':
        high = num
    elif inp == 'c':
        print("Game over. Your secret number was: %i"% num)
    else:
        print("Sorry, I did not understand your input.")
