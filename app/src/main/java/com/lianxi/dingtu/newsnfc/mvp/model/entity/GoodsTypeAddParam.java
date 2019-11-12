package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class GoodsTypeAddParam implements Serializable {

    /**
     * Name : sample string 1
     * Description : sample string 2
     */

    private String Name;
    private String Description;

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
}
