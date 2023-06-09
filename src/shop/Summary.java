package shop;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Summary extends JFrame {

    Database database = new Database();
    private JScrollPane scpDemo;
    private JTableHeader jth;
    private JTable tabDemo;

    private ManagerMenu managerMenu;

    public Summary(ManagerMenu managerMenu) {
        this.managerMenu = managerMenu;
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

    public void init() {
        setTitle("汇总商品");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        this.setLayout(null);
        setLocationRelativeTo(null);
        this.scpDemo = new JScrollPane();
        JButton bt1 = new JButton("返回");
        add(bt1);

        this.scpDemo.setBounds(0, 0, 580, 400);
        bt1.setBounds(481, 421, 100, 30);

        // 初次进入页面的渲染
        database.connect();
        String sql = "select * from goods";
        ResultSet rs = database.query(sql);
        printGood(rs);
        database.closeConnect();

        bt1.addActionListener(new ActionListener() {
            //返回上级界面按钮事件
            public void actionPerformed(ActionEvent e) {
                Summary.this.dispose();
                managerMenu.setVisible(true);
            }
        });

        JButton bt2 = new JButton("按序号默认排序");
        bt2.setBounds(581, 20, 150, 30);
        add(bt2);
        buttonAddActionListener(bt2, "id", "ASC");

        JButton bt3 = new JButton("按价格升序排序");
        bt3.setBounds(581, 70, 150, 30);
        add(bt3);
        buttonAddActionListener(bt3, "price", "ASC");

        JButton bt4 = new JButton("按价格降序排序");
        bt4.setBounds(581, 120, 150, 30);
        add(bt4);
        buttonAddActionListener(bt4, "price", "DESC");

        JButton bt5 = new JButton("按销量升序排序");
        bt5.setBounds(581, 170, 150, 30);
        add(bt5);
        buttonAddActionListener(bt5, "sales", "ASC");

        JButton bt6 = new JButton("按销量降序排序");
        bt6.setBounds(581, 220, 150, 30);
        add(bt6);
        buttonAddActionListener(bt6, "sales", "DESC");

        JButton bt7 = new JButton("按剩余量升序排序");
        bt7.setBounds(581, 270, 150, 30);
        add(bt7);
        buttonAddActionListener(bt7, "quantity", "ASC");

        JButton bt8 = new JButton("按剩余量降序排序");
        bt8.setBounds(581, 320, 150, 30);
        add(bt8);
        buttonAddActionListener(bt8, "quantity", "DESC");


        JLabel income = new JLabel("销售总额：" + sumIncome() + "元");
        income.setBounds(200, 421, 200, 30);
        income.setFont(new Font("宋体", Font.BOLD, 20));
        add(income);

        add(this.scpDemo);
    }

    private void printGood(ResultSet rs) {
        try {
            int count = 0;
            while (rs.next()) {
                count++;
            }
            // 将查询获得的记录数据，转换成适合生成JTable的数据形式
            Object[][] info = new Object[count][6];
            String[] title = {"序号", "商品名称", " 商品价格", "销售量", "库存余量"};
            count = 0;
            rs.beforeFirst();
            while (rs.next()) {
                info[count][0] = rs.getString("id");
                info[count][1] = rs.getString("name");
                info[count][2] = rs.getFloat("price");
                info[count][3] = rs.getString("sales");
                info[count][4] = rs.getString("quantity");
                count++;
            }
            // 创建JTable
            tabDemo = new JTable(info, title);
            // 显示表头
            jth = tabDemo.getTableHeader();
            // 将JTable加入到带滚动条的面板中
            scpDemo.getViewport().add(tabDemo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buttonAddActionListener(JButton button, String attribute, String order) {
        button.addActionListener(new ActionListener() {
            // 按剩余量降序按钮事件
            public void actionPerformed(ActionEvent e) {
                database.connect();
                String sql = "SELECT * FROM goods ORDER BY " + attribute + " " + order;
                ResultSet rs = database.query(sql);
                printGood(rs);
                database.closeConnect();
            }
        });
    }

    private String sumIncome() {
        float result = 0;
        database.connect();
        String sql = "SELECT * FROM goods";
        ResultSet rs = database.query(sql);
        try {
            while (rs.next()) {
                result += rs.getFloat("price") * rs.getFloat("sales");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String str = "" + result;
        return str;
    }
}
