package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class GoodsPicTo implements Serializable {
    private int GoodsNo;
    private String Cont_sign;
    private String Base64Str;

    public int getGoodsNo() {
        return GoodsNo;
    }

    public void setGoodsNo(int goodsNo) {
        GoodsNo = goodsNo;
    }

    public String getCont_sign() {
        return Cont_sign;
    }

    public void setCont_sign(String cont_sign) {
        Cont_sign = cont_sign;
    }

    public String getBase64Str() {
        return Base64Str;
    }

    public void setBase64Str(String base64Str) {
        Base64Str = base64Str;
    }
}
