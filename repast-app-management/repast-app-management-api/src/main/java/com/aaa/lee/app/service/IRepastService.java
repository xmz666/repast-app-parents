package com.aaa.lee.app.service;

import com.aaa.lee.app.domain.Member;
import com.aaa.lee.app.domain.MemberReceiveAddress;

import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.domain.OmsOrderItem;
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
    List<OmsOrderAndShopInfoVo>  selectOrderAndShop(@RequestParam("token") String token,@RequestParam("orderStatus") Integer orderStatus);
    /**
     * @Author Lee
     * @Description
     *  根据订单编号查询订单详情
     * @Param  * @param orderSn
     * @Return com.aaa.lee.app.domain.OmsOrder
     * @Date 2019/11/29
     */
    @PostMapping("/selectOrderInfo")
    OmsOrder selectOrderInfo(@RequestParam("orderSn") String  orderSn);

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
}
