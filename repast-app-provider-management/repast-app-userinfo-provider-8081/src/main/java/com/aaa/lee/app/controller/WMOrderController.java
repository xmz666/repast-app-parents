package com.aaa.lee.app.controller;

import com.aaa.lee.app.Myconst.WXConst;
import com.aaa.lee.app.service.*;
import com.aaa.lee.app.utils.PayUtil;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/11/23 00:01
 * @Description
 **/
@RestController
public class WMOrderController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private WMOrderItemService orderItemService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WMOrderService orderService;
    @Autowired
    private OrderSettingService orderSettingService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    /**
     * @Author Zhang Wei
     * @Description
     *      付款成功，根据订单编号修改外卖订单数据
     * @Param  * @param orderSn
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @PostMapping("/WMpaySuccess")
    public Boolean WMpaySuccess(@RequestParam("orderSn") String  orderSn,@RequestParam("token") String  token){
        Boolean aBoolean = orderService.WMpaySuccess(orderSn,token);
        System.out.println(aBoolean);
        return aBoolean;
    };
    /**
     * @Author Zhang Wei
     * @Description
     *          发货成功，根据订单编号修改订单信息
     *          设置定时任务，定时自动确认收货
     * @Param  * @param orderSn
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @PostMapping("/deliverySuccess")
    public Boolean deliverySuccess(@RequestParam("orderSn") String  orderSn,@RequestParam("token") String  token){
        return orderService.deliverySuccess(orderSn,token,orderSettingService);
    };
    /**
     * @Author Zhang Wei
     * @Description
     *      收货成功，根据订单编号修改订单信息
     * @Param [orderSn]
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @PostMapping("/reveiveSuccess")
    public Boolean receiveSuccess(@RequestParam("orderSn") String orderSn, @RequestParam("token") String token){
       return orderService.receiveSuccess(orderSn,token,orderSettingService,memberService,commentService,orderItemService);
    };
    /**
     * @Author Zhang Wei
     * @Description
     *      未支付订单，取消外卖订单
     * @Param [orderSn]
     * @Return java.lang.Boolean
     * @Date 2019/11/28
     */
    @PostMapping("/WXcancleOrder")
    public Boolean WXcancleOrder(@RequestParam("orderSn") String  orderSn,@RequestParam("token") String  token){
        return orderService.WXcancleOrder(orderSn,token);
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      支付接口
     * @Param [ordersn, openid, amount, httpServletRequest]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Date 2019/12/6
     */
    @GetMapping("/WMpay")
    public Map<String, Object> WMpay(@RequestParam("orderSn") String orderSn, @RequestParam(name = "openid") String openid, @RequestParam(name = "amount") Float amount, HttpServletRequest httpServletRequest){
        Map<String, Object> pay = orderService.pay(orderSn, openid, amount, httpServletRequest);
        if(pay!=null){
            return pay;
        }
        return null;
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      微信支付回调
     * @Param []
     * @Return void
     * @Date 2019/12/6
     */
    @PostMapping("/wxNotify")
    public void wxNotify() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        Map map = WXPayUtil.xmlToMap(notityXml);

        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
            String prestr = PayUtil.createLinkString(validParams);
            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if (PayUtil.verify(prestr, (String) map.get("sign"), WXConst.KEY, "utf-8")) {
                //说明用户付款成功
                //注意要判断微信支付重复回调，支付成功后微信会重复的进行回调
                String orderSn = (String) map.get("out_trade_no");
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}
