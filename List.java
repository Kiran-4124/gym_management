package gymfitnessp;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class List extends JFrame {

    public List() {
        setTitle("Members List");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Column Names
        String[] columns = {"ID", "NAME", "MOBILE", "MEMBERSHIP", "SESSION", "VIEW"};

        // Sample Data (ID in column 0)
        Object[][] data = {
            {1, "kiran", "1234376428", "Annually with Trainer", "EVENING", "VIEW"},
            {2, "abc", "787878787", "Monthly with Trainer", "FORENOON", "VIEW"}
        };

        // Table Model
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only VIEW button column editable
            }
        };

        JTable table = new JTable(model);

        // Hide the ID column
        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setPreferredWidth(0);

        // Add button to the VIEW column
        table.getColumn("VIEW").setCellRenderer(new ButtonRenderer());
        table.getColumn("VIEW").setCellEditor(new ButtonEditor(new JCheckBox(), table));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }

    // Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Button Editor
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;
        private JTable table;
        private int row;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;

            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int memberId = Integer.parseInt(table.getValueAt(row, 0).toString());
                String memberName = table.getValueAt(row, 1).toString();

                // Open your real ProfilePage (replace this with your actual class)
                new ProfilePage(memberId, memberName);
            }
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }

    
    class ProfilePage extends JFrame {
        public ProfilePage(int memberId, String memberName) {
            setTitle("Profile Page - " + memberName);
            setSize(300, 200);
            setLocationRelativeTo(null);

            JLabel label = new JLabel("Member ID: " + memberId + ", Name: " + memberName, SwingConstants.CENTER);
            add(label);

            setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(List::new);
    }
}
