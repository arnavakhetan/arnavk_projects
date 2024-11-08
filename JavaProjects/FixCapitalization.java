// This program will fix the capitalization of sentences such that only the first letter of a sentence is capitalized.
// It will abide by the punctuation and ./!/? symbols will be treated as the end of a sentence.
// It will also abide by the extra spaces given in the program and not remove them.
// Filename: Fix Capitalization.java
// Author: Arnav Khetan
class FixCapitalization {
	public static void main(String[] args) {
		String Args = args[0];
		String trimmedArgs = Args.toLowerCase(); // converts all letters to lowercase
		
		if (trimmedArgs.length() > 0) {
		    int firstLetterIndex = 0;
		    // Index of the first non-whitespace character
		    while (firstLetterIndex < trimmedArgs.length() && Character.isWhitespace(trimmedArgs.charAt(firstLetterIndex))) {
		        firstLetterIndex++;
		    }
		    
		    // Once, the first character of the string is found, capitalize it. 
		    if (firstLetterIndex < trimmedArgs.length()) {
		        char firstLetter = Character.toUpperCase(trimmedArgs.charAt(firstLetterIndex));
		        String beforeFirstLetter = trimmedArgs.substring(0, firstLetterIndex); // Making sure to preserve the spaces in the start of the code
		        String restOfString = trimmedArgs.substring(firstLetterIndex + 1);
		        trimmedArgs = beforeFirstLetter + firstLetter + restOfString; // Rejoining the full string after capitalizing the first letter
		    } // This part is there in case some whitespace is put in front of the first character and to make sure that character is capitalized
		}

		for (int i = 0; i < trimmedArgs.length(); i++) {
			if (!Character.isLetter(trimmedArgs.charAt(i)) && !Character.isDigit(trimmedArgs.charAt(i)) && !Character.isWhitespace(trimmedArgs.charAt(i))) {
				// Line 14: If character is a special character/punctuation
				if (trimmedArgs.charAt(i) == '.' || trimmedArgs.charAt(i) == '!' || trimmedArgs.charAt(i) == '?') {
					// Line 16: If that character is a . or ! or ?
					for (int j = i + 1; j < trimmedArgs.length(); j++) {
						if (Character.isLetter(trimmedArgs.charAt(j))) {
							String beforeUpper = trimmedArgs.substring(0, j); // Takes the first letter that needs to be converted
							char upperLetter = Character.toUpperCase(trimmedArgs.charAt(j)); // Converts the first letter after ./!/? punctuation to capital
							String afterUpper = trimmedArgs.substring(j + 1);
							trimmedArgs = beforeUpper + upperLetter + afterUpper; // Rejoins the entire string after conversion
							break;
						}
					}
				} 
			} 
		}
		System.out.print(trimmedArgs); // Prints the final converted string
	}
}
