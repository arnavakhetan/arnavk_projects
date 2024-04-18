# Arnav Khetan
# Section A
# Problem 1A: Right Cylinder Volume

# Write a program yourname_volume.py that calculates the volume of a right cylinder given the base’s circumference and the cylinder’s height. 
# Your program should include the following functions:


import math  # Import Python's math module

def main(): 
    circumference = float(input("Enter base circumference: "))
    height = float(input("Enter cylinder height: "))
    radius = calculate_radius(circumference)
    volume = calculate_volume(radius, height)
    print(f"\nA right cylinder with base radius {radius:.3f} and height {height} has a volume of {volume:.3f}")
	# Prompts the user to enter the cylinder's base circumference (float)
	# Prompts the user to enter the cylinder's height (float)
	# Calls the calculate_radius(circumference) function to calculate a circle’s radius given the circle’s circumference.
	# Calls the calculate_volume(radius, height) function to calculate a cylinder’s volume given the cylinder’s base radius and the cylinder’s height.
	# Prints the cylinder’s base radius, height and volume as shown below, where the values for radius and volume are each rounded to 3 decimal places. 
	# Example of print statement: A right cylinder with base radius 1.680 and height 20.2 has a volume of 179.101.

def calculate_radius(circumference): 
    rad = circumference / (2 * math.pi)
    return rad
	# Takes a circle's circumference as a parameter
	# Computes the circle's radius using the following formula: r = C / 2π
	# Return the radius

def calculate_volume(radius, height): 
    vol = math.pi * (radius ** 2) * height
    return vol
	# Takes a cylinder's radius and height as parameters
	# Computes the cylinder's volume using the following formula: v = πr²h
	# Return the volume

main() # Call the main() function