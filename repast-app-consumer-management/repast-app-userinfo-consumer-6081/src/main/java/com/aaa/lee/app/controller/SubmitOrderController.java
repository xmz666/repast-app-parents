package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.SubmitOrderVO;
import com.aaa.lee.app.service.IRepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 * @data 2019/11/21 20:30
 * @project repast-app-parent
 * @declaration:
 */
@RestController
@Api(value = "提交订单页面",tags = "这是购物车与实际付款生成订单的中间页面")
public class SubmitOrderController extends BaseController {

    @Autowired
    private IRepastService repastService;

    /**
     * 从数据库中获取购物车信息，整合获取订单信信
     * @return
     */
    @PostMapping("/getSubmitOrder")
    @ApiOperation(value = "订单信息",notes = "提交订单页面商品信息")
    public ResultData getSubmitOrder(Long shopId, String token){
        List<SubmitOrderVO> submitOrder = repastService.getSubmitOrder(shopId,token);
        if (null != submitOrder){
            return operateSuccess(submitOrder);
        }else{
            return operateFailed();
        }
    }

    /**
     * 创建订单
     * @param submitOrderVO
     * @return
     */
    @PostMapping("/setOrder")
    @ApiOperation(value = "生成订单表",notes = "通过页面传过来的信息创建订单")
    public ResultData setOrder(@RequestBody List<SubmitOrderVO> submitOrderVO, @RequestParam("token") String token){
        int i = repastService.setOrder(submitOrderVO,token);
        System.out.println(submitOrderVO);
        if (i > 0){
            return operateSuccess();
        }else {
            return operateFailed();
        }
    }


}