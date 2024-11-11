// The purpose of this program is to find the root of a positive number and approximate it as best as possible without using the direct math formula.
// I noticed that very low numbers and very high numbers had somewhat close answers but could sometimes also be a little far away. 
// Perfect square numbers were giving an infinite loop before the while loop was fixed to accommodate for those numbers. 
// Some of them give exact answers and some very close.
class Root {
	public static void main(String[] args) {
		if (args.length == 0) { // Error to check if any input is entered
			System.err.println("ERROR");
			return;
		}
		
		double Args = Double.parseDouble(args[0]); // I found this method from here: https://stackoverflow.com/questions/6426491/parsing-string-to-double/6426531
		// Converting the input from a string into a double
		if (Args < 0){ // Error to check if a negative number is entered
			System.err.println("ERROR");
			return;
		}
		
		double low = 0.0;
		double high = Args;
		double avg = 0.0;
		
		while ((high - low) > (0.001 * low)) { // While loop condition
			avg = (high + low) / 2.0;
			if ((avg * avg) > Args) { // If higher
				high = avg;
			} else if ((avg * avg) < Args) { // If lower
				low = avg;
			} else { // Not specified by the question but needed in order to avoid an infinite loop when perfect square numbers are entered
				break;
			}
		}	
		System.out.println(avg); // Prints the square root answer
	}
}
