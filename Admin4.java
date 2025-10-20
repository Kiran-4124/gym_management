package project_UI;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class Admin4 extends JFrame {

    public Admin4() {
        setTitle("Payment View");
        setSize(1300, 750);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ✅ Menu Bar
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

        // ✅ Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(155, 35, 53));
        leftPanel.setBounds(0, 0, 900, 750);
        leftPanel.setLayout(null);
        add(leftPanel);

        // ✅ Table Title
        JLabel title = new JLabel("PAYMENT RECORDS");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBounds(300, 20, 400, 40);
        leftPanel.add(title);

        // ✅ Table Data & Model
        String[] columns = {"Name", "Mobile No.", "Membership", "View", "Alert"};
        Object[][] data = {
            {"John Doe", "9876543210", "Monthly - Trainer", "View", "Alert"},
            {"Riya Sharma", "9876501234", "Annual - No Trainer", "View", "Alert"},
            {"Aman Verma", "9876123450", "Monthly - No Trainer", "View", "Alert"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 3; // Only "View" and "Alert" are clickable
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);

        // ✅ Make "View" and "Alert" look like buttons
        TableColumn viewColumn = table.getColumnModel().getColumn(3);
        viewColumn.setCellRenderer(new ButtonRenderer());
        viewColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        TableColumn alertColumn = table.getColumnModel().getColumn(4);
        alertColumn.setCellRenderer(new ButtonRenderer());
        alertColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(80, 100, 740, 500);
        leftPanel.add(scrollPane);

        // ✅ Right Side Gym Image
        JLabel rightImage = new JLabel();
        rightImage.setBounds(900, 0, 950, 650);
        rightImage.setIcon(new ImageIcon(getClass().getResource("/project_UI/images/admin-pic.jpg")));// <-- replace with your local image path
        add(rightImage);
        setJMenuBar(MenuNavigation.createMenu(this));
        setVisible(true);
        
    }

    // ✅ Renderer for Button in Table
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

    // ✅ Editor for Button Click in Table
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean clicked;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
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
                if (label.equals("View")) {
                    JOptionPane.showMessageDialog(button, "Viewing Payment Details...");
                } else if (label.equals("Alert")) {
                    JOptionPane.showMessageDialog(button, "Sending Payment Alert...");
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

