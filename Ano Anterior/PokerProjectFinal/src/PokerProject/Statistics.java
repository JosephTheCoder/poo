package PokerProject;

public class Statistics {
	
	private int[] nbHands; // number of times each of the possible hands occurred (winning + lost)
	private int nbDeals; // number of deals
	private String[] winHands; // possible win hands (size: [nbHands.length]-1)
	
	// Constructor
	public Statistics(String[] handsWin){
		nbHands = new int[handsWin.length+1]; // [winHands.length] corresponds to the number of possible winning hands (+1 lost)
		nbDeals = 0;
		winHands = handsWin;
	}
	
	// **************************
	// Methods
	
	// returns nbHands
	public int[] getNbHands(){
		return nbHands;
	}
	
	// returns nbDeals
	public int getNbDeals(){
		return nbDeals;
	}
	
	// increments one of the 11 possible winning hands occurred
	public void incNbHands(int index){
		nbHands[index]++;
	}
	
	// increments the nbDeals
	public void incNbDeals(){
		nbDeals++;
	}
	
	// returns the length of [nbHands]
	public int sizeNbHands(){
		return nbHands.length;
	}
	
	// prints table with statistics in the terminal
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
