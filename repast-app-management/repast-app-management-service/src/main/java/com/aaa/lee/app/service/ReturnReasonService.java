package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.ReturnReason;
import com.aaa.lee.app.mapper.ReturnReasonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2019/11/22
 * @Description
 **/
@Service
public class ReturnReasonService extends BaseService<ReturnReason> {

    @Autowired
    private ReturnReasonMapper returnReasonMapper;

    @Override
    public Mapper<ReturnReason> getMapper() {
        return returnReasonMapper;
    }

    /**
     * @Author Administrator
     * @Description
     *        根据店铺ID获取退货原因列表
     * @Date 2019/11/22
     * @Param [shopId]
     * @return java.util.List<com.aaa.app.domain.ReturnReason>
     **/
    public List<ReturnReason> getReturnReasonListByShopId(Long shopId) {
        return returnReasonMapper.selectReturnReasonListByShopId(shopId);
    }

    /**
     * @Author Administrator
     * @Description
     *        获取可用原因列表
     * @Date 2019/11/22
     * @Param []
     * @return java.util.List<com.aaa.app.domain.ReturnReason>
     **/
    public List<ReturnReason> getReturnReasonList() {
        return returnReasonMapper.selectReturnReasonList();
    }
}
