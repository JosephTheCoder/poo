package PokerProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


@SuppressWarnings("serial")
public class GameBoard extends JFrame{
	
	private Game gameplay;
	
	private GamePanel WelcomePanel;
	private GamePanel MainPanel;
	private GamePanel AdvicePanel;
	private GamePanel GameOverPanel;
	
	private JTextField NametextField;
	private JTextField CreditTextField;
	
	private JLabel[] imgCards = {
		    new JLabel("Card1"),
		    new JLabel("Card2"),
		    new JLabel("Card3"),
		    new JLabel("Card4"),
		    new JLabel("Card5"),
		};
	
	JTable paytableFrame;
	
	private int result;
	private int[] hCardAdvice;
	boolean[] isCardHold;
	boolean[] adviceHold;
	
	public GameBoard(){
		super();
		setTitle("Video Poker by Group 2");
		
		// Window Size
		setBounds(0, 0, 870, 600);
		setResizable(true);	
		
		// Other attributes		
		setLayout(null);
		setBackground(SystemColor.textHighlight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	// Initialize the contents of the frame.
	public void initialize() {		
		setVisible(true);				
		buildGUI();
	}
	
	private void buildGUI() {
		WelcomePanel = new GamePanel();
		add(WelcomePanel);
		
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
				gameplay = new Interactive(Integer.parseInt(CreditTextField.getText()));				
				play();
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
	
	
	private void play() {
		
		// Adds the Advice Panel to background
		AdvicePanel = new GamePanel();		
		AdvicePanel.setVisible(false);
		add(AdvicePanel);
		
		WelcomePanel.setVisible(false);		
		MainPanel = new GamePanel();
		add(MainPanel);
		
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
		
		/*Setting initial bet value
		gameplay.betValue = 1;*/
		// Adding bet value display
		JLabel betVal = new JLabel(Integer.toString(gameplay.betValue));
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
        		//data[i][j] = Integer.toString(j*gameplay.tableDB.getPaytableValue(i));
        		data[i][j] = Integer.toString(gameplay.tableDB.getPaytableValue(i, j-1));
        	}
        	data[i][0] = gameplay.tableDB.getWinHand(i);
        }        
        paytableFrame = new JTable(data,columns){
        	@Override
            public boolean isCellEditable(int row, int column) {                
                    return false;               
            }
        };
        paytableFrame.setShowGrid(true);
        paytableFrame.setBackground(UIManager.getColor("Table.selectionBackground"));
        paytableFrame.setFont(new Font("Arial", Font.PLAIN, 14));        
        paytableFrame.setShowHorizontalLines(false);
        paytableFrame.setRowSelectionAllowed(false);
        paytableFrame.setFocusable(false);        
        paytableFrame.setBounds(17, 45, 818, 175);
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
		btnBet.setBounds(44, 517, 105, 30);
		MainPanel.add(btnBet);
		
		// Button "Bet Max"
		JButton btnBetMax = new JButton("Bet max");
		btnBetMax.setEnabled(true);
		btnBetMax.setFont(new Font("Arial", Font.PLAIN, 16));
		btnBetMax.setBackground(SystemColor.menu);
		btnBetMax.setBounds(193, 517, 105, 30);
		MainPanel.add(btnBetMax);
		
		// Button "Deal"
		JButton btnDeal = new JButton("Deal");
		btnDeal.setEnabled(false);
		btnDeal.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDeal.setBackground(SystemColor.menu);
		btnDeal.setBounds(342, 517, 140, 30);
		MainPanel.add(btnDeal);
		
		// Button "Advice"
		JButton btnAdvice = new JButton("Advice");
		btnAdvice.setEnabled(false);
		btnAdvice.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAdvice.setBackground(SystemColor.menu);
		btnAdvice.setBounds(526, 517, 140, 30);
		MainPanel.add(btnAdvice);		
		
		// Button "Draw"
		JButton btnDraw = new JButton("Draw");
		btnDraw.setEnabled(false);
		btnDraw.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDraw.setBackground(SystemColor.menu);
		btnDraw.setBounds(710, 517, 105, 30);
		MainPanel.add(btnDraw);
				
		// Buttons of cards
		JButton btnCard1 = new JButton("");
		btnCard1.setBounds(17, 266, 150, 218);				
		btnCard1.setContentAreaFilled(false);
		MainPanel.add(btnCard1);
		JButton btnCard2 = new JButton("");
		btnCard2.setBounds(184, 266, 150, 218);				
		btnCard2.setContentAreaFilled(false);
		MainPanel.add(btnCard2);
		JButton btnCard3 = new JButton("");
		btnCard3.setBounds(351, 266, 150, 218);				
		btnCard3.setContentAreaFilled(false);
		MainPanel.add(btnCard3);
		JButton btnCard4 = new JButton("");
		btnCard4.setBounds(518, 266, 150, 218);				
		btnCard4.setContentAreaFilled(false);
		MainPanel.add(btnCard4);
		JButton btnCard5 = new JButton("");
		btnCard5.setBounds(685, 266, 150, 218);				
		btnCard5.setContentAreaFilled(false);
		MainPanel.add(btnCard5);
		btnCard1.setEnabled(false);
		btnCard2.setEnabled(false);
		btnCard3.setEnabled(false);
		btnCard4.setEnabled(false);
		btnCard5.setEnabled(false);
		
		
		// Action on press of "Bet" Button
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				// Changes the bet value
				if(gameplay.betValue == 5){
					gameplay.betValue = 1;
					highlightCol(5,1);					
				} else {
					// Checks if player has enough credits for the bet
					if(gameplay.user.getCredits() - gameplay.betValue > 0){
						gameplay.betValue = gameplay.betValue + 1;
						highlightCol(gameplay.betValue-1,gameplay.betValue);
					} else {
						// Displays error message
						WinningHandText.setText("You don't have money for a bet higher than " + gameplay.betValue);
						WinningHandText.setForeground(new Color(204, 0, 51));
						highlightCol(gameplay.betValue,1);
						gameplay.betValue = 1;
					}
				}
				
				//Updates the bet display
				betVal.setText(Integer.toString(gameplay.betValue));

				// Updates button states
				btnDeal.setEnabled(true);	
			}
		});
		
		// Action on press of "Bet Max" Button
		btnBetMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks what is the legal maximumn to bet
				int max = 6;
				do{
					max = max - 1;
				}while(gameplay.user.getCredits() - max < 0);
				
				// Updates table
				highlightCol(gameplay.betValue,max);
								
				// Changes the bet value
				gameplay.betValue = max;
		        betVal.setText(Integer.toString(gameplay.betValue));			       
		        
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
				gameplay.user.incCredits(-gameplay.betValue);
				creditsVal.setText(Integer.toString(gameplay.user.getCredits()));
				
				// Updates the button states
				btnBet.setEnabled(false);
				btnBetMax.setEnabled(false);
				btnDeal.setEnabled(false);
				btnAdvice.setEnabled(true);
				btnDraw.setEnabled(true);
				btnCard1.setEnabled(true);
				btnCard2.setEnabled(true);
				btnCard3.setEnabled(true);
				btnCard4.setEnabled(true);
				btnCard5.setEnabled(true);
				btnCard1.setVisible(true);
				btnCard2.setVisible(true);
				btnCard3.setVisible(true);
				btnCard4.setVisible(true);
				btnCard5.setVisible(true);
			}
		});
		
		
		
		// Vector that will save the hold state of each card
		isCardHold = new boolean[5];
		for(int i=0; i<5; i++){
			isCardHold[i] = false;
		}
		
		// Vector with Hold Advice
		adviceHold = new boolean[5];
		Arrays.fill(adviceHold, false);					
		
		// Definition of the hold image
		ImageIcon holdIcon =  new ImageIcon(GameBoard.class.getResource("/images/hold.png"));
		
		// Action on press of pressing the card 1
		btnCard1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[0]){
					btnCard1.setIcon(null);
					isCardHold[0] = !isCardHold[0];
				} else {
					btnCard1.setIcon(holdIcon);
					isCardHold[0] = !isCardHold[0];
				}
			}
		});
		
		// Action on press of pressing the card 2
		btnCard2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[1]){
					btnCard2.setIcon(null);
					isCardHold[1] = !isCardHold[1];
				} else {
					btnCard2.setIcon(holdIcon);
					isCardHold[1] = !isCardHold[1];
				}
			}
		});
		
		// Action on press of pressing the card 3
		btnCard3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[2]){
					btnCard3.setIcon(null);
					isCardHold[2] = !isCardHold[2];
				} else {
					btnCard3.setIcon(holdIcon);
					isCardHold[2] = !isCardHold[2];
				}
			}
		});
				
		// Action on press of pressing the card 4
		btnCard4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[3]){
					btnCard4.setIcon(null);
					isCardHold[3] = !isCardHold[3];
				} else {
					btnCard4.setIcon(holdIcon);
					isCardHold[3] = !isCardHold[3];
				}
			}
		});
		
		// Action on press of pressing the card 5
		btnCard5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Checks if the card was already on hold
				if(isCardHold[4]){
					btnCard5.setIcon(null);
					isCardHold[4] = !isCardHold[4];
				} else {
					btnCard5.setIcon(holdIcon);
					isCardHold[4] = !isCardHold[4];
				}
			}
		});
		
		// Action on press of "Advice" Button
		btnAdvice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hCardAdvice = gameplay.tableDB.getAdvice(gameplay.user.getHand());
				for(int j=0; j<hCardAdvice.length; j++){
					System.out.println(hCardAdvice[j]);
					adviceHold[hCardAdvice[j]-1] = true;
				}
				if (hCardAdvice.length > 0){
					int idx = 0;
					for(int i=0; i<5; i++){
						if (hCardAdvice[idx] == i+1){
							adviceHold[i] = true;
							idx++;
							if (!(idx < hCardAdvice.length)) break;
						} else {
							adviceHold[i] = false;
						}
							
					}
				}
				for(int j=0; j<adviceHold.length; j++){
					System.out.println(adviceHold[j]);
				}
				
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
				result = Evaluation.handType(gameplay.user.getHand());
				
				// Checks if player won or lost
				if (result < 0){
					// Player lost, checks if game is over due to 0 credits
					if(gameplay.user.getCredits() <= 0){
						// Displays game over screen
						MainPanel.setVisible(false);
						GameOverPanel = new GamePanel();
						add(GameOverPanel);		
						
						// Adding gameover image
						JLabel gameoverImg = new JLabel("");
						gameoverImg.setIcon(new ImageIcon(GameBoard.class.getResource("/images/GameoverScreen.png")));
						gameoverImg.setBounds(0, 0, 870, 600);
						GameOverPanel.add(gameoverImg);
					}
				}else{
					// Increments the credit won by the player
					gameplay.user.incCredits(gameplay.tableDB.getPaytableValue(result, gameplay.betValue-1));
					creditsVal.setText(Integer.toString(gameplay.user.getCredits()));
					//System.out.println("player wins with a " + gameplay.tableDB.getWinHand(result).toUpperCase() + " and his credit is " + user.getCredits());
				}
				
				
				// Displays the winning hand
				if(result > 0){
					WinningHandText.setText("You won " + gameplay.tableDB.getPaytableValue(result, gameplay.betValue-1) + " credits " + "with " + gameplay.tableDB.getWinHand(result) + "!");	
					WinningHandText.setForeground(new Color(51, 204, 51));
				} else {
					WinningHandText.setText("You lost the " + gameplay.betValue + " credits :(");
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
				
				btnCard1.setIcon(null);
				btnCard2.setIcon(null);
				btnCard3.setIcon(null);
				btnCard4.setIcon(null);
				btnCard5.setIcon(null);
				btnCard1.setEnabled(false);
				btnCard2.setEnabled(false);
				btnCard3.setEnabled(false);
				btnCard4.setEnabled(false);
				btnCard5.setEnabled(false);
			}
		});
		
		
	}
	
	// Create Advice Panel
	public void showAdvicePanel(){
		
		MainPanel.setVisible(false);
		AdvicePanel.setVisible(true);
		
		displayHandAdvice();
		
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				AdvicePanel.setVisible(false);
				MainPanel.setVisible(true);
				displayHand();
				return;
			}
		});
		btnReturn.setForeground(Color.BLACK);
		btnReturn.setBackground(SystemColor.menu);
		btnReturn.setFont(new Font("Arial", Font.PLAIN, 16));
		//btnReturn.setBounds(371, 560, 128, 29);
		btnReturn.setBounds(342, 517, 140, 30);
		AdvicePanel.add(btnReturn);
		
	}
	
	
	public class CellHighlighterRenderer extends DefaultTableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
		    Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
		   
		    cell.setBackground(Color.YELLOW);
		    
		    return cell;
		}
	}
	
	public void highlightCol(int previndex, int index){
		// Column to highlight 
		TableColumn colToHighligh = paytableFrame.getColumnModel().getColumn(index);
		
		// Define new style and default style
        CellHighlighterRenderer cellRenderer = new CellHighlighterRenderer();
        TableCellRenderer defaultRenderer = colToHighligh.getCellRenderer();
		
		// update table style
        if (index > 0){
        	colToHighligh.setCellRenderer(cellRenderer);
        }
		colToHighligh = paytableFrame.getColumnModel().getColumn(previndex);
		colToHighligh.setCellRenderer(defaultRenderer);
	
		paytableFrame.repaint();
	}
		
	private void displayBackCards(){
		// Display the back of the first 5 cards
		for (int i=0; i<5; i++){
			imgCards[i].setBounds(17+i*167, 266, 150, 218);
			imgCards[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/back.png")));
			MainPanel.add(imgCards[i]);
		}
	}
	
	private void displayHand(){
		String cardName;
		for (int i=0; i<5; i++){
			imgCards[i].setIcon(null);
			cardName = gameplay.user.getHand()[i].toString();
			imgCards[i].setBounds(17+i*167, 266, 150, 218);
			imgCards[i].setIcon(new ImageIcon(GameBoard.class.getResource("/images/"+cardName+".png")));
			MainPanel.add(imgCards[i]);
		}
	}
	
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
