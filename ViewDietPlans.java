package LoginDemo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewDietPlans extends JFrame {

    private JPanel contentPanel;

    public ViewDietPlans() {
        setTitle("Available Diet Plans");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 245, 245)); // light gray background

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Load diet plans from DB
        loadDietPlans();

        setVisible(true);
    }

    private void loadDietPlans() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // ✅ Your DB connection info
            String url = "jdbc:mysql://localhost:3306/gym_fit";
            String user = "root";
            String pass = ""; // your MySQL password

            con = DriverManager.getConnection(url, user, pass);
            st = con.createStatement();
            rs = st.executeQuery("SELECT plan_name, plan_details FROM diet_plans");

            boolean found = false;

            while (rs.next()) {
                found = true;
                String name = rs.getString("plan_name");
                String details = rs.getString("plan_details");

                // Each diet plan panel
                JPanel planPanel = new JPanel();
                planPanel.setLayout(new BorderLayout(10, 10));
                planPanel.setBackground(Color.WHITE);
                planPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        BorderFactory.createLineBorder(new Color(200, 200, 200))
                ));

                JLabel planNameLabel = new JLabel(name);
                planNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
                planNameLabel.setForeground(new Color(34, 45, 65));

                JTextArea detailsArea = new JTextArea(details);
                detailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                detailsArea.setLineWrap(true);
                detailsArea.setWrapStyleWord(true);
                detailsArea.setEditable(false);
                detailsArea.setBackground(Color.WHITE);
                detailsArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                planPanel.add(planNameLabel, BorderLayout.NORTH);
                planPanel.add(detailsArea, BorderLayout.CENTER);

                contentPanel.add(planPanel);
                contentPanel.add(Box.createVerticalStrut(10)); // spacing between plans
            }

            if (!found) {
                JLabel noData = new JLabel("No diet plans available.", SwingConstants.CENTER);
                noData.setFont(new Font("Segoe UI", Font.ITALIC, 16));
                contentPanel.add(noData);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error fetching diet plans:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException ignored) {}
        }
    }
}
