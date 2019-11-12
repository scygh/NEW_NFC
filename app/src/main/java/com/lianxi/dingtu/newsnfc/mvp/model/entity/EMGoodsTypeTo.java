package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class EMGoodsTypeTo implements Serializable, IPickerViewData {

    /**
     * ID : e0aeae8c-b241-4de1-9f1b-6f2fffcf6ed2
     * Name : 肉类
     * ParentID :
     * Description :
     * State : 1
     */

    private String ID;
    private String Name;
    private String ParentID;
    private String Description;
    private int State;

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

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
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

    @Override public String getPickerViewText() {
        return Name;
    }
}
