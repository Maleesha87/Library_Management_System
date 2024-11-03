package librarySystem;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Loan extends JFrame {
    private JButton addButton, printButton;
    private JTable loanTable, returnTable;
    private DefaultTableModel loanTableModel, returnTableModel;
    private Connection connection;
    private JTextField booknameField, userNameField, loanDateField, dueDateField;

    public Loan() {
        setTitle("Library System - Loan Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        connectDatabase();

        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        JPanel leftPanel = createLeftPanel();
        add(leftPanel, BorderLayout.WEST);

        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        loadLoanData();

        setVisible(true);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(1000, 100));
        titlePanel.setBackground(new Color(0, 51, 102));

        titlePanel.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 3)); 

        JLabel titleLabel = new JLabel("Get Your Book", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        return titlePanel;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 51, 102));
        leftPanel.setPreferredSize(new Dimension(400, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel iconLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\User\\eclipse-workspace\\Library_Management_System\\Images\\white.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(resizedImage));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(iconLabel);

        JPanel inputPanel = createInputFieldsPanel();
        leftPanel.add(inputPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        addButton = createStyledButton("Add");
        addButton.addActionListener(e -> addLoan());
        buttonPanel.add(addButton);

        printButton = createStyledButton("Print");
        printButton.addActionListener(e -> printLoanInfo());
        buttonPanel.add(printButton);

        leftPanel.add(buttonPanel);

        return leftPanel;
    }

    private JPanel createInputFieldsPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        booknameField = new JTextField(15);
        inputPanel.add(createLabelAndTextField("Book Name:", booknameField));

        userNameField = new JTextField(15);
        inputPanel.add(createLabelAndTextField("User Name:", userNameField));

        loanDateField = new JTextField(LocalDate.now().toString());
        loanDateField.setEditable(false);
        inputPanel.add(createLabelAndTextField("Loan Date:", loanDateField));

        dueDateField = new JTextField(LocalDate.now().plusDays(14).toString());
        dueDateField.setEditable(false);
        inputPanel.add(createLabelAndTextField("Due Date:", dueDateField));

        return inputPanel;
    }

    private JPanel createLabelAndTextField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(label);
        textField.setPreferredSize(new Dimension(200, 25));
        panel.add(textField);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        loanTableModel = new DefaultTableModel(new String[]{"Book ID", "Book Title", "Author", "Year"}, 0);
        loanTable = new JTable(loanTableModel);
        loanTable.setRowHeight(30);
        setupTable(loanTable);

        returnTableModel = new DefaultTableModel(new String[]{"Loan ID", "Book Name", "User Name", "Loan Date", "Due Date"}, 0);
        returnTable = new JTable(returnTableModel);
        returnTable.setRowHeight(30);
        setupTable(returnTable);

        JScrollPane loanScrollPane = new JScrollPane(loanTable);
        JScrollPane returnScrollPane = new JScrollPane(returnTable);
        loanScrollPane.setPreferredSize(new Dimension(600, 225));
        returnScrollPane.setPreferredSize(new Dimension(600, 225));

        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BorderLayout());
        tablesPanel.add(loanScrollPane, BorderLayout.NORTH);
        tablesPanel.add(returnScrollPane, BorderLayout.SOUTH);

        tablePanel.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 3));
        tablePanel.add(tablesPanel, BorderLayout.CENTER);

        return tablePanel;
    }

    private void setupTable(JTable table) {
        table.getTableHeader().setBackground(new Color(0, 51, 102));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
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

    private void loadLoanData() {
        try {
            loanTableModel.setRowCount(0);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_book");

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("title"));
                row.add(rs.getString("author"));
                row.add(rs.getString("year"));

                loanTableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load loan data: " + e.getMessage());
        }
    }

    private void addLoan() {
        String bookname = booknameField.getText();
        String userName = userNameField.getText();
        String loanDate = loanDateField.getText();
        String dueDate = dueDateField.getText();

        if (!bookname.isEmpty() && !userName.isEmpty()) {
            try {
                String query = "INSERT INTO loan (book_name, username, loan_date, due_date) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, bookname);
                preparedStatement.setString(2, userName);
                preparedStatement.setString(3, loanDate);
                preparedStatement.setString(4, dueDate);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int loanId = generatedKeys.getInt(1);
                        
                        returnTableModel.setRowCount(0); 
                        Vector<Object> row = new Vector<>();
                        row.add(loanId);
                        row.add(bookname);
                        row.add(userName);
                        row.add(loanDate);
                        row.add(dueDate);
                        returnTableModel.addRow(row); 

                        JOptionPane.showMessageDialog(this, "Loan added successfully.");
                        booknameField.setText("");
                        userNameField.setText("");
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add loan.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to add loan: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    private void printLoanInfo() {
        Print printJob = new Print(returnTableModel);
        
            }
}
