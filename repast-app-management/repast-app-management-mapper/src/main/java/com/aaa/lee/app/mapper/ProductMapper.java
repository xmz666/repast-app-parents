package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.Product;
import tk.mybatis.mapper.common.Mapper;

public interface ProductMapper extends Mapper<Product> {

    int updateProStock(Product product);

    int updateBackProStock(Product product);

}