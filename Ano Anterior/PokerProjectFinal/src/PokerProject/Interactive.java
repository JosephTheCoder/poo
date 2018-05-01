package PokerProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interactive extends Game{
	
	// Constructor
	public Interactive(int money){
		super(money);
	}
	
	// **************************
	// Methods
	
	// Play Game in Interactive Mode
	public void play() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // while playing the game
    	String str;
        while (true){        	
        	System.out.print(">> ");
        	str = br.readLine();
        	inputPlay = str.toCharArray();
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
	
	// Ask for deal
	public void doDeal(){
		if(betValue > 0){
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
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		}
	}
	
	public static void main(String[] args) throws IOException{
		
		if (args.length<1){
			System.out.println("Insert the Player's Credit!");
			System.exit(1);
		}
		
		int playerCredit = Integer.parseInt(args[0]);
		if (playerCredit < 2) {
			System.out.println("Input number must be greater than 1");
			System.exit(1);
		}
		
		Interactive ToPlay = new Interactive(playerCredit);
		ToPlay.play();
		
	}
	
}
