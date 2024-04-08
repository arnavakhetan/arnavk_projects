def is_valid_grade(percentage_grade):
  if percentage_grade >= 0 and percentage_grade <= 100:
    return True
  else:
    return False

def calc_letter_grade(percentage_grade):
  if percentage_grade >= 90:
    return "A"
  elif percentage_grade >= 80 and percentage_grade < 90:
    return "B"
  elif percentage_grade >= 70 and percentage_grade < 80:
    return "C"
  elif percentage_grade >= 60 and percentage_grade < 70:
    return "D"
  else:
    return "F"

def main():
  percentage_grade = float(input("Enter grade: "))
  while is_valid_grade(percentage_grade) == False:
    percentage_grade = float(input("Please enter a grade between 0% and 100%: "))
  grade = calc_letter_grade(percentage_grade)
  print(f"Letter Grade: {grade}")

main()
