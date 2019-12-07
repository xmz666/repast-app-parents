package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.OmsOrderItem;
import com.aaa.lee.app.mapper.OmsOrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/28 09:19
 * @Description
 **/
@Service
public class OrderItemInfo extends BaseService<OmsOrderItem> {
    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;
    @Override
    public Mapper<OmsOrderItem> getMapper() {
        return omsOrderItemMapper;
    }
    /**
     * @Author Lee
     * @Description
     * 根据订单编号查询订单详情
     * @Param  * @param orderSn
     * @Return java.util.List<com.aaa.lee.app.domain.OmsOrderItem>
     * @Date 2019/11/29
     */
    public List<OmsOrderItem>  selectOrderItemInfo(String orderSn){
        OmsOrderItem omsOrderItem =new OmsOrderItem();
        omsOrderItem.setOrderSn(orderSn);
        try {
            List<OmsOrderItem> omsOrderItems = super.selectDomain(omsOrderItem);
            if (omsOrderItems.size()>0){
                return  omsOrderItems;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
