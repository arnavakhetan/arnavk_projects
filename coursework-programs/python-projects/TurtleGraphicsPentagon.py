import turtle 


def draw_spiral(my_turtle):
	length = 2
	for i in range(1, 101):
		my_turtle.forward(length)
		my_turtle.right(72)
		length = length + 2
def main():
	screen = turtle.Screen()
	my_turtle = turtle.Turtle()
	draw_spiral(my_turtle)
	screen.exitonclick()

main()
