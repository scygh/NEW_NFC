package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class BaiduGoodsTo implements Serializable {

    /**
     * GoodsNo : 6
     * GoodsType : e0aeae8c-b241-4de1-9f1b-6f2fffcf6ed2
     * GoodsName : 番茄炒蛋
     * Price : 15.05
     * Total : 999
     * GoodsNature : 0
     * PackageDetails :
     * State : 1
     * Description : 居家必备
     * Count : 0
     */

    private int GoodsNo;
    private String GoodsType;
    private String GoodsName;
    private double Price;
    private int Total;
    private int GoodsNature;
    private String PackageDetails;
    private int State;
    private String Description;
    private int Count;
    private String Count_Sign;
    public int getGoodsNo() {
        return GoodsNo;
    }

    public void setGoodsNo(int GoodsNo) {
        this.GoodsNo = GoodsNo;
    }

    public String getGoodsType() {
        return GoodsType;
    }

    public void setGoodsType(String GoodsType) {
        this.GoodsType = GoodsType;
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

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public int getGoodsNature() {
        return GoodsNature;
    }

    public void setGoodsNature(int GoodsNature) {
        this.GoodsNature = GoodsNature;
    }

    public String getPackageDetails() {
        return PackageDetails;
    }

    public void setPackageDetails(String PackageDetails) {
        this.PackageDetails = PackageDetails;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public String getCount_Sign() {
        return Count_Sign;
    }

    public void setCount_Sign(String count_Sign) {
        Count_Sign = count_Sign;
    }

    public BaiduGoodsTo() {
    }

    public BaiduGoodsTo(int goodsNo, String goodsName, double price) {
        GoodsNo = goodsNo;
        GoodsName = goodsName;
        Price = price;
    }
}
