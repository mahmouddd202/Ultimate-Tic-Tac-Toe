package SenArchPackage;

import javax.swing.JFrame;

public class MainForLeaderboard {

	public MainForLeaderboard() {
		
		 PlayerAndScoreGUI app = new PlayerAndScoreGUI ();
	      app.setVisible  (true);
	      app.setSize     (500, 200);
	      app.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	      app.setResizable(false);
	      app.setLocationRelativeTo(null);
		
	}
	
}
