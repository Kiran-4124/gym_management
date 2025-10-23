package LoginDemo;



import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.sql.*;



public class ProfilePage extends JFrame {



    private int memberId;

    private String username;



    // DB connection details

    private static final String DB_URL = "jdbc:mysql://localhost:3306/gym_fit";

    private static final String DB_USER = "root";        // change if needed

    private static final String DB_PASS = "";            // change if needed



    // Fields

    private JTextField nameField, ageField, mobileField, weightField, heightField;

    private JComboBox<String> genderBox, membershipBox, timeBox;

    private JPasswordField passwordField;

    private JLabel statusLabel; // label below buttons



    public ProfilePage(int memberId, String username) {

        super("Profile - " + username);

        this.memberId = memberId;

        this.username = username;



        setSize(500, 550);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        initUI();

        loadMemberData(); // Load details from DB

        setVisible(true);

    }



    private void initUI() {

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.fill = GridBagConstraints.HORIZONTAL;



        JLabel title = new JLabel("Member Profile", SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 22));



        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;

        panel.add(title, gbc);



        gbc.gridwidth = 1;

        gbc.gridy++;



        // === Form Fields ===

        addLabelAndField(panel, gbc, "Name:", nameField = new JTextField(15));

        addLabelAndField(panel, gbc, "Age:", ageField = new JTextField(15));

        addLabelAndField(panel, gbc, "Mobile:", mobileField = new JTextField(15));



        gbc.gridx = 0; gbc.gridy++;

        panel.add(new JLabel("Gender:"), gbc);

        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        gbc.gridx = 1;

        panel.add(genderBox, gbc);



        addLabelAndField(panel, gbc, "Weight (kg):", weightField = new JTextField(15));

        addLabelAndField(panel, gbc, "Height (cm):", heightField = new JTextField(15));



        gbc.gridx = 0; gbc.gridy++;

        panel.add(new JLabel("Membership:"), gbc);

        membershipBox = new JComboBox<>(new String[]{

                "Monthly with Trainer",

                "Monthly without Trainer",

                "Annually with Trainer",

                "Annually without Trainer"

        });

        gbc.gridx = 1;

        panel.add(membershipBox, gbc);



        gbc.gridx = 0; gbc.gridy++;

        panel.add(new JLabel("Preferred Time:"), gbc);

        timeBox = new JComboBox<>(new String[]{"Morning", "Forenoon", "Afternoon", "Evening"});

        gbc.gridx = 1;

        panel.add(timeBox, gbc);



        addLabelAndField(panel, gbc, "Password:", passwordField = new JPasswordField(15));



        // === Buttons ===

        gbc.gridx = 0; gbc.gridy++;

        gbc.gridwidth = 2;

        gbc.anchor = GridBagConstraints.CENTER;



        JButton saveBtn = new JButton("Save Changes");

        JButton cancelBtn = new JButton("Cancel");



        JPanel buttonPanel = new JPanel();

        buttonPanel.add(saveBtn);

        buttonPanel.add(cancelBtn);

        panel.add(buttonPanel, gbc);



        // ✅ Label Below Buttons (with default text)

        gbc.gridy++;

        statusLabel = new JLabel("Message", SwingConstants.CENTER);

        statusLabel.setFont(new Font("Arial", Font.ITALIC, 13));

        statusLabel.setForeground(new Color(70, 70, 70));

        panel.add(statusLabel, gbc);



        // === Event Listeners ===

        saveBtn.addActionListener(e -> updateMemberData());

        cancelBtn.addActionListener(e -> dispose());



        add(panel);

    }



    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field) {

        gbc.gridx = 0; gbc.gridy++;

        gbc.anchor = GridBagConstraints.LINE_END;

        panel.add(new JLabel(labelText), gbc);



        gbc.gridx = 1;

        gbc.anchor = GridBagConstraints.LINE_START;

        panel.add(field, gbc);

    }



    // ===== LOAD DATA FROM DB =====

    private void loadMemberData() {

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String sql = "SELECT * FROM members WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, memberId);



            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                nameField.setText(rs.getString("name"));

                ageField.setText(String.valueOf(rs.getInt("age")));

                mobileField.setText(rs.getString("mobile"));

                genderBox.setSelectedItem(capitalize(rs.getString("gender")));

                weightField.setText(String.valueOf(rs.getFloat("weight")));

                heightField.setText(String.valueOf(rs.getFloat("height")));

                membershipBox.setSelectedItem(rs.getString("membership"));

                passwordField.setText(rs.getString("password"));

                timeBox.setSelectedItem(capitalize(rs.getString("time")));

            }

        } catch (SQLException e) {

            statusLabel.setText("Error loading profile: " + e.getMessage());

            statusLabel.setForeground(Color.RED);

        }

    }



    // ===== UPDATE DATA IN DB =====

    private void updateMemberData() {

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String sql = "UPDATE members SET name=?, age=?, mobile=?, gender=?, weight=?, height=?, membership=?, password=?, time=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);



            ps.setString(1, nameField.getText().trim());

            ps.setInt(2, Integer.parseInt(ageField.getText().trim()));

            ps.setString(3, mobileField.getText().trim());

            ps.setString(4, genderBox.getSelectedItem().toString());

            ps.setFloat(5, Float.parseFloat(weightField.getText().trim()));

            ps.setFloat(6, Float.parseFloat(heightField.getText().trim()));

            ps.setString(7, membershipBox.getSelectedItem().toString());

            ps.setString(8, new String(passwordField.getPassword()));

            ps.setString(9, timeBox.getSelectedItem().toString());

            ps.setInt(10, memberId);



            int rows = ps.executeUpdate();

            if (rows > 0) {

                statusLabel.setText("✔ Profile updated successfully!");

                statusLabel.setForeground(new Color(0, 128, 0)); // green text

            } else {

                statusLabel.setText("No record updated. Check your data.");

                statusLabel.setForeground(Color.RED);

            }



        } catch (SQLException e) {

            statusLabel.setText("Error updating profile: " + e.getMessage());

            statusLabel.setForeground(Color.RED);

        } catch (NumberFormatException nfe) {

            statusLabel.setText("Please enter valid numeric values for age, weight, and height.");

            statusLabel.setForeground(Color.RED);

        }

    }



    private String capitalize(String s) {

        if (s == null || s.isEmpty()) return "";

        s = s.toLowerCase();

        return Character.toUpperCase(s.charAt(0)) + s.substring(1);

    }

}
