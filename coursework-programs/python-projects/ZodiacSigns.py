invalid_date = True

while invalid_date:
    month = int(input("Enter your birth month: "))

    if not (month >= 1 and month <= 12):
        print("The birthday you entered is invalid.\n")
        continue

    day = int(input("Enter day of your birth: "))

    if month == 2 and (day < 1 or day > 28):
        print("The birthday you entered is invalid.\n")
    elif (month == 4 or month == 6 or month == 9 or month == 11) and (day < 1 or day > 30):
        print("The birthday you entered is invalid.\n")
    elif (month == 1 or month == 3 or month == 5 or month == 7 or month == 8 or month == 10 or month == 12) and (day < 1 or day > 31):
        print("The birthday you entered is invalid.\n")
    else:
        invalid_date = False  

if (month == 3 and day >= 21) or (month == 4 and day <= 19):
  sign = "Aries"
elif (month == 4 and day >= 20) or (month == 5 and day <= 20):
  sign = "Taurus"
elif (month == 5 and day >= 21) or (month == 6 and day <= 20):
  sign = "Gemini"
elif (month == 6 and day >= 21) or (month == 7 and day <= 22):
  sign = "Cancer"
elif (month == 7 and day >= 23) or (month == 8 and day <= 22):
  sign = "Leo"
elif (month == 8 and day >= 23) or (month == 9 and day <= 22):
  sign = "Virgo"
elif (month == 9 and day >= 23) or (month == 10 and day <= 22):
  sign = "Libra"
elif (month == 10 and day >= 23) or (month == 11 and day <= 21):
  sign = "Scorpio"
elif (month == 11 and day >= 22) or (month == 12 and day <= 21):
  sign = "Sagittarius"
elif (month == 12 and day >= 22) or (month == 1 and day <= 19):
  sign = "Capricorn"
elif (month == 1 and day >= 20) or (month == 2 and day <= 18):
  sign = "Aquarius"
elif (month == 2 and day >= 19) or (month == 3 and day <= 20):
  sign = "Pisces"

print(f"You are a {sign}!")
