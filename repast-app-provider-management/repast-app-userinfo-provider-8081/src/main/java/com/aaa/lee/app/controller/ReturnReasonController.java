package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.ReturnReason;
import com.aaa.lee.app.service.ReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2019/11/22
 * @Description
 **/
@RestController
public class ReturnReasonController extends BaseController {

    @Autowired
    private ReturnReasonService returnReasonService;

    /**
     * @Author Administrator
     * @Description
     *        根据店铺ID获取退货原因列表
     * @Date 2019/11/22
     * @Param [shopId]
     * @return java.util.List<com.aaa.app.domain.ReturnReason>
     **/
    @GetMapping("/getReturnReasonListByShopId")
    public ResultData getReturnReasonListByShopId(@RequestParam("token") String token, @RequestParam("shopId") Long shopId){
        if (null==token || "".equals(token)){
            return tokenLost();
        }
        try {
            List<ReturnReason> returnReasonList = returnReasonService.getReturnReasonListByShopId(shopId);
            if (null!=returnReasonList && returnReasonList.size()>0){
                return operateSuccess(returnReasonList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operateFailed();
    }

    /**
     * @Author Administrator
     * @Description
     *        获取可用原因列表
     * @Date 2019/11/22
     * @Param []
     * @return java.util.List<com.aaa.app.domain.ReturnReason>
     **/
    @GetMapping("/getReturnReasonList")
    public ResultData getReturnReasonList(@RequestParam("token") String token){
        if (null==token || "".equals(token)){
            return tokenLost();
        }
        List<ReturnReason> returnReasonList = returnReasonService.getReturnReasonList();
        if (null!=returnReasonList && returnReasonList.size()>0){
            return operateSuccess(returnReasonList);
        }
        return operateFailed();
    }



}
