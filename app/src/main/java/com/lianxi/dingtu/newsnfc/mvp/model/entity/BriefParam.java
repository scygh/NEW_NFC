package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class BriefParam implements Serializable {
    private String name;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
