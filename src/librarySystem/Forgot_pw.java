package librarySystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

public class Forgot_pw {
    private JFrame frame;
    private JTextField usernameField;

    public Forgot_pw() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Forgot Password Form");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(13, 37, 63));
        leftPanel.setBounds(0, 0, 350, 500);
        leftPanel.setLayout(null);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\White.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  
        leftPanel.add(logoLabel);


        JLabel l1 = new JLabel("LIBRARY");
        l1.setFont(new Font("Arial", Font.BOLD, 32));
        l1.setForeground(Color.WHITE);
        l1.setBounds(100, 150, 200, 50);
        leftPanel.add(l1);

        JLabel l2 = new JLabel("System");
        l2.setFont(new Font("Arial", Font.BOLD, 24));
        l2.setForeground(Color.WHITE);
        l2.setBounds(130, 200, 100, 30);
        leftPanel.add(l2);

        frame.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(350, 0, 450, 500);
        rightPanel.setLayout(null);

        JLabel forgotPasswordLabel = new JLabel("Forgot Password");
        forgotPasswordLabel.setFont(new Font("Arial", Font.BOLD, 24));
        forgotPasswordLabel.setForeground(new Color(13, 37, 63));
        forgotPasswordLabel.setBounds(125, 140, 200, 30);
        rightPanel.add(forgotPasswordLabel);

        JLabel instructionLabel = new JLabel("Please enter your username");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setForeground(new Color(80, 80, 80));
        instructionLabel.setBounds(125, 185, 250, 30);
        rightPanel.add(instructionLabel);

        usernameField = new JTextField();
        usernameField.setBounds(125, 220, 200, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        rightPanel.add(usernameField);

        JButton resetPasswordButton = new JButton("RESET PASSWORD");
        resetPasswordButton.setBounds(125, 300, 200, 40);
        resetPasswordButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetPasswordButton.setBackground(new Color(13, 37, 63));
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.setFocusPainted(false);
        resetPasswordButton.setBorder(BorderFactory.createEmptyBorder());
        resetPasswordButton.addActionListener(e -> sendOtpEmail());

        rightPanel.add(resetPasswordButton);

        JButton backButton = new JButton("BACK");
        backButton.setBounds(125, 360, 200, 30);
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setBackground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        backButton.addActionListener(e -> frame.dispose());

        rightPanel.add(backButton);

        frame.add(rightPanel);
        frame.setVisible(true);
    }

    private void sendOtpEmail() {
        String username = usernameField.getText();
        if (!username.isEmpty()) {
            String recipientEmail = getEmailFromDatabase(username);
            if (recipientEmail != null) {
                String otpCode = generateOtp();
                boolean emailSent = sendEmail(recipientEmail, otpCode);

                if (emailSent) {
                    frame.dispose();
                    new OTP(otpCode, username); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to send OTP email.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Username not found.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter your username.");
        }
    }

    private String getEmailFromDatabase(String username) {
        String email = null;
        String url = "jdbc:mysql://localhost:3306/library";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("SELECT email FROM register WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage());
        }
        return email;
    }

    private String generateOtp() {
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    private boolean sendEmail(String recipient, String otpCode) {
        final String senderEmail = "mataralibrary@gmail.com";
        final String senderPassword = "qklb trlz tdff ilkp";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otpCode);
            String email = otpCode;
			String subject = null;
			Object body = null;
			sendEmail(email, subject, body);
            System.out.println("New OTP sent to email: " + email);

            Transport.send(message);
            System.out.println("OTP Email Sent Successfully!");
            return true;
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(frame, "Email sending failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

	private void sendEmail(String email, String subject, Object body) {
		
	}
}