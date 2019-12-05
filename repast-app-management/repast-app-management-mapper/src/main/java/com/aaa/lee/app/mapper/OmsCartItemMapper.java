package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.OmsCartItem;
import tk.mybatis.mapper.common.Mapper;

public interface OmsCartItemMapper extends Mapper<OmsCartItem> {
    int updateCartStatus(OmsCartItem omsCartItem);
}