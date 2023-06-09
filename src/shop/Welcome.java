package shop;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame {

    private JPanel contentPane;

    public Welcome() {
        setResizable(false);
        setTitle("超市管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 870, 580);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        //标题
        JLabel Login_Title = new JLabel("通通超市欢迎您");
        Login_Title.setFont(new Font("宋体", Font.BOLD, 40));
        Login_Title.setIcon(new ImageIcon("icon\\shop_icon.png"));

        //选择人员登录
        JButton Login_Customer = new JButton("顾客");
        Login_Customer.setFont(new Font("宋体", Font.BOLD, 15));
        Login_Customer.setIcon(new ImageIcon("icon\\customer_icon.png"));
        Login_Customer.addActionListener(new ActionListener() {
            //顾客按钮点击事件
            public void actionPerformed(ActionEvent e) {
                new CustomerMenu(Welcome.this);
                setVisible(false);
            }
        });

        JButton Login_Manager = new JButton("管理员");
        Login_Manager.addActionListener(new ActionListener() {
            //管理员按钮点击事件
            public void actionPerformed(ActionEvent e) {
                new Login(Welcome.this);
            }
        });
        Login_Manager.setIcon(new ImageIcon("icon\\admin_icon.png"));
        Login_Manager.setFont(new Font("宋体", Font.BOLD, 15));

        /**
         * 定义布局
         */
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGap(100).addComponent(Login_Customer, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 380, Short.MAX_VALUE).addComponent(Login_Manager, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE).addGap(68)).addGroup(gl_contentPane.createSequentialGroup().addGap(260).addComponent(Login_Title, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(236)));
        gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGap(83).addComponent(Login_Title, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE).addGap(99).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(Login_Customer).addComponent(Login_Manager, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)).addGap(167)));
        contentPane.setLayout(gl_contentPane);
    }

    public static void main(String[] args) {

        // 设置整体界面风格
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Welcome frame = new Welcome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
