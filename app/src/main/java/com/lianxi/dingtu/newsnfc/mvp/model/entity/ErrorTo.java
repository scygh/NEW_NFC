package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import java.io.Serializable;

public class ErrorTo implements Serializable {

    /**
     * error_code : 110
     * error_msg : Access token invalid or no longer valid
     */

    private int error_code;
    private String error_msg;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
