package com.aaa.lee.app.status;

public enum LoginStatus {
    LOGIN_SUCCESS("200", "登录成功"),
    LOGIN_FAILED("400", "登录失败"),
    USER_EXIST("201", "用户已经存在"),
    USER_NOT_EXIST("401", "用户不存在"),
    PASSWORD_WRONG("402", "密码错误"),
    LOGOUT_WRONG("405", "用户退出异常"),
    USER_HAS_MENU("202", "管理员菜单存在"),
    USER_HAS_NOT_MENU("406", "管理员菜单不存在");

    LoginStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
