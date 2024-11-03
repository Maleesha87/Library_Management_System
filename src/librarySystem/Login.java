package librarySystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame {

    public Login() {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUsername = "root";
        String dbPassword = "";

        setTitle("Library System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(34, 40, 49));
        setResizable(false);

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 400, 500);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(null);
        add(leftPanel);

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(400, 0, 400, 500);
        rightPanel.setBackground(new Color(0, 53, 102));
        rightPanel.setLayout(null);
        add(rightPanel);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\White.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setBounds(150, 30, 100, 100);
        rightPanel.add(logoLabel);

        JLabel l1 = new JLabel("LIBRARY", SwingConstants.CENTER);
        l1.setFont(new Font("Arial", Font.BOLD, 32));
        l1.setForeground(Color.WHITE);
        l1.setBounds(10, 160, 380, 50);
        rightPanel.add(l1);

        JLabel l2 = new JLabel("System", SwingConstants.CENTER);
        l2.setFont(new Font("Arial", Font.BOLD, 32));
        l2.setForeground(Color.WHITE);
        l2.setBounds(10, 200, 380, 50);
        rightPanel.add(l2);

        JLabel l3 = new JLabel("New to our platform? Sign Up now.", SwingConstants.CENTER);
        l3.setFont(new Font("Arial", Font.ITALIC, 14));
        l3.setForeground(Color.LIGHT_GRAY);
        l3.setBounds(10, 250, 380, 50);
        rightPanel.add(l3);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome Back!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0, 53, 102));
        welcomeLabel.setBounds(5, 30, 400, 50);
        leftPanel.add(welcomeLabel);

        // Instruction Label
        JLabel l4 = new JLabel("Please enter your credentials", SwingConstants.CENTER);
        l4.setFont(new Font("Arial", Font.PLAIN, 14));
        l4.setForeground(new Color(112, 112, 112));
        l4.setBounds(5, 80, 400, 30);
        leftPanel.add(l4);

        // User Type
        JComboBox<String> userType = new JComboBox<>(new String[]{"User", "Admin"});
        userType.setFont(new Font("Arial", Font.PLAIN, 16));
        userType.setBounds(50, 130, 300, 40);
        userType.setBackground(Color.WHITE);
        leftPanel.add(userType);

        // Username Field
        JTextField usernameField = new JTextField("Username");
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBounds(50, 190, 300, 40);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(217, 217, 217), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        usernameField.setForeground(Color.GRAY);
        leftPanel.add(usernameField);

        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Username");
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });

        // Password Field
        JPasswordField passwordField = new JPasswordField("Password");
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBounds(50, 250, 300, 40);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(217, 217, 217), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        passwordField.setForeground(Color.GRAY);
        leftPanel.add(passwordField);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        JButton signInButton = new JButton("SIGN IN");
        signInButton.setFont(new Font("Arial", Font.BOLD, 16));
        signInButton.setBackground(new Color(0, 51, 102));
        signInButton.setForeground(Color.WHITE); 
        signInButton.setFocusPainted(false);
        signInButton.setBounds(50, 320, 300, 40);
        signInButton.setBorder(BorderFactory.createEmptyBorder());
        signInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        leftPanel.add(signInButton);

        // Forgot Password
        JLabel forgotPassword = new JLabel("<html><u>Forgot password?</u></html>");
        forgotPassword.setForeground(new Color(0, 53, 102));
        forgotPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        forgotPassword.setBounds(50, 380, 200, 25);
        forgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        leftPanel.add(forgotPassword);

        forgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Forgot_pw();
                dispose();
            }
        });

        signInButton.addActionListener(e -> {
            String selectedUserType = userType.getSelectedItem().toString();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (selectedUserType.equals("Admin")) {
                if (username.equals("admin") && password.equals("admin")) {
                    JOptionPane.showMessageDialog(this, "Welcome, Admin!");
                    new Books(); 
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Admin credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (selectedUserType.equals("User")) { 
                String name = checkCredentials(url, dbUsername, dbPassword, username, password);
                if (name != null) {
                    JOptionPane.showMessageDialog(this, "Welcome, " + name + " (" + selectedUserType + ")!");
                    Loan n1 = new Loan(); 
                    n1.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a valid user type.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });


        setVisible(true);
    }

    private static String checkCredentials(String url, String dbUsername, String dbPassword, String username, String password) {
        String name = null;
        String query = "SELECT name FROM register WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    name = resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return name;
    }
}
