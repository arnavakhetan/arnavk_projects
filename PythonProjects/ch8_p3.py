def validate_email(email):
  parts = email.split("@")[0]
  if parts.isalnum() and parts[0].isalpha() and parts.islower() and email.endswith("@usfca.edu") == True:
    return True
  else:
    return False

def main():
  email = input("Enter a USF email address: ")
  if validate_email(email) == True:
    print("Valid USF email address!")
  else:
    print("Not a valid USF email address.")

main()
