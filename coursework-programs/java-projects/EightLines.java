// This program takes in your input through the Scanner class (while only accepting 8lines worth of code).
// It then prints out each line as its entered and each word is printed out separately in its own line.
import java.util.Scanner; // Import the Scanner class for taking in the input
class EightLines {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // Read in input
		
		for (int i = 0; i < 8; i++) { // To control the number of lines to 8
			if (scanner.hasNextLine()) {
				String inputLine = scanner.nextLine();
				
				Scanner lineScanner = new Scanner(inputLine); // Creating a scanner
				
				while (lineScanner.hasNext()) {
					String word = lineScanner.next(); // Read the next word
					System.out.println(word); // Print the word on its own line
				}
				lineScanner.close();
			} else {
				break; // Exit the loop if fewer than 8 lines were given in the input
			}
		}
		scanner.close(); // Close the scanner
	}
}
