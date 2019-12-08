package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.ReturnApply;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ReturnApplyMapper extends Mapper<ReturnApply> {
    /**
     * @Author Administrator
     * @Description
     *        通过订单id修改退款申请状态
     * @Date 2019/11/23
     * @Param [orderId, status]
     * @return java.lang.Integer
     **/
    Integer updateStatusByOrderId(@Param("orderId") Long orderId, @Param("status") Integer status);

    /**
     * @Author Administrator
     * @Description
     *        批量添加退货申请
     * @Date 2019/11/23
     * @Param [returnApplyList]
     * @return java.lang.Integer
     **/
    Integer batchInsertReturnApply(@Param("returnApplyList") List<ReturnApply> returnApplyList);
}