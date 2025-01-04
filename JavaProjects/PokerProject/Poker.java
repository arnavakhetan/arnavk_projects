// This program is called Poker.java and it can play a variation of poker with programs on other laptops through wifi and a dealer.
// The program also comes with an automatic test mode that immediately runs when the program is ran. It takes in samples of login, bet1, bet2 and done game protocols and categorizes parts of their inputs like through testing the arrays of the cards.
// I did not directly test by betting logic in code because it is pretty simple and straightforward. 
// In Bet1, I check for the highest card (highest card compares my face up and face down to all others' face up cards)/pair and only then I bet, otherwise fold. 
// Bet2 checks for pair/triple otherwise fold. During class testing, this method always made my program last for a while and I always came in Top 3.
// It has one class - Poker.
// Author - Arnav Khetan
import java.net.Socket;
import java.io.IOException; // Importing all the required packages
import java.io.DataInputStream;
import java.io.DataOutputStream;
public class Poker {
	private static Socket socket; 
	public static void main(String[] args) throws IOException {
		System.out.println("Test mode will run automatically.");
		runTestMode();
		socket = new Socket(args[0], Integer.parseInt(args[1])); // Need to input (in command line) the ip network of dealer and ip port number of server
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		handleLogin(dis, dos); // Login game protocol method (first login, but handleLogin can be called later as well)
		while (true) {
			handleCommands(dis, dos); // This will keep going until handleDone is called and the program terminates
		}
	}
	
	private static void handleLogin(DataInputStream dis, DataOutputStream dos) throws IOException { // Reads the login command and writes the GitHubID:Avatar_name
		read(dis);
		write("arnavakhetan:Arnav", dos);
	}
	private static void handleCommands(DataInputStream dis, DataOutputStream dos) throws IOException { // Method used to identify which game protocols are being used
		String command = read(dis);
		if (command.startsWith("login")) { // Calls the login game protocol
			handleLogin(dis, dos);
		} else if (command.startsWith("bet1")) { // Calls the bet1 game protocol
			handleBet1(command, dos);
		} else if (command.startsWith("bet2")) { // Calls the bet2 game protocol
			handleBet2(command, dos);
		} else if (command.startsWith("status")) { // Calls the status game protocol
			handleStatus(command);
		} else if (command.startsWith("done")) { // Calls the done game protocol
			handleDone(command);
		}
	}
	private static void handleBet1(String command, DataOutputStream dos) throws IOException {
		String[] parts = command.split(":"); // Using the ":" in the bet1 input to identify the various values given in the input protocol
		int chipsInStack = Integer.parseInt(parts[1]);
		int currentBetToMatch = Integer.parseInt(parts[3]);
		String holeCard = parts[4]; 
		String upCard = parts[5];
		
	    
		int previousBet = 0;
		int remainingAmount = currentBetToMatch - previousBet;
	    int myFaceDownValue = getCardValue(extractCardValue(holeCard)); // Extracting card values while converting letters like "K" into 13
	    int myFaceUpValue = getCardValue(extractCardValue(upCard));
	  
		int upIndex = 0; // Checking for the index of where the up part is mentioned in the given protocol such that the array for other players' face up cards can be taken correctly
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].equals("up")) {
				upIndex = i;
				break;
			}	
		}
		int otherPlayersCardCount = parts.length - (upIndex + 1);
		int[] otherPlayersUpCards = new int[otherPlayersCardCount];
		for (int i = 0; i < otherPlayersCardCount; i++) {
			otherPlayersUpCards[i] = getCardValue(extractCardValue(parts[upIndex + 1 + i])); // Making the array for other players' face up cards
		}
		
		boolean hasPair = checkPair(myFaceUpValue, myFaceDownValue);
	    boolean isHighest = isHighestCard(myFaceUpValue, myFaceDownValue, otherPlayersUpCards); // Calling the methods
	    
	    if (currentBetToMatch > chipsInStack) { // Betting logic for Bet 1
	        System.out.println("Not enough chips to match the bet. Folding.");
	        write("fold", dos);
	    } else if (hasPair) {
	        System.out.println("You have a pair. Betting with " + remainingAmount);
	        write("bet:" + remainingAmount, dos); // Betting the current match amount
	        previousBet = previousBet + remainingAmount;
	    } else if (!isHighest) {
	        System.out.println("You don't have the highest card. Folding.");
	        write("fold", dos); 
	    } else {
	        System.out.println("You have the highest card. Matching bet with " + remainingAmount);
	        write("bet:" + remainingAmount, dos); // Matching the current bet
	        previousBet = previousBet + remainingAmount;
	    }
	}    
	
	private static void handleBet2(String command, DataOutputStream dos) throws IOException {
		String[] bet2Parts = command.split(":");
		int chipsInStackBet2 = Integer.parseInt(bet2Parts[1]);
	    int currentBetToMatchBet2 = Integer.parseInt(bet2Parts[3]);
	    String holeCardBet2 = bet2Parts[4];
	    String upCard1Bet2 = bet2Parts[5];
	    String upCard2Bet2 = bet2Parts[6]; // Bet 2 now introduces my program's second face up card
	    int upIndexBet2 = 0;
	    for (int i = 0; i < bet2Parts.length; i++) {
	        if (bet2Parts[i].equals("up")) { // Checking for up again for the array
	            upIndexBet2 = i;
	            break;
	        }
	    }
	    int previousBet2 = 0;
	    int remainingAmountBet2 = currentBetToMatchBet2 - previousBet2;
	    int otherPlayersCardCountBet2 = bet2Parts.length - (upIndexBet2 + 1);
	    int numberOfPlayersBet2 = otherPlayersCardCountBet2 / 2;
	    
	    int[] otherPlayersFirstUpCards = new int[numberOfPlayersBet2];
	    int[] otherPlayersSecondUpCards = new int[numberOfPlayersBet2];

	    // Looping to extract and assign each player's two up cards
	    int playerIndexBet2 = 0;
	    for (int i = 0; i < otherPlayersCardCountBet2; i += 2) {
	        String firstCard = bet2Parts[upIndexBet2 + 1 + i];
	        String secondCard = bet2Parts[upIndexBet2 + 1 + i + 1];
	        otherPlayersFirstUpCards[playerIndexBet2] = getCardValue(extractCardValue(firstCard));
	        otherPlayersSecondUpCards[playerIndexBet2] = getCardValue(extractCardValue(secondCard));
	        playerIndexBet2++;
	    }

	    // Extracting and evaluating my program's cards
	    int myFaceDownValueBet2 = getCardValue(extractCardValue(holeCardBet2));
	    int myFaceUpValue1Bet2 = getCardValue(extractCardValue(upCard1Bet2));
	    int myFaceUpValue2Bet2 = getCardValue(extractCardValue(upCard2Bet2));
	    
	    boolean hasPairBet2 = checkPairBet2(myFaceUpValue1Bet2, myFaceUpValue2Bet2, myFaceDownValueBet2);
	    boolean hasTripleBet2 = checkTripleBet2(myFaceUpValue1Bet2, myFaceUpValue2Bet2, myFaceDownValueBet2);
	    
	    if (currentBetToMatchBet2 > chipsInStackBet2) { // Betting logic for Bet 2
	        System.out.println("Not enough chips to match the bet in Bet 2. Folding.");
	        write("fold", dos);
	    } else if (hasTripleBet2) {
	        System.out.println("You have a triple. Betting with " + remainingAmountBet2);
	        write("bet:" + remainingAmountBet2, dos);
	        previousBet2 = previousBet2 + remainingAmountBet2;
	    } else if (hasPairBet2) {
	        System.out.println("You have a pair in Bet 2. Betting with " + remainingAmountBet2);
	        write("bet:" + remainingAmountBet2, dos);
	        previousBet2 = previousBet2 + remainingAmountBet2;
	    } else {
	        System.out.println("No pair or triple. Folding.");
	        write("fold", dos);
	    }
	}
	private static String extractCardValue(String card) { // Taking just the value from the cards as suits don't matter
	    if (card.startsWith("10")) {
	        return "10"; // Checking if the card is a 10
	    } else {
	        return card.substring(0, 1); // Checking if the card is anything but a 10. If it is it will take that letter/number as the value of the card
	    }
	}
	
	private static int getCardValue(String card) { // Using this method to assign values for the letter cards
		switch (card) {
		case "A": return 14;
		case "K": return 13;
        case "Q": return 12;
        case "J": return 11;
        case "10": return 10;
        case "9": return 9;
        case "8": return 8;
        case "7": return 7;
        case "6": return 6;
        case "5": return 5;
        case "4": return 4;
        case "3": return 3;
        case "2": return 2;
        default: return -1;
		}
	}
	private static boolean checkPair(int myFaceUpValue, int myFaceDownValue) {
		return myFaceUpValue == myFaceDownValue; // Checks for pair
	}
	private static boolean isHighestCard(int myFaceUpValue, int myFaceDownValue, int[] otherPlayersUpCards) { // Compares the higher of my 2 cards to other players' face up cards
		int myHighestCardValue = Math.max(myFaceUpValue, myFaceDownValue);
		for (int opponentCardValue : otherPlayersUpCards) {
			if (opponentCardValue > myHighestCardValue) {
				return false;
			}
		}
		return true;
	}
	private static boolean checkTripleBet2(int myFaceUpValue1Bet2, int myFaceUpValue2Bet2, int myFaceDownValueBet2) {
	    return myFaceUpValue1Bet2 == myFaceUpValue2Bet2 && myFaceUpValue2Bet2 == myFaceDownValueBet2; // Checks for triple
	}
	
	private static boolean checkPairBet2(int myFaceUpValue1Bet2, int myFaceUpValue2Bet2, int myFaceDownValueBet2) {
	    return myFaceDownValueBet2 == myFaceUpValue1Bet2 || myFaceDownValueBet2 == myFaceUpValue2Bet2 || myFaceUpValue1Bet2 == myFaceUpValue2Bet2; // Checks for pair in Bet 2 (as more cards to check now)
	}
	
	private static void handleStatus(String command) { // Only using the win or lose part from status
		String[] partsStatus = command.split(":");
		String resultStatus = partsStatus[1];
		if (resultStatus.equals("win")) {
			System.out.println("I won!");
		} else if (resultStatus.equals("lose")) {
			System.out.println("I lost!");
		}
	}
	
	private static void handleDone(String command) {
		System.out.println("Game over: " + command); 
		try {
			if (socket != null && !socket.isClosed()) { // Required for the socket.close() line otherwise an error comes
				socket.close();
			}
		} catch (IOException e) { // Checking for the IOException and catching it if it's there
			e.printStackTrace();
		}
		System.exit(0); // Instantly terminates the program
		
	}
	
	private static void runTestMode() { // Test mode
		System.out.println("Test mode active.");
		handleTestLogin("arnavakhetan:Arnav");
		String[] testCommands = {"login", "bet1:500:0:12:KS:10D:up:AS:8H:10D:QD:2C", // Hardcoded array of trial game protocols
				"bet1:500:0:12:KS:10D:up:AS:KH:7C:5D:6S",
				"bet2:500:10:20:KS:10D:9C:up:AS:AH:8C:9H:QD:JC:2C:4H",
		        "bet2:500:10:5:7C:9D:3S:up:4C:7H:5D:6S:10C:2S:KH:QS",
		        "done"};
		for (String commands : testCommands) {
			if (commands.startsWith("login")) {
				handleTestLogin(commands);
			} else if (commands.startsWith("bet1")) { 
				handleTestBet1(commands);
			} else if (commands.startsWith("bet2")) { // Same type of code as in the socket version
                handleTestBet2(commands); 
            } else if (commands.startsWith("done")) {
                handleTestDone(commands);
            }
		}
	}
	
	private static void handleTestLogin(String login) { // Test login game protocol
		System.out.println("arnavakhetan:Arnav"); // Will always print the same values of my Github ID and avatar name
	}
	
	private static void handleTestDone(String commandTest) { // Test done game protocol
		System.out.println("Program ending...");
		// Instantly terminates the program
	}
	
	private static void handleTestBet1(String commands) {
		System.out.println("Received bet1 command: " + commands);
		String[] partsTest = commands.split(":");
		
		int currentBetToMatchTest = Integer.parseInt(partsTest[3]);
		String holeCardTest = partsTest[4];
		String upCardTest = partsTest[5];
		
	    System.out.println("Chips in stack: " + 500); // Basic print statements
	    System.out.println("Current pot size: " + 0);
	    System.out.println("Current bet to match: " + currentBetToMatchTest);
	    System.out.println("Your faced-down card: " + holeCardTest);
	    System.out.println("Your face-up card: " + upCardTest);
	    
	    System.out.print("Other players' up cards: ");
	    for (int i = 7; i < partsTest.length; i++) {
	        System.out.print(partsTest[i] + " "); // Iterates through to correctly print out the other players' face up cards
	    }
	    System.out.println();
	}	
	private static void handleTestBet2(String commands) {
		System.out.println("Received bet2 command: " + commands);
	    String[] partsTest = commands.split(":");

	    int currentBetToMatchTest = Integer.parseInt(partsTest[3]);
	    String holeCardTest = partsTest[4];
	    String upCard1Test = partsTest[5];
	    String upCard2Test = partsTest[6];
	    System.out.println("Chips in stack: " + 500);
	    System.out.println("Current pot size: " + 0);
	    System.out.println("Current bet to match: " + currentBetToMatchTest);
	    System.out.println("Your faced-down card: " + holeCardTest);
	    System.out.println("Your first face-up card: " + upCard1Test);
	    System.out.println("Your second face-up card: " + upCard2Test); // More cards to check now for both me, and the other players

	    System.out.print("Other players' up cards: ");
	    for (int i = 8; i < partsTest.length; i += 2) {
	        System.out.print(partsTest[i] + " " + partsTest[i + 1] + " | "); // Correctly prints out double face up cards for each other player, using | to separate
	    }
	    System.out.println();
	}
	private static void write(String s, DataOutputStream dos) throws IOException { // Write method
		dos.writeUTF(s);
		dos.flush();
	}
	private static String read(DataInputStream dis) throws IOException { // Read method
		return dis.readUTF();
	}
}
