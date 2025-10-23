package LoginDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.sql.*;
import java.util.UUID;

public class PaymentMember extends JFrame {

    private Connection conn;
    private int memberId;       // numeric ID from members table
    private String username;    // username from users table

    public PaymentMember(int memberId, String username) {
        super("Make Payment - " + username);
        this.memberId = memberId;
        this.username = username;

        // ===== Connect to Database =====
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_fit", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Connection Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // ===== UI Setup =====
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel header = new JLabel("Select Membership Plan to Pay");
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBounds(0, 10, 900, 40);
        add(header);

        String[] plans = {"MONTHLY - TRAINER", "MONTHLY - WITHOUT TRAINER", "ANNUALLY - TRAINER", "ANNUALLY - WITHOUT TRAINER"};
        int[] rates = {1499, 999, 14999, 11999};

        int x = 50, y = 80;
        for (int i = 0; i < plans.length; i++) {
            createPlanPanel(plans[i], rates[i], x, y);
            x += 400;
            if (x > 700) { x = 50; y += 180; }
        }

        setVisible(true);
    }

    private void createPlanPanel(String planName, int rate, int x, int y) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, 350, 150);
        panel.setBorder(BorderFactory.createTitledBorder(planName));
        add(panel);

        JLabel lblRate = new JLabel("Amount: ₹" + rate);
        lblRate.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblRate.setBounds(20, 30, 200, 25);
        panel.add(lblRate);

        JButton payNow = new JButton("Pay Now");
        payNow.setBounds(100, 70, 120, 30);
        panel.add(payNow);

        payNow.addActionListener(e -> {
            try {
                // Generate random transaction ID
                String transactionId = UUID.randomUUID().toString();

                // Insert into paymentmember table
                PreparedStatement insert = conn.prepareStatement(
                        "INSERT INTO paymentmember (`ID`, `NAME`, `TRANSACTION ID`, `MEMBERSHIP TYPE`) VALUES (?, ?, ?, ?)"
                );
                insert.setInt(1, memberId);          // numeric ID
                insert.setString(2, username);       // username
                insert.setString(3, transactionId);  // generated transaction ID
                insert.setString(4, planName);       // membership type
                insert.executeUpdate();

                // Success message
                JOptionPane.showMessageDialog(this,
                        "✅ Payment of ₹" + rate + " for " + planName + " successful!\nTransaction ID: " + transactionId,
                        "Payment Successful", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error processing payment: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> new PaymentMember(7, "cj7"));
    }
}
