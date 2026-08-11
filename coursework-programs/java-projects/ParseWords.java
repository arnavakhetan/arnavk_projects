// The program prints BLANK for every colon on its own output line.
// All types of characters (besides spaces) will be treated as one output line until separated by colon(s).
class ParseWords {
    public static void main(String[] args) {
        String Args = args[0];  // Getting the command line input
        String currentWord = "";  
        boolean lastColon = false;  // To track if the last character was a colon or not

        for (int i = 0; i < Args.length(); i++) {
            char currentChar = Args.charAt(i); // Creating a variable to store one character at a time
            
            if (currentChar == ':') {
                if (!currentWord.isEmpty()) { // If the string has one or more characters
                	// .isEmpty() was taken from: https://stackoverflow.com/questions/3321526/should-i-use-string-isempty-or-equalsstring
                    System.out.println(currentWord); // If there is a word, then print it
                    currentWord = "";  // Resetting the variable for the next word
                }
                
                if (lastColon == true || i == 0) {
                    // If the last character was a colon or the first character is a colon, then print BLANK
                    System.out.println("BLANK");
                }
                
                lastColon = true;  
            } else {
                // Append the characters into currentWord if it isn't a colon
                currentWord = currentWord + currentChar;
                lastColon = false; // Resetting the variable back to false
            }
        }

        // To print out any words remaining after the loop
        if (!currentWord.isEmpty()) {
            System.out.println(currentWord);
        } else if (lastColon == true && Args.length() > 1) {
            // Only print "BLANK" for the last colon if the input is more than 1 character long (condition is there to avoid excess "BLANK"s)
            System.out.println("BLANK");
        }
    }
}
