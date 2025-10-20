package project_UI;
import javax.swing.*;
import java.awt.*;

public class Admin2 extends JFrame {

    public Admin2() {
        setTitle("Admin Panel");
        setSize(1300, 750);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ✅ Menu Bar (Top Left)
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");
        JMenuItem add = new JMenuItem("Add");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem membership = new JMenuItem("Membership");
        JMenuItem payment = new JMenuItem("Payment");
        JMenuItem logout = new JMenuItem("Logout");

        menu.add(add);
        menu.add(delete);
        menu.add(membership);
        menu.add(payment);
        menu.addSeparator();
        menu.add(logout);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // ✅ Main Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(155, 35, 53));
        leftPanel.setBounds(0, 0, 900, 750);
        leftPanel.setLayout(null);
        add(leftPanel);

        // Form Fields
        addLabel(leftPanel, "ID", 50, 30);
        JTextField idField = addTextField(leftPanel, 150, 30);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBounds(180, 80, 120, 30);
        leftPanel.add(submitButton);

        addLabel(leftPanel, "NAME", 50, 120);
        JTextField nameField = addTextField(leftPanel, 150, 120);

        // Trainer/Members Radio
        JRadioButton trainerRB = createRadio(leftPanel, "TRAINER", 50, 170);
        JRadioButton memberRB = createRadio(leftPanel, "MEMBERS", 200, 170);
        ButtonGroup groupTM = new ButtonGroup();
        groupTM.add(trainerRB);
        groupTM.add(memberRB);

        addLabel(leftPanel, "AGE", 50, 220);
        JTextField ageField = addTextField(leftPanel, 150, 220);

        addLabel(leftPanel, "MOBILE NUMBER", 50, 270);
        JTextField mobileField = addTextField(leftPanel, 250, 270);

        addLabel(leftPanel, "GENDER", 50, 320);
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"MALE", "FEMALE", "OTHER"});
        genderCombo.setBounds(150, 320, 200, 30);
        leftPanel.add(genderCombo);

        addLabel(leftPanel, "WEIGHT", 50, 370);
        JTextField weightField = addTextField(leftPanel, 150, 370);

        addLabel(leftPanel, "HEIGHT", 50, 420);
        JTextField heightField = addTextField(leftPanel, 150, 420);

        // Membership Section
        addSectionTitle(leftPanel, "MEMBERSHIP:", 500, 30);
        String[] membershipOptions = {
            "MONTHLY WITH TRAINER", "MONTHLY WITHOUT TRAINER",
            "ANNUALLY WITH TRAINER", "ANNUALLY WITHOUT TRAINER"
        };
        int yPos = 70;
        ButtonGroup membershipGroup = new ButtonGroup();
        for (String m : membershipOptions) {
            JRadioButton rb = createRadio(leftPanel, m, 500, yPos);
            membershipGroup.add(rb);
            yPos += 40;
        }

        addLabel(leftPanel, "PASSWORD", 500, 220);
        JTextField passwordField = new JTextField();
        passwordField.setBounds(650, 220, 200, 25);
        leftPanel.add(passwordField);

        addLabel(leftPanel, "TIME", 500, 270);
        JComboBox<String> timeCombo = new JComboBox<>(new String[]{"FORENOON", "AFTERNOON", "EVENING"});
        timeCombo.setBounds(650, 270, 200, 30);
        leftPanel.add(timeCombo);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setBounds(550, 350, 300, 40);
        leftPanel.add(deleteButton);
        
        // ✅ Right Image Panel
        JLabel rightImage = new JLabel();
        rightImage.setBounds( 900, 0, 950, 650);
        rightImage.setIcon(new ImageIcon(getClass().getResource("/project_UI/images/admin-pic.jpg")));// <-- replace with your local image path
        add(rightImage);
        setJMenuBar(MenuNavigation.createMenu(this));
        setVisible(true);
        
    }

    // Utility Methods
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
        rb.setBackground(new Color(155, 35, 53));
        rb.setForeground(Color.WHITE);
        p.add(rb);
        return rb;
        

    }
   


    public static void main(String[] args) {
        new Admin2();
    }
}
