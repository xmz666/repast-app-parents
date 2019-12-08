package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.OrderItem;
import com.aaa.lee.app.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2019/11/23
 * @Description
 **/
@Service
public class OrderItemForReturnApplyService extends BaseService<OrderItem> {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Mapper<OrderItem> getMapper() {
        return orderItemMapper;
    }

    /**
     * @Author Administrator
     * @Description 
     *        通过订单id在订单明细表中查询要退货的商品列表
     * @Date 2019/11/23
     * @Param [orderId] 
     * @return java.util.List<com.aaa.app.domain.OrderItem>
     **/
    public List<OrderItem> getProductListByOrderId(Long orderId){
        return orderItemMapper.selectProductListByOrderId(orderId);
    }
}
