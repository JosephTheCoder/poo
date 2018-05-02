package PokerProject;

import java.io.IOException;

/**
 * Interface {@link VideoPoker} is the structure of Video Poker Game.
 * <p>
 * The fundamental methods for the general functioning of the game are stated:
 * <ul>
 * <li><tt>doCredit()</tt> - when asked for the credit of the player;</li>
 * <li><tt>doStatistics()</tt> - when asked for the statistics of the player since the beginning of the game;</li>
 * <li><tt>doBet()</tt> - when asked for bet a specific amount of money;</li>
 * <li><tt>doHold()</tt> - when asked for hold specific cards;</li>
 * <li><tt>doAdvice()</tt> - when asked for advice about what cards to hold;</li>
 * <li><tt>doEvaluation()</tt> - when asked for the evaluation of the final hand of the player.</li>
 * </ul>
 */
public interface VideoPoker {
	
	/**
	 * When asked for the credit of the player.
	 */
	void doCredit();
	
	/**
	 * When asked for the statistics of the player since the beginning of the game.
	 */
	void doStatistics();
	
	/**
	 * When asked for bet a specific amount of money.
	 */
	void doBet();
	
	/**
	 * When asked for hold specific cards.
	 */
	void doHold();
	
	/**
	 * When asked for advice about what cards to hold.
	 */
	void doAdvice();
	
	/**
	 * When asked for the evaluation of the final hand of the player.
	 */
	void doEvaluation();
	
	/**
	 * Play the game in a specific mode.
	 * @throws IOException If an error occurs while reading inputs.
	 */
	void play() throws IOException;
	
}
