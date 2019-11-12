package com.lianxi.dingtu.newsnfc.mvp.model.entity;

import android.text.TextUtils;

import com.lianxi.dingtu.newsnfc.mvp.model.api.Api;

import java.io.Serializable;

public class BaseJson<T> implements Serializable {
    private T Data;
    private String ReturnCode;
    private String ReturnMsg;
    private String ErrorCode;
    private String ErrorMsg;

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (TextUtils.equals(ReturnCode, Api.ReturnSuccess)) {
            return true;
        } else {
            return false;
        }
    }

    public T getData() {
        return Data;
    }

    public String getReturnCode() {
        return ReturnCode;
    }

    public String getReturnMsg() {
        return ReturnMsg;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }
}
