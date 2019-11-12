package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

/**
 *   private static final String STUDENTS_CREATE_TABLE_COMMODITYGROUP_SQL = "create table" + TABLE_COMMODITYGROUP + "("
 *             + "id integer(11) primary key autoincrement,"
 *             + "name varchar(20) not null,"
 *             + "beign_time varchar(11) not null,"
 *             + "lastime  varchar(11) not null,"
 *             + "isfilter  integer not null"
 *             + ");";
 */
public class CommodityGroup implements Serializable {
    public static final String TABLE_COMMODITYGROUP = "CommodityGroup";

    //表的各个字段名
    public static final String KEY_ID="ID";
    public static final String KEY_NAME="Name";
    public static final String KEY_BEGINTIME="BeginTime";
    public static final String KEY_ENDTIME="EndTime";
    public static final String KEY_ISFILTER="FilterSwitch";

    public int ID;
    public String Name;
    public String BeginTime;
    public String EndTime;
    public int FilterSwitch;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getFilterSwitch() {
        return FilterSwitch;
    }

    public void setFilterSwitch(int filterSwitch) {
        FilterSwitch = filterSwitch;
    }
}
