package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

class CustomerCheckMenu extends JDialog {

    private CustomerMenu Buydata = null;

    public CustomerCheckMenu(CustomerMenu Buydata) {
        this.Buydata = Buydata;
        init();
    }

    private void init() {
        this.setVisible(true);
        this.setBounds(100, 100, 500, 500);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();
        container.setLayout(null);

        JLabel check = new JLabel("凭据");
        JTextArea checkData = new JTextArea();
        setBuyData(checkData);

        JButton confirm = new JButton("确认");

        container.add(check);
        container.add(checkData);
        container.add(confirm);

        check.setBounds(220, 100, 100, 50);
        checkData.setBounds(100, 150, 300, 200);
        checkData.setEditable(false);
        checkData.setFont(new Font("宋体", Font.BOLD, 12));
        confirm.setBounds(200, 400, 100, 50);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Buydata.bback.setVisible(true);
            }
        });
    }

    private void setBuyData(JTextArea checkData) {
        double money = 0;
        String data = "客户" + Buydata.num + "的购物凭据：\n\n";


        for (int i = 0; i < Buydata.tempBuy.length; i++) {
            if (Buydata.tempBuy[i] != 0) {
                double thisMoney = Buydata.goodsObjList.get(i).getGoodsPrice() * Buydata.tempBuy[i];
                data += Buydata.goodsObjList.get(i).getGoodsName() + "×" + Buydata.tempBuy[i] + "  " + thisMoney + "元\n";
                money += thisMoney;
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        data += "\n总价：" + money + "元\n" + formatter.format(date);

        checkData.setText(data);
    }


}
