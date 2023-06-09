package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CustomerBuyMenu extends JDialog {
    private CustomerMenu Buydata = null;

    public CustomerBuyMenu(CustomerMenu Buydata) {
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

        JLabel confirm = new JLabel("确认购买");
        JButton confirmButton = new JButton("确认");
        JButton check = new JButton("查看凭据");
        check.setVisible(false);

        container.add(confirm);
        container.add(confirmButton);
        container.add(check);

        confirm.setBounds(220, 100, 100, 50);
        confirmButton.setBounds(200, 200, 100, 50);
        check.setBounds(200, 200, 100, 50);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirm.setText("购买成功");
                confirmButton.setVisible(false);
                check.setVisible(true);
            }
        });

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerCheckMenu(Buydata);
                updateDatebase();
                dispose();
                Buydata.close();
            }
        });
    }

    private void updateDatebase() {
        Database database = new Database();
        database.connect();

        for (int i = 0; i < Buydata.goodsObjList.size(); i++) {
            if (Buydata.tempBuy[i] != 0) {
                database.update("update goods set quantity = quantity - " + Buydata.tempBuy[i] + ", sales = sales + " + Buydata.tempBuy[i] + " where id = " + getNumString(i + 1) + ";");
            }
        }

        database.closeConnect();
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
