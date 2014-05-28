# Loops - http://beastie.cs.ua.edu/cs150/book/index_14.html

i = 0
while i < 5:
  print i
  i += 1

# Other loops
for i in range(0, 5, 1): # 5 exclusive
  print i

# The counting pattern
count = 0
for i in range(0, len(items), 1):
  count += 1

# The filtered-count pattern
count = 0
for i in range(0, len(items), 1):
  if items[i] % 2 == 0:
    count += 1

# The accumulate pattern
total = 0
for i in range(0, len(items), 1):
  total += items[i]

# The filtered-accumulate pattern
def sumEvens(items):
  total = 0
  for i in range(0, len(items), 1):
    if items[i] % 2 == 0:
      total += items[i]
  return total

# The search pattern
def find(target, items): # is target in items?
  return occurrences(target, items) > 0

def occurrences(target, items):
  count = 0
  for i in range(0, len(items), 1):
    if items[i] == target:
      count += 1
  return count

## search with short-circuit
def find(target, items):
  for i in range(0, len(items), 1):
    if items[i] == target:
      return True
  return False

# The extreme pattern
largest = items[0]
for i in range(1, len(items), 1): # start comparing at index 1
  if items[i] > largest:
    largest = items[i]

# The filter pattern
def extractEvens(items):
  for i in range(0, len(items), 1):
    if isEven(items[i]):
      evens = evens + [items[i]] # array concatenation
  return evens

## Add isEven function 
def isEven(value):
  if value % 2 == 0:
    return True
  return False

someValues = [1,2,4,3,5,7,8,6,9]

print extractEvens(someValues)

## Using append method because array concatenation is very slowly for large arrays
def extractEvens(items):
  evens = []
  for i in range(0, len(items), 1):
    if isEven(items[i]):
      evens.append(items[i]) # method invocation
  return evens

# The map pattern
def map(f, items):
  result = []
  for i in range(0, len(items), 1):
    transformed = f(items[i])
    result.append(transformed)
  return result

## Using this pattern
def increment(value):
  return value + 1

someValues = [1, 2, 3, 4, 5, 6, 7]
print map(increment, someValues)

# The shuffle pattern
## arrays with the same size
def shuffle(array1, array2):
  array3 = []
  for i in range(0, len(array1), 1):
    array3.append(array1[i])
    array3.append(array2[i])
  return array3

## arrays without the same size
def shuffle2(array1, array2):
  array3 = []
  if len(array1) < len(array2):
    for i in range(len(array1), 1):
      array3.append(array1[i])
      array3.append(array2[i])
    return array3 + array2[i+1:]
  else:
    for i in range(0, len(array2), 1):
      array3.append(array1[i])
      array3.append(array2[i])
    return array3 + array1[i+1:]

## using a while loop instead
def shuffle3(array1, array2):
  array3 = []
  i = 0
  while (i < len(array1) and i < len(array2)):
    array3.append(array1[i])
    array3.append(array2[i])
    i += 1
  return array3 + array1[i:] + array2[i:]

## back to shuffle2 but adding recursion
def shuffle4(array1, array2):
  if len(array2) < len(array1):
    return shuffle4(array2, array1) # arrays are flipped
  else:
    array3 = []
    for i in range(0, len(array1), 1):
      array3.append(array1[i])
      array3.append(array2[i])
    return array3 + array2[i+1:]

# The merge pattern

def merge(array1, array2):
  array3 = []
  i = 0
  j = 0
  while (i < len(array1) and j < len(array2)):
    if array1[i] < array2[j]:
      array3.append(array1[i])
      i += 1
    else:
      array3.append(array2[j])
      j += 1
  return array3 + array1[i:] + array2[j:]

## using the merge pattern
array1 = [1,3,4,6,8]
array2 = [2,5,7,9,10]

print merge(array1, array2)

# The fossilized pattern (never end!)

i = 0
while i < n:
  print(i)

# The missed-condition pattern (never end)

i = n
while i > 0:
  print(i)
  i += 1
