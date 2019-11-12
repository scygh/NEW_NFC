package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.util.List;

public class GoodsParam {

    /**
     * Number : 1
     * DeviceID : 1
     * GoodsExpenseList : [{"GoodsNo":5,"Count":1},{"GoodsNo":6,"Count":1}]
     */

    private int Number;
    private int DeviceID;
    private List<GoodsExpenseListBean> GoodsExpenseList;

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int DeviceID) {
        this.DeviceID = DeviceID;
    }

    public List<GoodsExpenseListBean> getGoodsExpenseList() {
        return GoodsExpenseList;
    }

    public void setGoodsExpenseList(List<GoodsExpenseListBean> GoodsExpenseList) {
        this.GoodsExpenseList = GoodsExpenseList;
    }

    public static class GoodsExpenseListBean {
        /**
         * GoodsNo : 5
         * Count : 1
         */

        private int GoodsNo;
        private int Count;

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int GoodsNo) {
            this.GoodsNo = GoodsNo;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }
    }
}
