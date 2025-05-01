package SenArchPackage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameEndPopup implements GameObserver {
    private Connection con;

    public GameEndPopup() {
        try {
            // Get connection from the DatabaseConnection singleton
            con = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onGameEnd(String winner) {
        if (winner.equals("tie")) {
            JOptionPane.showMessageDialog(null, "The game ended in a tie!");
        } else {
            JOptionPane.showMessageDialog(null, winner + " has won the game!");
            updatePlayerScore(winner);
        }
    }

    private void updatePlayerScore(String winnerName) {
        try {
            int scoreIncrement = 1;

            // First try to update existing player
            String updateSql = "UPDATE name_score SET score = score + ? WHERE name = ? AND user_id = ?";
            PreparedStatement pre = con.prepareStatement(updateSql);
            pre.setInt(1, scoreIncrement);
            pre.setString(2, winnerName);
            pre.setInt(3, Session.userId);

            int rowsAffected = pre.executeUpdate();

            if (rowsAffected == 0) {
                // If no rows were updated, create new entry
                //createNewPlayerEntry(winnerName);
            } else {
                System.out.println("Score updated successfully for player: " + winnerName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating score: " + e.getMessage());
        }
    }

   /* private void createNewPlayerEntry(String winnerName) {
        try {
            String insertSql = "INSERT INTO name_score (name, score, user_id) VALUES (?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(insertSql);
            pre.setString(1, winnerName);
            pre.setInt(2, 1); // Starting score of 1 for the win
            pre.setInt(3, Session.userId);

            int rowsInserted = pre.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("New player created with score: " + winnerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating player entry: " + e.getMessage());
        }
    }
    */
}