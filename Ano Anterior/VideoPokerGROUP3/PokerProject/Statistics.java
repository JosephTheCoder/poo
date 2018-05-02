package PokerProject;

import Variants.Variants;

/**
 * Class {@link Statistics} is responsible for analyzing the performance of the player in the game.
 * <p>
 * Each object has:
 * <ul>
 * <li><tt>nbHands</tt> - array of {@link Integer} that represents the number of times each of the possible hands occurred (number of winning hands + 1 lost);</li>
 * <li><tt>nbDeals</tt> - {@link Integer} that represents the total number of deals done by the player;</li>
 * <li><tt>winHands</tt> - array of {@link String} that represents the possible winning hands according to the {@link Variants} being used.</li>
 * </ul>
 */
public class Statistics {
	
	/**
	 * Number of times each of the possible hands occurred (winHands.length winning + 1 lost).
	 */
	private int[] nbHands;
	/**
	 * Number of deals.
	 */
	private int nbDeals;
	/**
	 * Possible winning hands (size: [nbHands.length]-1, because lost is not winning hand).
	 */
	private String[] winHands;
	
	/**
	 * Constructor specifying the possible winning hands according to the {@link Variants} being used.
	 * @param handsWin array of {@link String} with the list of the possible winning hands. Note that this
	 * array is obtained from the variant being used.
	 */
	public Statistics(String[] handsWin){
		nbHands = new int[handsWin.length+1]; // [winHands.length] corresponds to the number of possible winning hands (+1 lost)
		nbDeals = 0;
		winHands = handsWin;
	}
	
	/**
	 * Gets the array with the number of times each of the possible hands occurred (winHands.length winning + 1 lost).
	 * @return array of {@link Integer} with the count of each winning hand and lost.
	 */
	public int[] getNbHands(){
		return nbHands;
	}
	
	/**
	 * Gets number of deals.
	 * @return {@link Integer} with the number of deals.
	 */
	public int getNbDeals(){
		return nbDeals;
	}
	
	/**
	 * Increments by one the number of times a specific possible hand occurred.
	 * @param index {@link Integer} that corresponds to the position of the possibility to increment in the array.
	 */
	public void incNbHands(int index){
		nbHands[index]++;
	}
	
	/**
	 * Increments the number deals by one.
	 */
	public void incNbDeals(){
		nbDeals++;
	}
	
	/**
	 * Gets the length of the array with the number of times a specific possible hand occurred (winHands.length winning + 1 lost).
	 * @return length of nbHands
	 */
	public int sizeNbHands(){
		return nbHands.length;
	}
	
	/**
	 * Prints in the terminal the table with the statistics of the game.
	 * This table includes the number of times a winning hand occurred in a deal, the number of times a
	 * non-winning hand occurred in a deal, the total number of deals and the percentage of gain relative
	 * to the initial balance. All these informations are accounted since the beginning of the game.
	 * @param user player of the game which has the information of the money gained.
	 */
	public void printStatistics(Player user){
		
		Object[][] table = new String[nbHands.length+6][2];
		table[0] = new String[] { "Hand", "Nb"};
		table[1] = new String[] { "----------------------", "--------"};
		for(int i = 0; i < winHands.length; i++){
			table[i+2] = new String[] { winHands[i], String.valueOf(nbHands[i]) };
		}
		table[nbHands.length+1] = new String[] { "Other", String.valueOf(nbHands[nbHands.length-1]) };
		table[nbHands.length+2] = new String[] { "----------------------", "--------"};
		table[nbHands.length+3] = new String[] { "Total", String.valueOf(nbDeals) };
		table[nbHands.length+4] = new String[] { "----------------------", "--------"};
		table[nbHands.length+5] = new String[] { "Credit", String.valueOf(user.getCredits()) + " (" + String.format("%.2f",((Float.valueOf(user.getCredits())-Float.valueOf(user.getInitBal()))/Float.valueOf(user.getInitBal())*100)) + "%)" };
		
		for (final Object[] row : table) {
		    System.out.format("%-20s%-20s\n", row);
		}
	}
	
}
