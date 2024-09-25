// This code converts a given integer, positive or negative, (which in terms of the code is a string) into a 16-digit binary short number. It is able to convert decimals somehow but hasn't been programmed to do that so the answer is not guaranteed to be correct.
// The code does not check for the limit of the short variable (a number which is not between -32768 and 32767) so it will still run if such a number is given in the input.
class ShortToText {
    public static void main(String[] args) {
        String inputValue = args[0]; 

        short convertedValue = 0; 

        boolean isNegative = false;
        

        int index = 0; 
        if (inputValue.charAt(0) == '-') { 
            isNegative = true; 
            index = 1; 
        }
        for (int i = index; i < inputValue.length(); i++) {
            convertedValue = (short) (convertedValue * 10); 
            convertedValue = (short) (convertedValue + inputValue.charAt(i) - '0'); 
        }
        if (isNegative == true) { 
            convertedValue = (short) -convertedValue;
        }
        for (int i = 15; i >= 0; i--) {
            int bitValue = (convertedValue >> i) & 1; 
            System.out.print(bitValue);
        }
        System.out.println(); 
    }

}
