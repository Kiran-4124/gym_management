package project_UI;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Admin1 extends JFrame {
    Connection conn;

    public Admin1() {
        // Frame setup
        setTitle("Admin-add page");
        setSize(1300, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // ======= Establish DB Connection =======
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

        // ===== MENU BAR =====
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");
        JMenuItem Add = new JMenuItem("Add");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem membership = new JMenuItem("Membership");
        JMenuItem payment = new JMenuItem("Payment Details");
        JMenuItem logout = new JMenuItem("Logout");

        menu.add(Add);
        menu.add(delete);
        menu.add(membership);
        menu.add(payment);
        menu.add(logout);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // ===== LEFT PANEL =====
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(155, 40, 60));
        leftPanel.setBounds(0, 0, 950, 700);
        add(leftPanel);

        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);
        Color textColor = Color.WHITE;

        // ===== INPUT FIELDS =====
        JLabel lblId = new JLabel("ID");
        lblId.setBounds(80, 80, 100, 25);
        lblId.setFont(labelFont);
        leftPanel.add(lblId);
        lblId.setForeground(textColor);

        JTextField txtId = new JTextField();
        txtId.setBounds(200, 80, 230, 25);
        leftPanel.add(txtId);

        JLabel lblName = new JLabel("NAME");
        lblName.setBounds(80, 120, 100, 25);
        lblName.setFont(labelFont);
        leftPanel.add(lblName);
        lblName.setForeground(textColor);

        JTextField txtName = new JTextField();
        txtName.setBounds(200, 120, 230, 25);
        leftPanel.add(txtName);

        // Trainer and Members radio
        JRadioButton trainer = new JRadioButton("TRAINER");
        trainer.setBounds(80, 170, 120, 25);
        trainer.setBackground(leftPanel.getBackground());
        trainer.setFont(fieldFont);
        leftPanel.add(trainer);

        JRadioButton member = new JRadioButton("MEMBERS");
        member.setBounds(220, 170, 130, 25);
        member.setBackground(leftPanel.getBackground());
        member.setFont(fieldFont);
        leftPanel.add(member);

        ButtonGroup groupTM = new ButtonGroup();
        groupTM.add(trainer);
        groupTM.add(member);

        JLabel lblAge = new JLabel("AGE");
        lblAge.setBounds(80, 220, 100, 25);
        lblAge.setFont(labelFont);
        leftPanel.add(lblAge);
        lblAge.setForeground(textColor);

        JTextField txtAge = new JTextField();
        txtAge.setBounds(200, 220, 80, 25);
        leftPanel.add(txtAge);

        JLabel lblMobile = new JLabel("MOBILE NUMBER");
        lblMobile.setBounds(80, 260, 160, 25);
        lblMobile.setFont(labelFont);
        leftPanel.add(lblMobile);
        lblMobile.setForeground(textColor);

        JTextField txtMobile = new JTextField();
        txtMobile.setBounds(260, 260, 230, 25);
        leftPanel.add(txtMobile);

        JLabel lblGender = new JLabel("GENDER");
        lblGender.setBounds(80, 300, 100, 25);
        lblGender.setFont(labelFont);
        leftPanel.add(lblGender);
        lblGender.setForeground(textColor);

        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderCombo = new JComboBox<>(genders);
        genderCombo.setBounds(200, 300, 120, 25);
        leftPanel.add(genderCombo);

        JLabel lblWeight = new JLabel("WEIGHT");
        lblWeight.setBounds(80, 340, 100, 25);
        lblWeight.setFont(labelFont);
        leftPanel.add(lblWeight);
        lblWeight.setForeground(textColor);

        JTextField txtWeight = new JTextField();
        txtWeight.setBounds(200, 340, 100, 25);
        leftPanel.add(txtWeight);

        JLabel lblHeight = new JLabel("HEIGHT");
        lblHeight.setBounds(80, 380, 100, 25);
        lblHeight.setFont(labelFont);
        leftPanel.add(lblHeight);
        lblHeight.setForeground(textColor);

        JTextField txtHeight = new JTextField();
        txtHeight.setBounds(200, 380, 100, 25);
        leftPanel.add(txtHeight);

        // ===== MEMBERSHIP SIDE =====
        JLabel lblMembership = new JLabel("MEMBERSHIP:");
        lblMembership.setBounds(550, 80, 200, 25);
        lblMembership.setFont(labelFont);
        leftPanel.add(lblMembership);
        lblMembership.setForeground(textColor);

        JRadioButton m1 = new JRadioButton("MONTHLY WITH TRAINER");
        m1.setBounds(550, 120, 250, 25);
        m1.setBackground(leftPanel.getBackground());
        leftPanel.add(m1);

        JRadioButton m2 = new JRadioButton("MONTHLY WITHOUT TRAINER");
        m2.setBounds(550, 150, 250, 25);
        m2.setBackground(leftPanel.getBackground());
        leftPanel.add(m2);

        JRadioButton m3 = new JRadioButton("ANNUALLY WITH TRAINER");
        m3.setBounds(550, 180, 250, 25);
        m3.setBackground(leftPanel.getBackground());
        leftPanel.add(m3);

        JRadioButton m4 = new JRadioButton("ANNUALLY WITHOUT TRAINER");
        m4.setBounds(550, 210, 270, 25);
        m4.setBackground(leftPanel.getBackground());
        leftPanel.add(m4);

        ButtonGroup groupMembership = new ButtonGroup();
        groupMembership.add(m1);
        groupMembership.add(m2);
        groupMembership.add(m3);
        groupMembership.add(m4);

        JLabel lblPassword = new JLabel("PASSWORD");
        lblPassword.setBounds(550, 260, 120, 25);
        lblPassword.setFont(labelFont);
        leftPanel.add(lblPassword);
        lblPassword.setForeground(textColor);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(670, 260, 250, 25);
        leftPanel.add(txtPassword);

        JLabel lblTime = new JLabel("TIME");
        lblTime.setBounds(550, 310, 100, 25);
        lblTime.setFont(labelFont);
        leftPanel.add(lblTime);
        lblTime.setForeground(textColor);

        String[] times = {"Forenoon", "Afternoon", "Evening"};
        JComboBox<String> timeCombo = new JComboBox<>(times);
        timeCombo.setBounds(670, 310, 150, 25);
        leftPanel.add(timeCombo);

        JButton btnCreate = new JButton("CREATE");
        btnCreate.setBounds(650, 380, 200, 40);
        btnCreate.setFont(new Font("SansSerif", Font.BOLD, 16));
        leftPanel.add(btnCreate);

        // ===== BUTTON LOGIC =====
        btnCreate.addActionListener(e -> {
            String id = txtId.getText();
            String name = txtName.getText();
            String age = txtAge.getText();
            String mobile = txtMobile.getText();
            String gender = (String) genderCombo.getSelectedItem();
            String weight = txtWeight.getText();
            String height = txtHeight.getText();

            String membershipValue = null;
            if (m1.isSelected()) {
                membershipValue = "MONTHLY WITH TRAINER";
            } else if (m2.isSelected()) {
                membershipValue = "MONTHLY WITHOUT TRAINER";
            } else if (m3.isSelected()) {
                membershipValue = "ANNUALLY WITH TRAINER";
            } else if (m4.isSelected()) {
                membershipValue = "ANNUALLY WITHOUT TRAINER";
            }
            String password = new String(txtPassword.getPassword());
            String time = (String) timeCombo.getSelectedItem();

            try {
                if(trainer.isSelected()) {
                    // Insert into trainer table
                    String queryTrainer = "INSERT INTO trainer (id, name, age, `mobile number`, gender, weight, height, membership, password, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstTrainer = conn.prepareStatement(queryTrainer);
                    pstTrainer.setString(1, id);
                    pstTrainer.setString(2, name);
                    pstTrainer.setString(3, age);
                    pstTrainer.setString(4, mobile);
                    pstTrainer.setString(5, gender);
                    pstTrainer.setString(6, weight);
                    pstTrainer.setString(7, height);
                    if (membershipValue == null) {
                        pstTrainer.setNull(8, java.sql.Types.VARCHAR);
                    } else {
                        pstTrainer.setString(8, membershipValue);
                    }
                    pstTrainer.setString(9, password);
                    pstTrainer.setString(10, time);
                    pstTrainer.executeUpdate();
                    pstTrainer.close();

                    // Insert into users table (with id, username, password, role)
                    String queryUser = "INSERT INTO users (id, username, password, role) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstUser = conn.prepareStatement(queryUser);
                    pstUser.setString(1, id);
                    pstUser.setString(2, name); // using name as username
                    pstUser.setString(3, password);
                    pstUser.setString(4, "trainer");
                    pstUser.executeUpdate();
                    pstUser.close();

                    JOptionPane.showMessageDialog(this, "Trainer added successfully!");

                } else if(member.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Please add member logic as needed.");
                } else {
                    JOptionPane.showMessageDialog(this, "Please select Trainer or Member.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // ===== RIGHT IMAGE PANEL =====
        JLabel rightImage = new JLabel();
        rightImage.setBounds(950, 0, 350, 700);
        rightImage.setIcon(new ImageIcon(getClass().getResource("/project_UI/images/admin-pic.jpg"))); // change path if needed
        add(rightImage);
        setJMenuBar(MenuNavigation.createMenu(this));
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Admin1());
    }
}
