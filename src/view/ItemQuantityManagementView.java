package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemQuantityManagementView {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table;

    public ItemQuantityManagementView() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("物资管理");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));


        // Sidebar configuration
        String[] options = {"物资审核", "物资种类管理", "物资数量管理", "个人界面"};
        JList<String> sidebar = new JList<>(options);
        sidebar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sidebar.setSelectedIndex(2);  // Default selection for "物资管理"
        sidebar.setBackground(new Color(255, 182, 193)); // Pink color
        sidebar.setForeground(Color.BLACK);
        sidebar.setFont(new Font("宋体", Font.BOLD, 16));
        sidebar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (sidebar.getSelectedValue().equals("物资审核")) {
                    frame.dispose();
                    new ApplicationAuthorizationView();  // Assuming Page1 is also updated to remember the last state
                }
                if (sidebar.getSelectedValue().equals("物资种类管理")) {
                    frame.dispose();
                    new ItemTypeManagementView();  // Assuming Page1 is also updated to remember the last state
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
        searchPanel.add(new JLabel("搜索物资名称:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        // Table model setup
        String[] columnNames = {"物资名称", "物资余量", "操作"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setRowHeight(30);
        addInitialData(); // Method to add data to the model

        // Adding buttons to the table
        table.getColumn("操作").setCellRenderer(new ItemQuantityManagementView.ButtonRenderer());
        table.getColumn("操作").setCellEditor(new ItemQuantityManagementView.ButtonEditor(new JCheckBox()));
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

    private void addInitialData() {
        model.addRow(new Object[]{"电脑", 3, "编辑"});
        model.addRow(new Object[]{"氯化钠", 50, "编辑"});
        model.addRow(new Object[]{"烧杯", 7, "编辑"});

    }

    private void filterTable(String searchText) {
        DefaultTableModel filteredModel = new DefaultTableModel(new Object[]{"物资名称", "物资余量", "操作"}, 0);
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().contains(searchText)) {
                filteredModel.addRow(new Object[]{
                        model.getValueAt(i, 0),
                        model.getValueAt(i, 1),
                        "编辑"
                });
            }
        }
        table.setModel(filteredModel);
        table.getColumn("操作").setCellRenderer(new ItemQuantityManagementView.ButtonRenderer());
        table.getColumn("操作").setCellEditor(new ItemQuantityManagementView.ButtonEditor(new JCheckBox()));
    }

    static public class TextFieldEditor extends AbstractCellEditor implements TableCellEditor {
        private JTextField editor = new JTextField();

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            editor.setText((value != null) ? value.toString() : "");
            return editor;
        }

        @Override
        public Object getCellEditorValue() {
            return editor.getText();
        }
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
//                Object[] details = {"Item Name", true, "Item Type", 2};  // Example values
                String goodsName = table.getValueAt(selectedRow, 0).toString();
                int goodsQuantity=Integer.parseInt(table.getValueAt(selectedRow, 1).toString());
                String goodsType = "电子产品";

                String goodsReturnable="false";

                JDialog dialog = new JDialog(frame, "物资详情", true);
                dialog.setLayout(new BorderLayout());
                ItemQuantityManagementView.TextFieldEditor textFieldEditor = new ItemQuantityManagementView.TextFieldEditor();

                // Table for details
                DefaultTableModel detailsModel = new DefaultTableModel();
                detailsModel.addColumn("物资信息");
                detailsModel.addColumn("值");
                detailsModel.addRow(new Object[]{"物资名称", goodsName});
                detailsModel.addRow(new Object[]{"物资种类", goodsType});
                detailsModel.addRow(new Object[]{"物资余量", goodsQuantity});
                detailsModel.addRow(new Object[]{"是否需要归还", goodsReturnable});
//                detailsModel.addRow(new Object[]{"物品种类", details[2]});
                detailsModel.addRow(new Object[]{"用量编辑"});
                table.getColumnModel().getColumn(1).setCellEditor(textFieldEditor);
                JTable detailsTable = new JTable(detailsModel);
                dialog.add(new JScrollPane(detailsTable), BorderLayout.CENTER);

                // Buttons panel
                JPanel buttonsPanel = new JPanel();
                JButton approveButton = new JButton("确认编辑");
//                JButton denyButton = new JButton("不批准");
                buttonsPanel.add(approveButton);
//                buttonsPanel.add(denyButton);
                dialog.add(buttonsPanel, BorderLayout.SOUTH);

                // Button actions
                approveButton.addActionListener(ae -> {

                            if (detailsTable.getCellEditor() != null) {
                                detailsTable.getCellEditor().stopCellEditing();  // 结束编辑并获取值

                                String cellValue = (String) detailsTable.getValueAt(4, 1);  // 获取当前单元格的值
                                int cellValue1=Integer.valueOf(cellValue);

                                System.out.println("Edited Value: " + cellValue1);           // 打印或使用值
                            }
                            dialog.dispose();
                        }
                );
//                denyButton.addActionListener(ae -> {
//                    model.removeRow(selectedRow);
//                    dialog.dispose();
//                });

                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        }
    }
}