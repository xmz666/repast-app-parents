package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.service.IRepastService;
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
 * @Author Zhang Wei
 * @Date Create in 2019/11/22 09:05
 * @Description
 **/
@RestController
@Api(value = "订单信息",tags = "订单信息接口")
public class WMController extends BaseController {


    @Autowired
    private IRepastService repastService;
    /**
     * @Author Zhang Wei
     * @Description
     *      外卖付款成功，根据订单编号修改订单信息
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    @PostMapping("/WMpaySuccess")
    @ApiOperation(value = "付款成功",notes = "根据订单编号修改订单信息")
    public ResultData WMpaySuccess(@RequestParam("orderSn") String orderSn, @RequestParam("token") String token){
        Boolean aBoolean = repastService.WMpaySuccess(orderSn,token);
        if(aBoolean){
            return operateSuccess();
        }
        return operateFailed();
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      根据订单编号取消外卖订单
     * @Param [orderSn, date]
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/26
     */
    @PostMapping("/WMcancleOrder")
    @ApiOperation(value = "取消订单",notes = "根据订单编号修改订单信息")
    public ResultData WMcancleOrder(@RequestParam("orderSn") String orderSn, @RequestParam("token") String token){
        Boolean aBoolean = repastService.WMcancleOrder(orderSn,token);
        if(aBoolean){
            return operateSuccess();
        }
        return operateFailed();
    }
    /**
     * @Author Zhang Wei
     * @Description
     *          发货成功，根据订单编号修改订单信息
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    @PostMapping("/deliverySuccess")
    @ApiOperation(value = "发货成功",notes = "根据订单编号修改订单信息")
    public ResultData deliverySuccess(@RequestParam("orderSn") String orderSn, @RequestParam("token") String token){
        Boolean aBoolean = repastService.deliverySuccess(orderSn,token);
        if (aBoolean){
            return operateSuccess();
        }
        return operateFailed();
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      收货成功，根据订单编号修改订单信息
     * @Param [order]
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    @PostMapping("/reveiveSuccess")
    @ApiOperation(value = "收货成功",notes = "根据订单编号修改订单信息")
    public ResultData reveiveSuccess(@RequestParam("orderSn") String orderSn, @RequestParam("token") String token){
        Boolean aBoolean = repastService.reveiveSuccess(orderSn,token);
        if(aBoolean){
            return operateSuccess();
        }
        return operateFailed();
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      根据订单编号，对相应外卖订单进行支付操作
     * @Param [orderSn, openid, amount, request]
     * @Return com.alibaba.fastjson.JSONObject
     * @Date 2019/11/26
     */
    @GetMapping("/WMpay")
    @ApiOperation(value = "微信支付",notes = "调用微信支付接口")
    public ResultData WMpay(@RequestParam("orderSn")String orderSn, @RequestParam(name = "openid") String openid, @RequestParam(name = "amount") Float amount){
        Map<String, Object> result= repastService.WMpay(orderSn,openid,amount);
        if(result!=null){
            return operateSuccess("支付成功",result);
        }else {
            return operateFailed("支付失败");
        }
    }

    @PostMapping("/wxNotify")
    @ApiOperation(value = "微信支付回调",notes = "微信支付回调接口")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        repastService.wxNotify();
    }
}
