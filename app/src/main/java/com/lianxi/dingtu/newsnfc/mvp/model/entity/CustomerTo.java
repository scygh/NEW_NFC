package com.lianxi.dingtu.newsnfc.mvp.model.entity;

public class CustomerTo {

    /**
     * Id : 7cd6e26d-5aba-48e3-9371-e97a6d9032c2
     * Eid : f4076ccd-16bb-46b4-9f7a-26ea7f29b057
     * GoodsNo : 5
     * OrderNo : 1
     * GoodsName : 宫保鸡丁
     * Price : 35.5
     * Amount : 35.5
     * Count : 1
     * CreateTime : 2019-05-16 10:52:52
     */

    private String Id;
    private String Eid;
    private int GoodsNo;
    private int OrderNo;
    private String GoodsName;
    private double Price;
    private double Amount;
    private int Count;
    private String CreateTime;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getEid() {
        return Eid;
    }

    public void setEid(String Eid) {
        this.Eid = Eid;
    }

    public int getGoodsNo() {
        return GoodsNo;
    }

    public void setGoodsNo(int GoodsNo) {
        this.GoodsNo = GoodsNo;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String GoodsName) {
        this.GoodsName = GoodsName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
