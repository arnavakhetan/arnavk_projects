// The code wants to find the number of steps required to reduce all numbers between 1 and 200 to 1.
// Outputs in the fashion of: Positive_Integer Number_of_Steps.
class Steps {
    static int runSteps(int n) {
        int numberOfSteps = 0;
        while (n != 1) { 
            if (n % 2 == 0) { 
                n = n / 2; 
            } else {
                n = (3 * n) + 1; 
            }
            numberOfSteps++; 
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
