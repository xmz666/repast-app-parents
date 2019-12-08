package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.domain.Order;
import com.aaa.lee.app.domain.OrderItem;
import com.aaa.lee.app.domain.ReturnApply;
import com.aaa.lee.app.mapper.ReturnApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author Administrator
 * @Date 2019/11/23
 * @Description
 **/
@Service
public class ReturnApplyService extends BaseService<ReturnApply> {

    @Autowired
    private ReturnApplyMapper returnApplyMapper;

    @Override
    public Mapper<ReturnApply> getMapper() {
        return returnApplyMapper;
    }

    /**
     * @Author Administrator
     * @Description
     *        添加退货申请
     * @Date 2019/11/23
     * @Param [returnApply, orderRAService, orderItemRAService, shopInfoRAService]
     * @return java.lang.Integer
     **/
    @Transactional(rollbackFor = Exception.class)
    public Integer addReturnApply(ReturnApply returnApply, OrderForReturnApplyService orderRAService,
                                  OrderItemForReturnApplyService orderItemRAService, ShopInformationForReturnApplyService shopInfoRAService){
        //从订单中查询退货申请需要的信息
        Order orderInfoForReturnApply = orderRAService.getOrderInfoByOrderId(returnApply.getOrderId());
        //查询商店名称作为退货申请中的收货人
        String shopName = shopInfoRAService.getShopNameByShopId(orderInfoForReturnApply.getShopId());
        //从订单明细中查询要退货的商品列表
        List<OrderItem> returnProductList = orderItemRAService.getProductListByOrderId(returnApply.getOrderId());
        //把从订单中查询到的信息和查到的商品名称填入退货申请中
        settingRAInfoWithOrderInfoAndShopName(returnApply, orderInfoForReturnApply,shopName);
        //将要退货的商品生成相应的退货申请列表(returnApplyList)
        List<ReturnApply> returnApplyList = generateReturnApplyList(returnApply, returnProductList);
        //批量新增退款申请
        Integer addResult = returnApplyMapper.batchInsertReturnApply(returnApplyList);
        //开启定时任务，商家两天后未处理，则此次申请关闭，申请状态改为已关闭
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> {

            //关闭退货申请前前查看状态是否为 0(待处理)，如果是，则更改申请状态，更改为 3(已拒绝)
            Integer applyStatus = getReturnApplyStatusByOrderId(returnApply.getOrderId());
            if (0 == applyStatus){
                //更改申请状态为 3(已拒绝)
                Integer updateResult = returnApplyMapper.updateStatusByOrderId(returnApply.getOrderId(), 3);
                if (updateResult <= 0){
                    //如果申请状态更新失败，则再次尝试更新
                    returnApplyMapper.updateStatusByOrderId(returnApply.getOrderId(),3);
                }
            }
        },2, TimeUnit.DAYS);

        if (addResult > 0){
            return addResult;
        }
        return null;
    }

    /**
     * @Author Administrator
     * @Description
     *        将要退货的商品生成相应的退货申请列表(returnApplyList)
     * @Date 2019/11/23
     * @Param [returnApply, returnProductList]
     * @return java.util.List<com.aaa.app.domain.ReturnApply>
     **/
    private List<ReturnApply> generateReturnApplyList(ReturnApply returnApply, List<OrderItem> returnProductList) {
        List<ReturnApply> returnApplyList = new ArrayList<ReturnApply>();
        for (OrderItem orderItem : returnProductList) {
            ReturnApply apply = settingRAInfoWithProductInfoInOrderItem(returnApply, orderItem);
            returnApplyList.add(apply);
        }
        return returnApplyList;
    }

    /**
     * @Author Administrator
     * @Description
     *       根据订单id查询退货申请状态
     *          0->待处理; 1->退货中; 2->已完成; 3->已拒绝
     * @Date 2019/11/23
     * @Param
     * @return
     **/
    public Integer getReturnApplyStatusByOrderId(Long orderId){
        ReturnApply returnApply = new ReturnApply().setOrderId(orderId);
        List<ReturnApply> returnApplyList = returnApplyMapper.select(returnApply);
        Integer returnApplyStatus = returnApplyList.get(0).getStatus();
        return returnApplyStatus;
    }

    /**
     * @Author Administrator
     * @Description
     *        通过orderId查询退货申请详情
     * @Date 2019/11/23
     * @Param [orderId]
     * @return java.util.List<com.aaa.app.domain.ReturnApply>
     **/
    public List<ReturnApply> getReturnApplyInfoByOrderId(Long orderId){
        List<ReturnApply> returnApplyList = returnApplyMapper.select(new ReturnApply().setOrderId(orderId));
        return returnApplyList;
    }

    /**
     * @Author Administrator
     * @Description
     *        把查到的商品名称和从订单中查询到的信息填入ReturnApply对象中
     * @Date 2019/11/23
     * @Param [returnApply, orderInfoForReturnApply, shopName]
     * @return void
     **/
    private void settingRAInfoWithOrderInfoAndShopName(ReturnApply returnApply, Order orderInfoForReturnApply, String shopName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            returnApply.setShopId(orderInfoForReturnApply.getShopId())
                       .setOrderSn(orderInfoForReturnApply.getOrderSn())
                       .setMemberUsername(orderInfoForReturnApply.getMemberUsername())
                       .setReturnName(orderInfoForReturnApply.getReceiverName())
                       .setReturnPhone(orderInfoForReturnApply.getReceiverPhone())
                       .setReceiveMan(shopName)
                       .setReceiveTime(orderInfoForReturnApply.getReceiveTime())
                       .setCreateTime(sdf.parse(sdf.format(new Date())))
                       .setStatus(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author Administrator
     * @Description
     *        把从订单明细中查到的要退货的商品信息填入到ReturnApply对象中
     * @Date 2019/11/23
     * @Param [returnApply, orderItem]
     * @return void
     **/
    private ReturnApply settingRAInfoWithProductInfoInOrderItem(ReturnApply returnApply, OrderItem orderItem) {
        returnApply.setProductId(orderItem.getProductId())
                   .setProductPic(orderItem.getProductPic())
                   .setProductName(orderItem.getProductName())
                   .setProductBrand(orderItem.getProductBrand())
                   .setProductAttr(orderItem.getProductAttr())
                   .setProductCount(orderItem.getProductQuantity())
                   .setProductPrice(orderItem.getProductPrice())
                   .setProductRealPrice(orderItem.getRealAmount())
                   .setReturnAmount(orderItem.getRealAmount());
        return returnApply;
    }

}
