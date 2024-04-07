import random

def is_valid_choice(choice):
  if choice == "rock" or choice == "paper" or choice == "scissors":
    return True
  else:
    return False

def get_cpu_choice():
  cpu_answer = random.randint(1, 3)
  if cpu_answer == 1:
    cpu_answer = "rock"
  elif cpu_answer == 2:
    cpu_answer = "paper"
  elif cpu_answer == 3:
    cpu_answer = "scissors"
  return cpu_answer

def determine_winner(user_choice, cpu_answer):
  if user_choice == "rock" and cpu_answer == "scissors":
    winner = "You win!"
  elif user_choice == "scissors" and cpu_answer == "rock":
    winner = "You lose!"
  elif user_choice == "scissors" and cpu_answer == "paper":
    winner = "You win!"
  elif user_choice == "paper" and cpu_answer == "scissors":
    winner = "You lose!"
  elif user_choice == "rock" and cpu_answer == "paper":
    winner = "You lose!"
  elif user_choice == "paper" and cpu_answer == "rock":
    winner = "You win!"
  elif user_choice == cpu_answer:
    winner = "Tie!"
  return winner

def main():
  choice = input("Enter choice (rock/paper/scissors): ")
  while is_valid_choice(choice) == False:
    choice = input("Please enter rock, paper or scissors: ")
  user_choice = choice
  cpu_answer = get_cpu_choice()
  final_winner = determine_winner(user_choice, cpu_answer)
  print(f"You chose {user_choice} and Computer chose {cpu_answer}")
  print(final_winner)

main()
