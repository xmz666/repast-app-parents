package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.OmsCartItem;
import com.aaa.lee.app.domain.SubmitOrderVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OmsCartItemMapper extends Mapper<OmsCartItem> {
    int updateCartStatus(OmsCartItem omsCartItem);
    List<SubmitOrderVO> getOmsCartltem(@Param("memberId") Long memberId, @Param("shopId") Long shopId);

    void updataDeleteStatus(OmsCartItem omsCartItem);
}