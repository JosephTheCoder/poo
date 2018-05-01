package PokerProject;

import java.util.Arrays;

/**
 * Class {@link Player} represents the player of Video Poker.
 * <p>
 * Each object has two {@link Integer}:
 * <ul>
 * <li><tt>initBal</tt> - initial balance of the player;</li>
 * <li><tt>credits</tt> - player's current available money.</li>
 * </ul>
 * <p>
 * And also an array {@link Card} which represents the player's hand (5 cards).
 */
public class Player {
	
	/**
	 * Player's initial balance.
	 */
	private int initBal;
	/**
	 * Player's current available money.
	 */
	private int credits;
	/**
	 * Array of {@link Card} that correspond to the player's hand (5 cards).
	 */
	private Card[] hand;
	
	/**
	 * Constructor specifying one {@link Integer}: the initial balance of the player.
	 * Note that the initial balance and the current available money is equal in the beginning of the game.
	 * @param initial {@link Integer} associated to the initial balance of the player
	 */
	public Player(int initial){
		initBal = initial;
		credits = initial;
		hand = new Card[5];
	}
	
	/**
	 * Gets the current money of the player.
	 * @return player's current credits.
	 */
	public int getCredits(){
		return this.credits; // returns Player's current credits
	}
	
	/**
	 * Gets the initial balance of the player.
	 * @return player's initial balance.
	 */
	public int getInitBal(){
		return this.initBal; // returns Player's initial balance
	}
	
	/**
	 * Gets the current hand of the player (5 cards).
	 * @return array of {@link Card} with player's hand.
	 */
	public Card[] getHand(){
		return this.hand; // returns Player's hand
	}
	
	/**
	 * Returns the current hand of the player (5 cards) as a {@link String} (example: "7D AH 3C 8H KS").
	 * @return {@link String} with the 5 cards which correspond to the player's hand.
	 */
	public String getHandStr(){
		// It it like the toString() but only for the hand of the player 
		String str = "";
		for(int k = 0; k < hand.length; k++){
			str = str + hand[k].toString() + " "; // it uses the toString() from Card
		}
		// the use of [substring] is to remove the last added space
		return str.substring(0, str.length()-1); // returns Player's hand (as String)
	}
	
	/**
	 * Increments to the current credit of the player the value given as input.
	 * If it is a negative value, the current credit is decremented by that value.
	 * @param creditsToAdd money to add to player's credits.
	 */
	public void incCredits(int creditsToAdd){
		this.credits = this.credits + creditsToAdd; // sets a new Player's current credits
	}
	
	/**
	 * Sets a new hand (5 cards) for the player.
	 * Five cards are added to the array of {@link Card} by using the function <tt>draw()</tt> from {@link Deck}.
	 * The five first cards from the shuffled deck are used as the player's initial hand.
	 * @param deck set of available cards to draw.
	 */
	public void newHand(Deck deck){
		for (int k = 0; k < 5; k++){
			this.hand[k] = deck.draw();
		}
	}
	
	/**
	 * Sets player's hand after hold.
	 * The player decides which cards to hold from the initial hand.
	 * Those cards are maintained and new ones are drawn for replacing the ones not held.
	 * @param holdArray array of {@link Integer} with the indexes of the cards the player chose to hold.
	 * @param deck set of available cards to draw.
	 */
	public void holdCards(int[] holdArray, Deck deck){
		// Checks if the holdArray is empty. If so, all cards must be changed.
		if (holdArray == null || holdArray.length == 0){
			for (int k = 0; k < 5; k++){
				this.hand[k] = deck.draw();
			}
		} else {
			// In this case, the holdArray is sorted and then the cards mentioned are maintained and the others are replaced by new ones
			int index = 0;
			Arrays.sort(holdArray);
			for (int k = 0; k < 5; k++){
				// Checks if card is mentioned in the array for holding
				// [k+1] since in real life we start counting in 1 but in Java the first element is 0
				if (k+1 == holdArray[index]){
					if (index < (holdArray.length-1)) index++;
				} else {
					// In this case, a new card is drawn from the deck
					this.hand[k] = deck.draw();
				}
			}
		}
		
	}

}
