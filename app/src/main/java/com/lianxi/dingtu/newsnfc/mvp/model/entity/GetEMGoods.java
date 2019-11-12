package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/9/30 09:46
 */
public class GetEMGoods {

    /**
     * Content : [{"Id":"00000000-0000-0000-0000-000000000001","Name":"附加商品类","ParentId":null,"Description":"1","State":1},{"Id":"529d87d4-14ca-4a72-a573-30c9c558b358","Name":"啊啊啊","ParentId":null,"Description":"2","State":0},{"Id":"1e024250-8d92-4449-8f3c-49ef0bf9bda4","Name":"荤菜类","ParentId":null,"Description":"3","State":1},{"Id":"3d3d8767-2f6a-4f2f-90ab-60a82c512cb8","Name":"主食类","ParentId":null,"Description":"4","State":1},{"Id":"45a6d240-9f38-46b3-8b6a-62e21db21ed9","Name":"啊啊啊","ParentId":null,"Description":"5","State":0},{"Id":"c2b735c6-55dc-4754-9501-7268dd7b18e4","Name":"111","ParentId":null,"Description":"6","State":0},{"Id":"e664bd80-2574-424d-8f56-7f0ae2b433eb","Name":"11111","ParentId":null,"Description":"7","State":0},{"Id":"b1305df8-2ae6-4d28-944a-85161a6f0014","Name":"半荤菜类","ParentId":null,"Description":"8","State":1},{"Id":"df11a8e6-27f5-4d54-9120-8c2105840e63","Name":"哈哈哈","ParentId":null,"Description":"9","State":0},{"Id":"24330036-81e3-47fa-a14c-9420f9b354c0","Name":"凉菜类","ParentId":null,"Description":"10","State":1},{"Id":"494fd948-743b-4e51-8ee3-d04e32631757","Name":"粥类","ParentId":null,"Description":"11","State":1},{"Id":"ac4510f1-64a1-4ecd-959d-f2337611b4f7","Name":"123","ParentId":null,"Description":"12","State":0},{"Id":"fd25f4c6-3f36-40e3-b954-fc7fa83e803c","Name":"素菜类","ParentId":null,"Description":"13","State":1}]
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
         * Id : 00000000-0000-0000-0000-000000000001
         * Name : 附加商品类
         * ParentId : null
         * Description : 1
         * State : 1
         */

        private String Id;
        private String Name;
        private Object ParentId;
        private String Description;
        private int State;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public Object getParentId() {
            return ParentId;
        }

        public void setParentId(Object ParentId) {
            this.ParentId = ParentId;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }
    }
}
