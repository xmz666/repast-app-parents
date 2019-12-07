package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.service.IRepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@Api(value = "购物车",tags = "购物车操作接口")
public class CartController extends BaseController {

    @Autowired
    private IRepastService repastService;

    @PostMapping("/addCart")
    @ApiOperation(value = "更新购物车",notes = "更新购物车接口")
    public ResultData addCart(@RequestBody Map<String,Object> data){
        if(repastService.addCart(data)){
            return operateSuccess();
        }else{
            return operateFailed();
        }
    }
    @PostMapping("/selectCart")
    @ApiOperation(value = "查询购物车",notes = "查询购物车接口")
    public ResultData selectCart(@RequestBody Map<String,Object> data){
        if(null != repastService.selectCart(data)){
            return operateSuccess(repastService.selectCart(data));
        }else{
            return operateFailed();
        }
    }
    @PostMapping("/deleteCart")
    @ApiOperation(value = "清空购物车",notes = "清空购物车接口")
    public ResultData deleteCartByShopId(@RequestBody Map<String,Object> data){
        if(null != repastService.deleteCartByShopId(data)){
            return operateSuccess();
        }else{
            return operateFailed();
        }
    }


}
