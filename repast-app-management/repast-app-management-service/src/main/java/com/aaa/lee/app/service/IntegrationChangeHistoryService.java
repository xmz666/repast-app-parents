package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.IntegrationChangeHistory;
import com.aaa.lee.app.mapper.IntegrationChangeHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/11/27 11:51
 * @Description
 **/
@Service
public class IntegrationChangeHistoryService extends BaseService<IntegrationChangeHistory> {
    @Autowired
    private IntegrationChangeHistoryMapper integrationChangeHistoryMapper;

    @Override
    public Mapper<IntegrationChangeHistory> getMapper() {
        return integrationChangeHistoryMapper;
    }
    /**
     * @Author Zhang Wei
     * @Description
     *      修改积分变化历史记录
     * @Param [integrationChangeHistory]
     * @Return java.lang.Boolean
     * @Date 2019/11/27
     */
    public Boolean updateIntChangeHistory(IntegrationChangeHistory integrationChangeHistory){
        try {
            Integer updateResult = super.update(integrationChangeHistory);
            if(updateResult>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
