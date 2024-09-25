// This code converts a given integer, positive or negative, (which in terms of the code is a string) into a 16-digit binary short number. It is able to convert decimals somehow but hasn't been programmed to do that so the answer is not guaranteed to be correct.
// The code does not check for the limit of the short variable (a number which is not between -32768 and 32767) so it will still run if such a number is given in the input.
class ShortToText {
    public static void main(String[] args) {
        String inputValue = args[0]; // Assumes only the number will be entered in the command line argument

        short convertedValue = 0; // Defines the variable which will have the output value in it

        boolean isNegative = false;
        // Since you mentioned a negative number in the question, I decided to check for a negative input

        int index = 0; // Line 13 checks if the inputted value is negative or not
        if (inputValue.charAt(0) == '-') { // I did not know what the code for checking for a particular character was, so I looked it up
            // https://stackoverflow.com/questions/22861407/search-for-particular-character-inside-a-string I found it in this stack overflow site
            isNegative = true; // The input is negative so this is true
            index = 1; // To start the conversion after the minus sign and not include it in the conversion process
        }
        for (int i = index; i < inputValue.length(); i++) {
            convertedValue = (short) (convertedValue * 10); // Even though, convertedValue is defined as a short, need to cast it to a short here again otherwise Java gives an error
            convertedValue = (short) (convertedValue + inputValue.charAt(i) - '0'); // To move onto the next digit for conversion
        }
        if (isNegative == true) { // If input was negative, convert output to negative short by adding a minus sign in front of it
            convertedValue = (short) -convertedValue;
        }
        for (int i = 15; i >= 0; i--) {
            int bitValue = (convertedValue >> i) & 1; // >> is the right-shift operator for bits
            System.out.print(bitValue);
        }
        System.out.println(); // Needed to make sure the output in the terminal prints properly
    }

}
