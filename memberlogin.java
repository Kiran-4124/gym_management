package LoginDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class memberlogin extends JFrame {

    private JPanel contentPanel; // dynamic area to update

    public memberlogin(String memberId) {
        super("Member Dashboard - " + memberId);
        initUI(memberId);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initUI(String memberId) {
        // ====== Menu Bar ======
        JMenuBar menuBar = new JMenuBar();

        JMenu profileMenu = new JMenu("Profile");
        JMenu membershipMenu = new JMenu("Membership");
        JMenu paymentMenu = new JMenu("Payment");
        JMenu dietMenu = new JMenu("Diet Plan");
        JMenu settingsMenu = new JMenu("Settings");

        // Menu items
        JMenuItem viewProfile = new JMenuItem("View / Update Profile");
        JMenuItem viewMembership = new JMenuItem("View Membership Options");
        JMenuItem makePayment = new JMenuItem("Make Payment");
        JMenuItem generateReceipt = new JMenuItem("Generate Receipt");
        JMenuItem viewDiet = new JMenuItem("View Diet Plan");
        JMenuItem ResetPassword = new JMenuItem("Reset Password");
        JMenuItem logout = new JMenuItem("Logout");

        // Add items to menus
        profileMenu.add(viewProfile);
        membershipMenu.add(viewMembership);
        paymentMenu.add(makePayment);
        paymentMenu.add(generateReceipt);
        dietMenu.add(viewDiet);
        settingsMenu.add(ResetPassword);
        settingsMenu.add(logout);

        // Add menus to menu bar
        menuBar.add(profileMenu);
        menuBar.add(membershipMenu);
        menuBar.add(paymentMenu);
        menuBar.add(dietMenu);
        menuBar.add(settingsMenu);

        setJMenuBar(menuBar);

        // ====== Header ======
        JLabel welcome = new JLabel("Welcome, " + memberId + "!");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // ====== Content Panel ======
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new JLabel("Select an option from the menu above.", SwingConstants.CENTER), BorderLayout.CENTER);

        // ====== Footer ======
        JLabel footer = new JLabel("© Gym Management System", SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // ====== Main Layout ======
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(welcome, BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(footer, BorderLayout.SOUTH);

        // ====== Event Listeners ======
        viewProfile.addActionListener(e -> updateContent("Profile", "Profile Information:\nName: John Doe\nAge: 25\nMembership: Premium"));
        viewMembership.addActionListener(e -> updateContent("Membership Options", "Available Plans:\n- Basic (₹999/month)\n- Premium (₹1499/month)\n- Gold (₹1999/month)"));
        makePayment.addActionListener(e -> updateContent("Payment", "Payment Page:\nEnter card details or use UPI to continue."));
        generateReceipt.addActionListener(e -> updateContent("Receipt", "Receipt generated successfully!\nTransaction ID: GYM12345"));
        viewDiet.addActionListener(e -> updateContent("Diet Plan", "Your Diet Plan:\nMorning: Oats + Eggs\nLunch: Grilled Chicken\nEvening: Salad"));

        // ✅ Forgot password opens a new page
        ResetPassword.addActionListener(e -> {
        	
        new forgotPasswordPage();
        dispose();
        }); 

        logout.addActionListener(e -> {
        	JOptionPane.showMessageDialog(this, "You have logged out.");
            new LoginDemo();
            dispose();
        });
    }

    private void updateContent(String title, String message) {
        contentPanel.removeAll();

        JTextArea area = new JTextArea(message);
        area.setEditable(false);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createTitledBorder(title));
        area.setBackground(new Color(250, 250, 250));

        contentPanel.add(new JScrollPane(area), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new memberlogin("Member001"));
    }
}
