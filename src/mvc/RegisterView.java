package mvc;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import librarySystem.Login;

public class RegisterView extends JFrame {
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField;
    private JButton signUpButton, signInButton;

    String url = "jdbc:mysql://localhost:3306/library";
    String dbUsername = "root";
    String dbPassword = "";

    public RegisterView() {
        // JFrame setup
        setTitle("Library System - Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left panel setup
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 53, 102));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        leftPanel.add(Box.createVerticalStrut(100));

        // Left panel logo
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\White.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(logoLabel);

        JLabel l1 = new JLabel("LIBRARY System", SwingConstants.CENTER);
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        l1.setForeground(Color.WHITE);
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel l2 = new JLabel("Already have an Account? Sign In now.", SwingConstants.CENTER);
        l2.setFont(new Font("Arial", Font.PLAIN, 14));
        l2.setForeground(Color.WHITE);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);

        signInButton = new JButton("SIGN IN");
        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        signInButton.setBackground(Color.WHITE);
        signInButton.setForeground(new Color(0, 53, 102));
        signInButton.setFocusPainted(false);
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(l1);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(l2);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(signInButton);
        leftPanel.add(Box.createVerticalGlue());

        // Right panel setup
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(40, 100, 40, 100));
        rightPanel.setBackground(Color.WHITE);

        // Right panel logo
        ImageIcon logoIcon1 = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\Dark_blue.png");
        Image scaledLogo1 = logoIcon1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel1 = new JLabel(new ImageIcon(scaledLogo1));
        logoLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(logoLabel1);

        JLabel signUpLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        signUpLabel.setFont(new Font("Arial", Font.BOLD, 24));
        signUpLabel.setForeground(new Color(0, 53, 102));
        signUpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Please provide your information to sign up.", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setForeground(Color.GRAY);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField("Name");
        setupPlaceholderText(nameField, "Name");
        setupTextField(nameField);

        usernameField = new JTextField("Username");
        setupPlaceholderText(usernameField, "Username");
        setupTextField(usernameField);

        emailField = new JTextField("Email");
        setupPlaceholderText(emailField, "Email");
        setupTextField(emailField);

        passwordField = new JPasswordField("Password");
        setupPlaceholderText(passwordField, "Password");
        setupTextField(passwordField);

        // Sign Up Button
        signUpButton = new JButton("SIGN UP");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        signUpButton.setBackground(new Color(0, 53, 102));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(signUpLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(instructionLabel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(nameField);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(usernameField);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(emailField);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(passwordField);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(signUpButton);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    private void setupTextField(JComponent textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        textField.setMaximumSize(new Dimension(300, 35));
    }

    private void setupPlaceholderText(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    public void addSignUpListener(ActionListener listener) {
        signUpButton.addActionListener(listener);
    }

    public void addSignInListener(ActionListener listener) {
        signInButton.addActionListener(listener);
    }

    public String getName() {
        return nameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public boolean insertData() {
        String name = getName();
        String username = getUsername();
        String email = getEmail();
        String password = getPassword();

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO register (name, username, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.executeUpdate();
            displayMessage("Registration successful!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            displayMessage("Error: " + e.getMessage());
            return false;
        }
    }
}
