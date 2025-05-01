package SenArchPackage;

import javax.swing.*;    


import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class PlayerAndScoreGUI extends JFrame {

    Connection con;

    // GUI Components remain the same
    JTextArea playerTextArea = new JTextArea();
    JLabel scoreLabel = new JLabel("score: ");
    JTextField scoreTextFiled = new JTextField(10);
    JLabel nameLabel = new JLabel("Name: ");
    JTextField nameTextField = new JTextField(10);
    JButton addButton = new JButton("Add");
    JButton exitButton = new JButton("Back");
    JButton deleteAll = new JButton("delete all");
    JButton displayAll = new JButton("display all");

    private LinkedList<PlayerAndScore> PlayerAndScores = new LinkedList<>();

    public PlayerAndScoreGUI() {
        try {
            // Get connection from the DatabaseConnection singleton
            con = DatabaseConnection.getInstance().getConnection();
            System.out.println("Connection created");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        retrieveFromMySQL();

        JPanel flow1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel flow2Panel = new JPanel(new GridLayout(1, 2));
        JPanel gridPanel = new JPanel(new GridLayout(2, 1));

        playerTextArea.setEditable(false);

        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("serif", Font.BOLD, 20));

        flow1Panel.add(nameLabel);
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("serif", Font.BOLD, 20));
        flow1Panel.add(nameTextField);

        flow1Panel.setBackground(new Color(0x804314));

        addButton.setFocusable(false);
        exitButton.setFocusable(false);
        deleteAll.setFocusable(false);
        displayAll.setFocusable(false);

        flow2Panel.add(addButton);
        flow2Panel.add(exitButton);
        flow2Panel.add(deleteAll);

        gridPanel.add(flow1Panel);
        gridPanel.add(flow2Panel);

        JScrollPane scrollPane = new JScrollPane(playerTextArea);
        add(scrollPane, BorderLayout.CENTER);

        add(gridPanel, BorderLayout.SOUTH);

        addButton.addActionListener(event -> addPlayer());
        addButton.setForeground(Color.white);
        addButton.setFont(new Font("serif", Font.BOLD, 20));
        addButton.setBackground((new Color(0x006666)));

        exitButton.addActionListener(event -> exitApplication());
        exitButton.setForeground(Color.white);
        exitButton.setFont(new Font("serif", Font.BOLD, 20));
        exitButton.setBackground((new Color(0x006666)));

        deleteAll.addActionListener(event -> deleteAllFromMySQL());
        deleteAll.setForeground(Color.white);
        deleteAll.setFont(new Font("serif", Font.BOLD, 20));
        deleteAll.setBackground((new Color(0x006666)));

        displayAll.addActionListener(event -> displayAll());
        displayAll.setForeground(Color.white);
        displayAll.setFont(new Font("serif", Font.BOLD, 20));
        displayAll.setBackground((new Color(0x006666)));
    }


   private boolean isPlayerInLinkedList (String idStr)
   {
      boolean inList = false;

      for (PlayerAndScore player : PlayerAndScores)
      {
         if (player.getName().compareToIgnoreCase (idStr) == 0)
         {
            inList = true;
         }
      }

      return inList;
   }

   private void addPlayer ()
   
   {
	   
      if (isPlayerInLinkedList (nameTextField.getText()) == true)
      {
         JOptionPane.showMessageDialog (null, "Error: players name is already in the database.");
  }
      else
  {
    	  try
     {	 
    	String name = nameTextField.getText();
    	int id;
		id = 0;
	    exportToMySQL( name, id);

    	if (scoreTextFiled.getText()=="") {
    		PlayerAndScore player = new PlayerAndScore (name,0);
    		}
 
		else {
			PlayerAndScore player = new PlayerAndScore (name,id);
			
			PlayerAndScores.add (player);
			
			displayAll ();
			
			nameTextField.setText("");
			scoreTextFiled.setText("");
		        	}


         }
         catch (PlayerAndScoreException error)
         {
            JOptionPane.showMessageDialog (null, error.toString ());
         }
      }
   }

   private void deletePlayer()
   {
	   String idStr = scoreTextFiled.getText();

	   int id;
	   try {
		   id = Integer.parseInt(idStr);
	  }catch (NumberFormatException e) {
	    System.err.println("Error: name is not a valid .");
	    idStr= "0";
	    return; 
	  }
   	   	   
   
	   if (PlayerAndScores.size() == 0)
	   {
		   JOptionPane.showMessageDialog (null, "Error: Database is empty.");
	   }
	   else if (isPlayerInLinkedList (scoreTextFiled.getText()) == false)
	   {
		   JOptionPane.showMessageDialog (null, "Error: The player is not in the database.");
	   }
	   else
	   {
		   for (int s = 0; s < PlayerAndScores.size(); s++){
			   
			   int currscore = PlayerAndScores.get (s).getscore ();

			   if (currscore == 0)
			   {
				   PlayerAndScores.remove (s);
			   }
		   }
		   
		   displayAll ();

		   nameTextField.setText("");
		   scoreTextFiled.setText("");
      }
   }

   private void displayAll ()
   {
	    playerTextArea.setText(""); // Clear the text area before displaying names

	    // Create a list to store names already displayed
	    LinkedList<String> displayedNames = new LinkedList<>();

	    for (PlayerAndScore player : PlayerAndScores) {
	    	// Check if the name has already been displayed
	    	if (!displayedNames.contains(player.getName())) {
	    		// Append the name to the text area
	    		playerTextArea.append(player + "\n");
    
	    		// Add the name to the list of displayed names
	            displayedNames.add(player.getName());
	        }
	    }
	}
   
   private void exportToMySQL(String name, int score) {
	    try {
	        String sql = "INSERT INTO name_score (name, score, user_id) VALUES (?, ?, ?)";
	        PreparedStatement preparedStatement = con.prepareStatement(sql);

	        preparedStatement.setString(1, name);
	        preparedStatement.setInt(2, score);
	        preparedStatement.setInt(3, Session.userId);  // 💥 Connect player to current user

	        int rowsInserted = preparedStatement.executeUpdate();

	        if (rowsInserted > 0) {
	            System.out.println("A new record was inserted successfully for user: " + Session.username);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

   
   private void exitApplication ()
   {
	
	   Choose c = new Choose();
      this.dispose();
   }
   
   private void retrieveFromMySQL() {
	    try {
	        String sql = "SELECT name, score FROM name_score WHERE user_id = ?";
	        PreparedStatement preparedStatement = con.prepareStatement(sql);

	        preparedStatement.setInt(1, Session.userId);  // 💥 Select only the current user's players

	        ResultSet resultSet = preparedStatement.executeQuery();
	        
	        PlayerAndScores.clear(); // Clear the old list before loading new data

	        while (resultSet.next()) {
	            String name = resultSet.getString("name");
	            int score = resultSet.getInt("score");

	            try {
	                PlayerAndScore player = new PlayerAndScore(name, score);
	                PlayerAndScores.add(player);
	            } catch (PlayerAndScoreException e) {
	                e.printStackTrace();
	            }
	        }

	        displayAll(); // Update the GUI

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

   
   private void deleteAllFromMySQL() {
       try {
           // Prepare the SQL DELETE statement
    	   String sql = "DELETE FROM name_score";
    	   PreparedStatement preparedStatement = con.prepareStatement(sql);
   
    	   // Execute the DELETE statement
    	   int rowsDeleted = preparedStatement.executeUpdate();
   
    	   if (rowsDeleted > 0) {
    		   System.out.println("All records were deleted successfully.");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
   
//   private void closeConnection() {
//       if (con != null) {
//           try {
//               con.close();
//               System.out.println("Connection closed");
//           } catch (SQLException e) {
//        	   e.printStackTrace(); // Handle SQL exception
//           }
//       }
//   }
} 