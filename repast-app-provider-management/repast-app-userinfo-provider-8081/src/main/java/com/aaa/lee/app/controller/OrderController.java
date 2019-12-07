package com.aaa.lee.app.controller;

import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.domain.OmsOrderItem;
import com.aaa.lee.app.service.*;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import com.aaa.lee.app.vo.OmsOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/22 16:34
 * @Description
 **/
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderListService  orderListService;
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private OrderItemInfo orderItemInfo;
    @Autowired
    private MemberService memberService;

    @Autowired
    private CartItemAndOrderService cartItemAndOrderService;

    @Autowired
    private MemberRuleSettingService memberRuleSettingService;
    /**
     * @Author Lee
     * @Description
     *  添加订单测试
     * @Param  * @param omsOrder
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/22
     */
    @PostMapping("/addorder")
    public Boolean addOrder(@RequestBody List<OmsOrderVo> omsOrder){
        return orderService.addOrder(omsOrder,memberRuleSettingService,memberService);
    }
    /**
     * @Author Lee
     * @Description
     *      用户id查询该用户所有订单
     * @Param  * @param id
     * @Return java.util.List<com.aaa.lee.app.vo.OmsOrderAndShopInfoVo>
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderAndShop")
    public List<OmsOrderAndShopInfoVo> selectOrderAndShop(@RequestParam("token") String token,@RequestParam("orderStatus") Integer orderStatus){
        return  orderListService.selectOrderAndShop(memberService,token,orderStatus);
    }

    /**
     * @Author Lee
     * @Description
     *      根据订单编号查询订单详情
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.domain.OmsOrder
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderInfo")
    public OmsOrder selectOrderInfo(@RequestParam("orderSn") String orderSn){
        return  orderInfoService.selectOrderInfo(orderSn);
    }
    /**
     * @Author Lee
     * @Description
     *       根据订单编号查询订单中所有商品的详情
     * @Param  * @param OrderSn
     * @Return java.util.List<com.aaa.lee.app.domain.OmsOrderItem>
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderItemInfo")
    public  List<OmsOrderItem> selectOrderItemInfo(@RequestParam("orderSn") String orderSn){
        return orderItemInfo.selectOrderItemInfo(orderSn);
    }
/**
 * @Author Lee
 * @Description
 *  根据用户id和店铺id查询购物车商品详情
 * @Param  * @param memberId
 * @param shopId
 * @Return java.util.List<com.aaa.lee.app.vo.CartItemAndOrderVO>
 * @Date 2019/11/29
 */
    @PostMapping("/selectCartItemInfo")
    public  List<CartItemAndOrderVO> selectCartItemInfo(@RequestParam("token") String token,@RequestParam("shopId") Long shopId){
        return cartItemAndOrderService.selectCartItemInfo(memberService,shopId,token);
    }

    @PostMapping("/deleteOrder")
    public Boolean deleteOrder(@RequestParam("orderSn") String orderSn,@RequestParam("token") String token){
        return orderService.deleteOrder(orderSn,token);
    }
}
