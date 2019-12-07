package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.domain.OmsOrderItem;
import com.aaa.lee.app.service.IRepastService;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import com.aaa.lee.app.vo.OmsOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/22 16:28
 * @Description
 **/
@RestController
@Api(value = "添加订单信息", tags = "订单接口")
public class OrderController extends BaseController {
    @Autowired
    private IRepastService repastService;

    /**
     * @Author Lee
     * @Description
     *   添加订单测试
     * @Param  * @param omsOrder
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/22
     */
    @PostMapping("/addorder")
    @ApiOperation(value = "添加订单", notes = "执行添加订单")
    public ResultData  addOrder(@RequestBody List<OmsOrderVo> omsOrder) {
        Boolean aBoolean = repastService.addOrder(omsOrder);
        if (aBoolean) {
            return operateSuccess();
        } else {
            return operateFailed();
        }
    }
    /**
     * @Author Lee
     * @Description
     *  根据token和订单状态查询用户订单详情
     * @Param  * @param token
     * @param orderStatus
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/12/5
     */
    @PostMapping("/selectOrderAndShop")
    @ApiOperation(value = "查询订单", notes = "执行查询订单")
    public ResultData selectOrderAndShop(@RequestParam("token") String token,@RequestParam("orderStatus") Integer orderStatus){
        List<OmsOrderAndShopInfoVo> omsOrderAndShopInfoVos = repastService.selectOrderAndShop(token,orderStatus);
        if (omsOrderAndShopInfoVos.size()>0){
            return  exist(omsOrderAndShopInfoVos);
        }else {
            return notExist();
        }
    }
    /**
     * @Author Lee
     * @Description
     *  根据订单编号查询订单信息
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/12/5
     */
    @PostMapping("/selectOrderInfo")
    @ApiOperation(value = "查询订单详情", notes = "执行查询订单详情")
    public ResultData selectOrderInfo(@RequestParam("orderSn") String orderSn){
        OmsOrder omsOrder = repastService.selectOrderInfo(orderSn);
        if(omsOrder != null){
            return  exist(omsOrder);
        }else {
            return  notExist();
        }
    }

    /**
     * @Author Lee
     * @Description
     * 根据订单编号查询订单商品信息
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/12/5
     */
    @PostMapping("/selectOrderItemInfo")
    @ApiOperation(value = "查询订单商品详情", notes = "执行查询订单商品详情")

    public ResultData selectOrderItemInfo(@RequestParam("orderSn") String orderSn){
        List<OmsOrderItem> omsOrderItems = repastService.selectOrderItemInfo(orderSn);
        if(omsOrderItems.size()>0){
           return  exist(omsOrderItems);
        }else {
            return  notExist();
        }
    }

    @PostMapping("/selectCartItemInfo")
    @ApiOperation(value = "", notes = "执行查询购物车商品详情")
    public ResultData selectCartItemInfo(@RequestParam("token") String token, @RequestParam("shopId") Long shopId){
        List<CartItemAndOrderVO> cartItemAndOrderVOS = repastService.selectCartItemInfo(token,shopId);
        if(cartItemAndOrderVOS.size()>0){
            return  exist(cartItemAndOrderVOS);
        }else {
            return  notExist();
        }
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      根据订单编号修改订单为删除状态
     * @Param [orderSn, token]
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/12/6
     */
    @PostMapping("/deleteOrder")
    @ApiOperation(value = "删除订单", notes = "用户删除订单")
    public ResultData deleteOrder(@RequestParam("orderSn") String orderSn,@RequestParam("token") String token){
        Boolean aBoolean = repastService.deleteOrder(orderSn, token);
        if(aBoolean){
            return operateSuccess();
        }
        return operateFailed();
    }
}
