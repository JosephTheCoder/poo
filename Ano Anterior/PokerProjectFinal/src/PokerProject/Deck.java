package PokerProject;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> cards; // deck of 52 cards
	
	// Constructor
	public Deck() {
		cards = new ArrayList<Card>(); // creating an initially empty array of cards
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 4; j++) {
				Card newCard = new Card(i, j);
				// New card is added to the deck
				cards.add(newCard);
			}
		}
		shuffle(); //method for shuffling deck
	}
	
	// Contructor - creates Deck from vector of characters
	public Deck(char[] fileCards) {
		cards = new ArrayList<Card>(); // creating an initially empty array of cards
		int index = 0;
		while(index+1 < fileCards.length){
			Card newCard = new Card(String.valueOf(fileCards[index]), String.valueOf(fileCards[index+1]));
			cards.add(newCard);
			index = index+3;
		}
	}
	
	// **************************
	// Methods
	
	// shuffling the indexes of the array of cards
	public void shuffle() {
		Collections.shuffle(cards); 
	}
	
	// returns number of cards in deck
	public int deckSize() {
		return cards.size(); 
	}
	
	// returns a card from deck and removes it from deck
	public Card draw() { 
		Card returnCard = cards.get(0);  // Gets the first card of the array
		cards.remove(0); // Discarding the card since it won't be in the deck anymore
		return returnCard; 
		
	}
}
