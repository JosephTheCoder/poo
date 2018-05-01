package PokerProject;

import java.util.Arrays;

public class Player {
	
	private int initBal; // Player's initial balance
	private int credits; // Player's current credits
	private Card[] hand; // Player's hand (5 cards)
	
	// Constructor
	public Player(int initial){
		initBal = initial;
		credits = initial;
		hand = new Card[5];
	}
	
	// **************************
	// Methods
	
	// returns Player's current credits
	public int getCredits(){
		return this.credits;
	}
	
	// returns Player's initial balance
	public int getInitBal(){
		return this.initBal;
	}
	
	// returns Player's hand
	public Card[] getHand(){
		return this.hand;
	}
	
	// returns Player's hand (as String)
	public String getHandStr(){
		String str = "";
		for(int k = 0; k < hand.length; k++){
			str = str + hand[k].toString() + " ";
		}
		return str.substring(0, str.length()-1);
	}
	
	/* sets a new Player's current credits
	public void setCredits(int newCredits){
		this.credits = newCredits;
	}*/
	
	// sets a new Player's current credits
	public void incCredits(int creditsToAdd){
		this.credits = this.credits + creditsToAdd;
	}
	
	// sets a new Player's hand
	public void newHand(Deck deck){
		for (int k = 0; k < 5; k++){
			this.hand[k] = deck.draw();
		}
	}
	
	// sets Player's hand after hold
	public void holdCards(int[] holdArray, Deck deck){
		if (holdArray == null || holdArray.length == 0){
			for (int k = 0; k < 5; k++){
				this.hand[k] = deck.draw();
			}
		} else {
			int index = 0;
			Arrays.sort(holdArray);
			for (int k = 0; k < 5; k++){
				// Is NOT considering [array] decremented by 1
				if (k+1 == holdArray[index]){
					if (index < (holdArray.length-1)) index++;
				} else {
					this.hand[k] = deck.draw();
				}
			}
		}
		
	}

}
