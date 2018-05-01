package PokerProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Debug extends Game{
	
	private char[] cardInput;
	private String cmdFileName;
	private String cardsFileName;
	
	// Constructor
	public Debug(int money, String cmdNameStr, String cardNameStr){
		super(money);
		cmdFileName = cmdNameStr;
		cardsFileName = cardNameStr;
	}
	
	// **************************
	// Methods
	
	// Play Game in Debug Mode
	public void play() throws IOException{
		// Path to [cmd-file.txt]
		String cmdfilePath = "C:/Users/Utilizador/Documents/Java/PokerProjectFinal/" + cmdFileName;
        BufferedReader cmdBr = new BufferedReader(new FileReader(new File(cmdfilePath)));
		String cmdStr = cmdBr.readLine();
		char[] cmdInput = cmdStr.toCharArray();
		cmdBr.close();
		
		// Path to [card-file.txt]
		String cardfilePath = "C:/Users/Utilizador/Documents/Java/PokerProjectFinal/" + cardsFileName;
        BufferedReader cardBr = new BufferedReader(new FileReader(new File(cardfilePath)));
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
	    			doDeal();
	    			break;
	    		// Ask for hold (from 0 to 5 cards)
	    		case 'h' :
	    			// Check if it is the last character of the file
	    			if(k+1 < cmdInput.length){
	    				// Check if it is a space after deal command
		    			if (Character.isSpaceChar(cmdInput[k+1])){
		    				int cntChar = 0; // To count cards to hold
		    				int kAux = Integer.valueOf(k);
		    				// Check if it there are still characters to read in the file
		    				if(kAux+2 < cmdInput.length){
		    					// Count number of cards to hold
			    				while((kAux+2 < cmdInput.length) && Character.isDigit(cmdInput[kAux+2])){
			    					cntChar++;
			    					kAux = kAux+2;
			    				}
		    				}
		    				// In case no cards to hold
		    				if (cntChar == 0){
		    					inputPlay = new char[1];
		    					inputPlay[0] = cmdInput[k];
		    					k = k+2; // next character
		    				} else {
		    					// In case some cards to hold
		    					inputPlay = new char[1+2*cntChar]; // example: "h 1 2 3"
		    					inputPlay[0] = cmdInput[k];
		    					inputPlay[1] = cmdInput[k+1];
		    					for(int idx=0; idx <= cntChar; idx++){
			    					inputPlay[2+idx] = cmdInput[k+idx+2];
			    				}
		    					k = k+2+2*cntChar; // next character
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
	    			doAdvice();
	    			break;
	    		default :
	    			System.out.println(String.valueOf(inputPlay) + ": illegal command");
	    			break;
			}
			
		}
		
	}
	
	// Ask for deal (Debug Mode)
	public void doDeal(){
		if(betValue > 0){
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
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length<1){
			System.out.println("Insert the Player's Credit!");
			System.exit(1);
		}
		
		int playerCredit = Integer.parseInt(args[0]);
		if (playerCredit < 2) {
			System.out.println("Input number must be greater than 1");
			System.exit(1);
		}
		
		String nameCmd = "cmd-file.txt";
		String nameCard = "card-file.txt";
		
		Debug ToPlay1 = new Debug(playerCredit, nameCmd, nameCard);
		ToPlay1.play();
		
	}

}
