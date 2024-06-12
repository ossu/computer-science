# Hangman game
#

# -----------------------------------
# Helper code
# You don't need to understand this helper code,
# but you will have to know how to use the functions
# (so be sure to read the docstrings!)

import random

#fixed this line to read the words database, filepath must be modified to match directory from root
WORDLIST_FILENAME = "coursepages/intro-cs/MIT_6001/words.txt"

alphabet = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]

guess_cache = {
    ""
}

def loadWords():
    """
    Returns a list of valid words. Words are strings of lowercase letters.
    
    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print("Loading word list from file...")
    # inFile: file
    inFile = open(WORDLIST_FILENAME, 'r')
    # line: string
    line = inFile.readline()
    # wordlist: list of strings
    wordlist = line.split()
    print("  ", len(wordlist), "words loaded.")
    return wordlist

def chooseWord(wordlist):
    """
    wordlist (list): list of words (strings)

    Returns a word from wordlist at random
    """
    return random.choice(wordlist)

# end of helper code
# -----------------------------------

# Load the list of words into the variable wordlist
# so that it can be accessed from anywhere in the program
wordlist = loadWords()

def isWordGuessed(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: boolean, True if all the letters of secretWord are in lettersGuessed;
      False otherwise
    '''
    # FILL IN YOUR CODE HERE...
    
    count_letters = 0
    for i in secretWord:
        if i in lettersGuessed:
            count_letters += 1
    if count_letters == len(secretWord):
        return True
    else:
        return False
    
# Test script - my input case
# print(isWordGuessed("test", ["t", "e", "s", "t", "j", "r"]))


def getGuessedWord(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters and underscores that represents
      what letters in secretWord have been guessed so far.
    '''
    # FILL IN YOUR CODE HERE...

    current_word = []
    for i in secretWord:
        if i in lettersGuessed:
            current_word.append(i)
        else:
            current_word.append("_")
    return ''.join(current_word)

#test script - my input case
#print(getGuessedWord("attempt", ["a", "t", "r", "o"]))


def getAvailableLetters(lettersGuessed):
    '''
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters that represents what letters have not
      yet been guessed.
    '''
    # FILL IN YOUR CODE HERE...

    for i in lettersGuessed:
        if i in alphabet:
            alphabet.remove(i)
    
    return "".join(alphabet)
    
# test script - my test case
print(getAvailableLetters(["a", "r", "j", "q", "z"]))

def hangman(secretWord):
    '''
    secretWord: string, the secret word to guess.

    Starts up an interactive game of Hangman.

    * At the start of the game, let the user know how many 
      letters the secretWord contains.

    * Ask the user to supply one guess (i.e. letter) per round.

    * The user should receive feedback immediately after each guess 
      about whether their guess appears in the computers word.

    * After each round, you should also display to the user the 
      partially guessed word so far, as well as letters that the 
      user has not yet guessed.

    Follows the other limitations detailed in the problem write-up.
    '''
    # FILL IN YOUR CODE HERE...

    lives = 6
    lettersGuessed = []
    alphabet = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]

    print(f"The secret word contains {len(secretWord)} letters")
    print(" ")
    print(" Initiating hangman game...")
    print(" ")
    while(isWordGuessed(secretWord, lettersGuessed) == False):
      print(" Beginning round. ")
      print(" ")
      attempt = input("Please select an alphabetical lowercase letter: ")
      while attempt not in alphabet:
          print("error, that is not an acceptable guess, please try again")
          attempt = input("Please select an alphabetical lowercase letter: ")
      lettersGuessed.append(attempt)
      if attempt in secretWord:
        print(f"Good guess! {getGuessedWord(secretWord, lettersGuessed)}")
      else:
          print(f"Sorry, that is not a correct guess {getGuessedWord(secretWord, lettersGuessed)}")
          lives -= 1
          print(f"You lose one life. Remaining lives: {lives}")
      if lives == 0:
          print("Game lost")
          break
      elif isWordGuessed(secretWord, lettersGuessed) == True:
          print("You won!")
          break
      else:
          print(f"Your remaining available words are {alphabet}")




# When you've completed your hangman function, uncomment these two lines
# and run this file to test! (hint: you might want to pick your own
# secretWord while you're testing)

# secretWord = chooseWord(wordlist).lower()
hangman("test")
