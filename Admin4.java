package project_UI;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class Admin4 extends JFrame {
    Connection conn;
    DefaultTableModel model;

    public Admin4() {
        // Database connection
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/gym_fit", // DB url
                "root", // DB username
                ""      // DB password
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DB error: "+e.getMessage());
            System.exit(1);
        }

        setTitle("Payment View");
        setSize(1300, 750);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menu Bar (unchanged)
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

        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(155, 35, 53));
        leftPanel.setBounds(0, 0, 900, 750);
        leftPanel.setLayout(null);
        add(leftPanel);

        // Table Title
        JLabel title = new JLabel("PAYMENT RECORDS");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(300, 20, 400, 40);
        leftPanel.add(title);

        // Fetch data from DB and build table
        String[] columns = {"ID", "Name", "Mobile No.", "Membership", "View", "Alert"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4; // Only "View" and "Alert" are clickable
            }
        };

        // Fetch paymentmember, and for each, fetch corresponding member
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM paymentmember");
            while (rs.next()) {
                String memberId = rs.getString("ID");
                String name = rs.getString("NAME");
                String membershipType = rs.getString("MEMBERSHIP TYPE");
                String mobile = "", username = name;
                try {
                    PreparedStatement pstM = conn.prepareStatement("SELECT mobile, name FROM members WHERE id=?");
                    pstM.setString(1, memberId);
                    ResultSet rsM = pstM.executeQuery();
                    if(rsM.next()) {
                        mobile = rsM.getString("mobile");
                        username = rsM.getString("name");
                    }
                    rsM.close();
                    pstM.close();
                } catch(Exception e){ mobile = ""; }
                model.addRow(new Object[]{memberId, username, mobile, membershipType, "View", "Alert"});
            }
            rs.close();
            stmt.close();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "DB/read error: " + ex.getMessage());
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);

        TableColumn viewColumn = table.getColumnModel().getColumn(4);
        viewColumn.setCellRenderer(new ButtonRenderer());
        viewColumn.setCellEditor(new ButtonEditor(new JCheckBox(), conn, table));

        TableColumn alertColumn = table.getColumnModel().getColumn(5);
        alertColumn.setCellRenderer(new ButtonRenderer());
        alertColumn.setCellEditor(new ButtonEditor(new JCheckBox(), conn, table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(80, 100, 740, 500);
        leftPanel.add(scrollPane);

        // Right Side Gym Image
        JLabel rightImage = new JLabel();
        rightImage.setBounds(900, 0, 950, 650);
        rightImage.setIcon(new ImageIcon(getClass().getResource("/project_UI/images/admin-pic.jpg"))); // <-- replace local image path if needed
        add(rightImage);
        setJMenuBar(MenuNavigation.createMenu(this));
        setVisible(true);
    }

    // Renderer for Button in Table
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor for Button Click in Table
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean clicked;
        private Connection conn;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, Connection conn, JTable table) {
            super(checkBox);
            this.conn = conn;
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                int row, int col) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (clicked) {
                int row = table.getSelectedRow();
                int memberId = Integer.parseInt(table.getValueAt(row, 0).toString());
                String username = table.getValueAt(row, 1).toString();
                if (label.equals("View")) {
                    new ProfilePage(memberId, username);
                } else if (label.equals("Alert")) {
                    // Set the message for this member in DB
                    try {
                        PreparedStatement ps = conn.prepareStatement(
                            "UPDATE members SET message=? WHERE id=?");
                        ps.setString(1, "membership expiring soon");
                        ps.setInt(2, memberId);
                        ps.executeUpdate();
                        ps.close();
                        JOptionPane.showMessageDialog(button, "Alert set for " + username);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(button, "Error updating alert: "+e.getMessage());
                    }
                }
            }
            clicked = false;
            return label;
        }
    }

    public static void main(String[] args) {
        new Admin4();
    }
}
