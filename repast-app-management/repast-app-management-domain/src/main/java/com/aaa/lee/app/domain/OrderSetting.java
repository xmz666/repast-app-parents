package com.aaa.lee.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "oms_order_setting")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderSetting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 正常订单超时时间(分)
     */
    @Column(name = "normal_order_overtime")
    private Integer normalOrderOvertime;

    /**
     * 发货后自动确认收货时间（天）
     */
    @Column(name = "confirm_overtime")
    private Integer confirmOvertime;

    /**
     * 自动完成交易时间，不能申请售后（天）
     */
    @Column(name = "finish_overtime")
    private Integer finishOvertime;

    /**
     * 订单完成后自动好评时间（天）
     */
    @Column(name = "comment_overtime")
    private Integer commentOvertime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置店铺ID
     *
     * @param shopId 店铺ID
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取正常订单超时时间(分)
     *
     * @return normal_order_overtime - 正常订单超时时间(分)
     */
    public Integer getNormalOrderOvertime() {
        return normalOrderOvertime;
    }

    /**
     * 设置正常订单超时时间(分)
     *
     * @param normalOrderOvertime 正常订单超时时间(分)
     */
    public void setNormalOrderOvertime(Integer normalOrderOvertime) {
        this.normalOrderOvertime = normalOrderOvertime;
    }

    /**
     * 获取发货后自动确认收货时间（天）
     *
     * @return confirm_overtime - 发货后自动确认收货时间（天）
     */
    public Integer getConfirmOvertime() {
        return confirmOvertime;
    }

    /**
     * 设置发货后自动确认收货时间（天）
     *
     * @param confirmOvertime 发货后自动确认收货时间（天）
     */
    public void setConfirmOvertime(Integer confirmOvertime) {
        this.confirmOvertime = confirmOvertime;
    }

    /**
     * 获取自动完成交易时间，不能申请售后（天）
     *
     * @return finish_overtime - 自动完成交易时间，不能申请售后（天）
     */
    public Integer getFinishOvertime() {
        return finishOvertime;
    }

    /**
     * 设置自动完成交易时间，不能申请售后（天）
     *
     * @param finishOvertime 自动完成交易时间，不能申请售后（天）
     */
    public void setFinishOvertime(Integer finishOvertime) {
        this.finishOvertime = finishOvertime;
    }

    /**
     * 获取订单完成后自动好评时间（天）
     *
     * @return comment_overtime - 订单完成后自动好评时间（天）
     */
    public Integer getCommentOvertime() {
        return commentOvertime;
    }

    /**
     * 设置订单完成后自动好评时间（天）
     *
     * @param commentOvertime 订单完成后自动好评时间（天）
     */
    public void setCommentOvertime(Integer commentOvertime) {
        this.commentOvertime = commentOvertime;
    }
}