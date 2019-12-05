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
    //已发货状态
    public static final Integer STATUS_ORDER_SENT =2;
    //已完成状态
    public static final Integer STATUS_ORDER_FINISHED =3;
    //关闭订单状态
    public static final Integer STATUS_ORDER_CLOSE=4;
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

}
