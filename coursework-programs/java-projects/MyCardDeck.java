// This program understands if a pair of a value and a suit is an acceptable card.
// If it is, the card will be printed showing that the program understands that it is a proper card in a regular 52 card deck.
// If it's not, then the program will print "0ERROR" symbolizing that an error has happened.
// If no input is entered, then the program will print out the whole 52 card deck randomly and it will be completely shuffled each time it prints out.
// There are 3 classes in this program: Card, Deck, and MyCardDeck.
// Card represents an individual card and handles the part of how the card should be registered and printed out.
// Deck represents the entire 52 card deck and how it should be randomly shuffled and printed out.
// MyCardDeck has the main method and actually prints out the outputs from Card/Deck

import java.util.Random; // Importing the random package which will be used in the shuffle() method

class Card {
    private int value;
    private String suit;

    public Card(int value, String suit) {
        if (value >= 2 && value <= 14) { // Checking if value is between the required range
            char suitChar = Character.toUpperCase(suit.charAt(0)); // Making it upper case because then it's easier to check for the suit
            if (suitChar == 'S' || suitChar == 'H' || suitChar == 'C' || suitChar == 'D') { // If suit is one of the required suits
                this.value = value;
                this.suit = "" + suitChar; // To convert the suit character into a string as suit is defined as a String
            } else {
                this.value = 0; // Result of error in suit
                this.suit = "ERROR";
            }       
        } else {
            this.value = 0;
            this.suit = "ERROR"; // Result of error in value
        }
    }

    public Card(String cardStr) {
        if (cardStr == null || cardStr.length() < 2) { // Helping to check for errors
            this.value = 0;
            this.suit = "ERROR";
            return;
        }

        int pos = 0; // Creating this variable to help with the part of making sure that 10 is accepted as a value
        String valueStr = "";
        cardStr = cardStr.toUpperCase(); // Converting the entire string to upper case

        // Handle the 10 value card
        if (cardStr.charAt(pos) == '1' && cardStr.length() > pos + 1 && cardStr.charAt(pos + 1) == '0') {
            valueStr = "10";
            pos += 2;
        } else {
            char firstChar = cardStr.charAt(pos);
            if ((firstChar >= '2' && firstChar <= '9') || firstChar == 'J' || firstChar == 'Q' || firstChar == 'K' || firstChar == 'A') {
                valueStr = "" + firstChar;
                pos += 1;
            } else {
                this.value = 0;
                this.suit = "ERROR";
                return;
            }
        }

        if (cardStr.length() <= pos) { // Checking to see if a suit character has been entered or not
            this.value = 0;
            this.suit = "ERROR";
            return;
        }

        char suitChar = cardStr.charAt(pos); // Getting the suit character
        if (suitChar == 'S' || suitChar == 'H' || suitChar == 'C' || suitChar == 'D') {
            this.suit = "" + suitChar;
        } else {
            this.value = 0;
            this.suit = "ERROR";
            return;
        }

        switch (valueStr) { // Setting the value based on the valueStr
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
                this.value = Integer.parseInt(valueStr); // Converting the string value into an integer
                break;
            case "J":
                this.value = 11;
                break;
            case "Q":
                this.value = 12;
                break;
            case "K":
                this.value = 13;
                break;
            case "A": // Making sure the program registers the number conversions for the letter values
                this.value = 14;
                break;
            default:
                this.value = 0;
                this.suit = "ERROR";
                break;
        }
    }

    public int Value() {
        return this.value; // value method
    }

    public String suit() {
        return this.suit; // suit method
    }

    public String toString() { 
        String valueStr = "";
        switch (this.value) { // switch case conditional
            case 11: 
                valueStr = "J"; 
                break;
            case 12: 
                valueStr = "Q"; 
                break;
            case 13: 
                valueStr = "K"; 
                break;
            case 14: 
                valueStr = "A"; 
                break;
            default: 
                valueStr = Integer.toString(this.value); 
                break;
        }
        return valueStr + this.suit;
    }
}   

class Deck {
    private Card[] allMyCards = new Card[52];
    public Deck() {
        String[] suits = {"spades", "hearts", "clubs", "diamonds"}; // Making the array of the 52 card deck
        int cardIndex = 0;
        for (int i = 0; i < suits.length; i++) {
            String suitName = suits[i];
            for (int cardValue = 2; cardValue <= 14; cardValue++) {
                allMyCards[cardIndex] = new Card(cardValue, suitName); // Storing the value and suit at once
                cardIndex = cardIndex + 1;
            }
        }
    }
    
    public String toString() { // toString method
        String deckString = "";
        for (int i = 0; i < allMyCards.length; i++) {
            deckString = deckString + allMyCards[i].toString();
            if (i < allMyCards.length - 1) {
                deckString = deckString + " ";
            }
        }
        return deckString;
    }
    
    public void shuffle() {
        Random random = new Random(); // Using this package (imported at the start of the program) to help in randomly shuffling the deck
        for (int i = allMyCards.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = allMyCards[i];
            allMyCards[i] = allMyCards[j];
            allMyCards[j] = temp; 
        }       
    }
}           

class MyCardDeck {
    public static void main(String[] args) { // Main method
        if (args.length > 0) {
            Card yourCard = new Card(args[0]);
            System.out.println(yourCard); // Prints out the card if all is well/returns the 0ERROR
        } else {
            Deck myDeck = new Deck();
            myDeck.shuffle();
            System.out.println(myDeck); // Prints out the entire 52 card deck randomly shuffled if no input has been entered
        }
    }
}
