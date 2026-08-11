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

main()
