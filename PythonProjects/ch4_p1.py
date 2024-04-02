n = int(input("Enter a positive integer: "))
factorial = 1
statement = f"{n}! ="

for i in range(1, n + 1):
  factorial = factorial * i
  if i < n:
    statement = statement + f" {i} x"
  else:
    statement = statement + f" {i} = {factorial}"

print(statement)