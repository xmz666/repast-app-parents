package com.aaa.lee.app.mapper;

import com.aaa.lee.app.domain.Member;
import com.aaa.lee.app.vo.MemberAndLevelVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface MemberMapper extends Mapper<Member> {

    MemberAndLevelVo getMemberAndLevel(@Param("memberId") Long memberId, @Param("shopId") Long shopId);

    int updateMember(MemberAndLevelVo memberAndLevelVo);
}