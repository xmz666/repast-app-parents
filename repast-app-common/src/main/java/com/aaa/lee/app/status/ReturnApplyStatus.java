package com.aaa.lee.app.status;

public enum ReturnApplyStatus {
    APPLY_SUCCESS("200","退货申请成功,等待商家处理"),
    APPLY_FAILED("400", "退货申请异常,请退出重试"),
    APPLY_APPROVED("201","商家审核通过,退款申请已提交至微信"),
    APPLY_REFUND_SUCCEED("202","退款已入账"),
    APPLY_REFUSED("401","商家拒绝退款，退款失败");


    private String code;
    private String msg;

    ReturnApplyStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
