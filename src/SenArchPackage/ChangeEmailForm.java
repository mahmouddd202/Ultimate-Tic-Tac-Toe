package SenArchPackage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChangeEmailForm extends JFrame {

    private JTextField emailField;
    private JButton saveButton;

    public ChangeEmailForm() {
        setTitle("Change Email");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel emailLabel = new JLabel("New Email:");
        emailField = new JTextField();
        saveButton = new JButton("Save");

        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(saveButton);

        add(panel);
        setVisible(true);

        saveButton.addActionListener(e -> changeEmail());
    }

    private void changeEmail() {
        String newEmail = emailField.getText().trim();

        if (newEmail.isEmpty() || !newEmail.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email.");
            return;
        }

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "UPDATE users SET email = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, newEmail);
            stmt.setInt(2, Session.userId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Email updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email update failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
