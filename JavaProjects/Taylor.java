// This program prints out the Taylor series sum for 2, 10, 100, and 1000 terms.
// As seen by the output - the more the number of terms, the more accurate the answer becomes. 
// The answer for 1000 terms is the closest to the correct answer of ln(2).
class Taylor {
	public static void main(String[] args) {
		double correct_answer = Math.log(2); // Correct answer for ln(2)
		System.out.println("ln(2) = " + correct_answer);
		
		double sum10 = 0.0; 
		int counter = 1;
		int signOfTerm = 1; // signOfTerm is being used to alternate the sign from positive to negative as per the formula
		double x = 1;
		double power = x;
		while (counter <= 10) {
			double term = signOfTerm * power / counter; // Formula based on the question
            sum10 = sum10 + term; // Adding the term to the sum to keep track
            signOfTerm = signOfTerm * (-1);
            power = power * x;
            counter = counter + 1;
		}
		System.out.println("Taylor(10) = " + sum10); // Printing the answer for the Taylor series sum for 10 terms
	
		double sum100 = 0.0; // Same thing as above. Only the name for the sum variable has changed
		counter = 1;
		signOfTerm = 1; 
		x = 1;
		power = x;
		while (counter <= 100) {
			double term = signOfTerm * power / counter;
			sum100 = sum100 + term;
			signOfTerm = signOfTerm * (-1);
			power = power * x;
			counter = counter + 1;
		}
		System.out.println("Taylor(100) = " + sum100);
		
		double sum1000 = 0.0;
		counter = 1; // All variables besides the sum variable have been re-used. 
		signOfTerm = 1; 
		x = 1;
		power = x;
		while (counter <= 1000) {
			double term = signOfTerm * power / counter;
			sum1000 = sum1000 + term;
			signOfTerm = signOfTerm * (-1);
			power = power * x;
			counter = counter + 1; // Appending the counter with 1 after the current iteration finishes
		}
		System.out.print("Taylor(1000) = " + sum1000);
	}
}
