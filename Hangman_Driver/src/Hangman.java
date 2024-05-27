import java.util.Arrays;
import java.util.Scanner;

public class Hangman {
    private static int nextGameId = 1;
    private int gameId;
    private int attemptsLeft;
    private boolean gameWon;
    private char[] letters;
    private char[] wordToGuess;
    private char[] displayWord;
    private int wordLength;

    public Hangman() {
        gameId = nextGameId++;
        attemptsLeft = 10;
        gameWon = false;
        letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        wordToGuess = new char[10];
        displayWord = null;
        wordLength = 0;
    }
    
    private void clearScreen() {
    	for (int i = 0; i<20; i++){
        	System.out.println("\n");		
        }
    }

    private void updateDisplayWord(char charGuess) {
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

            System.out.println("Correct guess.\n");
        }
        else {
            System.out.println("Incorrect guess.\n");
        }
    }
    
    private void checkGameWon() {
    	char[] wordToGuessUpper = Arrays.copyOf(wordToGuess, wordLength);
        for (int i = 0; i < wordToGuessUpper.length; i++) {
            wordToGuessUpper[i] = Character.toUpperCase(wordToGuessUpper[i]);
        }

        if (Arrays.equals(displayWord, wordToGuessUpper)) { //arrays.equals() method is case-sensitive!!!
            gameWon = true;
        }
    }
    
    public void initializeGame_collectWord(Scanner keyIn) {
    	System.out.println("-------------------------------\n"
                + "       Welcome to HANGMAN       \n"
                + "-------------------------------\n\n"
                + "OK Guessing Player ... turn around, while your friend enters the word to guess!\n");
        System.out.println("GameID " + gameId + ": Other Player - Enter your word (up to 10 letters only, not case sensitive): ");
        while (true) {
            char[] inputChars = keyIn.nextLine().toLowerCase().toCharArray();
            boolean validWord = true;
            
            for (int i = 0; i < inputChars.length && i < 10; i++) {
                char c = inputChars[i];
                if (Character.isLetter(c)) {
                    wordToGuess[wordLength++] = c;
                }
                else {
                    System.out.println("GameID " + gameId + ": Error: Word must only contain letters. Please try again.");
                    wordLength = 0;
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

        displayWord = new char[wordLength];
        Arrays.fill(displayWord, '*');

        // Clear screen by printing blank lines
        clearScreen();
    }

    public boolean play_a_guess(Scanner keyIn) {
    	if (gameWon || attemptsLeft <= 0) {
            System.out.println("GameID " + gameId + " is already completed.");
            return false;
        }

        System.out.println("GameID " + gameId + ": Word to date: " + String.valueOf(displayWord) + " (" + attemptsLeft + " guess(es) left)");
        // Display the letters to try
        System.out.print("GameID " + gameId + ": Letters to try: ");
        for (char c : letters) {
            System.out.print(c);
        }
        System.out.println();
        
        // Prompt for guessing a letter or the full word
        System.out.println("GameID " + gameId + ": Want to solve the puzzle? Enter 'Y' to solve the puzzle, or 'N' to guess a character: ");

        char action = ' ';
	    int charCount = 0; // Counter for the number of characters entered
	    while (true) {
	        char inputChar = keyIn.findInLine(".").charAt(0); // Read a single character
	        charCount++;
	        
	        // Check if the input is complete (by trying to read another character)
	        if (keyIn.findInLine(".") == null) {
	            keyIn.nextLine(); 
	            
	            // Validate the input if only one character was entered
	            if (charCount == 1 && (inputChar == 'Y' || inputChar == 'N' || inputChar == 'y' || inputChar == 'n')) {
	                action = inputChar;
	                break;
	            }
	            else {
	                System.out.println("GameID " + gameId + ": Invalid input. Please enter 'Y' or 'N'.");
	                charCount = 0; // Reset the counter for the next attempt
	            }
	        } 
	        else {
	            System.out.println("GameID " + gameId + ": Invalid input. Please enter only one character.");
	            keyIn.nextLine(); 
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
	            char[] solutionGuess = keyIn.nextLine().toLowerCase().toCharArray();
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
	                charGuess = keyIn.next().toUpperCase().charAt(0);
	                keyIn.nextLine();
	                if (Character.isLetter(charGuess) && new String(letters).indexOf(charGuess) != -1) { 
	                    validInput = true;
	                }
	                else {
	                    System.out.println("Invalid input or letter already tried. Please enter a valid new character.");
	                }
	            }

	            updateDisplayWord(charGuess);
	            
	            checkGameWon();
	            break;
	        }
	    }
	    
	    // Print the final game outcome if the game is completed
	    if (gameWon) {
	        System.out.println("GameID " + gameId + ": Congratulations!!!\n"
	                + "You guessed the mystery word "
	                + String.valueOf(wordToGuess).trim()
	                + " with " + attemptsLeft + " guess(es) left!\n"
	                + "GameID " + gameId + ": Goodbye ....\n");
	        return false;
	    }
	    else if (attemptsLeft <= 0) {
	        System.out.println("GameID " + gameId + ": Sorry, you didn't find the mystery word!\n"
	                + "It was "
	                + String.valueOf(wordToGuess).trim()
	                + "\n\nGoodbye....\n");
	        return false;
	    }
	    
	    // Return true if the game is still active
	    return true;
    }
       
    public void playGame(Scanner keyIn) {
    	while (!gameWon && attemptsLeft > 0) {
            boolean continueGame = play_a_guess(keyIn);
            if (!continueGame) {
                break; // Exit the loop if the game is completed
            }
        }
    }
}
