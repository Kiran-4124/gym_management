package LoginDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginDemo {

    LoginDemo() {

        JFrame frame = new JFrame("Login Page");  
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        Color buttonColor = new Color(245, 232, 215);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(150, 0, 300, 500);
        mainPanel.setBackground(new Color(165, 36, 59));
        mainPanel.setLayout(null);
        frame.add(mainPanel);

        JLabel title = new JLabel("GYM-FIT");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBounds(110, 30, 200, 30);
        mainPanel.add(title);

        JLabel loginLabel = new JLabel("LOGIN");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setBounds(120, 70, 100, 30);
        mainPanel.add(loginLabel);

        JLabel userLabel = new JLabel("USERNAME:");
        userLabel.setBounds(10, 120, 100, 25);
        userLabel.setForeground(Color.WHITE);
        mainPanel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(100, 120, 150, 25);
        mainPanel.add(userField);

        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setBounds(10, 160, 100, 25);
        passwordLabel.setForeground(Color.WHITE);
        mainPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 160, 150, 25);
        mainPanel.add(passwordField);

        JLabel roleLabel = new JLabel("ROLE:");
        roleLabel.setBounds(10, 200, 100, 25);
        roleLabel.setForeground(Color.WHITE);
        mainPanel.add(roleLabel);

        String[] roles = {"admin", "member", "trainer"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBounds(100, 200, 150, 25);
        mainPanel.add(roleBox);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBounds(100, 240, 100, 30);
        submitButton.setBackground(buttonColor);
        mainPanel.add(submitButton);

        
        JLabel forgotLabel = new JLabel("FORGOT PASSWORD:");
        forgotLabel.setBounds(10, 290, 150, 25);
        forgotLabel.setForeground(Color.WHITE);
        mainPanel.add(forgotLabel);

        JButton forgotButton = new JButton("CLICK HERE");
        forgotButton.setBounds(160, 290, 120, 25);
        forgotButton.setBackground(buttonColor);
        mainPanel.add(forgotButton);

        
        JLabel newMemberLabel = new JLabel("NEW MEMBER:");
        newMemberLabel.setBounds(20, 330, 100, 25);
        newMemberLabel.setForeground(Color.WHITE);
        mainPanel.add(newMemberLabel);

        JButton newMemberButton = new JButton("CLICK HERE");
        newMemberButton.setBounds(160, 330, 120, 25);
        newMemberButton.setBackground(buttonColor);
        mainPanel.add(newMemberButton);

     
        submitButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passwordField.getPassword());
            String selectedRole = (String) roleBox.getSelectedItem();

            String url = "jdbc:mysql://localhost:3306/gym_fit";
            String dbUsername = "root";
            String dbPassword = "";

            try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
                 PreparedStatement pst = conn.prepareStatement(
                         "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?")) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, selectedRole);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame,
                            "✅ Login Successful as " + selectedRole.toUpperCase() + "!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "❌ Invalid Username, Password, or Role",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame,
                        "Database Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

       
        forgotButton.addActionListener(e -> {
            frame.dispose();
            new forgotPasswordPage();
        });

       
        newMemberButton.addActionListener(e -> {
            frame.dispose();
            new newMemberPage();
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginDemo();
    }
}
