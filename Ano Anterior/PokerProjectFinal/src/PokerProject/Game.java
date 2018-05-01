package PokerProject;

import java.io.IOException;

public abstract class Game implements VideoPoker{
	
	protected Player user;
	protected Statistics data;
	protected Deck deck;
	protected DoubleBonus tableDB;
	
	// Play variables;
	protected int betValue; // value of the bet
	protected boolean checkbet; // check if bet was done
    protected boolean checkdeal; // check if deal was done
	protected boolean checkhold; // check if hold was done
	protected char[] inputPlay; // input char[] with commands to play
	
	// Constructor
	public Game(int initMoney){
		user = new Player(initMoney); // create new Player
		tableDB = new DoubleBonus(); // choose DoubleBonus variant to use
		data = new Statistics(tableDB.getWinningHands()); // create statistics data
		betValue = 0; // betValue = 0 means that there is not previous bet value (first bet)
		checkbet = false; // initially bet was not done
		checkdeal = false; // initially deal was not done
		checkhold = true; // initially hold is done because it corresponds to the beginning of the game
	}
	
	// **************************
	// Methods
	
	// Ask for credit
	public void doCredit(){
		// checks if there are too many characters as command input (only "$" is allowed)
		if (inputPlay.length > 1){
			// error message
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		} else {
			// Credit message
			System.out.println("player's credit is " + user.getCredits());
		}
	}
	
	// Ask for Statistics
	public void doStatistics(){
		// checks if there are too many characters as command input (only "s" is allowed)
		if (inputPlay.length > 1){
			// error message
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		} else {
			// Statistics message (table)
			data.printStatistics(user);
		}
	}
	
	// Ask for bet
	public void doBet(){
		// checks if hold was done (only in that case we are allowed to do bet)
		if (checkhold){
			// Check if the value of the bet was given
			if (inputPlay.length >= 2){
				// Check if vector with too many things
				if (inputPlay.length > 3 || !Character.isSpaceChar(inputPlay[1])){
					// Error message
		        	System.out.println(String.valueOf(inputPlay) + ": illegal bet");
				} else {
    		        int betValue = Character.getNumericValue(inputPlay[2]);
    		        // Check if the value given is valid
    		        if (betValue <= 0 || betValue > 5){
    		        	// Error message
    		        	System.out.println(String.valueOf(inputPlay) + ": illegal bet");
    		        // Check if player has available balance
    		        } else if(user.getCredits()-betValue >= 0){
    		        	// player bets the input value
    		        	System.out.println("player is betting " + betValue);
    		        	checkbet = true;
    		        	checkhold = false;
    		        } else{
    		        	// Error message
    		        	System.out.println(String.valueOf(inputPlay) + ": illegal bet");
    		        }
				}
			} else {
				// Check if player has available balance
				if(user.getCredits()-betValue >= 0){
					// Check if there exists a previous bet
					if (betValue == 0){
						// No previous bet (initial deal) so bet is 5
						betValue = 5;
						System.out.println("player is betting " + betValue);
			        	checkbet = true;
			        	checkhold = false;
					} else {
						// betValue is the previous betValue
						System.out.println("player is betting " + betValue);
						checkbet = true;
						checkhold = false;
					}
				} else {
					// Error message
		        	System.out.println(String.valueOf(inputPlay) + ": illegal bet");
				}
			}
		} else {
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		}
	}
	
	// Ask for hold
	public void doHold(){
		// Checks if deal was done (only in that case hold is allowed)
		if(checkdeal){
			// Check cards to hold
			if (inputPlay.length >= 2){
				// Check if vector with too many characters or if it has not an odd length
				if (inputPlay.length > 11 || !Character.isSpaceChar(inputPlay[1]) || (inputPlay.length % 2 == 0)){
					// Error message
		        	System.out.println(String.valueOf(inputPlay) + ": illegal hold");
				} else {
					int k = 2;
					int[] holdVec = new int[(inputPlay.length-1)/2]; // Vector with cards to hold
					// Save in [holdVev] all cards to hold
					while (k < inputPlay.length){
						int holdValue = Character.getNumericValue(inputPlay[k]);
						// Check if acceptable value
						if (holdValue <= 0 || holdValue > 5){
        		        	// Error message
        		        	System.out.println(String.valueOf(inputPlay) + ": illegal hold");
    	        			break;
        		        } else {
        		        	holdVec[k/2-1] = holdValue;
        		        	// Check if space after value
        		        	if (k+1 < inputPlay.length){
	        		        	if (!Character.isSpaceChar(inputPlay[k+1])){
	        		        		// Error message
    	        		        	System.out.println(String.valueOf(inputPlay) + ": illegal hold");
    	    	        			break;
	        		        	}
        		        	}
        		        	k = k+2;
        		        }
					}
					// hold cards of the user
					user.holdCards(holdVec, deck);
					System.out.println("player's hand " + user.getHandStr());
					doEvaluation();
					checkdeal = false;
					checkhold = true;
				}	
			} else {
				// User wants to change all the cards
				int[] holdVec = new int[0];
				user.holdCards(holdVec, deck);
				System.out.println("player's hand " + user.getHandStr());
				// Evaluation of the final hand (check if it wins or loses) 
				doEvaluation();
				checkdeal = false;
				checkhold = true;
			}
			
		} else {
			// Error message
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		}
	}
	
	// Ask for advice
	public void doAdvice(){
		// Check if deal was done (only in that case it can show the perfect strategy)
		if(checkdeal){
			// Check if too many characters (only accepts "a")
			if (inputPlay.length > 1){
				// Error message
				System.out.println(String.valueOf(inputPlay) + ": illegal command");
			} else {
				// Advice
				int[] holdAdvice = tableDB.getAdvice(user.getHand());
				System.out.print("player should hold cards");
				for (int index = 0; index < holdAdvice.length; index++){
					System.out.print(" " + holdAdvice[index]);
				}
				System.out.print("\n");
			}
		} else {
			// Error message
			System.out.println(String.valueOf(inputPlay) + ": illegal command");
		}
	}
	
	// Evaluation of the final hand
	public void doEvaluation(){
		// Returns Integer associated with evaluation of final hand
		int result = tableDB.evaluation(user.getHand());
		// Increments the number of deals in data (statistics)
		data.incNbDeals();
		// Checks if player won or lost
		if (result < 0){
			// Player lost
			// Increments lost ("other") position of the [NbHands] vector
			data.incNbHands(data.sizeNbHands()-1);
			System.out.println("player loses and his credit is " + user.getCredits());
		}else{
			// Player won
			// Increments respective winning position of the [NbHands] vector
			data.incNbHands(result);
			// Increments the credit won by the player
			user.incCredits(tableDB.getPaytableValue(result, betValue-1));
			System.out.println("player wins with a " + tableDB.getWinHand(result).toUpperCase() + " and his credit is " + user.getCredits());
		}
		
	}
	
	public abstract void play() throws IOException;
	
}
