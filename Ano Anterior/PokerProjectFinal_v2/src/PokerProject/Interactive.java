package PokerProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Variants.Variants;

/**
 * Class {@link Interactive} extends Class {@link Game} and it represents the Interactive Mode of playing Video Poker.
 * <p>
 * When running in this mode, the player is playing the game through commands in the command line.
 */
public class Interactive extends Game{

	/**
	 * Constructor specifying the initial balance of the player and the variant of Video Poker being used.
	 * These attributes are the same used to construct the superclass {@link Game}.
	 * @param initMoney initial balance of the player when starting the game.
	 * @param optionTable variant of Video Poker used in the game.
	 */
	public Interactive(int initMoney, Variants optionTable){
		super(initMoney, optionTable);
	}
	
	
	/**
	 * Play the game in Interactive Mode.
	 * Reads continuously characters from the command line and acts accordingly.
	 * Options available:
	 * <ul>
	 * <li>$ - ask for the player's credit;</li>
	 * <li>s - ask for the statistics of the game since the beginning;</li>
	 * <li>b - ask for betting a value;</li>
	 * <li>d - ask for dealing cards;</li>
	 * <li>h - ask for holding cards;</li>
	 * <li>a - ask for advice about what cards to hold;</li>
	 * <li>q - ask for quitting the game (the player can only quit at the end of a move and before betting).</li>
	 * </ul>
	 * <p>
	 * Note that an error message is printed in case of illegal command input.
	 * @throws IOException If an error occurs while reading inputs from command.
	 */
	public void play() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // while playing the game
    	String str;
        while (true){        	
        	System.out.print(">> ");
        	str = br.readLine();
        	inputPlay = str.toCharArray();
        	if(inputPlay.length > 0){
	        	switch (inputPlay[0]){
	        		// Ask for credit
	        		case '$' :
	        			doCredit();
	        			break;	
	        		// Ask for statistics
	        		case 's' :
	        			doStatistics();
	        			break;	
	        		// Ask for bet (from 1 to 5)
	        		case 'b' :
	        			doBet();
	        			break;
	        		// Ask for deal (give cards)
	        		case 'd' :
	        			doDeal();
	        			break;
	        		// Ask for hold (from 0 to 5 cards)
	        		case 'h' :
	        			doHold();
	        			break;
	        		// Ask for advice
	        		case 'a' :
	        			doAdvice();
	        			break;
	    			// Exits Video Poker
	        		case 'q' :
	        			// You can only quiet before betting, which means, after the end of a move.
	        			if(checkhold){
	        				System.out.println("Game has ended.");
	        				System.exit(1);
	        			}else{
	        				System.out.println(String.valueOf(inputPlay) + ": illegal command");
	        			}
	        			break;
	        		default :
	        			System.out.println(String.valueOf(inputPlay) + ": illegal command");
	        			break;
	        	}
        	}
	        
        }
        
	}
	
	/**
	 * When asked for dealing the cards (in Interactive Mode) a message with the first hand
	 * is printed in the terminal. It is possible to ask for the deal only if there was a {@link betValue}
	 * previously done (in other moves) and before choosing which cards to hold.
	 * It creates a new {@link Deck} with 52 shuffled cards, it assigns five cards from the deck as the user's first hand
	 * and it decrements the bet value from the player's credit.
	 * Note that an error message is printed in case of illegal command input.
	 */
	public void doDeal(){
		if(betValue > 0 && !checkdeal){
			if (user.getCredits()-betValue >= 0){
				if (inputPlay.length > 1){
					System.out.println(String.valueOf(inputPlay) + ": illegal command");
				} else {
					// Give initial 5 cards to player
					deck = new Deck();
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
