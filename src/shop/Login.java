package shop;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField UserNametxt;
    private JTextField UserPasswordtxt;

    public Login(Welcome wel) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    init(wel);
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void init(Welcome wel) {
        setResizable(false);
        setTitle("管理员登录");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        //账号
        JLabel UserName = new JLabel("账号：");
        UserName.setFont(new Font("宋体", Font.BOLD, 20));
        UserNametxt = new JTextField();
        UserNametxt.setColumns(15);

        //密码
        JLabel UserPassword = new JLabel("密码：");
        UserPassword.setFont(new Font("宋体", Font.BOLD, 20));
        UserPasswordtxt = new JPasswordField();
        UserPasswordtxt.setColumns(15);

        //登录按钮
        JButton Login = new JButton("登录");
        Login.addActionListener(new ActionListener() {
            //登录按钮事件
            public void actionPerformed(ActionEvent e) {
                //获取文本
                String userName = UserNametxt.getText();
                String userPassword = new String(((JPasswordField) UserPasswordtxt).getPassword());

                // 登录事件判断
                if (StringUtil.isEmpty(userName)) {
                    JOptionPane.showMessageDialog(null, "账号不能为空");
                    return;
                }
                if (StringUtil.isEmpty(userPassword)) {
                    JOptionPane.showMessageDialog(null, "密码不能为空");
                    return;
                }

                /**
                 *  连接数据库检验账号密码是否正确
                 */

                Database db = new Database();
                if (!db.verify(userName, userPassword)) {
                    //账号密码错误
                    JOptionPane.showMessageDialog(null, "账号或密码错误");
                    return;
                }
                //账号密码正确
                new ManagerMenu();
                wel.setVisible(false);
                setVisible(false);
            }
        });

        //取消登录按钮
        JButton Cancel = new JButton("取消");
        Cancel.addActionListener(new ActionListener() {
            //取消按钮事件
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        //标题
        JLabel Title = new JLabel("管理员登录");
        Title.setFont(new Font("宋体", Font.BOLD, 35));
        Title.setIcon(new ImageIcon("icon\\admin_icon.png"));

        /**
         * 定义布局
         */

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addGap(85).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addGroup(gl_contentPane.createSequentialGroup().addComponent(UserName).addPreferredGap(ComponentPlacement.RELATED).addComponent(UserNametxt)).addGroup(gl_contentPane.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(38).addComponent(Cancel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE).addGap(81).addComponent(Login, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)).addGroup(gl_contentPane.createSequentialGroup().addComponent(UserPassword).addPreferredGap(ComponentPlacement.RELATED).addComponent(UserPasswordtxt, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE))))).addContainerGap(75, Short.MAX_VALUE)).addGroup(gl_contentPane.createSequentialGroup().addGap(101).addComponent(Title, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE).addGap(84)));
        gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(30).addComponent(Title).addGap(41).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(UserName).addComponent(UserNametxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(UserPasswordtxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(UserPassword)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false).addComponent(Login, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(Cancel, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)).addContainerGap()));
        contentPane.setLayout(gl_contentPane);
    }
}
