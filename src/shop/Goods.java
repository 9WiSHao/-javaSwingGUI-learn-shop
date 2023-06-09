package shop;

public class Goods {
    //商品信息：商品名称，商品价格，商品销量，商品剩余数量，商品编号
    private String goodsName;
    private double goodsPrice;
    private int goodsSales = 0;
    private int goodsQuantity;
    private String GoodsNum;

    public Goods(String goodsName, double goodsPrice, int goodsSales, int goodsQuantity, String goodsNum) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsSales = goodsSales;
        this.goodsQuantity = goodsQuantity;
        this.GoodsNum = goodsNum;
    }

    public Goods() {
    }

    public String getGoodsNum() {
        return GoodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        GoodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsSales() {
        return goodsSales;
    }

    public void setGoodsSales(int goodsSales) {
        this.goodsSales = goodsSales;
    }

    public int getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }
}
