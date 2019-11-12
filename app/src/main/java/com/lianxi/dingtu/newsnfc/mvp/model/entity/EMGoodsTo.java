package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class EMGoodsTo implements Serializable {

    /**
     * GoodsTypeName : 饮料
     * CreateSqlCommand :
     * GoodsNo : 4
     * GoodsType : f3ad6a69-d692-4c85-8f4a-9ec67eec21b0
     * GoodsName : 红牛
     * Price : 8.05
     * Total : 998
     * GoodsNature : 0
     * PackageDetails :
     * State : 1
     * Description :
     * Count : 0
     */

    private String GoodsTypeName;
    private String CreateSqlCommand;
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

    public String getGoodsTypeName() {
        return GoodsTypeName;
    }

    public void setGoodsTypeName(String GoodsTypeName) {
        this.GoodsTypeName = GoodsTypeName;
    }

    public String getCreateSqlCommand() {
        return CreateSqlCommand;
    }

    public void setCreateSqlCommand(String CreateSqlCommand) {
        this.CreateSqlCommand = CreateSqlCommand;
    }

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
