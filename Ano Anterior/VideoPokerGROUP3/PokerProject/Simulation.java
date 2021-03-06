package PokerProject;

import java.io.IOException;

import Variants.Variants;

/**
 * Class {@link Simulation} extends Class {@link Game} and it represents the Simulation Mode of playing Video Poker.
 * <p>
 * When running in this mode, the game is automatically played with a perfect strategy in order to
 * understand the average gain in the player's credit.
 * <p>
 * This class has one more attribute besides the ones from its superclass:
 * <ul>
 * <li><tt>totalNbDeals</tt> - final {@link Integer} that consists of the total number of deals.</li>
 * </ul>
 */
public class Simulation extends Game{
	
	/**
	 * Final {@link Integer} that consists of the total number of deals.
	 */
	private final int totalNbDeals;
	
	/**
	 * Constructor specifying the initial balance of the player, the value to bet during all simulation,
	 * the total number of deals and the {@link Variants} of Video Poker being used.
	 * Some of these attributes are the same used to construct the superclass {@link Game}.
	 * @param initMoney initial balance of the player when starting the game.
	 * @param betVal value to bet during all simulation.
	 * @param dealsValue total number of deals.
	 * @param optionTable variant of Video Poker used in the game.
	 */
	public Simulation(int initMoney, int betVal, int dealsValue, Variants optionTable) {
		super(initMoney, optionTable);
		betValue = betVal;
		totalNbDeals = dealsValue;
	}
	
	/**
	 * Play the game in Simulation Mode.
	 * The game is automatically played {@link totalNbDeals} times, betting always the specified {@link betValue}.
	 * In each move, a new shuffled deck is created, cards are dealt, the perfect strategy is used for deciding which
	 * cards to hold and the evaluation of the final hand is accomplished. At the end of all the deals, the table
	 * with the statistics of the game is printed in the terminal.
	 * @throws IOException Exception not needed but included since it implements the abstract method from {@link Game}.
	 */
	public void play() throws IOException{
		// Does [totalNbDeals] plays
		for(int i = 1; i <= totalNbDeals; i++){
			// Check if there is enough available balance
			if(user.getCredits()-betValue >= 0){
				// Shuffle the 52 cards from the deck
				deck = new Deck();
				// doBet() is not necessary
				// Ask for deal (give cards)
				doDeal();
				// Ask for advice (perfect strategy)
				// Ask for hold (from 1 to 5 cards)
				doHold();
			} else {
				System.out.println("Not enough money.");
				break;
			}
		}
		
		// Prints final statistics
		doStatistics();
	}
	
	/**
	 * The statistics (in Simulation Mode) are asked only at the end of the game, and then a table with the statistics
	 * is printed in the terminal.
	 * This method is an override of the method implemented in {@link Game}.
	 */
	@Override
	public void doStatistics(){
		data.printStatistics(user);
	}
	
	/**
	 * Dealing the cards (in Simulation Mode) means that five cards from the deck are assigned as the user's first
	 * hand and that the bet value is decremented from the player's credit.
	 */
	public void doDeal(){
		// Give initial 5 cards to player
		user.newHand(deck);
		// Bet money is taken from the player (updates player credits)
		user.incCredits(-betValue);
	}
	
	/**
	 * Holding cards (in Simulation Mode) means that the perfect strategy is used as the choice for which cards to hold.
	 * After holding, the evaluation of the final hand is done in order to verify if it is a winning hand or a lost one.
	 * This method is an override of the method implemented in {@link Game}.
	 */
	@Override
	public void doHold(){
		// hold cards of the user
		int[] holdVec = tableDB.getAdvice(user.getHand()); // Vector with cards to hold
		user.holdCards(holdVec, deck);
		doEvaluation();
	}
	
	/**
	 * The evaluation of the final hand (in Simulation Mode) consists of checking the result (win or lost),
	 * incrementing the number of deals (in the statistics), incrementing the incNbHands accordingly
	 * and incrementing the credits won in case of winning hand.
	 * This method is an override of the method implemented in {@link Game}.
	 */
	@Override
	public void doEvaluation(){
		int result = tableDB.evaluation(user.getHand());
		data.incNbDeals();
		if (result < 0){
			data.incNbHands(data.sizeNbHands()-1);
		} else {
			data.incNbHands(result);
			user.incCredits(tableDB.getPaytableValue(result, betValue-1));
		}
	}
	
	
}
