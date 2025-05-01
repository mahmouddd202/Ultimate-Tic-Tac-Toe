package SenArchPackage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUpForm extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton signUpButton;
    private JButton goToLoginButton;

    public SignUpForm() {
        setTitle("Sign Up - Ultimate Tic Tac Toe");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0x2C2C2C));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Fields
        usernameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(makeLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(makeLabel("Email:"));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(makeLabel("Password:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));

        // Buttons
        signUpButton = new JButton("Sign Up");
        goToLoginButton = new JButton("Go to Login");

        signUpButton.setBackground(new Color(0x006666));
        signUpButton.setForeground(Color.WHITE);
        goToLoginButton.setBackground(new Color(0x804314));
        goToLoginButton.setForeground(Color.WHITE);

        panel.add(signUpButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(goToLoginButton);

        add(panel);
        setVisible(true);
        
        // Placeholder action
        goToLoginButton.addActionListener(e -> {
            new LoginForm();  // Open login form
            dispose();        // Close sign up form
        });


        signUpButton.addActionListener(e -> registerUser());

    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Serif", Font.BOLD, 14));
        return label;
    }
    private void registerUser() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Basic validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();

            // Check if user/email already exists
            String checkQuery = "SELECT * FROM users WHERE username = ? OR email = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username or email already in use.");
                return;
            }

            // Insert user
            String insertQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password); // Later we can hash this

            int rowsInserted = insertStmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Account created successfully!");

                // Send email here
                MailService.sendEmail(
                    email,
                    "Welcome to Ultimate Tic Tac Toe!",
                    "Hello " + username + ",\n\nThank you for signing up! Get ready to enjoy the game!"
                );

                // Clear fields
                usernameField.setText("");
                emailField.setText("");
                passwordField.setText("");
            }


        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
    
}
