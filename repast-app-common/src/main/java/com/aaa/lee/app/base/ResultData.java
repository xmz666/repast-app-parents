package com.aaa.lee.app.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/21 18:58
 * @Description
 **/

public class ResultData<T> implements Serializable {
    private String code;
    private String msg;
    private String detail;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
