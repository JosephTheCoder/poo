package PokerProject;

public interface Variants {
	
	// returns the [winningHands] String vector
	public String[] getWinningHands();
	
	// returns index i of the [winningHands] String vector
	public String getWinHand(int i);
	
	// returns index i the [paytable] Integer vector
	public int getPaytableValue(int row, int column);
	
	// returns result of the hand
	public int evaluation(Card[] hand);
	
	// returns the perfect strategy for specific hand
	public int[] getAdvice(Card[] hand);
	
}
