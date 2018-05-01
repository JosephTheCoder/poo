package PokerProject;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
	private static final int DEFAULT_WIDTH = 870;
	private static final int DEFAULT_HEIGHT = 600;
	
	public GamePanel() {
		super();
		
		this.setVisible(true);
		this.setBounds(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setLayout(null);		
	}

}
