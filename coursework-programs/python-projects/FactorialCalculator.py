invalid_integer = True
while invalid_integer:
  try:
    n = int(input("Enter a positive integer: "))
    if n >= 0:
      invalid_integer = False
    else:
      print("The number is not positive. Please enter a positive integer.\n")
  except ValueError:
    print("That's not an integer. Please enter a positive integer.\n")

if n == 0:
  print("0! = 1")
else:
  factorial = 1
  statement = f"{n}! ="

  for i in range(1, n + 1):
    factorial = factorial * i
    if i < n:
      statement = statement + f" {i} x"
    else:
      statement = statement + f" {i} = {factorial}"

  print(statement)
