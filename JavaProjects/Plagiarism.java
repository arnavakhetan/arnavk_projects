// Plagiarism.java 
// Author: Arnav Khetan

class WordCounter {
	int totWords = 0; 
	int theWords = 0; 
	int aWords = 0;
	void getWord(String w) {
		w = w.replaceAll("[?/.,]","");
		totWords++;
		if (w.equalsIgnoreCase("the")) {
			theWords++;
		}
		if (w.equalsIgnoreCase("a") || w.equalsIgnoreCase("an")) {
			aWords++; 
		}
	}
	
	String statistics() {
		// Calculate percentages and print the results as
		// an integer percentage (in range from 0 thru 100).
		System.out.println("Percentage of THE words is " +
				((int) (0.5 + 100.0 * theWords / totWords)));
		System.out.println("Percentage of A or AN words is " +
				((int) (0.5 + 100.0 * aWords / totWords)));
		return "";
	}	
}


/** class Plagiarism
 *
 * Read a single command line argument, which contains multiple words.
 * Calculate and print the percentage of words that are 'the' or 'a' and
 * 'an'.  Ignore upper/lowercase, ignore punctuation, spacing, etc.
 */
public class Plagiarism {

	static public void main(String[] args) {
		// Input is expected in args[0]. Make sure input exists.
		if (args.length < 1) { return; }
		
		// Handle each word.
		WordCounter wc = new WordCounter();
		// or String inp = args[0];
		String[] words = args[0].split(" ");
		for (int i = 0; i < words.length; i++) {
			wc.getWord(words[i]);
		}
		
		// Print final results.
		wc.statistics();
	} // end main()
} // end class Plagiarism
