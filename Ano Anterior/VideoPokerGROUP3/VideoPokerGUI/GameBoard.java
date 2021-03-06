package VideoPokerGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


import PokerProject.Deck;
import PokerProject.Game;
import PokerProject.Interactive;
import PokerProject.Player;
import PokerProject.Statistics;
import Variants.Variants;

/**
 * Class {@link GameBoard} represents the graphical interface of the Video Poker game.
 * The class is able to create all the graphical interface and run an {@link Interactive} variant of the Class {@link Game} according
 * to a variant of Video Poker previously chosen.
 */
public class GameBoard{
	/**
	 * Variable of type {@link Game} that represents the game variables.
	 */
	private Game gameplay;
	/**
	 * Variable of type {@link JFrame} that represents the frame where all the panels are displayed.
	 */
	private JFrame frame;		
	/**
	 * Variable of type {@link GamePanel} that represents the welcome panel where the users start the game and define their name and 
	 * their initial credit 
	 */
	private GamePanel WelcomePanel;
	/**
	 * Variable of type {@link GamePanel} that represents the panel containing the user information (Name, bet and credit).
	 */
	private GamePanel MainPanel;
	/**
	 * Variable of type {@link GamePanel} that represents the panel displaying what's the best strategy to play given a specific hand (array
	 * of {@link Card}).
	 */
	private GamePanel AdvicePanel;
	/**
	 * Variable of type {@link GamePanel} that represents the panel displaying a game over image.
	 */
	private GamePanel GameOverPanel;
	/**
	 * Variable of type {@link JTextField} that represents the text field for player name.
	 */
	private JTextField NametextField;
	/**
	 * Variable of type {@link JTextField} that represents the text field for player initial credits.
	 */
	private JTextField CreditTextField;
	/**
	 * Variable of type {@link JLabel} that represents an array of cards images.
	 */
	private JLabel[] imgCards = {
		    new JLabel(""),
		    new JLabel(""),
		    new JLabel(""),
		    new JLabel(""),
		    new JLabel(""),
		};
	/**
	 * Variable of type {@link JButton} that represents an array of buttons for each card image.
	 */
	private JButton[] btnCard = {
		    new JButton(""),
		    new JButton(""),
		    new JButton(""),
		    new JButton(""),
		    new JButton(""),
		};
	/**
	 * Variable of type {@link paytableFrame} that represents the pay table displayed.
	 */
	PayTable paytableFrame;
	/**
	 * Variable of type {@link Integer} that represents the last result of a hand evaluation.
	 */
	private int result;
	/**
	 * Variable of type {@link Integer} that represents an array of cards to hold according to the best strategy.
	 */
	private int[] hCardAdvice;
	/**
	 * Variable of type {@link Boolean} that represents an array of cards that have a hold tag..
	 */
	boolean[] isCardHold;
	/**
	 * Variable of type {@link Boolean} that represents an array of cards that have a hold tag according to the best strategy.
	 */
	boolean[] adviceHold;
	
	
	/**
	 * Constructor specifying the variant of Video Poker being used (initial balance is set to zero, since it will be set later)
	 * and creating a back-end engine of type {@link Interactive}. This will allow the interface to have objects of type {@link Player}, 
	 * {@link Statistics}, {@link Deck} and also {@link Variants}, according to the parameter passed to the 
	 * constructor.
	 * @param optionTable variation of Video Poker used in the game.
	 */	
	public GameBoard(Variants optionTable){
		gameplay = new Interactive(0, optionTable);
	}
	
	/**
	 * Initialize the initial contents of the frame, setting the frame properties and creating the welcome screen.
	 */
	public void initializeGUI() {		
		frame = new JFrame("Video Poker by Group 2");
		frame.getContentPane().setPreferredSize(new Dimension(870, 600));		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		ImageIcon img = new ImageIcon(GameBoard.class.getResource("/images/AH.png"));
		frame.setIconImage(img.getImage());
		frame.setBackground(SystemColor.textHighlight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildWelcomeScreen();
	}
	
	/**
	 * Creates a welcome screen with a image, a start game button and  text fields on which the {@link NametextField} 
	 * and {@link CreditTextField} will be set. 
	 */
	private void buildWelcomeScreen() {
		// Creates the welcome panel
		WelcomePanel = new GamePanel();
		frame.add(WelcomePanel);
		
		// Adding welcome image
		JLabel image = new JLabel("Welcome image");
		image.setIcon(new ImageIcon(GameBoard.class.getResource("/images/welcomeScreenBigImage.png")));
		image.setBounds(40, 24, 790, 345);
		WelcomePanel.add(image);
		
		// Adding start game button
		JButton btnNewGame = new JButton("Start Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("New game started with credit = " + Integer.parseInt(CreditTextField.getText()));					
				
				// Updating credits of the player
				gameplay.user.incCredits(Integer.parseInt(CreditTextField.getText()));
				
				// Starts the main screen to play
				buildMainScreen();
			}
		});
		btnNewGame.setForeground(Color.BLACK);
		btnNewGame.setBackground(SystemColor.textHighlight);
		btnNewGame.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewGame.setBounds(345, 460, 179, 49);
		btnNewGame.setEnabled(false);
		WelcomePanel.add(btnNewGame);
		
		// Add Player Name TextField
		NametextField = new JTextField();
		NametextField.setHorizontalAlignment(SwingConstants.CENTER);
		NametextField.setFont(new Font("Arial", Font.PLAIN, 16));
		NametextField.setBounds(144, 380, 219, 49);
		WelcomePanel.add(NametextField);
		NametextField.setColumns(10);		
		
		// Add Player Credits TextField
		CreditTextField = new JTextField();		
		CreditTextField.setHorizontalAlignment(SwingConstants.CENTER);
		CreditTextField.setFont(new Font("Arial", Font.PLAIN, 16));
		CreditTextField.setBounds(507, 380, 219, 49);
		CreditTextField.setColumns(10);
		WelcomePanel.add(CreditTextField);		
		// Make sure a new game doesn't start unless the credit is inserted
		CreditTextField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  changed();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  changed();
			  }
			  public void insertUpdate(DocumentEvent e) {
			  	changed();
			  }

			  public void changed() {
				 if (CreditTextField.getText().equals("")){
					 btnNewGame.setEnabled(false);
				 } else {
					 btnNewGame.setEnabled(true);
				 }
			  }
		});
	}
		
	/**
	 * Creates the main screen, where most game actions are made including betting, holding and drawing cards.
	 * The method will use the following panels: 
	 * <ul>
	 * <li><tt>MainPanel</tt> - GamePanel where most of Video Poker game happens. Displays the user information;</li>
	 * <li><tt>AdvicePanel</tt> - GamePanel where the best strategy for a specific hand is displayed;</li>
	 * <li><tt>GamOverPanel</tt> - GamePanel that displays the game over message.</li>
	 * </ul>
	 */
	private void buildMainScreen() {
		
		// Hides the welcome panel and makes the main panel visible
		WelcomePanel.setVisible(false);		
		MainPanel = new GamePanel();
		frame.add(MainPanel);
		
		// Adds the Advice Panel to background
		AdvicePanel = new GamePanel();		
		AdvicePanel.setVisible(false);
		AdvicePanel.setBackground(Color.WHITE);
		makeAdvicePanel();
		frame.add(AdvicePanel);		
		
		// Add the player name
		JLabel PlayerName;
		if(NametextField.getText().equals("")){
			PlayerName = new JLabel("Player 1");
		} else { 
			PlayerName = new JLabel(NametextField.getText());			
		}
		PlayerName.setIcon(new ImageIcon(GameBoard.class.getResource("/images/PlayerName.png")));
		PlayerName.setFont(new Font("Arial", Font.PLAIN, 18));
		PlayerName.setBounds(17, 11, 220, 23);
		MainPanel.add(PlayerName);
		
		// Adding bet value display
		JLabel betVal = new JLabel(Integer.toString(gameplay.getBetValue()));
		betVal.setFont(new Font("Arial", Font.BOLD, 18));
		betVal.setIcon(new ImageIcon(GameBoard.class.getResource("/images/Betting.png")));
		betVal.setBounds(314, 11, 168, 23);
		MainPanel.add(betVal);
		
		// Adding credits display
		JLabel creditsVal = new JLabel(Integer.toString(gameplay.user.getCredits()));
		creditsVal.setFont(new Font("Arial", Font.PLAIN, 18));
		creditsVal.setIcon(new ImageIcon(GameBoard.class.getResource("/images/credits.png")));
		creditsVal.setBounds(615, 11, 220, 23);
		MainPanel.add(creditsVal);
		
		// Displays the pay table	
		String[] columns = {"Code", "Name", "High", "Low", "Close", "Volume"};
		String[][] data = new String[gameplay.tableDB.getWinningHands().length][6]; 
        for (int i=0; i<gameplay.tableDB.getWinningHands().length; i++ ){
        	for(int j=1; j<=5; j++){        		
        		data[i][j] = Integer.toString(gameplay.tableDB.getPaytableValue(i, j-1));
        	}
        	data[i][0] = gameplay.tableDB.getWinHand(i);
        }        
        paytableFrame = new PayTable(data,columns);      
        MainPanel.add(paytableFrame);
        
        // Add winning hand display
        JLabel WinningHandText = new JLabel("");
        WinningHandText.setHorizontalAlignment(SwingConstants.CENTER);
		WinningHandText.setFont(new Font("Arial", Font.BOLD, 15));
		WinningHandText.setBounds(129, 230, 605, 25);
		MainPanel.add(WinningHandText);
		
		// Display the back of the first 5 cards
		displayBackCards();
		
		// Button "Bet"
		JButton btnBet = new JButton("Bet");
		btnBet.setEnabled(true);
		btnBet.setBackground(SystemColor.control);
		btnBet.setFont(new Font("Arial", Font.PLAIN, 16));
		btnBet.setBounds(44, 550, 105, 30);
		MainPanel.add(btnBet);
		
		// Button "Bet Max"
		JButton btnBetMax = new JButton("Bet max");
		btnBetMax.setEnabled(true);
		btnBetMax.setFont(new Font("Arial", Font.PLAIN, 16));
		btnBetMax.setBackground(SystemColor.menu);
		btnBetMax.setBounds(193, 550, 105, 30);
		MainPanel.add(btnBetMax);
		
		// Button "Deal"
		JButton btnDeal = new JButton("Deal");
		btnDeal.setEnabled(false);
		btnDeal.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDeal.setBackground(SystemColor.menu);
		btnDeal.setBounds(342, 550, 140, 30);
		MainPanel.add(btnDeal);
		
		// Button "Advice"
		JButton btnAdvice = new JButton("Advice");
		btnAdvice.setEnabled(false);
		btnAdvice.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAdvice.setBackground(SystemColor.menu);
		btnAdvice.setBounds(526, 550, 140, 30);
		MainPanel.add(btnAdvice);		
		
		// Button "Draw"
		JButton btnDraw = new JButton("Draw");
		btnDraw.setEnabled(false);
		btnDraw.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDraw.setBackground(SystemColor.menu);
		btnDraw.setBounds(710, 550, 105, 30);
		MainPanel.add(btnDraw);
				
		// Buttons of cards
		for(int i = 0; i<5; i++){
			btnCard[i].setBounds(17+i*167, 266, 150, 218);				
			btnCard[i].setContentAreaFilled(false);
			MainPanel.add(btnCard[i]);
			btnCard[i].setEnabled(false);
		}
		
		// Action on press of "Bet" Button
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				// Changes the bet value
				if(gameplay.getBetValue() == 5){
					gameplay.setBetValue(1);
					paytableFrame.highlightCol(5,1);					
				} else {
					// Checks if player has enough credits for the bet
					if(gameplay.user.getCredits() - gameplay.getBetValue() > 0){
						gameplay.setBetValue(gameplay.getBetValue() + 1);
						paytableFrame.highlightCol(gameplay.getBetValue()-1,gameplay.getBetValue());
					} else {
						// Displays error message
						WinningHandText.setText("You don't have money for a bet higher than " + gameplay.getBetValue());
						WinningHandText.setForeground(new Color(204, 0, 51));
						paytableFrame.highlightCol(gameplay.getBetValue(),1);
						gameplay.setBetValue(1);
					}
				}
				
				// Updates the bet display
				betVal.setText(Integer.toString(gameplay.getBetValue()));

				// Updates button states
				btnDeal.setEnabled(true);	
			}
		});
		
		// Action on press of "Bet Max" Button
		btnBetMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks what is the legal maximum to bet
				int max = 6;
				do{
					max = max - 1;
				}while(gameplay.user.getCredits() - max < 0);
				
				// Updates table
				paytableFrame.highlightCol(gameplay.getBetValue(),max);
								
				// Changes the bet value
				gameplay.setBetValue(max);
		        betVal.setText(Integer.toString(gameplay.getBetValue()));			       
		        
				// Display back of cards
				displayBackCards();
				
				// Updates buttons states
				btnBet.setEnabled(false);
				btnBetMax.setEnabled(false);
				btnDeal.setEnabled(true);
			}
		});
		
		// Action on press of "Deal" Button
		btnDeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Resets the winning hand text
				WinningHandText.setText("");				
				MainPanel.add(WinningHandText);
				
				// Displays the hand
				gameplay.deck = new Deck();
				gameplay.user.newHand(gameplay.deck);			
				displayHand();				
				
				// Bet money is taken from the player (updates player credits)
				gameplay.user.incCredits(-gameplay.getBetValue());
				creditsVal.setText(Integer.toString(gameplay.user.getCredits()));
				
				// Updates the button states
				btnBet.setEnabled(false);
				btnBetMax.setEnabled(false);
				btnDeal.setEnabled(false);
				btnAdvice.setEnabled(true);
				btnDraw.setEnabled(true);
				for(int i=0; i<5; i++){
					btnCard[i].setEnabled(true);
					btnCard[i].setVisible(true);
				}			
			}
		});
		
				
		// Vector that will save the hold state of each card
		isCardHold = new boolean[5];
		for(int i=0; i<5; i++){
			isCardHold[i] = false;
		}			
		
		// Vector that will save the cards adviced to hold
		adviceHold = new boolean[5];
		for(int i=0; i<5; i++){
			adviceHold[i] = false;
		}
				
		// Definition of the hold image
		ImageIcon holdIcon =  new ImageIcon(GameBoard.class.getResource("/images/hold.png"));
		
		// Action on press of pressing the card 1
		btnCard[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[0]){
					btnCard[0].setIcon(null);
					isCardHold[0] = !isCardHold[0];
				} else {
					btnCard[0].setIcon(holdIcon);
					isCardHold[0] = !isCardHold[0];
				}
			}
		});
		
		// Action on press of pressing the card 2
		btnCard[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[1]){
					btnCard[1].setIcon(null);
					isCardHold[1] = !isCardHold[1];
				} else {
					btnCard[1].setIcon(holdIcon);
					isCardHold[1] = !isCardHold[1];
				}
			}
		});
		
		// Action on press of pressing the card 3
		btnCard[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[2]){
					btnCard[2].setIcon(null);
					isCardHold[2] = !isCardHold[2];
				} else {
					btnCard[2].setIcon(holdIcon);
					isCardHold[2] = !isCardHold[2];
				}
			}
		});
				
		// Action on press of pressing the card 4
		btnCard[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[3]){
					btnCard[3].setIcon(null);
					isCardHold[3] = !isCardHold[3];
				} else {
					btnCard[3].setIcon(holdIcon);
					isCardHold[3] = !isCardHold[3];
				}
			}
		});
		
		// Action on press of pressing the card 5
		btnCard[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[4]){
					btnCard[4].setIcon(null);
					isCardHold[4] = !isCardHold[4];
				} else {
					btnCard[4].setIcon(holdIcon);
					isCardHold[4] = !isCardHold[4];
				}
			}
		});
		
		// Action on press of "Advice" Button
		btnAdvice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Gets the cards to hold
				hCardAdvice = gameplay.tableDB.getAdvice(gameplay.user.getHand());
				
				// Updates the displayed cards
				Arrays.fill(adviceHold, false);
				for(int j=0; j < hCardAdvice.length; j++){					
					adviceHold[hCardAdvice[j]-1] = true;
				}
				
				// Activates the advice panel
				showAdvicePanel();
			}
		});
					
					
		// Action on press of "Draw" Button
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int nbHolds = 0;
				int index = 0;
				
				// Creation of holdVec
				for(int i=0; i<5; i++){
					if(isCardHold[i]) nbHolds++;
				}
				int[] holdVec = new int[nbHolds];
				for(int i=0; i<5; i++){
					if(isCardHold[i]){
						holdVec[index] = i+1;
						index++;
					}
				}
				
				// Update of the hand according to the cards to hold
				gameplay.user.holdCards(holdVec, gameplay.deck);
				displayHand();			
				
				// Evaluates the hand
				result = gameplay.tableDB.evaluation(gameplay.user.getHand());
				
				// Checks if player won or lost
				if (result < 0){
					// Player lost, checks if game is over due to 0 credits
					if(gameplay.user.getCredits() <= 0){
						// Displays game over screen
						makeGameOverPanel();
						frame.add(GameOverPanel);
					}
				}else{
					// Increments the credit won by the player
					gameplay.user.incCredits(gameplay.tableDB.getPaytableValue(result, gameplay.getBetValue()-1));
					creditsVal.setText(Integer.toString(gameplay.user.getCredits()));
				}
				
				
				// Displays the winning hand
				if(result > 0){
					WinningHandText.setText("You won " + gameplay.tableDB.getPaytableValue(result, gameplay.getBetValue()-1) + " credits " + "with " + gameplay.tableDB.getWinHand(result) + "!");	
					WinningHandText.setForeground(new Color(51, 204, 51));
				} else {
					WinningHandText.setText("You lost the " + gameplay.getBetValue() + " credits :(");
					WinningHandText.setForeground(new Color(204, 0, 51));
				}
				MainPanel.add(WinningHandText);
				
				// Updates the credits of the player
				creditsVal.setText(Integer.toString(gameplay.user.getCredits()));
				
				// Resets the deck
				gameplay.deck = new Deck();
				
				// Resets the hold vector
				for(int i=0; i<5; i++){
					isCardHold[i] = false;
				}
				
				// Updates the button states
				btnBet.setEnabled(true);
				btnBetMax.setEnabled(true);
				btnDeal.setEnabled(true);;
				btnAdvice.setEnabled(false);
				btnDraw.setEnabled(false);
				
				for(int i=0; i<5; i++){
					btnCard[i].setIcon(null);
					btnCard[i].setEnabled(false);
				}	
			}
		});
	}
		
	/**
	 * Creates the {@link GameOverPanel} adding the game over image.
	 */
	void makeGameOverPanel(){	
		// Crates the panel and sets initial visibility
		MainPanel.setVisible(false);
		GameOverPanel = new GamePanel();

		// Adding gameover image
		JLabel gameoverImg = new JLabel("");
		gameoverImg.setIcon(new ImageIcon(GameBoard.class.getResource("/images/GameoverScreen.png")));
		gameoverImg.setBounds(0, 0, 870, 600);
		GameOverPanel.add(gameoverImg);
	}
		
	/**
	 * Creates the {@link AdvicePanel} adding all the images and buttons needed.
	 */
	void makeAdvicePanel(){
		// Adds the main image
		JLabel AdviceImg = new JLabel("");
		AdviceImg.setIcon(new ImageIcon(GameBoard.class.getResource("/images/AdviceScreen.png")));
		AdviceImg.setBounds(0, 0, 870, 600);
		AdvicePanel.add(AdviceImg);
		
		// Adds the advice text
        JLabel AdviceText = new JLabel("The best advice we can give you to win is to hold these cards!");
        AdviceText.setHorizontalAlignment(SwingConstants.CENTER);
        AdviceText.setFont(new Font("Arial", Font.BOLD, 15));
        AdviceText.setBounds(120, 230, 605, 25);
        AdvicePanel.add(AdviceText);
		
		
		// Creates the return to main panel button		
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				AdvicePanel.setVisible(false);
				MainPanel.setVisible(true);
				displayHand();
			}
		});
		btnReturn.setForeground(Color.BLACK);
		btnReturn.setBackground(SystemColor.menu);
		btnReturn.setFont(new Font("Arial", Font.PLAIN, 16));	
		btnReturn.setBounds(356, 550, 140, 30);
		AdvicePanel.add(btnReturn);
		
		// Creates the hold button
		JButton btnAdviceHold = new JButton("Hold cards");
		btnAdviceHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				for (int i=0; i<5; i++){					
					if (adviceHold[i] == true){
						isCardHold[i] = true;
					} else {
						isCardHold[i] = false;
					}
				}
				AdvicePanel.setVisible(false);
				MainPanel.setVisible(true);
				displayHand();
			}
		});
		btnAdviceHold.setForeground(Color.BLACK);
		btnAdviceHold.setBackground(SystemColor.menu);
		btnAdviceHold.setFont(new Font("Arial", Font.PLAIN, 16));	
		btnAdviceHold.setBounds(356, 495, 140, 40);
		AdvicePanel.add(btnAdviceHold);
		
	}
	
	/**
	 * Changes the visibility of the {@link MainPanel} panel and reveals the {@link AdvicePanel} panel with the cards to hold according 
	 * to the best strategy.
	 */
	public void showAdvicePanel(){		
		MainPanel.setVisible(false);
		AdvicePanel.setVisible(true);
		
		displayHandAdvice();				
	}

	/**
	 * Displays the back of the cards for all the cards in the {@link MainPanel} panel.
	 */
	private void displayBackCards(){		
		for (int i=0; i<5; i++){
			imgCards[i].setBounds(17+i*167, 266, 150, 218);
			imgCards[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/back.png")));
			MainPanel.add(imgCards[i]);
		}
	}
	
	/**
	 * Displays images of the cards of a hand in the {@link MainPanel} panel.
	 */
	private void displayHand(){
		String cardName;
		for (int i=0; i<5; i++){
			imgCards[i].setIcon(null);
			cardName = gameplay.user.getHand()[i].toString();
			imgCards[i].setBounds(17+i*167, 266, 150, 218);
			if(isCardHold[i] == true){
				imgCards[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/"+cardName+".png")));
				btnCard[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/hold.png")));				
			} else {
				imgCards[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/"+cardName+".png")));
				btnCard[i].setIcon(null);
			}
			MainPanel.add(imgCards[i]);
		}
	}
	
	/**
	 * Displays images of the cards to hold according to the best strategy in the {@link AdvicePanel} panel.
	 */
	private void displayHandAdvice(){
		String cardName;
		for (int i=0; i<5; i++){
			if (adviceHold[i] == true){
				imgCards[i].setIcon(null);
				cardName = gameplay.user.getHand()[i].toString();
				imgCards[i].setBounds(17+i*167, 266, 150, 218);
				imgCards[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/"+cardName+".png")));
				AdvicePanel.add(imgCards[i]);
			} else {
				imgCards[i].setBounds(17+i*167, 266, 150, 218);
				imgCards[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/back.png")));
				AdvicePanel.add(imgCards[i]);
			}
		}
	}
	
}
