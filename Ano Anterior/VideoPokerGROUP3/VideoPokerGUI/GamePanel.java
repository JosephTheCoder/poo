package VideoPokerGUI;

import javax.swing.JPanel;

/**
 * Class {@link GamePanel} extends Class {@link JPanel} and represents the panels type used in Video Poker GUI.
 * <p>
 * By using this class, all panels created will have always a set of dimensions and properties that are different
 * from the default of {@link JPanel} Class.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
	/**
	 * Variable of type {@link Integer} that represents the width of the panel.
	 */
	private static final int DEFAULT_WIDTH = 870;
	/**
	 * Variable of type {@link Integer} that represents the height of the panel.
	 */
	private static final int DEFAULT_HEIGHT = 600;
	
	public GamePanel() {
		super();
		
		this.setVisible(true);
		this.setBounds(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setLayout(null);		
	}

}
