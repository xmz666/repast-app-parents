package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.ShopInfo;
import tk.mybatis.mapper.common.Mapper;

public interface ShopInformationMapper extends Mapper<ShopInfo> {
    /**
     * @Author Administrator
     * @Description
     *        通过shopId获取商店名作为退货申请的收货人
     * @Date 2019/11/23
     * @Param [shopId]
     * @return java.lang.String
     **/
    String selectShopNameByShopId(Long shopId);
}