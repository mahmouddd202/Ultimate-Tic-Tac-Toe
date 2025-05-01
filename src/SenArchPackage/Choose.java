package SenArchPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Choose implements ActionListener {
	
	JFrame f = new JFrame("UTIMATE TIC-TAC-TOE")  ;
		
		JButton LeaderBoard = new JButton("Leader Board");
		JButton _4x4 = new JButton("4x4");
		JButton _3v3_1v1 = new JButton("3x3");
		JButton profileButton = new JButton("profile");
	
	public Choose() {
		f.setSize(500,200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
				
		
		LeaderBoard.setFocusable(false);
		LeaderBoard.addActionListener(this);
		LeaderBoard.setBackground(new Color(0x804314));
		LeaderBoard.setForeground(Color.white);
		LeaderBoard.setFont(new Font("serif", Font.BOLD,20));
				
		
		_4x4.setFocusable(false);
		_4x4.addActionListener(this);
		_4x4.setBackground(new Color(0x804314));
		_4x4.setForeground(Color.white);
		_4x4.setFont(new Font("serif", Font.BOLD,20));
		
		_3v3_1v1.setFocusable(false);
		_3v3_1v1.addActionListener(this);
		_3v3_1v1.setBackground((new Color(0x006666)));
		_3v3_1v1.setForeground(Color.white);
		_3v3_1v1.setFont(new Font("serif", Font.BOLD,20));
		
		profileButton.setFocusable(false);
		profileButton.addActionListener(this);
		profileButton.setBackground(new Color(0x006666));
		profileButton.setForeground(Color.WHITE);
		profileButton.setFont(new Font("Serif", Font.BOLD, 20));
		profileButton.setPreferredSize(new Dimension(100, 100));

		
		
		_3v3_1v1.setPreferredSize(new Dimension(100, 100));
		_4x4.setPreferredSize(new Dimension(100, 100));
		LeaderBoard.setPreferredSize(new Dimension(100, 100));
		
		f.setLayout(new GridLayout(2,2));
		
		f.add(_3v3_1v1);
		f.add(_4x4);
		f.add(LeaderBoard);
		f.add(profileButton);
		
		f.setVisible(true);

		}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == profileButton) {
			    new ProfileForm();
			    f.dispose();
		
		    }
		

	    else if (e.getSource() == _4x4) {
	        PlayerChooser chooser = new PlayerChooser(4, 4, 16);
	        f.dispose();
	    }
	    else if (e.getSource() == _3v3_1v1) {
	        PlayerChooser chooser = new PlayerChooser(3, 3, 9);
	        f.dispose();
	    }
	     else if (e.getSource()== LeaderBoard) {
	    	 MainForLeaderboard mn = new MainForLeaderboard();
	    	 f.dispose();
	     }
	}
}