package librarySystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Books extends JFrame {
    private JTextField bookIdField, bookTitleField, authorField, yearField;
    private JButton addButton, updateButton, deleteButton, closeButton;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    public Books() {
        setTitle("Library System - Admin");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        connectDatabase();

        // Left Panel 
        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);

        // Right Panel 
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER); 

        loadBookData();

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        closeButton.addActionListener(e -> navigateToLogin());

        setVisible(true);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(0, 51, 102));
        leftPanel.setPreferredSize(new Dimension(350, 0));

        // Title Panel
        JPanel titlePanel = createTitlePanel();
        leftPanel.add(titlePanel);

        // Form Panel
        JPanel formPanel = createFormPanel();
        leftPanel.add(formPanel);

        return leftPanel;
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\White.png");
        Image image = imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon);

        JLabel titleLabel = new JLabel("Library System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 51, 102));
        titleLabel.setPreferredSize(new Dimension(350, 50));

        titlePanel.add(imageLabel);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createLabel("Book Id"));
        bookIdField = createStyledTextField();
        formPanel.add(bookIdField);

        formPanel.add(createLabel("Book Title"));
        bookTitleField = createStyledTextField();
        formPanel.add(bookTitleField);

        formPanel.add(createLabel("Author"));
        authorField = createStyledTextField();
        formPanel.add(authorField);

        formPanel.add(createLabel("Year"));
        yearField = createStyledTextField();
        formPanel.add(yearField);

        JPanel buttonPanel = createButtonPanel();
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buttonPanel);

        return formPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setOpaque(false);

        addButton = createStyledButton("Add");
        updateButton = createStyledButton("Update");
        deleteButton = createStyledButton("Delete");
        closeButton = createStyledButton("Close");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(closeButton);

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        tableModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Year"}, 0);
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(30);

        bookTable.getTableHeader().setBackground(new Color(0, 51, 102));
        bookTable.getTableHeader().setForeground(Color.WHITE);
        bookTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        bookTable.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setPreferredSize(new Dimension(300, 450));

        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 3));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setMaximumSize(new Dimension(250, 25));
        textField.setBackground(new Color(230, 230, 250));
        textField.setForeground(Color.DARK_GRAY);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 10));
        button.setBackground(new Color(200, 200, 250));
        button.setForeground(Color.DARK_GRAY);
        button.setPreferredSize(new Dimension(75, 25));
        return button;
    }

    private void connectDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/library";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    private void loadBookData() {
        try {
            tableModel.setRowCount(0);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_book");

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("title"));
                row.add(rs.getString("author"));
                row.add(rs.getInt("year"));
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addBook() {
        try {
            int id = Integer.parseInt(bookIdField.getText());
            String title = bookTitleField.getText();
            String author = authorField.getText();
            int year = Integer.parseInt(yearField.getText());

            String query = "INSERT INTO tbl_book (id, title, author, year) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, year);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully.");
            loadBookData();
            clearInputFields();

            pstmt.close();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        }
    }

    private void updateBook() {
        try {
            int id = Integer.parseInt(bookIdField.getText());
            String title = bookTitleField.getText();
            String author = authorField.getText();
            int year = Integer.parseInt(yearField.getText());

            String query = "UPDATE tbl_book SET title = ?, author = ?, year = ? WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book updated successfully.");
            loadBookData();
            clearInputFields();

            pstmt.close();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error updating book: " + e.getMessage());
        }
    }

    private void deleteBook() {
        try {
            int id = Integer.parseInt(bookIdField.getText());
            String query = "DELETE FROM tbl_book WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book deleted successfully.");
            loadBookData();
            clearInputFields();

            pstmt.close();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error deleting book: " + e.getMessage());
        }
    }

    private void clearInputFields() {
        bookIdField.setText("");
        bookTitleField.setText("");
        authorField.setText("");
        yearField.setText("");
    }

    private void navigateToLogin() {
        new Login();
        dispose();
    }
}
