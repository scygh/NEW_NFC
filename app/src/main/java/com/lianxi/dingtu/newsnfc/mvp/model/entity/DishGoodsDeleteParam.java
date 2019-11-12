package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class DishGoodsDeleteParam implements Serializable {

    /**
     * ImgStr : sample string 1
     * Cont_Sign : sample string 2
     * GoodsNo : 3
     */

    private String ImgStr;
    private String Cont_Sign;
    private int GoodsNo;

    public String getImgStr() {
        return ImgStr;
    }

    public void setImgStr(String ImgStr) {
        this.ImgStr = ImgStr;
    }

    public String getCont_Sign() {
        return Cont_Sign;
    }

    public void setCont_Sign(String Cont_Sign) {
        this.Cont_Sign = Cont_Sign;
    }

    public int getGoodsNo() {
        return GoodsNo;
    }

    public void setGoodsNo(int GoodsNo) {
        this.GoodsNo = GoodsNo;
    }
}
