package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.Order;
import com.aaa.lee.app.vo.ShopAndMemberSettingVo;
import tk.mybatis.mapper.common.Mapper;

public interface OrderMapper extends Mapper<Order> {
    Order selectOrderByOrderSn(String orderSn);
    ShopAndMemberSettingVo getShopSettingAndMbrRule(Long shopId, Integer type);

    /**
     * @Author Administrator
     * @Description
     *        根据订单Id查询要退货的订单信息
     * @Date 2019/11/23
     * @Param [orderId]
     * @return com.aaa.app.domain.Order
     **/
    Order selectOrderInfoByOrderId(Long orderId);
}