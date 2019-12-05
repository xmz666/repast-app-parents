package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartItemAndOrderVoMapper {
    List<CartItemAndOrderVO>selctCartItemAndOrder(@Param("memberId") Long memberId,@Param("shopId") Long shopId);


}
