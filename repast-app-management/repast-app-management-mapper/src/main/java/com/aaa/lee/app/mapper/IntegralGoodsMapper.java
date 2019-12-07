package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.*;

/**
 * TODO
 *
 * @data 2019/11/25 22:36
 * @project repast-app-parent
 * @declaration:
 */
public interface IntegralGoodsMapper {
    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    PmsProductVO getPmsProductVO(Long productId);


    /**
     * 创建积分订单表
     */
    int setIntegralOrder(OmsOrder omsOrder);

    /**
     * 创建同积分表
     */
    int setIntegralOrderItem(OmsOrderItem omsOrderItem);

    /**
     * 创建积分变化历史记录表
     */
    int setIntegralChangeHistory(UmsIntegrationChangeHistory integrationChangeHistory);

    /**
     * 根据memberid查询用户名
     * @param memberId
     * @return
     */
    Member getMemberNameById(Long memberId);


}
