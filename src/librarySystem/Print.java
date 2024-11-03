package librarySystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.*;

public class Print extends JFrame implements Printable {
    private DefaultTableModel tableModel;

    public Print(DefaultTableModel model) {
        this.tableModel = model;
        setTitle("Print Report");
        setSize(500, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Header Label
        JLabel headerLabel = new JLabel("Library Loan Report", SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 102, 204));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 3, 0, Color.DARK_GRAY),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        add(headerLabel, BorderLayout.NORTH);

        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(0, 153, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 3));
        add(scrollPane, BorderLayout.CENTER);

        JButton printButton = new JButton("Print Report");
        printButton.setFont(new Font("Arial", Font.BOLD, 14));
        printButton.setBackground(new Color(0, 204, 102));
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 153, 76), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        printButton.addActionListener(e -> printDocument());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void printDocument() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, "Printing failed: " + e.getMessage());
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
        // Title
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Library Loan Report", 150, 50); 

        g2d.drawLine(50, 55, 450, 55); 

        // Table header
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        int y = 70; 
        for (int col = 0; col < tableModel.getColumnCount(); col++) {
            g2d.drawString(tableModel.getColumnName(col), 50 + col * 100, y);
        }

        y += 20; 
        
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                String text = tableModel.getValueAt(row, col).toString();
                g2d.drawString(text, 50 + col * 100, y);
            }
            y += 20; 
        }

        return PAGE_EXISTS;
    }
}