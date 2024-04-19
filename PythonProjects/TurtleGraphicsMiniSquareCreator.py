import turtle 

def main():
    screen = turtle.Screen()
    turt = turtle.Turtle()
    num_squares = int(turtle.numinput("Number of Squares", "Enter a Number of Squares: "))
    draw_pattern(turt, num_squares)
    screen.exitonclick()

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

main() 
