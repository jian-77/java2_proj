package view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.ApplicationController;
import entity.Application;

public class ApplicationAuthorizationView {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table; // Declare table as a class-level variable

    public ApplicationAuthorizationView() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("页面 1 - 物资审核");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // Sidebar configuration
        String[] options = {"物资审核", "物资种类管理","物资数量管理", "个人界面"};
        JList<String> sidebar = new JList<>(options);
        sidebar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sidebar.setSelectedIndex(0);  // Default selection for "物资审核"
        sidebar.setBackground(new Color(255, 182, 193)); // Pink color
        sidebar.setForeground(Color.BLACK);
        sidebar.setFont(new Font("宋体", Font.BOLD, 16));
        sidebar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                switch (sidebar.getSelectedValue()) {
                    case "物资数量管理":
                        frame.dispose();
                        new ItemQuantityManagementView();
                        break;
                    case "个人界面":
                        // Handle personal interface logic here
                        break;
                }
            }
        });
        frame.add(new JScrollPane(sidebar), BorderLayout.WEST);

        // Center panel configuration
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        frame.add(centerPanel, BorderLayout.CENTER);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");
        searchPanel.add(new JLabel("搜索申请人:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // Table model setup
        String[] columnNames = {"申请人姓名", "申请时间", "操作"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setRowHeight(30);
        addInitialData(); // Method to add data to the model

        // Adding buttons to the table
        table.getColumn("操作").setCellRenderer(new ButtonRenderer());
        table.getColumn("操作").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Search button action listener
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            filterTable(searchText);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Method to add initial data to the table
    private void addInitialData() {
        Application[]applications=ApplicationController.queryA();
        for (int i = 0; i <10; i++) {
            if (applications[i] != null) {
                model.addRow(new Object[]{applications[i].getA_name(), "2022-05-20", "审核"});
            }
        }
    }

    // Method to filter table based on search text
    private void filterTable(String searchText) {
        DefaultTableModel filteredModel = new DefaultTableModel(new Object[]{"申请人姓名", "申请时间", "操作"}, 0);
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().contains(searchText)) {
                filteredModel.addRow(new Object[]{
                        model.getValueAt(i, 0),
                        model.getValueAt(i, 1),
                        "审核"
                });
            }
        }
        table.setModel(filteredModel);
        table.getColumn("操作").setCellRenderer(new ButtonRenderer());
        table.getColumn("操作").setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    // TableCellRenderer for buttons in the table
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(Color.WHITE);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // TableCellEditor for buttons in the table
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                showDetailsDialog(table, label);
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }

        private void showDetailsDialog(JTable table, String label) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                // Assuming additional details like item name, need for return, item type, quantity
                Object[] details = {"Item Name", true, "Item Type", 2};  // Example values
                String applicantName = table.getValueAt(selectedRow, 0).toString();
                String applicationTime = table.getValueAt(selectedRow, 1).toString();

                JDialog dialog = new JDialog(frame, "审核详情", true);
                dialog.setLayout(new BorderLayout());

                // Table for details
                DefaultTableModel detailsModel = new DefaultTableModel();
                detailsModel.addColumn("属性");
                detailsModel.addColumn("值");
                detailsModel.addRow(new Object[]{"申请人姓名", applicantName});
                detailsModel.addRow(new Object[]{"申请时间", applicationTime});
                detailsModel.addRow(new Object[]{"申请物品名称", details[0]});
                detailsModel.addRow(new Object[]{"是否需要归还", details[1]});
                detailsModel.addRow(new Object[]{"物品种类", details[2]});
                detailsModel.addRow(new Object[]{"申请用量", details[3]});
                JTable detailsTable = new JTable(detailsModel);
                dialog.add(new JScrollPane(detailsTable), BorderLayout.CENTER);

                // Buttons panel
                JPanel buttonsPanel = new JPanel();
                JButton approveButton = new JButton("批准");
                JButton denyButton = new JButton("不批准");
                buttonsPanel.add(approveButton);
                buttonsPanel.add(denyButton);
                dialog.add(buttonsPanel, BorderLayout.SOUTH);

                // Button actions
                approveButton.addActionListener(ae -> {
                    model.removeRow(selectedRow);
                    dialog.dispose();
                });
                denyButton.addActionListener(ae -> {
                    model.removeRow(selectedRow);
                    dialog.dispose();
                });

                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        }
    }
}
