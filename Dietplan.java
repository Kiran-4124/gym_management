package gymfitnessp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Make sure this class name 'Dietplan' matches your file name 'Dietplan.java'
public class Dietplan extends JFrame {

    // <<< REMOVED menuBox
    
    // Text areas for each of the 4 panels
    private JTextArea weightGainArea;
    private JTextArea weightLossArea;
    private JTextArea muscleGainArea;
    private JTextArea weightMaintArea;

    public Dietplan() {

        // === Window Settings ===
        setTitle("Diet Plans - GTM Fitness");
        setSize(900, 700); // <<< Made the window a bit shorter
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(128, 0, 0)); // Maroon color

        // === NEW: JMenuBar ===
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU"); // The main menu title
        menuBar.add(menu);

        // --- Create the menu items ---
        JMenuItem profileItem = new JMenuItem("Profile");
        JMenuItem dietPlansItem = new JMenuItem("Diet Plans");
        JMenuItem viewMembersItem = new JMenuItem("View Members");
        JMenuItem logoutItem = new JMenuItem("Logout");

        // --- Add items to the menu ---
        menu.add(profileItem);
        menu.add(dietPlansItem);
        menu.add(viewMembersItem);
        menu.addSeparator(); // Adds a visual line
        menu.add(logoutItem);

        // --- Add Actions to Menu Items ---
        
        // Action for "Profile"
        profileItem.addActionListener(e -> {
            new TestDB().setVisible(true);
            this.dispose();
        });

        // Action for "Diet Plans" (this screen)
        dietPlansItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You are already on the Diet Plans screen.");
        });
        
        // Action for "View Members"
        viewMembersItem.addActionListener(e -> {
            new List().setVisible(true);
            this.dispose();
        });

        // Action for "Logout"
        logoutItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // --- Set the menu bar for this frame ---
        setJMenuBar(menuBar);

        // === The old headerPanel, icon, trainerLabel, and menuBox code is GONE ===
        
        // === Load Background Image ===
        ImageIcon bgIcon = new ImageIcon("trainer_side.jpg");
        Image scaledImg = bgIcon.getImage().getScaledInstance(320, 500, Image.SCALE_SMOOTH);
        JLabel bgImage = new JLabel(new ImageIcon(scaledImg));
        bgImage.setBounds(580, 40, 320, 500); // <<< Adjusted Y position
        add(bgImage);

        // === Create the 4 Diet Panels ===
        weightGainArea = new JTextArea();
        weightLossArea = new JTextArea();
        muscleGainArea = new JTextArea();
        weightMaintArea = new JTextArea(); 

        // <<< Adjusted Y positions to move panels up
        createDietPanel("FOR WEIGHT GAIN", 50, 40, weightGainArea);
        createDietPanel("FOR WEIGHT LOSS", 320, 40, weightLossArea);
        createDietPanel("Muscle gain", 50, 340, muscleGainArea);
        createDietPanel("Weight maintence", 320, 340, weightMaintArea);

        loadDietPlans();
        setVisible(true);
    }
    
    // createDietPanel, loadDietPlans, and other methods are the same...

    private void createDietPanel(String title, int x, int y, JTextArea textArea) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(x, y, 250, 280);
        add(panel);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setBounds(15, 10, 220, 25);
        panel.add(titleLabel);

        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(15, 45, 220, 180);
        panel.add(scrollPane);

        JButton updateButton = new JButton("UPDATE");
        updateButton.setBounds(80, 240, 90, 25);
        panel.add(updateButton);
        updateButton.addActionListener(e -> updateDietPlan(title, textArea.getText()));
    }

    private void loadDietPlans() {
        weightGainArea.setText(getPlanDetails("FOR WEIGHT GAIN"));
        weightLossArea.setText(getPlanDetails("FOR WEIGHT LOSS"));
        muscleGainArea.setText(getPlanDetails("Muscle gain"));
        weightMaintArea.setText(getPlanDetails("Weight maintence"));
    }

    private String getPlanDetails(String planName) {
        String sql = "SELECT plan_details FROM diet_plans WHERE plan_name = ?";
        String details = "";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_fit", "root", "");
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, planName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                details = rs.getString("plan_details");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
        return details;
    }

    private void updateDietPlan(String planName, String planDetails) {
        String sql = "UPDATE diet_plans SET plan_details = ? WHERE plan_name = ?";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_fit", "root", "");
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, planDetails);
            ps.setString(2, planName);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "'" + planName + "' plan updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Plan '" + planName + "' not found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // <<< The old handleMenuSelection() method is GONE

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dietplan::new);
    }
}