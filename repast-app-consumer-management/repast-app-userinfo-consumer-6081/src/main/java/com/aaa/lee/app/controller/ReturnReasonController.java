package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.service.IRepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Administrator
 * @Date 2019/11/22
 * @Description
 **/
@RestController
@Api(value = "退货原因", tags = "退货原因接口")
public class ReturnReasonController extends BaseController {
    @Autowired
    private IRepastService repastService;

    /**
     * @Author Administrator
     * @Description
     *        根据店铺ID获取退货原因列表
     * @Date 2019/11/22
     * @Param [shopId]
     * @return com.aaa.app.base.ResultData
     **/
    @GetMapping("/queryReturnReasonListByShopId")
    @ApiOperation(value = "退货原因列表", notes = "根据店铺ID获取退货原因列表")
    public ResultData queryReturnReasonList(@RequestParam("token") String token, @RequestParam("shopId") Long shopId){
        return repastService.getReturnReasonListByShopId(token,shopId);
    }

    @GetMapping("/queryReturnReasonList")
    @ApiOperation(value = "退货原因列表", notes = "获取可用退货原因列表,不根据shop_id")
    public ResultData queryReturnReasonList(@RequestParam("token") String token){
        return repastService.getReturnReasonList(token);
    }

}
