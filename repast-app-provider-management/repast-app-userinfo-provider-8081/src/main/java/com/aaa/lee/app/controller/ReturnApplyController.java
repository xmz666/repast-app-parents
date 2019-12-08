package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.service.OrderForReturnApplyService;
import com.aaa.lee.app.service.OrderItemForReturnApplyService;
import com.aaa.lee.app.service.ReturnApplyService;
import com.aaa.lee.app.service.ShopInformationForReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aaa.lee.app.staticstatus.StaticProperties.*;


/**
 * @Author Administrator
 * @Date 2019/11/23
 * @Description
 **/
@RestController
public class ReturnApplyController extends BaseController {

    @Autowired
    private OrderForReturnApplyService orderRAService;
    @Autowired
    private OrderItemForReturnApplyService orderItemRAService;
    @Autowired
    private ShopInformationForReturnApplyService shopInfoRAService;
    @Autowired
    private ReturnApplyService returnApplyService;


    /**
     * @Author Administrator
     * @Description
     *        添加退货申请
     * @Date 2019/11/23
     * @Param [returnApply, token]
     * @return java.lang.Integer
     **/
    @PostMapping("/addReturnApply")
    public ResultData addReturnApply(@RequestBody ReturnApply returnApply, @RequestParam("token") String token){
        Integer addResult = returnApplyService.addReturnApply(returnApply, orderRAService, orderItemRAService, shopInfoRAService);
        if (null==token || "".equals(token)){
            return tokenLost();
        } else if (null==addResult || addResult <=0){
            return returnApplyFailed();
        } else {
            return operateSuccess();
        }
    }

    /**
     * @Author Administrator
     * @Description
     *        通过orderId查询退货申请详情
     * @Date 2019/11/23
     * @Param [orderId, token]
     * @return com.aaa.app.base.ResultData
     **/
    @GetMapping("/getReturnInfoByOrderId")
    public ResultData getReturnApplyInfoByOrderId(@RequestParam("orderId") Long orderId, @RequestParam("token") String token){
        List<ReturnApply> returnApplyList = returnApplyService.getReturnApplyInfoByOrderId(orderId);
        if (null==token || "".equals(token)){
            return tokenLost();
        } else if (null!=returnApplyList && returnApplyList.size()>0){
            return operateSuccess(returnApplyList);
        } else {
            return operateFailed();
        }
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
    @GetMapping("/getReturnStatusByOrderId")
    public ResultData getReturnApplyStatusByOrderId(@RequestParam("orderId") Long orderId, @RequestParam("token") String token){
        Integer returnApplyStatus = returnApplyService.getReturnApplyStatusByOrderId(orderId);
        // token丢失
        if (null==token || "".equals(token)){
            return tokenLost();
        }
        // 退货申请成功,等待商家处理
        if (APPLY_SUCCESS.equals(returnApplyStatus)){
            return returnApplySuccess(returnApplyStatus);
            // 商家审核通过,退款申请已提交至微信
        } else if (APPLY_APPROVED.equals(returnApplyStatus)){
            return returnApplyApproved(returnApplyStatus);
            // 退款已入账
        } else if (REFUND_SUCCEED.equals(returnApplyStatus)){
            return returnApplyRefundSucceed(returnApplyStatus);
            // 商家拒绝退款，退款失败
        } else if (APPLY_REFUSED.equals(returnApplyStatus)){
            return returnApplyRefused(returnApplyStatus);
        } else {
            return operateFailed();
        }
    }
}
