// This code prints out the cubes of numbers from 1 to 12.
// Cube of 13 and above are greater than 2000 so are not printed.
class Cubes {
    public static void main(String[] args) {
        int num = 1;
        int cube = 1;
        while (cube < 2000) { // While Loop with the check for cube being less than 2000
            cube = num * num * num; // Simple cube formula
            if (cube >= 2000) {
                break;
            }
            System.out.println(cube); // Prints out the cubed value
            num++; // Increments 1 each time to the value of "num"
        }
    }
}
