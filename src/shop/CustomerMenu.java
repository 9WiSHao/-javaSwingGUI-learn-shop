package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;


public class CustomerMenu extends JFrame {

    public static int num = 1;
    public Welcome bback = null;
    public Database database = new Database();
    public List<Goods> goodsObjList;
    public int[] tempBuy = new int[100];
    private CustomerMenu that;

    public CustomerMenu(Welcome bback) {
        that = this;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    init();
                    setVisible(true);
                    that.bback = bback;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void init() {
        // 初始化
        this.setSize(870, 580);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container container = this.getContentPane();
        container.setLayout(null);

        // 左上角客户编号

        JLabel customerNumber = new JLabel("客户" + (num++) + "号");
        customerNumber.setFont(new Font("微软雅黑", Font.BOLD, 30));
        // 购物车框提示文字
        JLabel shoppingCartSign = new JLabel("购物车");
        // 左边购物车框
        JList shoppingCarts = new JList();
        // 购物车框下面的删除键
        JButton deleteBuy = new JButton("删除");
        // 购买选单提示文字
        JLabel buyListSign = new JLabel("购买选单");
        // 右边购买选单
        JList goodList = new JList(setGoods());
        // 购买选单下面的添加按钮
        JButton addBuy = new JButton("添加");
        // 确认购买按钮
        JButton buyNow = new JButton("确认购买");

        container.add(customerNumber);
        container.add(shoppingCartSign);
        container.add(shoppingCarts);
        container.add(deleteBuy);
        container.add(buyListSign);
        container.add(goodList);
        container.add(addBuy);
        container.add(buyNow);

        // 设置以上组件大小和位置
        customerNumber.setBounds(30, 30, 400, 50);
        shoppingCartSign.setBounds(60, 70, 200, 30);
        shoppingCarts.setBounds(60, 100, 200, 300);
        deleteBuy.setBounds(60, 420, 200, 50);
        buyListSign.setBounds(600, 70, 200, 30);
        goodList.setBounds(600, 100, 200, 300);
        addBuy.setBounds(600, 420, 200, 50);
        buyNow.setBounds(60, 480, 740, 50);

        // 添加监听事件
        deleteBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = shoppingCarts.getSelectedIndex();
                if (index != -1) {
                    String goodNameWithX = (String) shoppingCarts.getSelectedValue();
                    String justGoodName = goodNameWithX.split("×")[0];
                    int goodIndex = database.findGoodsNum(justGoodName);
                    tempBuy[goodIndex] = 0;
                    shoppingCarts.setListData(setTempBuy(tempBuy));
                }
            }
        });

        addBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = goodList.getSelectedIndex();
                if (index != -1) {
                    tempBuy[index]++;
                    shoppingCarts.setListData(setTempBuy(tempBuy));
                }
            }
        });

        buyNow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 跳转到购买界面
                new CustomerBuyMenu(that);
            }
        });
    }

    // 使用此方法，把商品对象列表添加到gui列表
    private Vector setGoods() {
        database.setGoodsList();
        goodsObjList = database.getGoodsList();

        Vector content = new Vector();
        for (int i = 0; i < goodsObjList.size(); i++) {
            content.add(goodsObjList.get(i).getGoodsName() + "  剩余数量:" + goodsObjList.get(i).getGoodsQuantity());
        }
        return content;
    }

    // 使用此方法，把购物车商品号数组转化成向量，方便添加到gui列表
    private Vector setTempBuy(int[] buyArr) {
        Vector result = new Vector();
        for (int i = 0; i < buyArr.length; i++) {
            if (buyArr[i] != 0) {
                result.add(goodsObjList.get(i).getGoodsName() + "×" + buyArr[i]);
            }
        }
        return result;
    }

    //这是调用了关闭这个窗口，回首页的方法
    public void close() {
        this.dispose();
    }
}

