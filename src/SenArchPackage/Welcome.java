package SenArchPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Welcome implements ActionListener {
	JFrame f = new JFrame("UTIMATE TIC-TAC-TOE");
	JButton leaderboard = new JButton("Leaderboard");
	JButton play = new JButton("Play");
	JPanel buttonPanel = new JPanel();
	JPanel WelcomePanel = new JPanel();
	JLabel hello = new JLabel();
	
	public Welcome(){
			
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500,200);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		

		leaderboard.setFocusable(false);
		leaderboard.addActionListener(this);
		leaderboard.setBackground(new Color(0x804314));
		leaderboard.setFont(new Font("serif", Font.BOLD,22));
		leaderboard.setForeground(Color.white);
		
		play.setFocusable(false);
		play.addActionListener(this);
		play.setBackground(new Color(0x804314));
		play.setFont(new Font("serif", Font.BOLD,22));
		play.setForeground(Color.white);
		
		buttonPanel.setBackground(Color.GRAY);
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.setBounds(0,75,500,100);
		buttonPanel.add(play);
		buttonPanel.add(leaderboard);
				
		WelcomePanel.setBackground(new Color(0x006666));
		WelcomePanel.setBounds(0,0,500,75);
		WelcomePanel.add(hello);
		WelcomePanel.setLayout(new BorderLayout());
		
		hello.setForeground(Color.white);
		hello.setBounds(30,30,470,30);
		hello.setText("     Welcome To Ultimate Tic Tac Toe");
		hello.setFont(new Font("serif", Font.BOLD,25));
		hello.setBackground(new Color(0x230046));
		
		
		f.add(buttonPanel);
		f.add(WelcomePanel);
		f.setVisible(true);
	}
			

	@Override
	public void actionPerformed(ActionEvent e) {
	
		
		 if(e.getSource() == play) {
			Choose c = new Choose();
			f.dispose();
		}
		
		else if(e.getSource() == leaderboard) {
			MainForLeaderboard app = new MainForLeaderboard ();
			f.dispose();
			
		}
		
	}
		
}
	