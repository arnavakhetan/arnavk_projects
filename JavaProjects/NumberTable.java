class NumberTable {
    public static void main(String[] args) {
        for (int number = 100; number <= 255; number++) {
        	System.out.printf("%-4d", number); 
            int remainder = 0; 
            int hexNum = number;
            String hexRes = "";
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            while (hexNum > 0) {
                remainder = hexNum % 16;
                hexRes = hexDigits[remainder] + hexRes;
                hexNum = hexNum / 16;
            }
            if (hexRes.length() < 2) { 
                hexRes = "0" + hexRes;
            }
            System.out.printf("%2s ", hexRes); 
            for (int i = 7; i >= 0; i--) { 
                int bitValue = (number >> i) & 1; 
                System.out.print(bitValue);
            }
            System.out.println();
        }
    }
}    