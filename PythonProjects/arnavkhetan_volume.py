import math

def main(): 
    circumference = float(input("Enter base circumference: "))
    height = float(input("Enter cylinder height: "))
    radius = calculate_radius(circumference)
    volume = calculate_volume(radius, height)
    print(f"\nA right cylinder with base radius {radius:.3f} and height {height} has a volume of {volume:.3f}")

def calculate_radius(circumference): 
    rad = circumference / (2 * math.pi)
    return rad

def calculate_volume(radius, height): 
    vol = math.pi * (radius ** 2) * height
    return vol
main()
