// This code does the reverse of ShortToText.java and will convert a 16-bit binary short value into a number.
// The code is not programmed to check for negative numbers (in the input itself, not output)
// Trying to give a number that is equivalent to a number not in the range of a short value will make it wrap around the underflow/overflow.
class TextToShort {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].length() == 0 || args[0].length() > 16) { 
            System.err.println("Error: Incorrect input provided");
            return; 
        }
        String inputValue = args[0];
        short convertedValue = 0;
        for (int i = 0; i < inputValue.length(); i++) {
            char bit = inputValue.charAt(i); 
            convertedValue = (short) (convertedValue << 1); 
            if (bit == '1') {
                convertedValue = (short) (convertedValue | 1);
            }
        }
        System.out.println(convertedValue);
    }
}
