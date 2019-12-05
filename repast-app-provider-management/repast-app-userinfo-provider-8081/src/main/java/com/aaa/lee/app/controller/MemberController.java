package com.aaa.lee.app.controller;

import com.aaa.lee.app.domain.Member;
import com.aaa.lee.app.domain.MemberReceiveAddress;
import com.aaa.lee.app.service.MemberReceiveAddressService;
import com.aaa.lee.app.service.MemberService;
import com.aaa.lee.app.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/21 9:36
 * @Description
 **/
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MemberReceiveAddressService  memberReceiveAddressService;
    /**
     * @author Seven Lee
     * @description
     *      执行登录操作
     * @param
     * @date 2019/11/21
     * @return java.lang.Boolean
     * @throws 
    **/
    @PostMapping("/login")
    public Boolean doLogin(@RequestBody Member member) {
        return memberService.doLogin(member, redisService);
    }

/**
 * @Author Lee
 * @Description
 *  获取收货地址
 * @Param  * @param
 * @Return java.util.List<com.aaa.lee.app.domain.MemberReceiveAddress>
 * @Date 2019/11/21
 */
    @GetMapping("/recive")
    public List<MemberReceiveAddress> getMemberReceiveAddress(){
        return memberReceiveAddressService.getMemberReceiveAddress(redisService);

    }
}
