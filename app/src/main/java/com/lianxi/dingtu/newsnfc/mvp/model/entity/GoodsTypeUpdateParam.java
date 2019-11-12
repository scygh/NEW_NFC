package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class GoodsTypeUpdateParam implements Serializable {

    /**
     * ID : d1359dcb-789e-47a7-9676-61a754f722e8
     * Name : sample string 2
     * Description : sample string 3
     * State : sample string 4
     */

    private String ID;
    private String Name;
    private String Description;
    private String State;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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
