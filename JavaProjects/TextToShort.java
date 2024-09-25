// This code does the reverse of ShortToText.java and will convert a 16-bit binary short value into a number.
// The code is not programmed to check for negative numbers (in the input itself, not output)
// Trying to give a number that is equivalent to a number not in the range of a short value will make it wrap around the underflow/overflow.
class TextToShort {
    public static void main(String[] args) {
        // I am doing the version without any commas/spaces in the input
        if (args.length == 0 || args[0].length() == 0 || args[0].length() > 16) { // No command line argument or 0 characters or more than 16 characters
            System.err.println("Error: Incorrect input provided");
            return; // Exits the main method if error found
        }
        String inputValue = args[0];
        short convertedValue = 0;
        for (int i = 0; i < inputValue.length(); i++) {
            char bit = inputValue.charAt(i); // Takes each '0' and '1' value separately for conversion
            // charAt() Taken from: https://stackoverflow.com/questions/22861407/search-for-particular-character-inside-a-string
            convertedValue = (short) (convertedValue << 1); // Shift the current short value left by 1 (bitwise operator)
            if (bit == '1') {
                convertedValue = (short) (convertedValue | 1);
            }
        }
        System.out.println(convertedValue);
    }
}
