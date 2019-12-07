package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.OrderItem;
import com.aaa.lee.app.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/11/23 20:23
 * @Description
 **/
@Service
public class WMOrderItemService extends BaseService<OrderItem> {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Mapper<OrderItem> getMapper() {
        return orderItemMapper;
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      根据订单编号 查询对应订单商品信息
     * @Param [orderItem]
     * @Return java.util.List<com.aaa.lee.app.domain.OrderItem>
     * @Date 2019/11/23
     */
    public List<OrderItem> getProductByOrderSn(OrderItem orderItem){
        try {
            List<OrderItem> orderItem1 = super.selectDomain(orderItem);
            if(orderItem1.size()>0){
                return orderItem1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
