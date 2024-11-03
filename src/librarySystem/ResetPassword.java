package librarySystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.border.AbstractBorder;

public class ResetPassword {
    private String username;

	public ResetPassword(String username) {
		this.username = username;
		createAndShowGUI();
	}

	private void createAndShowGUI() {
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUsername = "root";
        String dbPassword = "";

        // Frame Setup
        JFrame frame = new JFrame("Library System - Reset Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new GridLayout(1, 2));

        // Left Panel 
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 44, 84));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\White.png"); 
        Image scaledLogo = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); 
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel libraryLabel = new JLabel("LIBRARY System");
        libraryLabel.setFont(new Font("Arial", Font.BOLD, 28));
        libraryLabel.setForeground(Color.WHITE);
        libraryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel taglineLabel = new JLabel("Your premier digital library for borrowing and reading books.");
        taglineLabel.setForeground(Color.LIGHT_GRAY);
        taglineLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(libraryLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(taglineLabel);
        leftPanel.add(Box.createVerticalGlue());

        // Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        ImageIcon logoIcon1 = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\Dark_blue.png"); 
        Image scaledLogo1 = logoIcon1.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); 
        JLabel logoLabel1 = new JLabel(new ImageIcon(scaledLogo1));
        logoLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel resetLabel = new JLabel("Reset Password");
        resetLabel.setFont(new Font("Arial", Font.BOLD, 26));
        resetLabel.setForeground(new Color(0, 44, 84));
        resetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Please enter your new password");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setForeground(Color.DARK_GRAY);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Password Fields
        JPasswordField newPasswordField = createStyledPasswordField();
        JPasswordField confirmPasswordField = createStyledPasswordField();

        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        newPasswordLabel.setForeground(Color.DARK_GRAY);
        newPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setForeground(Color.DARK_GRAY);
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Reset Button
        JButton resetButton = new JButton("RESET PASSWORD");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(new Color(0, 44, 84));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setBorder(new RoundedBorder(15));

        // Reset Button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = String.valueOf(newPasswordField.getPassword());
                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                        String updateQuery = "UPDATE register SET password = ? WHERE username = ?";
                        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                            statement.setString(1, newPassword);
                            statement.setString(2, username); 
                            
                            int rowsUpdated = statement.executeUpdate();
                            
                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(frame, "Password successfully updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                
                                 new Login(); 
                                frame.dispose(); 
                            } else {
                                JOptionPane.showMessageDialog(frame, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error updating password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        rightPanel.add(logoLabel1);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(resetLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(instructionLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(newPasswordLabel);
        rightPanel.add(newPasswordField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(confirmPasswordLabel);
        rightPanel.add(confirmPasswordField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(resetButton);
        rightPanel.add(Box.createVerticalGlue());

        frame.add(leftPanel);
        frame.add(rightPanel);
        frame.setVisible(true);
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setBorder(new RoundedBorder(15));
        return passwordField;
    }

    static class RoundedBorder extends AbstractBorder {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getForeground());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
        }
    }
}