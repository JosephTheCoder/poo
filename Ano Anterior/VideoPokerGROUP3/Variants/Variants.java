package Variants;

import PokerProject.Card;

/**
 * Interface {@link Variants} represents the existing variants of Video Poker
 * (for example, Deuces Wild, Joker's Wild or Double Bonus).
 * The differences between them are mainly the options for winning hands and
 * also the payback table.
 * <p>
 * Within the scope of this project, only Double Bonus variant is implemented.
 * However the structure of the code is intended to be extensible for other variants to be implemented.
 */
public interface Variants {
	
	/**
	 * Gets a string with the available winning hands according to the variant being used.
	 * @return array of {@link String} with the winning hands.
	 */
	String[] getWinningHands();
	
	/**
	 * Gets the winning hand with a specific index in the array of {@link String}.
	 * @param i index of the array of {@link String} that contains all options for winning hands according to the variant on use.
	 * @return {@link String} with a specific winning hand.
	 */
	String getWinHand(int i);
	
	/**
	 * Returns the payback of a specific winning hand according to the betting value, which are dependent on the variant on use.
	 * @param row index of the winning hand.
	 * @param column index of the betting value.
	 * @return payback of a specific winning hand according to the betting value.
	 */
	int getPaytableValue(int row, int column);
	
	/**
	 * Evaluates a specific hand according to the variant on use and outups the result.
	 * @param hand array of {@link Card} which corresponds to a hand of five cards .
	 * @return index of the winning hand in the array of {@link String} according to the variant on use. It returns -1 in case of not a winning hand.
	 */
	int evaluation(Card[] hand);
	
	/**
	 * Gets the advice for which cards to hold according to both the hand and the variant on use.
	 * @param hand array of {@link Card} which corresponds to a hand of five cards .
	 * @return array of {@link Integer} with the indexes of what cards to hold. It has zero length in case no cards should be held.
	 */
	int[] getAdvice(Card[] hand);
	
}
