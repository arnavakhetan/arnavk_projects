from account import Account
account_list = []

def create_account(username, password):
    new_account = Account(username, password)
    account_list.append(new_account)
    print("Account successfully created!")

def login(username, password):
    no_account_found = "yes"
    for i in range(len(account_list)):
        if username == account_list[i].get_username():
            if password == account_list[i].get_password():
                print("Successful Login!")
                no_account_found = "no"
                break
            else:
                print("Incorrect password")
                no_account_found = "no"
                break
    if no_account_found == "yes":
        print("No account with no username.")

def main():
    print("Select from the following options:\n1) Create account\n2) Login to account")
    option = input("Enter 1, 2, or anything else to exit: ")
    while option == "1" or option == "2":
        if option == "1":
            username = input("Enter new username: ")
            password = input("Enter new password: ")
            create_account(username, password)
        elif option == "2":
            username = input("Enter new username: ")
            password = input("Enter new password: ")
            login(username, password)
        print("\nSelect from the following options:\n1) Create account\n2) Login to account")
        option = input("Enter 1, 2, or anything else to exit: ")
    print("Goodbye!")

main()

