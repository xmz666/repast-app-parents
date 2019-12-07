package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.*;
import com.aaa.lee.app.mapper.OmsCartItemMapper;
import com.aaa.lee.app.mapper.OmsOrderItemMapper;
import com.aaa.lee.app.mapper.OmsOrderMapper;
import com.aaa.lee.app.mapper.PmsSkuStockMapper;
import com.aaa.lee.app.utils.OrderCodeFactory;
import com.aaa.lee.app.vo.OmsOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.aaa.lee.app.staticstatus.StaticProperties.*;

/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/22 16:40
 * @Description
 **/
@Service
public class OrderService extends BaseService<OmsOrder> {
    private Exception exception = new Exception("操作失败");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    private OmsOrderMapper omsOrderMapper;
    @Autowired
    private OmsOrderItemMapper omsOrderItemMapper;
    @Autowired
    private OmsCartItemMapper omsCartItemMapper;
    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;

    @Override
    public Mapper<OmsOrder> getMapper() {
        return omsOrderMapper;
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      根据订单编号查询订单信息
     * @Param [orderSn]
     * @Return com.aaa.lee.app.domain.Order
     * @Date 2019/11/28
     */
    public OmsOrder getOrderByOrderOrderSn(String orderSn){
        OmsOrder order = new OmsOrder();
        order.setOrderSn(orderSn);
        try {
            OmsOrder order1 = super.selectOne(order);
            if(order1 != null){
                return order1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author Lee
     * @Description /**
     * 创建订单
     * 在此页面需要获取到用户id，商铺id，订单编号，提交时间，用户名，订单总金额，应付金额，
     * status（订单状态）：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭
     * pay_type（支付方式）：0->未支付；1->支付宝；2->微信     获取支付方式是为了退款的时候原路返回
     * 同时在此页面还应该改变购物车的状态码由待清空状态 ：0
     * 转换为清空用户不可见状态：1  （更改为1是为了顾客在次进行购买的时候还能显示这些商品）
     * @Param * @param omsOrder
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addOrder(List<OmsOrderVo> omsOrder,MemberRuleSettingService memberRuleSettingService,MemberService memberService) {
        OmsOrderVo omsor1 = omsOrder.get(0);
        String token = omsor1.getToken();
        if (token != null) {
            Long memberId = memberService.getMemberId(token);
            OmsOrder omsOrder1 = new OmsOrder();
            OmsOrderItem omsOrderItem = new OmsOrderItem();
            OmsCartItem omsCartItem = new OmsCartItem();
            PmsSkuStock pmsSkuStock = new PmsSkuStock();
            Date date = new Date();
            long lTime = date.getTime();
            String orderCode = OrderCodeFactory.getOrderCode(lTime);
            try {
                Date date1 = new Date();
                String formatDate = null;
                //HH表示24小时制；
                DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formatDate = dFormat.format(date1);
                SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date strD = null;
                try {
                    strD = lsdStrFormat.parse(formatDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //获取到用户id，商铺id，订单编号，提交时间，用户名，订单总金额，应付金额，
                Long shopId = omsor1.getShopId();
                Long productId = omsor1.getProductId();
                BigDecimal payAmount = omsor1.getPayAmount();
                BigDecimal totalAmount = omsor1.getTotalAmount();
                int orderStatus = omsor1.getOrderStatus();
                Long cartId = omsor1.getCartId();
                String receiverName = omsor1.getReceiverName();
                String receiverPhone = omsor1.getReceiverPhone();

//查询店铺积分和成长规则
                MemberRuleSetting integrationSetting = memberRuleSettingService.selectMemberRuleSetting(shopId, STATUS_SHOP_INTEGRATION_RULE);
                MemberRuleSetting growthSetting = memberRuleSettingService.selectMemberRuleSetting(shopId, STATUS_SHOP_GROWTH_RULE);
                if(integrationSetting != null && growthSetting !=null) {
                    //获取每消费多少元增加一点积分
                    BigDecimal consumePerPoint = integrationSetting.getConsumePerPoint();
                    //获取获取积分的最低消费
                    BigDecimal lowOrderAmount = integrationSetting.getLowOrderAmount();
                    //获取每单获取最大积分数
                    Integer maxPointPerOrder = integrationSetting.getMaxPointPerOrder();
                    //获取每消费多少元增加一点成长值
                    BigDecimal consumePerPoint1 = growthSetting.getConsumePerPoint();
                    //获取获取成长值的最低消费
                    BigDecimal lowOrderAmount1 = growthSetting.getLowOrderAmount();
                    //获取每单获取最大成长值
                    Integer maxPointPerOrder1 = growthSetting.getMaxPointPerOrder();
                    if(payAmount != null && lowOrderAmount != null && lowOrderAmount1 != null){
                        //判断支付金额是否大于获取积分的最低消费
                        if(payAmount.compareTo(lowOrderAmount)>0) {
                            //获取该订单可获取的积分数(向下取整)
                            String s = payAmount.divide(consumePerPoint)
                                    .setScale(0, BigDecimal.ROUND_DOWN)
                                    .toString();
                            Integer integration = Integer.parseInt(s);
                            //判断积分是否大于每单最大积分
                            if(integration < maxPointPerOrder){
                                omsOrder1.setIntegration(integration);
                            }else{
                                omsOrder1.setIntegration(maxPointPerOrder);
                            }
                        }
                        //判断支付金额是否大于获取成长值的最低消费
                        if(payAmount.compareTo(lowOrderAmount1)>0){
                            //获取该订单可获取的成长值(向下取整)
                            String s = payAmount.divide(consumePerPoint1)
                                    .setScale(0, BigDecimal.ROUND_DOWN)
                                    .toString();
                            Integer growth = Integer.parseInt(s);
                            if(growth < maxPointPerOrder1){
                                omsOrder1.setGrowth(growth);
                            }else{
                                omsOrder1.setGrowth(maxPointPerOrder1);
                            }
                        }
                    }
                }
                omsOrder1.setShopId(shopId)
                        .setMemberId(memberId)
                        .setOrderSn(orderCode)
                        .setPayAmount(payAmount)
                        .setCreateTime(strD)
                        .setReceiverName(receiverName)
                        .setReceiverPhone(receiverPhone)
                        .setDeleteStatus(DELETE_STATUS_EXIST)
                        .setOrderStatus(orderStatus)
                        .setTotalAmount(totalAmount);
                int insert = omsOrderMapper.insert(omsOrder1);
                for (OmsOrderVo omsor : omsOrder) {
                    /*
                     获取订单中所包含的商品的信息，存入到数据库中
                     订单编号，商品id，商品图片，商品名字，销售价格，购买数量
                     */
                    Integer shoppingWay = omsor.getShoppingWay();
                    if (shoppingWay == 3) {
                        BigDecimal price = omsor.getProductPrice();
                        String productName = omsor.getProductName();
                        Integer productQuantity = omsor.getProductQuantity();
                        pmsSkuStock.setProductId(productId);
                        PmsSkuStock pmsSkuStock1 = pmsSkuStockMapper.selectOne(pmsSkuStock);
                        if (pmsSkuStock1.getStock() > productQuantity) {
                            omsOrderItem.setProductId(productId)
                                    .setProductName(productName)
                                    .setProductPrice(price)
                                    .setProductQuantity(productQuantity)
                                    .setOrderId(omsOrder1.getId())
                                    .setOrderSn(orderCode);
                            int insert1 = omsOrderItemMapper.insert(omsOrderItem);
                            if (insert1 > 0) {
                                omsCartItem.setMemberId(memberId);
                                omsCartItem.setId(cartId);
                                omsCartItem.setShopId(shopId);
                                omsCartItem.setProductId(productId);
                                omsCartItem.setDeleteStatus(DELETE_STATUS_NOEXIST);
                                int updaCart = omsCartItemMapper.updateCartStatus(omsCartItem);
                                if (updaCart > 0) {
                                    continue;
                                }
                                return false;
                            }
                        } else {
                             throw  exception;
                        }
                    } else {
                        BigDecimal price = omsor.getProductPrice();
                        String productName = omsor.getProductName();
                        Integer productQuantity = omsor.getProductQuantity();
                        omsOrderItem.setProductId(productId)
                                .setProductName(productName)
                                .setProductPrice(price)
                                .setOrderId(omsOrder1.getId())
                                .setProductQuantity(productQuantity)
                                .setOrderSn(orderCode);
                        int insert2 = omsOrderItemMapper.insert(omsOrderItem);
                        if (insert2 > 0) {
                            omsCartItem.setMemberId(memberId);
                            omsCartItem.setId(cartId);
                            omsCartItem.setShopId(shopId);
                            omsCartItem.setProductId(productId);
                            omsCartItem.setDeleteStatus(1);
                            int updaCart = omsCartItemMapper.updateCartStatus(omsCartItem);
                            if (updaCart > 0) {
                                continue;
                            }
                            return false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      修改订单为删除状态
     * @Param [orderSn, token]
     * @Return java.lang.Boolean
     * @Date 2019/12/6
     */
    public Boolean deleteOrder(String orderSn,String token){
        if(token != null){
            OmsOrder order = getOrderByOrderOrderSn(orderSn);
            Date date = new Date();
            String format = simpleDateFormat.format(date);
            try {
                Date formatDate = simpleDateFormat.parse(format);
                order.setModifyTime(formatDate);
                order.setDeleteStatus(DELETE_STATUS_NOEXIST);
                Integer updateResult = super.update(order);
                if(updateResult > 0 ){
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
