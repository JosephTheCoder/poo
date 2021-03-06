package PokerProject;

import java.io.IOException;
import java.util.Arrays;

import Variants.Variants;

/**
 * Class {@link Game} implements Interface {@link VideoPoker} and it represents the core functioning of the Video Poker.
 * <p>
 * Each object has four main attributes:
 * <ul>
 * <li><tt>user</tt> - variable of type {@link Player} that represents the player of the game;</li>
 * <li><tt>data</tt> - variable of type {@link Statistics} for analysis of the performance of the player in the game;</li>
 * <li><tt>deck</tt> - variable of type {@link Deck} that corresponds to the deck of cards used for playing;</li>
 * <li><tt>tableDB</tt> - variable of type {@link Variants} that corresponds to the information (database) from the variant of Video Poker being used.</li>
 * </ul>
 * <p>
 * And also five attributes for the functioning of the game:
 * <ul>
 * <li><tt>betValue</tt> - variable of type {@link Integer} that represents the value the player is betting in a move;</li>
 * <li><tt>checkbet</tt> - variable of type {@link boolean} for checking if a bet was done;</li>
 * <li><tt>checkdeal</tt> - variable of type {@link boolean} for checking if a deal was done;</li>
 * <li><tt>checkhold</tt> - variable of type {@link boolean} for checking if a hold was done;</li>
 * <li><tt>inputPlay</tt> - array of type {@link Character} that contains the commands to do the moves ($ - credit, a -advice, b - bet, s - statistics, d - deal, h - hold).</li>
 * </ul>
 * <p>
 * Note that this class is abstract since the method <tt>play()</tt> is only implemented in the subclasses.
 */
public abstract class Game implements VideoPoker{
	
	/**
	 * Variable of type {@link Player} that represents the player of the game.
	 */
	public Player user;
	/**
	 * Variable of type {@link Statistics} for analysis of the performance of the player in the game.
	 */
	public Statistics data;
	/**
	 * Variable of type {@link Deck} that corresponds to the deck of cards used for playing.
	 */
	public Deck deck;
	/**
	 * Variable of type {@link Variants} that corresponds to the variant of Video Poker being used.
	 */
	public Variants tableDB;
	
	/**
	 * Variable of type {@link Integer} that represents the value the player is betting in a move.
	 */
	protected int betValue;
	/**
	 * Variable of type {@link boolean} for checking if a bet was done.
	 */
	protected boolean checkbet;
	/**
	 * Variable of type {@link boolean} for checking if a deal was done.
	 */
    protected boolean checkdeal;
    /**
	 * Variable of type {@link boolean} for checking if a hold was done.
	 */
	protected boolean checkhold;
	/**
	 * Array of type {@link Character} that contains the commands to do the moves ($ - credit, a -advice, b - bet, s - statistics, d - deal, h - hold).
	 */
	protected char[] inputPlay;
	
	/**
	 * Constructor specifying the initial balance of the player and the {@link Variants} of Video Poker being used.
	 * Note that the {@link Statistics} variable's initialization is dependent on the variant being used since the possible winning hands change.
	 * The boolean variables are initialized as FALSE, except {@link checkhold} because it corresponds to the beginning of the game.
	 * @param initMoney initial balance of the player when starting the game.
	 * @param optionTable variant of Video Poker used in the game.
	 */
	public Game(int initMoney, Variants optionTable){
		user = new Player(initMoney); // create new Player
		tableDB = optionTable; // input variant to use
		data = new Statistics(tableDB.getWinningHands()); // create statistics data according to variant on use
		betValue = 0; // betValue = 0 means that there is not previous bet value (first bet)
		checkbet = false; // initially bet was not done
		checkdeal = false; // initially deal was not done
		checkhold = true; // initially hold is done because it corresponds to the beginning of the game
	}
	
	
	/**
	 * Sets the current value of the bet ({@link betValue}).
	 * @param newValue new betting value.
	 */
	public void setBetValue(int newValue){
		betValue = newValue;
	}
	
	/**
	 * Gets the current value of the bet ({@link betValue}).
	 * @return betting value.
	 */
	public int getBetValue(){
		return betValue;
	}
	
	/**
	 * When asked for the credit of the player (in Interactive Mode and in Debug) a message with the user credit's
	 * is printed in the terminal. It is possible to ask for the credit at any time of the game.
	 * Note that an error message is printed in case of illegal command input.
	 */
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
	
	/**
	 * When asked for the statistics of the game (in Interactive Mode and in Debug) a table with statistics
	 * is printed in the terminal. It is possible to ask for the statistics at any time of the game.
	 * Note that an error message is printed in case of illegal command input.
	 */
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
	
	/**
	 * When asked for betting during the game (in Interactive Mode and in Debug) a message with the value
	 * of the bet is printed in the terminal. In case the bet command does not include the value, the previous
	 * bet value is used or, if it is the first move, 5 value is used. If a value is included that value must be between
	 * 1 and 5. It is checked if the player has enough money to bet.
	 * It is possible to ask for the betting only at the beginning of a new move. This means that the {@link checkhold}
	 * must be true.
	 * Note that an error message is printed in case of illegal command input.
	 */
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
					int oldBet = betValue;
    		        betValue = Character.getNumericValue(inputPlay[2]);
    		        // Check if the value given is valid
    		        if (betValue <= 0 || betValue > 5){
    		        	// Error message
    		        	System.out.println(String.valueOf(inputPlay) + ": illegal bet");
    		        	betValue = oldBet; // returns the previous bet to betValue
    		        // Check if player has available balance
    		        } else if(user.getCredits()-betValue >= 0){
    		        	// player bets the input value
    		        	System.out.println("player is betting " + betValue);
    		        	checkbet = true;
    		        	checkhold = false;
    		        } else{
    		        	// Error message
    		        	System.out.println(String.valueOf(inputPlay) + ": illegal bet");
    		        	betValue = oldBet;// returns the previous bet to betValue
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
	
	/**
	 * When asked for the holding cards (in Interactive Mode and in Debug) a message with the final hand
	 * is printed in the terminal, as well as the evaluation of the final hand (if the player won or lost).
	 * The hold command is followed by the indexes of the cards the player wants to hold (example: "h 1 3"
	 * means that the player wants to hold the first and the third cards counting from left to right). If the
	 * hold command does not include any indexes, it means that the player wants all cards new. The indexes must
	 * be between 1 and 5.
	 * It is possible to ask for holding only after dealing (give the initial five cards). This means that the 
	 * {@link checkdeal} must be true.
	 * Note that an error message is printed in case of illegal command input.
	 */
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
	
	/**
	 * When asked for advice of what cards to hold (in Interactive Mode and in Debug) a message with the suggestion
	 * of the indexes is printed in the terminal.
	 * It is possible to ask for advice only after dealing (give the initial five cards). This means that the 
	 * {@link checkdeal} must be true.
	 * Note that an error message is printed in case of illegal command input.
	 */
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
				Arrays.sort(holdAdvice); // sort the order of indexes fo cards to hold
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
	
	/**
	 * After holding cards, the evaluation of the final hand is done and (in Interactive Mode and in Debug) a message
	 * saying if the player won or lost is printed in the terminal.
	 * The method evaluation from {@link Variants} if used to determine the result of the final hand.
	 * The number of deals is incremented in the {@link data} as well as the array incNbHands.
	 * In case of winning, the credit of the player is incremented by the value won.
	 */
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
	
	/**
	 * Play the game in a specific mode.
	 * Since it is specific for each mode, it is abstract in the superclass and only implemented
	 * in the subclasses.
	 * @throws IOException If an error occurs while reading inputs.
	 */
	public abstract void play() throws IOException;
		
	
}
