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


public class Choose implements ActionListener {
	
	JFrame f = new JFrame("UTIMATE TIC-TAC-TOE")  ;
	
		JButton _3x3 = new JButton("play vs AI");
		JButton _4x4 = new JButton("4x4");
		JButton _3v3_1v1 = new JButton("3x3");
		JButton back = new JButton("Back");
	
	public Choose() {
		f.setSize(500,200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
				
		
		_3x3.setFocusable(false);
		_3x3.addActionListener(this);
		_3x3.setBackground(new Color(0x804314));
		_3x3.setForeground(Color.white);
		_3x3.setFont(new Font("serif", Font.BOLD,20));
				
		
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
		
		back.setFocusable(false);
		back.addActionListener(this);
		back.setBackground((new Color(0x006666)));
		back.setForeground(Color.white);
		back.setFont(new Font("serif", Font.BOLD,20));
		
		
		_3v3_1v1.setPreferredSize(new Dimension(100, 100));
		_4x4.setPreferredSize(new Dimension(100, 100));
		_3x3.setPreferredSize(new Dimension(100, 100));
		back.setPreferredSize(new Dimension(100, 100));
		
		f.setLayout(new GridLayout(2,2));
		
		f.add(_3v3_1v1);
		f.add(_4x4);
		f.add(_3x3);
		f.add(back);
		
		f.setVisible(true);

		}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			Welcome w = new Welcome();
			f.dispose();
		}
//		else if (e.getSource() == _3x3) {
//			Main m = new Main();
//			f.dispose();
//			
//		}
		else if (e.getSource() == _4x4) {
			Main1v1 m1 = new Main1v1(4,4,16);
			f.dispose();
			
		}
		else if (e.getSource() == _3v3_1v1) {
			Main1v1 m2 = new Main1v1(3,3,9);
			f.dispose();
			
		}

	}

}
