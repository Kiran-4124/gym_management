package project_UI;

import javax.swing.*;
import java.awt.*;

public class login {

    login(){
       
        JFrame frame = new JFrame("GYM-FIT");
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
        title.setBounds(100, 30, 200, 30);
        mainPanel.add(title);

       
        JLabel loginLabel = new JLabel("LOGIN");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 18));
        loginLabel.setBounds(110, 70, 100, 30);
        mainPanel.add(loginLabel);

       
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(30, 120, 100, 25);
        mainPanel.add(idLabel);
        idLabel.setForeground(Color.WHITE);

        JTextField idField = new JTextField();
        idField.setBounds(100, 120, 150, 25);
        mainPanel.add(idField);

        
        JLabel passwordLabel = new JLabel("PASSWORD:");
        passwordLabel.setBounds(10, 160, 100, 25);
        mainPanel.add(passwordLabel);
        passwordLabel.setForeground(Color.WHITE);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 160, 150, 25);
        mainPanel.add(passwordField);

    
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBounds(100, 200, 100, 30); 
        mainPanel.add(submitButton);
        submitButton.setBackground(buttonColor);
        


        JLabel forgotLabel = new JLabel("FORGOT PASSWORD");
        forgotLabel.setBounds(10, 250, 150, 25);
        mainPanel.add(forgotLabel);
        forgotLabel.setForeground(Color.WHITE);

        JButton forgotButton = new JButton("CLICK HERE");
        forgotButton.setBounds(160, 250, 120, 25);
        mainPanel.add(forgotButton);
        forgotButton.setBackground(buttonColor);
        

        
        JLabel newMemberLabel = new JLabel("NEW MEMBER:");
        newMemberLabel.setBounds(20, 290, 100, 25);
        mainPanel.add(newMemberLabel);
        newMemberLabel.setForeground(Color.WHITE);

        JButton newMemberButton = new JButton("CLICK HERE");
        newMemberButton.setBounds(160, 290, 120, 25);
        mainPanel.add(newMemberButton);
        newMemberButton.setBackground(buttonColor);
       

       
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	/**AUTHOR:Rose Mary Joe
	        * Date:09.10.2025
	        */
    	new login();
    }
}

