package librarySystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OTP {
    private String correctOtp;  
	private String username;

	public OTP(String otpCode, String username) {
	    this.correctOtp = otpCode; 
	    this.username = username; 
	    createAndShowGUI();
	}

	private void createAndShowGUI() {
        JFrame frame = new JFrame("Library System OTP Verification");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\Dark_blue.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel checkLabel = new JLabel("Check your Mailbox");
        checkLabel.setFont(new Font("Arial", Font.BOLD, 24));
        checkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enterOtpLabel = new JLabel("Please enter the OTP to proceed");
        enterOtpLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        enterOtpLabel.setForeground(Color.DARK_GRAY);
        enterOtpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField otpField = new JTextField(15);
        otpField.setFont(new Font("Arial", Font.PLAIN, 16));
        otpField.setMaximumSize(new Dimension(200, 30));
        otpField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton verifyButton = new JButton("Verify");
        verifyButton.setFont(new Font("Arial", Font.BOLD, 16));
        verifyButton.setBackground(new Color(0, 44, 84));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(checkLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(enterOtpLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(otpField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(verifyButton);
        leftPanel.add(Box.createVerticalGlue());

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(0, 44, 84));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        ImageIcon libraryLogoIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\White.png");
        Image libraryLogoImage = libraryLogoIcon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        JLabel libraryLogoLabel = new JLabel(new ImageIcon(libraryLogoImage));
        libraryLogoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel l1 = new JLabel("LIBRARY");
        l1.setFont(new Font("Arial", Font.BOLD, 32));
        l1.setForeground(Color.WHITE);
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel l2 = new JLabel("System");
        l2.setFont(new Font("Arial", Font.BOLD, 24));
        l2.setForeground(Color.WHITE);
        l2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel("Your premier digital library for borrowing and reading books.");
        descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        descriptionLabel.setForeground(Color.WHITE);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(libraryLogoLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(l1);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(l2);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(descriptionLabel);
        rightPanel.add(Box.createVerticalGlue());

        frame.add(leftPanel);
        frame.add(rightPanel);

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredOtp = otpField.getText();

                if (enteredOtp.equals(correctOtp)) {
                    JOptionPane.showMessageDialog(frame, "OTP Verified Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose(); 
                    new ResetPassword(username);  
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid OTP. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}