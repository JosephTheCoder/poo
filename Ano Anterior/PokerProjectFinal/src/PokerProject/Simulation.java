package PokerProject;

import java.io.IOException;

public class Simulation extends Game{
	
	private final int totalNbDeals; // total number of deals
	
	// Constructor
	public Simulation(int money, int betVal, int dealsValue) {
		super(money);
		betValue = betVal;
		totalNbDeals = dealsValue;
	}
	
	// **************************
	// Methods
	
	// Play Game in Simulation Mode
	public void play() throws IOException{
		// Does [totalNbDeals] plays
		for(int i = 1; i <= totalNbDeals; i++){
			// Shuffle the 52 cards from the deck
			deck = new Deck();
			// doBet() is not necessary
			// Ask for deal (give cards)
			doDeal();
			// Ask for advice (perfect strategy)
			// Ask for hold (from 0 to 5 cards)
			doHold();
		}
		
		// Prints final statistics
		doStatistics();
	}
	
	// Ask for Statistics
	@Override
	public void doStatistics(){
		data.printStatistics(user);
	}
	
	// Ask for deal
	public void doDeal(){
		// Give initial 5 cards to player
		user.newHand(deck);
		// Bet money is taken from the player (updates player credits)
		user.incCredits(-betValue);
	}
	
	// Ask for hold
	@Override
	public void doHold(){
		// hold cards of the user
		int[] holdVec = tableDB.getAdvice(user.getHand()); // Vector with cards to hold
		user.holdCards(holdVec, deck);
		doEvaluation();
	}
	
	// Return evaluation of final hand
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
		
		int ToBet = 5;
		int NrDeals = 100;
		
		Simulation ToPlay1 = new Simulation(playerCredit, ToBet, NrDeals);
		ToPlay1.play();
		
	}
	
	
}
