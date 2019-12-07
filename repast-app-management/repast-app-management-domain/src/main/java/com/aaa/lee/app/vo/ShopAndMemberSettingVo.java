package com.aaa.lee.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/11/27 14:50
 * @Description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ShopAndMemberSettingVo implements Serializable {
    private Long shopId;
    private Integer normalOrderOvertime;
    private Integer confirmOvertime;
    private Integer finishOvertime;
    private Integer commentOvertime;
    private Integer continueSignPoint;
    private Integer consumePerPoint;
    private Integer lowOrderAmount;
    private Integer maxPointPerOrder;
}
