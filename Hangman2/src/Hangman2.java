import java.util.Arrays;
import java.util.Scanner;

public class Hangman2 {
    public static void main(String[] args) {
        int attemptsLeft = 10;
        boolean gameWon = false;
        char[] letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------------------------------\n"
                + "       Welcome to HANGMAN       \n"
                + "-------------------------------\n\n"
                + "OK Guessing Player ... turn around, while your friend enters the word to guess!\n");

        char[] wordToGuess = new char[10]; // Assuming a maximum of 10 characters
        int wordLength = 0; // To keep track of the actual length of the word

        // Read and validate the word to guess character by character
        System.out.print("Other Player - Enter your word (up to 10 letters only, not case sensitive): ");
        while (true) {
            char[] inputChars = scanner.nextLine().toLowerCase().toCharArray();
            boolean validWord = true;
            
            for (int i = 0; i < inputChars.length && i < 10; i++) {
                char c = inputChars[i];
                if (Character.isLetter(c)) {
                    wordToGuess[wordLength++] = c;
                }
                else {
                    System.out.println("Error: Word must only contain letters. Please try again.");
                    wordLength = 0; // Reset word length to start over
                    validWord = false;
                    break;
                }
            }

            if (validWord && wordLength > 0 && wordLength <= 10) {
                break;
            }
            else {
                System.out.print("Other Player - Enter your word (up to 10 letters only, not case sensitive): ");
            }
        }

        char[] displayWord = new char[wordLength];
        Arrays.fill(displayWord, '*');

		// Clear screen by printing blank lines
		for (int i = 0; i<20; i++){
			System.out.println("\n");
		}
        
        // Game loop
		while (attemptsLeft > 0 && !gameWon) {
			System.out.println("Word to date: " + String.valueOf(displayWord) + " (" + attemptsLeft + " guess(es) left)");
		    System.out.print("Letters to try: ");
		    for (char c: letters) {
		    	System.out.print(c);
		    }
		    System.out.println("\nWant to solve the puzzle? Enter 'Y' to solve the puzzle, or 'N' to guess a character: ");

		    char action = ' ';
		    int charCount = 0; // Counter for the number of characters entered
		    while (true) {
		        char inputChar = scanner.findInLine(".").charAt(0); // Read a single character
		        charCount++;
		        
		        // Check if the input is complete (by trying to read another character)
		        if (scanner.findInLine(".") == null) {
		            scanner.nextLine(); // Consume the newline
		            
		            // Validate the input if only one character was entered
		            if (charCount == 1 && (inputChar == 'Y' || inputChar == 'N' || inputChar == 'y' || inputChar == 'n')) {
		                action = inputChar;
		                break;
		            }
		            else {
		                System.out.println("Invalid input. Please enter 'Y' or 'N': ");
		                charCount = 0; // Reset the counter for the next attempt
		            }
		        } 
		        else {
		            System.out.println("Invalid input. Please enter only one character: ");
		            scanner.nextLine(); // Consume the rest of the input
		            charCount = 0; // Reset the counter for the next attempt
		        }
		    }

		    // Convert lowercase input to uppercase for consistent handling
		    if (action == 'y') {
		        action = 'Y';
		    } 
		    else if (action == 'n') {
		        action = 'N';
		    }

		    switch (action) {
		        case 'Y': {
		            System.out.print("Enter your solution: ");
		            char[] solutionGuess = scanner.nextLine().toLowerCase().toCharArray();
		            if (Arrays.equals(solutionGuess, Arrays.copyOf(wordToGuess, wordLength))) {
		                gameWon = true;
		                attemptsLeft--;
		            }
		            else {
		                System.out.println("Incorrect solution.");
		                attemptsLeft--;
		            }
		            break;
		        }
		        
		        case 'N': {
		            boolean validInput = false;
		            char charGuess = ' ';
		            while (!validInput) {
		                System.out.println("Which letter should I check for? ");
		                charGuess = scanner.next().toUpperCase().charAt(0);
		                scanner.nextLine(); // Consume the rest of the line
		                if (Character.isLetter(charGuess) && new String(letters).indexOf(charGuess) != -1) { 
		                    validInput = true;
		                }
		                else {
		                    System.out.println("Invalid input or letter already tried. Please enter a valid new character.");
		                }
		            }

		            boolean correctGuess = false;
		            char[] newDisplayWord = new char[displayWord.length];
		            for (int i = 0; i < wordLength; i++) {
		                if (!correctGuess && wordToGuess[i] == Character.toLowerCase(charGuess) && displayWord[i] == '*') {
		                    newDisplayWord[i] = Character.toUpperCase(charGuess);
		                    correctGuess = true;
		                }
		                else {
		                    newDisplayWord[i] = displayWord[i];
		                }
		            }
		            attemptsLeft--;

		            if (correctGuess) {
		                displayWord = newDisplayWord;
		                boolean allOccurrencesGuessed = true;
		                for (int i = 0; i < wordToGuess.length; i++) {
		                    if (wordToGuess[i] == Character.toLowerCase(charGuess) && displayWord[i] == '*') {
		                        allOccurrencesGuessed = false;
		                        break;
		                    }
		                }

		                if (allOccurrencesGuessed) {
		                    int index = new String(letters).indexOf(charGuess);
		                    if (index != -1) {
		                        letters[index] = '*';
		                    }
		                }

		                System.out.println("Correct guess.");
		            }
		            else {
		                System.out.println("Incorrect guess.");
		            }	          
		            
		            char[] wordToGuessUpper = Arrays.copyOf(wordToGuess, wordLength);
		            for (int i = 0; i < wordToGuessUpper.length; i++) {
		                wordToGuessUpper[i] = Character.toUpperCase(wordToGuessUpper[i]);
		            }

		            if (Arrays.equals(displayWord, wordToGuessUpper)) { //arrays.equals() method is case-sensitive!!!
		                gameWon = true;
		            }
		            break;
		        }
		    }
		}
		
        // End of game messages
        if (gameWon) {
            System.out.println("Congratulations!!!\n"
                    + "You guessed the mystery word "
            + String.valueOf(wordToGuess).trim()
            + " with " + attemptsLeft + " guess(es) left!");
        }
        else {
            System.out.println("Sorry, you didn't find the mystery word!\n"
                    + "It was "
            + String.valueOf(wordToGuess).trim()
            + "\n\nGoodbye....");
        }
        scanner.close();
    }
}
    	


