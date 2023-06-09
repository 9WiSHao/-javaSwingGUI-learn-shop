package shop;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class Add extends JFrame {

    private JTextField goodsNametxt;
    private JTextField goodsQuantitytxt;
    private JTextField goodsPricetxt;

    private JScrollPane scpDemo;
    private JTableHeader jth;
    private JTable tabDemo;

    private ManagerMenu managerMenu;

    public Add(ManagerMenu managerMenu) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    init();
                    setVisible(true);
                    Add.this.managerMenu = managerMenu;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 创建添加商品界面
     */
    public void init() {
        setTitle("添加商品");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        this.setLayout(null);
        this.scpDemo = new JScrollPane();
        this.scpDemo.setBounds(201, 0, 580, 300);
        add(this.scpDemo);

        // 初次进入页面的渲染
        Database database = new Database();
        database.connect();
        String sql_1 = "select * from goods";
        ResultSet rs = database.query(sql_1);
        printGood(rs);
        database.closeConnect();

        //添加商品按钮
        JLabel lblNewLabel = new JLabel("商品名：");
        lblNewLabel.setBounds(20, 1, 100, 50);
        add(lblNewLabel);
        JLabel lblNewLabel_1 = new JLabel("商品件数：");
        lblNewLabel_1.setBounds(20, 75, 100, 50);
        add(lblNewLabel_1);
        JLabel lblNewLabel_3 = new JLabel("商品价格：");
        lblNewLabel_3.setBounds(20, 151, 100, 50);
        add(lblNewLabel_3);
        JButton btnNewButton = new JButton("添加");
        btnNewButton.setBounds(51, 201, 100, 30);
        add(btnNewButton);

        //创建文本框
        goodsNametxt = new JTextField();
        goodsNametxt.setColumns(10);
        goodsNametxt.setBounds(101, 20, 100, 20);
        add(goodsNametxt);
        goodsQuantitytxt = new JTextField();
        goodsQuantitytxt.setColumns(10);
        goodsQuantitytxt.setBounds(101, 90, 100, 20);
        add(goodsQuantitytxt);
        goodsPricetxt = new JTextField();
        goodsPricetxt.setColumns(10);
        goodsPricetxt.setBounds(101, 171, 100, 20);
        add(goodsPricetxt);

        btnNewButton.addActionListener(new ActionListener() {
            //添加商品按钮事件
            public void actionPerformed(ActionEvent e) {
                String goodsName = goodsNametxt.getText();
                String goodsQuantity = goodsQuantitytxt.getText();
                String goodsNum = getNumString(tabDemo.getRowCount() + 1);
                String goodsPrice = goodsPricetxt.getText();
                if (StringUtil.isEmpty(goodsName)) {
                    JOptionPane.showMessageDialog(null, "请输入商品名称");
                    return;
                }
                if (StringUtil.isEmpty(goodsQuantity)) {
                    JOptionPane.showMessageDialog(null, "请输入商品数量");
                    return;
                }
                if (StringUtil.isEmpty(goodsPrice)) {
                    JOptionPane.showMessageDialog(null, "请输入商品价格");
                    return;
                }
                //连入数据库
                Database database = new Database();
                database.connect();

                String sql = "insert into goods(id,name,price,sales,quantity) values ('" + goodsNum + "','" + goodsName + "'," + goodsPrice + ",0," + goodsQuantity + ")";
                database.update(sql);

                String sql_1 = "select * from goods";
                ResultSet rs = database.query(sql_1);
                printGood(rs);

                database.closeConnect();
            }
        });

        JButton btnNewButton_1 = new JButton("返回");
        btnNewButton_1.setBounds(51, 251, 100, 30);
        add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            //返回上级界面按钮事件
            public void actionPerformed(ActionEvent e) {
                Add.this.dispose();
                managerMenu.setVisible(true);
            }
        });
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

    private String getNumString(int num) {
        String numString;
        if (num < 10) {
            numString = "0" + num;
        } else {
            numString = "" + num;
        }
        return numString;
    }
}
