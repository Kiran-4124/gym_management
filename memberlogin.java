package LoginDemo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class newMemberPage extends JFrame {

    private JTextField idField, nameField, ageField, mobileField, weightField, heightField;
    private JPasswordField passwordField;
    private JComboBox<String> genderBox, timeBox;
    private JRadioButton mWithTrainer, mWithoutTrainer, aWithTrainer, aWithoutTrainer;
    private JButton createButton;
    private ButtonGroup membershipGroup;

    public newMemberPage() {

        setTitle("New Member Registration");
        setSize(900, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(146, 34, 62));

        JLabel bgImage = new JLabel(new ImageIcon("gym_fit.jpg"));
        bgImage.setBounds(580, 0, 320, 500);
        add(bgImage);

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Color textColor = Color.WHITE;

        int labelX = 40, fieldX = 180, y = 40, gap = 40;

        // Optional ID field
        addLabel("ID (optional):", labelX, y, labelFont, textColor);
        idField = addTextField(fieldX, y);

        y += gap;
        addLabel("NAME:", labelX, y, labelFont, textColor);
        nameField = addTextField(fieldX, y);

        y += gap;
        addLabel("AGE:", labelX, y, labelFont, textColor);
        ageField = addTextField(fieldX, y);

        y += gap;
        addLabel("MOBILE:", labelX, y, labelFont, textColor);
        mobileField = addTextField(fieldX, y);

        y += gap;
        addLabel("GENDER:", labelX, y, labelFont, textColor);
        genderBox = new JComboBox<>(new String[]{"MALE", "FEMALE", "OTHER"});
        genderBox.setBounds(fieldX, y, 150, 25);
        add(genderBox);

        y += gap;
        addLabel("WEIGHT (kg):", labelX, y, labelFont, textColor);
        weightField = addTextField(fieldX, y);

        y += gap;
        addLabel("HEIGHT (cm):", labelX, y, labelFont, textColor);
        heightField = addTextField(fieldX, y);

        y += gap;
        addLabel("PASSWORD:", labelX, y, labelFont, textColor);
        passwordField = new JPasswordField();
        passwordField.setBounds(fieldX, y, 150, 25);
        add(passwordField);

        JLabel membershipLabel = new JLabel("MEMBERSHIP TYPE:");
        membershipLabel.setFont(labelFont);
        membershipLabel.setForeground(textColor);
        membershipLabel.setBounds(370, 50, 200, 25);
        add(membershipLabel);

        mWithTrainer = new JRadioButton("MONTHLY WITH TRAINER");
        mWithoutTrainer = new JRadioButton("MONTHLY WITHOUT TRAINER");
        aWithTrainer = new JRadioButton("ANNUALLY WITH TRAINER");
        aWithoutTrainer = new JRadioButton("ANNUALLY WITHOUT TRAINER");

        membershipGroup = new ButtonGroup();
        membershipGroup.add(mWithTrainer);
        membershipGroup.add(mWithoutTrainer);
        membershipGroup.add(aWithTrainer);
        membershipGroup.add(aWithoutTrainer);

        int memY = 80;
        for (JRadioButton btn : new JRadioButton[]{mWithTrainer, mWithoutTrainer, aWithTrainer, aWithoutTrainer}) {
            btn.setBounds(370, memY, 250, 25);
            btn.setForeground(textColor);
            btn.setBackground(new Color(146, 34, 62));
            add(btn);
            memY += 30;
        }

        JLabel timeLabel = new JLabel("TIME SLOT:");
        timeLabel.setFont(labelFont);
        timeLabel.setForeground(textColor);
        timeLabel.setBounds(370, 230, 150, 25);
        add(timeLabel);

        timeBox = new JComboBox<>(new String[]{"FORENOON", "AFTERNOON", "EVENING"});
        timeBox.setBounds(470, 230, 150, 25);
        add(timeBox);

        createButton = new JButton("CREATE");
        createButton.setBounds(400, 300, 160, 40);
        createButton.setBackground(Color.WHITE);
        createButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        createButton.setFocusPainted(false);
        add(createButton);

        createButton.addActionListener(e -> insertData());

        setVisible(true);
    }

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

    private void insertData() {
        String idText = idField.getText().trim();
        String name = nameField.getText().trim();
        String age = ageField.getText().trim();
        String mobile = mobileField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String weight = weightField.getText().trim();
        String height = heightField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String time = (String) timeBox.getSelectedItem();

        String membership = "";
        if (mWithTrainer.isSelected()) membership = "Monthly with Trainer";
        else if (mWithoutTrainer.isSelected()) membership = "Monthly without Trainer";
        else if (aWithTrainer.isSelected()) membership = "Annually with Trainer";
        else if (aWithoutTrainer.isSelected()) membership = "Annually without Trainer";

        if (name.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_fit", "root", "");

            Integer memberId = null;

            // Check custom ID if entered
            if (!idText.isEmpty()) {
                try {
                    int customId = Integer.parseInt(idText);

                    PreparedStatement checkStmt = con.prepareStatement("SELECT id FROM members WHERE id = ?");
                    checkStmt.setInt(1, customId);
                    ResultSet checkRs = checkStmt.executeQuery();
                    if (checkRs.next()) {
                        JOptionPane.showMessageDialog(this, "ID already exists! Please use another ID.");
                        return;
                    } else {
                        memberId = customId;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID! Please enter a number.");
                    return;
                }
            }

            // Insert into members table
            PreparedStatement ps;
            if (memberId == null) {
                String memberQuery = "INSERT INTO members (name, age, mobile, gender, weight, height, membership, password, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(memberQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, age);
                ps.setString(3, mobile);
                ps.setString(4, gender);
                ps.setString(5, weight);
                ps.setString(6, height);
                ps.setString(7, membership);
                ps.setString(8, password);
                ps.setString(9, time);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) memberId = rs.getInt(1);
            } else {
                String memberQuery = "INSERT INTO members (id, name, age, mobile, gender, weight, height, membership, password, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(memberQuery);
                ps.setInt(1, memberId);
                ps.setString(2, name);
                ps.setString(3, age);
                ps.setString(4, mobile);
                ps.setString(5, gender);
                ps.setString(6, weight);
                ps.setString(7, height);
                ps.setString(8, membership);
                ps.setString(9, password);
                ps.setString(10, time);
                ps.executeUpdate();
            }

            // Username: full name + ID
            String username = name.replaceAll("\\s+", "") + memberId;

            // Insert into users table
            String userQuery = "INSERT INTO users (id, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(userQuery);
            ps2.setInt(1, memberId);
            ps2.setString(2, username);
            ps2.setString(3, password);
            ps2.setString(4, "member");
            ps2.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Member Registered Successfully!\n" +
                            "Member ID: " + memberId + "\n" +
                            "Username: " + username);

            con.close();
            dispose();
            new LoginDemo(); // redirect to login page

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new newMemberPage();
    }
}
