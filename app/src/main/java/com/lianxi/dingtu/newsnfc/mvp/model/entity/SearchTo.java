package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class SearchTo extends ErrorTo {

    /**
     * result_num : 2
     * result : [{"location":{"left":127,"top":70,"width":103,"height":101},"dishes":[{"score":0.47976872324944,"brief":"{\"name\":\"baicai\",\"id\":333}","cont_sign":"475124309,1080176642"}]},{"location":{"left":312,"top":163,"width":78,"height":79},"dishes":[{"score":0.013810452073812,"brief":"{\"name\":\"doufu\",\"id\":333}","cont_sign":"475124309,1080176642"}]}]
     * log_id : 1837888624
     */

    private long result_num;
    private long log_id;
    private List<ResultBean> result;

    public long getResult_num() {
        return result_num;
    }

    public void setResult_num(long result_num) {
        this.result_num = result_num;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * location : {"left":127,"top":70,"width":103,"height":101}
         * dishes : [{"score":0.47976872324944,"brief":"{\"name\":\"baicai\",\"id\":333}","cont_sign":"475124309,1080176642"}]
         */

        private LocationBean location;
        private List<DishesBean> dishes;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public List<DishesBean> getDishes() {
            return dishes;
        }

        public void setDishes(List<DishesBean> dishes) {
            this.dishes = dishes;
        }

        public static class LocationBean {
            /**
             * left : 127
             * top : 70
             * width : 103
             * height : 101
             */

            private int left;
            private int top;
            private int width;
            private int height;

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

        public static class DishesBean implements Serializable, IPickerViewData {
            /**
             * score : 0.47976872324944
             * brief : {"name":"baicai","id":333}
             * cont_sign : 475124309,1080176642
             */

            private double score;
            private String brief;
            private String cont_sign;
            private List<BriefBean> briefBeans;

            public List<BriefBean> getBriefBeans() {
                return briefBeans;
            }

            public void setBriefBeans(List<BriefBean> briefBeans) {
                this.briefBeans = briefBeans;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getCont_sign() {
                return cont_sign;
            }

            public void setCont_sign(String cont_sign) {
                this.cont_sign = cont_sign;
            }

            @Override public String getPickerViewText() {
                try {
                    JSONObject json = new JSONObject(brief);
                    return json.getString("GoodsName");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return brief;
            }

            public static class BriefBean {
                /**
                 *"brief": "{\"CompanyCode\": \"1001\",\"GoodsNo\":\"65535\",\"GoodsName\":\"托盘\"}",
                 */

                private int CompanyCode;
                private int GoodsNo;
                private String GoodsName;

                public int getCompanyCode() {
                    return CompanyCode;
                }

                public void setCompanyCode(int companyCode) {
                    CompanyCode = companyCode;
                }

                public int getGoodsNo() {
                    return GoodsNo;
                }

                public void setGoodsNo(int goodsNo) {
                    GoodsNo = goodsNo;
                }

                public String getGoodsName() {
                    return GoodsName;
                }

                public void setGoodsName(String goodsName) {
                    GoodsName = goodsName;
                }
            }
        }


}
}
