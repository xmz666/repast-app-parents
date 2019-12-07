package com.aaa.lee.app.Myconst;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/11/26 09:04
 * @Description
 **/
public class WXConst {
    //微信小程序appid
    public static String APPID = "wx8a3fcf509313fd74";
    //微信小程序appsecret
    public static String APPSECRET = "e8cb3f526ac67e41dffb8fb4201873da";
    //微信支付主体
    public static String TITLE = "test-wxpay";

    //微信商户号
    public static String MCH_ID="1361137902";
    //微信支付的商户密钥
    public static final String KEY = "367151c5fd0d50f1e34a68a802d6bbca";
    //获取微信Openid的请求地址
    public static String WX_GETOPENID_URL = "";
    //支付成功后的服务器回调url
    public static final String NOTIFY_URL="http://localhost:6081/wxNotify";
    //签名方式
    public static final String SIGNTYPE = "MD5";
    //交易类型
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //微信退款通知地址
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
}
