package Main;

import java.awt.EventQueue;
import java.io.IOException;
// import java.util.Date;

import PokerProject.Debug;
import PokerProject.Game;
import PokerProject.Interactive;
import PokerProject.Simulation;
import Variants.DoubleBonus;
import Variants.Variants;
import VideoPokerGUI.GameBoard;

/**
 * Class {@link Main} corresponds to the association of the whole structure of Video Poker.
 * <p>
 * The {@link Game} object is created and according to the input arguments it is initialized as one of the available modes:
 * <ul>
 * <li><b>{@link Interactive}:</b> <tt>$ java -jar "JAR-NAME".jar -i credit</tt>;</li>
 * <li><b>{@link Debug}:</b> <tt>$ java -jar "JAR-NAME".jar -d credit cmd-file card-file</tt>;</li>
 * <li><b>{@link Simulation}:</b> <tt>$ java -jar "JAR-NAME".jar -s credit bet nbdeals</tt>;</li>
 * </ul>
 * <p>
 * The Swing GUI mode is also included through:
 * <ul>
 * <li><b>Swing GUI:</b> <tt>$ java -jar "JAR-NAME".jar -g</tt>.</li>
 * </ul>
 * <p>
 * An error message is launched in case the input arguments are not according to expected.
 */
public class Main {
	
	/**
	 * Main method used for running the game.
	 * Creates a {@link Game} object and the programmer chooses which {@link Variants} of Video Poker to use.
	 * According to the input arguments, the {@link Game} object is initialized to one of the three modes:
	 * {@link Interactive}, {@link Debug} and {@link Simulation}.
	 * The Swing GUI mode is also available and an error message is launched in case the input arguments
	 * are not according to expected.
	 * In the terminal a welcome message is printed and, in case one of the three main modes is launched,
	 * the method <tt>play()</tt> is runned.
	 * @throws IOException If an error occurs while reading input arguments.
	 * @param args input arguments.
	 */
	public static void main(String[] args) throws IOException {
		
		// For analyzing computation time
		// long startTime = System.currentTimeMillis();
		
		boolean gui = false;
		Game gameplay = null;
		String welcomemsg = "Your game has started in ";
		
		// Programmer chooses here which variant of VideoPoker to use
		Variants optionTable = new DoubleBonus();
		
		// In case no arguments were given
		if(args.length < 1){
			System.out.println("Please insert the terminal commands.");
			System.out.println("Invalid arguments to play VideoPoker. \n " +
								"Interactive Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -i credit \n" +
								"Debug Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file \n" +
								"Simulation Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals" +
								"Swing GUI Format: java -jar <<YOUR-JAR-NAME>>.jar -g");
			System.exit(1);
		}
				
		switch(args[0]){
			case "-i":
				if (args.length == 2){
					gameplay = new Interactive(Integer.parseInt(args[1]), optionTable);
					welcomemsg = welcomemsg + "Interactive mode. You can now play.";
				} else {
					// error message
					System.out.println("Please insert the player credit to play in Interactive mode.");
					System.out.println("Interactive Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -i credit \n");
					System.exit(1);
				}
				break;
			case "-d":
				if (args.length == 4){
					gameplay = new Debug(Integer.parseInt(args[1]), args[2], args[3], optionTable);
					welcomemsg = welcomemsg + " Debug mode.";
				} else {
					// error message
					System.out.println("Please insert the correct arguments to play in Debug mode.");
					System.out.println("Debug Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file");
					System.exit(1);
				}
				break;
			case "-s":
				if (args.length == 4){
					gameplay = new Simulation(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), optionTable);
					welcomemsg = welcomemsg + "Simulation mode.";
				} else {
					// error message
					System.out.println("Please insert the correct arguments to play in Simulation mode.");
					System.out.println("Simulation Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals");
					System.exit(1);
				}
				break;
			case "-g":
				if (args.length == 1){
					gui = true;
				} else {
					// error message
					System.out.println("Please check the terminal commands.");
					System.exit(1);
				}
				break;	
			default:
				// error message
				System.out.println("Please check the terminal commands.");
				System.out.println("Invalid arguments to play VideoPoker. \n " +
								   "Interactive Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -i credit \n" +
								   "Debug Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file \n" +
								   "Simulation Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals" +
								   "Swing GUI Format: java -jar <<YOUR-JAR-NAME>>.jar -g");
				System.exit(1);	
		}
		
		if(gui){
			// Launch the application interface.
			System.out.println("Launching GUI.");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						// Create the main frame
						GameBoard window = new GameBoard(optionTable);	
						window.initializeGUI();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			// Welcome message
			System.out.println("Welcome to VideoPoker!");
			System.out.println(welcomemsg);
			
			// Start the Game
			gameplay.play();
		}
		
		// For analyzing computation time
		// long endTime   = System.currentTimeMillis();
		// long totalTime = endTime - startTime;
		// System.out.println("Computation Time = " + totalTime + " milliseconds.");
		
	}

}
