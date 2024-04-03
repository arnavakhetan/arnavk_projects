number = int(input("Enter a number: "))
process = "anything"

while process != "n":
  reversed = 0
  temp_number = number

  while temp_number > 0:
    LastDigit = temp_number % 10
    reversed = (reversed * 10) + LastDigit
    temp_number = temp_number // 10

  print(f"Reversed: {reversed}")
  process = input("Again? (y/n): ")
  if process != "n":
    number = int(input("\nEnter a number: "))

print("\nGoodbye!")
