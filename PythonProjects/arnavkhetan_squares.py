# Arnav Khetan
# Section B
# Problem 1B: Repeating Squares

# Write a program yourname_squares.py that uses Turtle Graphics and repetition structure(s) to draw a pattern of repeating squares that looks like the figure on the instruction sheet. 
# The smallest square is 10 × 10 and each subsequent square is 10 pixels larger. Your program should include the following functions:


import turtle # Import Python's turtle module

def main():
    screen = turtle.Screen()
    turt = turtle.Turtle()
    num_squares = int(turtle.numinput("Number of Squares", "Enter a Number of Squares: "))
    draw_pattern(turt, num_squares)
    screen.exitonclick()
	# Initializes a screen and a turtle using the turtle module
	# Prompts the user to enter the number of squares they would like to be drawn (integer) (Assume the user enters a positive integer)
	# Calls the draw_pattern() function, passing the turtle and number of squares to be drawn (integer) as a arguments

def draw_pattern(turt, num_squares):
    turt.setheading(180)
    length = 10
    for i in range(4):
        turt.forward(length)
        turt.right(90)
    for j in range(num_squares - 1):
        length = length + 10
        turt.forward(length)
        turt.right(90)
        turt.forward(length)
        turt.right(90)
        turt.forward(length)
        turt.right(90)
        turt.forward(length)
        turt.right(90)    
	# Void function that takes a turtle and number of squares to be drawn (integer) as parameters
	# Draws the figure shown on the instruction sheet with the number of squares the user entered
	# The smallest square is 10px × 10px and each subsequent square is 10 pixels larger in width and height
	# The figure may be situated anywhere within the screen.

main() # Call the main() function