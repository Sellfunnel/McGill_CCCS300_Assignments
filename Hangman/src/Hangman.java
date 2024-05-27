import java.util.Scanner;
public class Hangman {
	public static void main(String[] args) {
		int attemptsLeft = 10;
		boolean gameWon = false;
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Scanner scanner = new Scanner(System.in);
		System.out.println("-------------------------------\n"
				+ "       Welcome to HANGMAN       \n"
				+ "-------------------------------\n\n"
				+ "OK Guessing Player ... turn around, while your friend enters the word to guess!\n");
		
		String wordToGuess; // make sure wordToGuess <= 10
		while (true){
			 wordToGuess = scanner.nextLine().toLowerCase();
			 System.out.println("Other Player - Enter your word (up to 10 letters only, not case sensitive): " + wordToGuess);
			 if (wordToGuess.length() <= 10 && wordToGuess.matches("[a-zA-Z]+")) { // Ensure only letters are allowed
				 break;
			 }
			 	else{
				 System.out.println("Error: Word must be up to 10 letters and only contain letters. Please try again.");
			 	}
		}		
		
		String displayWord = "";
		for (int i = 0; i < wordToGuess.length(); i++){
			displayWord += "*";
		}	
		
		// Clear screen by printing blank lines
		for (int i = 0; i<20; i++){
			System.out.println("\n");
		}
		
		while (attemptsLeft > 0 && !gameWon){
			System.out.println("Word to date: " + displayWord + " (" + attemptsLeft + " guess(es) left)");
			System.out.println("Letters to try: " + letters);
			System.out.println("Want to solve the puzzle? Enter \"Y\" to solve the puzzle, or \"N\" to guess a character: ");
			String action = scanner.nextLine().trim().toUpperCase();			
			switch (action){
				case "Y":{
					System.out.println("Enter your solution: ");
					String solutionGuess = scanner.nextLine().toLowerCase();
					if (solutionGuess.equals(wordToGuess)){
							gameWon = true;
							attemptsLeft--;
					}
						else{
						System.out.println("Incorrect solution. ");
						attemptsLeft--;
						}
					if (displayWord.equals(wordToGuess)) {
						gameWon = true;
					}
					break;
				}
				case "N": {
					boolean validInput = false;
					String charGuess = "";
					while (!validInput) {
						System.out.println("Which letter should I check for? ");
						charGuess = scanner.nextLine().toUpperCase().trim();
						if (charGuess.length() == 1 && Character.isLetter(charGuess.charAt(0)) && letters.contains(charGuess)) { // If the letter is not found, it means the player has already guessed that letter
							validInput = true;
							}
						else {
							System.out.println("Invalid input or letter already tried. Please enter a valid new character.");
				        }
					}
					
					/* 1. check input char correct in displayWord. if true, correctGuess = true.
					because !correctGuess, it will only show the first repeated character in newDisplayWord per guess.*/
				    boolean correctGuess = false;
				    String newDisplayWord = "";
				    for(int i = 0; i < wordToGuess.length(); i++) { //wordToGuess is lowerCase, replace * in displayWord to correct guessed char. 
				    	if (!correctGuess && wordToGuess.charAt(i) == charGuess.toLowerCase().charAt(0) && displayWord.charAt(i) == '*') {
				    		newDisplayWord += charGuess.toUpperCase(); 
				    		correctGuess = true;
				    	}
				    	else {
				    		newDisplayWord += displayWord.charAt(i); 
				    	}
				    }
				    attemptsLeft--; // no matter correctGuess is true/false as assignment want.
				    
		    		/* 2. if there is repeated character in wordToGuess, it will stop on first repeated character correctly guessed.
		    		 * then in allOccurenceGuessed, if there is no * in wordToGuess, then it is true, otherwise, it will be false.
		    		 */
				    if (correctGuess) {
				    	displayWord = newDisplayWord;				    		
				    	boolean allOccurrencesGuessed = true;
				        for (int i = 0; i < wordToGuess.length(); i++) {
				            if (wordToGuess.charAt(i) == charGuess.toLowerCase().charAt(0) && displayWord.charAt(i) == '*') {
				                allOccurrencesGuessed = false;
				                break;
				            }
				        }
				        
				        if (allOccurrencesGuessed) {
				        	// Replace the guessed letter in letters with an asterisk
				        	int index = letters.indexOf(charGuess);
					    	if (index != -1) {
					    	letters = letters.substring(0, index) + "*" + letters.substring(index + 1); //replace guessed letter to *
					    	}
				        }

				        System.out.println("Correct guess.");
				    }	
				    else {
				    	System.out.println("Incorrect guess.");
				    }
				    	
				    if(displayWord.equalsIgnoreCase(wordToGuess)) { // Use equalsIgnoreCase to ignore case differences
				    	gameWon = true;
				    }
				    break;
				}
			}		
		}
		if(gameWon){
			System.out.println("Congratulations!!!\n"
					+ "You guessed the mystery word \"" + wordToGuess.toUpperCase() + "\" in " + attemptsLeft +" guesses!");
		}
		else {
			System.out.println("Sorry you didn't find the mystery word!\n"
					+ "It was \"" + wordToGuess.toUpperCase() + "\"\n\n" + "Goodbye....");
		}
		scanner.close();
	}
}