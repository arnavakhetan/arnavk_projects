// This program will randomly print out one of my 3 favurite desserts. 
// It has 5 classes: Dessert, IceCream, Cake, Pudding, MyDesserts.
// Dessert is the abstract class and contains the abstract class definitions of the 3 methods that will be used in this program: ingredients, name, where.
// IceCream, Cake, and Pudding are the child classes of Dessert.
// This program is an example of Object-Oriented Programming.

// Parent Dessert class
abstract class Dessert {
	abstract String ingredients();
	abstract String name();
	abstract String where();
	
	public String toString() {
		return name() + " contains " + ingredients() + " and the best comes from " + where();
	}
}

// IceCream child class of Dessert
class IceCream extends Dessert {
	String ingredients() {
		return "milk, sugar, chocolate";
	}
	String name() {
		return "Chocolate Ice Cream";
	}
	String where() {
		return "India";
	}
}

// Cake child class of Dessert
class Cake extends Dessert {
	String ingredients() {
		return "flour, sugar, baking soda, milk";
	}
	String name() {
		return "Cake";
	}
	String where() {
		return "Switzerland";
	}
}

// Pudding child class of Dessert
class Pudding extends Dessert {
	String ingredients() {
		return "milk, flour, rice";
	}
	String name() {
		return "Kheer";
	}
	String where() {
		return "India";
	}
}

// MyDesserts class with the main method
public class MyDesserts {
	public static void main(String[] args) {
		Dessert[] desserts = {new IceCream(), new Cake(), new Pudding()};
		int index = (int) (Math.random() * desserts.length); // Randomizer
		System.out.println(desserts[index]);
	}
}
