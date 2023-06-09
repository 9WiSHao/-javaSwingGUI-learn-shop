package shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private Statement statement;
    private ResultSet resultSet;
    public Connection connection;

    private List<Goods> goodsList = new ArrayList<>();

    public Database() {

    }

    //登录验证
    public boolean verify(String UserName, String UserPassword) {
        connect();
        String sql = "select * from user where username = '" + UserName + "';";
        try {
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(UserPassword)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnect();
        return false;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public int getGoodsListSize() {
        return goodsList.size();
    }

    public Goods findGoodsObj(String name) {
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).getGoodsName().equals(name)) {
                return goodsList.get(i);
            }
        }
        return null;
    }

    public int findGoodsNum(String name) {
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).getGoodsName().equals(name)) {
                return i;
            }
        }
        return 0;
    }

    public void connect() {
        try {
            // 1. 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 用户信息和 url
            String url = "jdbc:mysql://localhost:3306/shop?useUnicode=true&characterEncoding=utf8&useSSL=true";
            String username = "root";
            String password = "admin123";
            connection = DriverManager.getConnection(url, username, password);
            // 4. 执行SQL的对象
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnect() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 注意，因为statement.executeUpdate(sql)这个函数一次只能执行一条sql语句，所以参数输入只能是一条，多了就会报错
    public void update(String sql) {
        try {
            int res = statement.executeUpdate(sql);
            if (res > 0) {
                System.out.println("更新成功," + res + "行受影响");
            } else {
                System.out.println("更新失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String sql) {
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // 这是个通用的查询方法，可以用来查询任何表（测试用）
    public void queryAndPrint(String sql) {
        try {
            resultSet = statement.executeQuery(sql);

            // 获取元数据以了解查询的结构
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 遍历每一行结果
            while (resultSet.next()) {
                // 对于每一列，获取其值并打印
                for (int i = 1; i <= columnCount; i++) {  // 注意：ResultSet 中的列索引是从 1 开始的
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    System.out.println(columnName + ": " + value);
                }
                System.out.println();  // 每个记录后空一行以增加可读性
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGoodsList() {
        try {
            connect();
            String sql = "select * from goods";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = (String) resultSet.getObject("name");
                double price = (double) resultSet.getObject("price");
                int sales = (int) resultSet.getObject("sales");
                int quantity = (int) resultSet.getObject("quantity");
                String id = (String) resultSet.getObject("id");
                goodsList.add(new Goods(name, price, sales, quantity, id));
            }
            closeConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void printDatabase() {
        try {
            String sql = "select * from goods";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("id=" + resultSet.getObject("id"));
                System.out.println("name=" + resultSet.getObject("name"));
                System.out.println("price=" + resultSet.getObject("price"));
                System.out.println("sales=" + resultSet.getObject("sales"));
                System.out.println("quantity=" + resultSet.getObject("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
