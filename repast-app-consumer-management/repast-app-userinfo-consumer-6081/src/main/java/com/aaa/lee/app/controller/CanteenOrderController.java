package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.Order;
import com.aaa.lee.app.service.IRepastService;
import com.aaa.lee.app.utils.PayUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/22 09:05
 * @Description
 **/
@RestController
@Api(value = "订单信息",tags = "订单信息接口")
public class CanteenOrderController extends BaseController {

    @Autowired
    private IRepastService repastService;
    /**
     * @Author SGZ
     * @Description
     *      根据订单编号查询订单信息
     * @Param [order]
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    @GetMapping("/getOrderByOrderOrderSn")
    @ApiOperation(value = "查询订单",notes = "根据订单主键查询订单信息")
    public ResultData getOrderByOrderOrderSn(@RequestParam("orderSn") String orderSn){
        Order order1 = repastService.getOrderByOrderOrderSn(orderSn);
        if (order1 != null){
            return exist(order1);
        }
        return notExist();
    }
    /**
     * @Author SGZ
     * @Description
     *      付款成功，根据订单编号修改订单信息
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    @PostMapping("/paySuccess")
    @ApiOperation(value = "付款成功",notes = "根据订单编号修改订单信息")
    public ResultData paySuccess(@RequestParam("orderSn") String orderSn, @RequestParam("token") String token){
        Boolean aBoolean = repastService.paySuccess(orderSn,token);
        if(aBoolean){
            return operateSuccess();
        }
        return operateFailed();
    }


    /**
     * @Author SGZ
     * @Description
     *      根据订单编号，对相应订单进行支付操作
     * @Param [orderSn, openid, amount, request]
     * @Return com.alibaba.fastjson.JSONObject
     * @Date 2019/11/26
     */
    @PostMapping("/pay")
    @ApiOperation(value = "微信支付",notes = "调用微信支付接口")
    public JSONObject pay(@RequestParam("orderSn")String orderSn, @RequestParam(name = "openid") String openid, @RequestParam(name = "amount") Float amount, HttpServletRequest request){
        JSONObject jsonObject = PayUtil.wxPay(orderSn, openid, amount,request);
        return jsonObject;
    }


    /**
     * @Author sgz
     * @Description
     * 取消订单
     * @Param [orderSn]
     * @Date 2019/12/6 8:51
     * @Return  com.aaa.lee.app.base.ResultData
     * @Throws
     **/
    @PostMapping("/cancleOrder")
    @ApiOperation(value = "取消订单",notes = "根据订单编号修改订单信息")

    public ResultData cancleOrder(@RequestParam("orderSn") String orderSn, @RequestParam("token") String token){
        Boolean aBoolean = repastService.cancleOrder(orderSn,token);
        if(aBoolean){
            return operateSuccess();
        }
        return operateFailed();
    }

    @PostMapping("/notice")
    public void notice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map payResult = PayUtil.wxNotify(request, response);
//        if(payResult.get("notify_result")!=null&"SUCCESS".equals(payResult.get("notify_result"))){
//            String orderId = (String)payResult.get("out_trade_no");
//            Order order = CanteenOrderService.selectOrderByOrderId(orderId);
//            order.setStatusId(STATUS_ORDER_PAID);
//            CanteenOrderService.updateOrder(order);
//        }

    }
}
