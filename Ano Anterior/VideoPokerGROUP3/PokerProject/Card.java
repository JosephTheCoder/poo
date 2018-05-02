package PokerProject;

import java.util.Arrays;

/**
 * Class {@link Card} represents each one of the 52 cards from the {@link Deck}.
 * <p>
 * Each object has two {@link Integer} attributes:
 * <ul>
 * <li><tt>value</tt> - index from array <tt>values</tt> that represents the value of the card;</li>
 * <li><tt>suit</tt> - index from array <tt>suits</tt> that represents the suit of the card.</li>
 * </ul>
 * <p>
 * They correspond to indexes of two final and static arrays of {@link String}:
 * <ul>
 * <li><tt>values</tt> = {A, 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K};</li>
 * <li><tt>suits</tt> = {C, D, H, S}.</li>
 * </ul>
 */
public class Card {
	
	/**
	 * String array with the available options for card values: A, 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q and K.
	 */
	private final static String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
	/**
	 * String array with the available options for card suits: Clubs, Diamonds, Hearts and Spades.
	 */
	private final static String[] suits = {"C", "D", "H", "S"};
	/**
	 * Index from String[] values that represents the value of the card.
	 */
	private int value;
	/**
	 * Index from String[] suits that represents the suit of the card.
	 */
	private int suit;
	
	/**
	 * Constructor specifying two {@link Integer} arguments: the value and the suit of the card.
	 * @param v {@link Integer} associated to the value of the card
	 * @param s {@link Integer} associated to the suit of the card
	 */
	public Card (int v, int s){
		value = v;
		suit = s;
	}
	
	/**
	 * Constructor specifying two {@link String} arguments: the value and the suit of the card.
	 * @param val {@link String} associated to the value of the card
	 * @param sui {@link String} associated to the suit of the card
	 */
	public Card(String val, String sui){
		value = Arrays.asList(values).indexOf(val); // finds the string in the array and gets its index.
		suit = Arrays.asList(suits).indexOf(sui); // finds the string in the array and gets its index.
	}
	
	/**
	 * Gets the suit of the card.
	 * @return Suit of the card.
	 */
	public int getSuit() {
		return suit;
	}
	
	/**
	 * Gets the value of the card.
	 * @return Value of the card.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * String that describes the card.
	 * @return Complete description of the Card (example: "7D").
	 */
	@Override
	public String toString() {	
		return values[value] + suits[suit];
	}
	
}
