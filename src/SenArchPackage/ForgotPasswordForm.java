package SenArchPackage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ForgotPasswordForm extends JFrame {

    private JTextField emailField;
    private JButton sendButton;

    public ForgotPasswordForm() {
        setTitle("Forgot Password");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(new JLabel("Enter your email:"));
        emailField = new JTextField();
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));

        sendButton = new JButton("Send Recovery Email");
        panel.add(sendButton);

        add(panel);
        setVisible(true);

        sendButton.addActionListener(e -> sendRecoveryEmail());
    }

    private void sendRecoveryEmail() {
        String email = emailField.getText().trim();

        if (email.isEmpty() || !email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email.");
            return;
        }

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT username, password FROM users WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                String subject = "Password Recovery - Ultimate Tic Tac Toe";
                String body = "Hello " + username + ",\n\nYour password is: " + password + "\n\nGood luck!";

                MailService.sendEmail(email, subject, body);
                JOptionPane.showMessageDialog(this, "Recovery email sent to " + email);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No account found with that email.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
