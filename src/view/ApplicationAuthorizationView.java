package view;//package view;
import controller.ApplicationController;
import entity.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import controller.ItemController;
import entity.User;

public class ApplicationAuthorizationView {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private Application[]applications;
    private filter_Application[] filter_applications;

    public ApplicationAuthorizationView() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("页面 1 - 物资审核");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(new BorderLayout(10, 10));

        // Sidebar configuration
        String[] options = {"物资审核", "物资种类管理", "物资数量管理", "个人界面"};
        JList<String> sidebar = new JList<>(options);

        sidebar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sidebar.setSelectedIndex(0);  // Default selection for "物资审核"
        sidebar.setBackground(new Color(255, 182, 193)); // Pink color
        sidebar.setForeground(Color.BLACK);
        sidebar.setFont(new Font("宋体", Font.BOLD, 16));
        sidebar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                switch (sidebar.getSelectedValue()) {
                    case "物资种类管理":
                        frame.dispose();
                        new ItemTypeManagementView();
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
        JPanel sidebarPanel = new JPanel(new BorderLayout());
        sidebarPanel.add(sidebar, BorderLayout.CENTER);
        frame.add(sidebarPanel, BorderLayout.WEST);

        // Search panel configuration
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton("搜索");
        searchPanel.add(new JLabel("搜索申请物资:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.NORTH);

        // Table configuration
        String[] columnNames = {"申请编号", "申请人姓名", "申请物资", "申请时间", "操作"};
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
                    if ("审核".equals(value)) {
                        showDetailsDialogue(row);
                    }
                }
            }
        });

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Search functionality
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filter_applications = new filter_Application[1000];
                String searchText = searchField.getText().toLowerCase();
                DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0);
                int index = 0;
                int application_index = 0;
                for (int i = 0; i < model.getRowCount(); i++) {
                    while (applications[application_index].isDelete) {
                        application_index++;
                    }
                    String name = model.getValueAt(i, 2).toString().toLowerCase();
                    if (name.contains(searchText)) {
                        filteredModel.addRow(new Object[]{applications[application_index].getAid(), applications[application_index].getUser().getName(), applications[application_index].getItem().getName(), applications[application_index].getApplication_time(), "审核"});
                        filter_Application tmp = new filter_Application(applications[application_index], i);
                        filter_applications[index] = tmp;
                        index++;
                    }
                    application_index++;
                }
                table.setModel(filteredModel);
            }
        });

        // Ensure the frame is centered
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showDetailsDialogue(int row) {
        Application a;
       if (filter_applications!=null) {
            a= filter_applications[row].application;
       }
       else{
           int index=0;
           int real_row=0;
           while (real_row<row){
               if (!applications[index].isDelete){
                   real_row++;
               }
               index++;
           }
           a = applications[real_row];
       }
        JDialog dialog = new JDialog(frame, "详细信息", true);
        dialog.setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"属性", "值"};
        String[] rowData = {"申请人姓名", a.getUser().getName(),
                "申请时间",a.getApplication_time(),
                "申请物品名称",a.getItem().getName(),
                "是否需要归还",String.valueOf(a.getItem().isReturnable()),
                "物品种类",a.getItem().getType(),
                "申请用量",String.valueOf(a.getApplied_quantity()),
                "物资余量",String.valueOf(a.getItem().getQuantity()),
                "是否删除",String.valueOf(a.getItem().isDeleted()),
                };



        DefaultTableModel detailsModel = new DefaultTableModel(columnNames, 0);
        for (int i = 0; i < rowData.length; i += 2) {
            detailsModel.addRow(new Object[]{rowData[i], rowData[i + 1]});
        }
        JTable detailsTable = new JTable(detailsModel);
        dialog.add(new JScrollPane(detailsTable), BorderLayout.CENTER);

        // Button setup
        JPanel buttonsPanel = new JPanel();
        JButton approveButton = new JButton("批准");
        JButton denybutton=new JButton("不批准");
        buttonsPanel.add(approveButton);
        buttonsPanel.add(denybutton);
        dialog.add(buttonsPanel, BorderLayout.SOUTH);

        approveButton.addActionListener(ae -> {

            if (filter_applications!=null){
                ((DefaultTableModel) table.getModel()).removeRow(row);
                model.removeRow(filter_applications[row].real_row);
                filter_applications[row].application.isDelete=true;
                ItemController.editItemQuantity(filter_applications[row].application.getItem(),filter_applications[row].application.getItem().getQuantity()-filter_applications[row].application.getApplied_quantity());
            }
            else{
                model.removeRow(row);
//                int index=row;
//                while (applications[index]!=null){
//                    applications[index]=applications[index+1];
//                    index++;
//                }
                applications[row].isDelete=true;
                ItemController.editItemQuantity(applications[row].getItem(),applications[row].getItem().getQuantity()-applications[row].getApplied_quantity());
            }
            ApplicationController.pass(a, LoginView.user.getAccount());
            dialog.dispose();
        });
        denybutton.addActionListener(ae -> {
            if (filter_applications!=null){
                ((DefaultTableModel) table.getModel()).removeRow(row);
                model.removeRow(filter_applications[row].real_row);
                filter_applications[row].application.isDelete=true;
            }
            else{
                model.removeRow(row);
                applications[row].isDelete=true;
            }
            ApplicationController.reject(a, LoginView.user.getAccount());
            dialog.dispose();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void addInitialData() {
        applications= ApplicationController.queryA();

        for (int i = 0; i <1000; i++) {
            if (applications[i] != null) {
               model.addRow(new Object[]{applications[i].getAid(),applications[i].getUser().getName(),applications[i].getItem().getName(),applications[i].getApplication_time(),"审核"});
            }
        }
    }



    private static class filter_Application{
        Application application;
        int real_row;
        public filter_Application(Application application,int real_row){
            this.application=application;
            this.real_row=real_row;
        }

    }


}

