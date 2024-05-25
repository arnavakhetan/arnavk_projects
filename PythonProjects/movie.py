class Movie:
    def __init__(self, title, release_year, director):
        self.__title = title
        self.__release_year = release_year
        self.__director = director
        self.__ratings = []
        self.__average_rating = 0.0

    def __str__(self):
        return (f"{self.__title} ({self.__release_year}) Directed by: {self.__director}")
    
    def add_rating(self, rating):
        self.__ratings.append(rating)
        
    def calc_average_rating(self):
        self.__average_rating = sum(self.__ratings) / len(self.__ratings)
    
    def get_title(self):
        return self.__title

    def get_release_year(self):
        return self.__release_year

    def get_director(self):
        return self.__director
    
    def get_ratings(self):
        return self.__ratings

    def get_average_rating(self):
        return self.__average_rating
    
    def set_title(self, title):
        self.__title = title

    def set_release_year(self, release_year):
        self.__release_year = release_year

    def set_director(self, director):
        self.__director = director
    
    def set_average_rating(self, average_rating):
        self.__average_rating = average_rating