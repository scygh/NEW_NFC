package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class BaiduTokenTo implements Serializable {

    /**
     * BaiduAppId : 16048247
     * BaiduApiKey : OwxiVmFsOBG7kClGP9p926o3
     * BaiduSecretKey : nFGlY5aEyfpLlcSIBV9swIvvNcW8P9DX
     */

    private String BaiduAppId;
    private String BaiduApiKey;
    private String BaiduSecretKey;

    public String getBaiduAppId() {
        return BaiduAppId;
    }

    public void setBaiduAppId(String BaiduAppId) {
        this.BaiduAppId = BaiduAppId;
    }

    public String getBaiduApiKey() {
        return BaiduApiKey;
    }

    public void setBaiduApiKey(String BaiduApiKey) {
        this.BaiduApiKey = BaiduApiKey;
    }

    public String getBaiduSecretKey() {
        return BaiduSecretKey;
    }

    public void setBaiduSecretKey(String BaiduSecretKey) {
        this.BaiduSecretKey = BaiduSecretKey;
    }
}
