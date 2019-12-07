package com.aaa.lee.app.mapper;

import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OmsOrderAndShopInfoVoMapper extends Mapper<OmsOrderAndShopInfoVo> {
  List<OmsOrderAndShopInfoVo> getOrderAndShopInfo(@Param("memberId") Long memberId, @Param("orderStatus") Integer orderStatus);
}