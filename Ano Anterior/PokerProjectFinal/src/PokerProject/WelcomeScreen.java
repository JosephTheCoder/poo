package PokerProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JScrollBar;

public class WelcomeScreen {

	private JFrame frame;
	private final JPanel panel = new JPanel();
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeScreen window = new WelcomeScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WelcomeScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 887, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel.setBounds(0, 0, 870, 600);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewGame = new JButton("Return");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewGame.setForeground(Color.BLACK);
		btnNewGame.setBackground(UIManager.getColor("Table.light"));
		btnNewGame.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewGame.setBounds(371, 560, 128, 29);
		btnNewGame.setEnabled(false);
		panel.add(btnNewGame);
		
		textField_1 = new JTextField();
		textField_1.getDocument().addDocumentListener(new DocumentListener() {
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
			     if (textField_1.getText().equals("")){
			    	 btnNewGame.setEnabled(false);
			     }
			     else {
			    	 btnNewGame.setEnabled(true);
			    }

			  }
			});
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField_1.setColumns(10);
		textField_1.setBounds(507, 380, 219, 49);
		panel.add(textField_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(89, 88, 160, 105);
		panel.add(lblNewLabel);
		
		
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
		Object[][] data = {
			    {"Kathy", "Smith",
			     "Snowboarding", new Integer(5), new Boolean(false)},
			    {"John", "Doe",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Sue", "Black",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Jane", "White",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Joe", "Brown",
			     "Pool", new Integer(10), new Boolean(false)}
			};
		
		
	}
}
