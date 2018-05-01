package PokerProject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class {@link Deck} represents 52 different cards (as in a real deck).
 * <p>
 * Each object has one attribute which is an {@link ArrayList} of {@link Card}.
 */
public class Deck {
	
	/**
	 * ArrayList of {@link Card} that represents a deck of 52 cards.
	 */
	private ArrayList<Card> cards;
	
	/**
	 * Constructor which creates an initially empty array of cards, then adds the 52 different cards and finally shuffles the array.
	 */
	public Deck() {
		cards = new ArrayList<Card>(); // creating an initially empty array of cards
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				Card newCard = new Card(i, j);
				// New card is added to the deck
				cards.add(newCard);
			}
		}
		shuffle(); // method for shuffling deck
	}
	
	/**
	 * Constructor specifying the cards to add to the deck.
	 * Used in Debug Mode, where the cards are defined in an input file.
	 * @param fileCards Array of {@link Character} with cards to add to the deck. Example: "AH JC 2S".
	 */
	public Deck(char[] fileCards) {
		cards = new ArrayList<Card>(); // creating an initially empty array of cards
		int index = 0;
		// do until the end of the file is reached
		while(index+1 < fileCards.length){
			// new card is created with the constructor specifying two Strings ("AH", for example).
			Card newCard = new Card(String.valueOf(fileCards[index]), String.valueOf(fileCards[index+1]));
			cards.add(newCard); // new card is added to the deck
			// it jumps 3 characters because, considering the example after reading "AH", it must read "JC" and there is a space in between
			index = index+3;
		}
	}
	
	/**
	 * Shuffles the order of the array of cards.
	 */
	public void shuffle() {
		Collections.shuffle(cards); // shuffling the indexes of the array of cards
	}
	
	/**
	 * Gets the size of the array of cards.
	 * @return Number of cards in the deck.
	 */
	public int deckSize() {
		return cards.size(); // returns number of cards in the deck
	}
	
	/**
	 * Gets the first card from the deck, returns it and removes it from the deck since it can't be drawn again.
	 * @return First card from the deck.
	 */
	public Card draw() { 
		Card returnCard = cards.get(0);  // Gets the first card of the array
		cards.remove(0); // Discarding the card since it won't be in the deck anymore
		return returnCard; // returns a card from deck and removes it from deck
		
	}
}
