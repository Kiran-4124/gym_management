package LoginDemo;



import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.sql.*;



public class MembershipPage extends JFrame {



    private int memberId;

    private String username;



    // DB details

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gym_fit";

    private static final String DB_USER = "root";     // change if needed

    private static final String DB_PASS = "";         // change if needed



    // Components

    private JLabel currentMembershipLabel;

    private JComboBox<String> membershipBox;



    public MembershipPage(int memberId, String username) {

        super("Membership - " + username);

        this.memberId = memberId;

        this.username = username;



        setSize(500, 350);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        initUI();

        loadCurrentMembership();

        setVisible(true);

    }



    private void initUI() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.fill = GridBagConstraints.HORIZONTAL;



        JLabel title = new JLabel("Membership Options", SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 22));



        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;

        panel.add(title, gbc);



        // Current membership label

        gbc.gridwidth = 1;

        gbc.gridy++;

        gbc.gridx = 0;

        panel.add(new JLabel("Current Membership:"), gbc);



        currentMembershipLabel = new JLabel("Loading...");

        gbc.gridx = 1;

        panel.add(currentMembershipLabel, gbc);



        // Dropdown for new membership

        gbc.gridy++;

        gbc.gridx = 0;

        panel.add(new JLabel("Select New Plan:"), gbc);



        membershipBox = new JComboBox<>(new String[]{

                "Monthly with Trainer",

                "Monthly without Trainer",

                "Annually with Trainer",

                "Annually without Trainer"

        });

        gbc.gridx = 1;

        panel.add(membershipBox, gbc);



        // Buttons

        gbc.gridy++;

        gbc.gridx = 0; gbc.gridwidth = 2;

        gbc.anchor = GridBagConstraints.CENTER;



        JButton updateBtn = new JButton("Update Membership");

        JButton cancelBtn = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(updateBtn);

        buttonPanel.add(cancelBtn);



        panel.add(buttonPanel, gbc);



        // ===== Event Listeners =====

        updateBtn.addActionListener(e -> updateMembership());

        cancelBtn.addActionListener(e -> dispose());



        add(panel);

    }



    // ===== Load current membership from DB =====

    private void loadCurrentMembership() {

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String sql = "SELECT membership FROM members WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, memberId);



            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String currentPlan = rs.getString("membership");

                currentMembershipLabel.setText(currentPlan);

                membershipBox.setSelectedItem(currentPlan);

            } else {

                currentMembershipLabel.setText("No record found");

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(this, "Error loading membership: " + e.getMessage());

        }

    }



    // ===== Update membership in DB =====

    private void updateMembership() {

        String newPlan = membershipBox.getSelectedItem().toString();



        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String sql = "UPDATE members SET membership = ? WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, newPlan);

            ps.setInt(2, memberId);



            int rows = ps.executeUpdate();

            if (rows > 0) {

                currentMembershipLabel.setText(newPlan);

                JOptionPane.showMessageDialog(this, "Membership updated successfully!");

            } else {

                JOptionPane.showMessageDialog(this, "Failed to update membership.");

            }



        } catch (SQLException e) {

            JOptionPane.showMessageDialog(this, "Error updating membership: " + e.getMessage());

        }

    }

}
