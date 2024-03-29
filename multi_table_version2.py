multi_table = "y"
print("Generate a new Multiplication Table!")
while multi_table == "y":
  integer = int(input("Enter a number (2-20): "))
  invalid_int = True

  while invalid_int == True:
    if integer < 2 or integer > 20:
      print("Please enter a number between 2 and 20.")
      integer = int(input("Enter a number (2-20): "))
    else:
      invalid_int = False

  for i in range(1, integer + 1):
    print(f"\t{i}", end = "")
  print("\tRow Total")

  for i in range(1, integer + 1):
    print(f"{i}", end = "")
    total = 0
    for j in range(1, integer + 1):
      print(f"\t{i * j}", end = "")
      total = total + (i * j)
    print(f"\t{total}")

  new_table = input("Would you like to generate another multiplication table? (y/n): ")
  while new_table != "y" and new_table != "n":
    new_table = input("Please enter 'y' for yes and 'n' for no: ")
  if new_table == "y":
    print("\nGenerate a new Multiplication Table!")
  if new_table == "n":
    print("Goodbye!")
    break
