//package view;
//
//import controller.ItemController;
//import entity.Item;
//
//import javax.swing.*;
//import javax.swing.table.AbstractTableModel;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.table.TableCellEditor;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class ItemTypeManagementView {
//    private JFrame frame;
//    private DefaultTableModel model;
//    private JTable table;
//
//    private Item[] items;
//
//    public ItemTypeManagementView() {
//        initializeUI();
//    }
//
//    private void initializeUI() {
//        frame = new JFrame("物资管理");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.setLayout(new BorderLayout(10, 10));
//
//        // Sidebar configuration
//        String[] options = {"物资审核", "物资种类管理", "物资数量管理", "个人界面"};
//        JList<String> sidebar = new JList<>(options);
//        sidebar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        sidebar.setSelectedIndex(1);  // Default selection for "物资管理"
//        sidebar.setBackground(new Color(255, 182, 193)); // Pink color
//        sidebar.setForeground(Color.BLACK);
//        sidebar.setFont(new Font("宋体", Font.BOLD, 16));
//        sidebar.addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                if (sidebar.getSelectedValue().equals("物资审核")) {
//                    frame.dispose();
//                    new ApplicationAuthorizationView();  // Assuming Page1 is also updated to remember the last state
//                }
//                if (sidebar.getSelectedValue().equals("物资数量管理")) {
//                    frame.dispose();
//                    new ItemQuantityManagementView();  // Assuming Page1 is also updated to remember the last state
//                }
//                if (sidebar.getSelectedValue().equals("个人界面")) {
//                    frame.dispose();
//                    new Manager_UI();  // Assuming Page1 is also updated to remember the last state
//                }
//            }
//        });
//        frame.add(new JScrollPane(sidebar), BorderLayout.WEST);
//
//        // Center panel configuration
//        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
//        frame.add(centerPanel, BorderLayout.CENTER);
//
//        // Search panel
//        JPanel searchPanel = new JPanel(new FlowLayout());
//        JTextField searchField = new JTextField(20);
//        JButton searchButton = new JButton("搜索");
//        JButton addButton = new JButton("增加");
//        searchPanel.add(new JLabel("搜索物资名称:"));
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);
//        searchPanel.add(addButton);
//
//        // Search button action listener
//        searchButton.addActionListener(e -> {
//            String searchText = searchField.getText().trim();
//            filterTable(searchText);
//        });
//
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // 创建 JDialog
//                JDialog dialog = new JDialog(frame, "Add Dialog", true);
//                dialog.setLayout(new BorderLayout());
//
//                // 创建 JTable 和模型
//                JTable table = new JTable(new AbstractTableModel() {
//                    String[] columnNames = {"属性", "值"};
//                    Object[][] data = {
//                            {"名称", ""},
//                            {"种类", ""},
//                            {"归还性", ""}
//                    };
//
//                    @Override
//                    public int getRowCount() {
//                        return data.length;
//                    }
//
//                    @Override
//                    public int getColumnCount() {
//                        return columnNames.length;
//                    }
//
//                    @Override
//                    public Object getValueAt(int rowIndex, int columnIndex) {
//                        return data[rowIndex][columnIndex];
//                    }
//
//                    @Override
//                    public boolean isCellEditable(int rowIndex, int columnIndex) {
//                        return columnIndex == 1; // 使第二列可编辑
//                    }
//
//                    @Override
//                    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//                        data[rowIndex][columnIndex] = aValue;
//                    }
//
//                    @Override
//                    public String getColumnName(int column) {
//                        return columnNames[column];
//                    }
//                });
//
//                // 将表格添加到对话框中
//                dialog.add(new JScrollPane(table), BorderLayout.CENTER);
//
//                // 创建保存按钮并添加动作监听器
//                JButton saveButton = new JButton("添加");
//
//                saveButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (table.getCellEditor() != null) {
//                            table.getCellEditor().stopCellEditing();
//                        }
//                        // 遍历每一行，获取第二列的值
//                        for (int i = 0; i < table.getRowCount(); i++) {
//                            System.out.println("Value at row " + i + ": " + table.getValueAt(i, 1));
//                        }
//                        dialog.dispose();
//                    }
//                });
//
//                // 将按钮添加到对话框底部
//                dialog.add(saveButton, BorderLayout.SOUTH);
//
//                dialog.pack();
//                dialog.setLocationRelativeTo(frame);
//                dialog.setVisible(true);
//            }
//        });
//
//        frame.add(centerPanel, BorderLayout.CENTER);
//
//        // Table model setup
//        centerPanel.add(searchPanel, BorderLayout.NORTH);
//        String[] columnNames = {"物资名称", "物资种类", "归还性", "操作1", "操作2"};
//        model = new DefaultTableModel(null, columnNames);
//        table = new JTable(model);
//        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
//        table.setRowHeight(30);
//        addInitialData(); // Method to add data to the model
//
//        // Adding buttons to the table
//        table.getColumn("操作1").setCellRenderer(new ButtonRenderer());
//        table.getColumn("操作1").setCellEditor(new ButtonEditor(new JCheckBox()));
//        table.getColumn("操作2").setCellRenderer(new ButtonRenderer());
//        table.getColumn("操作2").setCellEditor(new ButtonEditor(new JCheckBox()));
//        JScrollPane scrollPane = new JScrollPane(table);
//        centerPanel.add(scrollPane, BorderLayout.CENTER);
//
//        frame.setLocationRelativeTo(null);  // Center the frame
//        frame.setVisible(true);
//    }
//
//    private void addInitialData() {
//        items = ItemController.queryI();
//        for (int i = 0; i < 1000; i++) {
//            if (items[i] != null) {
//                model.addRow(new Object[]{items[i].getId(), items[i].getName(), items[i].getType(), "删除", "编辑"});
//            }
//        }
//    }
//
//    private void filterTable(String searchText) {
//        DefaultTableModel filteredModel = new DefaultTableModel(new Object[]{"物资编码", "物资名称", "物资种类", "归还性", "操作1", "操作2"}, 0);
//        for (int i = 0; i < model.getRowCount(); i++) {
//            if (model.getValueAt(i, 0).toString().contains(searchText)) {
//                filteredModel.addRow(new Object[]{
//                        model.getValueAt(i, 0),
//                        model.getValueAt(i, 1),
//                        model.getValueAt(i, 2),
//                        model.getValueAt(i, 3),
//                        "删除",
//                        "编辑"
//                });
//            }
//        }
//        table.setModel(filteredModel);
//        table.getColumn("操作1").setCellRenderer(new ButtonRenderer());
//        table.getColumn("操作1").setCellEditor(new ButtonEditor(new JCheckBox()));
//        table.getColumn("操作2").setCellRenderer(new ButtonRenderer());
//        table.getColumn("操作2").setCellEditor(new ButtonEditor(new JCheckBox()));
//    }
//
//    static public class TextFieldEditor extends AbstractCellEditor implements TableCellEditor {
//        private JTextField editor = new JTextField();
//
//        @Override
//        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//            editor.setText((value != null) ? value.toString() : "");
//            return editor;
//        }
//
//        @Override
//        public Object getCellEditorValue() {
//            return editor.getText();
//        }
//    }
//
//    // TableCellRenderer for buttons in the table
//    class ButtonRenderer extends JButton implements TableCellRenderer {
//        public ButtonRenderer() {
//            setOpaque(true);
//            setBackground(Color.WHITE);
//        }
//
//        public Component getTableCellRendererComponent(JTable table, Object value,
//                                                       boolean isSelected, boolean hasFocus, int row, int column) {
//            setText((value == null) ? "" : value.toString());
//            return this;
//        }
//    }
//
//    // TableCellEditor for buttons in the table
//    class ButtonEditor extends DefaultCellEditor {
//        protected JButton button;
//        private String label;
//        private boolean isPushed;
//
//        public ButtonEditor(JCheckBox checkBox) {
//            super(checkBox);
//            button = new JButton();
//            button.setOpaque(true);
//            button.setBackground(Color.WHITE);
//            button.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    fireEditingStopped();
//                }
//            });
//        }
//
//        public Component getTableCellEditorComponent(JTable table, Object value,
//                                                     boolean isSelected, int row, int column) {
//            if (isSelected) {
//                button.setForeground(table.getSelectionForeground());
//                button.setBackground(table.getSelectionBackground());
//            } else {
//                button.setForeground(table.getForeground());
//                button.setBackground(table.getBackground());
//            }
//            label = (value == null) ? "" : value.toString();
//            button.setText(label);
//            isPushed = true;
//            return button;
//        }
//
//        public Object getCellEditorValue() {
//            if (isPushed) {
//                if (label.equals("编辑"))
//                    showDetailsDialog(table, label);
//                if (label.equals("删除"))
//                    deleteType(table, label);
//            }
//            isPushed = false;
//            return label;
//        }
//
//        public boolean stopCellEditing() {
//            isPushed = false;
//            return super.stopCellEditing();
//        }
//
//        protected void fireEditingStopped() {
//            super.fireEditingStopped();
//        }
//
//        private void deleteType(JTable table, String label) {
//            //delete……
//        }
//
//        private void showDetailsDialog(JTable table, String label) {
//            int selectedRow = table.getSelectedRow();
//            if (selectedRow >= 0) {
//                String goodsName = table.getValueAt(selectedRow, 0).toString();
//                String goodsReturnable = table.getValueAt(selectedRow, 1).toString();
//                String goodsType = table.getValueAt(selectedRow, 2).toString();
//
//                JDialog dialog = new JDialog(frame, "种类更改", true);
//                dialog.setLayout(new BorderLayout());
//                TextFieldEditor textFieldEditor = new TextFieldEditor();
//
//                // Table for details
//                DefaultTableModel detailsModel = new DefaultTableModel();
//                detailsModel.addColumn("原信息");
//                detailsModel.addColumn("编辑后信息");
//                detailsModel.addRow(new Object[]{goodsName});
//                table.getColumnModel().getColumn(1).setCellEditor(textFieldEditor);
//                detailsModel.addRow(new Object[]{goodsReturnable});
//                table.getColumnModel().getColumn(1).setCellEditor(textFieldEditor);
//                detailsModel.addRow(new Object[]{goodsType});
//                table.getColumnModel().getColumn(1).setCellEditor(textFieldEditor);
//
//                JTable detailsTable = new JTable(detailsModel);
//                dialog.add(new JScrollPane(detailsTable), BorderLayout.CENTER);
//
//                // Buttons panel
//                JPanel buttonsPanel = new JPanel();
//                JButton approveButton = new JButton("确认编辑");
//                buttonsPanel.add(approveButton);
//                dialog.add(buttonsPanel, BorderLayout.SOUTH);
//
//                // Button actions
//                approveButton.addActionListener(ae -> {
//                    if (detailsTable.getCellEditor() != null) {
//                        detailsTable.getCellEditor().stopCellEditing();  // 结束编辑并获取值
//
//                        String cellValue = (String) detailsTable.getValueAt(0, 1);  // 获取当前单元格的值
//                        String cellValue1 = (String) detailsTable.getValueAt(1, 1);
//                        String cellValue2 = (String) detailsTable.getValueAt(2, 1);
//
//                        System.out.println("Edited Value: " + cellValue);
//                        System.out.println("Edited Value: " + cellValue1);
//                        System.out.println("Edited Value: " + cellValue2); // 打印或使用值
//                    }
//                    dialog.dispose();
//                });
//
//                dialog.pack();
//                dialog.setLocationRelativeTo(frame);
//                dialog.setVisible(true);
//            }
//        }
//    }
//}
package view;//package view;
import controller.ApplicationController;
import entity.Application;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import controller.ItemController;
import entity.Item;
import entity.User;

public class ItemTypeManagementView {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private Item[]items;
    private filter_item[] filter_items;

    public ItemTypeManagementView() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("页面 1 - 物资审核");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // Sidebar configuration
        String[] options = {"物资审核", "物资种类管理", "物资数量管理", "个人界面"};
        JList<String> sidebar = new JList<>(options);
        sidebar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sidebar.setSelectedIndex(1);  // Default selection for "物资审核"
        sidebar.setBackground(new Color(255, 182, 193)); // Pink color
        sidebar.setForeground(Color.BLACK);
        sidebar.setFont(new Font("宋体", Font.BOLD, 16));
        sidebar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                switch (sidebar.getSelectedValue()) {
                    case "物资审核":
                        frame.dispose();
                        new ApplicationAuthorizationView();
                        break;
                    case "物资数量管理":
                        frame.dispose();
                        new ItemQuantityManagementView();
                        break;
                    case "个人界面":
                        frame.dispose();
                        new Manager_UI();
                        break;

                }
            }
        });
        frame.add(new JScrollPane(sidebar), BorderLayout.WEST);

        // Search panel configuration
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");
        JButton addButton=new JButton("添加");
        searchPanel.add(new JLabel("搜索物资:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(addButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Table configuration
        String[] columnNames = {"物资编码","物资名称","物资种类","归还性", "操作"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setRowHeight(30);
        addInitialData(); // Method to add data to the model
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
                int row = e.getY() / table.getRowHeight(); // get the row of the button

                // Make sure the click is within the bounds of the table
                if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                    Object value = table.getValueAt(row, column);
                    if ("删除".equals(value)) {
                        showDetailsDialogue(row);
                    }

                }
            }
        });

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Search functionality
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filter_items = new filter_item[1000];
                String searchText = searchField.getText().toLowerCase();
                DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0);
                int index = 0;
                int item_index = 0;
                for (int i = 0; i < model.getRowCount(); i++) {
                    while (items[item_index].isDeleted()) {
                        item_index++;
                    }
                    String name = model.getValueAt(i, 1).toString().toLowerCase();
                    if (name.contains(searchText)) {
                        filteredModel.addRow(new Object[]{items[item_index].getId(),items[item_index].getName(),items[item_index].getType(),items[item_index].isReturnable(),"删除"});
                       // filteredModel.addRow(new Object[]{filter_items[item_index].getAid(), applications[application_index].getUser().getName(), applications[application_index].getItem().getName(), applications[application_index].getApplication_time(), "审核"});
                        filter_item tmp = new filter_item(items[item_index], i);
                        filter_items[index] = tmp;
                        index++;
                    }
                    item_index++;
                }
                table.setModel(filteredModel);
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JDialog dialog = new JDialog(frame, "详细信息", true);
                dialog.setLayout(new BorderLayout());


                // Table setup
                JTable table=new JTable(new AbstractTableModel() {
                    String[] columnNames = {"属性", "值"};
                    Object[][] data = {
                            {"名称", ""},
                            {"种类", ""},
                            {"归还性", ""}
                    };
                    public int getRowCount() {
                        return data.length;
                    }

                    @Override
                    public int getColumnCount() {
                        return columnNames.length;
                    }

                    @Override
                    public Object getValueAt(int rowIndex, int columnIndex) {
                        return data[rowIndex][columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return columnIndex == 1; // 使第二列可编辑
                    }

                    @Override
                    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                        data[rowIndex][columnIndex] = aValue;
                    }

                    @Override
                    public String getColumnName(int column) {
                        return columnNames[column];
                    }
                });

                dialog.add(new JScrollPane(table), BorderLayout.CENTER);

                // 创建保存按钮并添加动作监听器
                JButton saveButton = new JButton("添加");

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (table.getCellEditor() != null) {
                            table.getCellEditor().stopCellEditing();
                        }
                        // 遍历每一行，获取第二列的值
//                        for (int i = 0; i < table.getRowCount(); i++) {
//                            System.out.println("Value at row " + i + ": " + table.getValueAt(i, 1));
//                        }
                        String name= (String) table.getValueAt(0, 1);
                        String type= (String) table.getValueAt(1, 1);
                        String returnable1= (String) table.getValueAt(2, 1);
                        boolean returnable;
                        if (returnable1.equals("false")){
                            returnable=false;
                        }
                        else{
                            returnable=true;
                        }
                        Item tmp=new Item();
                        tmp.setName(name);
                        tmp.setType(type);
                        tmp.setReturnable(returnable);
                        tmp.setDeleted(false);
                        tmp.setQuantity(0);
                        ItemController.addItemType(tmp);
                        int index=0;
                        while (items[index]!=null){
                            index++;
                        }
                        items[index]=tmp;
                        model.addRow(new Object[]{items[index].getId(),items[index].getName(),items[index].getType(),items[index].isReturnable(),"删除"});

                        dialog.dispose();
                    }

                });
                dialog.add(saveButton, BorderLayout.SOUTH);

                dialog.pack();
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });

        // Ensure the frame is centered
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showDetailsDialogue(int row) {
        Item a;
        if (filter_items!=null) {
            a= filter_items[row].item;
        }
        else{
            a = items[row];
        }

        if (filter_items!=null){
            ((DefaultTableModel) table.getModel()).removeRow(row);
            model.removeRow(filter_items[row].real_row);
            filter_items[row].item.setDeleted(true);
        }
        else{
            model.removeRow(row);
            items[row].setDeleted(true);
        }
        ItemController.deleteItemType(a);

    }

    private void addInitialData() {
        items= ItemController.queryI();
        for (int i = 0; i <1000; i++) {
            if (items[i] != null) {
                model.addRow(new Object[]{items[i].getId(),items[i].getName(),items[i].getType(),items[i].isReturnable(),"删除"});
            }
        }
    }



    private static class filter_item{
        Item item;
        int real_row;
        public filter_item(Item item,int real_row){
            this.item=item;
            this.real_row=real_row;
        }

    }


}

