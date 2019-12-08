package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.OrderItem;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderItemMapper extends Mapper<OrderItem> {

    List<OrderItem> getOrderItemByOrderSn(String orderSn);

    /**
     * @Author Administrator
     * @Description
     *        通过订单id在订单明细表中查询要退货的商品列表
     * @Date 2019/11/23
     * @Param [orderId]
     * @return java.util.List<com.aaa.app.domain.OrderItem>
     **/
    List<OrderItem> selectProductListByOrderId(Long orderId);
}