package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.OrderSetting;
import com.aaa.lee.app.mapper.OrderSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/12/02 10:55
 * @Description
 **/
@Service
public class OrderSettingService extends BaseService<OrderSetting> {
    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public Mapper<OrderSetting> getMapper() {
        return orderSettingMapper;
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      根据店铺编号查询店铺的订单设置
     * @Param [shopId]
     * @Return com.aaa.lee.app.domain.OrderSetting
     * @Date 2019/12/2
     */
    public OrderSetting selectOrderSetting(Long shopId){
        OrderSetting orderSetting = new OrderSetting();
        orderSetting.setShopId(shopId);
        try {
            OrderSetting orderSetting1 = super.selectOne(orderSetting);
            if(orderSetting1 != null){
                return orderSetting1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
