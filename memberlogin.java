package LoginDemo;

import javax.swing.*;
import java.awt.*;

public class memberlogin extends JFrame {

    private JPanel contentPanel;
    private int memberId;      // numeric ID from DB
    private String username;   // username from users table

    public memberlogin(int memberId, String username) {
        super("Member Dashboard - " + username);
        this.memberId = memberId;
        this.username = username;
        initUI();
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initUI() {
        JMenuBar menuBar = new JMenuBar();

        JMenu profileMenu = new JMenu("Profile");
        JMenu membershipMenu = new JMenu("Membership");
        JMenu paymentMenu = new JMenu("Payment");
        JMenu dietMenu = new JMenu("Diet Plan");
        JMenu settingsMenu = new JMenu("Settings");

        JMenuItem viewProfile = new JMenuItem("View / Update Profile");
        JMenuItem viewMembership = new JMenuItem("View Membership Options");
        JMenuItem makePayment = new JMenuItem("Make Payment");
        JMenuItem generateReceipt = new JMenuItem("Generate Receipt");
        JMenuItem viewDiet = new JMenuItem("View Diet Plan");
        JMenuItem resetPassword = new JMenuItem("Reset Password");
        JMenuItem logout = new JMenuItem("Logout");

        profileMenu.add(viewProfile);
        membershipMenu.add(viewMembership);
        paymentMenu.add(makePayment);
        paymentMenu.add(generateReceipt);
        dietMenu.add(viewDiet);
        settingsMenu.add(resetPassword);
        settingsMenu.add(logout);

        menuBar.add(profileMenu);
        menuBar.add(membershipMenu);
        menuBar.add(paymentMenu);
        menuBar.add(dietMenu);
        menuBar.add(settingsMenu);
        setJMenuBar(menuBar);

        JLabel welcome = new JLabel("Welcome, " + username + "!");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new JLabel("Select an option from the menu above.", SwingConstants.CENTER), BorderLayout.CENTER);

        JLabel footer = new JLabel("© Gym Management System", SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(welcome, BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(footer, BorderLayout.SOUTH);

        // ===== Event Listeners =====
        viewDiet.addActionListener(e -> {
            new ViewDietPlans();
        });

        makePayment.addActionListener(e -> {
            new PaymentMember(memberId, username); // pass numeric ID and username
        });

        // Reset Password -> redirect to forgotPasswordPage
        resetPassword.addActionListener(e -> {
            dispose();  // close current memberlogin window
            new forgotPasswordPage();  // open forgotPasswordPage
        });

        logout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "You have logged out.");
            new LoginDemo();
            dispose();
        });
    }
}
