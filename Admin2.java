package project_UI;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Admin2 extends JFrame {
    Connection conn;
    JTextField idField, nameField, ageField, mobileField, weightField, heightField, passwordField;
    JComboBox<String> genderCombo, timeCombo;
    JRadioButton trainerRB, memberRB;
    ButtonGroup groupTM, membershipGroup;
    JRadioButton[] membershipRadios;

    public Admin2() {
        // DB Connection
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gym_fit",
                "root",
                ""
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed!\n"+e.getMessage());
            System.exit(1);
        }

        setTitle("Admin Panel");
        setSize(1300, 750);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(155,35,53));
        leftPanel.setBounds(0, 0, 900, 750);
        leftPanel.setLayout(null);
        add(leftPanel);

        addLabel(leftPanel, "ID", 50, 30);
        idField = addTextField(leftPanel, 150, 30);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBounds(180, 80, 120, 30);
        leftPanel.add(submitButton);

        addLabel(leftPanel, "NAME", 50, 120);
        nameField = addTextField(leftPanel, 150, 120);

        trainerRB = createRadio(leftPanel, "TRAINER", 50, 170);
        memberRB = createRadio(leftPanel, "MEMBERS", 200, 170);
        groupTM = new ButtonGroup();
        groupTM.add(trainerRB); groupTM.add(memberRB);

        addLabel(leftPanel, "AGE", 50, 220);
        ageField = addTextField(leftPanel, 150, 220);

        addLabel(leftPanel, "MOBILE NUMBER", 50, 270);
        mobileField = addTextField(leftPanel, 250, 270);

        addLabel(leftPanel, "GENDER", 50, 320);
        genderCombo = new JComboBox<>(new String[]{"MALE", "FEMALE", "OTHER"});
        genderCombo.setBounds(150, 320, 200, 30);
        leftPanel.add(genderCombo);

        addLabel(leftPanel, "WEIGHT", 50, 370);
        weightField = addTextField(leftPanel, 150, 370);

        addLabel(leftPanel, "HEIGHT", 50, 420);
        heightField = addTextField(leftPanel, 150, 420);

        addSectionTitle(leftPanel, "MEMBERSHIP:", 500, 30);
        String[] membershipOptions = {
            "MONTHLY WITH TRAINER", "MONTHLY WITHOUT TRAINER",
            "ANNUALLY WITH TRAINER", "ANNUALLY WITHOUT TRAINER"
        };
        membershipGroup = new ButtonGroup();
        membershipRadios = new JRadioButton[membershipOptions.length];
        int yPos = 70;
        for(int i=0; i<membershipOptions.length; i++) {
            membershipRadios[i] = createRadio(leftPanel, membershipOptions[i], 500, yPos);
            membershipGroup.add(membershipRadios[i]);
            yPos += 40;
        }

        addLabel(leftPanel, "PASSWORD", 500, 220);
        passwordField = new JTextField();
        passwordField.setBounds(650, 220, 200, 25);
        leftPanel.add(passwordField);

        addLabel(leftPanel, "TIME", 500, 270);
        timeCombo = new JComboBox<>(new String[]{"FORENOON", "AFTERNOON", "EVENING"});
        timeCombo.setBounds(650, 270, 200, 30);
        leftPanel.add(timeCombo);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(550, 350, 300, 40);
        leftPanel.add(deleteButton);

        JLabel rightImage = new JLabel();
        rightImage.setBounds(900, 0, 950, 650);
        rightImage.setIcon(new ImageIcon(getClass().getResource("/project_UI/images/admin-pic.jpg"))); // Change path if needed
        add(rightImage);

        // ==== LOGIC ====

        // Submit: Try trainer table first, then member table
        submitButton.addActionListener(e -> {
            String id = idField.getText().trim();
            if(id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an ID!");
                return;
            }
            // Try trainer first
            boolean found = false;
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM trainer WHERE id = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    nameField.setText(rs.getString("name"));
                    ageField.setText(rs.getString("age"));
                    mobileField.setText(rs.getString("mobile number"));
                    genderCombo.setSelectedItem(rs.getString("gender").toUpperCase());
                    weightField.setText(rs.getString("weight"));
                    heightField.setText(rs.getString("height"));
                    passwordField.setText(rs.getString("password"));
                    timeCombo.setSelectedItem(rs.getString("time").toUpperCase());
                    trainerRB.setSelected(true);
                    memberRB.setSelected(false);
                    // Membership radio: clear selection and disable
                    membershipGroup.clearSelection();
                    setMembershipRadiosEnabled(false);
                    found = true;
                }
                rs.close(); ps.close();

                if(!found) {
                    // Try members table
                    ps = conn.prepareStatement("SELECT * FROM members WHERE id = ?");
                    ps.setString(1, id);
                    rs = ps.executeQuery();
                    if(rs.next()) {
                        nameField.setText(rs.getString("name"));
                        ageField.setText(rs.getString("age"));
                        mobileField.setText(rs.getString("mobile"));
                        genderCombo.setSelectedItem(rs.getString("gender").toUpperCase());
                        weightField.setText(rs.getString("weight"));
                        heightField.setText(rs.getString("height"));
                        passwordField.setText(rs.getString("password"));
                        timeCombo.setSelectedItem(rs.getString("time").toUpperCase());
                        memberRB.setSelected(true);
                        trainerRB.setSelected(false);
                        // Membership radio: set selected and enabled
                        String membership = rs.getString("membership");
                        setMembershipRadiosEnabled(true);
                        membershipGroup.clearSelection();
                        for(JRadioButton rb : membershipRadios) {
                            if(rb.getText().equalsIgnoreCase(membership)) {
                                rb.setSelected(true);
                                break;
                            }
                        }
                        found = true;
                    }
                    rs.close(); ps.close();
                }
                if(!found) JOptionPane.showMessageDialog(this, "No trainer/member found with this ID!");
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
            }
        });

        // DELETE: deletes from users table AND from trainer/members table, whichever exists
        deleteButton.addActionListener(e -> {
            String id = idField.getText().trim();
            if(id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter ID to delete!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                "Really delete this user everywhere?",
                "Confirm", JOptionPane.YES_NO_OPTION);
            if(confirm != JOptionPane.YES_OPTION) return;

            try {
                // Delete from trainer table
                PreparedStatement ps = conn.prepareStatement("DELETE FROM trainer WHERE id = ?");
                ps.setString(1, id);
                int trows = ps.executeUpdate();
                ps.close();

                // Delete from members table
                ps = conn.prepareStatement("DELETE FROM members WHERE id = ?");
                ps.setString(1, id);
                int mrows = ps.executeUpdate();
                ps.close();

                // Delete from users table
                ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
                ps.setString(1, id);
                int urows = ps.executeUpdate();
                ps.close();

                if(trows > 0 || mrows > 0 || urows > 0) {
                    JOptionPane.showMessageDialog(this, "Deleted Successfully!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "ID not found!");
                }
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage());
            }
        });
        setJMenuBar(MenuNavigation.createMenu(this));
        setVisible(true);
    }

    private void setMembershipRadiosEnabled(boolean enable) {
        for(JRadioButton rb : membershipRadios) {
            rb.setEnabled(enable);
        }
    }

    private void clearFields() {
        idField.setText(""); nameField.setText(""); ageField.setText(""); mobileField.setText("");
        weightField.setText(""); heightField.setText(""); passwordField.setText("");
        genderCombo.setSelectedIndex(0); timeCombo.setSelectedIndex(0);
        groupTM.clearSelection(); membershipGroup.clearSelection();
        setMembershipRadiosEnabled(true);
    }
    private void addLabel(JPanel p, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 200, 25);
        p.add(label);
    }
    private JTextField addTextField(JPanel p, int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 230, 25);
        p.add(field);
        return field;
    }
    private void addSectionTitle(JPanel p, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 300, 30);
        p.add(label);
    }
    private JRadioButton createRadio(JPanel p, String text, int x, int y) {
        JRadioButton rb = new JRadioButton(text);
        rb.setBounds(x, y, 300, 30);
        rb.setBackground(new Color(155,35,53));
        rb.setForeground(Color.WHITE);
        p.add(rb);
        return rb;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Admin2());
    }
}
