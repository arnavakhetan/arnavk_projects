import turtle 
import math
screen = turtle.Screen()
my_turtle = turtle.Turtle()

square = int(turtle.numinput("Number of squares", "Enter number of squares:"))
screen.bgcolor("black")
my_turtle.pencolor("white")
side_length = 300
my_turtle.penup()
my_turtle.goto(-150, -150)
my_turtle.pendown()

for i in range(4):
    my_turtle.forward(side_length)
    my_turtle.left(90)
    
for j in range(square - 1):
    my_turtle.forward(side_length / 2)
    side_length = side_length / math.sqrt(2)
    my_turtle.left(45)
    my_turtle.forward(side_length)
    my_turtle.left(90)
    my_turtle.forward(side_length)  
    my_turtle.left(90)
    my_turtle.forward(side_length)
    my_turtle.left(90)
    my_turtle.forward(side_length)
    my_turtle.left(90)

# Below 2 lines are to make the ending exactly like the example video. 
my_turtle.forward(side_length / 2)
my_turtle.left(45)
screen.exitonclick()
