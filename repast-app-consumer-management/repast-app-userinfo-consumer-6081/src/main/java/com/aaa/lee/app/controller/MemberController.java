package com.aaa.lee.app.controller;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.domain.Member;
import com.aaa.lee.app.domain.MemberReceiveAddress;
import com.aaa.lee.app.service.IRepastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Company AAA软件教育
 * @Author Lee
 * @Date Create in 2019/11/21 20:10
 * @Description
 **/
@RestController
@Api(value = "用户信息", tags = "用户信息接口")
public class MemberController<Res, Resd> extends BaseController {
    @Autowired
    private IRepastService repastService;

    /**
     * @author  Lee
     * @description
     *      执行登录操作
     * @param
     * @date 2019/11/21
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "执行登录操作")
    public ResultData doLogin(Member member) {
        if(repastService.doLogin(member)) {
            return  operateSuccess();
        } else {
            return operateFailed();
        }
    }
    /**
     * @Author Lee
     * @Description
     *      获取收货地址
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/21
     */
    @GetMapping("/receive")
    @ApiOperation(value = "收货地址", notes="获取会员收货地址")
    public  ResultData getMemberReceiveAddress(){
        List<MemberReceiveAddress> memberReceiveAddress = repastService.getMemberReceiveAddress();
        if (memberReceiveAddress.size()>0){
            return  exist(memberReceiveAddress);
        }else {
            return notExist();
        }
    }



}
