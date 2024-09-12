// Program prints tables/triangle of numbers with multiplication.
// The last number on each line is the first number of that line multiplied by the "number of numbers" in that line.
class Triangle {
    public static void main(String[] args) {
        for (int num = 1; num <= 15; num++) { // First for loop is for each individual line
            for (int mult = 1; mult <= num; mult++) { // Second for loop is responsible for the multiplication
                int result = num * mult; // and for the printing out of numbers on each line themselves
                System.out.print(result);
                if (mult == num) { // This if statement is there to avoid an extra space at the end of each line
                    continue; // Otherwise (without lines 9 to 11), the correct output still comes but there will be an extra space at the end of each line
                }
                System.out.print(" ");
            }
            System.out.println(); // Goes to the next line
        }
    }
}
