import random
def check_answer(binary_number, decimal_number):
  decimal_num = 0
  for i in range(len(binary_number)):
    if binary_number[i] == "1":
      decimal_num = decimal_num + (2 ** (len(binary_number) - 1 - i))
  return decimal_num == decimal_number

def generate_binary():
  return (f"{random.randint(0, 1)}{random.randint(0, 1)}{random.randint(0, 1)}{random.randint(0, 1)}{random.randint(0, 1)}")

def main():
  play_again = "yes"
  rounds = 0
  correct = 0
  while play_again != "exit":
    binary_number = generate_binary()
    decimal_number = int(input(f"What is the decimal value of the binary number {binary_number}? "))
    if check_answer(binary_number, decimal_number) == True:
      print("Correct!\n")
      correct = correct + 1
    else:
      print("Incorrect!\n")
    play_again = input("Enter 'exit' to quit. Enter anything else to play again: ")
    rounds = rounds + 1
  print(f"In {rounds} rounds, you answered {correct} questions correctly. Thanks for playing!")

main()
