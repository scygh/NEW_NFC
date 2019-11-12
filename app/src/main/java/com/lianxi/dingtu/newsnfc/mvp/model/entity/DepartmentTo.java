package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;

public class DepartmentTo implements Serializable,IPickerViewData {

    /**
     * ID : 00000000-0000-0000-0000-000000000001
     * ParentID : “”
     * Name : 默认部门
     * Description : 系统默认的部门
     * State : 1
     */

    private String ID;
    private String ParentID;
    private String Name;
    private String Description;
    private int State;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String ParentID) {
        this.ParentID = ParentID;
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
