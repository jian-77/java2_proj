package view;
import controller.ApplicationController;
import entity.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import controller.ItemController;

public class User_UI {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table; // Declare table as a class-level variable
    private Application[]applications;

    public User_UI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("页面 6 - 申请人UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // Sidebar configuration
        String[] options = {"物资申请", "个人界面"};
        JList<String> sidebar = new JList<>(options);
        sidebar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sidebar.setSelectedIndex(1);  // Default selection for "物资审核"
        sidebar.setBackground(new Color(255, 182, 193)); // Pink color
        sidebar.setForeground(Color.BLACK);
        sidebar.setFont(new Font("宋体", Font.BOLD, 16));
        sidebar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                switch (sidebar.getSelectedValue()) {

                    case "物资申请":
                        // Handle personal interface logic here
                        frame.dispose();
                        new ItemApplicationView();
                        break;


                }
            }
        });
        frame.add(new JScrollPane(sidebar), BorderLayout.WEST);

        // Center panel configuration
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        frame.add(centerPanel, BorderLayout.CENTER);

        // Search panel
//        JPanel searchPanel = new JPanel(new FlowLayout());
//        JTextField searchField = new JTextField(20);
//        JButton searchButton = new JButton("搜索");
//        searchPanel.add(new JLabel("搜索物资名称:"));
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);
//        centerPanel.add(searchPanel, BorderLayout.NORTH);
//        JPanel userPanel=new JPanel(new FlowLayout());
        String account="12010516";
        String password="12010516";
//        JLabel userLabel = new JLabel("Username:"+account);
//        JLabel passLabel = new JLabel("Password:"+password);
//        userPanel.add(userLabel);
//        userPanel.add(passLabel);
        String[] columnNames1 = {"账号", "密码"};
        model = new DefaultTableModel(null, columnNames1);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 50));
        table.setRowHeight(30);
        addAccount();
        JScrollPane scrollPane1 = new JScrollPane(table);
        centerPanel.add(scrollPane1, BorderLayout.NORTH);

        // Table model setup
        String[] columnNames = {"申请id","审核人姓名", "物资名称", "物资申请量","审核结果","操作"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 100));
        table.setRowHeight(30);
        addInitialData(); // Method to add data to the model

        // Adding buttons to the table
        table.getColumn("操作").setCellRenderer(new ButtonRenderer());
        table.getColumn("操作").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane scrollPane = new JScrollPane(table);
        JButton outButton=new JButton("注销");
        outButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SwingUtilities.invokeLater(() -> new LoginView().createAndShowGUI());
            }


        });
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(outButton,BorderLayout.SOUTH);


        // Search button action listener
//        searchButton.addActionListener(e -> {
//            String searchText = searchField.getText().trim();
//            filterTable(searchText);
//        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void addAccount() {
        model.addRow(new Object[]{LoginView.user.getName(), LoginView.user.getPassword()});


    }
    // Method to add initial data to the table
    private void addInitialData() {
        applications= ApplicationController.query_record_U(LoginView.user.getAccount());

        for (int i = 0; i <1000; i++) {
            if (applications[i] != null) {
                String status;
                if (applications[i].getStatus()==0){
                    status="未审核";
                } else if (applications[i].getStatus()==1) {
                    status="审核通过";
                }
                else{
                    status="审核不通过";
                }
                model.addRow(new Object[]{applications[i].getAid(),applications[i].getManager().getName(),applications[i].getItem().getName(),applications[i].getApplied_quantity(),status,"操作"});
            }
        }
//
//        model.addRow(new Object[]{"1","A","电脑",3,"未批准", "详情"});
//        model.addRow(new Object[]{"2","B","氯化钠",50,"已批准","详情"});
//        model.addRow(new Object[]{"3","C","烧杯", 7,"已批准","详情"});

    }

    // Method to filter table based on search text
    private void filterTable(String searchText) {
        DefaultTableModel filteredModel = new DefaultTableModel(new Object[]{"申请id","审核人姓名", "物资名称", "物资申请量","审核结果","操作"}, 0);
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().contains(searchText)) {
                filteredModel.addRow(new Object[]{
                        model.getValueAt(i, 0),
                        model.getValueAt(i, 1),
                        model.getValueAt(i, 2),
                        model.getValueAt(i, 3),
                        model.getValueAt(i, 4),
                        "详情"
                });
            }
        }
        table.setModel(filteredModel);
        table.getColumn("操作").setCellRenderer(new ButtonRenderer());
        table.getColumn("操作").setCellEditor(new ButtonEditor(new JCheckBox()));
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
                Long applicationId=Long.valueOf(table.getValueAt(selectedRow,0).toString());
                String authorizationName;
                if ( applications[selectedRow].getManager().getName()!=null){
                    authorizationName = applications[selectedRow].getManager().getName();
                }else{
                    authorizationName ="";
                }


                String goodsName = table.getValueAt(selectedRow, 2).toString();
//              String goodsType = table.getValueAt(selectedRow, 1).toString();
                int goodsQuantity=Integer.parseInt(table.getValueAt(selectedRow, 3).toString());
                String authorizationState=table.getValueAt(selectedRow, 4).toString();


                String goodsType=applications[selectedRow].getItem().getType();
                String goodsReturnable= String.valueOf(applications[selectedRow].getItem().isReturnable());
                String applicationTime=applications[selectedRow].getApplication_time();;
                String returnState= String.valueOf(applications[selectedRow].isReturned());
                String returnTime=applications[selectedRow].getReturn_time();



                JDialog dialog = new JDialog(frame, "审核记录详情", true);
                dialog.setLayout(new BorderLayout());
                TextFieldEditor textFieldEditor = new TextFieldEditor();

                // Table for details
                DefaultTableModel detailsModel = new DefaultTableModel();
                detailsModel.addColumn("审核信息");
                detailsModel.addColumn("值");
                detailsModel.addRow(new Object[]{"申请id", applicationId});
                detailsModel.addRow(new Object[]{"审核人姓名", authorizationName});
                detailsModel.addRow(new Object[]{"物资名称", goodsName});
                detailsModel.addRow(new Object[]{"物资余量", goodsQuantity});
                detailsModel.addRow(new Object[]{"审核状态", authorizationState});
                detailsModel.addRow(new Object[]{"物资种类", goodsType});
                detailsModel.addRow(new Object[]{"是否需要归还", goodsReturnable});
                detailsModel.addRow(new Object[]{"申请时间", applicationTime});
                detailsModel.addRow(new Object[]{"归还状态", returnState});
                detailsModel.addRow(new Object[]{"归还时间", returnTime});
//                detailsModel.addRow(new Object[]{"物品种类", details[2]});
//                detailsModel.addRow(new Object[]{"申请用量"});
//                table.getColumnModel().getColumn(1).setCellEditor(textFieldEditor);
                JTable detailsTable = new JTable(detailsModel);
                dialog.add(new JScrollPane(detailsTable), BorderLayout.CENTER);

                JPanel buttonsPanel = new JPanel();
                JButton approveButton = new JButton("归还");
//                JButton denyButton = new JButton("不批准");
                buttonsPanel.add(approveButton);
//                buttonsPanel.add(denyButton);
                dialog.add(buttonsPanel, BorderLayout.SOUTH);

                // Button actions
                approveButton.addActionListener(ae -> {
                            if(goodsReturnable.equals("false")||returnState.equals("true")||applications[selectedRow].getStatus()==0||applications[selectedRow].getStatus()==2)
                            {
                                System.out.println("无需归还");
                            }
                            else
                            {
                                ItemController.editItemQuantity(applications[selectedRow].getItem(),applications[selectedRow].getItem().getQuantity()+applications[selectedRow].getApplied_quantity());
                                LocalDate currentDate = LocalDate.now();
                                String return_time=String.valueOf(currentDate);
                                ApplicationController.returned(applications[selectedRow],return_time);
                                applications[selectedRow].setReturned(true);
                                System.out.println("成功归还");
                            }


                            dialog.dispose();
                        }
                );
//                denyButton.addActionListener(ae -> {
//                    model.removeRow(selectedRow);
//                    dialog.dispose();
//                });


                // Buttons panel
//                JPanel buttonsPanel = new JPanel();
//                JButton approveButton = new JButton("申请");
////                JButton denyButton = new JButton("不批准");
//                buttonsPanel.add(approveButton);
////                buttonsPanel.add(denyButton);
//                dialog.add(buttonsPanel, BorderLayout.SOUTH);
//
//                // Button actions
//                approveButton.addActionListener(ae -> {
//
//                            if (detailsTable.getCellEditor() != null) {
//                                detailsTable.getCellEditor().stopCellEditing();  // 结束编辑并获取值
//
//                                String cellValue = (String) detailsTable.getValueAt(4, 1);  // 获取当前单元格的值
//                                int cellValue1=Integer.valueOf(cellValue);
//                                System.out.println("Edited Value: " + cellValue1);           // 打印或使用值
//                            }
//                            dialog.dispose();
//                        }
//                );
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
