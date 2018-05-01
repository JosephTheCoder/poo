package Main;

import java.awt.EventQueue;
import java.io.IOException;

import PokerProject.Debug;
import PokerProject.Game;
import PokerProject.GameBoard;
import PokerProject.Interactive;
import PokerProject.Simulation;


/**
* The Video Poker program implements an application that
* allows you to play Video Poker in 4 different modes.
*
* @author  João Beirão, João Raposo, Luís Alves
* @version 1.0
* @since   2017-05-12 
*/
public class Main {

	public static void main(String[] args) throws IOException {
				
		boolean gui = false;
		Game gameplay = null;
		String welcomemsg = "Your game has started in ";
		
		// In case no arguments were given
		if(args.length < 1){
			System.out.println("Please insert the terminal commands.");
			System.out.println("Invalid arguments to play VideoPoker. \n " +
								"Interactive Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -i credit \n" +
								"Debug Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file \n" +
								"Simulation Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals");
			System.exit(1);
		}
				
		switch(args[0]){
			case "-i":
				if (args.length == 2){
					gameplay = new Interactive(Integer.parseInt(args[1]));
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
					gameplay = new Debug(Integer.parseInt(args[1]), args[2], args[3]);
					welcomemsg = welcomemsg + " Debug mode.";
				} else {
					// error message
					System.out.println("Please check the terminal commands.");
					System.out.println("Debug Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file");
					System.exit(1);
				}
				break;
			case "-s":
				if (args.length == 4){
					gameplay = new Simulation(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
					welcomemsg = welcomemsg + "Simulation mode.";
				} else {
					// error message
					System.out.println("Please check the terminal commands.");
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
						"Simulation Mode Format: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals");
				System.exit(1);	
		}
		
		if(gui){
			// Launch the application interface.
			System.out.println("Launching GUI.");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						// Create the main frame
						GameBoard window = new GameBoard();	
						window.initialize();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			// Welcome message
			System.out.println("Welcome to VideoPoker!");
			System.out.println(welcomemsg);
			
			// Start the gameplay
			gameplay.play();
		}
		
	}

}
