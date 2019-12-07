package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.OrderSetting;
import com.aaa.lee.app.mapper.OrderSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/23 15:42
 * @Description
 **/
@Service
public class CanteenOrderSettingService extends BaseService<OrderSetting> {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public Mapper<OrderSetting> getMapper() {
        return orderSettingMapper;
    }

    /**
     * @Author SGZ
     * @Description
     *      根据shopId查询对应商家订单设置信息
     * @Param  * @param shopId
     * @Return com.aaa.lee.app.domain.OrderSetting
     * @Date 2019/11/23
     */
    public OrderSetting getOrderSettingByShopId(Long shopId){
        try {
            OrderSetting orderSetting = super.get(shopId);
            if(orderSetting != null){
                return orderSetting;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
