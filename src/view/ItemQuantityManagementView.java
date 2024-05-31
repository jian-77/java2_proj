package view;

import javax.swing.*;
import java.awt.*;

public class ItemQuantityManagementView {
    private JFrame frame;

    public ItemQuantityManagementView() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("物资管理");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(10, 10));

        // Sidebar configuration
        String[] options = {"物资审核", "物资管理", "个人界面"};
        JList<String> sidebar = new JList<>(options);
        sidebar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sidebar.setSelectedIndex(1);  // Default selection for "物资管理"
        sidebar.setBackground(new Color(255, 182, 193)); // Pink color
        sidebar.setForeground(Color.BLACK);
        sidebar.setFont(new Font("宋体", Font.BOLD, 16));
        sidebar.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (sidebar.getSelectedValue().equals("物资审核")) {
                    frame.dispose();
                    new ApplicationAuthorizationView();  // Assuming Page1 is also updated to remember the last state
                }
            }
        });
        frame.add(new JScrollPane(sidebar), BorderLayout.WEST);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.add(new JLabel("物资管理内容显示区域"));
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
