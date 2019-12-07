package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.PmsProductVO;
import com.aaa.lee.app.service.IRepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @data 2019/11/25 23:08
 * @project repast-app-parent
 * @declaration:
 */
@RestController
@Api(value = "积分商城商品数据信息", tags = "积分商城商城信息接口")
public class IntegralGoodsController  extends BaseController {

    @Autowired
    private IRepastService repastService;

    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    @GetMapping("/getIntegralGoods")
    @ApiOperation(value = "积分商城商品数据信息", notes = "进行查询操作")
    public ResultData getIntegralGoods(Long productId, String token){
        PmsProductVO integralGoods = repastService.getIntegralGoods(productId,token);
        if(null != integralGoods) {
            return operateSuccess(integralGoods);
        } else {
            return operateFailed();
        }
    }

    /**
     * 提交积分物品，生成积分订单
     */
    @PostMapping("/setIntegralOrder")
    @ApiOperation(value = "积分订单",notes = "通过vo实体类数据添加积分订单")
    public ResultData setIntegralOrder(@RequestBody PmsProductVO productVO, @RequestParam("token") String token){
        int i = repastService.setIntegralOrder(productVO,token);
        if (i > 0){
            return operateSuccess();
        }
        return operateFailed();
    }

}
