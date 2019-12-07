package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.SkuStock;
import tk.mybatis.mapper.common.Mapper;

public interface SkuStockMapper extends Mapper<SkuStock> {

    int updateSkuStock(SkuStock skuStock);

    int updateBackSkuStock(SkuStock skuStock);
}