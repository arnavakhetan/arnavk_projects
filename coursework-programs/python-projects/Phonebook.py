def create_phonebook(file):
    phonebook = {}
    for line in file:
        strip = line.strip("\n")
        split = strip.split(", ")
        phonebook.update({split[0]:[split[1], split[2]]})
    return phonebook

def search_phonebook(name, phonebook):
    if name in phonebook:
        print(f"Name: {name}\nPhone number: {phonebook[name][0]}\nAddress: {phonebook[name][1]}") 
    else:
        print("Contact not found.")
def main():
    again = "y"
    file = open("phonebook.txt", "r")
    phonebook = create_phonebook(file)
    while again == "y":
        name = input("Enter name to search: ")
        search_phonebook(name, phonebook)
        again = input("Search again (y/n)? ")
        print(" ")
    print("Goodbye!")
    file.close()

main()
