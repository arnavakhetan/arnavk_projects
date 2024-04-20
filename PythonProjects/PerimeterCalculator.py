# Arnav Khetan
# Section A
# Problem 1A: Circle Perimeter


import math   

def distance(x1, y1, x2, y2):    
	dist = math.sqrt(((x2 - x1) ** 2) + ((y2 - y1) ** 2))
	return dist

def perim(r):
    peri = 2 * math.pi * r
    return peri


def main(): 
    x1 = float(input("Enter x for center point: "))
    y1 = float(input("Enter y for center point: "))
    x2 = float(input("Enter x for permimeter point: "))
    y2 = float(input("Enter y for permimeter point: "))
    r = distance(x1, y1, x2, y2)
    perimeter = perim(r)
    print(f"Permimeter of circle centered at ({x1}, {y1}) with radius {r} is {perimeter}")
    
	# These values represent two points on a Cartesian plane (x1, y1) and (x2, y2)
	# Calls the distance(x1, y1, x2, y2) function to calculate the distance between two points.
	# Calls the perim(r) function to calculate the perimeter of a circle with radius r. 
	# Prints the circle’s center point, radius, and perimeter.
	# Example of print statement: Perimeter of circle centered at (0.0 , 0.0) with radius 11.180339887498949 is 70.24814731040726

main()
