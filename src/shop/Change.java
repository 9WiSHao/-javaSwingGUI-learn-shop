package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class Change extends JFrame {

    private JTextField goodsNametxt;
    private JTextField goodsQuantitytxt;
    private JTextField goodsPricetxt;


    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    public List<Goods> goodsObjList;

    private ManagerMenu managerMenu;


    JList goodList = new JList(setGoods());

    public Change(ManagerMenu managerMenu) {
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

    /**
     * 创建修改商品界面
     */
    public void init() {
        setTitle("修改商品");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        this.setLayout(null);

        JLabel lblNewLabel = new JLabel("修改选中项商品名为：");
        lblNewLabel.setBounds(4, 5, 130, 50);
        add(lblNewLabel);
        JLabel lblNewLabel_1 = new JLabel("修改选中项件数为：");
        lblNewLabel_1.setBounds(10, 56, 130, 50);
        add(lblNewLabel_1);
        JLabel lblNewLabel_2 = new JLabel("修改选中项价格为：");
        lblNewLabel_2.setBounds(10, 106, 130, 50);
        add(lblNewLabel_2);
        goodsNametxt = new JTextField();
        goodsNametxt.setColumns(10);
        goodsNametxt.setBounds(121, 20, 100, 30);
        add(goodsNametxt);
        goodsQuantitytxt = new JTextField();
        goodsQuantitytxt.setColumns(10);
        goodsQuantitytxt.setBounds(121, 71, 100, 30);
        add(goodsQuantitytxt);
        goodsPricetxt = new JTextField();
        goodsPricetxt.setColumns(10);
        goodsPricetxt.setBounds(121, 121, 100, 30);
        add(goodsPricetxt);

        JButton btnNewButton = new JButton("修改");
        btnNewButton.setBounds(51, 201, 100, 30);
        add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            //修改商品按钮事件
            public void actionPerformed(ActionEvent e) {
                String goodsName = goodsNametxt.getText();
                String goodsQuantity = goodsQuantitytxt.getText();
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
                int index = goodList.getSelectedIndex();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "未选择要修改的商品");
                    return;
                }
                index++;
                String realIndex = getNumString(index);

                Database database = new Database();
                database.connect();
                String sql = "UPDATE `shop`.`goods` SET `name` = '" + goodsName + "', `price` = '" + goodsPrice + "', `quantity` = '" + goodsQuantity + "' WHERE (`id` = '" + realIndex + "')";
                database.update(sql);

                goodList.setListData(setGoods());

                database.closeConnect();
            }
        });

        btnNewButton_1 = new JButton("下架选中商品");
        btnNewButton_1.setBounds(41, 251, 120, 30);
        add(btnNewButton_1);
        btnNewButton_1.addActionListener(new ActionListener() {
            //返回上级界面按钮事件
            public void actionPerformed(ActionEvent e) {

                Database database = new Database();
                database.connect();
                int index = goodList.getSelectedIndex();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "未选择要修改的商品");
                    return;
                }
                index++;
                String realIndex = getNumString(index);

                String sql = "DELETE FROM `shop`.`goods` WHERE (`id` = '" + realIndex + "')";
                database.update(sql);

                for (int i = index; i < goodsObjList.size(); i++) {
                    String sql2 = "UPDATE `shop`.`goods` SET `id` = '" + getNumString(i) + "' WHERE (`id` = '" + getNumString(i + 1) + "')";
                    database.update(sql2);
                }

                goodList.setListData(setGoods());

                database.closeConnect();
            }
        });


        btnNewButton_2 = new JButton("返回");
        btnNewButton_2.setBounds(51, 351, 100, 30);
        add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {
            //返回上级界面按钮事件
            public void actionPerformed(ActionEvent e) {
                Change.this.dispose();
                managerMenu.setVisible(true);
            }
        });

        goodList.setBounds(221, 0, 580, 400);
        add(goodList);
    }

    private Vector setGoods() {
        Database db1 = new Database();
        db1.setGoodsList();
        goodsObjList = db1.getGoodsList();

        Vector content = new Vector();
        for (int i = 0; i < goodsObjList.size(); i++) {
            content.add("商品编号：" + goodsObjList.get(i).getGoodsNum() + "        商品名称: " + goodsObjList.get(i).getGoodsName() + "        商品价格： " + goodsObjList.get(i).getGoodsPrice() + "        剩余数量: " + goodsObjList.get(i).getGoodsQuantity());
        }
        return content;
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
