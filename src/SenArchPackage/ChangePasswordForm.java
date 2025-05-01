package SenArchPackage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ChangePasswordForm extends JFrame {

    private JPasswordField passwordField;
    private JButton saveButton;

    public ChangePasswordForm() {
        setTitle("Change Password");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel passwordLabel = new JLabel("New Password:");
        passwordField = new JPasswordField();
        saveButton = new JButton("Save");

        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(saveButton);

        add(panel);
        setVisible(true);

        saveButton.addActionListener(e -> changePassword());
    }

    private void changePassword() {
        String newPassword = new String(passwordField.getPassword()).trim();

        if (newPassword.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password must be at least 4 characters.");
            return;
        }

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "UPDATE users SET password = ? WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setInt(2, Session.userId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Password updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password update failed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
