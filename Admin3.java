package project_UI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Admin3 extends JFrame {

    Connection conn; // ✅ Global database connection

    public Admin3() {
        setTitle("Membership Plans");
        setSize(1300, 750);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ✅ 1. Establish database connection
        try {
            String userName = "root";
            String password = ""; // Your MySQL password (if any)
            String url = "jdbc:mysql://localhost:3306/gym_fit";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("✅ Database connection established");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Database Connection Failed: " + e.getMessage());
        }

        // ✅ 2. Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");
        menu.add(new JMenuItem("Add"));
        menu.add(new JMenuItem("Delete"));
        menu.add(new JMenuItem("Membership"));
        menu.add(new JMenuItem("Payment"));
        menu.addSeparator();
        menu.add(new JMenuItem("Logout"));
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // ✅ 3. Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(155, 35, 53));
        leftPanel.setBounds(0, 0, 900, 750);
        leftPanel.setLayout(null);
        add(leftPanel);

        // ✅ 4. Create 4 Membership Panels
        createPlanPanel(leftPanel, "MONTHLY - TRAINER", 60, 80);
        createPlanPanel(leftPanel, "MONTHLY - WITHOUT TRAINER", 480, 80);
        createPlanPanel(leftPanel, "ANNUALLY - TRAINER", 60, 380);
        createPlanPanel(leftPanel, "ANNUALLY - WITHOUT TRAINER", 480, 380);

        // ✅ 5. Right Image
        JLabel rightImage = new JLabel();
        rightImage.setBounds(900, 0, 950, 650);
        rightImage.setIcon(new ImageIcon(getClass().getResource("/project_UI/images/admin-pic.jpg")));
        add(rightImage);

        // ✅ 6. Show Frame
        setVisible(true);
    }

    // ✅ Map UI type name to database type name
    private String mapTypeToDB(String type) {
        switch (type) {
            case "MONTHLY - TRAINER": return "Monthly with trainers";
            case "MONTHLY - WITHOUT TRAINER": return "Monthly without trainers";
            case "ANNUALLY - TRAINER": return "Annually with trainers";
            case "ANNUALLY - WITHOUT TRAINER": return "Annually without trainers";
            default: return type;
        }
    }

    // ✅ Function to create each Membership Panel
    private void createPlanPanel(JPanel parent, String type, int x, int y) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(x, y, 350, 250);
        parent.add(panel);

        JLabel titleLabel = new JLabel(type);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLabel.setBounds(20, 10, 300, 30);
        panel.add(titleLabel);

        JLabel daysLabel = new JLabel("DAYS:");
        daysLabel.setBounds(20, 60, 80, 25);
        panel.add(daysLabel);

        JTextField daysField = new JTextField();
        daysField.setBounds(120, 60, 180, 25);
        panel.add(daysField);

        JLabel amountLabel = new JLabel("AMOUNT:");
        amountLabel.setBounds(20, 110, 100, 25);
        panel.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(120, 110, 180, 25);
        panel.add(amountField);

        // ✅ Load data from database
        try {
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT days, payment FROM membership WHERE type = ?");
            pst.setString(1, mapTypeToDB(type));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                daysField.setText(rs.getString("days"));
                amountField.setText(rs.getString("payment"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error loading data for " + type + ": " + e.getMessage());
        }

        // ✅ Update Button
        JButton updateButton = new JButton("UPDATE");
        updateButton.setBounds(110, 170, 120, 30);
        updateButton.setBackground(new Color(220, 150, 150));
        panel.add(updateButton);

        updateButton.addActionListener(e -> {
            try {
                String dbType = mapTypeToDB(type);
                PreparedStatement update = conn.prepareStatement(
                        "UPDATE membership SET days = ?, payment = ? WHERE type = ?");
                update.setInt(1, Integer.parseInt(daysField.getText()));
                update.setInt(2, Integer.parseInt(amountField.getText()));
                update.setString(3, dbType);

                int rows = update.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "✅ Updated Successfully for " + type);
                } else {
                    JOptionPane.showMessageDialog(null, "⚠️ No record found for " + type + " in database!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "❌ Error Updating: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        new Admin3();
    }
}
