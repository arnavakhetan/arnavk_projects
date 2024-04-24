num_list = []
for i in range(5):
  number = float(input(f"Enter number {i+1}: "))
  num_list.append(number)

print(f"\nLowest number: {min(num_list)}")
print(f"Highest number: {max(num_list)}")
print(f"Total: {sum(num_list)}")
print(f"Average: {sum(num_list) / 5}")
