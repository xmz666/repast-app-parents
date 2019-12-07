package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.OrderItem;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderItemMapper extends Mapper<OrderItem> {

    List<OrderItem> getOrderItemByOrderSn(String orderSn);
}