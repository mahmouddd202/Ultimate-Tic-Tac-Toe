package SenArchPackage;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton goToSignUpButton;
    private JButton forgotPasswordButton;

    public LoginForm() {
        setTitle("Login - Ultimate Tic Tac Toe");
        setSize(400, 320);
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
        passwordField = new JPasswordField();

        panel.add(makeLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(makeLabel("Password:"));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(5));

        // Forgot Password
        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setForeground(Color.BLUE);
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setContentAreaFilled(false);
        forgotPasswordButton.setFont(new Font("Serif", Font.PLAIN, 12));
        forgotPasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(forgotPasswordButton);
        panel.add(Box.createVerticalStrut(15));

        // Login and Sign Up buttons
        loginButton = new JButton("Login");
        goToSignUpButton = new JButton("Go to Sign Up");

        loginButton.setBackground(new Color(0x006666));
        loginButton.setForeground(Color.WHITE);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        goToSignUpButton.setBackground(new Color(0x804314));
        goToSignUpButton.setForeground(Color.WHITE);
        goToSignUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.setMaximumSize(new Dimension(200, 30));
        goToSignUpButton.setMaximumSize(new Dimension(200, 30));

        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(goToSignUpButton);

        add(panel);
        setVisible(true);

        // Actions
        loginButton.addActionListener(e -> loginUser());
        goToSignUpButton.addActionListener(e -> {
            new SignUpForm();
            dispose();
        });
        forgotPasswordButton.addActionListener(e -> new ForgotPasswordForm());
    }

    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Serif", Font.BOLD, 14));
        return label;
    }

    private void loginUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Save to session
                Session.userId = rs.getInt("id");
                Session.username = rs.getString("username");

                new Choose();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
}
