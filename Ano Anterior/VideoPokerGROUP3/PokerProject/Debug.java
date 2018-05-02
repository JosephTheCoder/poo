package PokerProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import Variants.Variants;

/**
 * Class {@link Debug} extends Class {@link Game} and it represents the Debug Mode of playing Video Poker.
 * <p>
 * When running in this mode, the game is fully loaded from two files:
 * <ul>
 * <li><tt>cmd-file</tt> - file with the commands for playing the game;</li>
 * <li><tt>card-file</tt> - file with the cards (already ordered) for the game.</li>
 * </ul>
 * <p>
 * This class has three more attributes besides the ones from its superclass:
 * <ul>
 * <li><tt>cardInput</tt> - array of {@link Character} that contains the content of the commands' file;</li>
 * <li><tt>cmdFileName</tt> - variable of type {@link String} which represents the name of the file with the commands for playing the game.</li>
 * <li><tt>cardsFileName</tt> - variable of type {@link String} which represents the name of the file with the cards (already ordered) for the game.</li>
 * </ul>
 */
public class Debug extends Game{
	
	/**
	 * Array of {@link Character} that contains the content of the commands' file.
	 */
	private char[] cardInput;
	/**
	 * Variable of type {@link String} which represents the name of the file with the commands for playing the game.
	 */
	private String cmdFileName;
	/**
	 * Variable of type {@link String} which represents the name of the file with the cards (already ordered) for the game.
	 */
	private String cardsFileName;
	
	/**
	 * Constructor specifying the initial balance of the player, the name of two files and the {@link Variants} of Video Poker being used.
	 * Some of these attributes are the same used to construct the superclass {@link Game}.
	 * @param initMoney initial balance of the player when starting the game.
	 * @param cmdNameStr name of the file with the commands for playing the game.
	 * @param cardNameStr name of the file with the cards (already ordered) for the game.
	 * @param optionTable variant of Video Poker used in the game.
	 */
	public Debug(int initMoney, String cmdNameStr, String cardNameStr, Variants optionTable){
		super(initMoney, optionTable);
		cmdFileName = cmdNameStr;
		cardsFileName = cardNameStr;
	}
	
	/**
	 * Play the game in Debug Mode.
	 * Reads the commands from a file, saves them in {@link cardInput},
	 * reads the cards to use in the game from a file, saves them in the {@link deck} and then acts accordingly
	 * until there are no more commands to read.
	 * Options available:
	 * <ul>
	 * <li>$ - ask for the player's credit;</li>
	 * <li>s - ask for the statistics of the game since the beginning;</li>
	 * <li>b - ask for betting a value;</li>
	 * <li>d - ask for dealing cards;</li>
	 * <li>h - ask for holding cards;</li>
	 * <li>a - ask for advice about what cards to hold.</li>
	 * </ul>
	 * <p>
	 * Note that an error message is printed in case of illegal command input.
	 * @throws IOException If an error occurs while reading inputs from file.
	 */
	public void play() throws IOException{
		// Read file [cmd-file.txt]
        BufferedReader cmdBr = new BufferedReader(new FileReader(new File(cmdFileName)));
		String cmdStr = cmdBr.readLine();
		char[] cmdInput = cmdStr.toCharArray();
		cmdBr.close();
		
		// Read file [card-file.txt]
        BufferedReader cardBr = new BufferedReader(new FileReader(new File(cardsFileName)));
		String cardStr = cardBr.readLine();
		cardInput = cardStr.toCharArray();
		cardBr.close();
		
		// In Debug Mode, the deck is given from the file for the whole game
		deck = new Deck(cardInput);
		
		int k = 0;
		// Go through the commands file while there are still characters to read
		while (k < cmdInput.length){
			// Verify each character
			switch (cmdInput[k]){
				// Ask for credit
				case '$' :
					// Check if it is the last character of the file
					if(k+1 < cmdInput.length){
						// Check if there is a space after credit command
						if (Character.isSpaceChar(cmdInput[k+1])){
							inputPlay = new char[1];
							inputPlay[0] = cmdInput[k];
							k = k+2; // next character
						} else {
							// This means invalid command
							inputPlay = new char[2];
							inputPlay[0] = cmdInput[k];
							inputPlay[1] = cmdInput[k+1];
							k = k+3; // next character
						}
					} else {
						// In case of last command of the file
						inputPlay = new char[1];
						inputPlay[0] = cmdInput[k];
						k = k+2; // next character
					}
					System.out.println("\n-cmd " + String.valueOf(inputPlay));
					doCredit();
					break;
				// Ask for statistics
				case 's' :
					// Check if it is the last character of the file
					if(k+1 < cmdInput.length){
						// Check if there is a space after statistics command
						if (Character.isSpaceChar(cmdInput[k+1])){
							inputPlay = new char[1];
							inputPlay[0] = cmdInput[k];
							k = k+2; // next character
						} else {
							// This means invalid command
							inputPlay = new char[2];
							inputPlay[0] = cmdInput[k];
							inputPlay[1] = cmdInput[k+1];
							k = k+3; // next character
						}
					} else {
						// In case of last command of the file
						inputPlay = new char[1];
						inputPlay[0] = cmdInput[k];
						k = k+2; // next character
					}
					System.out.println("\n-cmd " + String.valueOf(inputPlay));
					doStatistics();
					break;
	    		// Ask for bet (from 1 to 5)
	    		case 'b' :
	    			// Check if it is the last character of the file
	    			if(k+1 < cmdInput.length){
	    				// Check if there is a space after bet command
		    			if (Character.isSpaceChar(cmdInput[k+1])){
		    				// Check if there is a character after that corresponds to the value of the bet
		    				if ((k+2 < cmdInput.length) && Character.isDigit(cmdInput[k+2])){
		    					// Check if there are still characters to read in the file
		    					if (k+3 < cmdInput.length){
		    						// Check if there is a space after the value of the bet
		    						if (Character.isSpaceChar(cmdInput[k+3])){
		    							inputPlay = new char[3];
				    					inputPlay[0] = cmdInput[k];
				    					inputPlay[1] = cmdInput[k+1];
				    					inputPlay[2] = cmdInput[k+2];
				    					k = k+4; // next character
		    						} else {
		    							// This means invalid bet (two or more digit value)
		    							int auxk = Integer.valueOf(k); // To count number of characters (!= space) after first digit of the amount to bet
		    							int cntNr = 0;
	    			    				while(auxk+3 < cmdInput.length && !Character.isSpaceChar(cmdInput[auxk+3])){
	    			    					cntNr++;
	    			    					auxk++;
	    			    				}
	    			    				inputPlay = new char[3+cntNr]; // example: "b 123"
	    		    					inputPlay[0] = cmdInput[k];
	    		    					inputPlay[1] = cmdInput[k+1];
	    		    					inputPlay[2] = cmdInput[k+2];
	    		    					for(int idx=0; idx < cntNr; idx++){
	    			    					inputPlay[3+idx] = cmdInput[k+idx+3];
	    			    				}
	    		    					k = k+4+cntNr; // next character
		    						}
		    					} else {
		    						inputPlay = new char[3];
			    					inputPlay[0] = cmdInput[k];
			    					inputPlay[1] = cmdInput[k+1];
			    					inputPlay[2] = cmdInput[k+2];
			    					k = k+4; // next character
		    					}
		    				} else {
		    					// bet with no indication of value
		    					inputPlay = new char[1];
		    					inputPlay[0] = cmdInput[k];
		    					k = k+2; // next character
		    				}
						} else {
							// This means invalid bet
							inputPlay = new char[2];
							inputPlay[0] = cmdInput[k];
							inputPlay[1] = cmdInput[k+1];
							k = k+3; // next character
						}
	    			} else {
	    				// In case of last command of the file
	    				inputPlay = new char[1];
    					inputPlay[0] = cmdInput[k];
    					k = k+2; // next character
	    			}
	    			System.out.println("\n-cmd " + String.valueOf(inputPlay));
	    			doBet();
	    			break;
	    		// Ask for deal (give cards)
	    		case 'd' :
	    			// Check if it is the last character of the file
	    			if(k+1 < cmdInput.length){
	    				// Check if it is a space after deal command
		    			if (Character.isSpaceChar(cmdInput[k+1])){
		    				inputPlay = new char[1];
							inputPlay[0] = cmdInput[k];
							k = k+2; // next character
						} else {
							// This means invalid command
							inputPlay = new char[2];
							inputPlay[0] = cmdInput[k];
							inputPlay[1] = cmdInput[k+1];
							k = k+3; // next character
						}
	    			} else {
	    				// In case of last command of the file
	    				inputPlay = new char[1];
						inputPlay[0] = cmdInput[k];
						k = k+2;
	    			}
	    			System.out.println("\n-cmd " + String.valueOf(inputPlay));
	    			doDeal();
	    			break;
	    		// Ask for hold (from 0 to 5 cards)
	    		case 'h' :
	    			// Check if it is the last character of the file
	    			if(k+1 < cmdInput.length){
	    				// Check if it is a space after hold command
		    			if (Character.isSpaceChar(cmdInput[k+1])){
		    				int cntChar = 0; // To count cards to hold
		    				int kAux = Integer.valueOf(k+2);
		    				// Check if there are indexes of cards to hold
		    				while((kAux < cmdInput.length) && (Character.isDigit(cmdInput[kAux]) || Character.isSpaceChar(cmdInput[kAux]))){
		    					cntChar++;
		    					kAux++;
		    				}
		    				// In case no cards to hold
		    				if (cntChar == 0){
		    					inputPlay = new char[1];
		    					inputPlay[0] = cmdInput[k];
		    					k = k+2; // next character
		    				} else{
		    					// In case some cards to hold
		    					inputPlay = new char[2+cntChar]; // example: "h 1 2 3"
		    					inputPlay = Arrays.copyOfRange(cmdInput, k, k+cntChar+2);
		    					if (inputPlay[2+cntChar-1] == ' ') inputPlay = Arrays.copyOfRange(cmdInput, k, k+cntChar+1);
		    					k = k+2+cntChar; // next character
		    				}
						} else {
							// This means invalid command
							inputPlay = new char[2];
							inputPlay[0] = cmdInput[k];
							inputPlay[1] = cmdInput[k+1];
							k = k+3;
						}
	    			} else {
	    				// In case of last command of the file
	    				inputPlay = new char[1];
    					inputPlay[0] = cmdInput[k];
    					k = k+2;
	    			}
	    			System.out.println("\n-cmd " + String.valueOf(inputPlay));
	    			doHold();
	    			break;
	    		// Ask for advice
	    		case 'a' :
	    			// Check if it is the last character of the file
	    			if(k+1 < cmdInput.length){
	    				// Check if it is a space after deal command
		    			if (Character.isSpaceChar(cmdInput[k+1])){
		    				inputPlay = new char[1];
							inputPlay[0] = cmdInput[k];
							k = k+2;
						} else {
							// This means invalid command
							inputPlay = new char[2];
							inputPlay[0] = cmdInput[k];
							inputPlay[1] = cmdInput[k+1];
							k = k+3;
						}
	    			} else {
	    				// In case of last command of the file
	    				inputPlay = new char[1];
						inputPlay[0] = cmdInput[k];
						k = k+2;
	    			}
	    			System.out.println("\n-cmd " + String.valueOf(inputPlay));
	    			doAdvice();
	    			break;
	    		default :
	    			System.out.println("\n-cmd " + cmdInput[k]);
	    			System.out.println(String.valueOf(cmdInput[k]) + ": illegal command");
	    			k = k+2;
	    			break;
			}
			
		}
		
	}
	
	/**
	 * When asked for dealing the cards (in Debug Mode) a message with the first hand
	 * is printed in the terminal. It is possible to ask for the deal only if there was a {@link betValue}
	 * previously done (in other moves) and before choosing which cards to hold.
	 * Five cards from the deck are assigned as the user's first hand and the bet value is decremented from the player's credit.
	 * Note that an error message is printed in case of illegal command input.
	 */
	public void doDeal(){
		if(betValue > 0 && !checkdeal){
			if (user.getCredits()-betValue >= 0){
				if (inputPlay.length > 1){
					System.out.println(String.valueOf(inputPlay) + ": illegal command");
				} else {
					// Give initial 5 cards to player
					user.newHand(deck);
					// Bet money is taken from the player (updates player credits)
					user.incCredits(-betValue);
					System.out.println("player's hand " + user.getHandStr());
					checkbet = false;
					checkdeal = true;
				}
			} else {
				// Error message
	        	System.out.println(String.valueOf(inputPlay) + ": illegal bet");
			}
		} else {
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		}
	}
	

}
