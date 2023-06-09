package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMenu extends JFrame {

    public ManagerMenu() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    init();
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 创建管理员主菜单
     */
    public void init() {
        setTitle("管理员主菜单");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 600);
        Container container = this.getContentPane();
        container.setLayout(null);

        JButton btnNewButton = new JButton("添加商品");
        btnNewButton.addActionListener(new ActionListener() {
            //添加商品按钮事件
            public void actionPerformed(ActionEvent e) {
                new Add(ManagerMenu.this);
                setVisible(false);
            }
        });

        JButton btnNewButton_1 = new JButton("修改商品");
        btnNewButton_1.addActionListener(new ActionListener() {
            //修改商品按钮事件
            public void actionPerformed(ActionEvent e) {
                new Change(ManagerMenu.this);
                setVisible(false);
            }
        });

        JButton btnNewButton_2 = new JButton("汇总商品");
        btnNewButton_2.addActionListener(new ActionListener() {
            //下架商品按钮事件
            public void actionPerformed(ActionEvent e) {
                new Summary(ManagerMenu.this);
                setVisible(false);
            }
        });


        // 布局
        container.add(btnNewButton);
        container.add(btnNewButton_1);
        container.add(btnNewButton_2);


        btnNewButton.setBounds(150, 240, 120, 50);
        btnNewButton_1.setBounds(550, 240, 120, 50);
        btnNewButton_2.setBounds(950, 240, 120, 50);
    }

}
