package com.aaa.lee.app.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/11/28 11:31
 * @Description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MemberAndLevelVo implements Serializable {
    private Long id;
    private Long shopId;
    private Long memberLevelId;
    private Integer integration;
    private Integer growth;
    private Integer historyIntegration;
    private Integer commentGrowthPoint;
}
