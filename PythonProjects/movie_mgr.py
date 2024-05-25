from movie import Movie

def read_movie_data(file):
	movie_list = []
	for line in file:
		strip = line.strip("\n")
		split = strip.split(", ")
		movie_object = Movie(split[0], split[1], split[2])
		movie_list.append(movie_object)
	return movie_list 

def main():
	file = open("movie_data.txt", "r")
	movie_list = read_movie_data(file)
	file.close()
	continue_search = "y"
	while continue_search.lower() == "y":
		movie_title = input("Please enter a movie title: ")
		movie_found = False
		movie_count = 1
		movie_found_list = []
		for i in range(len(movie_list)):
			if movie_title.lower() in movie_list[i].get_title().lower():
				movie_found = True
				print(f"{movie_count}) {movie_list[i]}")
				movie_found_list.append(movie_list[i])
				movie_count = movie_count + 1
		if movie_found == False:
			print("No movies found.")
		else:
			movie_rate_input = input("Which movie would you like to rate? # ")
			movie_rate = int(movie_rate_input)
			if (1 <= movie_rate <= len(movie_found_list)):
				rate = float(input(f"Enter rating for {movie_found_list[movie_rate - 1].get_title()}: "))
				movie_found_list[movie_rate - 1].add_rating(rate)
				movie_found_list[movie_rate - 1].calc_average_rating()
				print(f"{movie_found_list[movie_rate - 1]} now has an average rating of {movie_found_list[movie_rate - 1].get_average_rating()}")
			else:
				print("Invalid choice.")
		continue_search = input("Would you like to search for another movie? ")
		while continue_search not in ["y", "n", "Y", "N"]:
			print("That was not a valid response. Please try again.")
			continue_search = input("Would you like to search for another movie? ")
	print("Goodbye!")		

main()