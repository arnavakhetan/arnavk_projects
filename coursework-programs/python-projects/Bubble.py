import random
import time

def bubble_sort(arr):
    n = len(arr)
    for i in range(n - 1):
        for j in range(n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]

numbers = [random.randint(0, 40000) for _ in range(10000)]
    
start_time = time.time()
    
    
bubble_sort(numbers)
    
end_time = time.time()
    
time_taken = end_time - start_time
print(f"{time_taken:.6f}")

