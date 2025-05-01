package SenArchPackage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileForm extends JFrame {

    private JLabel welcomeLabel;
    private JButton changeEmailButton;
    private JButton changePasswordButton;
    private JButton forgotPasswordButton;
    private JButton deleteAccountButton;
    private JButton logoutButton;
    private JButton back;

    public ProfileForm() {
        setTitle("Profile - Ultimate Tic Tac Toe");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0x2C2C2C));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        welcomeLabel = new JLabel("Welcome, " + Session.username + "!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(20));

        // Create and center buttons
        changeEmailButton = makeButton("Change Email");
        changePasswordButton = makeButton("Change Password");
        forgotPasswordButton = makeButton("Forgot Password");
        deleteAccountButton = makeButton("Delete Account");
        logoutButton = makeButton("Logout");
        back = makeButton("Back");
        back.setBackground(new Color(0x804314));  // Different style

        panel.add(changeEmailButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(changePasswordButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(forgotPasswordButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(deleteAccountButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(logoutButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(back);

        add(panel);
        setVisible(true);

        // Action listeners
        changeEmailButton.addActionListener(e -> new ChangeEmailForm());
        changePasswordButton.addActionListener(e -> new ChangePasswordForm());
        forgotPasswordButton.addActionListener(e -> forgotPassword());
        deleteAccountButton.addActionListener(e -> deleteAccount());
        logoutButton.addActionListener(e -> {
            Session.clear();
            new LoginForm();
            dispose();
        });
        back.addActionListener(e -> goBack());
    }

    private JButton makeButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0x006666));
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 30));
        return button;
    }

    private void forgotPassword() {
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT email, password FROM users WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Session.userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String subject = "Password Recovery - Ultimate Tic Tac Toe";
                String body = "Hello " + Session.username + ",\n\nYour current password is: "
                        + rs.getString("password") + "\n\nStay safe!";

                MailService.sendEmail(email, subject, body);
                JOptionPane.showMessageDialog(this, "Recovery email sent to: " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Email not found for user.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error sending email: " + ex.getMessage());
        }
    }

    private void deleteAccount() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Session.userId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Account deleted successfully.");
                Session.clear();
                new LoginForm();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete account.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void goBack() {
        new Choose();
        dispose();
    }
}
