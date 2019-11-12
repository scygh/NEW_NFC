package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/9/29 10:31
 */
public class GetDetailList {


    /**
     * Content : [{"Goods":{"GoodsNo":1,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"红烧肉","Price":15,"Total":99,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Hong Shao Rou "},"ImgCount":0},{"Goods":{"GoodsNo":2,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"红烧排骨","Price":15,"Total":98,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Hong Shao Pai Gu "},"ImgCount":0},{"Goods":{"GoodsNo":4,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"鱼香肉丝","Price":0.01,"Total":55,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Yu Xiang Rou Si "},"ImgCount":0},{"Goods":{"GoodsNo":13,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"红烧狮子头","Price":12,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Hong Shao Shi Zi Tou "},"ImgCount":0},{"Goods":{"GoodsNo":17,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"梅菜扣肉","Price":15,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Mei Cai Kou Rou "},"ImgCount":0},{"Goods":{"GoodsNo":18,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"红烧鱼块","Price":10,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Hong Shao Yu Kuai "},"ImgCount":0},{"Goods":{"GoodsNo":19,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"小黄鱼","Price":12,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Xiao Huang Yu "},"ImgCount":0},{"Goods":{"GoodsNo":20,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"秋刀鱼","Price":15,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Qiu Dao Yu "},"ImgCount":0},{"Goods":{"GoodsNo":21,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"红烧鳘鱼","Price":12,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Hong Shao Min Yu "},"ImgCount":0},{"Goods":{"GoodsNo":22,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"糖醋里脊","Price":10,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Tang Cu Li Ji "},"ImgCount":0}]
     * Result : true
     * Message : Success!
     * StatusCode : 200
     */

    private boolean Result;
    private String Message;
    private int StatusCode;
    private List<ContentBean> Content;

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public List<ContentBean> getContent() {
        return Content;
    }

    public void setContent(List<ContentBean> Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        /**
         * Goods : {"GoodsNo":1,"GoodsType":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","GoodsName":"红烧肉","Price":15,"Total":99,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Hong Shao Rou "}
         * ImgCount : 0
         */

        private GoodsBean Goods;
        private int ImgCount;

        public GoodsBean getGoods() {
            return Goods;
        }

        public void setGoods(GoodsBean Goods) {
            this.Goods = Goods;
        }

        public int getImgCount() {
            return ImgCount;
        }

        public void setImgCount(int ImgCount) {
            this.ImgCount = ImgCount;
        }

        public static class GoodsBean {
            /**
             * GoodsNo : 1
             * GoodsType : 1e024250-8d92-4449-8f3c-49ef0bf9bda4
             * GoodsName : 红烧肉
             * Price : 15
             * Total : 99
             * GoodsNature : 0
             * PackageDetails : null
             * State : 1
             * Description : null
             * GoodsLetter : Hong Shao Rou
             */

            private int GoodsNo;
            private String GoodsType;
            private String GoodsName;
            private double Price;
            private int Total;
            private int GoodsNature;
            private Object PackageDetails;
            private int State;
            private Object Description;
            private String GoodsLetter;

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

            public Object getPackageDetails() {
                return PackageDetails;
            }

            public void setPackageDetails(Object PackageDetails) {
                this.PackageDetails = PackageDetails;
            }

            public int getState() {
                return State;
            }

            public void setState(int State) {
                this.State = State;
            }

            public Object getDescription() {
                return Description;
            }

            public void setDescription(Object Description) {
                this.Description = Description;
            }

            public String getGoodsLetter() {
                return GoodsLetter;
            }

            public void setGoodsLetter(String GoodsLetter) {
                this.GoodsLetter = GoodsLetter;
            }
        }
    }
}
