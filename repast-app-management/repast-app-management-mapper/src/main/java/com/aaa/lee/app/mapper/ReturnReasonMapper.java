package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.ReturnReason;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReturnReasonMapper extends Mapper<ReturnReason> {

    /**
     * @Author Administrator
     * @Description
     *        根据店铺ID获取退货原因列表
     * @Date 2019/11/22
     * @Param [shopId]
     * @return java.util.List<com.aaa.app.domain.ReturnReason>
     **/
    List<ReturnReason> selectReturnReasonListByShopId(@Param("shopId") Long shopId);

    /**
     * @Author Administrator
     * @Description
     *        获取可用原因列表
     * @Date 2019/11/22
     * @Param []
     * @return java.util.List<com.aaa.app.domain.ReturnReason>
     **/
    List<ReturnReason> selectReturnReasonList();

}