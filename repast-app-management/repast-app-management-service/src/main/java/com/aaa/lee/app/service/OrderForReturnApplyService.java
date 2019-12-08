package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.Order;
import com.aaa.lee.app.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author Administrator
 * @Date 2019/11/23
 * @Description
 **/
@Service
public class OrderForReturnApplyService extends BaseService<Order> {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Mapper<Order> getMapper() {
        return orderMapper;
    }

    /**
     * @Author Administrator
     * @Description
     *        根据订单Id查询要退货的订单信息
     * @Date 2019/11/23
     * @Param [orderId]
     * @return com.aaa.app.domain.Order
     **/
    public Order getOrderInfoByOrderId(Long orderId){
        return orderMapper.selectOrderInfoByOrderId(orderId);
    }
}
