// Program prints tables/triangle of numbers with multiplication.
// The last number on each line is the first number of that line multiplied by the "number of numbers" in that line.
class Triangle {
    public static void main(String[] args) {
        for (int num = 1; num <= 15; num++) { 
            for (int mult = 1; mult <= num; mult++) {
                int result = num * mult; 
                System.out.print(result);
                if (mult == num) { 
                    continue; 
                }
                System.out.print(" ");
            }
            System.out.println(); 
        }
    }
}
