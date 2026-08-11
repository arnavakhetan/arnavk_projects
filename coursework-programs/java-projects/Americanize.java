// This program has 2 classes: Americanize and WordConvert.
// Americanize prints out the word and WordConvert converts the word.
// Converts all instances of "tea" to "coffee" while only processing the full proper words.
// It will maintain the proper punctuation, capitalization and spaces given in the input.
class Americanize {
	public static void main(String[] args) {
		String Args = args[0]; // Getting the command line input
		String currentWord = "";
		WordConvert converter = new WordConvert(); // Creating the instance of the object
		
		for (int i = 0; i <= Args.length(); i++) {
			if (i < Args.length() && Character.isLetter(Args.charAt(i))) { 
				currentWord = currentWord + Args.charAt(i); // Appending on a character basis until a non-letter is found
			} else { // Once a non-letter comes, process the current word and store it for conversion
				if (currentWord.length() > 0) {
					System.out.print(converter.convert(currentWord)); // Prints the converted and non-converted words
					currentWord = "";
				} 
				
				if (i < Args.length()) {
					System.out.print(Args.charAt(i)); // Responsible for the printing of the punctuation and spaces
				}
			}
		}
	}
}

class WordConvert { // Responsible for the conversion of tea to coffee
	public String convert(String wordToConvert) {
		if (wordToConvert.equals("tea")) { // all lowercase
			return "coffee";
		} else if (wordToConvert.equals("TEA")) { // all uppercase
			return "COFFEE";
		} else if (wordToConvert.equals("Tea")) { // first letter capital
			return "Coffee";
		}
		return wordToConvert; // Returns the converted word
	}
}
