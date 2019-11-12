package com.lianxi.dingtu.newsnfc.mvp.model.entity;

/**
 *    //创建 Commodity 表的 sql 语句
 *     private static final String STUDENTS_CREATE_TABLE_COMMODITY_SQL = "create table" + TABLE_COMMODITY + "("
 *             + "id integer(11) primary key autoincrement,"
 *             + "goods_name varchar(20) not null,"
 *             + "goods_group_id integer(11) not null,"
 *             + ");";
 */
public class Commodity {
    //表名
    public static final String TABLE_COMMODITY = "Commodity";

    //表的各个字段名
    public static final String KEY_ID="ID";
    public static final String KEY_NAME="GoodsName";
    public static final String KEY_GOODSGROUPID="GroupID";
    public static final String KEY_GOODSNO="GoodsNo";

    public int ID;
    public String GoodsName;
    public int GroupID;
    public int GoodsNo;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int groupID) {
        GroupID = groupID;
    }

    public int getGoodsNo() {
        return GoodsNo;
    }

    public void setGoodsNo(int goodsNo) {
        GoodsNo = goodsNo;
    }
}
