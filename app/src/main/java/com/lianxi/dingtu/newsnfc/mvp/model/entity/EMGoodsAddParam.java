package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class EMGoodsAddParam implements Serializable {


    /**
     * GoodsNo : 4
     * GoodsType : f4a93a22-1319-4b0f-b10f-311b959f3577
     * GoodsName : 红烧排骨
     * Price : 4.05
     * Total : 5
     * GoodsNature : 6
     * PackageDetails : sample string 7
     * State : 0
     * Description : sample string 8
     * Count : 999
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
}
