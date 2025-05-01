package SenArchPackage;

import javax.swing.*;


import com.mysql.cj.protocol.x.XProtocolRow;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class PlayerChooser extends JFrame {

    private JComboBox<String> playerXCombo;
    private JComboBox<String> playerOCombo;
    private JButton startButton;
    private JButton backButton;
    private int rows, cols, cells; // Needed for Main1v1 constructor
    public PlayerChooser(int rows, int cols, int cells) {
        this.rows = rows;
        this.cols = cols;
        this.cells = cells;

        setTitle("Choose Players");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with background color and padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0x2C2C2C)); // Dark gray background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // top, left, bottom, right padding

        // Labels
        JLabel labelX = new JLabel("Select Player X:");
        labelX.setForeground(Color.WHITE);
        labelX.setFont(new Font("Serif", Font.BOLD, 16));

        JLabel labelO = new JLabel("Select Player O:");
        labelO.setForeground(Color.WHITE);
        labelO.setFont(new Font("Serif", Font.BOLD, 16));

        // Combo boxes
        playerXCombo = new JComboBox<>();
        playerOCombo = new JComboBox<>();
        styleComboBox(playerXCombo);
        styleComboBox(playerOCombo);

        // Buttons
        startButton = new JButton("Start Game");
        backButton = new JButton("Back");

        startButton.setBackground(new Color(0x006666));
        backButton.setBackground(new Color(0x804314));
        startButton.setForeground(Color.WHITE);
        backButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Serif", Font.BOLD, 15));
        backButton.setFont(new Font("Serif", Font.BOLD, 15));
        startButton.setFocusable(false);
        backButton.setFocusable(false);

        // Add components to panel
        mainPanel.add(labelX);
        mainPanel.add(playerXCombo);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(labelO);
        mainPanel.add(playerOCombo);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(0x2C2C2C)); // match main background
        buttonPanel.add(startButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel);

        // Set main panel as content pane
        setContentPane(mainPanel);

        // Load player names
        loadPlayersFromDatabase();

        // Listeners
        backButton.addActionListener(e -> {
            new Choose();
            dispose();
        });

        startButton.addActionListener(e -> startGame());

        setVisible(true);
    }

    private void loadPlayersFromDatabase() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT name FROM name_score WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Session.userId);  // r
            ResultSet rs = ps.executeQuery();

            ArrayList<String> playerNames = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                playerXCombo.addItem(name);
                playerOCombo.addItem(name);
            }

            if (playerXCombo.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No players found! Please add players first.");
                dispose(); // Close this window
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        String playerX = (String) playerXCombo.getSelectedItem();
        String playerO = (String) playerOCombo.getSelectedItem();

        if (playerX.equals(playerO)) {
            JOptionPane.showMessageDialog(this, "Player X and Player O must be different!");
            return;
        }

        // Pass playerX and playerO to the Main1v1 (or your game class)
        Main1v1 game = new Main1v1(rows, cols, cells, playerX, playerO);
        dispose(); // Close the choose window
    }
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(new Color(0xEEEEEE));
        comboBox.setForeground(Color.BLACK);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboBox.setFocusable(false);
        comboBox.setMaximumSize(new Dimension(250, 30));
    }

    
}
