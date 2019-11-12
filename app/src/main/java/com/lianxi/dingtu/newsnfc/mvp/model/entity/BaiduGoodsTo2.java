package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class BaiduGoodsTo2 implements Serializable {


    /**
     * GoodsIofo : {"GoodsNo":4,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"鱼香肉丝","GoodsLetter":"Yu Xiang Rou Si ","Price":0.01,"Total":100,"GoodsNature":0,"PackageDetails":"","State":1,"Description":"","Count":0}
     * Count_Sign : 2767862680,3582927465
     */

    private GoodsIofoBean GoodsIofo;
    private String Count_Sign;

    public GoodsIofoBean getGoodsIofo() {
        return GoodsIofo;
    }

    public void setGoodsIofo(GoodsIofoBean GoodsIofo) {
        this.GoodsIofo = GoodsIofo;
    }

    public String getCount_Sign() {
        return Count_Sign;
    }

    public void setCount_Sign(String Count_Sign) {
        this.Count_Sign = Count_Sign;
    }

    public static class GoodsIofoBean {
        /**
         * GoodsNo : 4
         * GoodsType : 1e024250-8d92-4449-8f3c-49ef0bf9bda4
         * GoodsName : 鱼香肉丝
         * GoodsLetter : Yu Xiang Rou Si
         * Price : 0.01
         * Total : 100
         * GoodsNature : 0
         * PackageDetails :
         * State : 1
         * Description :
         * Count : 0
         */

        private int GoodsNo;
        private String GoodsType;
        private String GoodsName;
        private String GoodsLetter;
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

        public String getGoodsLetter() {
            return GoodsLetter;
        }

        public void setGoodsLetter(String GoodsLetter) {
            this.GoodsLetter = GoodsLetter;
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
}
