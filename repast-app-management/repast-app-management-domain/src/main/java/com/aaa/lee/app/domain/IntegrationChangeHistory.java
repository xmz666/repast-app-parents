package com.aaa.lee.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "ums_integration_change_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class IntegrationChangeHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 创建记录时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 积分变化类型 1. 外卖下单获取积分，2.食堂下单获取积分，3. 管理员修改 ，4. 积分兑换消耗积分
     */
    @Column(name = "change_type")
    private Integer changeType;

    /**
     * 积分改变数量
     */
    @Column(name = "change_count")
    private Integer changeCount;

    /**
     * 操作人员
     */
    @Column(name = "operate_man")
    private String operateMan;

    /**
     * 操作备注
     */
    @Column(name = "operate_note")
    private String operateNote;

    /**
     * 积分来源：0->购物奖励；1->管理员修改；2->购物消费
     */
    @Column(name = "source_type")
    private Integer sourceType;
}