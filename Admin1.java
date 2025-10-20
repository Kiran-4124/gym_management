package project_UI;

import javax.swing.*;
import java.awt.*;

public class Admin1 extends JFrame {

    public Admin1() {
        // Frame setup
        setTitle("Amin-add page");
        setSize(1300, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

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

        // ===== RIGHT IMAGE PANEL =====
        JLabel rightImage = new JLabel();
        rightImage.setBounds(950, 0, 350, 700);
        rightImage.setIcon(new ImageIcon(getClass().getResource("/project_UI/images/admin-pic.jpg")));// <-- replace with your local image path
        add(rightImage);
        setJMenuBar(MenuNavigation.createMenu(this));
        setVisible(true);
        

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Admin1());
        
    }
}

