validation = ""
while True:
  name = input("Enter your name: ")
  validation = name.split()
  if len(validation) == 3 and all(word.isalpha() for word in validation):
    break
  else:
    print("You must enter your first, middle and last name.")
print(f"{validation[0][0].upper()}.{validation[1][0].upper()}.{validation[2][0].upper()}.")
