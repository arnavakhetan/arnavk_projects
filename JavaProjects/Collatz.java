// The code wants to find the number of steps required to reduce all numbers between 1 and 200 to 1.
// Outputs in the fashion of: Positive_Integer Number_of_Steps.
// Filled in the lines 6 to 13 and added appropriate comments.
class Collatz {
    static int runSteps(int n) {
        int numberOfSteps = 0;
        while (n != 1) { // Satisfies the condition of "keep repeating that process until your number equals 1"
            if (n % 2 == 0) { // To check if the number is even
                n = n / 2; // If the number is even, then divide it by 2
            } else {
                n = (3 * n) + 1; // If the number is not even, multiply it by 3 then add 1
            }
            numberOfSteps++; // Increments 1 each time to numberOfSteps
        }
        return numberOfSteps;
    }

    static public void main(String[] args) {
        int currentNumber = 1;
        while (currentNumber <= 200) {
            System.out.println(currentNumber + " " + runSteps(currentNumber));
            currentNumber++;
        }
    }
}