package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.mapper.OmsOrderAndShopInfoVoMapper;
import com.aaa.lee.app.mapper.OmsOrderMapper;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/28 08:56
 * @Description
 **/
@Service
public class OrderListService extends BaseService<OmsOrderAndShopInfoVo> {
    @Override
    public Mapper<OmsOrderAndShopInfoVo> getMapper() {
        return omsOrderAndShopInfoVoMapper;
    }


    @Autowired
    private OmsOrderAndShopInfoVoMapper omsOrderAndShopInfoVoMapper;
    /**
     * @Author Lee
     * @Description
     *  根据用户id所有订单详情
     * @Param  * @param null
     * @Return
     * @Date 2019/11/26
     */
    public List<OmsOrderAndShopInfoVo> selectOrderAndShop(MemberService memberService,String token,Integer orderStatus){
        if(token!=null) {
            Long memberId = memberService.getMemberId(token);
            List<OmsOrderAndShopInfoVo> orderAndShopInfo = omsOrderAndShopInfoVoMapper.getOrderAndShopInfo(memberId,orderStatus);
            if (orderAndShopInfo.size() > 0) {
                return orderAndShopInfo;
            }
        }
        return  null;
    }




}
