package PokerProject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Window.Type;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame{

	public JFrame frame;
	private JTable table;

	

	// Creation of application interface.
	public MainFrame() {
		initialize();
	}

	/* Launch the application interface.
	// Launch the application interface.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Create the main frame
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);									
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	// Initialize the contents of the frame.
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Video Poker by Group 2");
		frame.setBackground(SystemColor.textHighlight);
		frame.getContentPane().setBackground(SystemColor.textHighlight);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Button "Bet"
		JButton btnBet = new JButton("Bet");
		btnBet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnBet.setEnabled(true);
		btnBet.setBackground(SystemColor.control);
		btnBet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBet.setBounds(44, 517, 105, 30);
		frame.getContentPane().add(btnBet);
		
		// Button "Bet Max"
		JButton btnBetMax = new JButton("Bet max");
		btnBetMax.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBetMax.setBackground(SystemColor.menu);
		btnBetMax.setBounds(193, 517, 105, 30);
		frame.getContentPane().add(btnBetMax);
		
		// Cards images
		JLabel imgCard1 = new JLabel("imgCard1");
		imgCard1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		imgCard1.setBackground(Color.WHITE);
		imgCard1.setIcon(new ImageIcon(MainFrame.class.getResource("/images/2C.png")));
		imgCard1.setBounds(17, 266, 150, 218);
		frame.getContentPane().add(imgCard1);
		
		JLabel imgCard2 = new JLabel("imgCard2");
		imgCard2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		imgCard2.setIcon(new ImageIcon(MainFrame.class.getResource("/images/1D.png")));
		imgCard2.setBackground(Color.WHITE);
		imgCard2.setBounds(184, 266, 150, 218);
		frame.getContentPane().add(imgCard2);
		
		JLabel imgCard3 = new JLabel("imgCard3");
		imgCard3.setBackground(Color.WHITE);
		imgCard3.setBounds(351, 266, 150, 218);
		frame.getContentPane().add(imgCard3);
		
		JLabel imgCard4 = new JLabel("imgCard4");
		imgCard4.setBackground(Color.WHITE);
		imgCard4.setBounds(518, 266, 150, 218);
		frame.getContentPane().add(imgCard4);
		
		JLabel label = new JLabel("imgCard4");
		label.setIcon(new ImageIcon(MainFrame.class.getResource("/images/2C.png")));
		label.setBackground(Color.WHITE);
		label.setBounds(685, 266, 150, 218);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("Deal");
		button.setOpaque(true);
		button.setForeground(Color.DARK_GRAY);
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBackground(Color.YELLOW);
		button.setBounds(342, 517, 140, 30);
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("Advice");
		button_1.setOpaque(true);
		button_1.setForeground(Color.DARK_GRAY);
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_1.setBackground(Color.YELLOW);
		button_1.setBounds(526, 517, 140, 30);
		frame.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("Draw");
		button_2.setOpaque(true);
		button_2.setForeground(Color.DARK_GRAY);
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button_2.setBackground(Color.YELLOW);
		button_2.setBounds(710, 517, 105, 30);
		frame.getContentPane().add(button_2);
		
		JLabel lblNewLabel = new JLabel("Player name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/images/PlayerName.png")));
		lblNewLabel.setBounds(17, 11, 220, 23);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel label_2 = new JLabel("5");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		label_2.setIcon(new ImageIcon(MainFrame.class.getResource("/images/Betting.png")));
		label_2.setBounds(390, 11, 84, 23);
		
		
		frame.getContentPane().add(label_2);
		
		JLabel label_1 = new JLabel("Player name");
		label_1.setIcon(new ImageIcon(MainFrame.class.getResource("/images/credits.png")));
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(615, 11, 220, 23);
		frame.getContentPane().add(label_1);
		
		table = new JTable();
		table.setBackground(UIManager.getColor("Table.foreground"));
		table.setShowGrid(false);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setShowHorizontalLines(false);
		table.setRowSelectionAllowed(false);
		table.setBounds(20, 42, 824, 181);
		frame.getContentPane().add(table);
		
		JLabel lblWinningHand = new JLabel("Winning Hand");
		lblWinningHand.setForeground(new Color(204, 0, 51));
		lblWinningHand.setHorizontalAlignment(SwingConstants.CENTER);
		lblWinningHand.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWinningHand.setBounds(129, 230, 605, 25);
		frame.getContentPane().add(lblWinningHand);
		frame.setBounds(100, 100, 870, 600);
		
		// Action on press of "Bet" Button
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imgCard1.setIcon(new ImageIcon(MainFrame.class.getResource("/images/3C.png")));
				imgCard1.setBounds(0, 0, 150, 218);

				// Disables the button
				btnBet.setEnabled(false);
				btnBetMax.setEnabled(false);
			}
		});
		
		
		
	}
}
