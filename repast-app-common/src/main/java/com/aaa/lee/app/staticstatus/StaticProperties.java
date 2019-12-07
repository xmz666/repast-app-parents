package com.aaa.lee.app.staticstatus;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/21 9:58
 * @Description
 **/
public class StaticProperties {

    public static final String OK = "OK";
    public static final String REDIS_KEY = "member";
    //支付方式为微信支付
    public static final Integer STATUS_ORDER_WXPAID =2;
    //待发货状态
    public static final Integer STATUS_ORDER_PAID =1;
    //待支付状态
    public static final Integer STATUS_ORDER_NOT_PAID =0;
    //已发货状态
    public static final Integer STATUS_ORDER_SENT =2;
    //已完成状态
    public static final Integer STATUS_ORDER_FINISHED =3;
    //关闭订单状态
    public static final Integer STATUS_ORDER_CLOSE=4;
    //无效订单状态
    public static final Integer STATUS_ORDER_INVALID=5;
    //确认收货状态
    public static final Integer STATUS_ORDER_CONFIRM=1;
    //未确认收货
    public static final Integer STATUS_ORDER_NOT_CONFIRM=0;
    //评论状态：显示
    public static final Integer STATUS_COMMENT_SHOW=1;
    //好评
    public static final Integer STATUS_COMMENT_GOOD=5;
    //积分变化类型：外卖获取积分
    public static final Integer STATUS_INTEGRATION_TAKE_OUT_ORDER=1;
    //积分变化类型：食堂点餐获取积分
    public static final Integer STATUS_INTEGRATION_RESTURANT_ORDER=2;
    //积分变化类型：管理员操作积分
    public static final Integer STATUS_INTEGRATION_MANAGER_OPERATED=3;
    //积分变化类型：积分兑换消耗积分
    public static final Integer STATUS_INTEGRATION_INTEGRATION_CONSUMED=4;
    //积分来源：购物奖励积分
    public static final Integer STATUS_INTEGRATION_COME_FROM_REWORD=0;
    //积分来源：管理员操作
    public static final Integer STATUS_INTEGRATION_COME_FROM_MANAGER_OPERATED=1;
    //积分来源：购物消费
    public static final Integer STATUS_INTEGRATION_COME_FROM_CONSUMED=2;
    //店铺积分规则
    public static final Integer STATUS_SHOP_INTEGRATION_RULE=0;
    //店铺成长值规则
    public static final Integer STATUS_SHOP_GROWTH_RULE=1;

    //删除状态：0->未删除；1->已删除
    public static final Integer DELETE_STATUS_EXIST=0;
    public static final Integer DELETE_STATUS_NOEXIST=1;

    //生成订单时的状态  订单状态：0.外卖 ，1.订餐 ， 2. 拼团 ， 3. 预定 4. 超市 ，5. 积分订单
    public static final Integer ORDER_STATUS_ZERO = 0;
    public static final Integer ORDER_STATUS_ONE = 1;
    public static final Integer ORDER_STATUS_TWO = 2;
    public static final Integer ORDER_STATUS_THREE = 3;
    public static final Integer ORDER_STATUS_FOUR = 4;
    public static final Integer ORDER_STATUS_FIVE = 5;

    //订单付款状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
    public static final Integer NEW_ORDER_STATUS_ZERO = 0;
    public static final Integer NEW_ORDER_STATUS_ONE = 1;
    public static final Integer NEW_ORDER_STATUS_TWO = 2;
    public static final Integer NEW_ORDER_STATUS_THREE = 3;
    public static final Integer NEW_ORDER_STATUS_FOUR = 4;
    public static final Integer NEW_ORDER_STATUS_FIVE = 5;

    //购买积分商城商品的件数
    public static final Integer INTEGRAGOODS_NUMBER = 1;

    //积分变化类型 1. 外卖下单获取积分，2.食堂下单获取积分，3. 管理员修改 ，4. 积分兑换消耗积分
    public static final Integer INTEGRA_CHANGE_ONE = 1;
    public static final Integer INTEGRA_CHANGE_TWO = 2;
    public static final Integer INTEGRA_CHANGE_THREE = 3;
    public static final Integer INTEGRA_CHANGE_FOUR = 4;

    //改变购物车中的状态码   顾客可见状态： 0 ： 生成订单前可见  1：生成订单后不可见
    public static final Integer CART_STATUS_ZERO = 0;
    public static final Integer CART_STATUS_ONE = 1;



}
