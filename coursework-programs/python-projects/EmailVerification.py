def validate_email(email):
  parts = email.split("@")[0]
  if parts.isalnum() and parts[0].isalpha() and parts.islower() and email.endswith("@gmail.com") == True: 
    return True
  else:
    return False
# Specifically for "gmail.com" emails but can be edited to work for other emails as well.
def main():
  email = input("Enter a Gmail email address: ")
  if validate_email(email) == True:
    print("Valid Gmail Email address!")
  else:
    print("Not a valid Gmail Email address.")

main()
