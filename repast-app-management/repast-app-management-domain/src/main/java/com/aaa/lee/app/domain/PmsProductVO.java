package com.aaa.lee.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PmsProductVO implements Serializable {
    /**
     * 商品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pmsId;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 商品名字
     */
    private String username;

    /**
     * 图片路径
     */
    private String pic;


    /**
     * 上架状态：0->下架；1->上架
     */
    @Column(name = "publish_status")
    private Integer publishStatus;

    /**
     * 价格
     */
    private BigDecimal price;


    /**
     * 商品描述
     */
    private String description;
}