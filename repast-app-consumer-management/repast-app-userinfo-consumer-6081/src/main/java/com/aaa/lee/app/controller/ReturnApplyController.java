package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.ReturnApply;
import com.aaa.lee.app.service.IRepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @Date 2019/11/23
 * @Description
 **/
@RestController
@Api(value = "退货申请", tags = "退货申请接口")
public class ReturnApplyController {
    @Autowired
    private IRepastService repastService;

    /**
     * @Author Administrator
     * @Description
     *        添加退货申请
     * @Date 2019/11/23
     * @Param [returnApply, token]
     * @return com.aaa.app.base.ResultData
     **/
    @PostMapping("/addReturnApply")
    @ApiOperation(value = "添加退货申请", notes = "添加退货申请")
    public ResultData addReturnApply(@RequestBody ReturnApply returnApply, @RequestParam("token") String token){
        return repastService.addReturnApply(returnApply,token);
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
    @ApiOperation(value = "通过orderId查询退货申请详情",notes = "通过orderId查询退货申请详情")
    public ResultData getReturnApplyInfoByOrderId(@RequestParam("orderId") Long orderId, @RequestParam("token") String token){
        return repastService.getReturnApplyInfoByOrderId(orderId, token);
    }

    /**
     * @Author Administrator
     * @Description
     *       根据订单id查询退货申请状态
     *          0->待处理; 1->退货中; 2->已完成; 3->已拒绝
     * @Date 2019/11/23
     * @Param [orderId, token]
     * @return com.aaa.app.base.ResultData
     **/
    @GetMapping("/getReturnStatusByOrderId")
    @ApiOperation(value = "根据订单id查询退货申请状态",notes = "根据订单id查询退货申请状态")
    public ResultData getReturnApplyStatusByOrderId(@RequestParam("orderId") Long orderId, @RequestParam("token") String token){
        return repastService.getReturnApplyStatusByOrderId(orderId, token);
    }

}
