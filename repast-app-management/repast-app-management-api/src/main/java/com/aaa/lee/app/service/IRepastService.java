package com.aaa.lee.app.service;

import com.aaa.lee.app.domain.*;

import com.aaa.lee.app.fallback.RepastFallBackFactory;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import com.aaa.lee.app.vo.OmsOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "userinfo-interface-provider", fallbackFactory = RepastFallBackFactory.class)
public interface IRepastService {
/**
 * @Author Lee
 * @Description
 *  执行登陆操作
 * @Param  * @param member
 * @Return java.lang.Boolean
 * @Date 2019/11/21
 */
    @PostMapping("/login")
    Boolean doLogin(@RequestBody Member member);

    /**
     * @Author Lee
     * @Description
     *      获取收货地址
     * @Param  * @param
     * @Return java.util.List<com.aaa.lee.app.domain.MemberReceiveAddress>
     * @Date 2019/11/21
     */
    @GetMapping("/recive")
    List<MemberReceiveAddress> getMemberReceiveAddress();
    /**
     * @Author Lee
     * @Description
     *      添加订单测试
     * @Param  * @param omsOrder
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/22
     */
    @PostMapping("/addorder")
    Boolean addOrder(@RequestBody List<OmsOrderVo> omsOrder);

    /**
     * @Author Lee
     * @Description
     * 根据用户id查询订单
     * @Param  * @param id
     * @Return
     * @Date 2019/11/26
     */
    @PostMapping("/selectOrderAndShop")
    List<OmsOrderAndShopInfoVo>  selectOrderAndShop(@RequestParam("token") String token, @RequestParam("orderStatus") Integer orderStatus);
    /**
     * @Author Lee
     * @Description
     *  根据订单编号查询订单详情
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.domain.OmsOrder
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderInfo")
    OmsOrder selectOrderInfo(@RequestParam("orderSn") String orderSn);

    /**
     * @Author Lee
     * @Description
     * 根据订单编号查询订单商品详情
     * @Param  * @param orderSn
     * @Return java.util.List<com.aaa.lee.app.domain.OmsOrderItem>
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderItemInfo")
    List<OmsOrderItem> selectOrderItemInfo(@RequestParam("orderSn") String orderSn);
    /**
     * @Author Lee
     * @Description
     * 根据用户id和店铺id查询购物车商品详情
     * @Param  * @param memberId
     * @param shopId
     * @Return java.util.List<com.aaa.lee.app.vo.CartItemAndOrderVO>
     * @Date 2019/11/29
     */
    @PostMapping("/selectCartItemInfo")
    List<CartItemAndOrderVO> selectCartItemInfo(@RequestParam("token") String token, @RequestParam("shopId") Long shopId);

    /**
     * @Author Li Yuzhao
     * @Description 更新购物车接口
     * @Param  * @param data
     * @Return java.lang.Boolean
     * @Date 2019/11/29
     */
    @PostMapping("/addCart")
    Boolean addCart(@RequestBody Map<String, Object> data);
    /**
     * @Author Li Yuzhao
     * @Description 查询购物车接口
     * @Param  * @param shopId
     * @Return java.util.List<com.aaa.lee.app.domain.Cart>
     * @Date 2019/11/29
     */
    @PostMapping("/selectCart")
    List<Cart> selectCart(@RequestBody Map<String, Object> data);
    /**
     * @Author Li Yuzhao
     * @Description 清空购物车接口
     * @Param  * @param shopId
     * @Return java.lang.Boolean
     * @Date 2019/11/29
     */
    @PostMapping("/deleteCart")
    Boolean deleteCartByShopId(@RequestBody Map<String, Object> data);

    /**
     * 通过店铺id和用户id获取当前用户所选购当前商家的商品
     * @param shopId
     * @param memberId
     * @return
     */
    @PostMapping("/getSubmitOrder")
    List<SubmitOrderVO> getSubmitOrder(@RequestParam("shopId") Long shopId, @RequestParam("token") String token);

    /**
     * 通过页面信息创建订单
     */
    @PostMapping("/setOrder")
    int setOrder(@RequestBody List<SubmitOrderVO> submitOrderVO, @RequestParam("token") String token);

    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    @GetMapping("/getIntegralGoods")
    PmsProductVO getIntegralGoods(@RequestParam(value = "productId") Long productId, @RequestParam(value = "token") String token);

    /**
     * 提交积分物品，生成积分订单
     */
    @PostMapping("/setIntegralOrder")
    int setIntegralOrder(@RequestBody PmsProductVO productVO, @RequestParam("token") String token);
    /**
     * @Author Lee
     * @Description
     *      添加订单测试
     * @Param  * @param omsOrder
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/22
     */
    @PostMapping("/addorder")
    Boolean addOrder(@RequestBody Map<String, Object> data);

    /**
     * @Author Lee
     * @Description
     * 根据用户id查询购物车
     * @Param  * @param id
     * @Return
     * @Date 2019/11/26
     */
    @PostMapping("/selectOrderAndShop")
    List<OmsOrderAndShopInfoVo>  selectOrderAndShop(@RequestParam("memberId") Long memberId);

    /**
     * @Author Lee
     * @Description
     * 根据用户id和店铺id查询购物车商品详情
     * @Param  * @param memberId
     * @param shopId
     * @Return java.util.List<com.aaa.lee.app.vo.CartItemAndOrderVO>
     * @Date 2019/11/29
     */
    @PostMapping("/selectCartItemInfo")
    List<CartItemAndOrderVO> selectCartItemInfo(@RequestParam("memberId") Long memberId, @RequestParam("shopId") Long shopId);

    @PostMapping("/deleteOrder")
    Boolean deleteOrder(@RequestParam("orderSn") String orderSn,@RequestParam("token") String token);

    /**
     * @Author Zhang Wei
     * @Description
     *      外卖付款成功，根据订单编号修改订单数据
     * @Param  * @param order
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @PostMapping("/WMpaySuccess")
    Boolean WMpaySuccess(@RequestParam("orderSn") String orderSn,@RequestParam("token") String token);

    /**
     * @Author Zhang Wei
     * @Description
     *      发货成功，根据订单编号修改订单状态
     *      设置定时任务，定时自动确认收货
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    @PostMapping("/deliverySuccess")
    Boolean deliverySuccess(@RequestParam("orderSn") String orderSn,@RequestParam("token") String token);

    /**
     * @Author Zhang Wei
     * @Description
     *      收获成功，根据订单编号修改订单状态
     *      根据店铺用户积分和成长规则，修改用户表的积分和成长值
     *      修改积分修改历史记录
     *      定时自动好评
     * @Param [orderSn]
     * @Return java.lang.Boolean
     * @Date 2019/11/30
     */
    @PostMapping("/reveiveSuccess")
    Boolean reveiveSuccess(@RequestParam("orderSn") String orderSn,@RequestParam("token") String token);

    /**
     * @Author Zhang Wei
     * @Description
     *      根据订单编号取消外卖订单
     * @Param [orderSn]
     * @Return java.lang.Boolean
     * @Date 2019/11/30
     */
    @PostMapping("/WMcancleOrder")
    Boolean WMcancleOrder(@RequestParam("orderSn") String orderSn,@RequestParam("token") String token);
    /**
     * @Author Zhang Wei
     * @Description
     *      微信支付接口
     * @Param [orderSn, openid, amount]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Date 2019/12/6
     */
    @GetMapping("/WMpay")
    Map<String, Object> WMpay(@RequestParam("orderSn")String orderSn, @RequestParam(name = "openid") String openid, @RequestParam(name = "amount") Float amount);

    /**
     * @Author Zhang Wei
     * @Description
     *      微信支付回调接口
     * @Param []
     * @Return void
     * @Date 2019/12/6
     */
    @PostMapping("/wxNotify")
    void wxNotify() throws Exception ;

    @GetMapping("/getOrderByOrderOrderSn")
    /**
     * @Author sgz
     * @Description
     * 根据订单编号查询订单详情
     * @Param [orderSn]
     * @Date 2019/12/6 22:40
     * @Return  com.aaa.lee.app.domain.Order
     * @Throws
     **/
    Order getOrderByOrderOrderSn(@RequestParam("orderSn")String orderSn);

    @PostMapping("/paySuccess")
    /**
     * @Author sgz
     * @Description
     * 食堂点餐成功
     * @Param [orderSn, token]
     * @Date 2019/12/6 22:38
     * @Return  java.lang.Boolean
     * @Throws
     **/
    Boolean paySuccess(@RequestParam("orderSn") String orderSn,@RequestParam("token") String  token);
    /**
     * @Author sgz
     * @Description
     * 取消订单
     * @Param [orderSn]
     * @Date 2019/12/6 8:48
     * @Return  java.lang.Boolean
     * @Throws
     **/
    @PostMapping("/cancleOrder")
    Boolean cancleOrder(@RequestParam("orderSn") String orderSn,@RequestParam("token") String  token);
}
