package com.aaa.lee.app.fallback;
import com.aaa.lee.app.domain.*;
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
            @Override
            public Boolean addCart(Map<String,Object> data) {
                System.out.println("测试更新购物车熔断数据");
                return null;
            }

            @Override
            public List<Cart> selectCart(Map<String,Object> data) {
                System.out.println("测试查询购物车熔断数据");
                return null;
            }

            @Override
            public Boolean deleteCartByShopId(Map<String,Object> data) {
                System.out.println("测试清空购物车熔断数据");
                return null;
            }
            @Override
            public List<SubmitOrderVO> getSubmitOrder(@Param("shopId") Long shopId,@Param("token")String token) {
                System.out.println("测试中转提交订单页面熔断数据");
                return null;
            }

            @Override
            public int setOrder(List<SubmitOrderVO> submitOrderVO,String token) {
                System.out.println("测试创建订单页面熔断数据");
                return 0;
            }

            @Override
            public PmsProductVO getIntegralGoods(Long productId,String token) {
                System.out.println("测试商品id获取积分商城具体商品数据");
                return null;
            }

            @Override
            public int setIntegralOrder(PmsProductVO productVO,String token) {
                System.out.println("生成积分订单熔断测试");
                return 0;
            }

            @Override
            public Boolean addOrder( Map<String,Object> data) {
                System.out.println("添加订单熔断数据");
                return null;
            }

            @Override
            public List<OmsOrderAndShopInfoVo>  selectOrderAndShop(Long id) {
                System.out.println("查询订单熔断");
                return null;
            }

            @Override
            public List<CartItemAndOrderVO> selectCartItemInfo(Long memberId, Long shopId) {
                System.out.println("查询购物车商品详情");
                return null;
            }

            @Override
            public Boolean deleteOrder(String orderSn, String token) {
                System.out.println("删除订单熔断");
                return null;
            }

            @Override
            public Boolean WMpaySuccess(String  orderSn,String token) {
                System.out.println("测试付款成功，根据订单编号修改订单数据");
                return null;
            }

            @Override
            public Boolean deliverySuccess(String  orderSn,String token) {
                System.out.println("测试发货成功，根据订单编号修改订单数据");
                return null;
            }

            @Override
            public Boolean reveiveSuccess(String orderSn,String token) {
                System.out.println("测试收货成功，根据订单编号修改订单数据");
                return null;
            }

            @Override
            public Boolean WMcancleOrder(String orderSn,String token) {
                System.out.println("测试取消订单，根据订单编号修改订单数据");
                return null;
            }

            @Override
            public Map<String, Object> WMpay(String orderSn, String openid, Float amount) {
                System.out.println("测试微信支付");
                return null;
            }

            @Override
            public void wxNotify() throws Exception {
                System.out.println("测试微信支付回调");
            }

            @Override
            public Order getOrderByOrderOrderSn(String orderSn) {
                return null;
            }

            @Override
            public Boolean paySuccess(String orderSn, String token) {
                return null;
            }

            @Override
            public Boolean cancleOrder(String orderSn, String token) {
                return null;
            }
        };
        return repastService;
    }
}
