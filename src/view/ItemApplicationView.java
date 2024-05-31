package view;


import javax.swing.*;
import java.awt.*;

public class ItemApplicationView {
    public ItemApplicationView() {
        JFrame frame = new JFrame("页面 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 420);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel("这是页面 2", SwingConstants.CENTER);
        label.setFont(new Font("宋体", Font.BOLD, 24));
        frame.add(label);

        frame.setVisible(true);
    }
}


