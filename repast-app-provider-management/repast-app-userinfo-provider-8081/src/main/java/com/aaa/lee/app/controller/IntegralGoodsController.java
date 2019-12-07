package com.aaa.lee.app.controller;

import com.aaa.lee.app.domain.PmsProductVO;
import com.aaa.lee.app.service.IntegralGoodsService;
import com.aaa.lee.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @data 2019/11/25 22:13
 * @project repast-app-parent
 * @declaration:
 * 积分商城：
 *      1、获取页面方法
 *      2、操作订单方法
 */
@RestController
public class IntegralGoodsController {

    /**
     * @TODO
     */
    @Autowired
    private IntegralGoodsService goodsService;

    @Autowired
    private MemberService memberService;

    /**
     * 通过商品id获取整条积分商城的信息
     * @param productId
     * @return
     */
    @GetMapping("/getIntegralGoods")
    public PmsProductVO getIntegralGoods(Long productId, String token){
        PmsProductVO pmsProductVO = goodsService.getPmsProductVO(productId,token);
        return pmsProductVO;
    }

    /**
     * 提交积分物品，生成积分订单
     */
    @PostMapping("/setIntegralOrder")
    public int setIntegralOrder(@RequestBody PmsProductVO productVO, String token){
        int i = goodsService.setIntegralOrder(productVO,token,memberService);
        return i;
    }
}
