package gymfitnessp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TestDB1 extends JFrame {

    private JTextField idField, nameField, ageField, mobileField;
    private JPasswordField passwordField;
    private JComboBox<String> genderBox, timeBox; // <<< Removed menuBox
    private JButton updateButton;

    public TestDB1() {

        // === Window Settings ===
        setTitle("Trainer Dashboard - GTM Fitness");
        setSize(900, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(128, 0, 0)); // Maroon color

        // === NEW: JMenuBar ===
        JMenuBar menuBar = new JMenuBar();
        
        // This is the main menu title
        JMenu trainerMenu = new JMenu("TRAINER");
        menuBar.add(trainerMenu);

        // --- Create the menu items ---
        JMenuItem profileItem = new JMenuItem("Profile");
        JMenuItem dietPlansItem = new JMenuItem("Diet Plans");
        JMenuItem viewMembersItem = new JMenuItem("View Members");
        JMenuItem logoutItem = new JMenuItem("Logout");

        // --- Add items to the "TRAINER" menu ---
        trainerMenu.add(profileItem);
        trainerMenu.add(dietPlansItem);
        trainerMenu.add(viewMembersItem);
        trainerMenu.addSeparator(); // Adds a visual line
        trainerMenu.add(logoutItem);

        // --- Add Actions to Menu Items ---
        
        // Action for "Profile" (this screen)
        profileItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You are already on the Profile screen.");
        });

        // Action for "Diet Plans"
        dietPlansItem.addActionListener(e -> {
            // Make sure you have the DietPlanFrame.java file
            new Dietplan().setVisible(true); // Open DietPlans
            this.dispose(); // Close this window
        });
        
        // Action for "View Members"
        viewMembersItem.addActionListener(e -> {
            // Make sure you have the ViewMembersFrame.java file
            new List().setVisible(true); // Open ViewMembers
            this.dispose(); // Close this window
        });

        // Action for "Logout"
        logoutItem.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // --- Set the menu bar for this frame ---
        setJMenuBar(menuBar);

        // === The old headerPanel, icon, trainerLabel, and menuBox code is GONE ===

        // === Load and Set Background Image ===
        ImageIcon bgIcon = new ImageIcon("trainer_side.jpg"); // Make sure this image is in your project folder
        Image scaledImg = bgIcon.getImage().getScaledInstance(320, 500, Image.SCALE_SMOOTH);
        JLabel bgImage = new JLabel(new ImageIcon(scaledImg));
        bgImage.setBounds(580, 0, 320, 500);
        add(bgImage);

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Color textColor = Color.WHITE;

        // <<< Start Y-position higher up (30 instead of 80)
        int labelX = 80, fieldX = 250, y = 30, gap = 40; 

        // === ID ===
        addLabel("ID", labelX, y, labelFont, textColor);
        idField = addTextField(fieldX, y);

        // === NAME ===
        y += gap;
        addLabel("NAME", labelX, y, labelFont, textColor);
        nameField = addTextField(fieldX, y);

        // === AGE ===
        y += gap;
        addLabel("AGE", labelX, y, labelFont, textColor);
        ageField = addTextField(fieldX, y);

        // === MOBILE NUMBER ===
        y += gap;
        addLabel("MOBILE NUMBER", labelX, y, labelFont, textColor);
        mobileField = addTextField(fieldX, y);

        // === GENDER ===
        y += gap;
        addLabel("GENDER", labelX, y, labelFont, textColor);
        genderBox = new JComboBox<>(new String[]{"MALE", "FEMALE"});
        genderBox.setBounds(fieldX, y, 150, 25);
        add(genderBox);

        // === PASSWORD ===
        y += gap;
        addLabel("PASSWORD", labelX, y, labelFont, textColor);
        passwordField = new JPasswordField();
        passwordField.setBounds(fieldX, y, 150, 25);
        add(passwordField);

        // === TIME ===
        y += gap;
        addLabel("TIME", labelX, y, labelFont, textColor);
        timeBox = new JComboBox<>(new String[]{"FORENOON", "AFTERNOON", "EVENING"});
        timeBox.setBounds(fieldX, y, 150, 25);
        add(timeBox);

        // === Button ===
        updateButton = new JButton("UPDATE");
        updateButton.setBounds(430, 200, 120, 40); // <<< Adjusted Y position
        styleButton(updateButton);
        add(updateButton);

        // === Button Action ===
        updateButton.addActionListener(e -> updateTrainer());

        setVisible(true);
    }

    // === The old handleMenuSelection() method is GONE ===

    
    // Helper methods (addLabel, addTextField, styleButton) remain the same...
    
    private void addLabel(String text, int x, int y, Font f, Color c) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(f);
        label.setForeground(c);
        add(label);
    }

    private JTextField addTextField(int x, int y) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 25);
        add(tf);
        return tf;
    }
    
    private void styleButton(JButton btn) {
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
    }
    
    // updateTrainer method remains the same...

    private void updateTrainer() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String mobile = mobileField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String password = new String(passwordField.getPassword()).trim();
        String time = (String) timeBox.getSelectedItem();

        if (id.isEmpty() || name.isEmpty() || mobile.isEmpty() || ageStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }

        String sql = "UPDATE trainer SET name=?, age=?, mobile=?, gender=?, password=?, time=? WHERE id=?";
        
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_fit", "root", "");
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(ageStr));
            ps.setString(3, mobile);
            ps.setString(4, gender);
            ps.setString(5, password);
            ps.setString(6, time);
            ps.setString(7, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Trainer updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Trainer ID not found!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Age. Please enter a valid number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestDB1::new);
    }
}