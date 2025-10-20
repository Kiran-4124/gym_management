package project_UI;
import javax.swing.*;

public class MenuNavigation {

    public static JMenuBar createMenu(JFrame currentFrame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("MENU");

        JMenuItem add = new JMenuItem("Add");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem membership = new JMenuItem("Membership");
        JMenuItem payment = new JMenuItem("Payment");
        JMenuItem logout = new JMenuItem("Logout");

        // ✅ When clicked, open correct page and close current window
        add.addActionListener(e -> {
            currentFrame.dispose();
            new Admin1();
        });

        delete.addActionListener(e -> {
            currentFrame.dispose();
            new Admin2();
        });

        membership.addActionListener(e -> {
            currentFrame.dispose();
            new Admin3();
        });

        payment.addActionListener(e -> {
            currentFrame.dispose();
            new Admin4();
        });

        logout.addActionListener(e -> {
            currentFrame.dispose();
            new login();
        });

        menu.add(add);
        menu.add(delete);
        menu.add(membership);
        menu.add(payment);
        menu.addSeparator();
        menu.add(logout);

        menuBar.add(menu);
        return menuBar;
    }
}

