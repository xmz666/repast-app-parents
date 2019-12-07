package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.Comment;
import com.aaa.lee.app.domain.Order;
import com.aaa.lee.app.domain.OrderItem;
import com.aaa.lee.app.domain.OrderSetting;
import com.aaa.lee.app.mapper.OrderMapper;

import com.aaa.lee.app.utils.PayUtil;
import com.aaa.lee.app.utils.delay.DelayCancelOrderTask;
import com.aaa.lee.app.utils.delay.DelayCancelOrderTaskManager;
import com.aaa.lee.app.vo.MemberAndLevelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.aaa.lee.app.staticstatus.StaticProperties.*;
import static com.aaa.lee.app.utils.PayUtil.*;


/**
 * @Company AAA软件教育
 * @Author Zhang Wei
 * @Date Create in 2019/11/22 23:36
 * @Description
 **/
@Service
public class WMOrderService extends BaseService<Order> {
    private ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(4);
    private Exception exception = new Exception("操作失败");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Mapper<Order> getMapper() {
        return orderMapper;
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      根据订单编号查询订单信息
     * @Param [orderSn]
     * @Return com.aaa.lee.app.domain.Order
     * @Date 2019/11/28
     */
    public Order getOrderByOrderOrderSn(String orderSn){
        Order order = new Order();
        order.setOrderSn(orderSn);
        try {
            Order order1 = super.selectOne(order);
            if(order1 != null){
                return order1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      付款成功，根据订单编号修改订单数据
     *      付款成功15分钟后，修改状态为已发货
     * @Param [orderSn token]
     * @Return java.lang.Boolean
     * @Date 2019/11/28
     */
    public Boolean WMpaySuccess(String  orderSn,String token){
        if(token != null && "" != token){
            Order order = getOrderByOrderOrderSn(orderSn);
            try {
                Date date = new Date();
                String format = simpleDateFormat.format(date);
                Date formatDate = simpleDateFormat.parse(format);
                //修改订单状态为待发货，支付时间，修改时间
                order.setPayType(STATUS_ORDER_WXPAID)
                        .setStatus(STATUS_ORDER_PAID)
                        .setPaymentTime(formatDate)
                        .setModifyTime(formatDate);
                Integer updateResult = super.update(order);
                if(updateResult>0){
                    mScheduledExecutorService.schedule(new Runnable() {
                        @Override
                        public void run() {
                            Order orderByOrderOrderSn = getOrderByOrderOrderSn(orderSn);
                            //判断该订单是否已发货
                            if(orderByOrderOrderSn.getStatus().equals(STATUS_ORDER_PAID)){
                                Date date = new Date();
                                String format = simpleDateFormat.format(date);
                                try {
                                    Date formatDate = simpleDateFormat.parse(format);
                                    //修改订单状态为已发货，修改时间
                                    order.setModifyTime(formatDate)
                                            .setDeliveryTime(formatDate)
                                            .setStatus(STATUS_ORDER_SENT)
                                            .setConfirmStatus(STATUS_ORDER_NOT_CONFIRM);
                                    Integer updateResult = WMOrderService.super.update(order);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },15, TimeUnit.MINUTES);
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
    /**
     * @Author Zhang Wei
     * @Description
     *      发货成功，根据订单编号修改订单信息
     *      设置定时任务，定时自动确认收货
     * @Param  * @param orderSn token OrderSettingService
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    public Boolean deliverySuccess(String  orderSn,String token,OrderSettingService orderSettingService){
        if(token != null && "" != token) {
            Order order = getOrderByOrderOrderSn(orderSn);
            Long shopId = order.getShopId();
            //获取店铺自动确认收货时间
            OrderSetting orderSetting = orderSettingService.selectOrderSetting(shopId);
            Integer confirmOvertime = orderSetting.getConfirmOvertime();
            try {
                Date date = new Date();
                String format = simpleDateFormat.format(date);
                Date formatDate = simpleDateFormat.parse(format);
                //修改订单发货时间，修改时间，订单状态为已发货
                order.setDeliveryTime(formatDate)
                        .setModifyTime(formatDate)
                        .setStatus(STATUS_ORDER_SENT)
                        .setConfirmStatus(STATUS_ORDER_NOT_CONFIRM);
                Integer updateResult = WMOrderService.super.update(order);
                if (updateResult > 0) {
                    //如果逾期未确认收货，自动确认收货
                    mScheduledExecutorService.schedule(new Runnable() {
                        @Override
                        public void run() {
                            Order order = getOrderByOrderOrderSn(orderSn);
                            if (order.getStatus() .equals(STATUS_ORDER_SENT) ) {
                                Date date = new Date();
                                String format = simpleDateFormat.format(date);
                                try {
                                    Date formatDate = simpleDateFormat.parse(format);
                                    //修改订单状态为已收货，确认收货时间，修改时间
                                    order.setStatus(STATUS_ORDER_FINISHED)
                                            .setConfirmStatus(STATUS_ORDER_CONFIRM)
                                            .setReceiveTime(formatDate)
                                            .setModifyTime(formatDate);
                                    Integer updateResult = WMOrderService.super.update(order);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, confirmOvertime,TimeUnit.MINUTES);
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

    /**
     * @Author Zhang Wei
     * @Description
     *      收货成功，根据订单编号修改订单状态
     *      修改用户表的积分和成长值
     *      修改积分修改历史记录
     *      定时自动好评
     * @Param [orderSn, token,memberService, commentService, orderItemService]
     * @Return java.lang.Boolean
     * @Date 2019/11/28
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean receiveSuccess(String  orderSn, String token, OrderSettingService orderSettingService, MemberService memberService, CommentService commentService, WMOrderItemService orderItemService){
        if(token != null && "" != token) {
            Order order = getOrderByOrderOrderSn(orderSn);
            Long memberId = order.getMemberId();
            Long orderId = order.getId();
            Long shopId = order.getShopId();
            //根据店id和用户id查询对应用户在对应店铺的积分和评论可获得的成长值
            MemberAndLevelVo memberAndLevel = memberService.getMemberAndLevel(memberId, shopId);
            //修改订单状态为收获成功，修改确认收货时间，修改时间
            try {
                Date date = new Date();
                String format = simpleDateFormat.format(date);
                Date formatDate = simpleDateFormat.parse(format);
                order.setStatus(STATUS_ORDER_FINISHED)
                        .setReceiveTime(formatDate)
                        .setConfirmStatus(STATUS_ORDER_CONFIRM)
                        .setModifyTime(formatDate);
                Integer updateResult = WMOrderService.super.update(order);
                if (updateResult > 0) {
                    //获取订单可获取的积分和成长值
                    Integer integration = order.getIntegration();
                    Integer growth = order.getGrowth();
                    //获取用户原有的积分和成长值
                    Integer integration1 = memberAndLevel.getIntegration();
                    Integer growth1 = memberAndLevel.getGrowth();
                    //获取历史积分
                    Integer historyIntegration = memberAndLevel.getHistoryIntegration();
                    //修改用户表的积分和成长值
                    memberAndLevel.setIntegration(integration + integration1);
                    memberAndLevel.setHistoryIntegration(integration1 + historyIntegration);
                    memberAndLevel.setGrowth(growth + growth1);

                    Boolean aBoolean = memberService.updateMember(memberAndLevel);
                    if (!aBoolean) {
                        throw  exception;
                    }
                    //获取店铺自动好评时间
                    OrderSetting orderSetting = orderSettingService.selectOrderSetting(shopId);
                    Integer commentOvertime = orderSetting.getCommentOvertime();
                    mScheduledExecutorService.schedule(new Runnable() {
                        @Override
                        public void run() {
                            Comment comment = new Comment();
                            comment.setOrderId(orderId).setShopId(shopId);
                            Comment comment1 = commentService.selectComment(comment);
                            //判断该订单是否已进行评论
                            if (comment1 == null) {
                                OrderItem orderItem = new OrderItem();
                                orderItem.setOrderSn(orderSn);
                                List<OrderItem> productByOrderId = orderItemService.getProductByOrderSn(orderItem);
                                //判断该订单是否只有一个商品
                                if (productByOrderId.size() > 0 && productByOrderId.size() == 1) {
                                    //如果该订单只有一个商品，productId等商品信息不为空
                                    comment.setProductId(productByOrderId.get(0).getProductId())
                                            .setProductName(productByOrderId.get(0).getProductName())
                                            .setProductAttribute(productByOrderId.get(0).getProductAttr())
                                            .setPics(productByOrderId.get(0).getProductPic());
                                }
                                Date date = new Date();
                                String format = simpleDateFormat.format(date);
                                try {
                                    Date formatDate = simpleDateFormat.parse(format);
                                    //设置好评，默认展示，好评时间
                                    comment.setStar(STATUS_COMMENT_GOOD)
                                            .setShowStatus(STATUS_COMMENT_SHOW)
                                            .setCreateTime(formatDate);
                                    Boolean aBoolean = commentService.addComment(comment);
                                    //设置订单表中的评论时间
                                    order.setCommentTime(formatDate)
                                            .setModifyTime(formatDate);
                                    Integer updateResult = WMOrderService.super.update(order);
                                    //评论成功后获得成长值
                                    Integer commentGrowthPoint = memberAndLevel.getCommentGrowthPoint();
                                    memberAndLevel.setGrowth(commentGrowthPoint + memberAndLevel.getGrowth());
                                    Boolean aBoolean1 = memberService.updateMember(memberAndLevel);
//                                    if(updat    eResult < 0 || !aBoolean|| !aBoolean1){
//                                        throw exception;
//                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, commentOvertime,TimeUnit.MINUTES);
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
    /**
     * @Author Zhang Wei
     * @Description
     *      取消订单，根据订单编号，修改订单信息，
     * @Param [orderSn]
     * @Return java.lang.Boolean
     * @Date 2019/11/26
     */
    public Boolean WXcancleOrder(String orderSn,String token){
        if(token != null && "" != token) {
            Order order = getOrderByOrderOrderSn(orderSn);
            try {
                Date date = new Date();
                String format = simpleDateFormat.format(date);
                Date date1 = simpleDateFormat.parse(format);
                order.setModifyTime(date1)
                        .setStatus(STATUS_ORDER_CLOSE);
                Integer updateResult = super.update(order);
                if (updateResult < 0) {
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

    /**
     * @Author Zhang Wei
     * @Description
     *      微信支付
     * @Param [openid, orderSn, amount, request]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Date 2019/12/6
     */
    public Map<String, Object> pay(String orderSn, String openid, Float amount, HttpServletRequest request){
        Map<String, Object> result= wxPay(orderSn,openid,amount,request);
        if("OK".equals(result.get("errMsg"))){
            //取消预约单，则删除延时队列中的超时等待信号
            Order order = getOrderByOrderOrderSn(orderSn);
            DelayCancelOrderTaskManager delayCancelOrderTaskManager = DelayCancelOrderTaskManager.getInstance();
            PayTimeoutCancelOrderProcessor processor = new PayTimeoutCancelOrderProcessor(order.getId());
            Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(15));
            long timeout = TimeUnit.NANOSECONDS.convert(duration.toNanos(), TimeUnit.NANOSECONDS);
            DelayCancelOrderTask<?> delayCancelOrderTask = new DelayCancelOrderTask<>(timeout, processor);
            delayCancelOrderTaskManager.removeTask(delayCancelOrderTask);
            return result;
        }
        return null;
    }

    /**
     * @Author Zhang Wei
     * @Description
     *      用户退出订单后又恢复下单
     * @Param [orderSn, openid, amount, request]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Date 2019/12/6
     */
    public Map<String, Object> toRestoreOrder(String orderSn, String openid, Float amount,HttpServletRequest request){
        Map<String, Object> result= PayUtil.wxPay(orderSn,openid,amount, request);
        if("OK".equals(result.get("errMsg"))){
            //取消预约单，则删除延时队列中的超时等待信号
            Order order = getOrderByOrderOrderSn(orderSn);
            DelayCancelOrderTaskManager delayCancelOrderTaskManager = DelayCancelOrderTaskManager.getInstance();
            PayTimeoutCancelOrderProcessor processor = new PayTimeoutCancelOrderProcessor(order.getId());
            Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(15));
            long timeout = TimeUnit.NANOSECONDS.convert(duration.toNanos(), TimeUnit.NANOSECONDS);
            DelayCancelOrderTask<?> delayCancelOrderTask = new DelayCancelOrderTask<>(timeout, processor);
            delayCancelOrderTaskManager.removeTask(delayCancelOrderTask);
            Order order1 = new Order();
            order1.setOrderSn(orderSn);
            order1.setStatus(STATUS_ORDER_NOT_PAID);
            try {
                Integer updateResult = super.update(order1);
                if(updateResult > 0){
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

   /**
    * @Author Zhang Wei
    * @Description
    *       根据订单编号修改订单状态为无效订单
    * @Param [ordersn]
    * @Return java.lang.Boolean
    * @Date 2019/12/6
    */
    public Boolean alterOrderStatus(String ordersn){
        Order order = new Order();
        order.setOrderSn(ordersn);
        order.setStatus(STATUS_ORDER_INVALID);
        try {
            Integer updateResult = super.update(order);
            if(updateResult>0){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @Author Zhang Wei
     * @Description
     *          获取未支付的订单列表
     * @Param []
     * @Return java.util.List<com.aaa.lee.app.domain.Order>
     * @Date 2019/12/6
     */
    public List<Order> getPayNotTimeoutOrderList(){
        Order order = new Order();
        order.setStatus(STATUS_ORDER_NOT_PAID);
        try {
            List<Order> orders = super.selectDomain(order);
            if(orders.size()>0){
                return orders;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
