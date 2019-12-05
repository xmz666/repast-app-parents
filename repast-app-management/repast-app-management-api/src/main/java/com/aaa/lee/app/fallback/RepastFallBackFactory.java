package com.aaa.lee.app.fallback;
import com.aaa.lee.app.domain.Member;
import com.aaa.lee.app.domain.MemberReceiveAddress;
import com.aaa.lee.app.domain.OmsOrder;
import com.aaa.lee.app.domain.OmsOrderItem;
import com.aaa.lee.app.service.IRepastService;
import com.aaa.lee.app.vo.CartItemAndOrderVO;
import com.aaa.lee.app.vo.OmsOrderAndShopInfoVo;
import com.aaa.lee.app.vo.OmsOrderVo;
import feign.Param;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/21 19:44
 * @Description
 **/
@Component
public class RepastFallBackFactory implements FallbackFactory<IRepastService> {
    @Override
    public IRepastService create(Throwable throwable) {
        IRepastService repastService = new IRepastService() {
            @Override
            public Boolean doLogin(Member member) {
                System.out.println("测试登录熔断数据");
                return null;
            }

            @Override
            public List<MemberReceiveAddress> getMemberReceiveAddress() {
                System.out.println("测试收货地址熔断数据");
                return null;
            }

            @Override
            public Boolean addOrder( List<OmsOrderVo> omsOrder) {
                System.out.println("添加订单熔断数据");
                return null;
            }

            @Override
            public List<OmsOrderAndShopInfoVo>  selectOrderAndShop(String token,Integer orderStatus) {
                System.out.println("查询订单熔断");
                return null;
            }

            @Override
            public OmsOrder selectOrderInfo(String orderSn) {
                System.out.println("查询订单详情");
                return null;
            }

            @Override
            public List<OmsOrderItem> selectOrderItemInfo(String OrderSn) {
                System.out.println("查询订单商品详情");
                return null;
            }

            @Override
            public List<CartItemAndOrderVO> selectCartItemInfo(String token,Long shopId) {
                System.out.println("查询购物车商品详情");
                return null;
            }

        };
        return repastService;
    }
}
