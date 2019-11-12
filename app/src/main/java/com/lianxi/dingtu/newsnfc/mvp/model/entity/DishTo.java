package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.util.List;

public class DishTo extends ErrorTo {

    /**
     * log_id : 7357081719365269362
     * result_num : 5
     * result : [{"calorie":"119","has_calorie":true,"name":"酸汤鱼","probability":"0.396031","baike_info":{"baike_url":"http://baike.baidu.com/item/%E9%85%B8%E6%B1%A4%E9%B1%BC/1754055","description":"酸汤鱼，是黔桂湘交界地区的一道侗族名菜，与侗族相邻的苗、水、瑶等少数民族也有相似菜肴，但其中以贵州侗族酸汤鱼最为有名，据考证此菜肴最早源于黎平县雷洞镇牙双一带。制作原料主要有鱼肉、酸汤、山仓子等香料。成菜后，略带酸味、幽香沁人、鲜嫩爽口开胃，是贵州\u201c黔系\u201d菜肴的代表作之一。这道菜通常先自制酸汤，之后将活鱼去掉内脏，入酸汤煮制。"}},{"calorie":"38","has_calorie":true,"name":"原味黑鱼煲","probability":"0.265432"},{"calorie":"144","has_calorie":true,"name":"椒鱼片","probability":"0.0998993"},{"calorie":"98","has_calorie":true,"name":"酸菜鱼","probability":"0.0701917"},{"calorie":"257.65","has_calorie":true,"name":"柠檬鱼","probability":"0.0471465"}]
     */

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * calorie : 119
         * has_calorie : true
         * name : 酸汤鱼
         * probability : 0.396031
         * baike_info : {"baike_url":"http://baike.baidu.com/item/%E9%85%B8%E6%B1%A4%E9%B1%BC/1754055","description":"酸汤鱼，是黔桂湘交界地区的一道侗族名菜，与侗族相邻的苗、水、瑶等少数民族也有相似菜肴，但其中以贵州侗族酸汤鱼最为有名，据考证此菜肴最早源于黎平县雷洞镇牙双一带。制作原料主要有鱼肉、酸汤、山仓子等香料。成菜后，略带酸味、幽香沁人、鲜嫩爽口开胃，是贵州\u201c黔系\u201d菜肴的代表作之一。这道菜通常先自制酸汤，之后将活鱼去掉内脏，入酸汤煮制。"}
         */

        private String calorie;
        private boolean has_calorie;
        private String name;
        private String probability;
        private BaikeInfoBean baike_info;

        public String getCalorie() {
            return calorie;
        }

        public void setCalorie(String calorie) {
            this.calorie = calorie;
        }

        public boolean isHas_calorie() {
            return has_calorie;
        }

        public void setHas_calorie(boolean has_calorie) {
            this.has_calorie = has_calorie;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProbability() {
            return probability;
        }

        public void setProbability(String probability) {
            this.probability = probability;
        }

        public BaikeInfoBean getBaike_info() {
            return baike_info;
        }

        public void setBaike_info(BaikeInfoBean baike_info) {
            this.baike_info = baike_info;
        }

        public static class BaikeInfoBean {
            /**
             * baike_url : http://baike.baidu.com/item/%E9%85%B8%E6%B1%A4%E9%B1%BC/1754055
             * description : 酸汤鱼，是黔桂湘交界地区的一道侗族名菜，与侗族相邻的苗、水、瑶等少数民族也有相似菜肴，但其中以贵州侗族酸汤鱼最为有名，据考证此菜肴最早源于黎平县雷洞镇牙双一带。制作原料主要有鱼肉、酸汤、山仓子等香料。成菜后，略带酸味、幽香沁人、鲜嫩爽口开胃，是贵州“黔系”菜肴的代表作之一。这道菜通常先自制酸汤，之后将活鱼去掉内脏，入酸汤煮制。
             */

            private String baike_url;
            private String description;

            public String getBaike_url() {
                return baike_url;
            }

            public void setBaike_url(String baike_url) {
                this.baike_url = baike_url;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
