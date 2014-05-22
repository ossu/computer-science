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


