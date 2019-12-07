package com.aaa.lee.app.service;
import com.aaa.lee.app.mapper.CartItemAndOrderVoMapper;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/29 10:34
 * @Description
 **/
@Service
public class CartItemAndOrderService {
    @Autowired
    private CartItemAndOrderVoMapper cartItemAndOrderVoMapper;
    /**
     * @Author Lee
     * @Description
     * 根据用户id和店铺id查询购物车商品详情
     * @Param  * @param memberId
     * @param shopId
     * @Return java.util.List<com.aaa.lee.app.vo.CartItemAndOrderVO>
     * @Date 2019/11/29
     */

    public List<CartItemAndOrderVO> selectCartItemInfo(MemberService memberService,Long shopId,String token){
        if(token!=null){
            Long memberId = memberService.getMemberId(token);
            List<CartItemAndOrderVO> cartItemAndOrderVOS = cartItemAndOrderVoMapper.selctCartItemAndOrder(memberId,shopId);
        if (cartItemAndOrderVOS.size()>0){
            return cartItemAndOrderVOS;
        }
        }
        return null;
    }

}
