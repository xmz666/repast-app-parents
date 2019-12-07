package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.MemberRuleSetting;
import com.aaa.lee.app.mapper.MemberRuleSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/12/02 11:13
 * @Description
 **/
@Service
public class MemberRuleSettingService extends BaseService<MemberRuleSetting> {
    @Autowired
    private MemberRuleSettingMapper memberRuleSettingMapper;

    @Override
    public Mapper<MemberRuleSetting> getMapper() {
        return memberRuleSettingMapper;
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      根据店铺Id查询用户积分和成长值规则
     * @Param [shopId]
     * @Return com.aaa.lee.app.domain.MemberRuleSetting
     * @Date 2019/12/2
     */
    public MemberRuleSetting selectMemberRuleSetting(Long shopId,Integer type){
        MemberRuleSetting memberRuleSetting = new MemberRuleSetting();
        memberRuleSetting.setShopId(shopId).setType(type);
        try {
            MemberRuleSetting memberRuleSetting1 = super.selectOne(memberRuleSetting);
            if(memberRuleSetting1 != null ){
                return memberRuleSetting1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
