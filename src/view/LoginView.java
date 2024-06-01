package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.LoginController;
import entity.User;

public class LoginView {
    static User  user;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // 创建主框架
        JFrame frame = new JFrame("实验物资管理系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 420);  // 增加窗口大小
        frame.setLocationRelativeTo(null); // 居中显示

        // 设置背景颜色
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(255, 182, 193)); // 粉红色
        backgroundPanel.setLayout(new GridBagLayout());
        frame.setContentPane(backgroundPanel);

        // 创建标题标签
        JLabel titleLabel = new JLabel("实验物资管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 28));  // 增大字体
        titleLabel.setForeground(Color.BLACK);

        // 添加标题标签到背景面板
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 0, 30, 0); // 设置外边距
        backgroundPanel.add(titleLabel, gbc);

        // 创建主面板
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(360, 240));  // 增加面板大小
        panel.setLayout(new GridBagLayout());

        // 添加主面板到背景面板
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // 重置外边距
        backgroundPanel.add(panel, gbc);

        // 创建登录标签
        JLabel loginLabel = new JLabel("LOGIN", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 22));  // 增大字体

        // 创建账号和密码标签
        JLabel usernameLabel = new JLabel("账号:");
        usernameLabel.setFont(new Font("宋体", Font.PLAIN, 18));  // 增大字体
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(new Font("宋体", Font.PLAIN, 18));  // 增大字体

        // 创建账号和密码输入框
        JTextField usernameField = new JTextField(18);  // 增加列数
        usernameField.setFont(new Font("宋体", Font.PLAIN, 18));  // 增大字体
        usernameField.setToolTipText("请输入账号");

        JTextField passwordField = new JPasswordField(18);  // 增加列数
        passwordField.setFont(new Font("宋体", Font.PLAIN, 18));  // 墮大字体
        passwordField.setToolTipText("请输入密码");

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBackground(new Color(255, 182, 193)); // 粉红色
        loginButton.setFont(new Font("宋体", Font.PLAIN, 18));  // 增大字体
        loginButton.setFocusPainted(false);

        // 添加按钮点击事件
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                Long account = Long.valueOf(username);
                String password = passwordField.getText();
                ResultSet rs = LoginController.Login(account, password);

                try {
                    if (rs.next()) {
                        user=new User();
                        user.setAccount(rs.getLong(1));
                        user.setName(rs.getString(3));
                    if (rs.getBoolean("privilege")) {
                        frame.dispose();
                        new ApplicationAuthorizationView();
                        System.out.println(user.getName());
                    } else {
                        frame.dispose();
                        new ItemApplicationView();
                    }
                } else {
                        JOptionPane.showMessageDialog(frame, "请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // 布局设置
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(loginLabel, c);

        c.gridwidth = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, c);

        // 设置窗口可见
        frame.setVisible(true);
    }
}