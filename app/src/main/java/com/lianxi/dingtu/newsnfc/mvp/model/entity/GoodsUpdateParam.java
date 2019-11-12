package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class GoodsUpdateParam implements Serializable {


    /**
     * GoodsNo : 1
     * Price : sample string 2
     * Total : sample string 3
     * Description : sample string 4
     * State : sample string 5
     */

    private int GoodsNo;
    private String Price;
    private String Total;
    private String Description;
    private String State;

    public int getGoodsNo() {
        return GoodsNo;
    }

    public void setGoodsNo(int GoodsNo) {
        this.GoodsNo = GoodsNo;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }
}
