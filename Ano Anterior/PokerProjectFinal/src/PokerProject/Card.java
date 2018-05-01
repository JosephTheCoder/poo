package PokerProject;

import java.util.Arrays;

public class Card{
	
	private int value; // index related to the value of the card
	private int suit; // index related to the suit of the card
	
	// Set of options for value
	private final static String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
	// Set of options for suit
	private final static String[] suits = {"C", "D", "H", "S"};
	
	// Constructor
	public Card (int v, int s){
		value = v;
		suit = s;
	}
	
	// creates Card from String arguments
	public Card(String val, String sui){
		value = Arrays.asList(values).indexOf(val);
		suit = Arrays.asList(suits).indexOf(sui);
	}
	
	// **************************
	// Methods
	
	// returns Card's suit
	public int getSuit() {
		return suit;
	}
	
	// returns Card's value
	public int getValue() {
		return value;
	}
	
	// example: "7D" (seven of diamonds)
	@Override
	public String toString() {	
		return values[value] + suits[suit];
	}	
	
}
